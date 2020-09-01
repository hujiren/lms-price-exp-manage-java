package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @Classname PriceExpAxisVo
 * @Date 2020/9/1 17:25
 */
@Data
@ApiModel(value="轴  返回对象", description="轴 返回对象")
public class PriceExpAxisVo extends Model<PriceExpAxisVo> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceMainId" , value = "价格主表id")
    private Long priceMainId;

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据")
    private String axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据")
    private String axisPortrait;
}
