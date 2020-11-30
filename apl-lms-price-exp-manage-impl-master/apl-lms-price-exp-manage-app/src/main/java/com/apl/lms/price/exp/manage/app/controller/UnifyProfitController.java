package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.validate.ApiParamValidate;
import com.apl.lms.price.exp.manage.service.UnifyProfitService;
import com.apl.lms.price.exp.pojo.dto.UnifyProfitDto;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author hjr start
 * @Classname UnifyProfitController
 * @Date 2020/11/4 9:51
 */
@RestController
@RequestMapping("/unify-profit")
@Validated
@Slf4j
@Api(value = "统一利润", tags = "统一利润")
public class UnifyProfitController {

    @Autowired
    UnifyProfitService unifyProfitService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存统一利润", notes = "保存统一利润")
    public ResultUtil<Boolean> save(@Validated @RequestBody UnifyProfitDto unifyProfitDto) throws IOException {
        ApiParamValidate.validate(unifyProfitDto);

        unifyProfitService.saveUnifyProfit(unifyProfitDto);
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS , true);

    }

    @PostMapping(value = "/del")
    @ApiOperation(value = "批量删除统一利润", notes = "批量删除统一利润")
    public ResultUtil<Boolean> del(@RequestBody List<Long> ids) throws IOException {
        Integer result = unifyProfitService.del(ids);
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS , result);
    }


    @PostMapping(value = "/get-list")
    @ApiOperation(value = "查询统一利润列表", notes = "按或者不按客户组id查询")
    @ApiImplicitParam(name = "customerGroupId", value = "客户组id", required = false, paramType = "query")
    public ResultUtil<List<UnifyProfitDto>> getList(Long customerGroupId) {
        List<UnifyProfitDto> list = unifyProfitService.getList(customerGroupId, 0L);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, list);
    }

}
