package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/8 - 9:17
 */
@Data
@ApiModel(value = "特殊物品插入对象", description = "特殊物品插入对象")
@TableName("special_commodity")
public class SpecialCommodityAddDto {

    @ApiModelProperty(name = "id", value = "特殊物品id", hidden = true)
    private Long id;

    @ApiModelProperty(name = "specialCommodityName", value = "特殊物品名称", required = true)
    @NotBlank(message = "特殊物品名称不能为空")
    private String specialCommodityName;

    @ApiModelProperty(name = "specialCommodityNameEn", value = "特殊物品英文名称", required = true)
    @NotBlank(message = "特殊物品英文名称不能为空")
    private String specialCommodityNameEn;

    @ApiModelProperty(name = "code", value = "代码", required = true)
    @NotNull(message = "code不能为空")
    private Integer code;
}
