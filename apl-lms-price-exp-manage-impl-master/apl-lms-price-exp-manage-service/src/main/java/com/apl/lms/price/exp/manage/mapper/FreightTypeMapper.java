package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.FreightTypePo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hjr
 * @since 2020-10-07
 */
@Repository
public interface FreightTypeMapper extends BaseMapper<FreightTypePo> {


    /**
     * @Desc: 查找列表
     * @Author: ${cfg.author}
     * @Date: 2020-10-07
     */
    List<FreightTypePo> getList();

    /**
     * 批量添加
     * @param freightTypePoList
     * @return
     */
    Integer addBatch(@Param("freightTypePoList") List<FreightTypePo> freightTypePoList);

    /**
     * 根据租户id查找列表
     * @param innerOrgId
     * @return
     */
    @SqlParser(filter = true)
    List<FreightTypePo> getListByInnerOrgId(Long innerOrgId);
}