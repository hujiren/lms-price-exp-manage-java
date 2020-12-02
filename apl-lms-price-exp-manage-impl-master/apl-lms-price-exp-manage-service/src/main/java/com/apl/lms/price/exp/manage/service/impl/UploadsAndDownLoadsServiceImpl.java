package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.log.StaticLog;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.apl.lib.constants.CommonStatusCode;
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
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author hjr start
 * @Classname ExportPriceExlServiceInpl
 * @Date 2020/10/15 15:22
 */
@Service
public class UploadsAndDownLoadsServiceImpl implements UploadsAndDownLoadsService {

    enum ExportPriceEnum {

        EXPORT_SUCCESS("EXPORT_SUCCESS", "EXPORT SUCCESS! 导出成功!"),
        EXPORT_FAILED("EXPORT_FAILED", "EXPORT_FAILED! 导出失败!"),
        NO_CORRESPONDING_PRICE("NO_CORRESPONDING_PRICE", "没有对应价格!"),
        NO_VALID_FILE_WAS_FOUND("NO_VALID_FILE_WAS_FOUND", "没有找到有效文件"),
        TEMPLATE_DOES_NOT_EXIST("Template does not exist", "模板不存在"),
        NO_PRICE_LIST_TEMPLATE_FOUND("NO_PRICE_LIST_TEMPLATE_FOUND", "没有找到价格表模板"),
        PLEASE_UPLOAD_EXCEL_FILE("PLEASE_UPLOAD_EXCEL_FILE", "请上传Excel文件"),
        YOU_ALREADY_HAVE_THE_TEMPLATE("YOU_ALREADY_HAVE_THE_TEMPLATE", "您已拥有模板"),
        UPLOAD_SUCCESSFUL("UPLOAD_SUCCESSFUL", "上传成功"),
        THIS_FILE_IS_ALREADY_EXISTS("THIS_FILE_IS_ALREADY_EXISTS", "该文件已存在"),
        THIS_FILE_DOSE_NOT_EXISTS("THIS_FILE_DOSE_NOT_EXISTS", "该文件不存在"),
        THE_FILE_CANNOT_BE_FOUND("THE_FILE_CANNOT_BE_FOUND", "找不到指定文件"),
        PLEASE_PASS_IN_THE_FILE_IN_THE_CORRECT_FORMAT("PLEASE_PASS_IN_THE_FILE_IN_THE_CORRECT_FORMAT","请传入正确格式的Excel文件"),
        PLEASE_UPLOAD_A_PROPERLY_FORMATTED_EXCEL_FILE_OR_USE_OFFICE_2007_OR_HIGHER_TO_GENERATE_EXCEL(
                "PLEASE_UPLOAD_A_PROPERLY_FORMATTED_EXCEL_FILE_OR_USE_OFFICE_2007_OR_HIGHER_TO_GENERATE_EXCEL",
                "请上传正确格式的Excel文件或使用office 2007+ 及更高版本生成Excel"),
        THE_UPLOADED_FILE_CANNOT_BE_EMPTY("THE_UPLOADED_FILE_CANNOT_BE_EMPTY", "不能上传空文件")
        ;

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

    @Autowired
    PriceExpDataService priceExpDataService;

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

            if(null == customerGroupId)
                customerGroupId = 0l;

            //获取价格主表信息
            List<ExpPriceInfoBo> expPriceInfoList = priceExpService.getPriceInfoByIds(ids);
            if (null == expPriceInfoList || expPriceInfoList.size() < 1) {
                IS_NO_CORRESPONDING_PRICE = true;
                throw new AplException(ExportPriceEnum.NO_CORRESPONDING_PRICE.code, ExportPriceEnum.NO_CORRESPONDING_PRICE.msg, null);
            }

            //获取分区ids
            String zoneName;
            List<Long> zoneIds = new ArrayList<>();

