package com.apl.lms.price.exp.manage.app.controller;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.pojo.dto.PriceExpListDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpListInsertDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpListKeyDto;
import com.apl.lms.price.exp.pojo.vo.PriceExpInfoVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:26
 */
@RestController
@RequestMapping("/exp-list")
@Validated
@Api(value = "快递价格",tags = "快递价格")
public class PriceExpController {

    @Autowired
    PriceExpService priceExpService;

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页获取快递价格列表" , notes = "根据状态, 体基, 账号类型, 渠道类型, 客户组, 关键字来查询")
    public ResultUtil<Page<PriceExpListVo>> getList(PageDto pageDto , @Validated PriceExpListKeyDto priceExpListKeyDto){

        return priceExpService.getPriceExpList(pageDto, priceExpListKeyDto);
    }

    @PostMapping(value = "/delete")
    @ApiOperation(value =  "批量删除" , notes = "根据ids删除快递价格")
    public ResultUtil<Boolean> delete(@NotEmpty(message = "ids不能为空") @RequestBody List<Long> ids){

        return priceExpService.delPriceExp(ids);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value =  "更新" , notes = "根据Id修改快递价格")
    public ResultUtil<Boolean> update(@Validated PriceExpListDto priceExpListDto){

        return priceExpService.updPriceExp(priceExpListDto);
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value =  "新增" , notes = "新增快递价格")
    public ResultUtil<Long> insert(@Validated PriceExpListInsertDto priceExpListInsertDto){

        String[] customerName = priceExpListInsertDto.getCustomerName().replaceAll(" ", "").split(",");
        if(priceExpListInsertDto.getCustomerIds().size() != customerName.length){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL.code, "传入的客户信息不匹配", null);
        }

        String[] customerGroup = priceExpListInsertDto.getCustomerGroupsName().replaceAll(" ", "").split(",");
        if(priceExpListInsertDto.getCustomerGroupsId().size() != customerGroup.length) {
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL.code, "传入的客户组信息不匹配", null);
        }

        return priceExpService.insPriceExp(priceExpListInsertDto);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取快递价格详情" , notes = "获取快递价格详情")
    @ApiImplicitParam(name = "id",value = "id",required = true  , paramType = "query")
    public ResultUtil<PriceExpInfoVo> get(@NotNull(message = "id不能为空") @Min(value = 1, message = "id不能小于1") Long id){

        return priceExpService.getPriceExpInfo(id);
    }

}
