package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.FuelChargeService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.vo.FuelChargeVo;
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
import java.io.IOException;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:09
 */
@RestController
@RequestMapping("/fuel-charge")
@Validated
@Api(value = "燃油费",tags = "燃油费")
public class FuelChargeController {

    @Autowired
    FuelChargeService fuelChargeService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "获取燃油费列表" , notes = "获取燃油费列表")
    public ResultUtil<List<FuelChargeVo>> getList(){

        return fuelChargeService.getList();
    }

    @PostMapping(value = "/del")
    @ApiOperation(value =  "删除" , notes = "根据id删除")
    @ApiImplicitParam(name = "id",value = "燃油费Id",required = true  , paramType = "query")
    public ResultUtil<Boolean> del(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id) throws IOException {

        return fuelChargeService.delFuelCharge(id);
    }

    @PostMapping(value = "/upd")
    @ApiOperation(value =  "更新" , notes = "根据id更新燃油费")
    public ResultUtil<Boolean> upd( @Validated FuelChargeAddDto fuelChargeAddDto) throws IOException {

        return fuelChargeService.updFuelCharge(fuelChargeAddDto);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value =  "添加" , notes = "添加燃油费")
    public ResultUtil<String> add( @Validated FuelChargeAddDto fuelChargeAddDto){

        return fuelChargeService.addFulCharge(fuelChargeAddDto);
    }
}
