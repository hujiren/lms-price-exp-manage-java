package com.apl.lms.price.exp.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apl.cache.AplCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.join.JoinFieldInfo;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper2.PriceExpProfitMapper;
import com.apl.lms.price.exp.manage.service.PriceExpProfitService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.manage.service.UnifyProfitService;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.apl.sys.lib.feign.InnerFeign;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    static JoinFieldInfo joinCustomerGroupFieldInfo = null; //关联 客户组 反射字段缓存

    @Override
    public ResultUtil<Boolean> delById(Long id){

        Integer flag = baseMapper.deleteById(id);
        if(flag > 0){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS , true);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL , false);
    }


    @Override
    public PriceExpProfitPo getProfit(Long priceId){

        PriceExpProfitPo priceExpProfitPo = baseMapper.getProfit(priceId);

        return priceExpProfitPo;
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
     * @param priceExpProfitPo1
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> saveProfit(PriceExpProfitPo priceExpProfitPo1) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(priceExpProfitPo1);
        PriceExpProfitPo priceExpProfitPo = objectMapper.readValue(json, PriceExpProfitPo.class);

        List<PriceExpProfitDto> emptyProfitList = new ArrayList<>();
        List<PriceExpProfitDto> increaseProfit = null;
        if(null == priceExpProfitPo.getIncreaseProfit()){
            priceExpProfitPo.setIncreaseProfit(emptyProfitList);
        }

        if(priceExpProfitPo1.getAddProfitWay().equals(1)){
            //上调的利润
            increaseProfit = priceExpProfitPo.getIncreaseProfit();
            if(increaseProfit.size() < 1)
                return ResultUtil.APPRESULT(PriceExpProfitServiceCode.HAVE_AT_LEAST_ONE_PROFIT.code, PriceExpProfitServiceCode.HAVE_AT_LEAST_ONE_PROFIT.msg, 0L);
        }

        //将添加利润方式更新到价格表
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        priceExpMainPo.setId(priceExpProfitPo.getId());
        priceExpMainPo.setAddProfitWay(priceExpProfitPo.getAddProfitWay());
        priceExpService.updateById(priceExpMainPo);

        // TODO: 2020/11/4 满足 addProfitWay=2 的条件时,算法还没有写, 先直接返回保存成功
        if(priceExpProfitPo1.getAddProfitWay().equals(0) || priceExpProfitPo1.getAddProfitWay().equals(2))
            ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, 0L);

        if(increaseProfit.size() >0){
            //遍历上调的利润
            for (PriceExpProfitDto priceExpSaleProfitDto : increaseProfit) {
                //将空属性设为默认值
                if(null != priceExpSaleProfitDto.getCountryCode()){
                    priceExpSaleProfitDto.setCountryCode(priceExpSaleProfitDto.getCountryCode().toUpperCase());
                }else{
                    priceExpSaleProfitDto.setCountryCode("");
                }

                if(null == priceExpSaleProfitDto.getZoneNum())
                    priceExpSaleProfitDto.setZoneNum("");

                if(null == priceExpSaleProfitDto.getUnitWeightProfit())
                    priceExpSaleProfitDto.setUnitWeightProfit(0.0);

                if(null == priceExpSaleProfitDto.getFirstWeightProfit())
                    priceExpSaleProfitDto.setFirstWeightProfit(0.0);

                if(null == priceExpSaleProfitDto.getProportionProfit())
                    priceExpSaleProfitDto.setProportionProfit(0.0);

                if(null == priceExpSaleProfitDto.getStartWeight())
                    priceExpSaleProfitDto.setStartWeight(0.0);

                if(null == priceExpSaleProfitDto.getEndWeight())
                    priceExpSaleProfitDto.setEndWeight(0.0);
            }
        }

        List<PriceExpProfitDto> quoteProfit = null;
        if(priceExpProfitPo.getId() > 0){
            //根据priceId找到引用价格id
            ExpPriceInfoBo innerOrgIdAndPriceDatId = priceExpService.getPriceInfo(priceExpProfitPo.getId());
            if(innerOrgIdAndPriceDatId.getQuotePriceId() > 0) {
                //如果引用价格id大于0, 则根据引用价格id获取 <最终利润>
                quoteProfit = getQuoteProfit(innerOrgIdAndPriceDatId.getQuotePriceId());

            }
        }
            
        if(null == quoteProfit)//表示没有引用价格id 或没有引用价格对应的利润
            quoteProfit = emptyProfitList;//设置为空
        if(null != quoteProfit && quoteProfit.size() > 0){
            String str = JSON.toJSONString(quoteProfit);
            quoteProfit = JSONObject.parseArray(str, PriceExpProfitDto.class);
        }
        //合并
        Long customerGroupId =0l;//
        List<PriceExpProfitDto> finalProfit = mergeProfit(increaseProfit, quoteProfit, customerGroupId);

        Integer flag = 0;
        Long checkId = baseMapper.exists(priceExpProfitPo.getId());

        priceExpProfitPo.setIncreaseProfit(increaseProfit);
        priceExpProfitPo.setFinalProfit(finalProfit);
        if(null!=checkId && checkId>0){
            //如果有相同id则更新
            flag = baseMapper.updProfit(priceExpProfitPo);
        }
        else {
            //如果没有相同id则是添加 id采用价格表id
            flag = baseMapper.addProfit(priceExpProfitPo);
        }

        if(flag.equals(0)){
            throw new AplException(CommonStatusCode.SYSTEM_FAIL , null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, priceExpProfitPo.getId());
    }
    
    private List<PriceExpProfitDto> getQuoteProfit(Long priceId){
        PriceExpProfitPo priceFinalProfit = baseMapper.getPriceFinalProfit(priceId);
        List<PriceExpProfitDto> finalProfit = priceFinalProfit.getFinalProfit();
        return finalProfit;
    }

    //合并利润
    static List<PriceExpProfitDto> mergeProfit(List<PriceExpProfitDto> list1, List<PriceExpProfitDto> list2, Long customerGroupId){
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

    public static void main(String[] args) {

//        List<PriceExpProfitDto> list1 = new ArrayList<>();
//        PriceExpProfitDto priceExpProfitDto = new PriceExpProfitDto(id, null, "",  "", 0.0, 10.0, 0.0, 1.1, 0.0);
//        list1.add(priceExpProfitDto);
//        priceExpProfitDto = new PriceExpProfitDto(id, null, "",  "", 10.0, 20.5, 0.0, 1.09, 0.0);
//        list1.add(priceExpProfitDto);
//
//        List<PriceExpProfitDto> list2 = new ArrayList<>();
//        PriceExpProfitDto priceExpProfitDto2 = new PriceExpProfitDto(id, null, "",  "", 0.0, 1.0, 0.0, 1.2, 0.0);
//        list2.add(priceExpProfitDto2);
//        priceExpProfitDto2 = new PriceExpProfitDto(id, null, "",  "", 1.0, 5.0, 0.0, 1.09, 0.0);
//        list2.add(priceExpProfitDto2);

//        mergeProfit(list1, list2);
    }

    @Override
    public PriceExpProfitPo getTenantProfit(Long quotePriceId) {
        return baseMapper.getTenantProfit(quotePriceId);
    }

}