package com.apl.lms.price.exp.manage.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpCostMapper;
import com.apl.lms.price.exp.manage.service.PriceExpCostService;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddBaseDto;
import com.apl.lms.price.exp.pojo.entity.PriceListForDelBatch;
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
     * 保存成本价格数据
     * @param priceMainId
     * @param priceExpAddDto
     * @return
     */
    @Override
    public Boolean addPriceExpCost(PriceExpAddBaseDto priceExpAddDto, Long costPriceId, Long priceMainId, Long quotePriceId) {

        PriceExpCostPo priceExpCostPo = new PriceExpCostPo();
        BeanUtil.copyProperties(priceExpAddDto, priceExpCostPo);
        priceExpCostPo.setId(costPriceId);
        priceExpCostPo.setPriceStatus(1);
        priceExpCostPo.setQuotePriceId(quotePriceId);
        priceExpCostPo.setPriceMainId(priceMainId);
        priceExpCostPo.setPriceCode(priceExpAddDto.getPriceCode());
        priceExpCostPo.setPriceName(priceExpAddDto.getPriceName());
        priceExpCostPo.setChannelCategory(priceExpAddDto.getChannelCategory());
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
     * 组装批量删除条件集合
     * @param priceIdList
     * @return
     */
    @Override
    public List<PriceListForDelBatch> getPriceListForDel(List<Long> priceIdList) {
        List<PriceListForDelBatch> dataList = baseMapper.getPriceListForDel(priceIdList);
        return dataList;
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

}
