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

/**
 * @author hjr start
 * @date 2020/8/8 - 9:17
 */
@Data
@TableName("surcharge")
@ApiModel(value = "附加费-持久化对象", description = "附加费-持久化对象")
public class SurchargePo extends Model<SurchargePo> {

    @TableId("id")
    @ApiModelProperty(name = "id", value = "附加费Id", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "chargeName", value = "附加费名称", required = true)
    @NotBlank(message = "附加费名称不能为空")
    @Length(max = 50, message = "附加费名称长度不能超过50")
    private String chargeName;

    @ApiModelProperty(name = "chargeNameEn", value = "附加费英文名称", required = true)
    @NotBlank(message = "附加费英文名称不能为空")
    @Length(max = 50, message = "附加费英文名称长度不能超过50")
    private String chargeNameEn;

    @ApiModelProperty(name = "computingFormula", value = "计算公式", required = true)
    @NotBlank(message = "计算公式不能为空")
    @Length(max = 50, message = "计算公式长度不能超过50")
    private String computingFormula;

    @ApiModelProperty(name = "code", value = "代码", required = true)
    @NotNull(message = "code不能为空")
    @Min(value = 0, message = "计算公式简码不能小于0")
    private Integer code;
}
