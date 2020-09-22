package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpProfitAssembleDto
 * @Date 2020/9/22 18:08
 */
@Data
@ApiModel(value = "利润组装对象", description = "利润组装对象")
public class PriceExpProfitAssembleDto {

    @ApiModelProperty(name = "id", value = "id")
    private Long id;

    @ApiModelProperty(name = "profitDtoList", value = "利润列表")
    List<PriceExpProfitDto> profitDtoList;
}
