package com.apl.lms.price.exp.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:36
 */
@Data
@ApiModel(value="快递销售价格表-组装返回对象", description="快递销售价格表-组装返回对象")
public class PriceExpSaleVo {

    @ApiModelProperty(name = "customerGroupIds" , value = "客户组ids")
    private String customerGroupIds;

    @ApiModelProperty(name = "customerGroupNames" , value = "客户组名称")
    private String customerGroupNames;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private String customerIds;

    @ApiModelProperty(name = "customerNames" , value = "客户名称")
    private String customerNames;


}
