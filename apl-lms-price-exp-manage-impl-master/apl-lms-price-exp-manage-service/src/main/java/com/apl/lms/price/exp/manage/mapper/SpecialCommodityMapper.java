package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.SpecialCommodityKeyDto;
import com.apl.lms.price.exp.pojo.po.SpecialCommodityPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 航空公司 Mapper 接口
 * </p>
 *
 * @author cy
 * @since 2020-04-13
 */
@Mapper
@Repository
public interface SpecialCommodityMapper extends BaseMapper<SpecialCommodityPo> {


    /**
     * @Desc: 查找特殊物品列表
     * @Author:
     * @Date: 2020-08-08
     */
    List<SpecialCommodityPo> getList(Page page, @Param("key") SpecialCommodityKeyDto specialCommodityKeyDto);

    /**
     * 批量插入特殊物品
     * @param specialCommodityAddDtoList
     * @return
     */
    Integer addSpecialCommodity(@Param("po") List<SpecialCommodityPo> specialCommodityAddDtoList);

    /**
     * 获取特殊物品详细
     * @param id
     * @return
     */
    SpecialCommodityPo getSpecialCommodity(@Param("id") Long id);
}
