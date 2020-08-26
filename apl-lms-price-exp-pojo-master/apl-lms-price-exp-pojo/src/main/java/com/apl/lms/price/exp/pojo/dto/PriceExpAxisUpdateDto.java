package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_axis")
@ApiModel(value="价格表轴数据  更新对象", description="价格表轴数据 更新对象")
public class PriceExpAxisUpdateDto extends Model<PriceExpAxisUpdateDto> implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "轴数据表Id")
    @NotNull(message = "轴数据Id不能为空")
    private Long id;

    @ApiModelProperty(name = "priceId" , value = "价格表主表Id", hidden = true)
    private Long priceId;

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据")
    @NotBlank(message = "x轴数据不能为空")
    private List axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据")
    @NotBlank(message = "y轴数据不能为空")
    private List axisPortrait;
}
