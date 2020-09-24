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
    @ApiModelProperty(name = "priceDataId" , value = "价格数据表Id")
    private Long priceDataId;

    @ApiModelProperty(name = "axisVo" , value = "数据轴")
    private PriceExpAxisVo axisVo;

    @ApiModelProperty(name = "priceData" , value = "价格表数据")
    private List<Object> priceData;
}
