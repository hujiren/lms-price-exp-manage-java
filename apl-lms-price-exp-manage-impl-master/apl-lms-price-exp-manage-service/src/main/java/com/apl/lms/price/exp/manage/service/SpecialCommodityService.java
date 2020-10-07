package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.po.SpecialCommodityPo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 航空公司 service接口
 * </p>
 *
 * @author hjr
 * @since 2020-08-08
 */
public interface SpecialCommodityService extends IService<SpecialCommodityPo> {

    /**
     * @Desc: 分页查找 特殊物品 列表
     * @author hjr
     * @since 2020-08-08
     */
    List<SpecialCommodityPo> getList();

    /**
     * @Desc: 根据Id删除特殊物品
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Boolean> delSpecialCommodity(Long id);

    /**
     * @Desc: 批量新增特殊物品
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Integer> addSpecialCommodity(List<SpecialCommodityPo> specialCommodityPoList);
}
