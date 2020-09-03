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

    @PostMapping("/add-partner-cache")
    @ApiOperation(value = "添加服务商缓存")
    public ResultUtil<Boolean> addPartnerCache(@RequestParam("keys") String keys, Long minKey, Long maxKey){

        return cacheService.addPartnerCache(keys, minKey, maxKey);
    }
}
