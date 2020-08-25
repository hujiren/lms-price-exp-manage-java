package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @Classname PriceExpDataService
 * @Date 2020/8/19 17:30
 */
public interface PriceExpDataService extends IService<PriceExpDataPo> {

    /**
     * 根据价格主表删除数据
     * @param priceExpMainId
     * @return
     */
    Integer deleteByPriceExpMainId(Long priceExpMainId);

    /**
     * 根据主表id获取主数据信息
     * @param id
     * @return
     */
    PriceExpDataVo getPriceExpDataInfoByMainId(Long id);

    /**
     * 获取多租户id
     * @param id
     * @return
     */
    Long getInnerOrgId(Long id);

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    Boolean updatePriceExpData(PriceExpDataPo priceExpDataPo);
}
