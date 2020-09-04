package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.PriceZoneNameService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceZoneNamePo;
import com.apl.lms.price.exp.pojo.vo.PriceZoneVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:09
 */
@RestController
@RequestMapping("/zone")
@Validated
@Api(value = "快递分区表",tags = "快递分区表")
public class PriceZoneNameController {

    @Autowired
    PriceZoneNameService priceZoneService;

    @PostMapping(value = "/get-zone-name-list")
    @ApiOperation(value =  "分页获取快递分区名称列表" , notes = "根据关键字来查询")
    public ResultUtil<Page<PriceZoneVo>> getList(PageDto pageDto ,
                                                 @Validated PriceZoneNameKeyDto priceZoneNameKeyDto){

        return priceZoneService.getPriceZoneNameList(pageDto, priceZoneNameKeyDto);
    }

    @PostMapping(value = "/del-batch-zone-name")
    @ApiOperation(value =  "批量删除" , notes = "根据ids批量删除")
    public ResultUtil<Boolean> delBatch( @RequestBody List<Long> ids){

        return priceZoneService.delBatchPriceZoneName(ids);
    }

    @PostMapping(value = "/add-zone-name")
    @ApiOperation(value =  "添加分区表名称" , notes = "添加分区表名称")
    public ResultUtil<Long> add( @Validated PriceZoneNamePo priceZoneAddDto){

        return priceZoneService.addPriceZoneName(priceZoneAddDto);
    }

    @PostMapping(value = "/upd-zone-name")
    @ApiOperation(value =  "更新" , notes = "根据id更新快递分区")
    public ResultUtil<Boolean> upd( @Validated PriceZoneNamePo priceZoneUpdDto){

        return priceZoneService.updPriceZoneName(priceZoneUpdDto);
    }

}
