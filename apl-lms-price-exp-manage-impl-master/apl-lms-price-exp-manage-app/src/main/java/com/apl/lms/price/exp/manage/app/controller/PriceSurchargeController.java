package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.utils.ResultUtil;
import com.apl.lib.validate.ApiParamValidate;
import com.apl.lms.price.exp.manage.service.PriceSurchargeService;
import com.apl.lms.price.exp.pojo.po.PriceSurchargePo;
import com.apl.lms.price.exp.pojo.vo.PriceSurchargeVo;
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
 * @Classname PriceSurchargeController
 * @Date 2020/9/28 11:27
 */
@RestController
@Api(value = "价格附加费", tags = "价格附加费")
@RequestMapping("/price-surcharge")
public class PriceSurchargeController {

    @Autowired
    public PriceSurchargeService priceSurchargeService;


    @PostMapping(value = "/save")
    @ApiOperation(value =  "批量保存",  notes ="批量保存")
    public ResultUtil<Boolean> save(@Validated @RequestBody List<PriceSurchargePo> priceSurchargePos) throws Exception {

        return priceSurchargeService.save(priceSurchargePos);
    }

    @PostMapping(value = "/del")
    @ApiOperation(value =  "删除" , notes = "删除")
    @ApiImplicitParam(name = "id",value = " id",required = true  , paramType = "query")
    public ResultUtil<Boolean> delById(@NotNull(message = "id不能为空") @Min(value = 1 , message = "id不能小于1") Long id) {

        return priceSurchargeService.delById(id);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取详细" , notes = "获取详细")
    @ApiImplicitParam(name = "priceId",value = "价格表id",required = true  , paramType = "query")
    public ResultUtil<List<PriceSurchargeVo>> selectById(@NotNull(message = "价格表id不能为空") @Min(value = 1 , message = "id不能小于1") Long priceId) throws Exception {

        return priceSurchargeService.selectById(priceId);
    }

}
