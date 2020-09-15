package com.apl.lms.price.exp.pojo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @Classname PriceListForDelBatch
 * @Date 2020/9/15 11:37
 */
@Data
@ApiModel(value = "组装批量删除价格表条件entity")
public class PriceListForDelBatch extends Model<PriceListForDelBatch> {

    @ApiModelProperty(name = "价格表Id", value = "价格表id")
    private Long id;

    @ApiModelProperty(name = "引用价格id", value = "引用价格id")
    private Long quotePriceId;

    @ApiModelProperty(name = "主表id", value = "主表id")
    private Long priceMainId;

    @ApiModelProperty(name = "价格数据表id", value = "价格数据表id")
    private Long priceDataId;
}
