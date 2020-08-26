package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.validate.ApiParamValidate;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.pojo.dto.*;
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

    @PostMapping(value = "/add-exp-price")
    @ApiOperation(value = "新增快递价格", notes = "新增快递价格")
    public ResultUtil<Long> addExpPrice(PriceExpMainInsertDto priceExpMainInsertDto,
                                        PriceExpSaleAddDto priceExpSaleAddDto,
                                        PriceExpCostAddDto priceExpCostAddDto,
                                        PriceExpAxisInsertDto priceExpAxisInsertDto,
                                        PriceExpDataInsertDto priceExpDataInsertDto) {
        if(priceExpSaleAddDto != null) {
            String[] customerName = priceExpSaleAddDto.getCustomerName().split(",");
            if (priceExpSaleAddDto.getCustomerIds().size() != customerName.length) {
                return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL.code, "传入的客户信息不匹配", null);
            }

            String[] customerGroup = priceExpSaleAddDto.getCustomerGroupsName().split(",");
            if (priceExpSaleAddDto.getCustomerGroupsId().size() != customerGroup.length) {
                return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL.code, "传入的客户组信息不匹配", null);
            }
        }

        if(priceExpSaleAddDto != null && priceExpSaleAddDto.getCustomerGroupsName() != null && priceExpCostAddDto == null){
            ApiParamValidate.validate(priceExpSaleAddDto);
            ApiParamValidate.notEmpty("priceName", priceExpSaleAddDto.getSalePriceCode());
            ApiParamValidate.notEmpty("salePriceMainId", priceExpSaleAddDto.getSalePriceMainId());
            ApiParamValidate.notEmpty("channelCategory", priceExpSaleAddDto.getSaleChannelCategory());
            return priceExpService.addSalePrice(priceExpSaleAddDto);

        }else if(priceExpSaleAddDto == null && priceExpCostAddDto != null && priceExpCostAddDto.getPartnerId() != null){
            ApiParamValidate.validate(priceExpCostAddDto);
            ApiParamValidate.notEmpty("priceName", priceExpCostAddDto.getCostPriceName());
            ApiParamValidate.notEmpty("channelCategory", priceExpCostAddDto.getCostChannelCategory());
            ApiParamValidate.notEmpty("costPriceMainId", priceExpCostAddDto.getCostPriceMainId());
            return priceExpService.addCostPrice(priceExpCostAddDto);

        }else if(priceExpSaleAddDto != null && priceExpCostAddDto != null && priceExpSaleAddDto.getCustomerGroupsName() != null
        && priceExpCostAddDto.getPartnerId() != null){

            //校验成本价格对象
            ApiParamValidate.validate(priceExpCostAddDto);
            ApiParamValidate.notEmpty("priceName", priceExpCostAddDto.getCostPriceName());
            ApiParamValidate.notEmpty("channelCategory", priceExpCostAddDto.getCostChannelCategory());

            //校验销售价格对象
            ApiParamValidate.validate(priceExpSaleAddDto);
            ApiParamValidate.notEmpty("priceName", priceExpSaleAddDto.getSalePriceName());
            ApiParamValidate.notEmpty("channelCategory", priceExpSaleAddDto.getSaleChannelCategory());

            //校验主价格表对象
            ApiParamValidate.validate(priceExpMainInsertDto);
            ApiParamValidate.notEmpty("startWeight", priceExpMainInsertDto.getStartWeight().toString());
            ApiParamValidate.notEmpty("endWeight", priceExpMainInsertDto.getEndWeight().toString());
            ApiParamValidate.notEmpty("pricePublishedId", priceExpMainInsertDto.getPricePublishedId());
            ApiParamValidate.notEmpty("aging", priceExpMainInsertDto.getAging());

            //校验轴数据对象
            ApiParamValidate.validate(priceExpAxisInsertDto);
            ApiParamValidate.notEmpty("axisTransverse", priceExpAxisInsertDto.getAxisTransverse().toString());
            ApiParamValidate.notEmpty("axisPortrait", priceExpAxisInsertDto.getAxisPortrait().toString());

            //校验主要数据对象
            ApiParamValidate.validate(priceExpDataInsertDto);
            ApiParamValidate.notEmpty("priceData", priceExpDataInsertDto.getPriceData().toString());

        }
        
        return priceExpService.addExpPrice(priceExpMainInsertDto,priceExpSaleAddDto, priceExpCostAddDto, priceExpAxisInsertDto, priceExpDataInsertDto);
    }


    @PostMapping(value = "/update-sale-price")
    @ApiOperation(value = "更新销售价格表", notes = "根据Id修改销售价格表")
    public ResultUtil<Boolean> updateSalePrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                               @Validated PriceExpSaleUpdateDto priceExpSaleUpdateDto,
                                               PriceExpAxisUpdateDto priceExpAxisUpdateDto,
                                               PriceExpDevelopInfoUpdateDto priceExpDevelopInfoUpdateDto) {
        if(priceExpMainUpdateDto != null){
            ApiParamValidate.validate(priceExpMainUpdateDto);
            ApiParamValidate.notEmpty("id", priceExpMainUpdateDto.getId());
            ApiParamValidate.notEmpty("startWeight", priceExpMainUpdateDto.getStartWeight().toString());
            ApiParamValidate.notEmpty("endWeight", priceExpMainUpdateDto.getEndWeight().toString());
            ApiParamValidate.notEmpty("aging", priceExpMainUpdateDto.getAging());
        }

        if(priceExpAxisUpdateDto != null){
            ApiParamValidate.validate(priceExpAxisUpdateDto);
            ApiParamValidate.notEmpty("id", priceExpAxisUpdateDto.getId());
        }

        if(priceExpDevelopInfoUpdateDto != null){
            ApiParamValidate.validate(priceExpDevelopInfoUpdateDto);
            ApiParamValidate.notEmpty("id", priceExpDevelopInfoUpdateDto.getId());
        }

        return priceExpService.updateSalePrice(priceExpMainUpdateDto, priceExpSaleUpdateDto, priceExpAxisUpdateDto, priceExpDevelopInfoUpdateDto);
    }

    @PostMapping(value = "/update-cost-price")
    @ApiOperation(value = "更新成本价格表", notes = "根据Id修改成本价格表")
    public ResultUtil<Boolean> updateCostPrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                               @Validated PriceExpCostUpdateDto priceExpCostUpdateDto,
                                               PriceExpAxisUpdateDto priceExpAxisUpdateDto,
                                               PriceExpDevelopInfoUpdateDto priceExpDevelopInfoUpdateDto) {

        if(priceExpMainUpdateDto != null){
            ApiParamValidate.validate(priceExpMainUpdateDto);
            ApiParamValidate.notEmpty("id", priceExpMainUpdateDto.getId());
            ApiParamValidate.notEmpty("startWeight", priceExpMainUpdateDto.getStartWeight().toString());
            ApiParamValidate.notEmpty("endWeight", priceExpMainUpdateDto.getEndWeight().toString());
            ApiParamValidate.notEmpty("aging", priceExpMainUpdateDto.getAging());
        }

        if(priceExpAxisUpdateDto != null){
            ApiParamValidate.validate(priceExpAxisUpdateDto);
            ApiParamValidate.notEmpty("id", priceExpAxisUpdateDto.getId());
        }

        if(priceExpDevelopInfoUpdateDto != null){
            ApiParamValidate.validate(priceExpDevelopInfoUpdateDto);
            ApiParamValidate.notEmpty("id", priceExpDevelopInfoUpdateDto.getId());
        }
        
        return priceExpService.updateCostPrice(priceExpMainUpdateDto, priceExpCostUpdateDto, priceExpAxisUpdateDto, priceExpDevelopInfoUpdateDto);
    }

    @PostMapping(value = "/update-price-data")
    @ApiOperation(value = "更新主表数据", notes = "更新主表数据")
    public ResultUtil<Boolean> updatePriceData(PriceExpDataUpdateDto priceExpMainUpdateDto){
        return priceExpService.updatePriceData(priceExpMainUpdateDto);
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

