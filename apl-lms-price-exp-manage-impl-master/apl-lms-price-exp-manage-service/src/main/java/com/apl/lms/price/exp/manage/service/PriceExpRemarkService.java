package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author hjr start
 * @Classname PriceExpRemarkService
 * @Date 2020/8/19 18:10
 */
public interface PriceExpRemarkService extends IService<PriceExpRemarkPo> {



    /**
     * 更新
     * @param priceExpRemarkPo
     * @return
     */
    Boolean updateRemark(PriceExpRemarkPo priceExpRemarkPo);

    /**
     * 获取详情
     * @param id
     * @return
     */
    PriceExpRemarkPo getPriceExpRemark(Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);

    /**
     * 批量获取
     * @param ids
     * @return
     */
    Map<Long, PriceExpRemarkPo> getPriceExpRemarkBatch(List<Long> ids);

    /**
     * 获取引用租户的备注
     * @param quotePriceId
     * @return
     */
    PriceExpRemarkPo getTenantPriceRemark(Long quotePriceId);
}
