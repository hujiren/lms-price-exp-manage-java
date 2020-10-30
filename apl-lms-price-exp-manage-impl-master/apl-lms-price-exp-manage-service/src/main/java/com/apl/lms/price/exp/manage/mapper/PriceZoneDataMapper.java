package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
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
     * @param id
     * @return
     */
    @SqlParser(filter = true)
    List<PriceZoneDataListVo> getList(@Param("id") Long id);

    /**
     * 根据分区表id批量删除
     * @param ids
     * @return
     */
    Integer deleteByZoneId(@Param("ids") List<Long> ids);

    /**
     * 根据分区表Id批量删除
     * @param ids
     * @return
     */
    Integer delBatchByZoneId(@Param("ids") List<Long> ids);

    /**
     * 根据zoneId批量查询数据
     * @param ids
     * @return
     */
    List<PriceZoneDataListVo> getListByZoneIds(@Param("ids")HashSet<Long> ids);
}
