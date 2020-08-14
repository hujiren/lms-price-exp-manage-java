package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.PriceZoneService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.vo.PriceZoneVo;
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
 * @date 2020/8/6 - 14:09
 */
@RestController
@RequestMapping("/zone")
@Validated
@Api(value = "快递分区",tags = "快递分区")
public class PriceZoneController {

    @Autowired
    PriceZoneService priceZoneService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页获取快递分区列表" , notes = "根据关键字来查询")
    public ResultUtil<Page<PriceZoneVo>> getList(PageDto pageDto ,
                                                 @Validated PriceZoneInsertKeyDto priceZoneInsertKeyDto){

        return priceZoneService.getList(pageDto, priceZoneInsertKeyDto);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取快递分区详细" , notes = "查询")
    @ApiImplicitParam(name = "id",value = "id",required = true  , paramType = "query")
    public ResultUtil<Page<PriceZoneVo>> getList(@NotNull @Min(value = 0, message = "id不可小于0") Long id){

        return priceZoneService.get(id);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "批量删除" , notes = "根据ids批量删除")
    public ResultUtil<Boolean> deleteBatch( @RequestBody List<Long> ids){

        return priceZoneService.delBatchPriceZone(ids);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value =  "更新" , notes = "根据id更新快递分区")
    public ResultUtil<Boolean> update( @Validated PriceZoneDto priceZoneDto){

        return priceZoneService.updPriceZone(priceZoneDto);
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value =  "新增快递分区" , notes = "新增快递分区")
    public ResultUtil<Long> insert( @Validated PriceZoneInsertDto priceZoneInsertDto){

        return priceZoneService.addPriceZone(priceZoneInsertDto);
    }
}
