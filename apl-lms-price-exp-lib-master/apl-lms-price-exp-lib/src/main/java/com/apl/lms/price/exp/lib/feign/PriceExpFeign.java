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
     * 添加服务商缓存
     * @param keys
     * @param minKey
     * @param maxKey
     * @return
     */
    @PostMapping("/cache/add-partner-cache")
    ResultUtil<Boolean> addPartnerCache(@RequestParam("keys") String keys,
                                          @RequestParam("minKey") Long minKey,
                                          @RequestParam("maxKey") Long maxKey);

}
