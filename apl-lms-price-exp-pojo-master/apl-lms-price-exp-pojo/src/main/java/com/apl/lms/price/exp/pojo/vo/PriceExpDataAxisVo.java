package com.apl.lms.price.exp.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@ApiModel(value="快递价格表数据和轴数据-返回对象", description="快递价格表数据和轴数据-返回对象")
public class PriceExpDataAxisVo implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceMainId" , value = "价格主表id")
    private Long priceMainId;

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据")
    private String axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据")
    private String axisPortrait;

    @ApiModelProperty(name = "priceData" , value = "价格表数据")
    private List<Object> priceData;
}
