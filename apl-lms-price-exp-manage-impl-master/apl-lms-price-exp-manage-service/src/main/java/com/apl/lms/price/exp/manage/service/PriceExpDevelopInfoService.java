package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.po.PriceExpDevelopInfoPo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @Classname PriceExpDevelopInfoService
 * @Date 2020/8/19 18:10
 */
public interface PriceExpDevelopInfoService extends IService<PriceExpDevelopInfoPo> {


    //根据主表Id更新数据
    Boolean updateByPriceExpMainId(PriceExpDevelopInfoPo priceExpDevelopInfoPo);

    //根据主表id获取详细信息
    PriceExpDevelopInfoPo getDevelopInfoByMainId(Long id);

    /**
     * 根据关联表id删除数据
     * @param id
     * @return
     */
    Integer deleteByPriceId(Long id);

}
