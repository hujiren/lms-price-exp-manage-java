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
@TableName("surcharge")
@ApiModel(value = "附加费持久化对象", description = "附加费持久化对象")
public class SurchargeDto {

    @ApiModelProperty(name = "id", value = "附加费Id", required = true)
    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "附加费Id不能为空")
    private Long id;

    @ApiModelProperty(name = "chargeName", value = "附加费名称", required = true)
    @NotBlank(message = "附加费名称不能为空")
    private String chargeName;

    @ApiModelProperty(name = "computingFormula", value = "计算公式", required = true)
    @NotBlank(message = "计算公式不能为空")
    private String computingFormula;

    @ApiModelProperty(name = "currency", value = "币制", required = true)
    @NotBlank(message = "币制不能为空")
    private String currency;
}
