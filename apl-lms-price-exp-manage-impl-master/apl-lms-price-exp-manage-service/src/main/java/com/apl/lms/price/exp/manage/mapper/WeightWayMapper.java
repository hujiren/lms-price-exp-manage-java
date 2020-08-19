package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.WeightWayDto;
import com.apl.lms.price.exp.pojo.dto.WeightWayInsertDto;
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
public interface WeightWayMapper extends BaseMapper<WeightWayDto> {

    /**
     * @Desc: 查找计泡方式列表
     * @Author:
     * @Date: 2020-08-08
     */
    List<WeightWayDto> getList(Page page, @Param("key") WeightWayKeyDto weightWayKeyDto);

    /**
     * 根据Id删除计泡方式
     * @param id
     * @return
     */
    Integer delById(@Param("id") Long id);

    /**
     * 更新计泡方式
     * @param weightWayDto
     * @return
     */
    Integer updWeightWay(@Param("po") WeightWayDto weightWayDto);

    /**
     * 批量新增计泡方式
     * @param weightWayInsertDtoList
     * @return
     */
    Integer addWeightWay(@Param("po") List<WeightWayInsertDto> weightWayInsertDtoList);

    /**
     * 获取计泡方式详情
     * @param id
     * @return
     */
    WeightWayDto getWeightWay(@Param("id") Long id);
}
