package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;

/**
 * @author hjr start
 * @date 2020-08-18 17:33
 */
public interface CacheService {

    ResultUtil<Boolean> addSpecialCommodityCache(String keys, Long minKey, Long maxKey);

    ResultUtil<Boolean> addSurchargeCache(String keys, Long minKey, Long maxKey);

    ResultUtil<Boolean> addWeightWayCache(String keys, Long minKey, Long maxKey);

    ResultUtil<Boolean> addPartnerCache(String keys, Long minKey, Long maxKey);
}
