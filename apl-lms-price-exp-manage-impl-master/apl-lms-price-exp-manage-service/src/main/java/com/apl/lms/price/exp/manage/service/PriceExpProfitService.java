package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.apl.lib.utils.ResultUtil;

import java.util.List;

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
    Long saveProfit(PriceExpProfitPo priceExpProfitPo);


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
    ResultUtil<List<PriceExpProfitPo>> getList(Long priceId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);


}