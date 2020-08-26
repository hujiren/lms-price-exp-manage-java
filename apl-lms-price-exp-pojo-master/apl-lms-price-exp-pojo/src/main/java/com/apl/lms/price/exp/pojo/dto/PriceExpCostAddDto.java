package com.apl.lms.price.exp.pojo.dto;
import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @Classname PriceExpCostPo
 * @Date 2020/8/20 14:43
 */
@Data
@ApiModel(value="成本价格表  持久化对象", description="成本价格表 持久化对象")
public class PriceExpCostAddDto extends Model<PriceExpCostAddDto> {


    @ApiModelProperty(name = "partnerId" , value = "合作商id")
    @NotNull(message = "合作商id不能为空")
    private Long partnerId;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格id", hidden = true)
    private Integer quotePriceId;

    @ApiModelProperty(name = "quotePriceFinalId" , value = "引用价格最终id", hidden = true)
    private Integer quotePriceFinalId;

    @ApiModelProperty(name = "priceStatus" , value = "成本价格表状态 1正常 2计账 3无效",hidden = true)
    @TypeValidator(value = {"0","1","2","3"} , message = "价格表状态错误")
    private Integer priceStatus;

    @ApiModelProperty(name = "costPriceCode" , value = "成本价格表代码")
    @NotBlank(message = "成本价格表代码不能为空")
    private String costPriceCode;

    @ApiModelProperty(name = "costPriceName" , value = "成本价格表名称")
    @NotBlank(message = "成本价格表名称不能为空")
    private String costPriceName;

    @ApiModelProperty(name = "costChannelCategory" , value = "成本价格渠道类型")
    @NotBlank(message = "成本价格渠道类型不能为空")
    private String costChannelCategory;

    @ApiModelProperty(name = "costPriceMainId", value = "成本价格-主表Id")
    private Long costPriceMainId;

    @ApiModelProperty(name = "costSaleRemark", value = "成本价格销售备注")
    private String costSaleRemark;

    @ApiModelProperty(name = "costPartnerRemark", value = "成本价格表合作商备注")
    private String costPartnerRemark;
}
