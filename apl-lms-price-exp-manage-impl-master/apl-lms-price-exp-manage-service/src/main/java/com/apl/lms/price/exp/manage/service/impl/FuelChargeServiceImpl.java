package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.dao.ExpListMapper;
import com.apl.lms.price.exp.manage.dao.FuelChargeMapper;
import com.apl.lms.price.exp.manage.pojo.dto.ExpListDto;
import com.apl.lms.price.exp.manage.pojo.dto.ExpListKeyDto;
import com.apl.lms.price.exp.manage.pojo.dto.FuelChargeDto;
import com.apl.lms.price.exp.manage.pojo.dto.FuelChargeKeyDto;
import com.apl.lms.price.exp.manage.pojo.po.ExpListPo;
import com.apl.lms.price.exp.manage.pojo.po.FuelChargePo;
import com.apl.lms.price.exp.manage.pojo.vo.ExpListVo;
import com.apl.lms.price.exp.manage.pojo.vo.FuelChargeVo;
import com.apl.lms.price.exp.manage.service.ExpListService;
import com.apl.lms.price.exp.manage.service.FuelChargeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Service
@Slf4j
public class FuelChargeServiceImpl extends ServiceImpl<FuelChargeMapper, FuelChargePo> implements FuelChargeService {

    enum ExpListServiceCode {
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
     * @param expListKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<FuelChargeVo>> getList(PageDto pageDto, FuelChargeKeyDto expListKeyDto) {

        Page<FuelChargeVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<FuelChargeVo> fuelChargeVoList = baseMapper.getList(page, expListKeyDto);

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
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新燃油费
     * @param fuelChargeDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updFuelCharge(FuelChargeDto fuelChargeDto) {

        FuelChargePo fuelChargePo = new FuelChargePo();
        BeanUtils.copyProperties(fuelChargeDto, fuelChargePo);

        Integer integer = baseMapper.updFuelCharge(fuelChargePo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 新增燃油费
     * @param fuelChargeDto
     * @return
     */
    @Override
    public ResultUtil<Long> insFuelCharge(FuelChargeDto fuelChargeDto) {

        FuelChargePo fuelChargePo = new FuelChargePo();
        BeanUtils.copyProperties(fuelChargeDto, fuelChargePo);
        fuelChargePo.setId(SnowflakeIdWorker.generateId());

        Integer integer = baseMapper.insertFuelCharge(fuelChargePo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, fuelChargePo.getId());
    }
}
