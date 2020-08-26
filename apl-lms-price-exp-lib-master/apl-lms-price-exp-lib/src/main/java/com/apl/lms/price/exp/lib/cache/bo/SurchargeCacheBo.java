package com.apl.lms.price.exp.lib.cache.bo;

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
public class SurchargeCacheBo {

    @ApiModelProperty(name = "id", value = "附加费Id")
    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "chargeName", value = "附加费名称")
    private String chargeName;

    @ApiModelProperty(name = "computingFormula", value = "计算公式")
    private String computingFormula;

    @ApiModelProperty(name = "currency", value = "币制")
    private String currency;
}
