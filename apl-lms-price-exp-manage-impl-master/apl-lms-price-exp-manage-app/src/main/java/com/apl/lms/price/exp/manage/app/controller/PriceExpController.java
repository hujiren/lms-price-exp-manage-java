package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.PriceExpAxisService;
import com.apl.lms.price.exp.manage.service.PriceExpDataService;
import com.apl.lms.price.exp.manage.service.PriceExpRemarkService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:26
 */
@RestController
@RequestMapping("/exp-list")
@Validated
@Api(value = "快递价格",tags = "快递价格")
public class PriceExpController {

    @Autowired
    PriceExpService priceExpService;

    @Autowired
    PriceExpAxisService priceExpAxisService;

    @Autowired
    PriceExpDataService priceExpDataService;

    @Autowired
    PriceExpRemarkService priceExpRemarkService;

    @PostMapping(value = "/get-sale-list")
    @ApiOperation(value = "分页获取销售价格列表", notes = "分页获取销售价格列表")
    public ResultUtil<Page<PriceExpSaleListVo>> getSaleList(PageDto pageDto, @Validated PriceExpSaleListKeyDto priceExpSaleListKeyDto) {

        priceExpSaleListKeyDto.setPriceType(1);

        return priceExpService.getPriceExpSaleList(pageDto, priceExpSaleListKeyDto);
    }

    @PostMapping(value = "/get-customer-list")
    @ApiOperation(value = "分页获取客户价格列表", notes = "分页获取客户价格列表")
    public ResultUtil<Page<PriceExpSaleListVo>> getCustomerList(PageDto pageDto, @Validated PriceExpSaleListKeyDto priceExpSaleListKeyDto) {

        priceExpSaleListKeyDto.setPriceType(2);

        return priceExpService.getPriceExpSaleList(pageDto, priceExpSaleListKeyDto);
    }

    @PostMapping(value = "/get-cost-list")
    @ApiOperation(value = "分页获取成本价格列表", notes = "分页获取成本价格列表")
    public ResultUtil<Page<PriceExpCostListVo>> getCostList(PageDto pageDto, @Validated PriceExpCostKeyDto priceExpCostListKeyDto) {

        return priceExpService.getPriceExpCostList(pageDto, priceExpCostListKeyDto);
    }

    @PostMapping(value = "/get-published-price-list")
    @ApiOperation(value = "获取公布价列表", notes = "获取公布价")
    public ResultUtil<Page<PriceExpCostListVo>> getPublishedPriceList(PageDto pageDto, @Validated PriceExpPublishedKeyDto keyDto) {

        return priceExpService.getPublishedPriceList(pageDto, keyDto);
    }

    @PostMapping(value = "/get-sale-info")
    @ApiOperation(value = "获取销售价格详情", notes = "获取销售价格详情")
    @ApiImplicitParam(name = "id", value = "销售价格表id", required = true, paramType = "query")
    public ResultUtil<PriceExpSaleInfoVo> getPriceExpSaleInfo(@NotNull(message = "销售id不能为空") @Min(value = 1, message = "id不能小于1") Long id) throws Exception {

        return priceExpService.getPriceExpSaleInfo(id);
    }

    @PostMapping(value = "/get-cost-info")
    @ApiOperation(value = "获取成本价格详情", notes = "获取成本价格详情")
    @ApiImplicitParam(name = "id", value = "成本价格表id", required = true, paramType = "query")
    public ResultUtil<PriceExpCostInfoVo> getPriceExpCostInfo(@NotNull(message = "成本id不能为空") @Min(value = 1, message = "id不能小于1") Long id) throws Exception {

        return priceExpService.getPriceExpCostInfo(id);
    }

    @PostMapping(value = "/get-price-axis")
    @ApiOperation(value = "获取数据轴", notes = "获取数据轴")
    @ApiImplicitParam(name = "id", value = "轴数据id", required = true, paramType = "query")
    public ResultUtil<PriceExpAxisVo> getPriceExpAxis(@NotNull(message = "轴数据id不能为空") @Min(value = 1, message = "id不能小于1") Long id) {

        return priceExpAxisService.getAxisInfoById(id);
    }

