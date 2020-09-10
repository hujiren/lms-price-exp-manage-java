package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.FuelChargeMapper;
import com.apl.lms.price.exp.manage.service.FuelChargeService;
import com.apl.lms.price.exp.pojo.dto.FuelChargeKeyDto;
import com.apl.lms.price.exp.pojo.dto.FuelChargeSaveDto;
import com.apl.lms.price.exp.pojo.po.FuelChargePo;
import com.apl.lms.price.exp.pojo.vo.FuelChargeVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    /**
     * 分页查询快递价格
     * @param pageDto
     * @param
     * @return
     */
    @Override
    public ResultUtil<Page<FuelChargeVo>> getList(PageDto pageDto, FuelChargeKeyDto fuelChargeKeyDto) {

        Page<FuelChargeVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        if(null != fuelChargeKeyDto.getChannelCategory())
        fuelChargeKeyDto.setChannelCategory(fuelChargeKeyDto.getChannelCategory().toUpperCase());
        List<FuelChargeVo> fuelChargeVoList = baseMapper.getList(page, fuelChargeKeyDto);

        page.setRecords(fuelChargeVoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据id删除燃油费
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delFuelCharge(Long id) {

        Integer integer = baseMapper.delById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新燃油费
     * @param fuelChargeSaveDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updFuelCharge(FuelChargeSaveDto fuelChargeSaveDto) {

        FuelChargePo fuelChargePo = new FuelChargePo();
        fuelChargePo.setStartDate(new Timestamp(fuelChargeSaveDto.getStartDate()));
        fuelChargePo.setEndDate(new Timestamp(fuelChargeSaveDto.getEndDate()));
        fuelChargePo.setFuelCharge(fuelChargeSaveDto.getFuelCharge());
        fuelChargePo.setChannelCategory(fuelChargeSaveDto.getChannelCategory());
        fuelChargePo.setId(fuelChargeSaveDto.getId());

        FuelChargeVo fuelChargeVo = baseMapper.getFuelCharge(fuelChargePo.getId());
        if(fuelChargeVo == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }

        Integer integer = baseMapper.updFuelCharge(fuelChargePo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 新增燃油费
     * @param fuelChargeSaveDto
     * @return
     */
    @Override
    public ResultUtil<String> addFulCharge(FuelChargeSaveDto fuelChargeSaveDto) {

        FuelChargePo fuelChargePo = new FuelChargePo();
        fuelChargePo.setEndDate(new Timestamp(fuelChargeSaveDto.getEndDate()));
        fuelChargePo.setStartDate(new Timestamp(fuelChargeSaveDto.getStartDate()));
        fuelChargePo.setFuelCharge(fuelChargeSaveDto.getFuelCharge());
        fuelChargePo.setChannelCategory(fuelChargeSaveDto.getChannelCategory());
        fuelChargePo.setId(SnowflakeIdWorker.generateId());

        Integer integer = baseMapper.insertFuelCharge(fuelChargePo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, fuelChargePo.getId().toString());
    }

    /**
     * 获取燃油费详细
     * @param id
     * @return
     */
    @Override
    public ResultUtil<FuelChargeVo> getFuelCharge(Long id) {

        FuelChargeVo fuelChargeVo = baseMapper.getFuelCharge(id);
        if(fuelChargeVo == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, fuelChargeVo);
    }
}
