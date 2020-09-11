package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @Classname ReferenceCostDto
 * @Date 2020/9/11 16:26
 */
@Data
@ApiModel(value="引用成本价格对象", description="引用成本价格对象")
public class ReferenceCostDto extends Model<ReferenceCostDto> {

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    private Long partnerId;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格id", required = true)
    @Min(value = 0, message = "引用价格id不能小于0")
    @NotNull( message = "引用价格id不能为空")
    private Long quotePriceId;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称", required = true)
    @NotBlank(message = "价格表名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;
}
