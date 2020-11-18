package com.apl.lms.price.exp.manage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.apl.cache.AplCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.join.JoinBase;
import com.apl.lib.join.JoinFieldInfo;
import com.apl.lib.join.JoinUtil;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.common.lib.cache.JoinCountry;
import com.apl.lms.common.lib.feign.LmsCommonFeign;
import com.apl.lms.price.exp.manage.mapper.PriceZoneDataMapper;
import com.apl.lms.price.exp.manage.service.PriceZoneDataService;
import com.apl.lms.price.exp.manage.service.PriceZoneNameService;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.apl.lms.price.exp.pojo.vo.PriceZoneNameVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
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
 * @Classname PriceZoneDataServiceImpl
 * @Date 2020/8/31 15:16
 */
@Service
@Slf4j
public class PriceZoneDataServiceImpl extends ServiceImpl<PriceZoneDataMapper, PriceZoneDataListVo> implements PriceZoneDataService {

    JoinFieldInfo joinCountryFieldInfo = null;

    @Autowired
    AplCacheUtil aplCacheUtil;

    @Autowired
    LmsCommonFeign lmsCommonFeign;

    @Autowired
    PriceZoneNameService priceZoneNameService;

    @Value("${lms.exp-zone.export.template-file-name:}")
    String templateFileName;

    @Value("${lms.exp-zone.export.out-file-name:export-exp-zone.xlsx}")
    String outFileName;

    enum PriceZoneDataServiceCode {
        ID_DOES_NOT_EXITS("ID_DOES_NOT_EXITS", "id不存在"),
        NO_VALID_FILE_WAS_FOUND("NO_VALID_FILE_WAS_FOUND", "没有找到有效文件"),
        TEMPLATE_DOES_NOT_EXIST("Template does not exist", "模板不存在");

        private String code;
        private String msg;

        PriceZoneDataServiceCode(String code, String msg) {

            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 获取列表
     * @param id
     * @return
     */
    @Override
    public List<PriceZoneDataListVo> getList(Long id) throws Exception {
        List<PriceZoneDataListVo> priceZoneDataListVo = baseMapper.getList(id);

        List<JoinBase> joinTabs = new ArrayList<>();
        JoinCountry joinCountry = new JoinCountry(1, lmsCommonFeign, aplCacheUtil);

        if(null!=joinCountryFieldInfo) {
            //已经缓存国家反射字段
            joinCountry.setJoinFieldInfo(joinCountryFieldInfo);
        }
        else{
            joinCountry.addField("countryCode",  String.class, "nameCn", "countryNameCn",String.class);
            joinCountry.addField("countryCode", String.class, "nameEn", "countryNameEn",String.class);

            joinCountryFieldInfo = joinCountry.getJoinFieldInfo();
        }
        joinTabs.add(joinCountry);
        JoinUtil.join(priceZoneDataListVo, joinTabs);

        zoneNumSort(priceZoneDataListVo);

        return priceZoneDataListVo;
    }

    @Override
    public ResultUtil<Boolean> deleteBatch(List<Long> ids) {
        Integer resInteger = baseMapper.deleteByZoneId(ids);
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, resInteger);
    }

    /**
     * 根据分区表id批量删除
     * @param ids
     * @return
     */
    @Override
    public Integer delBatchByZoneId(List<Long> ids) {
        return baseMapper.delBatchByZoneId(ids);
    }

