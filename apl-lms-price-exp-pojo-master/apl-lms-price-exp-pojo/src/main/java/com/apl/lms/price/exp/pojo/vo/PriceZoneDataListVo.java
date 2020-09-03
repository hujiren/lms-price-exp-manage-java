package com.apl.lms.price.exp.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="分区表数据  返回对象", description="分区表数据 返回对象")
public class PriceZoneDataListVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格主表id")
    private Long id;

    @ApiModelProperty(name = "zoneNum" , value = "分区号")
    private String zoneNum;

    @ApiModelProperty(name = "countryCode" , value = "国家简码")
    private String countryCode;
}
