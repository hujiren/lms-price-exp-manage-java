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
@ApiModel(value="快递价格表主数据字符串-返回对象", description="快递价格表主数据字符串-返回对象")
public class PriceExpDataStringVo implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceDataId", value = "价格数据表id")
    private Long priceDataId;

    @ApiModelProperty(name = "priceData" , value = "价格表数据")
    private List<List<String>> priceData;
}