    /**
     * 组装分区数据
     */
    @Override
    public Map<Long, List<PriceZoneDataListVo>> assemblingZoneData(List<Long> zoneIds) throws Exception {

        if(null == zoneIds || zoneIds.size() < 1){
            return Collections.emptyMap();
        }
        //分区Id去重
        HashSet<Long> idSet = new HashSet<>();
        for (Long zoneId : zoneIds) {
            idSet.add(zoneId);
        }
        //根据zoneId获取所有数据
        List<PriceZoneDataListVo> allZoneDataList = baseMapper.getListByZoneIds(idSet);

        //将国家中英文名称通过缓存组装好
        List<JoinBase> joinTabs = new ArrayList<>();
        JoinCountry joinCountry = new JoinCountry(1, lmsCommonFeign, aplCacheUtil);

        if(null!=joinCountryFieldInfo) {
            //已经缓存国家反射字段
            joinCountry.setJoinFieldInfo(joinCountryFieldInfo);
        }
        else{
            joinCountry.addField("countryCode",  String.class, "nameCn", "countryNameCn",String.class);
            joinCountry.addField("countryCode", String.class, "nameEn", "countryNameEn",String.class);

            joinCountryFieldInfo = joinCountry.getJoinFieldInfo();
        }
        joinTabs.add(joinCountry);
        JoinUtil.join(allZoneDataList, joinTabs);

        Map<Long, List<PriceZoneDataListVo>> zoneTabMaps = new HashMap<>();
        List<PriceZoneDataListVo> oneZoneDataList;

        //遍历所有数据
        for (PriceZoneDataListVo priceZoneDataVo : allZoneDataList) {

            //根据分区id将数据进行归类,放入map中
            oneZoneDataList = zoneTabMaps.get(priceZoneDataVo.getZoneId());
            if (null == oneZoneDataList) {
                oneZoneDataList = new ArrayList();
                zoneTabMaps.put(priceZoneDataVo.getZoneId(), oneZoneDataList);
            }
            oneZoneDataList.add(priceZoneDataVo);
        }
        List<PriceZoneDataListVo> sourceZoneDataList;
        List<PriceZoneDataListVo> newZoneDataList;

        StringBuilder sbNameCn = new StringBuilder();
        StringBuilder sbNameEn = new StringBuilder();

        //遍历map,每一个map代表一个分区id对应的所有分区数据
        for (Map.Entry<Long, List<PriceZoneDataListVo>> zoneTabEntry : zoneTabMaps.entrySet()) {

            sourceZoneDataList = zoneTabEntry.getValue();//存储map的value的list集合
            zoneNumSort(sourceZoneDataList);

            newZoneDataList = new ArrayList<>();
            //将分区号相同的对象合并到新的map中,并将国家的中英文名用逗号拼接组装成新的属性
            String zoneNum ="";
            String zoneNum0 = "";
            for (PriceZoneDataListVo vo : sourceZoneDataList) {
                zoneNum = vo.getZoneNum();
                if(!zoneNum0.equals(zoneNum))  {
                    if(sbNameCn.length()>0){
                        PriceZoneDataListVo  priceZoneDataListVo = new  PriceZoneDataListVo();
                        newZoneDataList.add(priceZoneDataListVo);
                        priceZoneDataListVo.setZoneNum(zoneNum0);
                        priceZoneDataListVo.setCountryNameCn(sbNameCn.toString());
                        priceZoneDataListVo.setCountryNameEn(sbNameEn.toString());
                    }
                    sbNameCn.setLength(0);
                    sbNameEn.setLength(0);
                }

                if(sbNameCn.length()>0){
                    sbNameCn.append(", ");
                    sbNameEn.append(", ");
                }
                sbNameCn.append(vo.getCountryNameCn());
                sbNameEn.append(vo.getCountryNameEn());

                zoneNum0 = zoneNum;
            }

            if(sbNameCn.length()>0){
                PriceZoneDataListVo  priceZoneDataListVo = new  PriceZoneDataListVo();
                newZoneDataList.add(priceZoneDataListVo);
                priceZoneDataListVo.setZoneNum(zoneNum);
                priceZoneDataListVo.setCountryNameCn(sbNameCn.toString());
                priceZoneDataListVo.setCountryNameEn(sbNameEn.toString());
            }

            zoneTabEntry.setValue(newZoneDataList);

            sbNameCn.setLength(0);
            sbNameEn.setLength(0);
        }

        return zoneTabMaps;
    }


    String numPattern = "^-?\\d+(\\.\\d+)?$";

    //分区号排序
    void zoneNumSort(List<PriceZoneDataListVo> list) {

        Collections.sort(list, (o1, o2) -> {

            if(o1.getZoneNum().matches(numPattern) && o2.getZoneNum().matches(numPattern)) {
                if (o1.getZoneNum().length() < o2.getZoneNum().length())
                    return -1;
                else if (o1.getZoneNum().length() > o2.getZoneNum().length())
                    return 1;
            }

            return o1.getZoneNum().compareTo(o2.getZoneNum());
        });

    }

