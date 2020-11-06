package com.apl.lms.price.exp.manage.service.impl;

import com.apl.cache.AplCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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


    @Override
    public PriceExpProfitPo getProfit(Long priceId){
        ExpPriceInfoBo priceInfo = priceExpService.getPriceInfo(priceId);
        PriceExpProfitPo priceExpProfitPo = baseMapper.getIncreaseProfit(priceId);
        priceExpProfitPo.setAddProfitWay(priceInfo.getAddProfitWay());
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
     * @param priceExpProfitPo
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> saveProfit(PriceExpProfitPo priceExpProfitPo) {

        //将添加利润方式更新到价格表
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        priceExpMainPo.setId(priceExpProfitPo.getId());
        priceExpMainPo.setAddProfitWay(priceExpProfitPo.getAddProfitWay());

        if(null != priceExpMainPo.getAddProfitWay())
            priceExpService.updatePriceExpMain(priceExpMainPo);

        Integer flag = 0;
        PriceExpProfitPo existsProfit = baseMapper.exists(priceExpProfitPo.getId());
        if(null!=existsProfit && existsProfit.getId()>0){
            if(null == existsProfit.getCostProfit() || existsProfit.getCostProfit().size() < 1)
                priceExpProfitPo.setCostProfit(priceExpProfitPo.getIncreaseProfit());
            //如果有相同id则更新
            flag = baseMapper.updProfit(priceExpProfitPo);
        }
        else {
            //如果没有相同id则是添加 id采用价格表id
            priceExpProfitPo.setCostProfit(priceExpProfitPo.getIncreaseProfit());
            flag = baseMapper.addProfit(priceExpProfitPo);
        }

        if(flag.equals(0)){
            throw new AplException(CommonStatusCode.SYSTEM_FAIL , null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, priceExpProfitPo.getId());
    }


    public List<PriceExpProfitDto> getQuotePriceProfit(Long quotePriceId, Long customerGroupId, Integer addProfitWay, Long innerOrgId){

        List<PriceExpProfitDto> profitList = null;
        List<PriceExpProfitDto> emptyList = new ArrayList();
        PriceExpProfitPo quotePriceExpProfitPo = baseMapper.getPriceProfit(quotePriceId);

        if(addProfitWay.equals(0))
            return emptyList;
        else if(addProfitWay.equals(1)){
            profitList = mergeProfit(quotePriceExpProfitPo.getCostProfit(), quotePriceExpProfitPo.getIncreaseProfit(), 0l);

        }else if(addProfitWay.equals(2)){
            if((null != customerGroupId && customerGroupId > 0) && (null != innerOrgId && innerOrgId > 0)){
                List<PriceExpProfitDto> profitDtoList = unifyProfitService.getListForTenant(customerGroupId, innerOrgId);

                if(null != profitDtoList && profitDtoList.size() > 0){
                    Collections.sort(profitDtoList, Comparator.comparing(PriceExpProfitDto::getStartWeight));
                    profitList = mergeProfit(quotePriceExpProfitPo.getCostProfit(), profitDtoList, 0l);
                }
            }
        }

        if(null != profitList)
            return profitList;
        else
            return emptyList;
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


    public PriceExpProfitPo getTenantProfit(Long quotePriceId) {
        return baseMapper.getPriceProfit(quotePriceId);
    }

}