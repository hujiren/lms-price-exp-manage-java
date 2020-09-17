package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 * @author hjr start
 * @date 2020/8/5 - 12:03
 */
@Data
@ApiModel(value="快递成本价格表-条件查询对象", description="快递成本价格表-条件查询对象")
public class PriceExpCostKeyDto {

    @ApiModelProperty(name = "volumeDivisor", value = "体积基数")
    @Range(min = 1, message = "体积基数不能小于1")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "accountType", value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
    @Min(value = 0, message = "账号类型不能小于0")
    private Integer accountType;

    @ApiModelProperty(name = "specialCommodity", value = "特殊物品code")
    private Integer specialCommodity;

    @ApiModelProperty(name = "partnerId", value = "服务商id")
    @Min(value = 0, message = "服务商id不能小于0")
    private Long partnerId;

    @ApiModelProperty(name = "priceStatus", value = "销售价格表状态 1正常 2计账 3无效 4未过期 5即将过期 6已过期")
    @TypeValidator(value = {"0","1","2","3","4","5","6"} , message = "销售价格表状态错误")
    @Min(value = 0, message = "销售价格表状态不能小于0")
    private Integer priceStatus;

    @ApiModelProperty(name = "channelCategory", value = "成本价渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "keyword", value = "关键词: 按价格表名称")
    private String keyword;

    @ApiModelProperty(name = "specialCommodity2", value = "特殊物品code", hidden = true)
    private String specialCommodityCopy;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }

    public String getChannelCategory() {
        if (channelCategory != null && channelCategory.trim().equals(""))
            channelCategory = null;

        return channelCategory;
    }
}
