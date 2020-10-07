package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Min;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class CarrierPo extends Model<CarrierPo> {


    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    @ApiModelProperty(name = "carrierName" , value = "运输方名称" , required = true)
    @NotEmpty(message = "运输方名称不能为空")
    private String carrierName;


    private static final long serialVersionUID=1L;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
