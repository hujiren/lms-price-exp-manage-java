package com.apl.lms.price.exp.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author hjr start
 * @Classname ExpPriceInfoBo
 * @Date 2020/9/15 11:37
 */
@Data
@ApiModel(value = "快递价格批量关联属性对象", description = "快递价格批量关联属性对象")
public class ExpPriceInfoBo {

    @ApiModelProperty(name = "id", value = "价格表id")
    private Long id;

    @ApiModelProperty(name = "quotePriceId", value = "引用价格id")
    private Long quotePriceId;

    @ApiModelProperty(name = "priceDataId", value = "价格数据表Id")
    private Long priceDataId;

    @ApiModelProperty(name = "innerOrgId", value = "租户Id")
    private Long innerOrgId;

    @ApiModelProperty(name = "priceFormat", value = "价格表格式")
    private Integer priceFormat;

    @ApiModelProperty(name = "updTime", value = "更新时间")
    private Timestamp updTime;

    @ApiModelProperty(name = "quotePriceUpdTime", value = "引用价格更新时间")
    private Timestamp quotePriceUpdTime;

    @ApiModelProperty(name = "channelCategory", value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "zoneId", value = "分区id")
    private Long zoneId;

    @ApiModelProperty(name = "zoneName", value = "分区表名称")
    private String zoneName;

    @ApiModelProperty(name = "endDate", value = "截止日期")
    private Timestamp endDate;

    @ApiModelProperty(name = "priceName", value = "价格表名称")
    private String priceName;
}
