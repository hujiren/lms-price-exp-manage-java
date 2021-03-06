package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @Classname WeightSectionUpdDto
 * @Date 2020/9/23 15:50
 */
@Data
@ApiModel(value = "表首列-更新对象", description = "表首列-更新对象")
public class AxisPortraitUpdDto {

    @ApiModelProperty(name = "priceDataId", value = "价格表数据Id",required = true)
    @NotNull(message = "价格表数据id不能为空")
    @Min(value = 0, message = "价格表数据id不能小于0")
    private Long priceDataId;

    @ApiModelProperty(name = "axisPortrait" , value = "首列数据", required = true)
    @NotEmpty(message = "首列数据不能为空")
    private List<List<String>> axisPortrait;

}
