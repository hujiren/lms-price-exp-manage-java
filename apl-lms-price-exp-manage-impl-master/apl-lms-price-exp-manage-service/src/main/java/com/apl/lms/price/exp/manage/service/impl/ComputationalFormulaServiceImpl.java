package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.ComputationalFormulaMapper;
import com.apl.lms.price.exp.manage.service.ComputationalFormulaService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.ComputationalFormulaPo;
import com.apl.lms.price.exp.pojo.vo.ComputationalFormulaVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Service
@Slf4j
public class ComputationalFormulaServiceImpl extends ServiceImpl<ComputationalFormulaMapper, ComputationalFormulaPo> implements ComputationalFormulaService {

    enum ExpListServiceCode {
        ;

        private String code;
        private String msg;

        ExpListServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 分页查询快递价格
     * @param pageDto
     * @param
     * @return
     */
    @Override
    public ResultUtil<Page<ComputationalFormulaVo>> getList(PageDto pageDto, ComputationalFormulaKeyDto computationalFormulaKeyDto) {
        
        Page<ComputationalFormulaVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<ComputationalFormulaVo> computationalFormulaVoList = baseMapper.getList(page, computationalFormulaKeyDto);

        page.setRecords(computationalFormulaVoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据id删除燃油费
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delComputationalFormula(Long id) {

        Integer integer = baseMapper.delById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新燃油费
     * @param
     * @return
     */
    @Override
    public ResultUtil<Boolean> updComputationalFormula(ComputationalFormulaDto computationalFormulaDto) {

        ComputationalFormulaPo computationalFormulaPo = new ComputationalFormulaPo();
        BeanUtils.copyProperties(computationalFormulaDto, computationalFormulaPo);
        Integer integer = baseMapper.updComputationalFormula(computationalFormulaPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 新增燃油费
     * @param computationalFormulaInsertDto
     * @return
     */
    @Override
    public ResultUtil<Long> addComputationalFormula(ComputationalFormulaInsertDto computationalFormulaInsertDto) {

        ComputationalFormulaPo computationalFormulaPo = new ComputationalFormulaPo();
        BeanUtils.copyProperties(computationalFormulaInsertDto, computationalFormulaPo);
        computationalFormulaPo.setId(SnowflakeIdWorker.generateId());
        Integer integer = baseMapper.addComputationalFormula(computationalFormulaPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, computationalFormulaPo.getId());
    }
}
