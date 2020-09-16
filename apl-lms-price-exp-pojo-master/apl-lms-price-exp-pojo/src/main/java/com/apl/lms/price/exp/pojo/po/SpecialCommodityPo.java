package com.apl.lms.price.exp.pojo.po;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hjr start
 * @Classname SpecialCommodityPo
 * @Date 2020/9/16 15:35
 */
@Data
@ApiModel(value = "特殊物品 持久化对象", description = "特殊物品持久化对象")
@TableName("special_commodity")
public class SpecialCommodityPo extends Model<SpecialCommodityPo> implements Serializable {

    @ApiModelProperty(name = "id", value = "特殊物品id", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId(value = "id")
    @Min(value = 0, message = "特殊物品id不能小于0")
    @NotNull(message = "特殊物品id不能为空")
    private Long id;

    @ApiModelProperty(name = "specialCommodityName", value = "特殊物品名称", required = true)
    @NotBlank(message = "特殊物品名称不能为空")
    @Length(max = 50, message = "特殊物品名称长度不能超过50")
    private String specialCommodityName;

    @ApiModelProperty(name = "specialCommodityNameEn", value = "特殊物品英文名称", required = true)
    @NotBlank(message = "特殊物品英文名称不能为空")
    @Length(max = 50, message = "特殊物品英文名称长度不能超过50")
    private String specialCommodityNameEn;

    @ApiModelProperty(name = "code", value = "代码", required = true)
    @NotNull(message = "code不能为空")
    @Min(value = 0, message = "特殊物品简码不能小于0")
    private Integer code;
}
