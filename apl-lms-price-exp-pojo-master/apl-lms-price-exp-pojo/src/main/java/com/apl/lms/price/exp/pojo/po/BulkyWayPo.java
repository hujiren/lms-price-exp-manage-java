package com.apl.lms.price.exp.pojo.po;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @date 2020/8/8 - 9:17
 */
@Data
@ApiModel(value = "计泡方式-插入对象", description = "计泡方式-插入对象")
@TableName("bulky_way")
public class BulkyWayPo extends Model<BulkyWayPo> implements Serializable {

    @ApiModelProperty(name = "id", value = "计泡方式Id", hidden = true)
    private Long id;

    @ApiModelProperty(name = "bulkyWayName", value = "计泡方式名称", required = true)
    @NotBlank(message = "计泡方式名称不能为空")
    @Length(max = 50, message = "计泡方式名称能长度不能超过50")
    private String bulkyWayName;

    @ApiModelProperty(name = "bulkyWayNameEn", value = "计泡方式英文名称", required = true)
    @NotBlank(message = "计泡方式英文名称不能为空")
    @Length(max = 50, message = "计泡方式名称能长度不能超过50")
    private String bulkyWayNameEn;

    @ApiModelProperty(name = "computingFormula", value = "计算公式", required = true)
    @NotBlank(message = "计算公式不能为空")
    @Length(max = 50, message = "计泡方式名称能长度不能超过50")
    private String computingFormula;

    @ApiModelProperty(name = "code", value = "代码", required = true)
    @NotNull(message = "代码不能为空")
    @Min(value = 0, message = "计泡方式代码不能小于0")
    private Integer code;


}
