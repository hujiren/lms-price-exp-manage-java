package com.apl.lms.price.exp.manage.service.impl;

import com.apl.cache.AplCacheHelper;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.net.SecurityUserNetService;
import com.apl.lms.price.exp.manage.mapper.FuelChargeMapper;
import com.apl.lms.price.exp.manage.service.FuelChargeService;
import com.apl.lms.price.exp.pojo.dto.FuelChargeAddDto;
import com.apl.lms.price.exp.pojo.po.FuelChargePo;
import com.apl.lms.price.exp.pojo.vo.FuelChargeVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Service
@Slf4j
public class FuelChargeServiceImpl extends ServiceImpl<FuelChargeMapper, FuelChargePo> implements FuelChargeService {

    enum ExpListServiceCode {
        ID_IS_NOT_EXITS("ID_IS_NOT_EXITS","id不存在")
        ;

        private String code;
        private String msg;

        ExpListServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Autowired
    AplCacheHelper aplCacheHelper;

    /**
     * 查询快递价格
     * @param
     * @param
     * @return
     */
    @Override
    public ResultUtil<List<FuelChargeVo>> getList() {

        List<FuelChargeVo> fuelChargeVoList = baseMapper.getList();

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, fuelChargeVoList);
    }

    /**
     * 根据id删除燃油费
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delFuelCharge(Long id) throws IOException {

        FuelChargePo fuelChargePo = baseMapper.selectById(id);

        if(null != fuelChargePo){
            baseMapper.delById(id);
            SecurityUser securityUser = SecurityUserNetService.getSecurityUser(aplCacheHelper);
            aplCacheHelper.opsForKey("exp-price-fuel-value").delByBucket(securityUser.getInnerOrgCode(), fuelChargePo.getChannelCategory());
        }

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新燃油费
     * @param fuelChargeAddDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updFuelCharge(FuelChargeAddDto fuelChargeAddDto) throws IOException {

        FuelChargePo fuelChargePo = new FuelChargePo();
        fuelChargePo.setStartDate(new Timestamp(fuelChargeAddDto.getStartDate()));
        fuelChargePo.setEndDate(new Timestamp(fuelChargeAddDto.getEndDate()));
        fuelChargePo.setFuelCharge(fuelChargeAddDto.getFuelCharge());
        fuelChargePo.setChannelCategory(fuelChargeAddDto.getChannelCategory());
        fuelChargePo.setId(fuelChargeAddDto.getId());

        Integer resultNum = baseMapper.updFuelCharge(fuelChargePo);
        if(resultNum < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }

        SecurityUser securityUser = SecurityUserNetService.getSecurityUser(aplCacheHelper);

        aplCacheHelper.opsForKey("exp-price-fuel-value").delByBucket(securityUser.getInnerOrgCode(), fuelChargePo.getChannelCategory());

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 新增燃油费
     * @param fuelChargeAddDto
     * @return
     */
    @Override
    public ResultUtil<String> addFulCharge(FuelChargeAddDto fuelChargeAddDto) {

        FuelChargePo fuelChargePo = new FuelChargePo();
        fuelChargePo.setEndDate(new Timestamp(fuelChargeAddDto.getEndDate()));
        fuelChargePo.setStartDate(new Timestamp(fuelChargeAddDto.getStartDate()));
        fuelChargePo.setFuelCharge(fuelChargeAddDto.getFuelCharge());
        fuelChargePo.setChannelCategory(fuelChargeAddDto.getChannelCategory());
        fuelChargePo.setId(SnowflakeIdWorker.generateId());

        Integer resultNum = baseMapper.insertFuelCharge(fuelChargePo);
        if(resultNum < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, fuelChargePo.getId().toString());
    }

}
