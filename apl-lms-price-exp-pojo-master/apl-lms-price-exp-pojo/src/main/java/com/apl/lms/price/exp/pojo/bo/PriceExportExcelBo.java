package com.apl.lms.price.exp.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExportExcelBo
 * @Date 2020/10/16 15:03
 */
@Data
@ApiModel(value = "导出价格表对象", description = "导出价格表对象")
public class PriceExportExcelBo implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceDataId", value = "价格数据表id")
    private Long priceDataId;

    @ApiModelProperty(name = "priceData" , value = "价格表数据")
    private List<List<Object>> priceData;

    @ApiModelProperty(name = "channelCategory", value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "zoneId", value = "分区id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long zoneId;

    @ApiModelProperty(name = "zoneName", value = "分区表名称")
    private String zoneName;

    @ApiModelProperty(name = "endDate", value = "截止日期")
    private Timestamp endDate;

    @ApiModelProperty(name = "priceName", value = "价格表名称")
    private String priceName;

    @ApiModelProperty(name = "remark", value = "备注")
    private String remark;

}
