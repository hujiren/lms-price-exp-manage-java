package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceIncreaseProfitPo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceIncreaseProfitMapper
 * @Date 2020/11/14 10:19
 */
@Repository
public interface PriceIncreaseProfitMapper extends BaseMapper<PriceIncreaseProfitPo> {

    /**
     * 根据价格表id获取增加的利润列表
     * @param priceId
     * @return
     */
    @SqlParser(filter = true)
    List<PriceIncreaseProfitPo> getList(Long priceId);

    /**
     * 获取详细信息
     * @param increaseId
     * @return
     */
    PriceIncreaseProfitPo getIncreaseInfo(Long increaseId);
}
