package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lms.price.exp.manage.mapper.PriceExpCostMapper;
import com.apl.lms.price.exp.manage.service.PriceExpCostService;
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
     * 根据主表id更新数据
     * @param priceExpCostPo
     * @return
     */
    @Override
    public Boolean updateByPriceExpMainId(PriceExpCostPo priceExpCostPo) {

        Integer i  = baseMapper.updateByPriceExpMainId(priceExpCostPo);

        return i > 0 ? true : false;

    }

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
     * 根据id删除数据
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Long id) {
        return baseMapper.deleteById(id);
    }
}
