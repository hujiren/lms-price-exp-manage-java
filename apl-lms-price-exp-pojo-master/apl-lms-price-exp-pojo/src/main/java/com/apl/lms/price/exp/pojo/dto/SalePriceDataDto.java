package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hjr start
 * @Classname SalePriceDataDto
 * @Date 2020/11/7 9:28
 */
@Data
@ApiModel(value = "获取销售价格表-交互对象", description = "获取销售价格表-交互对象")
public class SalePriceDataDto implements Serializable {

    @ApiModelProperty(name = "id", value = "价格表Id", required = true)
    @NotNull(message = "价格表Id不能为空")
    private Long id;

    @ApiModelProperty(name = "customerGroupId", value = "客户组id", required = true)
    @NotNull(message = "客户组id不能为空")
    private Long customerGroupId;
}
