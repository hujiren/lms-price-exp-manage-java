package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.WeightWayUpdDto;
import com.apl.lms.price.exp.pojo.dto.WeightWayAddDto;
import com.apl.lms.price.exp.pojo.dto.WeightWayKeyDto;
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
public interface WeightWayMapper extends BaseMapper<WeightWayUpdDto> {

    /**
     * @Desc: 查找计泡方式列表
     * @Author:
     * @Date: 2020-08-08
     */
    List<WeightWayUpdDto> getList(Page page, @Param("key") WeightWayKeyDto weightWayKeyDto);

    /**
     * 批量新增计泡方式
     * @param weightWayAddDtoList
     * @return
     */
    Integer addWeightWay(@Param("po") List<WeightWayAddDto> weightWayAddDtoList);

    /**
     * 获取计泡方式详情
     * @param id
     * @return
     */
    WeightWayUpdDto getWeightWay(@Param("id") Long id);
}
