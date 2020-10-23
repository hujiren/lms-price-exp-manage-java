package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.ChannelCategoryService;
import com.apl.lms.price.exp.pojo.po.ChannelCategoryPo;
import com.apl.lms.price.exp.pojo.vo.ChannelCategoryListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 18:09
 */
@RestController
@RequestMapping("/channel-category")
@Validated
@Api(value = "渠道类型",tags = "渠道类型")
public class  ChannelCategoryController {

    @Autowired
    ChannelCategoryService channelCategoryService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "获取渠道类型列表" , notes = "获取渠道类型列表")
    public ResultUtil<List<ChannelCategoryListVo>> getList(){

        return channelCategoryService.getList();
    }

    @PostMapping(value = "/del")
    @ApiOperation(value =  "删除" , notes = "根据id删除渠道类型")
    @ApiImplicitParam(name = "id",value = "渠道类型id",required = true  , paramType = "query")
    public ResultUtil<Boolean> del(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return channelCategoryService.delChannelCategory(id);
    }

    @PostMapping(value = "/upd")
    @ApiOperation(value =  "更新" , notes = "根据id更新渠道类型")
    public ResultUtil<Boolean> upd(@Validated ChannelCategoryPo channelCategoryPo){

        return channelCategoryService.updChannelCategory(channelCategoryPo);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value =  "新增渠道类型" , notes = "新增渠道类型")
    public ResultUtil<String> add( @Validated ChannelCategoryPo channelCategoryPo){

        return channelCategoryService.addChannelCategory(channelCategoryPo);
    }

}
