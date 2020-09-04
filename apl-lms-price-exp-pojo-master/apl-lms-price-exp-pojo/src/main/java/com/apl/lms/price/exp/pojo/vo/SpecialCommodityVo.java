package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/8 - 9:17
 */
@Data
@ApiModel(value = "特殊物品返回对象", description = "特殊物品返回对象")
public class SpecialCommodityVo {

    @ApiModelProperty(name = "id", value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "specialCommodityName", value = "特殊物品名称")
    private String specialCommodityName;

    @ApiModelProperty(name = "code", value = "代码")
    private Integer code;
}
