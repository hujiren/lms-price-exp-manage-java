package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.PriceZoneDataService;
import com.apl.lms.price.exp.pojo.dto.PriceZoneNameKeyDto;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResultUtil<List<PriceZoneDataListVo>> getList(@Validated PriceZoneNameKeyDto priceZoneNameKeyDto){
        return priceZoneDataService.getList(priceZoneNameKeyDto);
    }

    @PostMapping(value = "/delete-batch")
    @ApiOperation(value =  "批量删除" , notes = "批量删除")
    public ResultUtil<Boolean> deleteBatch(@RequestParam("ids") List<Long> ids){
        return priceZoneDataService.deleteBatch(ids);
    }
}
