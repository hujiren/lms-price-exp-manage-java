package com.apl.lms.price.exp.lib.feign;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.lib.feign.impl.PriceExpFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hjr start
 * @Classname PriceExpFeign
 * @Date 2020/8/19 10:35
 */
@Component
@FeignClient(value = "apl-lms-price-exp-manage-app" , fallback = PriceExpFeignImpl.class)
public interface PriceExpFeign {

    /**
     * 添加特殊物品缓存
     * @param keys
     * @param minKey
     * @param maxKey
     * @return
     */
    @PostMapping("/cache/add-special-commodity-cache")
    ResultUtil<Boolean> addSpecialCommodityCache(@RequestParam("keys") String keys,
                                               @RequestParam("minKey") Long minKey,
                                               @RequestParam("maxKey") Long maxKey);


    /**
     * 添加附加费缓存
     * @param keys
     * @param minKey
     * @param maxKey
     * @return
     */
    @PostMapping("/cache/add-surcharge-cache")
    ResultUtil<Boolean> addSurchargeCache(@RequestParam("keys") String keys,
                                                 @RequestParam("minKey") Long minKey,
                                                 @RequestParam("maxKey") Long maxKey);

    /**
     * 添加计泡方式缓存
     * @param keys
     * @param minKey
     * @param maxKey
     * @return
     */
    @PostMapping("/cache/add-weight-way-cache")
    ResultUtil<Boolean> addWeightWayCache(@RequestParam("keys") String keys,
                                          @RequestParam("minKey") Long minKey,
                                          @RequestParam("maxKey") Long maxKey);

}
