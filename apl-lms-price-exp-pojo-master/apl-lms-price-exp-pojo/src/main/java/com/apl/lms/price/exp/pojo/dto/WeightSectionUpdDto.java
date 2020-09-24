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
@ApiModel(value = "重量段-更新对象", description = "重量段-更新对象")
public class WeightSectionUpdDto {

    @ApiModelProperty(name = "priceDataId", value = "价格表数据Id",required = true)
    @NotNull(message = "价格表数据id不能为空")
    @Min(value = 0, message = "价格表数据id不能小于0")
    private Long priceDataId;

    @ApiModelProperty(name = "weightSection" , value = "重量段", required = true)
    @NotEmpty(message = "重量段不能为空")
    private List<WeightSectionDto> weightSection;
}
