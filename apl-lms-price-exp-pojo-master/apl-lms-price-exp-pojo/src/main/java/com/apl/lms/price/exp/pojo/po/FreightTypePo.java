package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Min;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 *  持久化对象
 * </p>
 *
 * @author hjr
 * @since 2020-10-07
 */
@Data
@TableName("freight_type")
@ApiModel(value="运输类型-持久化对象", description="运输类型-持久化对象")
public class FreightTypePo extends Model<FreightTypePo> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id", value = "运输类型id", required = true)
    @NotNull(message = "运输类型id不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Min(value = 0, message = "运输类型id不能小于0")
    private Long id;

    @ApiModelProperty(name = "code" , value = "编号" , required = true)
    @NotNull(message = "编号不能为空")
    private Integer code;

    @ApiModelProperty(name = "freightTypeName" , value = "运输名称" , required = true)
    @NotBlank(message = "运输名称不能为空")
    @Length(max = 50, message = "运输名称长度不能超过50")
    private String freightTypeName;


    private static final long serialVersionUID=1L;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
