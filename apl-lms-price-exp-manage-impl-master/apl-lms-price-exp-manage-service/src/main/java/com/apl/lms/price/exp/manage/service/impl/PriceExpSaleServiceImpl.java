package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lms.price.exp.manage.mapper.PriceExpSaleMapper;
import com.apl.lms.price.exp.manage.service.PriceExpSaleService;
import com.apl.lms.price.exp.pojo.po.PriceExpSalePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpSaleServiceImpl extends ServiceImpl<PriceExpSaleMapper, PriceExpSalePo> implements PriceExpSaleService {

    /**
     * 根据主表id更新数据
     */
    @Override
    public Boolean updateByPriceExpMainId(PriceExpSalePo priceExpSalePo) {
        Integer i = baseMapper.updateByPriceExpMainId(priceExpSalePo);
        return i > 0 ? true : false;
    }

    /**
     * 获取销售价格表的详细信息
     * @param id
     * @return
     */
    @Override
    public PriceExpSaleVo getPriceExpSaleInfoByMainId(Long id) {
        return baseMapper.getPriceExpSaleInfoByMainId(id);
    }

    /**
     * 根据主表id获取统计数量
     * @param priceExpMainId
     * @return
     */
    @Override
    public Integer getPriceDataIdCount(Long priceExpMainId) {
        return baseMapper.getPriceDataIdCount(priceExpMainId);
    }

    /**
     * 根据id得到主表Id
     * @param id
     * @return
     */
    @Override
    public Long getPriceDataId(Long id) {
        return baseMapper.getPriceDataId(id);
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
