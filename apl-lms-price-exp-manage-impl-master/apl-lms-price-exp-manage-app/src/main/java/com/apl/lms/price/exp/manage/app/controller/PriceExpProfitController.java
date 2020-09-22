package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lms.price.exp.manage.service.PriceExpProfitService;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitAssembleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.apl.lms.price.exp.pojo.po.PriceExpProfitPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpProfitListVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpProfitInfoVo;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitKeyDto;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.validate.ApiParamValidate;

import javax.validation.constraints.Min;
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
    public ResultUtil<Long> save(@Validated @RequestBody PriceExpProfitPo priceExpProfitPo) {
        ApiParamValidate.validate(priceExpProfitPo);

        return priceExpProfitService.saveProfit(priceExpProfitPo);
    }


    @PostMapping(value = "/get-list")
    @ApiOperation(value = "获取利润数据列表", notes = "获取利润数据列表")
    @ApiImplicitParam(name = "priceId", value = " 价格表id", required = true, paramType = "query")
    public ResultUtil<PriceExpProfitListVo> getList(@NotNull(message = "价格表id不能为空") Long priceId) {

        return priceExpProfitService.getList(priceId);
    }


}
