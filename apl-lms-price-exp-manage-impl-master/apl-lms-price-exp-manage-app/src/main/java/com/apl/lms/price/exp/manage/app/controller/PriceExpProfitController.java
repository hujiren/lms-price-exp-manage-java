package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.validate.ApiParamValidate;
import com.apl.lms.price.exp.manage.service.PriceExpProfitService;
import com.apl.lms.price.exp.pojo.dto.ExpPriceProfitSaveDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author hjr
 * @since 2020-09-11
 */
@RestController
@RequestMapping(value = "/price_exp_profit")
@Validated
@Api(value = "报价利润", tags = "报价利润")
@Slf4j
public class PriceExpProfitController {

    @Autowired
    public PriceExpProfitService priceExpProfitService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", notes = "保存")
    public ResultUtil<Long> save(@Validated @RequestBody ExpPriceProfitSaveDto expPriceProfitSaveDto) throws JsonProcessingException {
        ApiParamValidate.validate(expPriceProfitSaveDto);
        ResultUtil<Long> longResultUtil = priceExpProfitService.saveProfit(expPriceProfitSaveDto);
        return longResultUtil;

    }

    @PostMapping(value = "/get")
    @ApiOperation(value = "获取利润数据", notes = "获取利润数据")
    @ApiImplicitParam(name = "priceId", value = " 价格表id", required = true, paramType = "query")
    public ResultUtil<ExpPriceProfitSaveDto> get(@NotNull(message = "价格表id不能为空") Long priceId){
        ExpPriceProfitSaveDto profit = priceExpProfitService.getProfit(priceId, 0L);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS ,profit);
    }

}
