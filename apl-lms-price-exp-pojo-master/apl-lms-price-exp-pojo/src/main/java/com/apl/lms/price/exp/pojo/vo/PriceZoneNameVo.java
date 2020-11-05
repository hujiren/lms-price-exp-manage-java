package com.apl.lms.price.exp.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @Classname PriceZoneNameVo
 * @Date 2020/11/2 14:08
 */
@Data
public class PriceZoneNameVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id", value = "分区id")
    private Long id;

    @ApiModelProperty(name = "channelCategory", value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "zoneName", value = "分区名称")
    private String zoneName;
}
