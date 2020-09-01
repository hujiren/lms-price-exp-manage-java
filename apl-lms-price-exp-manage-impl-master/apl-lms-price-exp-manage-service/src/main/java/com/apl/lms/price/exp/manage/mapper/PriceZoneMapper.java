package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.dto.PriceZoneNameKeyDto;
import com.apl.lms.price.exp.pojo.po.PriceZonePo;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.apl.lms.price.exp.pojo.vo.PriceZoneVo;
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
public interface PriceZoneMapper extends BaseMapper<PriceZonePo> {

    /**
     * 获取燃油费分页信息列表
     * @param page
     * @return
     */
    List<PriceZoneVo> getPriceZoneNameList(Page<PriceZoneVo> page, @Param("dto") PriceZoneNameKeyDto priceZoneNameKeyDto);

    /**
     * 根据Id删除快递分区
     * @param ids
     * @return
     */
    Integer delPriceZoneName(@Param("ids") List<Long> ids);

    /**
     * 更新快递分区
     * @param priceZonePo
     * @return
     */
    Integer updPriceZoneName(@Param("po") PriceZonePo priceZonePo);

    /**
     * 新增快递分区
     * @param priceZonePo
     * @return
     */
    Integer addPriceZoneName(@Param("po") PriceZonePo priceZonePo);

}
