package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.ExpListKeyDto;
import com.apl.lms.price.exp.pojo.po.ExpListPo;
import com.apl.lms.price.exp.pojo.vo.ExpListVo;
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
public interface ExpListMapper extends BaseMapper<ExpListPo> {

    /**
     * 获取快递价格分页信息列表
     * @param page
     * @param expListKeyDto
     * @return
     */
    List<ExpListVo> getExpList(Page page, @Param("key") ExpListKeyDto expListKeyDto);

    /**
     * 根据Id删除快递价格
     * @param id
     * @return
     */
    Integer delById(@Param("id") Long id);

    /**
     * 更新快递价格
     * @param expListPo
     * @return
     */
    Integer updExpList(@Param("po") ExpListPo expListPo);

    /**
     * 新增快递价格
     * @param expListPo
     * @return
     */
    Integer insertExpList(@Param("po") ExpListPo expListPo);
}
