package com.apl.lms.price.exp.manage.service.impl;

import com.apl.cache.AplCacheHelper;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.lib.cache.bo.PartnerCacheBo;
import com.apl.lms.price.exp.manage.mapper.CacheMapper;
import com.apl.lms.price.exp.manage.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author hjr start
 * @date 2020-08-18 17:33
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    AplCacheHelper aplCacheHelper;

    @Autowired
    CacheMapper cacheMapper;

    /**
     * 添加服务商缓存
     * @param keys
     * @param minKey
     * @param maxKey
     * @return
     * @throws IOException
     */
    @Override
    public ResultUtil<Boolean> addPartnerCache(String keys, Long minKey, Long maxKey) throws IOException {

        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        Map<String, PartnerCacheBo> maps = cacheMapper.addPartnerCache(keys, minKey, maxKey, securityUser.getInnerOrgId());

        if(null != maps && maps.size()>0) {
            aplCacheHelper.opsForValue("priceManage").set(maps);
            return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, true);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_FAIL, false);
    }

}
