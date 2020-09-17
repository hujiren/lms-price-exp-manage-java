package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import com.apl.lms.common.query.manage.dto.SpecialCommodityDto;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
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
public class PriceExpBaseUpdDto implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "价格表Id", required = true)
    @NotNull(message = "价格表id不能为空")
    private Long id;

    @ApiModelProperty(name = "priceMainId" , value = "主表Id", required = true)
    private Long mainId;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称", required = true)
    @NotBlank(message = "价格表名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "priceStatus" , value = "销售价格表状态 1正常 2计账 3无效")
    @TypeValidator(value = {"0","1","2","3"} , message = "销售价格表状态错误")
    private Integer priceStatus;

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

    @ApiModelProperty(name = "zoneId" , value = "分区表Id")
    @Min(value = 0, message = "分区表id最小值为0")
    private Long zoneId;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    @Range(min = 1, max = 3, message = "账号类型值只能为1或2或3")
    @NotNull(message = "账号类型不能为空")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数", required = true)
    @Range(min = 5000, max = 9999, message = "体积除数错误")
    @NotNull(message = "体积除数不能为空")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<SpecialCommodityDto> specialCommodity;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id")
    @Min(value = 0, message = "公布价最小值为0")
    private Long pricePublishedId;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是", required = true)
    @NotNull(message = "是否是公布价不能为空")
    @Range(min = 1, max = 2, message = "是否是公布价错误")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    @ApiModelProperty(name = "remark" , value = "备注")
    private String remark;


}
