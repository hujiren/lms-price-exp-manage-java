package com.apl.lms.price.exp.lib.cache.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020/8/8 - 9:17
 */
@Data
public class SpecialCommodityCacheBo {

    @ApiModelProperty(name = "id", value = "特殊物品id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "specialCommodityName", value = "特殊物品名称")
    private String specialCommodityName;
}
