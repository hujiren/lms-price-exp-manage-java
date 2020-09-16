package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.ComputationalFormulaService;
import com.apl.lms.price.exp.pojo.po.PriceExpComputationalFormulaPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:09
 */
@RestController
@RequestMapping("/computational-formula")
@Validated
@Api(value = "计算公式",tags = "计算公式")
public class ComputationalFormulaController {

    @Autowired
    ComputationalFormulaService computationalFormulaService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "获取报价计算公式列表" , notes = "")
    @ApiImplicitParam(name = "priceId",value = "价格表id",required = true  , paramType = "query")
    public ResultUtil<List<PriceExpComputationalFormulaPo>> getList(@NotNull @Min(value = 1, message = "id不能小于1") Long priceId){

        return computationalFormulaService.getList(priceId);
    }

    @PostMapping(value = "/del")
    @ApiOperation(value =  "删除" , notes = "根据id删除")
    @ApiImplicitParam(name = "id",value = "计算公式id",required = true  , paramType = "query")
    public ResultUtil<Boolean> del(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return computationalFormulaService.delComputationalFormula(id);
    }

    @PostMapping(value = "/upd")
    @ApiOperation(value =  "更新" , notes = "根据id更新计算公式")
    public ResultUtil<Boolean> upd(@Validated PriceExpComputationalFormulaPo computationalFormulaUpdDto){

        return computationalFormulaService.updComputationalFormula(computationalFormulaUpdDto);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value =  "新增计算公式" , notes = "新增计算公式")
    public ResultUtil<Long> add(@Validated PriceExpComputationalFormulaPo computationalFormulaAddDto){

        return computationalFormulaService.addComputationalFormula(computationalFormulaAddDto);
    }



}
