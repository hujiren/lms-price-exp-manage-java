package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.SpecialCommodityService;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityDto;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityInsertDto;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityKeyDto;
import com.apl.lms.price.exp.pojo.vo.SpecialCommodityVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.Validate;
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
@RequestMapping("/special-commodity")
@Validated
@Api(value = "特殊物品",tags = "特殊物品")
public class SpecialCommodityController {

    @Autowired
    SpecialCommodityService specialCommodityService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页获取特殊物品列表" , notes = "根据关键字来查询")
    public ResultUtil<Page<SpecialCommodityVo>> getList(PageDto pageDto ,
                                                        @Validated SpecialCommodityKeyDto specialCommodityKeyDto){
        return specialCommodityService.getList(pageDto, specialCommodityKeyDto);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "删除" , notes = "根据id删除")
    @ApiImplicitParam(name = "id", value = "特殊物品Id", required = true, paramType = "query")
    public ResultUtil<Boolean> delete(@NotNull(message = "id不能为空") Long id){

        return specialCommodityService.delSpecialCommodity(id);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value =  "更新" , notes = "根据id更新特殊物品")
    public ResultUtil<Boolean> update( @Validated SpecialCommodityDto specialCommodityDto){

        return specialCommodityService.updSpecialCommodity(specialCommodityDto);
    }

    @PostMapping(value = "/insert-batch")
    @ApiOperation(value =  "批量新增" , notes = "批量新增特殊物品")
    public ResultUtil<Integer> insertBatch( @RequestBody List<SpecialCommodityInsertDto> specialCommodityInsertDtoList){

        return specialCommodityService.addSpecialCommodity(specialCommodityInsertDtoList);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取特殊物品详细" , notes = "获取特殊物品详细")
    @ApiImplicitParam(name = "id", value = "特殊物品Id", required = true, paramType = "query")
    public ResultUtil<SpecialCommodityVo> get(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return specialCommodityService.getSpecialCommodity(id);
    }
}
