package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.PriceExpProfitMapper;
import com.apl.lms.price.exp.manage.service.PriceExpProfitService;
import com.apl.lms.price.exp.pojo.bo.PriceExpSaleProfitBo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.apl.lib.constants.CommonStatusCode;

import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpProfitListVo;

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


    @Override
    public ResultUtil<Long> add(PriceExpProfitPo priceExpProfitPo){

        List<PriceExpSaleProfitBo> increaseProfit = priceExpProfitPo.getIncreaseProfit();
        if(increaseProfit.size() != 0){
            for (PriceExpSaleProfitBo priceExpSaleProfitBo : increaseProfit) {
                if(null != priceExpSaleProfitBo.getCountryCode()){
                    priceExpSaleProfitBo.setCountryCode(priceExpSaleProfitBo.getCountryCode().toUpperCase());
                }
            }
        }
        priceExpProfitPo.setIncreaseProfit(increaseProfit);
        priceExpProfitPo.setId(SnowflakeIdWorker.generateId());
        priceExpProfitPo.setFinalProfit(increaseProfit);
        Integer flag = baseMapper.addProfit(priceExpProfitPo);
        if(flag.equals(1)){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS , priceExpProfitPo.getId());
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL , null);
    }


    @Override
    public ResultUtil<Boolean> updById(PriceExpProfitPo priceExpProfitPo){
        List<PriceExpSaleProfitBo> increaseProfit = priceExpProfitPo.getIncreaseProfit();
        if(increaseProfit.size() != 0){
            for (PriceExpSaleProfitBo priceExpSaleProfitBo : increaseProfit) {
                if(null != priceExpSaleProfitBo.getCountryCode()){
                    priceExpSaleProfitBo.setCountryCode(priceExpSaleProfitBo.getCountryCode().toUpperCase());
                }
            }
        }
        priceExpProfitPo.setIncreaseProfit(increaseProfit);
        priceExpProfitPo.setFinalProfit(priceExpProfitPo.getIncreaseProfit());
        Integer flag = baseMapper.updProfit(priceExpProfitPo);
        if(flag.equals(1)){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS , true);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL , false);
    }


    @Override
    public ResultUtil<Boolean> delById(Long id){

        Integer flag = baseMapper.deleteById(id);
        if(flag > 0){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS , true);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL , false);
    }


    @Override
    public ResultUtil<PriceExpProfitListVo> getList(Long priceId){

        List<PriceExpProfitListVo> list = baseMapper.getList(priceId);

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
}