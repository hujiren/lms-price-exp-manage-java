package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.IncreaseProfitDto;
import com.apl.lms.price.exp.pojo.po.PriceIncreaseProfitPo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceIncreaseProfitService
 * @Date 2020/11/14 10:15
 */
public interface PriceIncreaseProfitService extends IService<PriceIncreaseProfitPo> {

    /**
     * 根据价格表id获取增加的利润的列表
     * @param priceId
     * @return
     */
    ResultUtil<IncreaseProfitDto> getList(Long priceId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    ResultUtil<Boolean> deleteBatch(List<Long> ids);

    /**
     * 批量保存 新增或修改
     * @param increaseProfitDto
     * @return
     */
    ResultUtil<Boolean> saveBatchIncreaseProfit(IncreaseProfitDto increaseProfitDto) throws Exception;
}
