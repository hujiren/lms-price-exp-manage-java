package com.apl.lms.price.exp.manage.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.PriceExpCostMapper;
import com.apl.lms.price.exp.manage.service.PriceExpCostService;
import com.apl.lms.price.exp.pojo.dto.PriceExpCostAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpCostPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
     * @param priceExpMainId
     * @return
     */
    @Override
    public Long getPriceDataId(Long priceExpMainId) {
        return baseMapper.getPriceDataId(priceExpMainId);
    }

    /**
     * 通过主表id获取统计条数
     * @param priceMainId
     * @return
     */
    @Override
    public Integer getPriceDataIdCount(Long priceMainId) {
        return baseMapper.getPriceDataIdCount(priceMainId);
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
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Long id) {
        return baseMapper.deleteById(id);
    }
}
