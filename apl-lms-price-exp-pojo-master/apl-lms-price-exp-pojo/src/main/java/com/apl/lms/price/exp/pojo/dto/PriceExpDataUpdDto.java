package com.apl.lms.price.exp.pojo.dto;
import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_data")
@ApiModel(value="快递价格表数据-更新对象", description="快递价格表数据-更新对象")
public class PriceExpDataUpdDto implements Serializable {

    @ApiModelProperty(name = "id" , value = "价格表id", required = true)
    @NotNull(message = "价格表id不能为空")
    private Long id;

    @ApiModelProperty(name = "priceData" , value = "价格表数据", required = true)
    @NotEmpty(message = "价格表数据不能为空")
    private List<Object> priceData;

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据", required = true)
    @NotEmpty(message = "x轴数据不能为空")
    private List<List<String>> axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据", required = true)
    @NotEmpty(message = "y轴数据不能为空")
    private List<List<String>> axisPortrait;

    @ApiModelProperty(name = "priceFormat" , value = "价格表格式 1横向 2纵向", required = true)
    @NotNull(message = "价格表格式不能为空")
    @TypeValidator(value = {"1","2"}, message = "价格表格式只能为1或者2")
    private Integer priceFormat;

    @ApiModelProperty(name = "startWeight" , value = "起始重", required = true)
    @NotNull(message = "起始重不能为空")
    @Min(value = -1, message = "起始重不能小于0")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重", required = true)
    @NotNull(message = "截止重不能为空")
    @Min(value = 0, message = "截止重要大于0")
    private Double endWeight;

}
