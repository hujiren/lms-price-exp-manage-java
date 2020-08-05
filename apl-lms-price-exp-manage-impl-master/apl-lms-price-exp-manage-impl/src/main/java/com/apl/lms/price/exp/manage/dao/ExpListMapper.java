package com.apl.lms.price.exp.manage.dao;
import com.apl.lms.price.exp.manage.pojo.dto.ExpListKeyDto;
import com.apl.lms.price.exp.manage.pojo.po.ExpListPo;
import com.apl.lms.price.exp.manage.pojo.vo.ExpListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Mapper
@Repository
public interface ExpListMapper extends BaseMapper<ExpListPo> {

    /**
     * 获取快递价格分页信息列表
     * @param page
     * @param expListKeyDto
     * @return
     */
    List<ExpListVo> getExpList(Page page, @Param("today") Timestamp timestamp, @Param("key") ExpListKeyDto expListKeyDto);

    /**
     * 根据Id删除
     * @param id
     * @return
     */
    Integer delById(@Param("id") Long id);

    /**
     * 修改
     * @param expListPo
     * @return
     */
    Integer updExpList(@Param("po") ExpListPo expListPo);

    /**
     * 插入
     * @param expListPo
     * @return
     */
    Integer insertExpList(@Param("po") ExpListPo expListPo);
}
