package com.apl.lms.price.exp.pojo.dto;

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
@EqualsAndHashCode(callSuper = false)
@TableName("weight_way")
@ApiModel(value = "计泡方式持久化对象", description = "计泡方式持久化对象")
public class WeightWayUpdDto {

    @ApiModelProperty(name = "id", value = "计泡方式Id", required = true)
    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "计泡方式Id不能为空")
    private Long id;

//    @ApiModelProperty(name = "weightWayName", value = "计泡方式名称", required = true)
//    @NotBlank(message = "计泡方式名称不能为空")
//    private String weightWayName;

//    @ApiModelProperty(name = "weightWayNameEn", value = "计泡方式英文名称", required = true)
//    @NotBlank(message = "计泡方式英文名称不能为空")
//    private String weightWayNameEn;

    @ApiModelProperty(name = "computingFormula", value = "计算公式", required = true)
    @NotBlank(message = "计算公式不能为空")
    private String computingFormula;

//    @ApiModelProperty(name = "code", value = "代码", required = true)
//    @NotNull(message = "代码不能为空")
//    private Integer code;
}
