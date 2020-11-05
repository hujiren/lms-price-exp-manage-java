package com.apl.lms.price.exp.manage.mapper2;

import com.apl.lms.price.exp.pojo.po.UnifyExpPricePo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hjr
 * @since 2020-09-11
 */
@Repository
public interface UnifyProfitMapper extends BaseMapper<UnifyExpPricePo> {


    List<UnifyExpPricePo> getUnifyProfit(Long customerGroupId);

    Integer updateUnifyProfit(@Param("po") UnifyExpPricePo unifyExpPricePo);

    Integer insertUnifyProfit(@Param("po") UnifyExpPricePo unifyExpPricePo);

    Integer deleteBatch(@Param("ids") List<Long> ids);
}