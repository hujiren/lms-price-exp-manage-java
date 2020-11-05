package com.apl.lms.price.exp.pojo.vo;
import com.apl.lms.common.query.manage.dto.SpecialCommodityDto;
import com.apl.lms.price.exp.pojo.dto.CustomerDto;
import com.apl.lms.price.exp.pojo.dto.CustomerGroupDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:36
 */
@Data
@ApiModel(value="快递销售价格表-详细信息返回对象", description="快递销售价格表-详细信息返回对象")
public class PriceExpPriceInfoVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格表id")
    private Long id;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceSaleName" , value = "报价销售名称")
    private String priceSaleName;

    @ApiModelProperty(name = "priceName" , value = "报价名称")
    private String priceName;

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long partnerId;

    @ApiModelProperty(name = "partnerName" , value = "服务商")
    private String partnerName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "priceStatus" , value = "状态 1正常 2计账 3无效")
    private Integer priceStatus;

    @ApiModelProperty(name = "startDate" , value = "起始日期")
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
    private Timestamp endDate;

    @ApiModelProperty(name = "currency" , value = "币制")
    private String currency;

    @ApiModelProperty(name = "zoneTabId" , value = "分区表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long zoneId;

    @ApiModelProperty(name = "zoneName" , value = "分区表名称")
    private String zoneName;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "specialCommodityStr" , value = "特殊物品", hidden = true)
    private String specialCommodityStr;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<SpecialCommodityDto> specialCommodity;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "priceFormat" , value = "价格表格式")
    private Integer priceFormat;

    @ApiModelProperty(name = "startWeight" , value = "起始重")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重")
    private Double endWeight;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    @ApiModelProperty(name = "priceDataId", value = "价格数据表id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long priceDataId;

    @ApiModelProperty(name = "customerGroup", value = "客户组")
    private List<CustomerGroupDto> customerGroup;

    @ApiModelProperty(name = "customer", value = "客户")
    private List<CustomerDto> customer;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long pricePublishedId;

    @ApiModelProperty(name = "publishedName" , value = "公布价名称")
    private String publishedName;

    @ApiModelProperty(name = "innerOrgId" , value = "租户id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long innerOrgId;

    @ApiModelProperty(name = "quoteTenantCode" , value = "引用租户code")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String quoteTenantCode;

    @ApiModelProperty(name = "orgCode" , value = "租户code")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String orgCode;

    @ApiModelProperty(name = "updTime" , value = "更新时间")
    private Timestamp updTime;

    @ApiModelProperty(name = "quotePriceUpdTime" , value = "引用价格更新时间")
    private Timestamp quotePriceUpdTime;

    @ApiModelProperty(name = "synStatus" , value = "同步状态 1同步成功 2同步异常 3引用价格已删除")
    private Integer synStatus;

}
