package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceSurchargePo;
import com.apl.lms.price.exp.pojo.vo.PriceSurchargeVo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hjr
 * @since 2020-09-28
 */
@Repository
public interface PriceSurchargeMapper extends BaseMapper<PriceSurchargePo> {

    /**
     * 获取详细
     * @param priceId
     * @return
     */
    @SqlParser(filter = true)
    List<PriceSurchargeVo> getByPriceId(Long priceId);

    @SqlParser(filter = true)
    List<Long> getIdBatchByPriceId(Long priceId);

    @SqlParser(filter = true)
    List<PriceSurchargePo> selectByPriceId(Long priceId);

    Integer delBatch(String ids);
}