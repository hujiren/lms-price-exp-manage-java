package com.apl.lms.price.exp.manage.mapper;


import com.apl.lms.price.exp.pojo.dto.FuelChargeKeyDto;
import com.apl.lms.price.exp.pojo.po.FuelChargePo;
import com.apl.lms.price.exp.pojo.vo.FuelChargeVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Mapper
@Repository
public interface FuelChargeMapper extends BaseMapper<FuelChargePo> {

    /**
     * 获取燃油费分页信息列表
     * @param page
     * @return
     */
    List<FuelChargeVo> getList(Page<FuelChargeVo> page, @Param("key") FuelChargeKeyDto expListKeyDt, @Param("startTimes") Timestamp startTimes, @Param("endTimes") Timestamp endTimes);

    /**
     * 根据Id删除燃油费
     * @param id
     * @return
     */
    Integer delById(@Param("id") Long id);

    /**
     * 更新燃油费
     * @param fuelChargePo
     * @return
     */
    Integer updFuelCharge(@Param("po") FuelChargePo fuelChargePo);

    /**
     * 新增燃油费
     * @param fuelChargePo
     * @return
     */
    Integer insertFuelCharge(@Param("po") FuelChargePo fuelChargePo);



}
