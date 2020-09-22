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


    @PostMapping(value = "/add")
    @ApiOperation(value = "添加", notes = "添加利润")
    public ResultUtil<Long> add(@Validated @RequestBody PriceExpProfitPo priceExpProfitPo) {
        ApiParamValidate.validate(priceExpProfitPo);

        return priceExpProfitService.add(priceExpProfitPo);
    }


    @PostMapping(value = "/upd")
    @ApiOperation(value = "更新", notes = "更新利润")
    public ResultUtil<Boolean> updById(@Validated @RequestBody PriceExpProfitPo priceExpProfitPo) {

        return priceExpProfitService.updById(priceExpProfitPo);
    }


    @PostMapping(value = "/del")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParam(name = "id", value = " id", required = true, paramType = "query")
    public ResultUtil<Boolean> delById(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id) {

        return priceExpProfitService.delById(id);
    }


    @PostMapping(value = "/get-list")
    @ApiOperation(value = "获取利润数据列表", notes = "获取利润数据列表")
    @ApiImplicitParam(name = "priceId", value = " 价格表id", required = true, paramType = "query")
    public ResultUtil<PriceExpProfitListVo> getList(@NotNull(message = "价格表id不能为空") Long priceId) {

        return priceExpProfitService.getList(priceId);
    }


//    @PostMapping(value = "/merge-profit")
//    @ApiOperation(value = "合并利润", notes = "合并利润")
//    public ResultUtil<PriceExpProfitListVo> mergeProfit(@Validated @RequestBody ) {
//
//        return priceExpProfitService.mergeProfit(priceExpProfitAssembleDto);
//    }
}
