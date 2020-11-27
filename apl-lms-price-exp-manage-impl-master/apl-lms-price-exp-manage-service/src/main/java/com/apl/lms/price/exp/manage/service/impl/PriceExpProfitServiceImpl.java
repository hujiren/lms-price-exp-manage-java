package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.apl.cache.AplCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpProfitMapper;
import com.apl.lms.price.exp.manage.service.PriceExpProfitService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.manage.service.PriceIncreaseProfitService;
import com.apl.lms.price.exp.manage.service.UnifyProfitService;
import com.apl.lms.price.exp.pojo.bo.CustomerGroupBo;
import com.apl.lms.price.exp.pojo.dto.ExpPriceProfitDto;
import com.apl.lms.price.exp.pojo.dto.IncreaseProfitDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.apl.lms.price.exp.pojo.dto.UnifyProfitDto;
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

    @Autowired
    PriceIncreaseProfitService priceIncreaseProfitService;

    /**
     * 删除利润
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delById(Long id){

        Integer flag = baseMapper.deleteById(id);
        if(flag > 0){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS , true);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL , false);
    }



    /**
     * 批量删除
     */
    @Override
    public Integer delBatch(String ids) {
        Integer resultNum = baseMapper.delBatch(ids);

        return resultNum;
    }

    /**
     * 保存利润
     * @param expPriceProfitDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> saveProfit(ExpPriceProfitDto expPriceProfitDto) {

        //将添加利润方式更新到价格表
        if(null != expPriceProfitDto.getAddProfitWay()){
            PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
            priceExpMainPo.setId(expPriceProfitDto.getId());
            priceExpMainPo.setAddProfitWay(expPriceProfitDto.getAddProfitWay());
            priceExpService.updatePriceExpMain(priceExpMainPo);
        }
        
        Integer flag = 0;
        Long id = baseMapper.exists(expPriceProfitDto.getId());
        PriceExpProfitPo priceExpProfitPo = new PriceExpProfitPo();
        priceExpProfitPo.setId(expPriceProfitDto.getId());
        if(null != expPriceProfitDto.getCostProfit() && !expPriceProfitDto.getCostProfit().isEmpty()){
            List<PriceExpProfitDto> costProfit = expPriceProfitDto.getCostProfit();
            String costProfitStr = JSONObject.toJSONString(costProfit);
            priceExpProfitPo.setCostProfit(costProfitStr);
        }else{
            priceExpProfitPo.setCostProfit("");
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
     * 获取利润
     * @param priceId 价格表id
     * @param customerGroupId 客户组id
     * @param addProfitWay 添加利润方式
     * @param tenantId 租户id
     * @return
     */
    @Override
    public ExpPriceProfitDto getProfit(Long priceId, Long customerGroupId, Integer addProfitWay, Long tenantId){

        if(addProfitWay.equals(0))
            return null;

        PriceExpProfitPo priceExpProfitPo = baseMapper.getProfit(priceId);

        ExpPriceProfitDto expPriceProfitDto = new ExpPriceProfitDto();
        expPriceProfitDto.setId(priceId);
        expPriceProfitDto.setAddProfitWay(addProfitWay);

        //成本利润
        if(null != priceExpProfitPo && null != priceExpProfitPo.getCostProfit() && !priceExpProfitPo.getCostProfit().equals("")) {
            String costProfit = priceExpProfitPo.getCostProfit();
            List<PriceExpProfitDto> profitDtoList = JSONObject.parseArray(costProfit, PriceExpProfitDto.class);
            expPriceProfitDto.setCostProfit(profitDtoList);
        }

        List<PriceExpProfitDto> increaseProfitList2 = null;
        if(addProfitWay.equals(1)){
            //此价格， 单独增加利润
            ResultUtil<IncreaseProfitDto> increaseProfitDtoResultUtil = priceIncreaseProfitService.getList(priceId);
            IncreaseProfitDto resultIncreaseData = increaseProfitDtoResultUtil.getData();
            if(null != resultIncreaseData)
                increaseProfitList2 = resultIncreaseData.getIncreaseProfit();
        }
        else if(addProfitWay.equals(2)){
            //此价格， 统一增加利润
            increaseProfitList2 = new ArrayList<>();
            List<UnifyProfitDto> unifyProfitList = unifyProfitService.getList(customerGroupId, tenantId);//.....
            for (UnifyProfitDto unifyProfitDto : unifyProfitList) {
                PriceExpProfitDto unifyProfit = new PriceExpProfitDto();
                BeanUtil.copyProperties(unifyProfitDto, unifyProfit);
                increaseProfitList2.add(unifyProfit);
            }
        }

        List<PriceExpProfitDto> increaseProfitList = new ArrayList<>();
        if(customerGroupId > 0 && null!=increaseProfitList2){
            //如果客户组id大于0, 则按照客户组id进行筛选
            for (PriceExpProfitDto profitDto : increaseProfitList2) {

                List<CustomerGroupBo> customerGroups = profitDto.getCustomerGroups();
                if(null != customerGroups && customerGroups.size() > 0){
                    for (CustomerGroupBo customerGroup : customerGroups) {
                        if(customerGroup.getCustomerGroupId().equals(customerGroupId)){
                            increaseProfitList.add(profitDto);
                            break;
                        }
                    }
                }
            }
        }
        if(increaseProfitList.size() > 0)
            expPriceProfitDto.setIncreaseProfit(increaseProfitList);

        return expPriceProfitDto;
    }


    @Override
    public List<PriceExpProfitDto> assembleSaleProfit(ExpPriceProfitDto profit) {
        List<PriceExpProfitDto> costProfit = profit.getCostProfit();
        List<PriceExpProfitDto> increaseProfit = profit.getIncreaseProfit();
        if(null == costProfit)
            costProfit = Collections.emptyList();
        if(null == increaseProfit)
            increaseProfit = Collections.emptyList();
        List<PriceExpProfitDto> profitList = mergeProfit(costProfit, increaseProfit);

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