            //分区表id和价格表数据
            Map<Long, PriceExpDataObjVo> priceDataMap = new HashMap<>();
            for (ExpPriceInfoBo expPriceInfoBo : expPriceInfoList) {

                if(null !=  expPriceInfoBo.getZoneName() && expPriceInfoBo.getZoneId() > 0){
                    zoneName = expPriceInfoBo.getZoneName().replace("-", "").replace(" ", "");
                    if (!zoneName.contains("分区"))
                        zoneName += "分区表";
                    expPriceInfoBo.setZoneName(zoneName);

                    zoneIds.add(expPriceInfoBo.getZoneId());
                }

                expPriceInfoBo.setPriceName(expPriceInfoBo.getPriceName().replace("-","").replace(" ",""));

                PriceExpDataObjVo priceExpData = priceExpDataService.getPriceExpData(expPriceInfoBo,
                        expPriceInfoBo.getId(), customerGroupId > 0, customerGroupId, true);

                priceDataMap.put(expPriceInfoBo.getId(), priceExpData);
            }

            //组装分区数据
            Map<Long, List<PriceZoneDataListVo>> zoneDataMap = priceZoneDataService.assemblingZoneData(zoneIds);

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

                expPriceInfo.setPriceName(expPriceInfo.getPriceName().replace("-", "").replace(" ", ""));

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
                fillPriceShell(excelWriter, priceSheet, expPriceInfo, priceExpRemarkPo, priceZoneNamePo, priceDataList, creationHelper, xssfCellStyle);

                sheetNo++;
            }

            if (!directoryTempName.equals("") && directoryTempName.contains("目录")) {
                //填写目录
                fillDirectoryShell(creationHelper, excelWriter, directorySheet, expPriceInfoList);
            }

            //填写分区表
            if (!zoneSheetMaps.isEmpty() && !zoneDataMap.isEmpty()) {
                fillZone(excelWriter, zoneSheetMaps, zoneDataMap, expPriceInfoList, wb, xssfCellStyle, xssfFont);
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
                        XSSFCreationHelper creationHelper,
                        XSSFCellStyle xssfCellStyle) {

        //填充价格表模板内容
        fillPriceInfo(excelWriter, priceSheet, expPriceInfo, priceZoneNamePo, creationHelper);

        //填写价格表模板内容
        fillPriceData(excelWriter, priceSheet, priceDataList, xssfCellStyle);

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
                               List<List<Object>> priceDataList,
                               XSSFCellStyle xssfCellStyle) {

        Sheet sheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();
        //查找priceList单元格
        Cell priceListFistCell = findCell(sheet, "priceList");

        Row fieldRow = priceListFistCell.getRow();
        int fieldRowNum = fieldRow.getRowNum();
        int priceColIndex = priceListFistCell.getColumnIndex();

        xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);//水平
        xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直
//        xssfCellStyle.setBorderLeft(BorderStyle.THIN);
//        xssfCellStyle.setBorderRight(BorderStyle.THIN);
//        xssfCellStyle.setBorderTop(BorderStyle.THIN);
//        xssfCellStyle.setBorderBottom(BorderStyle.THIN);

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

        Row fieldRow2 = sheet.getRow(fieldRowNum);
        int lastCellNum = fieldRow2.getLastCellNum();
        for(int i = fieldRow2.getRowNum(); i <= priceDataList.size(); i++){

            Row row = sheet.getRow(i);
            for(int j = 0; j < lastCellNum; j++){
                Cell cell = row.getCell(j);
//                sheet.autoSizeColumn(j); 自适应列宽
                cell.getCellStyle().cloneStyleFrom(xssfCellStyle);
            }
        }
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
    public void fillZone(
                         ExcelWriter excelWriter,
                         Map<Long, zoneDataInfo> zoneIndexMap,
                         Map<Long, List<PriceZoneDataListVo>> longListMap,
                         List<ExpPriceInfoBo> expPriceInfoBoList,
                         XSSFWorkbook wb,
                         XSSFCellStyle xssfCellStyle,
                         XSSFFont xssfFont
                         ){

        Map<String, Object> map;
        String priceName = null;
        xssfCellStyle.setWrapText(true);
        xssfCellStyle.setBorderLeft(BorderStyle.THIN);
        xssfCellStyle.setBorderRight(BorderStyle.THIN);
        xssfCellStyle.setBorderTop(BorderStyle.THIN);
        xssfCellStyle.setBorderBottom(BorderStyle.THIN);
        xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);//水平
        xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直
        int fontSize = 11;
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
            sheet.setColumnWidth(0, 3 * 1208);
            sheet.setColumnWidth(1, 10 * 1208);
            sheet.setColumnWidth(2, 10 * 1208);
            Cell cell = findCell(sheet, "价格表");

