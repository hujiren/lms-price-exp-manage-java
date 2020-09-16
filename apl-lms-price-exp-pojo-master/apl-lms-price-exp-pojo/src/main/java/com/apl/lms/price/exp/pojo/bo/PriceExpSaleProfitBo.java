package com.apl.lms.price.exp.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpSaleProfitBo
 * @Date 2020/9/11 11:14
 */
@Data
@ApiModel(value = "快递销售价格利润-组装对象", description = "快递销售价格利润-组装对象")
public class PriceExpSaleProfitBo {

    @ApiModelProperty(name = "customerGroup" , value = "客户组", required = true)
    @NotBlank(message = "客户组不能为空")
    private Long customerGroup;

    @ApiModelProperty(name = "zoneNum", value = "分区号")
    @Length(max = 3, message = "分区号最大长度为3")
    private String zoneNum;

    @ApiModelProperty(name = "countryCode", value = "国家简码")
    @Length(max = 12, message = "国家简码最大长度为12")
    private String countryCode;

    @ApiModelProperty(name = "startWeight", value = "起始重", required = true)
    @NotNull(message = "起始重不能为空")
    @Min(value = 0, message = "起始重不能小于0")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight", value = "截止重", required = true)
    @NotNull(message = "截止重不能为空")
    @Min(value = 0, message = "截止重不能小于0")
    private Double endWeight;

    @ApiModelProperty(name = "firstWeightProfit", value = "为首重增加利润")
    private Double firstWeightProfit;

    @ApiModelProperty(name = "unitWeightProfit", value = "按单位重量增加利润")
    private Double unitWeightProfit;

    @ApiModelProperty(name = "proportionProfit", value = "按比例增加利润")
    private Double proportionProfit;
}
