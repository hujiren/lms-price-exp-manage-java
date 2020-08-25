package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceExpDevelopInfoPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author hjr start
 * @Classname PriceExpDevelopInfoMapper
 * @Date 2020/8/19 18:12
 */
@Repository
public interface PriceExpDevelopInfoMapper extends BaseMapper<PriceExpDevelopInfoPo> {

    /**
     * 根据主表id更新数据
     * @param priceExpDevelopInfoPo
     * @return
     */
    Integer updateByPriceExpMainId(@Param("po") PriceExpDevelopInfoPo priceExpDevelopInfoPo);

    /**
     * 根据主表id获取详细信息
     * @param id
     * @return
     */
    PriceExpDevelopInfoPo getDevelopInfoByMainId(@Param("id") Long id);

    /**
     * 根据扩展表Id删数据
     * @param id
     * @return
     */
    Integer deleteByPriceId(@Param("id") Long id);

}
