package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpProfitListVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpProfitInfoVo;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitKeyDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * 查询价格表Id
     * @param priceId
     * @return
     */
    Integer getPriceId(@Param("id") Long priceId);

    /**
     * @Desc: 查找列表
     * @Author: ${cfg.author}
     * @Date: 2020-09-11
     */
    List<PriceExpProfitListVo> getList(@Param("variable")Integer variable, @Param("priceId") Long priceId);
}