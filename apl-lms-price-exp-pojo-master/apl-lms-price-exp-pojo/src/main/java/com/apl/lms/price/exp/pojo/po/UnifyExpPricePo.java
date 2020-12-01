package com.apl.lms.price.exp.pojo.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @Classname UnifyExpPricePo
 * @Date 2020/11/4 9:39
 */
@Data
@ApiModel(value = "统一利润-持久化对象", description = "统一利润-持久化对象")
public class UnifyExpPricePo {

    @ApiModelProperty(name = "id" , value = "统一利润Id")
    private Long id;

    @ApiModelProperty(name = "customerGroupIds" , value = "客户组id")
    private String customerGroupIds;

    @ApiModelProperty(name = "customerGroupNames" , value = "客户组名称")
    private String customerGroupNames;

    @ApiModelProperty(name = "startWeight", value = "起始重")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight", value = "截止重")
    private Double endWeight;

    @ApiModelProperty(name = "firstWeightProfit", value = "首重加")
    private Double firstWeightProfit;

    @ApiModelProperty(name = "unitWeightProfit", value = "单位重加")
    private Double unitWeightProfit;

    @ApiModelProperty(name = "proportionProfit", value = "比例加")
    private Double proportionProfit;
}
