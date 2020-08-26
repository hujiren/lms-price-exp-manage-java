package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.FuelChargeDto;
import com.apl.lms.price.exp.pojo.dto.FuelChargeInsertDto;
import com.apl.lms.price.exp.pojo.dto.FuelChargeKeyDto;
import com.apl.lms.price.exp.pojo.po.FuelChargePo;
import com.apl.lms.price.exp.pojo.vo.FuelChargeVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface FuelChargeService extends IService<FuelChargePo> {

    /**
     * 分页查询燃油费列表
     * @param pageDto
     * @param fuelChargeKeyDto
     * @return
     */
    ResultUtil<Page<FuelChargeVo>> getList(PageDto pageDto, FuelChargeKeyDto fuelChargeKeyDto);

    /**
     * 删除燃油费
     * @param id
     * @return
     */
    ResultUtil<Boolean> delFuelCharge(Long id);

    /**
     * 更新燃油费
     * @param fuelChargeDto
     * @return
     */
    ResultUtil<Boolean> updFuelCharge(FuelChargeDto fuelChargeDto);

    /**
     * 新增燃油费
     * @param fuelChargeInsertDto
     * @return
     */
    ResultUtil<String> addFulCharge(FuelChargeInsertDto fuelChargeInsertDto);

    /**
     * 获取燃油费详情
     * @param id
     * @return
     */
    ResultUtil<FuelChargeVo> getFuelCharge(Long id);
}
