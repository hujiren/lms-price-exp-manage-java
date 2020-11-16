package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hjr start
 * @Classname IncreaseProfitDto
 * @Date 2020/11/14 11:06
 */
@Data
@ApiModel(value = "增加的利润-保存对象", description = "增加的利润-保存对象")
public class IncreaseProfitDto {

    @ApiModelProperty(name = "id", value = "价格表id", required = true)
    private Long id;

    @ApiModelProperty(name = "addProfitWay", value = "添加利润方式 0不加 1单独加 2统一加", required = true)
    private Integer addProfitWay;

    @ApiModelProperty(name = "increaseProfit", value = "增加的利润", required = true)
    private List<PriceExpProfitDto> increaseProfit;
}
