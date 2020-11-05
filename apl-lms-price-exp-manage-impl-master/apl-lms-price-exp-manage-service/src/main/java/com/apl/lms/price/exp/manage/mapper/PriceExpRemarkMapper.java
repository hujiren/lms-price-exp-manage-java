package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author hjr start
 * @Classname PriceExpRemarkMapper
 * @Date 2020/8/19 18:12
 */
@Repository
public interface PriceExpRemarkMapper extends BaseMapper<PriceExpRemarkPo> {
    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(@Param("ids") String ids);

    /**
     * 获取详细
     * @param id
     * @return
     */
    PriceExpRemarkPo getById(@Param("id") Long id);

    /**
     * 更新
     * @param priceExpRemarkPo
     * @return
     */
    Integer updById(@Param("po") PriceExpRemarkPo priceExpRemarkPo);

    /**
     * 检查id是否存在
     * @param id
     * @return
     */
    Long exists(Long id);

    /**
     * 批量获取
     * @param ids
     * @return
     */
    @MapKey(value = "id")
    Map<Long, PriceExpRemarkPo> selectBatch(@Param("ids") List<Long> ids);

    /**
     * 获取引用租户的remark
     * @param quotePriceId
     * @return
     */
    @SqlParser(filter = true)
    PriceExpRemarkPo getTenantPriceRemark(Long quotePriceId);
}
