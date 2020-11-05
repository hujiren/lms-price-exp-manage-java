package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceReferenceDto
 * @Date 2020/11/4 15:01
 */
@Data
@ApiModel(value = "引用价格-前后端交互对象", description = "引用价格-前后端交互对象")
public class PriceReferenceDto {

    @ApiModelProperty(name = "quotePriceId", value = "引用价格id", required = true)
    @NotNull(message = "引用价格id不能为空")
    @Min(value = 0, message = "引用价格id不能小于0")
    private Long quotePriceId;

    @ApiModelProperty(name = "channelCategory", value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "priceCode", value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName", value = "价格表名称", required = true)
    @NotBlank(message = "价格表名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "priceSaleName", value = "销售价名称")
    private String priceSaleName;

    @ApiModelProperty(name = "partnerId", value = "服务商id", required = true)
    @NotNull(message = "服务商id不能为空")
    @Min(value = 0, message = "服务商id不能小于0")
    private Long partnerId;

    @ApiModelProperty(name = "partnerName", value = "服务商名称", required = true)
    @NotBlank(message = "服务商名称不能为空")
    private String partnerName;

    @ApiModelProperty(name = "customer", value = "客户")
    private List<CustomerDto> customer;

    @ApiModelProperty(name = "customerGroup", value = "客户组")
    private List<CustomerGroupDto> customerGroup;

    @ApiModelProperty(name = "tenantCode", value = "引用租户code", required = true)
    @NotBlank(message = "引用租户code不能为空")
    private String tenantCode;

    @ApiModelProperty(name = "addProfitWay", value = "添加利润的方式 0不加 1单独加 2统一加", required = true)
    @NotNull(message = "添加利润方式不能为空")
    private Integer addProfitWay;
}
