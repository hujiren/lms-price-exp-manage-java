package com.apl.lms.price.exp.manage.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpCostMapper;
import com.apl.lms.price.exp.manage.service.PriceExpCostService;
import com.apl.lms.price.exp.pojo.dto.PriceExpCostAddDto;
import com.apl.lms.price.exp.pojo.entity.PriceListForDelBatch;
import com.apl.lms.price.exp.pojo.entity.RelevanceForMainData;
import com.apl.lms.price.exp.pojo.po.PriceExpCostPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpCostServiceImpl extends ServiceImpl<PriceExpCostMapper, PriceExpCostPo> implements PriceExpCostService {


    /**
     * 根据主表id获取成本价格详情
     * @param id
     * @return
     */
    @Override
    public PriceExpCostVo getPriceExpCostInfo(Long id) {
        return baseMapper.getPriceExpCostInfo(id);
    }

    /**
     * 根据id获取主表id
     * @param ids
     * @return
     */
    @Override
    public RelevanceForMainData getPriceDataIds(List<Long> ids) {
        return baseMapper.getPriceDataIds(ids);
    }

    /**
     * 通过主表id获取统计条数
     * @param priceMainIds
     * @return
     */
    @Override
    public Integer getPriceDataIdCount(List<Long> priceMainIds) {
        return baseMapper.getPriceDataIdCount(priceMainIds);
    }


    /**
     * 保存成本价格数据
     * @param priceMainId
     * @param priceExpCostAddDto
     * @return
     */
    @Override
    public Boolean addPriceExpCost(Long priceMainId, PriceExpCostAddDto priceExpCostAddDto, Long costId) {

        PriceExpCostPo priceExpCostPo = new PriceExpCostPo();
        BeanUtil.copyProperties(priceExpCostAddDto, priceExpCostPo);
        priceExpCostPo.setId(costId);
        priceExpCostPo.setPriceStatus(1);
        priceExpCostPo.setQuotePriceId(0l);
        priceExpCostPo.setPriceMainId(priceMainId);
        priceExpCostPo.setPriceCode(priceExpCostAddDto.getPriceCode());
        priceExpCostPo.setPriceName(priceExpCostAddDto.getPriceName());
        priceExpCostPo.setChannelCategory(priceExpCostAddDto.getChannelCategory());
        Boolean insertSuccess = priceExpCostPo.insert();
        return insertSuccess;
    }

    /**
     * 根据id删除
     * @param ids
     * @return
     */
    @Override
    public Integer deleteById(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids);
    }

    /**
     * 获取主表Id
     * @param id
     * @return
     */
    @Override
    public Long getPriceDataId(Long id) {
        return baseMapper.getPriceDataId(id);
    }

    @Override
    public Long addReferenceCost(PriceExpCostPo priceExpCostPo) {
        boolean insert = priceExpCostPo.insert();
        return insert ? priceExpCostPo.getId() : 0L;
    }

    /**
     * 组装批量删除条件集合
     * @param priceIdList
     * @return
     */
    @Override
    public List<PriceListForDelBatch> getPriceListForDel(List<Long> priceIdList) {
        List<PriceListForDelBatch> dataList = baseMapper.getPriceListForDel(priceIdList);
        return dataList;
    }
}
