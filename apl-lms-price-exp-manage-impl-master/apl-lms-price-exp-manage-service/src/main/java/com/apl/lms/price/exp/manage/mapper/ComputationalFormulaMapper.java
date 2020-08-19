package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.ComputationalFormulaKeyDto;
import com.apl.lms.price.exp.pojo.po.ComputationalFormulaPo;
import com.apl.lms.price.exp.pojo.vo.ComputationalFormulaVo;
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
public interface ComputationalFormulaMapper extends BaseMapper<ComputationalFormulaPo> {

    /**
     * 获取燃油费分页信息列表
     * @param page
     * @return
     */
    List<ComputationalFormulaVo> getList(Page<ComputationalFormulaVo> page, @Param("key") ComputationalFormulaKeyDto computationalFormulaKeyDto);

    /**
     * 根据Id删除计算公式
     * @param id
     * @return
     */
    Integer delById(@Param("id") Long id);

    /**
     * 更新计算公式
     * @param computationalFormulaPo
     * @return
     */
    Integer updComputationalFormula(@Param("po") ComputationalFormulaPo computationalFormulaPo);

    /**
     * 新增计算公式
     * @param computationalFormulaPo
     * @return
     */
    Integer addComputationalFormula(@Param("po") ComputationalFormulaPo computationalFormulaPo);

    /**
     * 获取计算公式详细
     * @param id
     * @return
     */
    ComputationalFormulaVo getComputationalFormula(@Param("id") Long id);
}
