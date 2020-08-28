package com.apl.lms.price.exp.manage.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpAxisMapper;
import com.apl.lms.price.exp.manage.service.PriceExpAxisService;
import com.apl.lms.price.exp.pojo.dto.PriceExpAxisAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpAxisServiceImpl extends ServiceImpl<PriceExpAxisMapper, PriceExpAxisPo> implements PriceExpAxisService {

    /**
     * 根据主表id删除数据
     * @param priceExpMainId
     * @return
     */
    @Override
    public Integer deleteByPriceExpMainId(Long priceExpMainId) {
        return baseMapper.deleteByPriceExpMainId(priceExpMainId);
    }

    /**
     * 根据主表id获取详细数据
     * @param id
     * @return
     */
    @Override
    public PriceExpAxisPo getPriceExpAxisInfoByMainId(Long id) {
        return baseMapper.getPriceExpAxisInfoByMainId(id);

    }


    /**
     * 保存价格表轴数据
     * @param priceMainId
     * @param priceExpAxisAddDto
     * @return
     */
    @Override
    public Boolean addPriceExpAxis(Long priceMainId, PriceExpAxisAddDto priceExpAxisAddDto) {
        PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
        BeanUtil.copyProperties(priceExpAxisAddDto, priceExpAxisPo);
        priceExpAxisPo.setPriceMainId(priceMainId);
        Integer saveSuccess = baseMapper.insertAxis(priceExpAxisPo);
        return saveSuccess > 0 ? true : false;
    }
}
