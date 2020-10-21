package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.log.StaticLog;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.apl.lib.exception.AplException;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lms.price.exp.manage.service.ExportPriceService;
import com.apl.lms.price.exp.manage.service.PriceExpRemarkService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.manage.service.PriceZoneNameService;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.apl.lms.price.exp.pojo.po.PriceZoneNamePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataObjVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author hjr start
 * @Classname ExportPriceExlServiceInpl
 * @Date 2020/10/15 15:22
 */
@Service
public class ExportPriceServiceImpl implements ExportPriceService {

    enum ExportPriceEnum {

        EXPORT_SUCCESS("EXPORT_SUCCESS", "EXPORT SUCCESS! 导出成功!"),
        EXPORT_FAILED("EXPORT_FAILED", "EXPORT_FAILED! 导出失败!"),
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "NO_CORRESPONDING_DATA! 没有对应数据!"),
        NO_VALID_FILE_WAS_FOUND("NO_VALID_FILE_WAS_FOUND", "没有找到有效文件"),
        TEMPLATE_DOES_NOT_EXIST("Template does not exist", "模板不存在");

        private String code;
        private String msg;

        ExportPriceEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
        ;
    }

    @Autowired
    PriceExpService priceExpService;

    @Autowired
    PriceZoneNameService priceZoneNameService;

    @Autowired
    PriceExpRemarkService priceExpRemarkService;


    @Value("${lms.exp-price.export.template-file-name:}")
    String templateFileName;

    @Value("${lms.exp-price.export.out-file-name:export-exp-price.xlsx}")
    String outFileName;

    @Override
    public void exportExpPrice(HttpServletResponse response, List<Long> ids) throws IOException {

        if(null==templateFileName || templateFileName.length()<2){
            throw new AplException(ExportPriceEnum.NO_VALID_FILE_WAS_FOUND.code, ExportPriceEnum.NO_VALID_FILE_WAS_FOUND.msg, null);
        }

        //注意 使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setCharacterEncoding("utf-8");
        ExcelWriter excelWriter = null;
        String newTempFileName = null;

        try {

            //价格表数据
            Map<Long, PriceExpDataObjVo> priceDataMap = new HashMap<>();

            for (Long id : ids) {
                PriceExpDataObjVo priceExpDataInfo = priceExpService.getPriceExpDataInfoByPriceId(id);
                priceDataMap.put(id, priceExpDataInfo);
            }
            //价格表内容
            List<ExpPriceInfoBo> expPriceInfoList = priceExpService.getPriceInfoByIds(ids);

            //备注
            Map<Long, PriceExpRemarkPo> priceExpRemarkMap = priceExpRemarkService.getPriceExpRemarkBatch(ids);

            //分区
            List<Long> zoneIds = new ArrayList<>();
            for (ExpPriceInfoBo expPriceInfoBo : expPriceInfoList) {
                zoneIds.add(expPriceInfoBo.getZoneId());
            }
            Map<Long, PriceZoneNamePo> priceZoneNameMap = priceZoneNameService.getPriceZoneNameBatch(zoneIds);

            //创建新的模板文件，并复制模板Sheet
            SecurityUser securityUser = CommonContextHolder.getSecurityUser();
            String templateFileNameByTenant = templateFileName.replace("-tenant", "-"+securityUser.getInnerOrgCode());
            File templateFile = new File(templateFileNameByTenant);
            if(!templateFile.exists()) {
                templateFileNameByTenant = templateFileName.replace("-tenant", "-common");
                templateFile = new File(templateFileName);
            }

            if(!templateFile.exists()) {
                throw new AplException(ExportPriceEnum.TEMPLATE_DOES_NOT_EXIST.code, ExportPriceEnum.TEMPLATE_DOES_NOT_EXIST.msg, null);
            }
            //返回上一级目录, 即不带文件名的全路径, 构建临时文件全路径
            newTempFileName = templateFile.getParent() + "/export-exp-price-temp-" + UUID.randomUUID() + ".xlsx";

            FileInputStream fs = new FileInputStream(templateFileNameByTenant);
            XSSFWorkbook wb = new XSSFWorkbook(fs);
            Sheet templateSheet =  wb.getSheetAt(0);
            for (int sheetNo = 1; sheetNo < expPriceInfoList.size(); sheetNo++) {
                Sheet newSheet =  wb.createSheet();
                wb.setSheetName(sheetNo, expPriceInfoList.get(sheetNo).getPriceName());
                CopySheet(templateSheet, newSheet);
            }
            wb.setSheetName(0, expPriceInfoList.get(0).getPriceName());
            //输出临时模板文件
            FileOutputStream fileOut = new FileOutputStream(newTempFileName);
            wb.write(fileOut);
            fileOut.close();

            //按新的模板文件创建excelWriter对象
            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(newTempFileName).build();

            // 填写多个工作表
            for (int sheetNo = 0; sheetNo < expPriceInfoList.size(); sheetNo++) {

                ExpPriceInfoBo expPriceInfo = expPriceInfoList.get(sheetNo);
                List<List<Object>> priceDataList = priceDataMap.get(expPriceInfo.getId()).getPriceData();
                if (null == priceDataList || priceDataList.size() < 1) {
                    continue;
                }

                WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo).build();

                //获取备注
                PriceExpRemarkPo priceExpRemarkPo = priceExpRemarkMap.get(expPriceInfo.getId());

                //获取分区
                PriceZoneNamePo priceZoneNamePo = priceZoneNameMap.get(expPriceInfo.getZoneId());

                //填写一个工作表
                fillShell(excelWriter, writeSheet, expPriceInfo, priceExpRemarkPo, priceZoneNamePo, priceDataList);
            }

            //web导出
            response.setContentType("application/vnd.ms-excel");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            outFileName = URLEncoder.encode(outFileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + outFileName);

            //关闭excel
            if (null != excelWriter) {
                excelWriter.finish();
            }

        } catch (Exception e) {

            //关闭excel
            if (null != excelWriter) {
                excelWriter.finish();
            }

            e.printStackTrace();
            StaticLog.error(e);
        }
        finally {
            try {
                if(null!=newTempFileName) {
                    File delFile = new File(newTempFileName);
                    delFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                StaticLog.error(e);
            }
        }

    }

    void fillShell(ExcelWriter excelWriter,
                   WriteSheet writeSheet,
                   ExpPriceInfoBo expPriceInfo,
                   PriceExpRemarkPo priceExpRemarkPo,
                   PriceZoneNamePo priceZoneNamePo,
                   List<List<Object>> priceDataList){


        //填充报价格信息内容
        filePriceInfo(excelWriter, writeSheet, expPriceInfo, priceExpRemarkPo, priceZoneNamePo);

        //填写新的工作表内容
        fillPriceData(excelWriter, writeSheet, priceDataList);

        //填写备注
        List<String> saleRemarkList = new ArrayList<>();
        String[] remarkArray = priceExpRemarkPo.getSaleRemark().split("\\n");
        for (String s : remarkArray) {
            saleRemarkList.add(s);
        }
        fillPriceRemark(excelWriter, writeSheet,saleRemarkList);
    }

    //填写备注信息内容
    void fillPriceRemark(ExcelWriter excelWriter, WriteSheet writeSheet, List<String> saleRemarkList) {

        Sheet sheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();

        //查找priceList单元格
        Cell priceListFistCell = findPriceListFirstCell(sheet, "remark");

        Row fieldRow = priceListFistCell.getRow();
//        int priceColIndex = priceListFistCell.getColumnIndex();

        // 构建表格数据，行为Map类型
        // 和填写模板sheet字段( {c1}, {c2}, {c3}...)
        Map<String, String> rowMap;
        String colName;
        List<Map<String, String>> priceDataListNew = new ArrayList<>();
        for (int index = 0; index < saleRemarkList.size(); index++) {
            rowMap = new HashMap<>();
            colName = "c" + index;
            rowMap.put(colName, saleRemarkList.get(index));
            Cell cell = fieldRow.createCell(index);
            cell.setCellValue("{." + colName + "}");
            priceDataListNew.add(rowMap);
        }
        //填写表格数据
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(priceDataListNew, fillConfig, writeSheet);
    }

    //填充报价格信息内容
