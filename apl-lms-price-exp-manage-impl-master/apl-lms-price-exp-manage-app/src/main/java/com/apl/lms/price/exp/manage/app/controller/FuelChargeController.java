package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.pojo.dto.ChannelCateGoryDto;
import com.apl.lms.price.exp.manage.pojo.dto.ChannelCateGoryKeyDto;
import com.apl.lms.price.exp.manage.pojo.dto.FuelChargeDto;
import com.apl.lms.price.exp.manage.pojo.dto.FuelChargeKeyDto;
import com.apl.lms.price.exp.manage.pojo.vo.ChannelCateGoryVo;
import com.apl.lms.price.exp.manage.pojo.vo.FuelChargeVo;
import com.apl.lms.price.exp.manage.service.ChannelCategoryService;
import com.apl.lms.price.exp.manage.service.FuelChargeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    @ApiOperation(value =  "分页获取燃油费列表" , notes = "查询燃油费")
    @ApiImplicitParam(name = "fuelChargeKeyDto",value = "燃油费查询对象",required = true  , paramType = "query")
    public ResultUtil<Page<FuelChargeVo>> getList(PageDto pageDto ,
                                                  @NotNull(message = "燃油费对象不能为空") @Validated FuelChargeKeyDto fuelChargeKeyDto){
        return fuelChargeService.getList(pageDto, fuelChargeKeyDto);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "删除" , notes = "根据id删除燃油费")
    @ApiImplicitParam(name = "id",value = "燃油费id",required = true  , paramType = "query")
    public ResultUtil<Boolean> delete(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return fuelChargeService.delFuelCharge(id);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value =  "更新" , notes = "根据id更新燃油费")
    @ApiImplicitParam(name = "fuelChargeDto", value = "燃油费更新对象", required = true, paramType = "query")
    public ResultUtil<Boolean> update(@NotNull(message = "燃油费对象不能为空") @Validated FuelChargeDto fuelChargeDto){

        return fuelChargeService.updFuelCharge(fuelChargeDto);
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value =  "新增燃油费" , notes = "新增燃油费")
    @ApiImplicitParam(name = "fuelChargeDto", value = "燃油费持久化对象", required = true, paramType = "query")
    public ResultUtil<Long> insert(@NotNull(message = "燃油费对象不能为空") @Validated FuelChargeDto fuelChargeDto){

        return fuelChargeService.insFuelCharge(fuelChargeDto);
    }
}
