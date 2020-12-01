package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.PriceIncreaseProfitService;
import com.apl.lms.price.exp.pojo.dto.IncreaseProfitDto;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceIncreaseProfitController
 * @Date 2020/11/14 10:13
 */
@RestController
@Slf4j
@RequestMapping("/increase-profit")
@Api(value = "增加的利润", tags = "增加的利润")
public class PriceIncreaseProfitController {

    @Autowired
    PriceIncreaseProfitService priceIncreaseProfitService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "获取增加的利润列表" , notes = "获取增加的利润列表")
    @ApiImplicitParam(name = "priceId", value = "价格表id", required = true, paramType = "query")
    public ResultUtil<IncreaseProfitDto> getList(Long priceId){

        return priceIncreaseProfitService.getList(priceId);
    }

    @PostMapping(value = "/delete-batch")
    @ApiOperation(value =  "批量删除" , notes = "根据id批量删除")
    @ApiImplicitParam(name = "priceId", value = "价格表id", required = true, paramType = "query")
    public ResultUtil<Boolean> del(@NotEmpty(message = "id不能为空") @RequestBody List<Long> increaseIds, Long priceId) throws IOException {

        return priceIncreaseProfitService.deleteBatch(increaseIds, priceId);
    }

    @PostMapping(value = "/save-batch")
    @ApiOperation(value =  "批量保存" , notes = "批量保存")
    public ResultUtil<Boolean> saveBatch(@Validated @RequestBody IncreaseProfitDto increaseProfitDto) throws Exception {

        return priceIncreaseProfitService.saveBatchIncreaseProfit(increaseProfitDto);
    }
}
