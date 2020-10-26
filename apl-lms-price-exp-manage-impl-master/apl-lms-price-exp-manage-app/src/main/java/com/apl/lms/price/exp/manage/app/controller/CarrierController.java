package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lms.price.exp.manage.service.CarrierService;
import com.apl.lms.price.exp.pojo.po.CarrierPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping(value = "/carrier")
@Validated
@Api(value = "运输方",tags = "运输方")
@Slf4j
public class CarrierController {

    @Autowired
    public CarrierService carrierService;

    @PostMapping(value = "/add")
    @ApiOperation(value =  "添加", notes ="添加")
    public ResultUtil<Long> add(@Validated CarrierPo carrierPo) {
        ApiParamValidate.validate(carrierPo);


        return carrierService.add(carrierPo);
    }

    @PostMapping(value = "/upd")
    @ApiOperation(value =  "更新",  notes ="更新")
    public ResultUtil<Boolean> updById(@Validated CarrierPo carrierPo) {
        ApiParamValidate.notEmpty("id", carrierPo.getId());
        ApiParamValidate.validate(carrierPo);

        return carrierService.updById(carrierPo);
    }

    @PostMapping(value = "/del")
    @ApiOperation(value =  "删除" , notes = "删除")
    @ApiImplicitParam(name = "id",value = " id",required = true  , paramType = "query")
    public ResultUtil<Boolean> delById(@NotNull(message = "id不能为空") @Min(value = 1 , message = "id不能小于1") Long id) {

        return carrierService.delById(id);
    }

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "查找列表" , notes = "查找列表")
    public ResultUtil<List<CarrierPo>> getList() {

        return carrierService.getList();
    }

    @PostMapping(value = "/get-list-selector")
    @ApiOperation(value =  "查找列表-选择器" , notes = "查找列表-选择器")
    @ApiImplicitParam(name = "innerOrgId",value = " 租户id",required = true  , paramType = "query")
    public ResultUtil<List<CarrierPo>> getList(Long innerOrgId) {

        return carrierService.getListByInnerOrgId(innerOrgId);
    }
}
