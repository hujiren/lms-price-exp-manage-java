package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.log.StaticLog;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.apl.lib.exception.AplException;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.StringUtil;
import com.apl.lms.price.exp.manage.service.*;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.apl.lms.price.exp.pojo.po.PriceZoneNamePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataObjVo;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.*;
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
        NO_CORRESPONDING_PRICE("NO_CORRESPONDING_PRICE", "没有对应价格!"),
        NO_VALID_FILE_WAS_FOUND("NO_VALID_FILE_WAS_FOUND", "没有找到有效文件"),
        TEMPLATE_DOES_NOT_EXIST("Template does not exist", "模板不存在"),
        NO_PRICE_LIST_TEMPLATE_FOUND("NO_PRICE_LIST_TEMPLATE_FOUND", "没有找到价格表模板");

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

    @Autowired
    PriceZoneDataService priceZoneDataService;

    @Value("${lms.exp-price.export.template-file-name:}")
    String templateFileName;

    @Value("${lms.exp-price.export.out-file-name:export-exp-price.xlsx}")
    String outFileName;


    /**
     * 导出Excel表
     * @param response
     * @param ids
     * @throws Exception
     */
    @Override
    public void exportExpPrice(HttpServletResponse response, List<Long> ids, Long customerGroupId) throws Exception {

        if (null == templateFileName || templateFileName.length() < 2) {
            throw new AplException(ExportPriceEnum.NO_VALID_FILE_WAS_FOUND.code, ExportPriceEnum.NO_VALID_FILE_WAS_FOUND.msg, null);
        }

        //注意 使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setCharacterEncoding("utf-8");
        ExcelWriter excelWriter = null;
        String newTempFileName = null;
        boolean IS_NO_CORRESPONDING_PRICE = false;

        try {

            //获取价格主表信息
            List<ExpPriceInfoBo> expPriceInfoList = priceExpService.getPriceInfoByIds(ids);
            if (null == expPriceInfoList || expPriceInfoList.size() < 1) {
                IS_NO_CORRESPONDING_PRICE = true;
                throw new AplException(ExportPriceEnum.NO_CORRESPONDING_PRICE.code, ExportPriceEnum.NO_CORRESPONDING_PRICE.msg, null);
            }

            //获取价格表数据
            Map<Long, PriceExpDataObjVo> priceDataMap = new HashMap<>();
            if(null != customerGroupId && customerGroupId > 0){
                for (Long id : ids) {

                    ResultUtil<PriceExpDataObjVo> salePriceData = priceExpService.getSalePriceExpData(id , customerGroupId);
                    PriceExpDataObjVo priceExpDataInfo = salePriceData.getData();
                    priceDataMap.put(id, priceExpDataInfo);
                }
            }else{
                for (Long id : ids) {
                    PriceExpDataObjVo priceExpDataInfo = priceExpService.getCostPriceExpData(id, Collections.emptyList(), false);
                    priceDataMap.put(id, priceExpDataInfo);
                }
            }

            //获取分区ids
            String zoneName;
            List<Long> zoneIds = new ArrayList<>();
            for (ExpPriceInfoBo expPriceInfoBo : expPriceInfoList) {
                expPriceInfoBo.setPriceName(expPriceInfoBo.getPriceName().replace("-", "").replace(" ", ""));
                if(null !=  expPriceInfoBo.getZoneName() && expPriceInfoBo.getZoneId() > 0){
                    zoneName = expPriceInfoBo.getZoneName().replace("-", "").replace(" ", "");
                    if (!zoneName.contains("分区"))
                        zoneName += "分区";
                    expPriceInfoBo.setZoneName(zoneName);

                    zoneIds.add(expPriceInfoBo.getZoneId());
                }
            }

            //获取分区组装数据
            Map<Long, List<PriceZoneDataListVo>> longListMap = priceZoneDataService.assemblingZoneData(zoneIds);

            //获取分区名称
            Map<Long, PriceZoneNamePo> priceZoneNameMap = priceZoneNameService.getPriceZoneNameBatch(zoneIds);

            //获取备注
            Map<Long, PriceExpRemarkPo> priceExpRemarkMap = priceExpRemarkService.getPriceExpRemarkBatch(ids);

            //创建新的模板文件，并复制模板Sheet
            TemplateInfo templateInfo = copyTemplateFile(expPriceInfoList);

            newTempFileName = templateInfo.templateFileName;

            XSSFWorkbook wb = templateInfo.wb;
            //创建超链接对象
            XSSFCreationHelper creationHelper = templateInfo.creationHelper;
            //获取单元格样式对象
            XSSFCellStyle xssfCellStyle = templateInfo.xssfCellStyle;
            //获取字体样式对象
            XSSFFont xssfFont = templateInfo.xssfFont;

            // 填写多个工作表
            int sheetNo = templateInfo.templateNo;

            int directorySheetNo = templateInfo.directoryTempNo;

            String directoryTempName = templateInfo.directoryTempName;

            Map<Long, zoneDataInfo> zoneSheetMaps = templateInfo.zoneSheetNoMap;


            //按新的模板文件创建excelWriter对象
            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateInfo.templateFileName).build();

            WriteSheet directorySheet = EasyExcel.writerSheet(directorySheetNo).build();

            for (ExpPriceInfoBo expPriceInfo : expPriceInfoList) {

                List<List<Object>> priceDataList = priceDataMap.get(expPriceInfo.getId()).getPriceData();
                if (null == priceDataList || priceDataList.size() < 1) {
                    sheetNo++;
                    continue;
                }

                WriteSheet priceSheet = EasyExcel.writerSheet(sheetNo).build();

                //获取备注
                PriceExpRemarkPo priceExpRemarkPo = priceExpRemarkMap.get(expPriceInfo.getId());

                //获取分区
                PriceZoneNamePo priceZoneNamePo = priceZoneNameMap.get(expPriceInfo.getZoneId());

                //填写一个工作表
                fillPriceShell(excelWriter, priceSheet, expPriceInfo, priceExpRemarkPo, priceZoneNamePo, priceDataList, creationHelper);

                sheetNo++;
            }

            if (!directoryTempName.equals("") && directoryTempName.contains("目录")) {
                //填写目录
                fillDirectoryShell(creationHelper, excelWriter, directorySheet, expPriceInfoList);
            }

            //填写分区表
            if (!zoneSheetMaps.isEmpty() && !longListMap.isEmpty()) {
                fillZone(excelWriter, zoneSheetMaps, longListMap, expPriceInfoList, wb);
            }

            //web导出
            response.setContentType("application/vnd.ms-excel");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            outFileName = URLEncoder.encode(outFileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + outFileName);

        } catch (Exception exception) {

            StaticLog.error(exception);
            if (IS_NO_CORRESPONDING_PRICE)
                throw new AplException(ExportPriceEnum.NO_CORRESPONDING_PRICE.code, ExportPriceEnum.NO_CORRESPONDING_PRICE.msg, null);
            else
                throw exception;
        } finally {
            try {
                if (null != newTempFileName) {
                    File delFile = new File(newTempFileName);
                    delFile.delete();
                }
            } catch (Exception e) {
                StaticLog.error(e);
            }

            //关闭excel
            if (null != excelWriter) {
                excelWriter.finish();
            }
        }

    }

    /**
     * 填写目录
     *
     * @param creationHelper
     * @param excelWriter
     * @param directorySheet
     * @param expPriceInfoList
     */
    private void fillDirectoryShell(CreationHelper creationHelper,
                                    ExcelWriter excelWriter,
                                    WriteSheet directorySheet,
                                    List<ExpPriceInfoBo> expPriceInfoList) {

        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(expPriceInfoList, fillConfig, directorySheet);
        Sheet sheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();
        Cell startCell = findCell(sheet, "渠道类型");
        if (null == startCell)
            return;
        int startColIndex = startCell.getColumnIndex();
        int startRowIndex = startCell.getRowIndex() + 1;
        int endRowIndex = startRowIndex + expPriceInfoList.size();
        for (int rowIndex = startRowIndex; rowIndex < endRowIndex; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (null == row)
                continue;

            Cell priceNameCell = row.getCell(startColIndex + 1);
            if (null != priceNameCell) {
                Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
                CellAddress address = priceNameCell.getAddress();
                String strAddress = address.formatAsString();
                hyperlink.setAddress("#" + priceNameCell.getStringCellValue() + "!" + strAddress);
                priceNameCell.setHyperlink(hyperlink);
            }

            Cell zoneNameCell = row.getCell(startColIndex + 2);
            if (null != zoneNameCell) {
                Hyperlink hyperlink2 = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
                CellAddress address = zoneNameCell.getAddress();
                String strAddress = address.formatAsString();
                hyperlink2.setAddress("#" + zoneNameCell.getStringCellValue() + "!" + strAddress);
                zoneNameCell.setHyperlink(hyperlink2);
            }
        }
    }

    //创建新的模板文件，并复制模板Sheet
    TemplateInfo copyTemplateFile(List<ExpPriceInfoBo> expPriceInfoList) throws IOException {
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String templateFileNameByTenant = templateFileName.replace("-tenant", "-" + securityUser.getInnerOrgCode());
        File templateFile = new File(templateFileNameByTenant);
        if (!templateFile.exists()) {
            templateFileNameByTenant = templateFileName.replace("-tenant", "-common");
            templateFile = new File(templateFileNameByTenant);
        }

        if (!templateFile.exists()) {
            throw new AplException(ExportPriceEnum.TEMPLATE_DOES_NOT_EXIST.code, ExportPriceEnum.TEMPLATE_DOES_NOT_EXIST.msg, null);
        }
        //返回上一级目录, 即不带文件名的全路径, 构建临时文件全路径
        String newTempFileName = templateFile.getParent() + "/export-exp-price-temp-" + UUID.randomUUID() + ".xlsx";

        FileInputStream fs = new FileInputStream(templateFileNameByTenant);
        XSSFWorkbook wb = new XSSFWorkbook(fs);

        //获取超链接创建对象
        XSSFCreationHelper creationHelper = wb.getCreationHelper();
        //获取操作样式对象
        XSSFCellStyle xssfCellStyle = wb.createCellStyle();
        //获取操作字体对象
        XSSFFont xssfFont = wb.createFont();

        String sheetName;
        int priceTemplateNo = 0;
        int zoneTemplateNo = 0;
        int directoryTempNo = 0;
        String directoryTempName = "";
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {//获取每个Sheet表
            sheetName = wb.getSheetAt(i).getSheetName();
            if (sheetName.contains("目录")) {
                directoryTempNo = i;
                directoryTempName = sheetName;
            } else if (sheetName.equals("价格表模板")) {
                priceTemplateNo = i;
            } else if (sheetName.equals("分区表模板")) {
                zoneTemplateNo = i;
            }
        }
        if (priceTemplateNo < 1) {
            throw new AplException(ExportPriceEnum.NO_PRICE_LIST_TEMPLATE_FOUND.code,
                    ExportPriceEnum.NO_PRICE_LIST_TEMPLATE_FOUND.msg);
        }
        //
        int sum = 0;
        Map<String, ExpPriceInfoBo> deWeightMap = new HashMap<>();
        for (ExpPriceInfoBo expPriceInfoBo : expPriceInfoList) {
            String priceName = expPriceInfoBo.getPriceName();
            if(deWeightMap.containsKey(priceName)){
                priceName = priceName + "(" + sum + ")";
                expPriceInfoBo.setPriceName(priceName);
            }
            deWeightMap.put(priceName, expPriceInfoBo);
            sum++;
        }
        //
        int sheetNo = priceTemplateNo + 1;
        wb.setSheetName(priceTemplateNo, expPriceInfoList.get(0).getPriceName());
        for (int i = 1; i < expPriceInfoList.size(); i++) {
            sheetName = expPriceInfoList.get(i).getPriceName();
            wb.cloneSheet(priceTemplateNo, sheetName);
            sheetNo++;
        }


        Map<Long, zoneDataInfo> zoneSheetMaps = new HashMap<>();
        HashMap<Long, ExpPriceInfoBo> transferMap = new HashMap<>();
        List<String> repetitionNameList = new ArrayList<>();
        for (ExpPriceInfoBo expPriceInfoBo : expPriceInfoList) {
            if(expPriceInfoBo.getZoneId() < 1)
                continue;
            if (!transferMap.containsKey(expPriceInfoBo.getZoneId()))
                transferMap.put(expPriceInfoBo.getZoneId(), expPriceInfoBo);
            else
                repetitionNameList.add(expPriceInfoBo.getPriceName());
        }

        if(transferMap.size() < 1 && zoneTemplateNo > 0){
            wb.removeSheetAt(zoneTemplateNo);
        }

        if (zoneTemplateNo > 0 && transferMap.size() > 0) {
            for (Map.Entry<Long, ExpPriceInfoBo> entry : transferMap.entrySet()) {
                ExpPriceInfoBo expPriceInfoBo = entry.getValue();
                zoneDataInfo zoneDataInfo = new zoneDataInfo();
                if (!StringUtil.isEmpty(expPriceInfoBo.getZoneName())) {
                    wb.cloneSheet(zoneTemplateNo, expPriceInfoBo.getZoneName());
                    zoneDataInfo.channelCategory = expPriceInfoBo.getChannelCategory();
                    zoneDataInfo.zoneName = expPriceInfoBo.getZoneName();
                    zoneDataInfo.sheetNo = sheetNo;
                    zoneSheetMaps.put(expPriceInfoBo.getZoneId(), zoneDataInfo);
                    sheetNo++;
                }
            }
            wb.removeSheetAt(zoneTemplateNo);
        }

        //输出临时模板文件
        FileOutputStream fileOut = new FileOutputStream(newTempFileName);
        wb.write(fileOut);
        fileOut.close();

        TemplateInfo templateInfo = new TemplateInfo();
        templateInfo.templateFileName = newTempFileName;
        templateInfo.templateNo = priceTemplateNo;
        templateInfo.directoryTempNo = directoryTempNo;
        templateInfo.directoryTempName = directoryTempName;
        templateInfo.creationHelper = creationHelper;
        templateInfo.xssfCellStyle = xssfCellStyle;
        templateInfo.xssfFont = xssfFont;
        templateInfo.wb = wb;
        templateInfo.zoneSheetNoMap = zoneSheetMaps;
        return templateInfo;
    }


    //填写快递价格表Sheet
    void fillPriceShell(ExcelWriter excelWriter,
                        WriteSheet priceSheet,
                        ExpPriceInfoBo expPriceInfo,
                        PriceExpRemarkPo priceExpRemarkPo,
                        PriceZoneNamePo priceZoneNamePo,
                        List<List<Object>> priceDataList,
                        XSSFCreationHelper creationHelper) {

        //填充价格表模板内容
        fillPriceInfo(excelWriter, priceSheet, expPriceInfo, priceZoneNamePo, creationHelper);

        //填写价格表模板内容
        fillPriceData(excelWriter, priceSheet, priceDataList);

        //填写价格表模板备注
        List<String> saleRemarkList = new ArrayList<>();
        String[] remarkArray = priceExpRemarkPo.getSaleRemark().split("\\n");
        for (String s : remarkArray) {
            saleRemarkList.add(s);
        }
        fillPriceRemark(excelWriter, priceSheet, saleRemarkList);

    }


    //填写报价格信息内容
    private void fillPriceInfo(ExcelWriter excelWriter,
                               WriteSheet writeSheet,
                               ExpPriceInfoBo expPriceInfo,
                               PriceZoneNamePo priceZoneNamePo,
                               XSSFCreationHelper creationHelper
    ) {

        Map<String, Object> map = new HashMap<>();
        if (null != expPriceInfo) {
            map.put("priceName", expPriceInfo.getPriceName());
            map.put("endData", expPriceInfo.getEndDate());
            map.put("channelCategory", expPriceInfo.getChannelCategory());
            map.put("currency", expPriceInfo.getCurrency());
        }
        if (null != priceZoneNamePo) {
            map.put("zoneName", priceZoneNamePo.getZoneName());
        }

        excelWriter.fill(map, writeSheet);

        Sheet sheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();
        Cell zoneNameCell = findCell(sheet, "分区表");
        if (null == expPriceInfo.getZoneId() || expPriceInfo.getZoneId() < 1)
            zoneNameCell.setCellValue("");
        else {
            Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
            hyperlink.setAddress("#" + expPriceInfo.getZoneName() + "!A1");
            zoneNameCell.setHyperlink(hyperlink);
        }
    }

    //填写报价格信息内容
    private void fillPriceData(ExcelWriter excelWriter,
                               WriteSheet writeSheet,
                               List<List<Object>> priceDataList) {

        Sheet sheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();
        //查找priceList单元格
        Cell priceListFistCell = findCell(sheet, "priceList");


        Row fieldRow = priceListFistCell.getRow();
        int priceColIndex = priceListFistCell.getColumnIndex();

        // 构建表格数据，行为Map类型
        // 和填写模板sheet字段( {p1}, {p2}, {p3}...)
        int colLen;
        String colName;
        Map rowMap;
        List<Map<String, Object>> priceDataListNew = new ArrayList<>();
        for (List<Object> objectList : priceDataList) {
            rowMap = new HashMap<>();
            colLen = objectList.size();
            for (Integer colIndex = priceColIndex; colIndex < colLen; colIndex++) {

                colName = "p" + colIndex.toString();
                rowMap.put(colName, objectList.get(colIndex));

                if (null != fieldRow) {
                    Cell cell = fieldRow.createCell(colIndex);
                    cell.setCellStyle(priceListFistCell.getCellStyle());
                    cell.setCellValue("{p." + colName + "}");
                }
            }
            priceDataListNew.add(rowMap);
            fieldRow = null;
        }

        //填写表格数据
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(new FillWrapper("p", priceDataListNew), fillConfig, writeSheet);

    }


    //填写备注信息内容
    private void fillPriceRemark(ExcelWriter excelWriter, WriteSheet writeSheet, List<String> saleRemarkList) {

        // 构建表格数据，行为Map类型
        Map<String, String> rowMap;
        List<Map<String, String>> remarkList = new ArrayList<>();
        for (int index = 0; index < saleRemarkList.size(); index++) {
            rowMap = new HashMap<>();
            rowMap.put("remark", saleRemarkList.get(index));
            remarkList.add(rowMap);
        }

        //填写表格数据
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(new FillWrapper("r", remarkList), fillConfig, writeSheet);
    }


    /**
     * 填写分区数据
     *
     * @param excelWriter
     * @param zoneIndexMap
     * @param longListMap
     */
    public void fillZone(ExcelWriter excelWriter,
                         Map<Long, zoneDataInfo> zoneIndexMap,
                         Map<Long, List<PriceZoneDataListVo>> longListMap,
                         List<ExpPriceInfoBo> expPriceInfoBoList,
                         XSSFWorkbook wb
                         ){

        Map<String, Object> map;
        String priceName = null;
        for (Map.Entry<Long, zoneDataInfo> zoneDataEntry : zoneIndexMap.entrySet()) {

            for (ExpPriceInfoBo expPriceInfoBo : expPriceInfoBoList) {
                if (zoneDataEntry.getValue().zoneName.equals(expPriceInfoBo.getZoneName())) {
                    priceName = expPriceInfoBo.getPriceName();
                }
            }

            //先填充zoneName和channelCategory
            WriteSheet zoneSheet = EasyExcel.writerSheet(zoneDataEntry.getValue().sheetNo).build();
            zoneSheet.setSheetName(zoneDataEntry.getValue().zoneName);

            map = new HashMap<>();
            if (null != zoneDataEntry) {
                map.put("zoneName", zoneDataEntry.getValue().zoneName);
                map.put("channelCategory", zoneDataEntry.getValue().channelCategory);
            }
            excelWriter.fill(map, zoneSheet);

            Map rowMap;
            Integer rowNum = 0;
            //构建填充sheet的List
            List<Map<String, PriceZoneDataListVo>> zoneDataList = new ArrayList<>();
            //遍历组装好的zoneDataMap
            for (Map.Entry<Long, List<PriceZoneDataListVo>> longListEntry : longListMap.entrySet()) {
                //找到与价格表分区id对应的Map
                if (zoneDataEntry.getKey().equals(longListEntry.getKey())) {
                    //获取map中的分区数据
                    List<PriceZoneDataListVo> zoneList = longListEntry.getValue();
                    //遍历分区数据放入作为填充对象的map中
                    for (PriceZoneDataListVo priceZoneDataListVo : zoneList) {
                        rowMap = new HashMap<>();
                        rowMap.put("zoneNum", priceZoneDataListVo.getZoneNum());
                        rowMap.put("countryNameCn", priceZoneDataListVo.getCountryNameCn());
                        rowMap.put("countryNameEn", priceZoneDataListVo.getCountryNameEn());
                        zoneDataList.add(rowMap);
                        rowNum++;
                    }
                } else {
                    continue;
                }
            }

            //填充分区数据
            excelWriter.fill(zoneDataList, zoneSheet);
            Sheet sheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();

            Cell cell = findCell(sheet, "价格表");
            if (cell != null) {
                //添加超链接
                XSSFCreationHelper creationHelper = wb.getCreationHelper();
                XSSFHyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
                hyperlink.setAddress("#" + priceName + "!A1");
                cell.setHyperlink(hyperlink);
            }

//            int startRowIndex = cell.getRowIndex() + 1;
//            int endRowIndex = startRowIndex + rowNum;
//            Row row = null;
//            int lastCellNum = 3;
//            for(int i = 3; i < 4; i++){
//                row = sheet.getRow(i);
//                if(null == row)
//                    continue;
//                for(int j = 1; j < 2; j++){
//                    Cell cell1 = row.getCell(j);
//                    cell1.setCellStyle();
//                }
//            }
        }
    }


    //查找单元格
    private Cell findCell(Sheet sheet, String context) {

        Cell cell;
        String strVal;
        Row fieldRow;
        for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
            fieldRow = sheet.getRow(rowIndex);
            if (null != fieldRow) {
                for (int colIndex = 0; colIndex < 20; colIndex++) {
                    cell = fieldRow.getCell(colIndex);
                    if (null != cell) {
                        strVal = cell.getStringCellValue();
                        if (null != strVal && strVal.trim().equals(context)) {
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
    private void copySheet(XSSFSheet sourceSheet, XSSFSheet targetSheet) {
        //getLastRowNum 获取有数据的最后一行的行号  getLastCellNum获取有数据的最后一列的列号
        int rowLastRowNum = sourceSheet.getLastRowNum();

        //遍历行
        for (int rowIndex = 0; rowIndex <= rowLastRowNum; rowIndex++) {

            //获取第<rowIndex>行
            XSSFRow sourceRow = sourceSheet.getRow(rowIndex);
            //获取当前行的最后一列列号
            int lastCellNum = sourceRow.getLastCellNum();
            //创建第<rowIndex>行
            XSSFRow targetRow = targetSheet.createRow(rowIndex);

            //遍历列
            for (int colIndex = 0; colIndex < lastCellNum; colIndex++) {
                //在当前行中取第<colIndex>个单元格
                XSSFCell sourceCell = sourceRow.getCell(colIndex);
                if (null != sourceCell) {
                    //获取单元格中的值
                    String sourceCellVal = sourceCell.getStringCellValue();
                    //为目标行创建单元格
                    Cell targetCell = targetRow.createCell(colIndex);
                    //为单元格赋值
                    targetCell.setCellValue(sourceCellVal);
                    //为单元格设置样式
                    targetCell.setCellStyle(sourceCell.getCellStyle());

                }
            }
        }
    }

    class zoneDataInfo {
        public Integer sheetNo;

        public String zoneName;

        public String channelCategory;
    }

    class TemplateInfo {
        public String templateFileName;

        public int templateNo;

        public int directoryTempNo;

        public String directoryTempName;

        public XSSFCreationHelper creationHelper;

        public XSSFCellStyle xssfCellStyle;

        public XSSFFont xssfFont;

        public XSSFWorkbook wb;

        public Map<Long, zoneDataInfo> zoneSheetNoMap;
    }

    public void setFrame(ExcelWriter excelWriter, XSSFCellStyle xssfCellStyle) {

        Sheet sheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();
        xssfCellStyle.setBorderTop(BorderStyle.MEDIUM);
        xssfCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        xssfCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        xssfCellStyle.setBorderRight(BorderStyle.MEDIUM);

        int lastRowNum = sheet.getLastRowNum();
        for (int i = 2; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (null == row)
                continue;
            int firstCellNum = row.getFirstCellNum();
            if (firstCellNum < 0)
                break;
            row.getCell(firstCellNum).getCellStyle().cloneStyleFrom(xssfCellStyle);

        }
    }
}