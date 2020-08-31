package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.dto.PriceZoneInsertKeyDto;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceZoneDataMapper
 * @Date 2020/8/31 15:17
 */
@Repository
public interface PriceZoneDataMapper extends BaseMapper<PriceZoneDataListVo> {

    /**
     * 获取详细
     * @param priceZoneInsertKeyDto
     * @return
     */
    List<PriceZoneDataListVo> getList(@Param("key") PriceZoneInsertKeyDto priceZoneInsertKeyDto);
}
