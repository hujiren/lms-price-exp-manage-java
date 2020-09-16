package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.SurchargeService;
import com.apl.lms.price.exp.pojo.dto.SurchargeUpdDto;
import com.apl.lms.price.exp.pojo.po.SurchargePo;
import com.apl.lms.price.exp.pojo.dto.SurchargeKeyDto;
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
@RequestMapping("/surcharge")
@Validated
@Api(value = "附加费",tags = "附加费")
public class SurchargeController {

    @Autowired
    SurchargeService surchargeService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页获取附加费列表" , notes = "根据关键字来查询")
    public ResultUtil<Page<SurchargePo>> getList(PageDto pageDto ,
                                                     @Validated SurchargeKeyDto surchargeKeyDto){
        return surchargeService.getList(pageDto, surchargeKeyDto);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "删除" , notes = "根据id删除")
    @ApiImplicitParam(name = "id", value = "附加费Id", required = true, paramType = "query")
    public ResultUtil<Boolean> del(@NotNull(message = "id不能为空") Long id){

        return surchargeService.delSurcharge(id);
    }

    @PostMapping(value = "/upd")
    @ApiOperation(value =  "更新" , notes = "根据id更新附加费")
    public ResultUtil<Boolean> upd( @Validated SurchargeUpdDto surchargeUpdDto){

        return surchargeService.updSurcharge(surchargeUpdDto);
    }

    @PostMapping(value = "/add-batch")
    @ApiOperation(value =  "批量新增" , notes = "批量新增附加费")
    public ResultUtil<Integer> addBatch(@RequestBody List<SurchargePo> surchargePoList){

        return surchargeService.addSurcharge(surchargePoList);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取附加费详细" , notes = "获取附加费详细")
    @ApiImplicitParam(name = "id", value = "附加费id", required = true, paramType = "query")
    public ResultUtil<SurchargePo> get(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return surchargeService.getSurcharge(id);
    }
}
