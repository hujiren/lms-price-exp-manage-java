package com.apl.lms.price.exp.manage.service.impl;

import com.apl.cache.AplCacheUtil;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.common.query.manage.po.CommonCarrierPo;
import com.apl.lms.price.exp.manage.mapper.CarrierMapper;
import com.apl.lms.price.exp.manage.service.CarrierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apl.lib.constants.CommonStatusCode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.apl.lms.price.exp.pojo.po.CarrierPo;
import java.util.List;


/**
 * <p>
 *  service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-10-07
 */
@Service
@Slf4j
public class CarrierServiceImpl extends ServiceImpl<CarrierMapper, CarrierPo> implements CarrierService {

    //状态code枚举
    /*enum CarrierServiceCode {

            ;

            private String code;
            private String msg;

            CarrierServiceCode(String code, String msg) {
                 this.code = code;
                 this.msg = msg;
            }
        }*/

    private static final String CACHE_KEY = "carrier";

    @Autowired
    AplCacheUtil aplCacheUtil;

    @Override
    public ResultUtil<Long> add(CarrierPo carrierPo){


        Integer flag = baseMapper.insert(carrierPo);
        if(flag.equals(1)){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS , carrierPo.getId());
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL , null);
    }


    @Override
    public ResultUtil<Boolean> updById(CarrierPo carrierPo){


        Integer flag = baseMapper.updateById(carrierPo);
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
    public ResultUtil<List<CommonCarrierPo>> getList(){

        List<CommonCarrierPo> carrierCacheList = (List<CommonCarrierPo>) aplCacheUtil.opsForValue().get(CACHE_KEY);

        List<CommonCarrierPo> carrierPoList = baseMapper.getList();
        if(null != carrierPoList && carrierCacheList.size() > 0){
            for (CommonCarrierPo commonCarrierPo : carrierPoList) {
                carrierCacheList.add(commonCarrierPo);
            }
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS , carrierCacheList);
    }

    @Override
    public ResultUtil<List<CommonCarrierPo>> getListByInnerOrgId(Long innerOrgId) {

        List<CommonCarrierPo> carrierCacheList = (List<CommonCarrierPo>) aplCacheUtil.opsForValue().get(CACHE_KEY);

        List<CommonCarrierPo> carrierPoList = baseMapper.getListByInnerOrgId(innerOrgId);
        if(null != carrierPoList && carrierCacheList.size() > 0){
            for (CommonCarrierPo commonCarrierPo : carrierPoList) {
                carrierCacheList.add(commonCarrierPo);
            }
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS , carrierCacheList);
    }
}