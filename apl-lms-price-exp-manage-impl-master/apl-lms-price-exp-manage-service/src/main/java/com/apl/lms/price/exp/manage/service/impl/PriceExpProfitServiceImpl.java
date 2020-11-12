package com.apl.lms.price.exp.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.apl.cache.AplCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpProfitMapper;
import com.apl.lms.price.exp.manage.service.PriceExpProfitService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.manage.service.UnifyProfitService;
import com.apl.lms.price.exp.pojo.bo.CustomerGroupBo;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.ExpPriceProfitSaveDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.apl.sys.lib.feign.InnerFeign;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-09-11
 */
@Service
@Slf4j
public class PriceExpProfitServiceImpl extends ServiceImpl<PriceExpProfitMapper, PriceExpProfitPo> implements PriceExpProfitService {

    //状态code枚举
    enum PriceExpProfitServiceCode {
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有找到利润数据"),
        HAVE_AT_LEAST_ONE_PROFIT("HAVE_AT_LEAST_ONE_PROFIT", "请至少添加一条利润"),
        CHOOSE_OR_ADD_A_UNIFY_PROFIT("CHOOSE_OR_ADD_A_UNIFY_PROFIT", "请选择或者添加一条统一利润");
        private String code;
        private String msg;

        PriceExpProfitServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Autowired
    PriceExpService priceExpService;

    @Autowired
    InnerFeign innerFeign;

    @Autowired
    AplCacheUtil aplCacheUtil;

    @Autowired
    UnifyProfitService unifyProfitService;


    @Override
    public ResultUtil<Boolean> delById(Long id){

        Integer flag = baseMapper.deleteById(id);
        if(flag > 0){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS , true);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL , false);
    }

    /**
     * 获取利润数据(含 根据客户组id进行过滤)
     * @param priceId
     * @param customerGroupId
     * @return
     */
    @Override
    public ExpPriceProfitSaveDto getProfit(Long priceId, Long customerGroupId){
        ExpPriceInfoBo priceInfo = priceExpService.getPriceInfo(priceId);
        if(null == priceInfo)
            return null;
        //增加的利润
        PriceExpProfitPo priceExpProfitPo = baseMapper.getProfit(priceId);
        ExpPriceProfitSaveDto expPriceProfitSaveDto = new ExpPriceProfitSaveDto();
        if(null != priceExpProfitPo && null != priceExpProfitPo.getIncreaseProfit() && !priceExpProfitPo.getIncreaseProfit().equals("")) {
            String increaseProfit = priceExpProfitPo.getIncreaseProfit();
            List<PriceExpProfitDto> profitDtoList = JSONObject.parseArray(increaseProfit, PriceExpProfitDto.class);
            expPriceProfitSaveDto.setIncreaseProfit(profitDtoList);
            if(customerGroupId > 0){
                //如果客户组id大于0, 则按照客户组id进行筛选
                List<PriceExpProfitDto> profitListVo = new ArrayList<>();
                for (PriceExpProfitDto profitDto : profitDtoList) {
                    List<CustomerGroupBo> customerGroups = profitDto.getCustomerGroups();
                    for (CustomerGroupBo customerGroup : customerGroups) {
                        if(customerGroup.getId().equals(customerGroupId)){
                            profitListVo.add(profitDto);
                            break;
                        }
                    }
                }
                expPriceProfitSaveDto.setIncreaseProfit(profitListVo);
            }
        }

        //成本利润
        if(null != priceExpProfitPo && null != priceExpProfitPo.getCostProfit() && !priceExpProfitPo.getCostProfit().equals("")) {
            String costProfit = priceExpProfitPo.getCostProfit();
            List<PriceExpProfitDto> profitDtoList = JSONObject.parseArray(costProfit, PriceExpProfitDto.class);
            expPriceProfitSaveDto.setCostProfit(profitDtoList);
            if(customerGroupId > 0){
                //如果客户组id大于0, 则按照客户组id进行筛选
                List<PriceExpProfitDto> profitListVo = new ArrayList<>();
                for (PriceExpProfitDto profitDto : profitDtoList) {
                    List<CustomerGroupBo> customerGroups = profitDto.getCustomerGroups();
                    for (CustomerGroupBo customerGroup : customerGroups) {
                        if(customerGroup.getId().equals(customerGroupId)){
                            profitListVo.add(profitDto);
                            break;
                        }
                    }
                }
                expPriceProfitSaveDto.setCostProfit(profitListVo);
            }
        }

        expPriceProfitSaveDto.setId(priceId);
        expPriceProfitSaveDto.setAddProfitWay(priceInfo.getAddProfitWay());

        return expPriceProfitSaveDto;
    }


