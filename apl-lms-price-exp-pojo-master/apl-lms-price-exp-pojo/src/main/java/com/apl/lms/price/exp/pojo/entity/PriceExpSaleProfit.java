package com.apl.lms.price.exp.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @author hjr start
 * @Classname PriceExpSaleProfit
 * @Date 2020/9/11 11:14
 */
@Data
@ApiModel(value = "销售利润", description = "销售利润")
public class PriceExpSaleProfit {

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称", required = true)
    @NotBlank(message = "客户组不能为空")
    private String customerGroupsName;

    @ApiModelProperty(name = "zoneNum", value = "分区号")
    private String zoneNum;

    @ApiModelProperty(name = "countryCode", value = "国家简码")
    private String countryCode;

    @ApiModelProperty(name = "startWeight", value = "起始重", required = true)
    private Double startWeight;

    @ApiModelProperty(name = "endWeight", value = "截止重", required = true)
    private Double endWeight;

    @ApiModelProperty(name = "firstWeightProfit", value = "为首重增加利润")
    private Double firstWeightProfit;

    @ApiModelProperty(name = "unitWeightProfit", value = "按单位重量增加利润")
    private Double unitWeightProfit;

    @ApiModelProperty(name = "proportionProfit", value = "按比例增加利润")
    private Double proportionProfit;
}
