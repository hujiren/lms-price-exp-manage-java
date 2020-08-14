package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value = "计泡方式插入对象", description = "计泡方式插入对象")
public class WeightWayInsertDto {

    @ApiModelProperty(name = "id", value = "计泡方式Id", hidden = true)
    private Long id;

    @ApiModelProperty(name = "weightWayName", value = "计泡方式名称", required = true)
    @NotBlank(message = "计泡方式名称不能为空")
    private String weightWayName;

    @ApiModelProperty(name = "computingFormula", value = "计算公式", required = true)
    @NotBlank(message = "计算公式不能为空")
    private String computingFormula;

}