    /**
     * 批量删除
     */
    @Override
    public Integer delBatch(String ids) {
        Integer res = baseMapper.delBatch(ids);
        return res;
    }

    /**
     * 保存利润
     * @param expPriceProfitSaveDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> saveProfit(ExpPriceProfitSaveDto expPriceProfitSaveDto) {

        //将添加利润方式更新到价格表
        if(null != expPriceProfitSaveDto.getAddProfitWay()){
            PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
            priceExpMainPo.setId(expPriceProfitSaveDto.getId());
            priceExpMainPo.setAddProfitWay(expPriceProfitSaveDto.getAddProfitWay());
            priceExpService.updatePriceExpMain(priceExpMainPo);
        }

        Integer flag = 0;
        Long id = baseMapper.exists(expPriceProfitSaveDto.getId());
        PriceExpProfitPo priceExpProfitPo = new PriceExpProfitPo();
        priceExpProfitPo.setId(expPriceProfitSaveDto.getId());
        if(null != expPriceProfitSaveDto.getCostProfit() && expPriceProfitSaveDto.getCostProfit().size() > 0){
            List<PriceExpProfitDto> costProfit = expPriceProfitSaveDto.getCostProfit();
            String s = JSONObject.toJSONString(costProfit);
            priceExpProfitPo.setCostProfit(s);
        }else{
            priceExpProfitPo.setCostProfit("");
        }
        if(null != expPriceProfitSaveDto.getIncreaseProfit() && expPriceProfitSaveDto.getIncreaseProfit().size() > 0){
            List<PriceExpProfitDto> increaseProfit = expPriceProfitSaveDto.getIncreaseProfit();
            String s = JSONObject.toJSONString(increaseProfit);
            priceExpProfitPo.setIncreaseProfit(s);
        }else{
            priceExpProfitPo.setIncreaseProfit("");
        }

        if(null != id && id > 0){
            //如果有相同id则更新
            flag = baseMapper.updateById(priceExpProfitPo);
        }
        else {
            //如果没有相同id则是添加 id采用价格表id
            flag = baseMapper.insert(priceExpProfitPo);
        }
        if(flag.equals(0)){
            throw new AplException(CommonStatusCode.SYSTEM_FAIL , null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, priceExpProfitPo.getId());
    }

    /**
     * 获取引用价格利润
     * @param quotePriceId
     * @return
     */
    public List<PriceExpProfitDto> getQuotePriceSaleProfit(Long quotePriceId){

        List<PriceExpProfitDto> profitList = null;
        List<PriceExpProfitDto> costProfitList = null;
        List<PriceExpProfitDto> increaseProfitList = null;
        List<PriceExpProfitDto> emptyList = new ArrayList();
        PriceExpProfitPo quotePriceExpProfitPo = baseMapper.getQuotePriceSaleProfit(quotePriceId);

        if(null == quotePriceExpProfitPo)
            return emptyList;

        String quotePriceCostProfit = quotePriceExpProfitPo.getCostProfit();
        String quotePriceIncreaseProfit = quotePriceExpProfitPo.getIncreaseProfit();

        if((null == quotePriceCostProfit || quotePriceCostProfit.equals(""))
                && null == quotePriceIncreaseProfit || quotePriceIncreaseProfit.equals(""))
            return emptyList;

        if(null != quotePriceCostProfit && !quotePriceCostProfit.equals(""))
            costProfitList = JSONObject.parseArray(quotePriceCostProfit, PriceExpProfitDto.class);

        if(null != quotePriceIncreaseProfit && !quotePriceIncreaseProfit.equals(""))
            increaseProfitList = JSONObject.parseArray(quotePriceIncreaseProfit, PriceExpProfitDto.class);

        //合并成本利润和增加的利润
        if(null != costProfitList && costProfitList.size() > 0 && null != increaseProfitList && increaseProfitList.size() > 0){
            profitList = mergeProfit(increaseProfitList, costProfitList);
        }

        if(null == costProfitList || costProfitList.size() < 1)
            profitList = increaseProfitList;

        if(null == increaseProfitList || increaseProfitList.size() < 1)
            profitList = costProfitList;

        return profitList;
    }

