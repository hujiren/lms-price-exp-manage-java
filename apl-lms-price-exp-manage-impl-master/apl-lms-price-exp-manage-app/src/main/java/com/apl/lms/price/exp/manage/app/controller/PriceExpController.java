package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.validate.ApiParamValidate;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostInfoVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostListVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleInfoVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    @PostMapping(value = "/get-sale-list")
    @ApiOperation(value = "分页获取销售价格列表", notes = "分页获取销售价格列表")
    public ResultUtil<Page<PriceExpSaleListVo>> getSaleList(PageDto pageDto, @Validated PriceExpSaleListKeyDto priceExpSaleListKeyDto) {

        return priceExpService.getPriceExpSaleList(pageDto, priceExpSaleListKeyDto);
    }

    @PostMapping(value = "/get-cost-list")
    @ApiOperation(value = "分页获取成本价格列表", notes = "分页获取成本价格列表")
    public ResultUtil<Page<PriceExpCostListVo>> getCostList(PageDto pageDto, @Validated PriceExpCostListKeyDto priceExpCostListKeyDto) {
        priceExpCostListKeyDto.setIsPublishedPrice(null);
        return priceExpService.getPriceExpCostList(pageDto, priceExpCostListKeyDto);
    }

    @PostMapping(value = "/get-published-price-list")
    @ApiOperation(value = "获取公布价列表", notes = "获取公布价")
    public ResultUtil<Page<PriceExpCostListVo>> getPublishedPriceList(PageDto pageDto, @Validated PriceExpCostListKeyDto priceExpCostListKeyDto) {
        priceExpCostListKeyDto.setIsPublishedPrice(1);
        return priceExpService.getPriceExpCostList(pageDto, priceExpCostListKeyDto);
    }

    @PostMapping(value = "/get-sale-info")
    @ApiOperation(value = "获取销售价格详情", notes = "获取销售价格详情")
    @ApiImplicitParam(name = "id", value = "销售价格表id", required = true, paramType = "query")
    public ResultUtil<PriceExpSaleInfoVo> getPriceExpSaleInfo(@NotNull(message = "销售id不能为空") @Min(value = 1, message = "id不能小于1") Long id) {

        return priceExpService.getPriceExpSaleInfo(id);
    }

    @PostMapping(value = "/get-cost-info")
    @ApiOperation(value = "获取成本价格详情", notes = "获取成本价格详情")
    @ApiImplicitParam(name = "id", value = "成本价格表id", required = true, paramType = "query")
    public ResultUtil<PriceExpCostInfoVo> getPriceExpCostInfo(@NotNull(message = "成本id不能为空") @Min(value = 1, message = "id不能小于1") Long id) {

        return priceExpService.getPriceExpCostInfo(id);
    }



    @PostMapping(value = "/add-price")
    @ApiOperation(value = "新增快递价格", notes = "新增快递价格")
    public ResultUtil<Long> addPrice(@Validated @RequestBody PriceExpMainAddDto priceExpMainAddDto,
                                     @Validated PriceExpCostAddDto priceExpCostAddDto,
                                     @Validated @RequestBody PriceExpSaleAddDto priceExpSaleAddDto,
                                     @Validated PriceExpAxisAddDto priceExpAxisAddDto,
                                     @Validated @RequestBody PriceExpDataAddDto priceExpDataAddDto) {

        return priceExpService.addExpPrice(priceExpMainAddDto,priceExpCostAddDto, priceExpSaleAddDto, priceExpAxisAddDto, priceExpDataAddDto);
    }


    @PostMapping(value = "/upd-sale-price")
    @ApiOperation(value = "更新销售价格表", notes = "根据Id修改销售价格表")
    public ResultUtil<Boolean> updateSalePrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                               @Validated PriceExpSaleUpdateDto priceExpSaleUpdateDto) {
        if(priceExpMainUpdateDto != null){
            ApiParamValidate.validate(priceExpMainUpdateDto);
            ApiParamValidate.notEmpty("startWeight", priceExpMainUpdateDto.getStartWeight().toString());
            ApiParamValidate.notEmpty("endWeight", priceExpMainUpdateDto.getEndWeight().toString());
            ApiParamValidate.notEmpty("startDate", priceExpMainUpdateDto.getStartDate().toString());
            ApiParamValidate.notEmpty("endDate", priceExpMainUpdateDto.getEndDate().toString());
        }

        return priceExpService.updateSalePrice(priceExpMainUpdateDto, priceExpSaleUpdateDto);
    }

    @PostMapping(value = "/upd-cost-price")
    @ApiOperation(value = "更新成本价格表", notes = "根据Id修改成本价格表")
    public ResultUtil<Boolean> updateCostPrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                               @Validated PriceExpCostUpdateDto priceExpCostUpdateDto) {

        if(priceExpMainUpdateDto != null){
            ApiParamValidate.validate(priceExpMainUpdateDto);
            ApiParamValidate.notEmpty("startWeight", priceExpMainUpdateDto.getStartWeight().toString());
            ApiParamValidate.notEmpty("endWeight", priceExpMainUpdateDto.getEndWeight().toString());
            ApiParamValidate.notEmpty("startDate", priceExpMainUpdateDto.getStartDate().toString());
            ApiParamValidate.notEmpty("endDate", priceExpMainUpdateDto.getEndDate().toString());
        }

        return priceExpService.updateCostPrice(priceExpMainUpdateDto, priceExpCostUpdateDto);
    }

    @PostMapping(value = "/upd-remark")
    @ApiOperation(value = "更新备注", notes = "根据Id更新备注")
    public ResultUtil<Boolean> updRemark(@Validated PriceExpRemarkPo priceExpRemarkPo) {

        return priceExpService.updRemark(priceExpRemarkPo);
    }

    @PostMapping(value = "/upd-price-data")
    @ApiOperation(value = "更新主表数据", notes = "更新主表数据")
    public ResultUtil<Boolean> updatePriceData(@Validated PriceExpDataAddDto priceExpDataAddDto,
                                               @Validated PriceExpAxisPo priceExpAxisPo){
        return priceExpService.updatePriceData(priceExpDataAddDto, priceExpAxisPo);
    }

    @PostMapping(value = "/delete-cost-price")
    @ApiOperation(value = "删除成本价格表", notes = "根据Id删除成本价格表")
    @ApiImplicitParam(name = "id", value = "成本价格表id", required = true, paramType = "query")
    public ResultUtil<Boolean> deleteCostPrice(
            @NotNull(message = "成本价格表id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return priceExpService.deleteCostPrice(id);
    }


    @PostMapping(value = "/delete-sale-price")
    @ApiOperation(value = "删除销售价格表", notes = "根据Id删除销售价格表")
    @ApiImplicitParam(name = "id", value = "销售价格表id", required = true, paramType = "query")
    public ResultUtil<Boolean> deleteSalePrice(
            @NotNull(message = "销售价格表id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return priceExpService.deleteSalePrice(id);
    }
}

