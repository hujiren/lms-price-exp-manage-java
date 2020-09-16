package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpDataMapper;
import com.apl.lms.price.exp.manage.service.PriceExpCostService;
import com.apl.lms.price.exp.manage.service.PriceExpDataService;
import com.apl.lms.price.exp.manage.service.PriceExpSaleService;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpDataAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpDataServiceImpl extends ServiceImpl<PriceExpDataMapper, PriceExpDataPo> implements PriceExpDataService {

    @Autowired
    PriceExpCostService priceExpCostService;

    @Autowired
    PriceExpSaleService priceExpSaleService;

    @Override
    public Integer deleteBatchById(List<Long> ids) {
        return baseMapper.deleteBatchById(ids);
    }

    /**
     * 根据价格表Id获取详细
     * @param priceId
     * @return
     */
    @Override
    public ResultUtil<PriceExpDataVo> getPriceExpDataInfoByPriceId(Long priceId) {

        //判断是成本表还是销售表
        Long mainId = priceExpCostService.getPriceDataId(priceId);
        Long mainId2 = priceExpSaleService.getPriceDataId(priceId);
        PriceExpDataVo priceExpDataVo = baseMapper.getPriceExpDataInfoById(priceId);
        if (priceExpDataVo == null) {
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpDataVo);
    }



    /**
     * 保存价格表数据
     * @param priceDataId
     * @return
     */
    @Override
    public Boolean addPriceExpData(Long priceDataId, PriceExpAddDto priceExpAddDto) {

        PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
        priceExpDataPo.setPriceData(priceExpAddDto.getPriceData());
        priceExpDataPo.setId(priceDataId);
        Integer saveSuccess = baseMapper.insertData(priceExpDataPo);
        return saveSuccess > 0 ? true :false;
    }

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    @Override
    public Boolean updById(PriceExpDataPo priceExpDataPo) {
        Integer integer = baseMapper.updById(priceExpDataPo);
        return integer > 0 ? true : false;
    }

    /**
     * 批量删除
     */
    @Override
    public Integer delBatch(String ids) {
        return baseMapper.delBatch(ids);
    }
}
