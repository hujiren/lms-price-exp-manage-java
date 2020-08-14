package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:09
 */
@RestController
@RequestMapping("/weight-way")
@Validated
@Api(value = "计泡方式",tags = "计泡方式")
public class WeightWayController {

    @Autowired
    WeightWayService weightWayService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页获取特殊物品列表" , notes = "根据关键字来查询")
    public ResultUtil<Page<WeightWayDto>> getList(PageDto pageDto ,
                                                  @Validated WeightWayKeyDto weightWayKeyDto){
        return weightWayService.getList(pageDto, weightWayKeyDto);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "删除" , notes = "根据id删除")
    @ApiImplicitParam(name = "id", value = "计泡方式id", required = true, paramType = "query")
    public ResultUtil<Boolean> delete(@NotNull(message = "id不能为空") Long id){

        return weightWayService.delWeightWay(id);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value =  "更新" , notes = "根据id更新计泡方式")
    public ResultUtil<Boolean> update( @Validated WeightWayDto weightWayDto){

        return weightWayService.updWeightWay(weightWayDto);
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value =  "新增" , notes = "新增计泡方式")
    public ResultUtil<Long> insert( @Validated WeightWayInsertDto weightWayInsertDto){

        return weightWayService.addWeightWay(weightWayInsertDto);
    }
}
