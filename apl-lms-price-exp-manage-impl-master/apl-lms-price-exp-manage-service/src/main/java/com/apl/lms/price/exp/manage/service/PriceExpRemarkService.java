package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @Classname PriceExpRemarkService
 * @Date 2020/8/19 18:10
 */
public interface PriceExpRemarkService extends IService<PriceExpRemarkPo> {


    /**
     * 根据关联表id删除数据
     * @param id
     * @return
     */
    Integer deleteById(Long id);

    /**
     * 根据Id获取详情
     * @param id
     * @return
     */
    PriceExpRemarkPo getDevelopInfoById(Long id);
}
