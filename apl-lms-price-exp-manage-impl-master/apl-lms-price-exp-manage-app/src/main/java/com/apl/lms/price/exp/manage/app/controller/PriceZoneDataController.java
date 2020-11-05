package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.PriceZoneDataService;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hjr start
 * @Classname PriceZoneDataController
 * @Date 2020/8/31 15:07
 */
@RestController
@RequestMapping("/zone-data")
@Validated
@Api(value = "分区数据",tags = "分区数据")
public class PriceZoneDataController {

    @Autowired
    PriceZoneDataService priceZoneDataService;

    @PostMapping(value = "/get-list2")
    @ApiOperation(value =  "获取快递分区数据-视图2" , notes = "获取快递分区数据-视图2")
    @ApiImplicitParam(name = "zoneId", value = "分区表id", required = true, paramType = "query")
    public ResultUtil<List<PriceZoneDataListVo>> getList(@NotNull(message = "id不能为空") Long zoneId) throws Exception {
        List<PriceZoneDataListVo> list = priceZoneDataService.getList(zoneId);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, list);
    }

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "获取快递分区数据-视图1" , notes = "获取快递分区数据-视图1")
    @ApiImplicitParam(name = "zoneId", value = "分区表id", required = true, paramType = "query")
    public ResultUtil<List<PriceZoneDataListVo>> assemblingZoneData(@NotNull(message = "id不能为空") Long zoneId) throws Exception{
        List<Long> zoneIds = new ArrayList<>();
        zoneIds.add(zoneId);
        Map<Long, List<PriceZoneDataListVo>> longListMap = priceZoneDataService.assemblingZoneData(zoneIds);
        List<PriceZoneDataListVo> zoneDataList = longListMap.get(zoneId);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, zoneDataList);
    }

    @PostMapping(value = "/delete-batch")
    @ApiOperation(value =  "批量删除" , notes = "批量删除")
    public ResultUtil<Boolean> deleteBatch(@NotEmpty(message = "id不能为空") @RequestBody List<Long> ids){
        return priceZoneDataService.deleteBatch(ids);
    }



    @GetMapping(value = "/export-zone")
    @ApiOperation(value =  "导出分区" , notes = "导出分区")
    @ApiImplicitParam(name = "zoneId", value = "分区表id", required = true, paramType = "query")
    public ResultUtil<Boolean> exportZone(HttpServletResponse response, Long zoneId){
        return priceZoneDataService.exportZone(response, zoneId);
    }
}
