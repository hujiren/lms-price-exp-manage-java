package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.PartnerService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PartnerPo;
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
 * @Classname PricePartnerController
 * @Date 2020/8/26 9:44
 */
@RestController
@RequestMapping("/partner")
@Validated
@Api(value = "服务商",tags = "服务商")
public class PartnerController {

    @Autowired
    PartnerService partnerService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页获取特殊物品列表" , notes = "根据关键字来查询")
    public ResultUtil<Page<PartnerPo>> getList(PageDto pageDto ,
                                               @Validated PartnerKeyDto partnerKeyDto){
        return partnerService.getList(pageDto, partnerKeyDto);
    }

    @PostMapping(value = "/del")
    @ApiOperation(value =  "删除" , notes = "根据id删除")
    @ApiImplicitParam(name = "id", value = "服务商id", required = true, paramType = "query")
    public ResultUtil<Boolean> del(@NotNull(message = "id不能为空") Long id){

        return partnerService.delPartner(id);
    }

    @PostMapping(value = "/upd")
    @ApiOperation(value =  "更新" , notes = "根据id更新服务商")
    public ResultUtil<Boolean> upd( @Validated PartnerPo partnerPo){

        return partnerService.updPartner(partnerPo);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value =  "新增" , notes = "新增服务商")
    public ResultUtil<Integer> add(@Validated PartnerAddDto partnerAddDto){

        return partnerService.addPartner(partnerAddDto);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取服务商详细" , notes = "获取服务商详细")
    @ApiImplicitParam(name = "id", value = "服务商Id", required = true, paramType = "query")
    public ResultUtil<PartnerPo> get(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return partnerService.getPartner(id);
    }
}
