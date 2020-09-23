package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.exception.AplException;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.PriceExpProfitMapper;
import com.apl.lms.price.exp.manage.service.PriceExpProfitService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;

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
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有找到对应数据");
        private String code;
        private String msg;

        PriceExpProfitServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Autowired
    PriceExpService priceExpService;


    @Override
    public ResultUtil<Boolean> delById(Long id){

        Integer flag = baseMapper.deleteById(id);
        if(flag > 0){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS , true);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL , false);
    }


    @Override
    public ResultUtil<List<PriceExpProfitPo>> getList(Long priceId){

        List<PriceExpProfitPo> list = baseMapper.getList(priceId);

        if(list.size() == 0){
            return ResultUtil.APPRESULT(PriceExpProfitServiceCode.NO_CORRESPONDING_DATA.code,
                    PriceExpProfitServiceCode.NO_CORRESPONDING_DATA.msg,null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS , list);
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
    public Long saveProfit(PriceExpProfitPo priceExpProfitPo){

        List<PriceExpProfitDto> emptyProfitList = new ArrayList<>();
        if(null == priceExpProfitPo.getIncreaseProfit()){
            priceExpProfitPo.setIncreaseProfit(emptyProfitList);
        }

        List<PriceExpProfitDto> increaseProfit = priceExpProfitPo.getIncreaseProfit();
        if(increaseProfit.size() >0){
            for (PriceExpProfitDto priceExpSaleProfitDto : increaseProfit) {
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
        if(priceExpProfitPo.getPriceId() > 0){
            //根据priceId找到引用价格id
            ExpPriceInfoBo innerOrgIdAndPriceDatId = priceExpService.getInnerOrgIdAndPriceDatId(priceExpProfitPo.getPriceId());
            if(innerOrgIdAndPriceDatId.getQuotePriceId() > 0) {
                quoteProfit = getQuoteProfit(innerOrgIdAndPriceDatId.getQuotePriceId());
            }

        }
        if(null == quoteProfit)
            quoteProfit = emptyProfitList;
        List<PriceExpProfitDto> finalProfit = mergeProfit(increaseProfit, quoteProfit);

        Integer flag = 0;
        priceExpProfitPo.setIncreaseProfit(increaseProfit);
        priceExpProfitPo.setFinalProfit(finalProfit);
        if(priceExpProfitPo.getId()>0){
            flag = baseMapper.updProfit(priceExpProfitPo);
        }
        else {
            priceExpProfitPo.setId(SnowflakeIdWorker.generateId());
            flag = baseMapper.addProfit(priceExpProfitPo);
        }

        if(flag.equals(0)){
            throw new AplException(CommonStatusCode.SYSTEM_FAIL , null);
        }

        return priceExpProfitPo.getId();
    }

    private List<PriceExpProfitDto> getQuoteProfit(Long priceId){
        List<PriceExpProfitDto> priceFinalProfit = baseMapper.getPriceFinalProfit(priceId);
        return priceFinalProfit;
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

            int i2=0;
            int size2 = list2.size();
            while (i2<size2){
                PriceExpProfitDto profitDto2 = list2.get(i2);

                if(!profitDto.getZoneNum().equals(profitDto2.getZoneNum())
                        || !profitDto.getCountryCode().equals(profitDto2.getCountryCode())
                        || profitDto2.getEndWeight() < profitDto.getStartWeight()) {
                    i2++;
                    continue;
                }

                if(profitDto2.getStartWeight() >= profitDto.getEndWeight())
                    break;

                if(i2 == 0)
                    startWeight = profitDto2.getStartWeight();
                else
                    startWeight = priorEndWeight;

                endWeight = profitDto.getEndWeight();
                if(endWeight > profitDto2.getEndWeight()) {
                    endWeight = profitDto2.getEndWeight();
                }

                newProfitDto = new PriceExpProfitDto();

                newFirstWeightProfit = profitDto.getFirstWeightProfit() + profitDto2.getFirstWeightProfit();
                newUnitWeightProfit = profitDto.getUnitWeightProfit() + profitDto2.getUnitWeightProfit();
                proportionProfit1 = profitDto.getProportionProfit();
                if(proportionProfit1.equals(0))
                    proportionProfit1 = 1.0;
                proportionProfit2 = profitDto2.getProportionProfit();
                if(proportionProfit2.equals(0))
                    proportionProfit2 = 1.0;
                newProportionProfit = proportionProfit1 * proportionProfit2;

                newProfitDto.setCustomerGroupsId(profitDto.getCustomerGroupsId());
                newProfitDto.setZoneNum(profitDto.getZoneNum());
                newProfitDto.setCountryCode(profitDto.getCountryCode());
                newProfitDto.setStartWeight(startWeight);
                newProfitDto.setEndWeight(endWeight);
                newProfitDto.setFirstWeightProfit(newFirstWeightProfit);
                newProfitDto.setUnitWeightProfit(newUnitWeightProfit);
                newProfitDto.setProportionProfit(newProportionProfit);
                newList.add(newProfitDto);

                //System.out.println(newProfitDto);

                priorEndWeight = endWeight;

                i2++;
            }
        }

        return  newList;
    }

    public static void main(String[] args) {

        List<PriceExpProfitDto> list1 = new ArrayList<>();
        PriceExpProfitDto priceExpProfitDto = new PriceExpProfitDto(null, "",  "", 0.0, 10.0, 0.0, 1.1, 0.0);
        list1.add(priceExpProfitDto);
        priceExpProfitDto = new PriceExpProfitDto(null, "",  "", 10.0, 20.5, 0.0, 1.09, 0.0);
        list1.add(priceExpProfitDto);

        List<PriceExpProfitDto> list2 = new ArrayList<>();
        PriceExpProfitDto priceExpProfitDto2 = new PriceExpProfitDto(null, "",  "", 0.0, 1.0, 0.0, 1.2, 0.0);
        list2.add(priceExpProfitDto2);
        priceExpProfitDto2 = new PriceExpProfitDto(null, "",  "", 1.0, 5.0, 0.0, 1.09, 0.0);
        list2.add(priceExpProfitDto2);

        mergeProfit(list1, list2);
    }


}