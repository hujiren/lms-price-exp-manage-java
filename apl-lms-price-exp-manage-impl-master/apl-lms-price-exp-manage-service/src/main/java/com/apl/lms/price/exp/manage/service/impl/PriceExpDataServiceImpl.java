package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lms.price.exp.manage.mapper.PriceExpDataMapper;
import com.apl.lms.price.exp.manage.service.PriceExpDataService;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpDataServiceImpl extends ServiceImpl<PriceExpDataMapper, PriceExpDataPo> implements PriceExpDataService {

    @Override
    public Integer deleteByPriceExpMainId(Long priceExpMainId) {
        return baseMapper.deleteByPriceExpMainId(priceExpMainId);
    }

    @Override
    public Boolean updateByPriceExpMainId(PriceExpDataPo priceExpDataPo) {

        Integer i = baseMapper.updateByPriceExpMainId(priceExpDataPo);
        return i > 0 ? true : false;
    }

    @Override
    public PriceExpDataVo getPriceExpDataInfoByMainId(Long id) {
        return baseMapper.getPriceExpDataInfoByMainId(id);
    }
}
