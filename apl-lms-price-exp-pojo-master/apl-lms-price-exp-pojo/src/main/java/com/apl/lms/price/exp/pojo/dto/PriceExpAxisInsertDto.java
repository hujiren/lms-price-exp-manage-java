package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_axis")
@ApiModel(value="轴  插入对象", description="轴 插入对象")
public class PriceExpAxisInsertDto extends Model<PriceExpAxisInsertDto> implements Serializable {


    @ApiModelProperty(name = "priceId" , value = "价格表主表Id", hidden = true)
    private Long priceId;

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据")
    @NotEmpty(message = "x轴数据不能为空")
    private List axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据")
    @NotEmpty(message = "y轴数据不能为空")
    private List axisPortrait;
}
