package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.ComputationalFormulaMapper;
import com.apl.lms.price.exp.manage.service.ComputationalFormulaService;
import com.apl.lms.price.exp.pojo.po.PriceExpComputationalFormulaPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
        ID_IS_NOT_EXITS("ID_IS_NOT_EXITS","id不存在")
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
    public List<PriceExpComputationalFormulaPo> getList(Long priceId) {

        List<PriceExpComputationalFormulaPo> computationalFormulaVoList = baseMapper.getList(priceId);

        return computationalFormulaVoList;
    }

    /**
     * 根据id删除燃油费
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delComputationalFormula(Long id) {

        Integer integer = baseMapper.deleteById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg, false);
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

        if(null == priceExpComputationalFormulaPo.getZoneNum()){
            priceExpComputationalFormulaPo.setZoneNum("");
        }
        if(null == priceExpComputationalFormulaPo.getCountry()){
            priceExpComputationalFormulaPo.setCountry("");
        }
        if(null == priceExpComputationalFormulaPo.getStartWeight()){
            priceExpComputationalFormulaPo.setStartWeight(0.0);
        }
        if(null == priceExpComputationalFormulaPo.getEndWeight()){
            priceExpComputationalFormulaPo.setEndWeight(0.0);
        }
        if(null == priceExpComputationalFormulaPo.getPackageType()){
            priceExpComputationalFormulaPo.setPackageType("");
        }
        Integer integer = baseMapper.updateById(priceExpComputationalFormulaPo);
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
     * 批量删除
     */
    @Override
    public Integer delBatch(String ids) {
        Integer res = baseMapper.delBatch(ids);
        return res;
    }
}
