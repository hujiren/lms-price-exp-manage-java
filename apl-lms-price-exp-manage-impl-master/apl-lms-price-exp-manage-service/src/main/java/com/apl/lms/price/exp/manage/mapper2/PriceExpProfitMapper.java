package com.apl.lms.price.exp.manage.mapper2;

import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hjr
 * @since 2020-09-11
 */
@Repository
public interface PriceExpProfitMapper extends BaseMapper<PriceExpProfitPo> {


    /**
     * 新增
     * @param priceExpProfitPo
     * @return
     */

    Integer addProfit(@Param("po") PriceExpProfitPo priceExpProfitPo);

    /**
     * 更新
     * @param priceExpProfitPo
     * @return
     */
    Integer updProfit(@Param("po") PriceExpProfitPo priceExpProfitPo);

    /**
     * @Desc: 查找列表
     * @Author: ${cfg.author}
     * @Date: 2020-09-11
     */
    PriceExpProfitPo getProfit(@Param("priceId") Long priceId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(@Param("ids") String ids);

    /**
     * 获取最终利润
     * @param priceId
     * @return
     */
    PriceExpProfitPo getPriceFinalProfit(@Param("priceId") Long priceId);

    /**
     * 校验id是否存在
     * @param id
     * @return
     */
    Long exists(Long id);
}