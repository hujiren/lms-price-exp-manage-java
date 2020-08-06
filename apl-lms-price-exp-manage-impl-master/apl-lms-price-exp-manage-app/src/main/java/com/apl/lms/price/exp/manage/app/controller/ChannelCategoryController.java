package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.validate.ApiParamValidate;
import com.apl.lms.price.exp.pojo.dto.ChannelCateGoryDto;
import com.apl.lms.price.exp.pojo.dto.ChannelCateGoryKeyDto;
import com.apl.lms.price.exp.pojo.vo.ChannelCateGoryVo;
import com.apl.lms.price.exp.manage.service.ChannelCategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/5 - 18:09
 */
@RestController
@RequestMapping("/channel-category")
@Validated
@Api(value = "渠道类型",tags = "渠道类型")
public class ChannelCategoryController {

    @Autowired
    ChannelCategoryService channelCategoryService;

    @Autowired
    DataSource dataSource;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页获取渠道类型列表" , notes = "根据关键字来查询")
    @ApiImplicitParam(name = "ChannelCateGoryKeyDto",value = "渠道类型查询",required = true  , paramType = "query")
    public ResultUtil<Page<ChannelCateGoryVo>> getList(PageDto pageDto ,
                                                       @NotNull(message = "渠道类型对象不能为空") @Validated ChannelCateGoryKeyDto channelCateGoryKeyDto){

        return channelCategoryService.getList(pageDto, channelCateGoryKeyDto);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "删除" , notes = "根据id删除渠道类型")
    @ApiImplicitParam(name = "id",value = "渠道类型id",required = true  , paramType = "query")
    public ResultUtil<Boolean> delete(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return channelCategoryService.delChannelCategory(id);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value =  "更新" , notes = "根据id更新渠道类型")
    @ApiImplicitParam(name = "channelCateGoryDto", value = "渠道类型更新对象", required = true, paramType = "query")
    public ResultUtil<Boolean> update(@NotNull(message = "渠道类型对象不能为空") @Validated ChannelCateGoryDto channelCateGoryDto){

        return channelCategoryService.updChannelCategory(channelCateGoryDto);
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value =  "新增渠道类型" , notes = "新增渠道类型")
    public ResultUtil<Long> insert(@Validated ChannelCateGoryDto channelCateGoryDto){

        return channelCategoryService.insChannelCategory(channelCateGoryDto);
    }


}
