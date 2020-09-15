package com.apl.lms.price.exp.manage.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpSaleMapper;
import com.apl.lms.price.exp.manage.service.PriceExpSaleService;
import com.apl.lms.price.exp.pojo.dto.PriceExpCostAddDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpSaleAddDto;
import com.apl.lms.price.exp.pojo.entity.RelevanceForMainData;
import com.apl.lms.price.exp.pojo.entity.PriceListForDelBatch;
import com.apl.lms.price.exp.pojo.po.PriceExpSalePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpSaleServiceImpl extends ServiceImpl<PriceExpSaleMapper, PriceExpSalePo> implements PriceExpSaleService {

    /**
     * 获取销售价格表的详细信息
     * @param id
     * @return
     */
    @Override
    public PriceExpSaleVo getPriceExpSaleInfoById(Long id) {
        return baseMapper.getPriceExpSaleInfoById(id);
    }

    /**
     * 根据主表id获取统计数量
     * @param ids
     * @return
     */
    @Override
    public Integer getPriceDataIdCount(List<Long> ids) {
        return baseMapper.getPriceDataIdCount(ids);
    }

    /**
     * 根据id得到主表Id和price_data_id
     * @param ids
     * @return
     */
    @Override
    public RelevanceForMainData getPriceDataIds(List<Long> ids) {
        return baseMapper.getPriceDataIds(ids);
    }

    /**
     * 根据id删除数据
     * @param ids
     * @return
     */
    @Override
    public Integer deleteById(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids);
    }

    /**
     * 保存销售价
     * @param priceExpCostAddDto
     * @param priceExpSaleAddDto
     * @param priceMainId
     * @param saleId
     * @return
     */
    @Override
    public Boolean addPriceExpSale(PriceExpCostAddDto priceExpCostAddDto, PriceExpSaleAddDto priceExpSaleAddDto, Long priceMainId, Long saleId) {
        PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
        BeanUtil.copyProperties(priceExpSaleAddDto, priceExpSalePo);
        priceExpSalePo.setId(saleId);
        priceExpSalePo.setQuotePriceId(0L);
        priceExpSalePo.setPriceCode(priceExpCostAddDto.getPriceCode());
        priceExpSalePo.setPriceName(priceExpCostAddDto.getPriceName());
        priceExpSalePo.setPriceStatus(1);
        priceExpSalePo.setPriceMainId(priceMainId);
        priceExpSalePo.setChannelCategory(priceExpCostAddDto.getChannelCategory());
//        Boolean saveSuccess = priceExpSalePo.insert();
        Integer integer = baseMapper.addPriceExpSale(priceExpSalePo);
        if(integer<1){
            return false;
        }
        return true;
    }

    /**
     * 更新
     * @param priceExpSalePo
     * @return
     */
    @Override
    public Boolean updateSaleById(PriceExpSalePo priceExpSalePo) {
        Integer integer = baseMapper.updateSaleById(priceExpSalePo);
        return integer > 0 ? true : false;
    }

    /**
     * 获取主表id
     * @param id
     * @return
     */
    @Override
    public Long getPriceDataId(Long id) {
        return baseMapper.getPriceDataId(id);
    }

    /**
     * 添加引用销售价
     * @param priceExpSalePo
     * @return
     */
    @Override
    public Long addReferenceSale(PriceExpSalePo priceExpSalePo) {
        Integer integer = baseMapper.addPriceExpSale(priceExpSalePo);
        return integer > 0 ? priceExpSalePo.getId() : 0L;
    }

    /**
     * 构建批量删除销售价格表条件集合
     * @param ids
     * @return
     */
    @Override
    public List<PriceListForDelBatch> getPriceListForDel(List<Long> ids) {

        List<PriceListForDelBatch> saleDataList = baseMapper.getPriceListForDel(ids);
        return null;
    }
}