    //合并利润
    static List<PriceExpProfitDto> mergeProfit(List<PriceExpProfitDto> list1, List<PriceExpProfitDto> list2){
        if(list1.size()<1 && list2.size()<1){
            List<PriceExpProfitDto> emptyList = new ArrayList<>();
            return emptyList;
        }
        if(list1.size()<1){
            return list2;
        }
        if(list2.size()<1){
            return list1;
        }

        Double startWeight = 0.0;
        Double endWeight = 0.0;
        Double priorEndWeight = 0.0;

        Double newFirstWeightProfit = 0.0;
        Double newUnitWeightProfit = 0.0;
        Double newProportionProfit = 0.0;
        Double proportionProfit1 = 0.0;
        Double proportionProfit2 = 0.0;
        PriceExpProfitDto newProfitDto = null;
        List<PriceExpProfitDto> newList = new ArrayList<>();

        for (PriceExpProfitDto profitDto: list1) {

            if(profitDto.getEndWeight().equals(0.0)){
                profitDto.setEndWeight(10000.0);
            }

            int i2 = 0;
            boolean firstWeight = true;
            int size2 = list2.size();
            while (i2<size2){
                PriceExpProfitDto profitDto2 = list2.get(i2);
                if(profitDto2.getEndWeight().equals(0.0)){
                    profitDto2.setEndWeight(10000.0);
                }

                if(!profitDto.getZoneNum().equals(profitDto2.getZoneNum())
                        || !profitDto.getCountryCode().equals(profitDto2.getCountryCode())
                        || profitDto2.getEndWeight() < profitDto.getStartWeight()) {
                    i2++;
                    continue;
                }

                if(profitDto2.getStartWeight() >= profitDto.getEndWeight())
                    break;

                if(firstWeight) {
                    if(profitDto2.getStartWeight()>profitDto.getStartWeight())
                        startWeight = profitDto2.getStartWeight();
                    else
                        startWeight = profitDto.getStartWeight();
                }
                else {
                    startWeight = priorEndWeight;
                }

                endWeight = profitDto.getEndWeight();
                if(endWeight > profitDto2.getEndWeight()) {
                    endWeight = profitDto2.getEndWeight();
                }

                newFirstWeightProfit = profitDto.getFirstWeightProfit() + profitDto2.getFirstWeightProfit();
                newUnitWeightProfit = profitDto.getUnitWeightProfit() + profitDto2.getUnitWeightProfit();
                proportionProfit1 = profitDto.getProportionProfit();
                if(proportionProfit1.equals(0))
                    proportionProfit1 = 1.0;
                proportionProfit2 = profitDto2.getProportionProfit();
                if(proportionProfit2.equals(0))
                    proportionProfit2 = 1.0;
                newProportionProfit = proportionProfit1 * proportionProfit2;

                newProfitDto = new PriceExpProfitDto();
                newProfitDto.setCustomerGroups(profitDto.getCustomerGroups());
                newProfitDto.setZoneNum(profitDto.getZoneNum());
                newProfitDto.setCountryCode(profitDto.getCountryCode());
                newProfitDto.setStartWeight(startWeight);
                newProfitDto.setEndWeight(endWeight);
                newProfitDto.setFirstWeightProfit(newFirstWeightProfit);
                newProfitDto.setUnitWeightProfit(newUnitWeightProfit);
                newProfitDto.setProportionProfit(newProportionProfit);
                newList.add(newProfitDto);

                priorEndWeight = endWeight;
                firstWeight = false;

                i2++;
            }
        }

        return  newList;
    }



}