            if (cell != null) {
                //添加超链接
                XSSFCreationHelper creationHelper = wb.getCreationHelper();
                XSSFHyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
                hyperlink.setAddress("#" + priceName + "!A1");
                cell.setHyperlink(hyperlink);
            }

            int startRowIndex = cell.getRowIndex() + 1;
            int endRowIndex = startRowIndex + rowNum;
            Row row = null;
            int lastCellNum = 3;
            for(int i = startRowIndex; i <= endRowIndex; i++){
                row = sheet.getRow(i);
                if(null == row)
                    continue;
                for(int j = 0; j < lastCellNum; j++){
                    Cell cell1 = row.getCell(j);
                    String cellValueStr = cell1.getStringCellValue();
                    if(cellValueStr.equals("分区号") || cellValueStr.equals("中文名") || cellValueStr.equals("英文名")){
                        xssfFont.setFontHeight(16);
                        xssfFont.setBold(true);
                        row.setHeightInPoints(30);
                        xssfCellStyle.setFont(xssfFont);
                        cell1.getCellStyle().cloneStyleFrom(xssfCellStyle);
                        continue;
                    }
                    if(j == 1){
                        cellValueStr = cell1.getStringCellValue();
                        Integer strLength = cellValueStr.length();
                        int rowNumSize = 1;
                        if(strLength > 41){
                            if(strLength % 41 == 0)
                                rowNumSize = strLength / 41;
                            else
                                rowNumSize += strLength / 41;
                        }
                        row.setHeightInPoints((float) (fontSize * rowNumSize * 1.5));//fontSize = 11
                    }
                    xssfFont.setFontHeight(fontSize);
                    xssfFont.setBold(false);
                    xssfCellStyle.setFont(xssfFont);
                    cell1.getCellStyle().cloneStyleFrom(xssfCellStyle);
//                    sheet.autoSizeColumn(j);
                }
            }
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


