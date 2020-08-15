package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.ComputationalFormulaService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.vo.ComputationalFormulaVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

/**
 * @author hjr start
 * @date 2020/8/6 - 14:09
 */
@RestController
@RequestMapping("/computational-formula")
@Validated
@Api(value = "计算公式",tags = "计算公式")
public class ComputationalFormulaController {

    @Autowired
    ComputationalFormulaService computationalFormulaService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页获取计算公式列表" , notes = "根据关键字来查询")
    public ResultUtil<Page<ComputationalFormulaVo>> getList(PageDto pageDto ,
                                                            @Validated ComputationalFormulaKeyDto computationalFormulaKeyDto){

        return computationalFormulaService.getList(pageDto, computationalFormulaKeyDto);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "删除" , notes = "根据id删除")
    @ApiImplicitParam(name = "id",value = "计算公式id",required = true  , paramType = "query")
    public ResultUtil<Boolean> delete(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return computationalFormulaService.delComputationalFormula(id);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value =  "更新" , notes = "根据id更新计算公式")
    public ResultUtil<Boolean> update(@Validated ComputationalFormulaDto computationalFormulaDto){

        return computationalFormulaService.updComputationalFormula(computationalFormulaDto);
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value =  "新增计算公式" , notes = "新增计算公式")
    public ResultUtil<Long> insert(@Validated ComputationalFormulaInsertDto computationalFormulaInsertDto){

        return computationalFormulaService.addComputationalFormula(computationalFormulaInsertDto);
    }
}