    @PostMapping(value = "/get-price-data")
    @ApiOperation(value = "获取价格表数据", notes = "获取价格表数据")
    @ApiImplicitParam(name = "id", value = "价格表Id", required = true, paramType = "query")
    public ResultUtil<PriceExpDataVo> getPriceExpData(@NotNull(message = "价格表Id不能为空") @Min(value = 1, message = "id不能小于1") Long id) {
        return priceExpDataService.getPriceExpDataInfoByPriceId(id);
    }

    @PostMapping(value = "/get-price-remark")
    @ApiOperation(value = "获取备注信息", notes = "获取备注信息")
    @ApiImplicitParam(name = "id", value = "价格表Id", required = true, paramType = "query")
    public ResultUtil<PriceExpRemarkPo> getPriceExpRemark(@NotNull(message = "价格表Id不能为空") @Min(value = 1, message = "id不能小于1") Long id) {
        return priceExpRemarkService.getPriceExpRemark(id);
    }

    @PostMapping(value = "/add-price")
    @ApiOperation(value = "新增快递价格", notes = "新增快递价格", consumes = "application/json")
    public ResultUtil<Long> addPrice(@Validated @RequestBody PriceExpAddDto priceExpAddDto){

        return priceExpService.addExpPrice(priceExpAddDto);
    }

    @PostMapping(value = "/upd-sale-price")
    @ApiOperation(value = "更新销售价格表", notes = "根据Id修改销售价格表")
    public ResultUtil<Boolean> updateSalePrice(@RequestBody @Validated PriceExpSaleUpdDto priceExpSaleUpdDto) {
        return priceExpService.updateSalePrice(priceExpSaleUpdDto);
    }

    @PostMapping(value = "/upd-cost-price")
    @ApiOperation(value = "更新成本价格表", notes = "根据Id修改成本价格表")
    public ResultUtil<Boolean> updateCostPrice(@Validated @RequestBody PriceExpCostUpdDto priceExpCostUpdDto) {

        return priceExpService.updateCostPrice(priceExpCostUpdDto);
    }

    @PostMapping(value = "/upd-remark")
    @ApiOperation(value = "更新备注", notes = "根据Id更新备注")
    public ResultUtil<Boolean> updRemark(@Validated PriceExpRemarkPo priceExpRemarkPo) {

        return priceExpRemarkService.updateRemark(priceExpRemarkPo);
    }

    @PostMapping(value = "/upd-price-data")
    @ApiOperation(value = "更新数据表数据", notes = "更新数据表数据")
    public ResultUtil<Boolean> updatePriceData(@Validated @RequestBody PriceExpDataAddDto priceExpDataAddDto){
        return priceExpService.updatePriceData(priceExpDataAddDto);
    }

    @PostMapping(value = "/delete-cost-batch")
    @ApiOperation(value = "批量删除成本价格表", notes = "根据Id批量删除成本价格表")
    @ApiImplicitParam(name = "isDelSaleAndCost", value = "是否同时删除销售价和成本价", required = true, paramType = "query")
    public ResultUtil<Boolean> deleteCostPrice(@NotEmpty(message = "id不能为空") @RequestParam("ids") ArrayList<Long> ids, Integer isDelSaleAndCost){
        Boolean delSaleAndCost = false;
        if(isDelSaleAndCost == 1){
            delSaleAndCost = true;
        }
        Integer priceType = 2;
        return priceExpService.deletePriceBatch(ids, priceType, delSaleAndCost);
    }

    @PostMapping(value = "/delete-sale-batch")
    @ApiOperation(value = "批量删除销售价格表", notes = "根据Id批量删除销售价格表")
    @ApiImplicitParam(name = "id", value = "价格表Id", required = true, paramType = "query")
    public ResultUtil<Boolean> deleteSalePrice(@NotEmpty(message = "销售价格表id不能为空") @RequestParam("ids") List<Long> ids, Integer isDelSaleAndCost){
        Boolean delSaleAndCost = false;
        if(isDelSaleAndCost == 1){
            delSaleAndCost = true;
        }
        Integer priceType = 1;
        return priceExpService.deletePriceBatch(ids, priceType, delSaleAndCost);
    }

    @PostMapping(value = "/reference-price")
    @ApiOperation(value = "引用价格表", notes = "引用价格表")
    public ResultUtil<Long> referencePrice(@RequestBody @Validated ReferencePriceDto referencePriceDto){

        return priceExpService.referencePrice(referencePriceDto);
    }
}

