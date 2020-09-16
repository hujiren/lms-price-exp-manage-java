package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hjr start
 * @Classname PriceExpAddDto
 * @Date 2020/9/1 15:48
 */
@Data
@ApiModel(value="快递价格表  插入对象", description="快递价格表 插入对象")
public class PriceExpCostUpdDto extends PriceExpUpdDto implements Serializable {


    @ApiModelProperty(name = "id" , value = "id", required = true)
    @NotNull(message = "id不能为空")
    @Min(value = 0, message = "id不能小于0")
    private Long id;

    @ApiModelProperty(name = "partnerId" , value = "服务商id", required = true)
    @Min(value = 0, message = "服务商id不能为空")
    private Long partnerId;

}
