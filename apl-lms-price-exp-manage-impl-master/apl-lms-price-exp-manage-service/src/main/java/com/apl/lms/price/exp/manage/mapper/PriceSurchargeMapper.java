package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceSurchargePo;
import com.apl.lms.price.exp.pojo.vo.PriceSurchargeVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

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
    List<PriceSurchargeVo> getByPriceId(Long priceId);
}