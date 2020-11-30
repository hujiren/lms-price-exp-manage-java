package com.apl.lms.price.exp.manage.service.impl;

import com.apl.cache.AplCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.common.lib.feign.LmsCommonFeign;
import com.apl.lms.price.exp.manage.mapper.CarrierMapper;
import com.apl.lms.price.exp.manage.service.CarrierService;
import com.apl.lms.price.exp.pojo.po.CarrierPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    enum CarrierServiceCode {

            ID_IS_NOT_EXISTS("ID_IS_NOT_EXISTS", "id不存在");

            private String code;
            private String msg;

            CarrierServiceCode(String code, String msg) {
                 this.code = code;
                 this.msg = msg;
            }
        }

    private static final String CACHE_KEY = "carrier";

    @Autowired
    AplCacheUtil aplCacheUtil;

    @Autowired
    LmsCommonFeign lmsCommonFeign;

    /**
     * 添加公共运输方
     * @param carrierPo
     * @return
     */
    @Override
    public ResultUtil<Long> add(CarrierPo carrierPo){

        carrierPo.setId(SnowflakeIdWorker.generateId());
        Integer resultNum = baseMapper.insert(carrierPo);
        if(resultNum.equals(1)){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS , carrierPo.getId());
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL , null);
    }

    /**
     * 更新公共运输方
     * @param carrierPo
     * @return
     */
    @Override
    public ResultUtil<Boolean> updById(CarrierPo carrierPo){

        Integer resultNum = baseMapper.updateById(carrierPo);
        if(resultNum.equals(1)){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS , true);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL , false);
    }

    /**
     * 删除公共运输方
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delById(Long id){

        Integer resultNum = baseMapper.deleteById(id);
        if(resultNum > 0){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS , true);
        }

        return ResultUtil.APPRESULT(CarrierServiceCode.ID_IS_NOT_EXISTS.code,
                CarrierServiceCode.ID_IS_NOT_EXISTS.msg,false);
    }

    /**
     * 获取公共运输方列表
     * @return
     */
    @Override
    public ResultUtil<List<CarrierPo>> getList(){

        List<CarrierPo> carrierPoList = baseMapper.getList();

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS , carrierPoList);
    }

    /**
     * 根据租户id获取公共运输方列表
     * @param innerOrgId
     * @return
     * @throws Exception
     */
    @Override
    public ResultUtil<List<CarrierPo>> getListByInnerOrgId(Long innerOrgId) throws Exception {

        List<CarrierPo> carrierCacheList = getCommonCarrierByCache();
        List<CarrierPo> carrierPoList = baseMapper.getListByInnerOrgId(innerOrgId);
        if(null != carrierPoList && carrierPoList.size() > 0){
            for (CarrierPo commonCarrierPo : carrierPoList) {
                carrierCacheList.add(commonCarrierPo);
            }
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS , carrierCacheList);
    }

    /**
     * 从缓存获取公共运输方列表
     * @return
     * @throws IOException
     */
    public List<CarrierPo> getCommonCarrierByCache() throws IOException {
        List<CarrierPo> carrierCacheList = (List<CarrierPo>) aplCacheUtil.opsForValue("priceManage").get(CACHE_KEY);
        return carrierCacheList;
    }
}