package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.lib.cache.bo.SpecialCommodityCacheBo;
import com.apl.lms.price.exp.lib.cache.bo.SurchargeCacheBo;
import com.apl.lms.price.exp.lib.cache.bo.WeightWayCacheBo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Repository
public interface CacheMapper extends BaseMapper {

    //添加特殊物品缓存
    @MapKey("cacheKey")
    Map<String, SpecialCommodityCacheBo> addSpecialCommodityCache(@Param("key") String keys,
                                                                  @Param("minKey") Long minKey,
                                                                  @Param("maxKey") Long maxKey,
                                                                  @Param("innerOrgId") Long innerOrgId);

    //添加附加费缓存
    @MapKey("cacheKey")
    Map<String, SurchargeCacheBo> addSurchargeCache(@Param("key") String keys,
                                                    @Param("minKey") Long minKey,
                                                    @Param("maxKey") Long maxKey,
                                                    @Param("innerOrgId") Long innerOrgId);

    //添加计泡方式缓存
    @MapKey("cacheKey")
    Map<String, WeightWayCacheBo> addWeightWayCache(@Param("key") String keys,
                                                    @Param("minKey") Long minKey,
                                                    @Param("maxKey") Long maxKey,
                                                    @Param("innerOrgId") Long innerOrgId);
}
