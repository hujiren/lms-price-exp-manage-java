package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.StringUtil;
import com.apl.lms.price.exp.manage.dao.PriceListDao;
import com.apl.lms.price.exp.manage.service.*;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
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

    @Autowired
    UploadsAndDownLoadsService uploadsAndDownLoadsService;

    @Autowired
    PriceListDao priceListDao;



    @PostMapping(value = "/get-sale-list")
    @ApiOperation(value = "分页查询销售价格列表", notes = "分页查询销售价格列表")
    public ResultUtil<Page<PriceExpSaleListVo>> getSaleList(PageDto pageDto, @Validated PriceExpSaleKeyDto priceExpSaleKeyDto) {

        priceListDao.createRealTable();
        priceExpSaleKeyDto.setPriceType(1);

        return priceExpService.getPriceExpSaleList(pageDto, priceExpSaleKeyDto);
    }


    @PostMapping(value = "/get-customer-list")
    @ApiOperation(value = "分页查询客户价格列表", notes = "分页查询客户价格列表")
    public ResultUtil<Page<PriceExpSaleListVo>> getCustomerList(PageDto pageDto, @Validated PriceExpSaleKeyDto priceExpSaleKeyDto) {

        priceListDao.createRealTable();
        priceExpSaleKeyDto.setPriceType(2);

        return priceExpService.getPriceExpSaleList(pageDto, priceExpSaleKeyDto);
    }


    @PostMapping(value = "/get-cost-list")
    @ApiOperation(value = "分页查询成本价格列表", notes = "分页查询成本价格列表")
    public ResultUtil<Page<PriceExpCostListVo>> getCostList(PageDto pageDto, @Validated PriceExpCostKeyDto priceExpCostListKeyDto) throws Exception {

        priceListDao.createRealTable();
        return priceExpService.getPriceExpCostList(pageDto, priceExpCostListKeyDto);
    }


    @PostMapping(value = "/get-published-price-list")
    @ApiOperation(value = "分页查询公布价列表", notes = "分页查询公布价列表")
    public ResultUtil<Page<PriceExpCostListVo>> getPublishedPriceList(PageDto pageDto, @Validated PriceExpPublishedKeyDto keyDto) {

        priceListDao.createRealTable();
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
    public ResultUtil<Long> addPrice(@Validated @RequestBody PriceExpAddDto priceExpAddDto) throws Exception {

        return priceExpService.addExpPrice(priceExpAddDto);
    }


    @PostMapping(value = "/sync-price")
    @ApiOperation(value = "同步价格表", notes = "同步价格表")
    public ResultUtil<Boolean> syncPrice(@NotEmpty(message = "价格表id不能为空") @RequestBody List<Long> priceIds) throws Exception {

        return priceExpService.syncPrice(priceIds);
    }


    @PostMapping(value = "/reference-price")
    @ApiOperation(value = "引用价格表", notes = "引用价格表")
    public ResultUtil<Boolean> referencePrice(@Validated @RequestBody PriceReferenceDto priceReferenceDto) throws Exception {

        return priceExpService.referencePrice(priceReferenceDto);
    }


    @PostMapping(value = "/delete-price-batch")
    @ApiOperation(value = "批量删除价格表", notes = "根据Id批量删除价格表")
    public ResultUtil<Boolean> deleteCostPrice(@NotEmpty(message = "价格表id不能为空") @RequestBody List<Long> priceIds) throws IOException {

        return priceExpService.deletePriceBatch(priceIds);
    }


    @PostMapping(value = "/upd-exp-price")
    @ApiOperation(value = "更新价格主表", notes = "更新价格主表")
    public ResultUtil<Boolean> updExpPrice(@Validated @RequestBody PriceExpUpdDto priceExpUpdDto) throws IOException {

        return priceExpService.updExpPrice(priceExpUpdDto);
    }


    @PostMapping(value = "/get-price-data")
    @ApiOperation(value = "获取成本价格表数据", notes = "获取成本价格表数据")
    @ApiImplicitParam(name = "id", value = "价格表Id", required = true, paramType = "query")
    public ResultUtil<PriceExpDataObjVo> getCostPriceExpData(@NotNull(message = "价格表Id不能为空") @Min(value = 1, message = "id不能小于1") Long id) throws Exception {

        PriceExpDataObjVo priceExpDataInfo = priceExpService.getPriceExpData(id,  false,  0l);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpDataInfo);
    }


    @PostMapping(value = "/get-sale-price-data")
    @ApiOperation(value = "获取销售价格表数据", notes = "获取销售价格表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id" , value = "价格表Id" , paramType = "query", required = true),
            @ApiImplicitParam(name = "customerGroupId" , value = "客户组id", paramType = "query", required = true),
    })
    public ResultUtil<PriceExpDataObjVo> getSalePriceExpData(Long id, Long customerGroupId) throws Exception {

        PriceExpDataObjVo priceExpDataInfo = priceExpService.getPriceExpData(id,  true,  customerGroupId);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpDataInfo);
    }


    @PostMapping(value = "/upd-price-data")
    @ApiOperation(value = "更新数据表数据", notes = "更新数据表数据")
    public ResultUtil<Boolean> updatePriceData(@Validated @RequestBody PriceExpDataUpdDto priceExpDataUpdDto) throws IOException {

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
    public ResultUtil<List<String> > updTransverseWeightSection(@RequestBody @Validated WeightSectionUpdDto weightSectionUpdDto) throws IOException {

        List<String>  headCells = priceExpService.updTransverseWeightSection(weightSectionUpdDto);

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, headCells);
    }


    @PostMapping(value = "/get-price-remark")
    @ApiOperation(value = "获取备注信息", notes = "获取备注信息")
    @ApiImplicitParam(name = "id", value = "价格表Id", required = true, paramType = "query")
    public ResultUtil<PriceExpRemarkPo> getPriceExpRemark(@NotNull(message = "价格表Id不能为空") @Min(value = 1, message = "id不能小于1") Long id) {

        PriceExpRemarkPo priceExpRemark = priceExpRemarkService.getPriceExpRemark(id);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpRemark);
    }


    @PostMapping(value = "/upd-remark")
    @ApiOperation(value = "更新备注", notes = "根据Id更新备注")
    public ResultUtil<Boolean> updRemark(@Validated @RequestBody PriceExpRemarkPo priceExpRemarkPo) throws IOException {

        Boolean saveResult = priceExpRemarkService.updateRemark(priceExpRemarkPo);

        if(saveResult)
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
    }

    @PostMapping(value = "/check-quote-price-is-exists")
    @ApiOperation(value = "检测价格是否已被引用", notes = "检测价格是否已被引用")
    @ApiImplicitParam(name = "quotePriceId", value = "引用价格id", required = true, paramType = "query")
    public ResultUtil<Boolean> isQuoteByExpPrice(@NotNull(message = "引用价格id不能为空") @Min(value = 0, message = "引用价格id不能小于0") Long quotePriceId){

        ResultUtil<Boolean> isQuoteByExpPrice = priceExpService.isQuoteByExpPrice(quotePriceId);

        return isQuoteByExpPrice;
    }

    @PostMapping(value = "/check-is-quote-partner-price")
    @ApiOperation(value = "检测是否引用服务商价格", notes = "检测是否引用服务商价格")
    @ApiImplicitParam(name = "priceId", value = "价格表id", required = true, paramType = "query")
    public ResultUtil<Boolean> isQuotePartnerPrice(@NotNull(message = "价格表id不能为空") @Min(value = 0, message = "价格表id不能小于0") Long priceId){

        ResultUtil<Boolean> isQuoteByExpPrice = priceExpService.isQuotePartnerPrice(priceId);

        return isQuoteByExpPrice;
    }

    @GetMapping(value = "/export-exp-price")
    @ApiOperation(value = "导出成本价快递价格", notes = "导出成本价快递价格")
    public void exportExpPrice(HttpServletResponse response, @NotBlank(message = "id不能为空") String ids) throws Exception {

        List<Long> priceIdList =  StringUtil.stringToLongList(ids);
        uploadsAndDownLoadsService.exportExpPrice(response, priceIdList, 0l);
    }


    @GetMapping(value = "/export-sale-exp-price")
    @ApiOperation(value = "导出销售价快递价格", notes = "导出销售价快递价格")
    public void exportSaleExpPrice(HttpServletResponse response,
                                   @NotBlank(message = "id不能为空") String ids,
                                   @NotNull(message = "客户组id不能为空") Long customerGroupId) throws Exception {

        List<Long> priceIdList =  StringUtil.stringToLongList(ids);
        uploadsAndDownLoadsService.exportExpPrice(response, priceIdList, customerGroupId);

    }
}

