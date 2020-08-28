package com.apl.lms.price.exp.manage.service;
import com.apl.lms.price.exp.pojo.dto.PriceExpAxisAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @Classname PriceExpDataService
 * @Date 2020/8/19 17:30
 */
public interface PriceExpAxisService extends IService<PriceExpAxisPo> {

    /**
     * 根据主表id删除数据
     * @param priceExpMainId
     * @return
     */
    Integer deleteByPriceExpMainId(Long priceExpMainId);

    /**
     * 根据主表id获取详细数据
     * @param id
     * @return
     */
    PriceExpAxisPo getPriceExpAxisInfoByMainId(Long id);


    /**
     * 保存价格表轴数据
     * @param priceMainId
     * @param priceExpAxisAddDto
     * @return
     */
    Boolean addPriceExpAxis(Long priceMainId, PriceExpAxisAddDto priceExpAxisAddDto);
}
