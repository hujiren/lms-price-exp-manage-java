package com.apl.lms.price.exp.pojo.vo;

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
@ApiModel(value="快递价格表主数据-返回对象", description="快递价格表主数据-返回对象")
public class PriceExpDataVo implements Serializable {

    @ApiModelProperty(name = "id", value = "id")
    private Long id;

    @ApiModelProperty(name = "priceData" , value = "价格表数据")
    private List<Object> priceData;
}
