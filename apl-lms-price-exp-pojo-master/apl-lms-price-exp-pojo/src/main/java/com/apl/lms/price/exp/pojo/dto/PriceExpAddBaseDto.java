package com.apl.lms.price.exp.pojo.dto;

import com.apl.lms.common.query.manage.dto.SpecialCommodityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpAddDto
 * @Date 2020/9/1 15:48
 */
@Data
public class PriceExpAddBaseDto implements Serializable {

    @ApiModelProperty(name = "startDate" , value = "起始日期", required = true)
    @NotNull(message = "起始日期不能为空")
    @Min(value = 1, message = "起始日期最小值为1")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期", required = true)
    @NotNull(message = "截止日期不能为空")
    @Min(value = 1, message = "截止日期最小值为1")
    private Long endDate;

    @ApiModelProperty(name = "currency" , value = "币制", required = true)
    @NotBlank(message = "币制不能为空")
    private String currency;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    @Range(min = 1, max = 3, message = "账号类型值只能为1或2或3")
    @NotNull(message = "账号类型不能为空")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<SpecialCommodityDto> specialCommodity;

    @ApiModelProperty(name = "priceFormat" , value = "价格表格式 1横向 2纵向")
    @Range(min = 1, max = 2, message = "价格表格式值只能为1或2")
    private Integer priceFormat;

    @ApiModelProperty(name = "startWeight" , value = "起始重", required = true)
    @NotNull(message = "起始重量不能为空")
    @Min(value = -1, message = "起始重不能小于0")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重", required = true)
    @Range(min = 0, max = 100000, message = "截止重量最小值为0, 最大值为100000")
    @NotNull(message = "截止重量不能为空")
    private Double endWeight;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id")
    private Long pricePublishedId;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是", required = true)
    @NotNull(message = "是否是公布价不能为空")
    @Range(min = 1, max = 2, message = "是否是公布价错误")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数", required = true)
    @Range(min = 5000, max = 9999, message = "体积除数错误")
    @NotNull(message = "体积除数不能为空")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "zoneId" , value = "分区表Id")
    private Long zoneId;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "报价名称", required = true)
    @NotBlank(message = "报价名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "priceSaleName" , value = "销售名称")
    private String priceSaleName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    private Long partnerId;

    @ApiModelProperty(name = "partnerName" , value = "服务商名称")
    private String partnerName;

    @ApiModelProperty(name = "customerGroup" , value = "客户组")
    private List<CustomerGroupDto> customerGroup;

    @ApiModelProperty(name = "customer" , value = "客户")
    private List<CustomerDto> customer;

    @ApiModelProperty(name = "remark" , value = "备注")
    private String remark;

    @ApiModelProperty(name = "saleRemark", value = "销售备注")
    private String saleRemark;
}
