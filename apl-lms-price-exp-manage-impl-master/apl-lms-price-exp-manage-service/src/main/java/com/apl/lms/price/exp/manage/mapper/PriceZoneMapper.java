package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.PriceZoneNameKeyDto;
import com.apl.lms.price.exp.pojo.po.PriceZoneNamePo;
import com.apl.lms.price.exp.pojo.vo.PriceZoneNameVo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Mapper
@Repository
public interface PriceZoneMapper extends BaseMapper<PriceZoneNamePo> {

    /**
     * 获取快递分区名称列表
     * @param page
     * @return
     */
    @SqlParser(filter = true)
    List<PriceZoneNameVo> getPriceZoneNameList(Page<PriceZoneNameVo> page, @Param("dto") PriceZoneNameKeyDto priceZoneNameKeyDto);

    /**
     * 根据Id删除快递分区名称
     * @param ids
     * @return
     */
    Integer delPriceZoneName(@Param("ids") List<Long> ids);

    /**
     * 更新快递分区名称
     * @param priceZonePo
     * @return
     */
    Integer updPriceZoneName(@Param("po") PriceZoneNamePo priceZonePo);

    /**
     * 新增快递分区名称
     * @param priceZonePo
     * @return
     */
    Integer addPriceZoneName(@Param("po") PriceZoneNamePo priceZonePo);

}
