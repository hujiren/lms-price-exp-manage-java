package com.apl.lms.price.exp.manage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.apl.lms.price.exp.manage.service.ExportPriceService;
import com.apl.lms.price.exp.manage.service.PriceExpRemarkService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.manage.service.PriceZoneNameService;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.bo.PriceExportExcelBo;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataObjVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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

    enum ExportPriceEnum{

        EXPORT_SUCCESS("EXPORT_SUCCESS", "EXPORT SUCCESS! 导出成功!"),
        EXPORT_FAILED("EXPORT_FAILED", "EXPORT_FAILED! 导出失败!"),
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "NO_CORRESPONDING_DATA! 没有对应数据!");

       private String code;
       private String msg;

        ExportPriceEnum(String code, String msg){
            this.code = code;
            this.msg = msg;
        };
    }

    @Autowired
    PriceExpService priceExpService;

    @Autowired
    PriceZoneNameService priceZoneNameService;

    @Autowired
    PriceExpRemarkService priceExpRemarkService;

    String templateFileName = "G:/temp/template2.xlsx";
    String outFileName = "export-exp-price.xlsx";

    @Override
    public void exportExpPrice(HttpServletResponse response, List<Long> ids) throws IOException {

        // 注意 使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setCharacterEncoding("utf-8");
        ExcelWriter excelWriter = null;

        try {

            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();
            //excelWriter = EasyExcel.write(outFileName).withTemplate(templateFileName).build();

            Map<Long, PriceExportExcelBo> priceExpDataMap = new HashMap<>();

            for (Long id : ids) {
                PriceExportExcelBo priceExportExcelBo = new PriceExportExcelBo();
                PriceExpDataObjVo priceExpDataInfo =  priceExpService.getPriceExpDataInfoByPriceId(id);
                ExpPriceInfoBo expPriceInfoBo = priceExpService.getPriceInfo(id);
                PriceExpRemarkPo priceExpRemark = priceExpRemarkService.getPriceExpRemark(id);
                if(null != priceExpDataInfo && null != expPriceInfoBo){
                    priceExportExcelBo.setPriceDataId(priceExpDataInfo.getPriceDataId());
                    priceExportExcelBo.setPriceData(priceExpDataInfo.getPriceData());
                    priceExportExcelBo.setChannelCategory(expPriceInfoBo.getChannelCategory());
                    priceExportExcelBo.setEndDate(expPriceInfoBo.getEndDate());
                    priceExportExcelBo.setZoneId(expPriceInfoBo.getZoneId());
                    priceExportExcelBo.setPriceName(expPriceInfoBo.getPriceName());
                }else{
                    continue;
                }
                if(null != priceExpRemark){}
                priceExportExcelBo.setRemark(priceExpRemark.getSaleRemark());
                String priceZoneName = priceZoneNameService.getPriceZoneName(expPriceInfoBo.getZoneId());
                priceExportExcelBo.setZoneName(priceZoneName);
                priceExpDataMap.put(id, priceExportExcelBo);
            }

            List priceList = null;


            //模板Sheet
            Sheet templateSheet = null;
            Cell priceListFistCell = null;
//            List<String> sheetNames = new ArrayList<>();
            Iterator<Sheet> iterator = excelWriter.writeContext().getWorkbook().sheetIterator();

//            int sheetStartIndex=0;
//            while(iterator.hasNext()) {
//                sheetNames.add(iterator.next().getSheetName());
//                sheetStartIndex++;
//            }

            int priceIndex = 0;
            for (Map.Entry<Long, PriceExportExcelBo> Entry : priceExpDataMap.entrySet()) {
                PriceExportExcelBo priceExportExcelBo = Entry.getValue();
                List<List<Object>> priceDataList = priceExportExcelBo.getPriceData();
                if(null == priceDataList || priceDataList.size() < 1) {
                    continue;
                }

                //创建新的工作表
                WriteSheet writeSheet = EasyExcel.writerSheet().sheetNo(priceIndex).sheetName(priceExportExcelBo.getPriceName()).build();

//                价格表名称,渠道类型 priceDataId, 分区表id 分区表名称  截止日期
//                String priceName = priceExportExcelBo.getPriceName();
//                sheetNames.add(priceName);

                //填充报价格信息内容
                filePriceInfo(excelWriter, writeSheet, priceExportExcelBo);

                if(priceIndex==0) {
                    templateSheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();
                    //查找priceList单元格
                    priceListFistCell = findPriceListFirstCell(templateSheet);
                }

                //填写新的工作表内容
                fillNewShell(excelWriter, writeSheet, priceListFistCell, priceDataList, priceIndex);
                priceIndex++;
            }

//            for(int sheetIndex=sheetStartIndex; sheetIndex<sheetNames.size(); sheetIndex++){
//                excelWriter.writeContext().getWorkbook().setSheetName(sheetIndex-sheetStartIndex, sheetNames.get(sheetIndex));
//            }

            response.setContentType("application/vnd.ms-excel");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            outFileName = URLEncoder.encode(outFileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + outFileName);

            //关闭excel
            if(null!=excelWriter) {
                excelWriter.finish();
            }

        } catch (Exception e) {

            //关闭excel
            if(null!=excelWriter) {
                excelWriter.finish();
            }

            e.printStackTrace();
           response.getWriter().write(URLEncoder.encode(ExportPriceEnum.EXPORT_FAILED.msg, "UTF-8"));
        }
    }

    //填充报价格信息内容
    void filePriceInfo(ExcelWriter excelWriter, WriteSheet writeSheet,  PriceExportExcelBo priceExportExcelBo){
        Map<String, Object> map = new HashMap<>();
        map.put("priceName", priceExportExcelBo.getPriceName());
        map.put("remark", priceExportExcelBo.getRemark());
        if(null != priceExportExcelBo.getChannelCategory())
            map.put("channelCategory", "渠道类型: " + priceExportExcelBo.getChannelCategory());
        else
            map.put("channelCategory", "渠道类型: 无");
        if(null != priceExportExcelBo.getZoneName())
            map.put("zoneName", "分区名称: " + priceExportExcelBo.getZoneName());
        else
            map.put("zoneName", "分区名称: 无");
        if(null != priceExportExcelBo.getEndDate())
            map.put("endData", "截止日期: " + priceExportExcelBo.getEndDate());
        else
            map.put("endData", "截止日期: 无");
        excelWriter.fill(map, writeSheet);

    }

    //填写报价格信息内容
    void fillNewShell(ExcelWriter excelWriter, WriteSheet writeSheet,Cell priceListFistCell, List<List<Object>> priceDataList, int priceIndex){

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
            colLen  = objectList.size();
            for(Integer colIndex = priceColIndex; colIndex<colLen; colIndex++){

                colName = "c"+colIndex.toString();
                rowMap.put(colName, objectList.get(colIndex));

                if(priceIndex==0) {
                    Cell cell = fieldRow.createCell(colIndex);
                    cell.setCellValue("{." + colName + "}");
                }
            }

            priceDataListNew.add(rowMap);
        }

        //填写表格数据
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(priceDataListNew, fillConfig, writeSheet);
    }

    //查找priceList单元格
    Cell findPriceListFirstCell(Sheet templateSheet){
        Cell cell;
        String strVal;
        Row fieldRow;
        for(int rowIndex=0; rowIndex<5; rowIndex++){
            fieldRow = templateSheet.getRow(rowIndex);
            if(null!=fieldRow) {
                for (int colIndex = 0; colIndex < 4; colIndex++) {
                    cell = fieldRow.getCell(colIndex);
                    if (null != cell) {
                        strVal = cell.getStringCellValue();
                        if (null!=strVal && strVal.trim().equals("priceList")) {
                            //找到priceList单元格
                            return cell;
                        }
                    }
                }
            }
        }

        //没有找到priceList单元格
        return null;
    }


}
