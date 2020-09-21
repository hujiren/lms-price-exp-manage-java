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

    @ApiModelProperty(name = "id", value = "价格表id")
    private Long id;

    @ApiModelProperty(name = "quotePriceId", value = "引用价格id")
    private Long quotePriceId;

    @ApiModelProperty(name = "priceDataId", value = "价格数据表Id")
    private Long priceDataId;

    @ApiModelProperty(name = "innerOrgId", value = "租户Id")
    private Long innerOrgId;

    public PriceListForDelBatchBo(Long id, Long priceDataId, Long quotePriceId, Long innerOrgId) {
        this.id = id;
        this.quotePriceId = quotePriceId;
        this.priceDataId = priceDataId;
        this.innerOrgId = innerOrgId;
    }
}
