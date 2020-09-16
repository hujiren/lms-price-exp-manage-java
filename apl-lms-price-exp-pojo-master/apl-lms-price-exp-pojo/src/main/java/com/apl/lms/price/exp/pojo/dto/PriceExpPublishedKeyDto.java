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
@ApiModel(value="快递成本价格-条件查询对象", description="快递价格成本价格-条件查询对象")
public class PriceExpPublishedKeyDto {


    @ApiModelProperty(name = "priceStatus", value = "价格表状态 1正常 2计账 3无效")
    @TypeValidator(value = {"0","1","2","3"} , message = "成本价格表状态错误")
    @Min(value = 0, message = "价格表状态不能小于0")
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
