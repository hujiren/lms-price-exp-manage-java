package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lms.price.exp.manage.mapper.PriceExpDevelopInfoMapper;
import com.apl.lms.price.exp.manage.service.PriceExpDevelopInfoService;
import com.apl.lms.price.exp.pojo.po.PriceExpDevelopInfoPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpDevelopInfoServiceImpl extends ServiceImpl<PriceExpDevelopInfoMapper, PriceExpDevelopInfoPo> implements PriceExpDevelopInfoService {


    /**
     * 根据主表id更新数据
     * @param priceExpDevelopInfoPo
     * @return
     */
    @Override
    public Boolean updateByPriceExpMainId(PriceExpDevelopInfoPo priceExpDevelopInfoPo) {

        Integer i = baseMapper.updateByPriceExpMainId(priceExpDevelopInfoPo);
        return i > 0 ? true : false;
    }

    /**
     * 根据主表id获取详细信息
     * @param id
     * @return
     */
    @Override
    public PriceExpDevelopInfoPo getDevelopInfoByMainId(Long id) {
        return baseMapper.getDevelopInfoByMainId(id);
    }

    /**
     * 根据扩展表Id删除数据
     * @param id
     * @return
     */
    @Override
    public Integer deleteByPriceId(Long id) {
        return baseMapper.deleteByPriceId(id);
    }

}
