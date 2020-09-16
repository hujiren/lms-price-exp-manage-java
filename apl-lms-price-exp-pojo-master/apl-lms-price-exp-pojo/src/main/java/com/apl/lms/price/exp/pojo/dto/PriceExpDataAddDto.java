package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
@ApiModel(value="快递价格表数据-插入对象", description="快递价格表数据-插入对象")
public class PriceExpDataAddDto implements Serializable {

    @ApiModelProperty(name = "id" , value = "主表id", required = true)
    @NotNull(message = "主表id不能为空")
    private Long id;

    @ApiModelProperty(name = "priceData" , value = "价格表数据", required = true)
    @NotEmpty(message = "价格表数据不能为空")
    private List<Object> priceData;

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据", required = true)
    @NotBlank(message = "x轴数据不能为空")
    private String axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据", required = true)
    @NotBlank(message = "y轴数据不能为空")
    private String axisPortrait;
}
