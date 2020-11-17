package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.ExpPriceProfitDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;

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
    ResultUtil<Long> saveProfit(ExpPriceProfitDto expPriceProfitDto) throws JsonProcessingException;


    /**
     * @Desc: 根据id 查找一个PriceExpProfitPo 实体
     * @author hjr
     * @since 2020-09-11
     */
    ResultUtil<Boolean> delById(Long id);


    /**
     * @Desc: 获取利润
     * @author hjr
     * @since 2020-09-11
     */
    ExpPriceProfitDto getProfit(Long priceId, Long customerGroupId, Integer addProfitWay, Long tenantId);


    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);

    /**
     * 合并利润, 组装成销售利润
     * @param profit
     * @return
     */
    List<PriceExpProfitDto> assembleSaleProfit(ExpPriceProfitDto profit);
}