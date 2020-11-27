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
     * 根据价格表id查询
     * @param priceId
     * @return
     */
    @SqlParser(filter = true)
    List<PriceSurchargeVo> getByPriceId(Long priceId);

    /**
     * 根据价格表id批量查询附加费id
     * @param priceId
     * @return
     */
    @SqlParser(filter = true)
    List<Long> getIdBatchByPriceId(Long priceId);

    /**
     * 批量查询
     * @param priceId
     * @return
     */
    @SqlParser(filter = true)
    List<PriceSurchargePo> selectByPriceId(Long priceId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);
}