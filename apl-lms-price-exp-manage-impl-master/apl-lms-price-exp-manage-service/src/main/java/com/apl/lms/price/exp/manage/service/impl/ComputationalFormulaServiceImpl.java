package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.ComputationalFormulaMapper;
import com.apl.lms.price.exp.manage.service.ComputationalFormulaService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceExpComputationalFormulaPo;
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
public class ComputationalFormulaServiceImpl extends ServiceImpl<ComputationalFormulaMapper, PriceExpComputationalFormulaPo> implements ComputationalFormulaService {

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
     * 获取快递价格计算公式
     * @param priceId
     * @param
     * @return
     */
    @Override
    public ResultUtil<List<ComputationalFormulaVo>> getList(Long priceId) {

        List<ComputationalFormulaVo> computationalFormulaVoList = baseMapper.getList(priceId);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, computationalFormulaVoList);
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
     * @param priceExpComputationalFormulaPo
     * @return
     */
    @Override
    public ResultUtil<Boolean> updComputationalFormula(PriceExpComputationalFormulaPo priceExpComputationalFormulaPo) {


        ComputationalFormulaVo computationalFormulaVo = baseMapper.getComputationalFormula(priceExpComputationalFormulaPo.getId());
        if(computationalFormulaVo == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }

        Integer integer = baseMapper.updComputationalFormula(priceExpComputationalFormulaPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 新增燃油费
     * @param priceExpComputationalFormulaPo
     * @return
     */
    @Override
    public ResultUtil<Long> addComputationalFormula(PriceExpComputationalFormulaPo priceExpComputationalFormulaPo) {

        priceExpComputationalFormulaPo.setId(SnowflakeIdWorker.generateId());
        Integer integer = baseMapper.insert(priceExpComputationalFormulaPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpComputationalFormulaPo.getId());
    }

    /**
     * 获取计算公式详细
     * @param id
     * @return
     */
    @Override
    public ResultUtil<ComputationalFormulaVo> getComputationalFormula(Long id) {

        ComputationalFormulaVo computationalFormulaVo = baseMapper.getComputationalFormula(id);
        if(computationalFormulaVo == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, computationalFormulaVo);
    }
}
