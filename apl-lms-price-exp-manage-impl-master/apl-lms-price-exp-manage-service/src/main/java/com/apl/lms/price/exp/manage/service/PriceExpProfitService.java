package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * <p>
 * service接口
 * </p>
 *
 * @author hjr
 * @since 2020-09-11
 */
public interface PriceExpProfitService extends IService<PriceExpProfitPo> {


    /**
     * @Desc: 根据id 更新一个PriceExpProfitPo 实体
     * @author hjr
     * @since 2020-09-11
     */
    ResultUtil<Long> saveProfit(PriceExpProfitPo priceExpProfitPo) throws JsonProcessingException;


    /**
     * @Desc: 根据id 查找一个PriceExpProfitPo 实体
     * @author hjr
     * @since 2020-09-11
     */
    ResultUtil<Boolean> delById(Long id);


    /**
     * @Desc: 分页查找 PriceExpProfitPo 列表
     * @author hjr
     * @since 2020-09-11
     */
    PriceExpProfitPo getProfit(Long priceId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);

    /**
     * 获取引用租户利润数据
     * @param quotePriceId
     * @return
     */
    PriceExpProfitPo getTenantProfit(Long quotePriceId);


}