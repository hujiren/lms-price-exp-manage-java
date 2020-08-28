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
@ApiModel(value="成本价格表  按条件查询对象", description="成本价格表  按条件查询对象")
public class  PriceExpCostListKeyDto {

    @ApiModelProperty(name = "volumeDivisor", value = "体积基数")
    @Range(min = 1, message = "体积基数不能小于1")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "accountType", value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
    @Min(value = 0, message = "账号类型不能小于0")
    private Integer accountType;

    @ApiModelProperty(name = "mainStatus", value = "价格主表状态 1正常 2计账 3无效 4即将过期 5已过期")
    @TypeValidator(value = {"0","1","2","3","4","5"} , message = "价格主表状态错误")
    @Min(value = 0, message = "价格主表状态不能小于0")
    private Integer mainStatus;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是", hidden = true)
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "partnerId", value = "服务商id")
    @Min(value = 0, message = "服务商id不能小于0")
    private Long partnerId;

    @ApiModelProperty(name = "priceStatus", value = "成本价格表状态 1正常 2计账 3无效")
    @TypeValidator(value = {"0","1","2","3"} , message = "成本价格表状态错误")
    @Min(value = 0, message = "成本价格表状态不能小于0")
    private Integer priceStatus;

    @ApiModelProperty(name = "channelCategory", value = "成本价渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "keyword", value = "关键词: 按价格表名称")
    private String keyword;

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
