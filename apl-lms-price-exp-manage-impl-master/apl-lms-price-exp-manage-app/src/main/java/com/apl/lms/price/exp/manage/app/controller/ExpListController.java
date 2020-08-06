package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.ExpListDto;
import com.apl.lms.price.exp.pojo.dto.ExpListKeyDto;
import com.apl.lms.price.exp.pojo.vo.ExpListVo;
import com.apl.lms.price.exp.manage.service.ExpListService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:26
 */
@RestController
@RequestMapping("/exp-list")
@Validated
@Api(value = "快递价格",tags = "快递价格")
public class ExpListController {

    @Autowired
    ExpListService expListService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页获取快递价格列表" , notes = "根据状态, 体基, 账号类型, 渠道类型, 客户组, 关键字来查询")
    @ApiImplicitParam(name = "expListKeyDto",value = "快递价格分页查询对象",required = true  , paramType = "query")
    public ResultUtil<Page<ExpListVo>> getList(PageDto pageDto ,
                                                  @NotNull(message = "快递价格对象不能为空") @Validated ExpListKeyDto expListKeyDto){

        return expListService.getExpList(pageDto, expListKeyDto);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "删除" , notes = "根据id删除快递价格")
    @ApiImplicitParam(name = "id",value = "id",required = true  , paramType = "query")
    public ResultUtil<Boolean> delete(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return expListService.delExpList(id);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value =  "更新" , notes = "根据Id修改快递价格")
    @ApiImplicitParam(name = "expListDto", value = "快递价格修改对象", required = true, paramType = "query")
    public ResultUtil<Boolean> update(@NotNull(message = "快递价格对象不能为空") @Validated ExpListDto expListDto){

        return expListService.updExpList(expListDto);
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value =  "新增" , notes = "新增快递价格")
    @ApiImplicitParam(name = "expListDto", value = "快递价格新增对象", required = true, paramType = "query")
    public ResultUtil<Long> insert(@NotNull(message = "快递价格对象不能为空") @Validated ExpListDto expListDto){

        return expListService.insExpList(expListDto);
    }

}
