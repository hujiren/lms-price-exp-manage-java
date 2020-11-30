package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.utils.ResultUtil;
import com.apl.lib.validate.ApiParamValidate;
import com.apl.lms.price.exp.manage.service.PriceExpProfitService;
import com.apl.lms.price.exp.pojo.dto.ExpPriceProfitDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author hjr
 * @since 2020-09-11
 */
@RestController
@RequestMapping(value = "/price-exp-profit")
@Validated
@Api(value = "报价利润", tags = "报价利润")
@Slf4j
public class PriceExpProfitController {

    @Autowired
    public PriceExpProfitService priceExpProfitService;

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", notes = "保存")
    public ResultUtil<Long> save(@Validated @RequestBody ExpPriceProfitDto expPriceProfitDto) throws IOException {
        ApiParamValidate.validate(expPriceProfitDto);
        ResultUtil<Long> longResultUtil = priceExpProfitService.saveProfit(expPriceProfitDto);
        return longResultUtil;

    }


}
