package com.apl.lms.price.exp.manage.service;


import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.po.PriceExpComputationalFormulaPo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface ComputationalFormulaService extends IService<PriceExpComputationalFormulaPo> {

    /**
     * 分页查询计算公式列表
     * @param priceId
     * @return
     */
    @SqlParser(filter = true)
    List<PriceExpComputationalFormulaPo> getList(Long priceId);

    /**
     * 删除计算公式
     * @param id
     * @return
     */
    ResultUtil<Boolean> delComputationalFormula(Long id);

    /**
     * 更新计算公式
     * @param priceExpComputationalFormulaPo
     * @return
     */
    ResultUtil<Boolean> updComputationalFormula(PriceExpComputationalFormulaPo priceExpComputationalFormulaPo);

    /**
     * 新增计算公式
     * @param priceExpComputationalFormulaPo
     * @return
     */
    ResultUtil<Long> addComputationalFormula(PriceExpComputationalFormulaPo priceExpComputationalFormulaPo);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);

    /**
     * 根据价格表Id获取计算公式id
     * @param priceId
     * @return
     */
    @SqlParser(filter = true)
    List<Long> getIdBatch(Long priceId);
}
