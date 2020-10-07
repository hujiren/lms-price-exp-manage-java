package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lms.price.exp.manage.service.FreightTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.apl.lms.price.exp.pojo.po.FreightTypePo;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.validate.ApiParamValidate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @author hjr
 * @since 2020-10-07
 */
@RestController
@RequestMapping(value = "/freight-type")
@Validated
@Api(value = "运输类型",tags = "运输类型")
@Slf4j
public class FreightTypeController {

    @Autowired
    public FreightTypeService freightTypeService;

    @PostMapping(value = "/add")
    @ApiOperation(value =  "批量添加", notes ="批量添加")
    public ResultUtil<Long> add(@RequestBody @Validated List<FreightTypePo> freightTypePoList) {
        ApiParamValidate.validate(freightTypePoList);

        return freightTypeService.add(freightTypePoList);
    }

    @PostMapping(value = "/del")
    @ApiOperation(value =  "删除" , notes = "删除")
    @ApiImplicitParam(name = "id",value = " id",required = true  , paramType = "query")
    public ResultUtil<Boolean> delById(@NotNull(message = "id不能为空") @Min(value = 1 , message = "id不能小于1") Long id) {

        return freightTypeService.delById(id);
    }

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "查询列表" , notes = "查询列表")
    public ResultUtil<List<FreightTypePo>> getList() {
        List<FreightTypePo> list = freightTypeService.getList();
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, list);
    }

    @PostMapping(value = "/get-list-admin")
    @ApiOperation(value =  "查询列表-管理员通道" , notes = "查询列表-管理员通道")
    @ApiImplicitParam(name = "innerOrgId",value = " 租户id",required = true  , paramType = "query")
    public ResultUtil<List<FreightTypePo>> getList(Long innerOrgId) {
        List<FreightTypePo> list = freightTypeService.getListByInnerOrgId(innerOrgId);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, list);
    }
}
