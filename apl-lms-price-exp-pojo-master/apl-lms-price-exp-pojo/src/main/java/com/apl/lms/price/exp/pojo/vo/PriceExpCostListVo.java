package com.apl.lms.price.exp.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:36
 */
@Data
@ApiModel(value="快递成本价格表-列表返回对象", description="快递成本价格表-列表返回对象")
public class PriceExpCostListVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格表id")
    private Long id;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "报价名称")
    private String priceName;

    @ApiModelProperty(name = "partnerId" , value = "成本价id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long partnerId;

    @ApiModelProperty(name = "partnerName" , value = "服务商")
    private String partnerName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "startDate" , value = "起始日期")
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
    private Timestamp endDate;

    @ApiModelProperty(name = "currency" , value = "币制")
    private String currency;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "accountType" , value = "账号类型")
    private Integer accountType;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private String specialCommodity;

    @ApiModelProperty(name = "priceStatus" , value = "成本价格状态")
    private Integer priceStatus;

    @ApiModelProperty(name = "synStatus" , value = "同步状态 1同步成功 2同步异常 3被引用价格已删除")
    private Integer synStatus;

}
