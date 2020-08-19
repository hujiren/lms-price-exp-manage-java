package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author hjr start
 * @date 2020/8/5 - 12:03
 */
@Data
@ApiModel(value="快递价格表  按条件查询对象", description="快递价格表  按条件查询对象")
public class PriceExpListKeyDto {

    @ApiModelProperty(name = "channelCategory", value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "volumeWeightCardinal", value = "体积基数")
    @Range(min = 1, message = "体积基数不能小于1")
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "accountType", value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
    private Integer accountType;

    @ApiModelProperty(name = "priceStatus", value = "价格表状态 1正常 2计账 3无效 4即将过期 5已过期")
    @TypeValidator(value = {"0","1","2","3","4","5"} , message = "价格表状态错误")
    private Integer priceStatus;

    @ApiModelProperty(name = "customerGroupsId", value = "客户组id")
    private Long customerGroupsId;

    @ApiModelProperty(name = "customerId", value = "客户id")
    private Long customerId;

    @ApiModelProperty(name = "forwarderId", value = "货代id")
    private Long forwarderId;

    @ApiModelProperty(name = "keyword", value = "关键词: 按客户名称, 价格表名称, 销售名称模糊查询")
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
