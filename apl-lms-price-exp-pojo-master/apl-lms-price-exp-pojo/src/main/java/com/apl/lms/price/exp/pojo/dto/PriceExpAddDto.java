package com.apl.lms.price.exp.pojo.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpAddDto
 * @Date 2020/9/1 15:48
 */
@Data
@ApiModel(value="快递价格表-插入对象", description="快递价格表-插入对象")
public class PriceExpAddDto extends PriceExpAddBaseDto{

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据", required = true)
    @NotEmpty(message = "x轴数据不能为空")
    private List<List<String>> axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据", required = true)
    @NotEmpty(message = "y轴数据不能为空")
    private List<List<String>> axisPortrait;

    @ApiModelProperty(name = "priceData" , value = "价格表数据", required = true)
    @NotEmpty(message = "价格表数据不能为空")
    private List<Object> priceData;


}
