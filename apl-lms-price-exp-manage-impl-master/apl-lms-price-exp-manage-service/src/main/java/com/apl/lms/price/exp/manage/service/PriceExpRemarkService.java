package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
    ResultUtil<PriceExpRemarkPo> getPriceExpRemark(Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);
}
