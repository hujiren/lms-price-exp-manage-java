package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.lib.cache.bo.PartnerCacheBo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.Map;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Repository
@Mapper
public interface CacheMapper extends BaseMapper {

    /**
     * 添加服务商缓存
     * @param keys
     * @param minKey
     * @param maxKey
     * @param innerOrgId
     * @return
     */
    @MapKey("cacheKey")
    Map<String, PartnerCacheBo> addPartnerCache(@Param("keys") String keys,
                                                @Param("minKey") Long minKey,
                                                @Param("maxKey") Long maxKey,
                                                @Param("innerOrgId") Long innerOrgId);
}
