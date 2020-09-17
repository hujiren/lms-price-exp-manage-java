package com.apl.lms.price.exp.manage.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.apl.lms.price.exp.manage.dao.PriceExpSaleDao;
import com.apl.lms.price.exp.manage.mapper.PriceExpSaleMapper;
import com.apl.lms.price.exp.manage.service.PriceExpSaleService;
import com.apl.lms.price.exp.pojo.bo.PriceListForDelBatchBo;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddBaseDto;
import com.apl.lms.price.exp.pojo.po.PriceExpSalePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleVo;
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
public class PriceExpSaleServiceImpl extends ServiceImpl<PriceExpSaleMapper, PriceExpSalePo> implements PriceExpSaleService {


    @Autowired
    PriceExpSaleDao priceExpSaleDao;

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
     * @param priceMainId
     * @param salePriceId
     * @return
     */
    @Override
    public Boolean addPriceExpSale(PriceExpAddBaseDto priceExpAddDto, Long salePriceId, Long priceMainId, Long quotePriceId) {

        priceExpSaleDao.createRealTable();

        PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
        BeanUtil.copyProperties(priceExpAddDto, priceExpSalePo);
        priceExpSalePo.setId(salePriceId);
        priceExpSalePo.setQuotePriceId(quotePriceId);
        priceExpSalePo.setPriceCode(priceExpAddDto.getPriceCode());
        priceExpSalePo.setPriceName(priceExpAddDto.getPriceName());
        priceExpSalePo.setPriceStatus(1);
        priceExpSalePo.setPriceMainId(priceMainId);
        priceExpSalePo.setChannelCategory(priceExpAddDto.getChannelCategory());
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
     * 构建批量删除销售价格表条件集合
     * @param ids
     * @return
     */
    @Override
    public List<PriceListForDelBatchBo> getPriceListForDel(List<Long> ids) {

        List<PriceListForDelBatchBo> saleDataList = baseMapper.getPriceListForDel(ids);
        return saleDataList;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public Integer delBatch(String ids) {
        Integer res = baseMapper.delBatch(ids);
        return res;
    }

    /**
     * 获取主表id
     * @param id
     * @return
     */
    @Override
    public Long getMainId(Long id) {
        return baseMapper.getMainId(id);
    }
}
