package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.PriceZoneDataService;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "获取快递分区数据" , notes = "获取快递分区数据")
    @ApiImplicitParam(name = "id", value = "分区表id", required = true, paramType = "query")
    public ResultUtil<List<PriceZoneDataListVo>> getList(@NotNull(message = "id不能为空") Long id) throws Exception {
        return priceZoneDataService.getList(id);
    }

    @PostMapping(value = "/delete-batch")
    @ApiOperation(value =  "批量删除" , notes = "批量删除")
    public ResultUtil<Boolean> deleteBatch(@NotEmpty(message = "id不能为空") @RequestBody List<Long> ids){
        return priceZoneDataService.deleteBatch(ids);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取" , notes = "获取")
    public ResultUtil<Map<Long, List<PriceZoneDataListVo>>> assemblingZoneData(@NotEmpty(message = "id不能为空") @RequestBody List<Long> ids) throws Exception {
        Map<Long, List<PriceZoneDataListVo>> longListMap = priceZoneDataService.assemblingZoneData(ids);
        return ResultUtil.APPRESULT("","",longListMap);
    }
}
