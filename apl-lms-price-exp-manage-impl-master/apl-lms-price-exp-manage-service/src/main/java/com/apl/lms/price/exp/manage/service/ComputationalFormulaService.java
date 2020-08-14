package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.ComputationalFormulaPo;
import com.apl.lms.price.exp.pojo.vo.ComputationalFormulaVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface ComputationalFormulaService extends IService<ComputationalFormulaPo> {

    /**
     * 分页查询计算公式列表
     * @param pageDto
     * @param computationalFormulaKeyDto
     * @return
     */
    ResultUtil<Page<ComputationalFormulaVo>> getList(PageDto pageDto, ComputationalFormulaKeyDto computationalFormulaKeyDto);

    /**
     * 删除计算公式
     * @param id
     * @return
     */
    ResultUtil<Boolean> delComputationalFormula(Long id);

    /**
     * 更新计算公式
     * @param computationalFormulaDto
     * @return
     */
    ResultUtil<Boolean> updComputationalFormula(ComputationalFormulaDto computationalFormulaDto);

    /**
     * 新增计算公式
     * @param computationalFormulaInsertDto
     * @return
     */
    ResultUtil<Long> addComputationalFormula(ComputationalFormulaInsertDto computationalFormulaInsertDto);



}