    /**
     * 导出分区数据
     * @param zoneId
     * @return
     */
    @Override
    public ResultUtil<Boolean> exportZone(HttpServletResponse response,  Long zoneId){

        if(null==templateFileName || templateFileName.length()<2){
            throw new AplException(PriceZoneDataServiceCode.NO_VALID_FILE_WAS_FOUND.code, PriceZoneDataServiceCode.NO_VALID_FILE_WAS_FOUND.msg, null);
        }

        response.setCharacterEncoding("utf-8");
        ExcelWriter excelWriter = null;

        PriceZoneNameVo zoneNameInfo = priceZoneNameService.getZoneNameInfo(zoneId);
        String newTempFileName = null;
        try {
            SecurityUser securityUser = CommonContextHolder.getSecurityUser();
            String templateFileNameByTenant = templateFileName.replace("-tenant", "-"+securityUser.getInnerOrgCode());
            File templateFile = new File(templateFileNameByTenant);
            if(!templateFile.exists()) {
                templateFileNameByTenant = templateFileName.replace("-tenant", "-common");
                templateFile = new File(templateFileNameByTenant);
            }
            if(!templateFile.exists()) {
                throw new AplException(PriceZoneDataServiceCode.TEMPLATE_DOES_NOT_EXIST.code, PriceZoneDataServiceCode.TEMPLATE_DOES_NOT_EXIST.msg, null);
            }

            newTempFileName = templateFile.getParent() + "/export-exp-zone-temp-" + UUID.randomUUID() + ".xlsx";

            FileInputStream fs = new FileInputStream(templateFileNameByTenant);
            XSSFWorkbook wb = new XSSFWorkbook(fs);
            wb.setSheetName(0, zoneNameInfo.getZoneName());

            //输出临时模板文件
            FileOutputStream fileOut = new FileOutputStream(newTempFileName);
            wb.write(fileOut);
            fileOut.close();

            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(newTempFileName).build();

            String sheetName = null;
            int zoneTemplateSheetIndex = 0;
            int numberOfSheets = wb.getNumberOfSheets();
            if(numberOfSheets < 1)
                throw new AplException(PriceZoneDataServiceCode.TEMPLATE_DOES_NOT_EXIST.code, PriceZoneDataServiceCode.TEMPLATE_DOES_NOT_EXIST.msg, null);

            for(int i = 0; i < numberOfSheets; i++){
                sheetName = wb.getSheetAt(i).getSheetName();
                if(sheetName.contains("分区表模板"))
                    zoneTemplateSheetIndex = i;
            }
            XSSFCellStyle xssfCellStyle = wb.createCellStyle();
            xssfCellStyle.setWrapText(true);
            xssfCellStyle.setBorderLeft(BorderStyle.THIN);
            xssfCellStyle.setBorderRight(BorderStyle.THIN);
            xssfCellStyle.setBorderTop(BorderStyle.THIN);
            xssfCellStyle.setBorderBottom(BorderStyle.THIN);
            xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);//水平
            xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直
            int fontSize = 11;
            XSSFFont xssfFont = wb.createFont();

            //第一次填充
            WriteSheet zoneSheet = EasyExcel.writerSheet(zoneTemplateSheetIndex).build();
            zoneSheet.setSheetName(zoneNameInfo.getZoneName());
            Map<String, String> zoneNameMap = new HashMap<>();
            zoneNameMap.put("zoneName", zoneNameInfo.getZoneName());
            zoneNameMap.put("channelCategory", zoneNameInfo.getChannelCategory());
            excelWriter.fill(zoneNameMap, zoneSheet);


            //第二次填充
            Integer rowNum = 0;
            Map rowMap = null;
            List<Long> idList = new ArrayList<>();
            idList.add(zoneId);
            Map<Long, List<PriceZoneDataListVo>> longListMap = assemblingZoneData(idList);//组装分区数据map(中文名,英文名)
            List<Map<String, String>> zoneDataList = new ArrayList<>();

            for (Map.Entry<Long, List<PriceZoneDataListVo>> zoneDataMap : longListMap.entrySet()) {
                if(zoneDataMap.getKey().equals(zoneId)){
                    List<PriceZoneDataListVo> zoneDataVoList = zoneDataMap.getValue();
                    for (PriceZoneDataListVo priceZoneDataListVo : zoneDataVoList) {
                        rowMap = new HashMap<>();
                        rowMap.put("zoneNum", priceZoneDataListVo.getZoneNum());
                        rowMap.put("countryNameCn", priceZoneDataListVo.getCountryNameCn());
                        rowMap.put("countryNameEn", priceZoneDataListVo.getCountryNameEn());
                        zoneDataList.add(rowMap);
                        rowNum++;
                    }
                }
            }

            excelWriter.fill(zoneDataList, zoneSheet);
            Sheet sheet = excelWriter.writeContext().writeSheetHolder().getCachedSheet();
            Cell cell = findCell(sheet, "分区号");
            int startRowIndex = cell.getRowIndex();
            int startCellIndex = cell.getColumnIndex();
            Row row = null;
            int lastCellNum = 0;
            for(int i = startRowIndex; i < longListMap.get(zoneId).size() + startRowIndex; i++){
                row = sheet.getRow(i);
                lastCellNum = startCellIndex + 2;
                if(null == row)
                    continue;
                for(int j = startCellIndex; j < lastCellNum + 1; j++){
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

                    if(startCellIndex + 1 == j){
                        Integer strLength = cellValueStr.length();
                        int rowNumSize = 1;
                        if(strLength > 35){
                            rowNumSize = strLength / 35;
                        }
                        row.setHeightInPoints((fontSize + 5) * rowNumSize);
                    }
                    xssfFont.setFontHeight(fontSize);
                    xssfFont.setBold(false);
                    xssfCellStyle.setFont(xssfFont);
                    cell1.getCellStyle().cloneStyleFrom(xssfCellStyle);
                }

            }

            //web导出
            response.setContentType("application/vnd.ms-excel");
            outFileName = URLEncoder.encode(outFileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + outFileName);

        } catch (IOException e) {
            return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_FAIL.code, e.getCause().toString(), false);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if(null!=newTempFileName) {
                File delFile = new File(newTempFileName);
                delFile.delete();
            }

            if (null != excelWriter) {
                excelWriter.finish();
            }
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS);
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
}
