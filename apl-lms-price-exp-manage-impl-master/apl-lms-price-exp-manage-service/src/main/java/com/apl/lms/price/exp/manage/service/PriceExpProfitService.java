package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.dto.PriceExpProfitAssembleDto;
import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpProfitListVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpProfitInfoVo;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitKeyDto;
import com.baomidou.mybatisplus.extension.service.IService;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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
     * @Desc: 添加一个PriceExpProfitPo实体
     * @author hjr
     * @since 2020-09-11
     */
    ResultUtil<Long> add(PriceExpProfitPo priceExpProfitPo);


    /**
     * @Desc: 根据id 更新一个PriceExpProfitPo 实体
     * @author hjr
     * @since 2020-09-11
     */
    ResultUtil<Boolean> updById(PriceExpProfitPo priceExpProfitPo);


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
    ResultUtil<PriceExpProfitListVo> getList(Long priceId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);

}