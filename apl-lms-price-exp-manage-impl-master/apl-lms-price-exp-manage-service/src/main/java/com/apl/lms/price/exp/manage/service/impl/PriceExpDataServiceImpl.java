package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.apl.cache.AplCacheHelper;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.StringUtil;
import com.apl.lms.net.PartnerNetService;
import com.apl.lms.net.pojo.bo.PartnerApiInfoBo;
import com.apl.lms.price.exp.manage.dao.PriceListDao;
import com.apl.lms.price.exp.manage.mapper2.PriceExpDataMapper;
import com.apl.lms.price.exp.manage.service.*;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.bo.PriceExpProfitMergeBo;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpAxisVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataObjVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataStringVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpDataServiceImpl extends ServiceImpl<PriceExpDataMapper, PriceExpDataPo> implements PriceExpDataService {

    enum PriceExpDataServiceCode {
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有对应数据"),
        ID_IS_NOT_EXIST("ID_IS_NOT_EXIST","id不存在");

        private String code;
        private String msg;

        PriceExpDataServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Autowired
    PriceExpService priceExpService;

    @Autowired
    PriceExpProfitService priceExpProfitService;

    @Autowired
    PriceExpAxisService priceExpAxisService;

    @Autowired
    UnifyProfitService unifyProfitService;

    @Autowired
    PriceListDao priceListDao;

    @Autowired
    AplCacheHelper aplCacheHelper;

    /**
     * 将String数组转换为List
     * @param array
     * @return
     */
    List<String> strArrayToList(String[] array) {

        List<String> list = new ArrayList<>();
        for (String s : array) {
            list.add(s);
        }
        return list;
    }

    /**
     * 根据价格表Id获取详细
     * @param id
     * @return
     */
    @Override
    public PriceExpDataVo getPriceExpDataInfoByPriceId(Long id) {

        PriceExpDataVo priceExpDataVo = baseMapper.getPriceExpDataInfoById(id);

        if (null == priceExpDataVo || null == priceExpDataVo.getPriceDataId()) {
            throw new AplException(PriceExpDataServiceCode.NO_CORRESPONDING_DATA.code,
                    PriceExpDataServiceCode.NO_CORRESPONDING_DATA.msg, null);
        }
        return priceExpDataVo;
    }


    /**
     * 保存价格表数据
     * @param priceDataId
     * @return
     */
    @Override
    public Boolean addPriceExpData(Long priceDataId, PriceExpAddDto priceExpAddDto) {

        PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
        priceExpDataPo.setPriceData(priceExpAddDto.getPriceData());
        priceExpDataPo.setId(priceDataId);
        Integer saveSuccess = baseMapper.insertData(priceExpDataPo);
        return saveSuccess > 0 ? true :false;
    }

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    @Override
    public Boolean updById(PriceExpDataPo priceExpDataPo) throws IOException {

        Integer saveResult = 0;
        Long priceDataId = baseMapper.exists(priceExpDataPo.getId());
        if(null==priceDataId || priceDataId.equals(0))
            saveResult = baseMapper.insertData(priceExpDataPo);
        else{
            saveResult = baseMapper.updById(priceExpDataPo);
            aplCacheHelper.opsForKey("exp-price-cell-value").delByBucket(priceDataId);
        }
        return saveResult > 0 ? true : false;
    }

    /**
     * 批量删除
     */
    @Override
    public Integer delBatch(String priceDataIds) throws IOException {
        List<Long> priceDataIdList = StringUtil.stringToLongList(priceDataIds);
        aplCacheHelper.opsForKey("exp-price-cell-value").delByBucket(priceDataIdList);
        return baseMapper.delBatch(priceDataIds);
    }

    /**
     * 更新表头
     * @param weightSectionUpdDto
     * @return
     */
    @Override
    @Transactional
    public List<String> updHeadCells(WeightSectionUpdDto weightSectionUpdDto,List<String> headCells) throws IOException {

        PriceExpDataStringVo priceExpDataStringVo = baseMapper.getHeadCells(weightSectionUpdDto.getPriceDataId());
        List<List<String>> allHeadCell = priceExpDataStringVo.getPriceData();
        if(null == allHeadCell)
            throw new AplException(PriceExpDataServiceCode.ID_IS_NOT_EXIST.code, PriceExpDataServiceCode.ID_IS_NOT_EXIST.msg);

        List<String> newHeadCells = new ArrayList<>();
        //读取之前价格表数据第一行
        List<String> oldHeadCells = allHeadCell.get(0);//select ...
        Integer weightCellIndex =  weightSectionUpdDto.getWeightSection().get(0).getIndex();
        for(int i=0; i<weightCellIndex; i++){
            newHeadCells.add(oldHeadCells.get(i));
        }
        for(int i=0; i<headCells.size(); i++){
            newHeadCells.add(headCells.get(i));
        }
        allHeadCell.remove(0);
        allHeadCell.add(0, newHeadCells);
        //更新保存新的表头
        Integer integer = baseMapper.updateData(weightSectionUpdDto.getPriceDataId(), allHeadCell);
        if(integer < 1)
            throw new AplException(CommonStatusCode.SAVE_FAIL,null);
        aplCacheHelper.opsForKey("exp-price-cell-value").delByBucket(weightSectionUpdDto.getPriceDataId());

        return newHeadCells;
    }

    public PriceExpDataObjVo getPriceExpData(ExpPriceInfoBo expPriceInfoBo, Long priceId, Boolean isSaleProfit, Long customerGroupId, boolean isExport) throws Exception {

        Long priceDataId = expPriceInfoBo.getPriceDataId();
        DecimalFormat df = new DecimalFormat("#.0");

        //转换原始价格数据start

        //获取价格表数据返回对象
        PriceExpDataVo priceExpDataInfo = getPriceExpDataInfoByPriceId(priceDataId);
        //价格表数据
        List<List<String>> priceData = priceExpDataInfo.getPriceData();
        Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");

        //转换类型
        List<List<Object>> priceDataVo = new ArrayList<>();
        for (List<String> priceDatum : priceData) {
            List<Object> priceDataObj = new ArrayList<>();
            for (String str : priceDatum) {
                Matcher isNum = pattern.matcher(str);
                if (isNum.matches()) {
                    Double var1 = Double.parseDouble(str);
                    String format = df.format(var1);
                    Double element = Double.parseDouble(format);
                    priceDataObj.add(element);
                } else {
                    priceDataObj.add(str);
                }
            }
            priceDataVo.add(priceDataObj);
        }
        //转换原始价格数据end

        ExpPriceProfitDto profitDto = null;
        PriceExpDataObjVo expDataObjVo = null;
        List<PriceExpProfitDto> costProfit = null;
        List<PriceExpProfitDto> increaseProfit = null;
        Integer addProfitWay = expPriceInfoBo.getAddProfitWay();
        if (!isSaleProfit && expPriceInfoBo.getQuotePriceId() > 0) {
            //如果是引用服务商的价格, 成本价必须为服务商的销售利润
            PartnerApiInfoBo partnerApiInfoBo = null;
            try{
                partnerApiInfoBo = PartnerNetService.getSysApiInfo(expPriceInfoBo.getPartnerId());
            }
            catch (Exception e){

            }
            if(null != partnerApiInfoBo) {
                //获取引用价格租户id  客户组id
                Long quotePriceTenantId = partnerApiInfoBo.getSysTenantId();
                Long quotePriceCustomerGroupId = partnerApiInfoBo.getCustomerGroupId();
                String quotePriceTenantCode = partnerApiInfoBo.getSysTenantCode();

                //获取引用价格的addProfitWay
                Integer quotePriceProfitWay = priceListDao.getPriceProfitWay(expPriceInfoBo.getQuotePriceId(), quotePriceTenantCode);

                //获取引用价格的利润, 根据 addProfitWay 得到costProfit 和 increaseProfit或者unifyProfit 或者 null
                if(null != quotePriceProfitWay)
                    profitDto = priceExpProfitService.getProfit(expPriceInfoBo.getQuotePriceId(), quotePriceCustomerGroupId, quotePriceProfitWay, quotePriceTenantId);

                if (null != profitDto)
                    increaseProfit = profitDto.getIncreaseProfit();
            }
        }

        if(null == profitDto)  {
            //如果是销售价, 或者没有引用价格的成本价, 销售价, 则根据自身价格的addProfitWay获取自己表中的利润
            profitDto = priceExpProfitService.getProfit(priceId, customerGroupId, addProfitWay, 0l);

            if(isSaleProfit && null != profitDto)
                increaseProfit = profitDto.getIncreaseProfit();
        }

        if(null != profitDto) {
            costProfit = profitDto.getCostProfit();
        }
            //加利润
            expDataObjVo = addProfitToThePrice(priceId, expPriceInfoBo, priceDataVo, costProfit, increaseProfit, isExport);

        expDataObjVo.setPriceDataId(priceDataId);

        return expDataObjVo;
    }

    //加利润
    private PriceExpDataObjVo addProfitToThePrice(Long priceId,
                                                 ExpPriceInfoBo priceInfoBo,
                                                 List<List<Object>> priceData,
                                                 List<PriceExpProfitDto> costProfitList,
                                                 List<PriceExpProfitDto> increaseProfitList,
                                                 boolean isExport) {

        List<PriceExpProfitMergeBo> costProfitList2 = null;
        //给利润组装分区和国家
        if(null != costProfitList && costProfitList.size() > 0){
             costProfitList2 = assembleZoneAndCountryForProfit(costProfitList);
        }
        List<PriceExpProfitMergeBo> increaseProfitList2 = null;
        if(null != increaseProfitList && increaseProfitList.size() > 0){
            increaseProfitList2 = assembleZoneAndCountryForProfit(increaseProfitList);
        }
        List<List<Object>> priceData2 = new ArrayList<>();

        //获取数据轴
        ResultUtil<PriceExpAxisVo> axisInfoVo = priceExpAxisService.getAxisInfoById(priceId);
        //轴: 重量段
        List<WeightSectionDto> weightSections = axisInfoVo.getData().getWeightSection();
        //轴: 分区国家
        List<List<String>> zoneAndCountrys = axisInfoVo.getData().getZoneCountry();
        //价格表格式
        int priceFormat = priceInfoBo.getPriceFormat();

        //起始列下标
        int startColIndex = 0;
        if (priceFormat == 1)
            startColIndex = weightSections.get(0).getIndex();
        else
            startColIndex = 1;

        List<String> zoneAndCountry = null;
        WeightSectionDto weightSectionDto = null;
        List<Object> objectList = priceData.get(0);
        priceData2.add(objectList);
        DecimalFormat df = new DecimalFormat("#.0");

        for (int rowIndex = 0; rowIndex < priceData.size() - 1; rowIndex++) {

            if (priceFormat == 1) {
                if (rowIndex < zoneAndCountrys.size())
                    zoneAndCountry = zoneAndCountrys.get(rowIndex);
            } else if (priceFormat == 2) {
                if (rowIndex < weightSections.size())
                    weightSectionDto = weightSections.get(rowIndex);
            }

            //除首行以外的行
            List<Object> cells = priceData.get(rowIndex + 1);
            List<Object> cells2 = new ArrayList<>();

            for (int colIndex = 0; colIndex < startColIndex; colIndex++) {
                Object priceStr = cells.get(colIndex);
                cells2.add(priceStr);
            }
            //代表着重量段中的index属性
            for (int colIndex = startColIndex; colIndex < cells.size(); colIndex++) {

                if (priceFormat == 1) {
                    if (colIndex - 1 < weightSections.size()) {
                        if (colIndex == 0)
                            weightSectionDto = weightSections.get(colIndex);
                        else
                            weightSectionDto = weightSections.get(colIndex - 1);
                    }
                } else if (priceFormat == 2) {
                    if (colIndex - 1 < zoneAndCountrys.size()) {
                        if (colIndex == 0)
                            zoneAndCountry = zoneAndCountrys.get(colIndex);
                        else
                            zoneAndCountry = zoneAndCountrys.get(colIndex - 1);
                    }
                }

                String priceStr = cells.get(colIndex).toString();
                if (!StringUtil.isEmpty(priceStr) && !priceStr.contains("*")) {
                    priceStr = priceStr.replaceAll(",", "");
                    Double priceVal = Double.parseDouble(priceStr);

                    //加成本利润
                    PriceCellInfo priceCellInfo = cellPriceAddProfit(priceVal, zoneAndCountry, weightSectionDto, costProfitList2);
                    priceCellInfo.setExpression(null);

                    //本价格增加的利润
                    priceCellInfo =  cellPriceAddProfit(priceCellInfo.getValue(), zoneAndCountry, weightSectionDto, increaseProfitList2);

                    String format = df.format(priceCellInfo.getValue());
                    Double priceVal2 = Double.parseDouble(format);
                    priceCellInfo.setValue(priceVal2);

                    if(isExport)
                        cells2.add(priceCellInfo.getValue());
                    else
                        cells2.add(priceCellInfo);
                }
                else{
                    cells2.add("");
                }
            }
            priceData2.add(cells2);
        }

        PriceExpDataObjVo objVo = new PriceExpDataObjVo();
        objVo.setPriceDataId(priceInfoBo.getPriceDataId());
        objVo.setPriceData(priceData2);

        return objVo;
    }


    /**
     * 给利润组装分区和国家简码
     * @param profitDtoList
     * @return
     */
    public List<PriceExpProfitMergeBo> assembleZoneAndCountryForProfit(List<PriceExpProfitDto> profitDtoList){

        List<PriceExpProfitMergeBo> profitBoList = new ArrayList<>();
        if(null == profitDtoList)
            return Collections.emptyList();

        for (PriceExpProfitDto profitDto : profitDtoList) {
            PriceExpProfitMergeBo priceExpProfitMergeBo = new PriceExpProfitMergeBo();
            BeanUtil.copyProperties(profitDto, priceExpProfitMergeBo);

            //将国家简码组装成List
            if (null != priceExpProfitMergeBo && null != priceExpProfitMergeBo.getCountryCode()) {
                String[] countryCodeArray = priceExpProfitMergeBo.getCountryCode().split(",");
                List<String> countryCodeList = strArrayToList(countryCodeArray);
                priceExpProfitMergeBo.setCountryCodeList(countryCodeList);
            }

            //将分区号组装成List
            if (null != priceExpProfitMergeBo && null != priceExpProfitMergeBo.getZoneNum()) {
                String[] zoneArray = priceExpProfitMergeBo.getZoneNum().split(",");
                List<String> zoneArrayList = strArrayToList(zoneArray);
                priceExpProfitMergeBo.setZoneNumList(zoneArrayList);
            }

            //如果起始重,截止重为空, 则设为默认值 0.0 ~ 10000.0
            if (null == priceExpProfitMergeBo.getStartWeight())
                priceExpProfitMergeBo.setStartWeight(0.0);
            if (null == priceExpProfitMergeBo.getEndWeight() || priceExpProfitMergeBo.getEndWeight().equals(0.0))
                priceExpProfitMergeBo.setEndWeight(10000.0);

            profitBoList.add(priceExpProfitMergeBo);
        }

        return profitBoList;
    }


    /**
     * 一个单元格价格添加利润
     * @param priceVal
     * @param zoneAndCountry
     * @param weightSectionDto
     * @param profitBoList
     * @return
     */
    PriceCellInfo cellPriceAddProfit(Double priceVal, List<String> zoneAndCountry, WeightSectionDto weightSectionDto, List<PriceExpProfitMergeBo> profitBoList){

        PriceCellInfo priceCellInfo = new PriceCellInfo();
        priceCellInfo.setValue(priceVal);
        if(null==priceVal || priceVal.equals(0) || null==profitBoList || profitBoList.isEmpty()){
            return  priceCellInfo;
        }

        int countryStartIndex = 0;
        String zone = null;
        if(zoneAndCountry.get(0).matches("^\\d")){
            zone = zoneAndCountry.get(0);
            countryStartIndex = 1;
        }

        Double startWeight = weightSectionDto.getWeightStart();
        Double endWeight = weightSectionDto.getWeightEnd();
        PriceExpProfitMergeBo findProfitMergeBo = null;

        //查找利润
        boolean isFind = false;
        for (PriceExpProfitMergeBo profitMergeBo : profitBoList) {

            if(profitMergeBo.getEndWeight()>0) {
                if (endWeight <= profitMergeBo.getStartWeight()  ||  startWeight>=profitMergeBo.getEndWeight())
                    continue;

                if (profitMergeBo.getStartWeight()>startWeight)
                    break;
            }

            if (null != zone && zone.length() > 0) {
                if (null == profitMergeBo.getZoneNumList() || profitMergeBo.getZoneNumList().size() == 0 || profitMergeBo.getZoneNumList().get(0).equals("") || profitMergeBo.getZoneNumList().contains(zone))
                    isFind = true;
            }

            if (!isFind) {
                if (null == profitMergeBo.getCountryCodeList() || profitMergeBo.getCountryCodeList().size() == 0 || profitMergeBo.getCountryCodeList().get(0).equals(""))
                    isFind = true;
                else {
                    for (int i = countryStartIndex; i < zoneAndCountry.size(); i++) {
                        String countryCode = zoneAndCountry.get(i);
                        if (profitMergeBo.getCountryCodeList().contains(countryCode)) {
                            isFind = true;
                            break;
                        }
                    }
                }
            }

            if (isFind) {
                findProfitMergeBo = profitMergeBo;
                break;
            }
        }

        if(null!=findProfitMergeBo){
            if(null == findProfitMergeBo.getFirstWeightProfit())
                findProfitMergeBo.setFirstWeightProfit(0.0);
            if(null == findProfitMergeBo.getProportionProfit())
                findProfitMergeBo.setProportionProfit(0.0);
            if(null == findProfitMergeBo.getUnitWeightProfit())
                findProfitMergeBo.setUnitWeightProfit(0.0);
            //找到利润，相加
            if(weightSectionDto.getChargingWay()==1){
                //首重加
                priceCellInfo.setValue(priceVal+findProfitMergeBo.getFirstWeightProfit());
                priceCellInfo.setExpression(priceVal+"+"+findProfitMergeBo.getFirstWeightProfit());

            } else if(weightSectionDto.getChargingWay()==2 || weightSectionDto.getChargingWay()==3 || weightSectionDto.getChargingWay()==4){
                // 单位重加
                priceCellInfo.setValue(priceVal+findProfitMergeBo.getUnitWeightProfit());
                priceCellInfo.setExpression(priceVal+"+"+findProfitMergeBo.getUnitWeightProfit());
            }

            if(findProfitMergeBo.getProportionProfit()>0) {
                //比例加
                priceCellInfo.setValue(priceVal*findProfitMergeBo.getProportionProfit());
                priceCellInfo.setExpression(priceVal+"*"+findProfitMergeBo.getProportionProfit());
            }
        }

        return priceCellInfo;
    }


    class PriceCellInfo{
        private Double value;

        private String expression;

        public Double getValue() {

            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }
    }
}
