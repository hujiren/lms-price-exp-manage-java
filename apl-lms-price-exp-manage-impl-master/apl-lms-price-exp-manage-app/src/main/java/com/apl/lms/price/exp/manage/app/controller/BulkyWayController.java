package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.BulkyWayService;
import com.apl.lms.price.exp.pojo.dto.BulkyWayUpdDto;
import com.apl.lms.price.exp.pojo.po.BulkyWayPo;
import com.apl.lms.price.exp.pojo.dto.BulkyWayKeyDto;
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
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:09
 */
@RestController
@RequestMapping("/bulky-way")
@Validated
@Api(value = "计泡方式",tags = "计泡方式")
public class BulkyWayController {

    @Autowired
    BulkyWayService bulkyWayService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页计泡方式列表" , notes = "根据关键字来查询")
    public ResultUtil<Page<BulkyWayUpdDto>> getList(PageDto pageDto ,
                                                    @Validated BulkyWayKeyDto bulkyWayKeyDto){
        return bulkyWayService.getList(pageDto, bulkyWayKeyDto);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "删除" , notes = "根据id删除")
    @ApiImplicitParam(name = "id", value = "计泡方式id", required = true, paramType = "query")
    public ResultUtil<Boolean> delete(@NotNull(message = "id不能为空") Long id){

        return bulkyWayService.del(id);
    }

    @PostMapping(value = "/upd")
    @ApiOperation(value =  "更新" , notes = "根据id更新计泡方式")
    public ResultUtil<Boolean> upd( @Validated BulkyWayUpdDto bulkyWayUpdDto){

        return bulkyWayService.upd(bulkyWayUpdDto);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value =  "批量新增" , notes = "批量新增新增计泡方式")
    public ResultUtil<Integer> insertBatch( @RequestBody List<BulkyWayPo> bulkyWayPoList){

        return bulkyWayService.add(bulkyWayPoList);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取计泡方式详细" , notes = "获取计泡方式详细")
    @ApiImplicitParam(name = "id", value = "计泡方式id", required = true, paramType = "query")
    public ResultUtil<BulkyWayUpdDto> get(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return bulkyWayService.get(id);
    }
}
