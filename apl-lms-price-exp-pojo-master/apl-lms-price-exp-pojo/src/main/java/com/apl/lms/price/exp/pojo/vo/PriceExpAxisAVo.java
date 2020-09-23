package com.apl.lms.price.exp.pojo.vo;

import com.apl.lms.price.exp.pojo.dto.WeightSectionDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpAxisVo
 * @Date 2020/9/1 17:25
 */
@Data
@ApiModel(value="轴-返回对象", description="轴-返回对象")
public class PriceExpAxisAVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceDataId" , value = "价格数据表id")
    private Long priceDataId;

    @ApiModelProperty(name = "priceFormat" , value = "报价格式")
    private Integer priceFormat;

    @ApiModelProperty(name = "weightSection" , value = "重量段")
    private List<WeightSectionDto> weightSection;

    @ApiModelProperty(name = "zoneCountry" , value = "分区国家")
    private List<String> zoneCountry;
}
