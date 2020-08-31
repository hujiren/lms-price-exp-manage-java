package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lms.price.exp.manage.mapper.PriceExpRemarkMapper;
import com.apl.lms.price.exp.manage.service.PriceExpRemarkService;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpRemarkServiceImpl extends ServiceImpl<PriceExpRemarkMapper, PriceExpRemarkPo> implements PriceExpRemarkService {


    /**
     * 根据扩展表Id删除数据
     * @param ids
     * @return
     */
    @Override
    public Integer deleteById(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    @Override
    public PriceExpRemarkPo getDevelopInfoById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    @Override
    public PriceExpRemarkPo getPriceExpRemark(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 更新
     * @param priceExpRemarkPo
     * @return
     */
    @Override
    public Integer updateRemark(PriceExpRemarkPo priceExpRemarkPo) {
        return baseMapper.updateById(priceExpRemarkPo);
    }
}
