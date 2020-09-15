package com.apl.lms.price.exp.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hjr start
 * @Classname RelevanceForMainData
 * @Date 2020/9/14 15:10
 */
@Data
@ApiModel(value = "主表与数据表关联对象", description = "主表与数据表关联对象")
public class RelevanceForMainData {

    @ApiModelProperty(value = "主表Id", name = "主表Id")
    List<Long> id;

    @ApiModelProperty(value = "数据表Id", name = "数据表Id")
    List<Long> priceDataId;


}
