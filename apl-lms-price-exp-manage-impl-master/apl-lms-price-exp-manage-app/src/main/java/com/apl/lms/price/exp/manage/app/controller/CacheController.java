package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hjr start
 * @date 2020-08-18 17:33
 */
@RestController
@RequestMapping("/cache")
@Api(value = "缓存",tags = "缓存")
@Validated
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping("/add-special-commodity-cache")
    @ApiOperation(value = "添加特殊物品缓存")
    public ResultUtil<Boolean> addSpecialCommodityCache(@RequestParam("keys") String keys, Long minKey, Long maxKey){

        return cacheService.addSpecialCommodityCache(keys, minKey, maxKey);
    }

    @PostMapping("/add-surcharge-cache")
    @ApiOperation(value = "添加附加费缓存")
    public ResultUtil<Boolean> addSurchargeCache(@RequestParam("keys") String keys, Long minKey, Long maxKey){

        return cacheService.addSurchargeCache(keys, minKey, maxKey);
    }

    @PostMapping("/add-weight-way-cache")
    @ApiOperation(value = "添加计泡方式缓存")
    public ResultUtil<Boolean> addWeightWayCache(@RequestParam("keys") String keys, Long minKey, Long maxKey){

        return cacheService.addWeightWayCache(keys, minKey, maxKey);
    }
}
