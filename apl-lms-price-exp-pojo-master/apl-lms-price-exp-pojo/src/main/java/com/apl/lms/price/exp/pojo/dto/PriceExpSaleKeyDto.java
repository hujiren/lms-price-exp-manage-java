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
@ApiModel(value="快递销售价格表-条件查询对象", description="快递销售价格表-按条件查询对象")
public class PriceExpSaleKeyDto {

    @ApiModelProperty(name = "priceType", value = "价格表类型", hidden = true)
    private Integer priceType;

    @ApiModelProperty(name = "volumeDivisor", value = "体积基数")
    @Range(min = 1, message = "体积基数不能小于1")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "accountType", value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
    @Min(value = 0, message = "账号类型不能小于0")
    private Integer accountType;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是", hidden = true)
    @TypeValidator(value = {"0","1","2"} , message = "公布价错误")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "customerGroupId", value = "客户组id")
    private Long customerGroupId;

    @ApiModelProperty(name = "specialCommodity", value = "特殊物品code")
    private Integer specialCommodity;

    @ApiModelProperty(name = "customerId", value = "客户id")
    private Long customerId;

    @ApiModelProperty(name = "priceStatus", value = "价格表状态 0全部 1正常(默认) 2计账 3无效 4未过期 5即将过期 6已过期")
    @TypeValidator(value = {"0","1","2","3","4","5","6"} , message = "价格表状态错误")
    @Min(value = 0, message = "价格表状态不能小于0")
    private Integer priceStatus;

    @ApiModelProperty(name = "channelCategory", value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "keyword", value = "关键词: 报价名称")
    private String keyword;

    @ApiModelProperty(name = "isQuote" , value = "是否引用 0全部 1是 2否")
    private Integer isQuote;

    @ApiModelProperty(name = "innerOrgId", value = "租户id", hidden = true)
    private Long innerOrgId;

    @ApiModelProperty(name = "synStatus" , value = "同步状态 0未同步 1同步成功 2同步异常 3引用价格表已被删除 4全部")
    private Integer synStatus;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }


    public String getChannelCategory() {
        if (channelCategory != null){
            if(channelCategory.trim().equals(""))
                channelCategory = null;
            else
                channelCategory = channelCategory.toUpperCase();
        }

        return channelCategory;
    }
}
