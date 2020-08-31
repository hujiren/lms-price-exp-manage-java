package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lms.price.exp.manage.mapper.PriceExpDataMapper;
import com.apl.lms.price.exp.manage.service.PriceExpDataService;
import com.apl.lms.price.exp.pojo.dto.PriceExpDataAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpDataServiceImpl extends ServiceImpl<PriceExpDataMapper, PriceExpDataPo> implements PriceExpDataService {

    @Override
    public Integer deleteByPriceExpMainId(List<Long> priceExpMainIds) {
        return baseMapper.deleteByMainIds(priceExpMainIds);
    }

    /**
     * 获取详细
     * @param id
     * @return
     */
    @Override
    public PriceExpDataVo getPriceExpDataInfoByMainId(Long id) {
        return baseMapper.getPriceExpDataInfoByMainId(id);
    }


    @Override
    public Long getInnerOrgId(Long id) {
        return baseMapper.getInnerOrgId(id);
    }

    /**
     * 保存价格表数据
     * @param priceMainId
     * @param priceExpDataAddDto
     * @return
     */
    @Override
    public Boolean addPriceExpData(Long priceMainId, PriceExpDataAddDto priceExpDataAddDto) {

        PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
        priceExpDataPo.setPriceData(priceExpDataAddDto.getPriceData());
        priceExpDataPo.setPriceMainId(priceMainId);
        Integer saveSuccess = baseMapper.insertData(priceExpDataPo);

        return saveSuccess > 0 ? true :false;
    }

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    @Override
    public Boolean updateByMainId(PriceExpDataPo priceExpDataPo) {
        Integer integer = baseMapper.updateByMainId(priceExpDataPo);
        return integer > 0 ? true : false;
    }
}