    /**
     * 上传Excel
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public ResultUtil<Boolean> uploadTemplateFile(MultipartFile file) throws IOException {

        Boolean isExcel = false;
        byte[] targetFileByteArr = file.getBytes();
        if(targetFileByteArr.length < 1)
            return ResultUtil.APPRESULT(ExportPriceEnum.THE_UPLOADED_FILE_CANNOT_BE_EMPTY.code,
                    ExportPriceEnum.THE_UPLOADED_FILE_CANNOT_BE_EMPTY.msg, null);

        FileMagic fileMagic = FileMagic.valueOf(file.getBytes());
        //FileMagic.OLE2代表office 97-2005    FileMagic.OOXML代表office 2007+   导出EXCEL表格用的是XSSFWorkBook, 不兼容FileMagic OLE2
        if(Objects.equals(fileMagic, FileMagic.OOXML)){
            isExcel = true;
        }
        if(!isExcel){
            return ResultUtil.APPRESULT(ExportPriceEnum.PLEASE_UPLOAD_A_PROPERLY_FORMATTED_EXCEL_FILE_OR_USE_OFFICE_2007_OR_HIGHER_TO_GENERATE_EXCEL.code,
                    ExportPriceEnum.PLEASE_UPLOAD_A_PROPERLY_FORMATTED_EXCEL_FILE_OR_USE_OFFICE_2007_OR_HIGHER_TO_GENERATE_EXCEL.msg, null);
        }

        String targetFileName = file.getOriginalFilename();//获取真实的文件名
        String substringName = "";
        int index = targetFileName.lastIndexOf(".");
        if(index > 0)
            substringName = targetFileName.substring(index + 1);

        FileFormat fileFormat = new FileFormat();
        if(!fileFormat.fileFormat.containsKey(substringName.toLowerCase())){
            return ResultUtil.APPRESULT(ExportPriceEnum.PLEASE_PASS_IN_THE_FILE_IN_THE_CORRECT_FORMAT.code,
                    ExportPriceEnum.PLEASE_PASS_IN_THE_FILE_IN_THE_CORRECT_FORMAT.msg, null);
        }

        FileOutputStream fos = null;
        try {
            SecurityUser securityUser = CommonContextHolder.getSecurityUser();
            String newFileName = templateFileName.replace("-tenant", "-" + securityUser.getInnerOrgCode());
            File newFile = new File(newFileName);
            fos = new FileOutputStream(newFile);
            fos.write(targetFileByteArr);

        } catch (IOException e) {
            return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_FAIL, false);

        } finally {
            if(null != fos)
                fos.close();
        }

        return ResultUtil.APPRESULT(ExportPriceEnum.UPLOAD_SUCCESSFUL.code,ExportPriceEnum.UPLOAD_SUCCESSFUL.msg, true);
    }

    /**
     * 判断Excel是否存在
     * @return
     */
    @Override
    public ResultUtil<Boolean> excelIsExists(){

        Boolean isExists = true;
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String newFileName = templateFileName.replace("-tenant", "-" + securityUser.getInnerOrgCode());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(newFileName);
        } catch (FileNotFoundException e) {
            isExists = false;
        }
        if(isExists)
            return ResultUtil.APPRESULT(ExportPriceEnum.THIS_FILE_IS_ALREADY_EXISTS.code, ExportPriceEnum.THIS_FILE_IS_ALREADY_EXISTS.msg, true);

        return ResultUtil.APPRESULT(ExportPriceEnum.THIS_FILE_DOSE_NOT_EXISTS.code, ExportPriceEnum.THIS_FILE_DOSE_NOT_EXISTS.msg, false);
    }

    /**
     * 下载Excel模板
     * @return
     */
    @Override
    public void downloadExcel(HttpServletResponse response) throws IOException {

        //vnd.ms-excel
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        outFileName = URLEncoder.encode(outFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + outFileName);

        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String newFileName = templateFileName.replace("-tenant", "-" + securityUser.getInnerOrgCode());
        OutputStream os = response.getOutputStream();
        InputStream is = null;
        File sourceFile = null;

        try {
            sourceFile = new File(newFileName);
            is = new FileInputStream(newFileName);
        } catch (FileNotFoundException e) {
           throw new AplException(ExportPriceEnum.THE_FILE_CANNOT_BE_FOUND.code, ExportPriceEnum.THE_FILE_CANNOT_BE_FOUND.msg, null);
        }
        byte[] sourceFileByteArr = new byte[(int) sourceFile.length()];
            int i = -1;
            i = is.read(sourceFileByteArr);
            if(i != -1)
                os.write(sourceFileByteArr);
            else
                throw new AplException(ExportPriceEnum.THE_FILE_CANNOT_BE_FOUND.code, ExportPriceEnum.THE_FILE_CANNOT_BE_FOUND.msg, null);

        if(null != os){
            os.flush();
            os.close();
        }
        if(null != is)
            is.close();
    }

    class FileFormat{
        private Map<String, Object> fileFormat = null;

        public FileFormat(){
            if(null == fileFormat)
                fileFormat = new HashMap<>();
            fileFormat.put("xls","d0cf11e0");
            fileFormat.put("xlsx","504b0304");
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
}