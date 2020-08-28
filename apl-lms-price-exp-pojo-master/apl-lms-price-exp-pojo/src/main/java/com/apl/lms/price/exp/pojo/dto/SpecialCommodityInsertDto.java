package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hjr start
 * @date 2020/8/8 - 9:17
 */
@Data
@ApiModel(value = "特殊物品插入对象", description = "特殊物品插入对象")
@TableName("special_commodity")
public class SpecialCommodityInsertDto {

    @ApiModelProperty(name = "id", value = "特殊物品id", hidden = true)
    private Long id;

    @ApiModelProperty(name = "specialCommodityName", value = "特殊物品名称", required = true)
    @NotBlank(message = "特殊物品名称不能为空")
    private String specialCommodityName;
}
