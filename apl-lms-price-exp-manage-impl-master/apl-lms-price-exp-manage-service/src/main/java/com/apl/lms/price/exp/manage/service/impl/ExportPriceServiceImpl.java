package com.apl.lms.price.exp.manage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.apl.lms.price.exp.manage.service.ExportPriceService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataObjVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hjr start
 * @Classname ExportPriceExlServiceInpl
 * @Date 2020/10/15 15:22
 */
@Service
public class ExportPriceServiceImpl implements ExportPriceService {

    enum ExportPriceEnum{

        EXPORT_SUCCESS("EXPORT_SUCCESS", "导出成功"),
        EXPORT_FAILED("EXPORT_FAILED", "导出失败"),
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有对应数据");

       private String code;
       private String msg;

        ExportPriceEnum(String code, String msg){
            this.code = code;
            this.msg = msg;
        };
    }

    @Autowired
    PriceExpService priceExpService;

    @Override
    public void exportPrice(HttpServletResponse response, Long id) throws IOException {

        ExcelWriter excelWriter = null;

        try {

            String templateFileName = "G:/temp/template.xlsx";

            // 注意 使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String outFileName = URLEncoder.encode("快递价格表.xlsx", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + outFileName);

            PriceExpDataObjVo priceExpDataInfo = priceExpService.getPriceExpDataInfoByPriceId(id);
            List<List<Object>> priceDataList = priceExpDataInfo.getPriceData();
            if(null == priceDataList || priceDataList.size() < 1) {
                response.getWriter().write(ExportPriceEnum.NO_CORRESPONDING_DATA.msg);
                return;
            }
            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            //填写报价格表名和备注
            Map<String, Object> map = new HashMap<>();
            map.put("priceName", "香港UPS红单B价");
            map.put("remark", "1、 “电池标签”每一件货物都需要规范粘贴\t\n");
            excelWriter.fill(map, writeSheet);

            //构造表格数据
            List<Map<String, Object>> writeDataList = buildFieldAndDataMap(excelWriter, priceDataList);
            if(null!=writeDataList) {
                //填写表格数据
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                excelWriter.fill(writeDataList, fillConfig, writeSheet);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write(ExportPriceEnum.EXPORT_FAILED.msg);
        } finally {
            if(null!=excelWriter) {
                excelWriter.finish();
            }
        }


    }

    //构造快递价格表数据
    List<Map<String, Object>>  buildFieldAndDataMap(ExcelWriter excelWriter, List<List<Object>> priceDataList){

        //模板Sheet
        Sheet templateSheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();

        //查找priceList单元格
        Cell priceListFistCell = findPriceListFirstCell(templateSheet);
        if(null == priceListFistCell) {
            //没有找到priceList单元格
            return null;
        }

        Cell cell;
        Row fieldRow = priceListFistCell.getRow();
        int priceColIndex = priceListFistCell.getColumnIndex();

        //构建字段和List<Map>
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

                cell = fieldRow.createCell(colIndex);
                cell.setCellValue("{."+colName+"}");
            }

            priceDataListNew.add(rowMap);
        }

        return priceDataListNew;
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
