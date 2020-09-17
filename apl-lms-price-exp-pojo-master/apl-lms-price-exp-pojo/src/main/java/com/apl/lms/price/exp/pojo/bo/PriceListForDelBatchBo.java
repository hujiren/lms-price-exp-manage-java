package com.apl.lms.price.exp.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @Classname PriceListForDelBatchBo
 * @Date 2020/9/15 11:37
 */
@Data
@ApiModel(value = "快递价格批量删除-组装对象", description = "快递价格批量删除-组装对象")
public class PriceListForDelBatchBo {

    @ApiModelProperty(name = "价格表Id", value = "价格表id")
    private Long id;

    @ApiModelProperty(name = "引用价格id", value = "引用价格id")
    private Long quotePriceId;

    @ApiModelProperty(name = "主表id", value = "主表id")
    private Long priceMainId;

}
