package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.FuelChargeAddDto;
import com.apl.lms.price.exp.pojo.po.FuelChargePo;
import com.apl.lms.price.exp.pojo.vo.FuelChargeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface FuelChargeService extends IService<FuelChargePo> {

    /**
     * 查询燃油费列表
     * @param
     * @return
     */
    ResultUtil<List<FuelChargeVo>> getList();

    /**
     * 删除燃油费
     * @param id
     * @return
     */
    ResultUtil<Boolean> delFuelCharge(Long id);

    /**
     * 更新燃油费
     * @param fuelChargeAddDto
     * @return
     */
    ResultUtil<Boolean> updFuelCharge(FuelChargeAddDto fuelChargeAddDto);

    /**
     * 新增燃油费
     * @param fuelChargeAddDto
     * @return
     */
    ResultUtil<String> addFulCharge(FuelChargeAddDto fuelChargeAddDto);
}
