package com.apl.lms.price.exp.manage.service.impl;

import com.apl.cache.AplCacheHelper;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lib.utils.StringUtil;
import com.apl.lms.price.exp.manage.mapper.ComputationalFormulaMapper;
import com.apl.lms.price.exp.manage.service.ComputationalFormulaService;
import com.apl.lms.price.exp.pojo.po.PriceExpComputationalFormulaPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    AplCacheHelper aplCacheHelper;

    /**
     * 根据价格表Id查询计算公式列表
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
     * 根据id删除计算公式
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delComputationalFormula(Long id, Long priceId) throws IOException {
            baseMapper.deleteById(id);
            aplCacheHelper.opsForKey("exp-price-formula").patternDel(priceId);
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新计算公式
     * @param priceExpComputationalFormulaPo
     * @return
     */
    @Override
    public ResultUtil<Boolean> updComputationalFormula(PriceExpComputationalFormulaPo priceExpComputationalFormulaPo) throws IOException {

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
        Integer resultNum = baseMapper.updateById(priceExpComputationalFormulaPo);
        if(resultNum < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }

        aplCacheHelper.opsForKey("exp-price-formula").patternDel(priceExpComputationalFormulaPo.getPriceId());

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 新增计算公式
     * @param priceExpComputationalFormulaPo
     * @return
     */
    @Override
    public ResultUtil<Long> addComputationalFormula(PriceExpComputationalFormulaPo priceExpComputationalFormulaPo) {

        priceExpComputationalFormulaPo.setId(SnowflakeIdWorker.generateId());
        Integer resultNum = baseMapper.insert(priceExpComputationalFormulaPo);
        if(resultNum < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpComputationalFormulaPo.getId());
    }

    /**
     * 根据价格表Id批量删除计算公式
     */
    @Override
    public Integer delBatch(String priceIds) throws IOException {
        Integer resultNum = baseMapper.delBatch(priceIds);
        List<Long> idList = StringUtil.stringToLongList(priceIds);
        aplCacheHelper.opsForKey("exp-price-formula").patternDel(idList);
        return resultNum;
    }

    /**
     * 获取引用租户的计算公式
     * @param quotePriceId
     * @return
     */
    @Override
    public List<PriceExpComputationalFormulaPo> getTenantComputationalFormula(Long quotePriceId) {
        return baseMapper.getTenantComputationalFormula(quotePriceId);
    }

    /**
     * 根据价格表Id获取计算公式id
     * @param priceId
     * @return
     */
    @Override
    public List<Long> getIdBatch(Long priceId) {
        List<Long> computationIds = baseMapper.getIdBatchByPriceId(priceId);
        return computationIds;
    }
}