//    Sheet getTempSheet(ExcelWriter excelWriter, WriteSheet writeSheet) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("priceName", "{priceName}");
//        map.put("endData", "{endData}");
//        map.put("remark", "{remark}");
//        map.put("channelCategory", "{channelCategory}");
//        map.put("zoneName", "{zoneName}");
//        excelWriter.fill(map, writeSheet);
//        Sheet templateSheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();
//
//        return templateSheet;
//    }

    //填充报价格信息内容
    void filePriceInfo(ExcelWriter excelWriter, WriteSheet writeSheet, ExpPriceInfoBo expPriceInfo, PriceExpRemarkPo priceExpRemarkPo, PriceZoneNamePo priceZoneNamePo) {
        Map<String, Object> map = new HashMap<>();
        if(null != expPriceInfo) {
            map.put("priceName", expPriceInfo.getPriceName());
            map.put("endData", expPriceInfo.getEndDate());
        }
//        if(null != priceExpRemarkPo) {
//            map.put("remark", priceExpRemarkPo.getSaleRemark());
//        }
        if(null != priceZoneNamePo) {
            map.put("channelCategory", priceZoneNamePo.getChannelCategory());
            map.put("zoneName", priceZoneNamePo.getZoneName());
        }

        excelWriter.fill(map, writeSheet);
    }

    //填写报价格信息内容
    void fillPriceData(ExcelWriter excelWriter, WriteSheet writeSheet, List<List<Object>> priceDataList) {

        Sheet sheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();

        //查找priceList单元格
        Cell priceListFistCell = findPriceListFirstCell(sheet, "priceList");

        Row fieldRow = priceListFistCell.getRow();
        int priceColIndex = priceListFistCell.getColumnIndex();

        // 构建表格数据，行为Map类型
        // 和填写模板sheet字段( {c1}, {c2}, {c3}...)
        int colLen;
        String colName;
        Map rowMap;
        List<Map<String, Object>> priceDataListNew = new ArrayList<>();
        for (List<Object> objectList : priceDataList) {
            rowMap = new HashMap<>();
            colLen = objectList.size();
            for (Integer colIndex = priceColIndex; colIndex < colLen; colIndex++) {

                colName = "c" + colIndex.toString();
                rowMap.put(colName, objectList.get(colIndex));

                Cell cell = fieldRow.createCell(colIndex);
                cell.setCellValue("{." + colName + "}");
            }

            priceDataListNew.add(rowMap);
        }

        //填写表格数据
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(priceDataListNew, fillConfig, writeSheet);
    }


    //查找单元格
    Cell findPriceListFirstCell(Sheet templateSheet, String cellInfo) {

        Cell cell;
        String strVal;
        Row fieldRow;
        for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
            fieldRow = templateSheet.getRow(rowIndex);
            if (null != fieldRow) {
                for (int colIndex = 0; colIndex < 4; colIndex++) {
                    cell = fieldRow.getCell(colIndex);
                    cell.setCellType(CellType.STRING);
                    if (null != cell) {
                        strVal = cell.getStringCellValue();
                        if (null != strVal && strVal.trim().equals(cellInfo)) {
                            //找到单元格
                            return cell;
                        }
                    }
                }
            }
        }

        //没有找到priceList单元格
        return null;
    }


    //复制单元格
    void CopySheet(Sheet sourceSheet, Sheet targetSheet) {
        //getLastRowNum 获取有数据的最后一行的行号  getLastCellNum获取有数据的最后一列的列号
        int rowLastRowNum = sourceSheet.getLastRowNum();

        //遍历行
        for(int rowIndex=0; rowIndex<=rowLastRowNum; rowIndex++){

            //获取第<rowIndex>行
            Row sourceRow = sourceSheet.getRow(rowIndex);
            //获取当前行的最后一列列号
            int lastCellNum = sourceRow.getLastCellNum();
            //创建第<rowIndex>行
            Row targetRow = targetSheet.createRow(rowIndex);

            //遍历列
            for (int colIndex = 0; colIndex <lastCellNum; colIndex++) {
                //在当前行中取第<colIndex>个单元格
                Cell sourceCell = sourceRow.getCell(colIndex);
                if(null!=sourceCell) {
                    //获取单元格中的值
                    String sourceCellVal = sourceCell.getStringCellValue();
                    //为目标行创建单元格
                    Cell targetCell = targetRow.createCell(colIndex);
                    //为单元格赋值
                    targetCell.setCellValue(sourceCellVal);
                    //为单元格设置样式
//                    CellStyle cellStyle = sourceCell.getCellStyle();
//                    cellStyle.cloneStyleFrom(cellStyle);
//                    cellStyle.setAlignment();
//                    targetCell.setCellStyle(cellStyle);
                    targetCell.setCellStyle(sourceCell.getCellStyle());

                }
            }
        }
    }


}
