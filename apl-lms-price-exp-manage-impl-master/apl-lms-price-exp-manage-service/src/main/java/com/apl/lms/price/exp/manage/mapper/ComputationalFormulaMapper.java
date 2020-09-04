package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceExpComputationalFormulaPo;
import com.apl.lms.price.exp.pojo.vo.ComputationalFormulaVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ComputationalFormulaMapper extends BaseMapper<PriceExpComputationalFormulaPo> {

    /**
     * 获取快递价格计算公式
     * @param priceId
     * @return
     */
    List<ComputationalFormulaVo> getList(Long priceId);

    /**
     * 根据Id删除计算公式
     * @param id
     * @return
     */
    Integer delById(@Param("id") Long id);

    /**
     * 更新计算公式
     * @param priceExpComputationalFormulaPo
     * @return
     */
    Integer updComputationalFormula(@Param("po") PriceExpComputationalFormulaPo priceExpComputationalFormulaPo);

    /**
     * 获取计算公式详细
     * @param id
     * @return
     */
    ComputationalFormulaVo getComputationalFormula(@Param("id") Long id);
}
