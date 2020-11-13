package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
     * @Desc: 查找利润
     * @Author: ${cfg.author}
     * @Date: 2020-09-11
     */
    @SqlParser(filter = true)
    PriceExpProfitPo getProfit(@Param("priceId") Long priceId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(@Param("ids") String ids);

    /**
     * 校验id是否存在
     * @param id
     * @return
     */
    Long exists(Long id);



}