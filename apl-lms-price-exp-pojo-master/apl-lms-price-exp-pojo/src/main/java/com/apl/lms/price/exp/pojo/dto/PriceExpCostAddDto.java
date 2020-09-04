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
 * @Classname PriceExpCostPo
 * @Date 2020/8/20 14:43
 */
@Data
@ApiModel(value="成本价格表  持久化对象", description="成本价格表 持久化对象")
public class PriceExpCostAddDto extends Model<PriceExpCostAddDto> {


    @ApiModelProperty(name = "partnerId" , value = "服务商id", required = true)
    @Min(value = 0, message = "服务商id不能小于0")
    @NotNull( message = "服务商id不能为空")
    private Long partnerId;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称", required = true)
    @NotBlank(message = "价格表名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "partnerRemark", value = "服务商备注")
    private String partnerRemark;

}
