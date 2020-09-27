package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.constants.CommonStatusCode;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:26
 */
@RestController
@RequestMapping("/price-list")
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
    @ApiOperation(value = "分页查询销售价格列表", notes = "分页查询销售价格列表")
    public ResultUtil<Page<PriceExpSaleListVo>> getSaleList(PageDto pageDto, @Validated PriceExpSaleListKeyDto priceExpSaleListKeyDto) {

        priceExpSaleListKeyDto.setPriceType(1);

        return priceExpService.getPriceExpSaleList(pageDto, priceExpSaleListKeyDto);
    }


    @PostMapping(value = "/get-customer-list")
    @ApiOperation(value = "分页查询客户价格列表", notes = "分页查询客户价格列表")
    public ResultUtil<Page<PriceExpSaleListVo>> getCustomerList(PageDto pageDto, @Validated PriceExpSaleListKeyDto priceExpSaleListKeyDto) {

        priceExpSaleListKeyDto.setPriceType(2);

        return priceExpService.getPriceExpSaleList(pageDto, priceExpSaleListKeyDto);
    }


    @PostMapping(value = "/get-cost-list")
    @ApiOperation(value = "分页查询成本价格列表", notes = "分页查询成本价格列表")
    public ResultUtil<Page<PriceExpCostListVo>> getCostList(PageDto pageDto, @Validated PriceExpCostKeyDto priceExpCostListKeyDto) throws Exception {

        return priceExpService.getPriceExpCostList(pageDto, priceExpCostListKeyDto);
    }


    @PostMapping(value = "/get-published-price-list")
    @ApiOperation(value = "分页查询公布价列表", notes = "分页查询公布价列表")
    public ResultUtil<Page<PriceExpCostListVo>> getPublishedPriceList(PageDto pageDto, @Validated PriceExpPublishedKeyDto keyDto) {

        return priceExpService.getPublishedPriceList(pageDto, keyDto);
    }


    @PostMapping(value = "/get-price-info")
    @ApiOperation(value = "获取价格详情", notes = "获取价格详情")
    @ApiImplicitParam(name = "id", value = "价格表id", required = true, paramType = "query")
    public ResultUtil<PriceExpPriceInfoVo> getPriceExpInfo(@NotNull(message = "价格表id不能为空")
                                                               @Min(value = 1, message = "价格表id不能小于1") Long id) throws Exception {

        return priceExpService.getPriceExpInfo(id);
    }


    @PostMapping(value = "/add-price")
    @ApiOperation(value = "新增快递价格", notes = "新增快递价格", consumes = "application/json")
    public ResultUtil<Long> addPrice(@Validated @RequestBody PriceExpAddDto priceExpAddDto){

        return priceExpService.addExpPrice(priceExpAddDto);
    }


    @PostMapping(value = "/reference-price")
    @ApiOperation(value = "引用价格表", notes = "引用价格表")
    public ResultUtil<Long> referencePrice(@RequestBody @Validated ReferencePriceDto referencePriceDto){

        return priceExpService.referencePrice(referencePriceDto);
    }


    @PostMapping(value = "/delete-price-batch")
    @ApiOperation(value = "批量删除价格表", notes = "根据Id批量删除价格表")
    public ResultUtil<Boolean> deleteCostPrice(@NotEmpty(message = "价格表id不能为空") @RequestBody List<Long> ids){
        return priceExpService.deletePriceBatch(ids);
    }


    @PostMapping(value = "/upd-exp-price")
    @ApiOperation(value = "根据Id修改价格主表", notes = "根据Id修改价格主表")
    public ResultUtil<Boolean> updExpPrice(@Validated @RequestBody PriceExpUpdDto priceExpUpdDto) {
        return priceExpService.updExpPrice(priceExpUpdDto);
    }


    @PostMapping(value = "/get-price-data")
    @ApiOperation(value = "获取价格表数据", notes = "获取价格表数据")
    @ApiImplicitParam(name = "id", value = "价格表Id", required = true, paramType = "query")
    public ResultUtil<PriceExpDataVo> getPriceExpData(@NotNull(message = "价格表Id不能为空") @Min(value = 1, message = "id不能小于1") Long id) throws Exception {
        return priceExpService.getPriceExpDataInfoByPriceId(id);
    }


    @PostMapping(value = "/upd-price-data")
    @ApiOperation(value = "更新数据表数据", notes = "更新数据表数据")
    public ResultUtil<Boolean> updatePriceData(@Validated @RequestBody PriceExpDataUpdDto priceExpDataUpdDto){
        return priceExpService.updatePriceData(priceExpDataUpdDto);
    }


    @PostMapping(value = "/get-price-axis")
    @ApiOperation(value = "获取数据轴", notes = "获取数据轴")
    @ApiImplicitParam(name = "id", value = "价格表id", required = true, paramType = "query")
    public ResultUtil<PriceExpAxisVo> getPriceExpAxis(@NotNull(message = "价格表Id不能为空") @Min(value = 1, message = "id不能小于1") Long id) {

        return priceExpAxisService.getAxisInfoById(id);
    }


    @PostMapping(value = "/upd-transverse-weight-section")
    @ApiOperation(value = "更新横向重量段", notes = "更新横向重量段")
    public ResultUtil<List<String> > updTransverseWeightSection(@RequestBody @Validated WeightSectionUpdDto weightSectionUpdDto) {
        List<String>  headCells = priceExpService.updTransverseWeightSection(weightSectionUpdDto);

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, headCells);
    }


    @PostMapping(value = "/get-price-remark")
    @ApiOperation(value = "获取备注信息", notes = "获取备注信息")
    @ApiImplicitParam(name = "id", value = "价格表Id", required = true, paramType = "query")
    public ResultUtil<PriceExpRemarkPo> getPriceExpRemark(@NotNull(message = "价格表Id不能为空") @Min(value = 1, message = "id不能小于1") Long id) {
        return priceExpRemarkService.getPriceExpRemark(id);
    }


    @PostMapping(value = "/upd-remark")
    @ApiOperation(value = "更新备注", notes = "根据Id更新备注")
    public ResultUtil<Boolean> updRemark(@Validated @RequestBody PriceExpRemarkPo priceExpRemarkPo) {

        Boolean saveResult = priceExpRemarkService.updateRemark(priceExpRemarkPo);

        if(saveResult)
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
    }

}

