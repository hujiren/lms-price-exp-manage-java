package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_axis")
@ApiModel(value="轴  持久化对象", description="轴 持久化对象")
public class PriceExpAxisPo extends Model<PriceExpAxisPo> implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "轴id")
    private Long id;

    @ApiModelProperty(name = "priceId" , value = "价格表主表Id")
    private Long priceId;

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据")
    @NotBlank(message = "x轴数据不能为空")
    private String axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据")
    @NotBlank(message = "y轴数据不能为空")
    private String axisPortrait;
}
