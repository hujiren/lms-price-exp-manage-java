package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.SpecialCommodityService;
import com.apl.lms.price.exp.pojo.po.SpecialCommodityPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @ApiOperation(value =  "获取特殊物品列表" , notes = "获取特殊物品列表")
    public ResultUtil<List<SpecialCommodityPo>> getList(){
        List<SpecialCommodityPo> list = specialCommodityService.getList();
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, list);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "删除" , notes = "根据id删除")
    @ApiImplicitParam(name = "id", value = "特殊物品Id", required = true, paramType = "query")
    public ResultUtil<Boolean> del(@NotNull(message = "id不能为空") Long id){

        return specialCommodityService.delSpecialCommodity(id);
    }

    @PostMapping(value = "/add-batch")
    @ApiOperation(value =  "批量新增" , notes = "批量新增特殊物品")
    public ResultUtil<Integer> insertBatch( @RequestBody List<SpecialCommodityPo> specialCommodityPoList){

        return specialCommodityService.addSpecialCommodity(specialCommodityPoList);
    }

}
