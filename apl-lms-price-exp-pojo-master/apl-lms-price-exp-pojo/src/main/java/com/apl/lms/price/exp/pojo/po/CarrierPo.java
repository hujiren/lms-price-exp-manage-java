package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *  持久化对象
 * </p>
 *
 * @author hjr
 * @since 2020-10-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("carrier")
@ApiModel(value=" 持久化对象", description=" 持久化对象")
public class CarrierPo extends Model<CarrierPo> implements Serializable{


    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "id" , required = true)
    @NotNull(message = "id不能为空")
    @Min(value = 0, message = "id不能小于0")
    private Long id;

    @ApiModelProperty(name = "carrierName" , value = "运输方名称" , required = true)
    @NotBlank(message = "运输方名称不能为空")
    @Length(max = 50, message = "运输方名称长度不能超过50")
    private String carrierName;


    private static final long serialVersionUID=1L;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
