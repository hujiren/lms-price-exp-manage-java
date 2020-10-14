package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @Classname ReferencePriceDto
 * @Date 2020/9/11 16:33
 */
@Data
@ApiModel(value="快递引用价格表-插入对象", description="快递引用价格表-插入对象")
public class ReferencePriceDto extends PriceExpAddBaseDto{

    @ApiModelProperty(name = "priceDataId" , value = "价格数据表id", required = true)
    @Min(value = 0, message = "价格数据表id不能小于0")
    @NotNull( message = "价格数据表id不能为空")
    private Long priceDataId;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格表id", required = true)
    @Min(value = 0, message = "引用价格表id不能小于0")
    @NotNull( message = "引用价格表id不能为空")
    private Long quotePriceId;

    @ApiModelProperty(name = "orgCode" , value = "引用租户code", required = true)
    @NotBlank( message = "引用租户code不能为空")
    private String orgCode;
}
