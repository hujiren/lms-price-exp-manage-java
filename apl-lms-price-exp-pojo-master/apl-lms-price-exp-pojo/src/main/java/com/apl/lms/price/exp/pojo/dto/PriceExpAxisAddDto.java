package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_axis")
@ApiModel(value="轴 插入对象", description="轴 插入对象")
public class PriceExpAxisAddDto extends Model<PriceExpAxisAddDto> implements Serializable {

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据", required = true)
    @NotBlank(message = "x轴数据不能为空")
    private String axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据", required = true)
    @NotBlank(message = "y轴数据不能为空")
    private String axisPortrait;
}
