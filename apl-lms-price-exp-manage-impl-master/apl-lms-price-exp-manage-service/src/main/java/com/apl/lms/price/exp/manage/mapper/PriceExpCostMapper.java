package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceExpCostPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author hjr start
 * @Classname PriceExpCostMapper
 * @Date 2020/8/21 10:50
 */
@Repository
public interface PriceExpCostMapper extends BaseMapper<PriceExpCostPo> {

    /**
     * 根据主表Id更新数据
     * @param priceExpCostPo
     * @return
     */
    Integer updateByPriceExpMainId(@Param("po") PriceExpCostPo priceExpCostPo);

    /**
     * 根据主表Id获取详情
     * @param id
     * @return
     */
    PriceExpCostVo getPriceExpCostInfo(@Param("id") Long id);

    /**
     * 根据id得到主表id
     * @param priceExpMainId
     * @return
     */
    Long getPriceDataId(@Param("id") Long priceExpMainId);

    /**
     * 通过主表id获取统计条数
     * @param priceMainId
     * @return
     */
    Integer getPriceDataIdCount(@Param("id") Long priceMainId);
}
