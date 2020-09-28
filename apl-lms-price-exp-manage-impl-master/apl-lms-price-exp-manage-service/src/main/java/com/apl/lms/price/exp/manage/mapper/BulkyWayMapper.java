package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.BulkyWayUpdDto;
import com.apl.lms.price.exp.pojo.po.BulkyWayPo;
import com.apl.lms.price.exp.pojo.dto.BulkyWayKeyDto;
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
public interface BulkyWayMapper extends BaseMapper<BulkyWayUpdDto> {

    /**
     * @Desc: 查找计泡方式列表
     * @Author:
     * @Date: 2020-08-08
     */
    List<BulkyWayUpdDto> getList(Page page, @Param("key") BulkyWayKeyDto bulkyWayKeyDto);

    /**
     * 批量新增计泡方式
     * @param bulkyWayPoList
     * @return
     */
    Integer add(@Param("po") List<BulkyWayPo> bulkyWayPoList);

    /**
     * 获取计泡方式详情
     * @param id
     * @return
     */
    BulkyWayUpdDto get(@Param("id") Long id);
}
