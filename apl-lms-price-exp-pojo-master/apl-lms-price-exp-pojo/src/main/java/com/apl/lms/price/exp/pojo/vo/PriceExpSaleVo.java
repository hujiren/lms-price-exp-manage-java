package com.apl.lms.price.exp.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ApiModelProperty(name = "id" , value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "priceCode" , value = "销售价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "销售价格表名称")
    private String priceName;

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private String customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private String customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "priceStatus" , value = "价格表状态")
    private Integer priceStatus;

    @ApiModelProperty(name = "priceMainId" , value = "主表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long priceMainId;





}
