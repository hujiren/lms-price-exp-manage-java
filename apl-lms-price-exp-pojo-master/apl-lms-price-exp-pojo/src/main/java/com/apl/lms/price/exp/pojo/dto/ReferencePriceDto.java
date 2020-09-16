package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname ReferencePriceDto
 * @Date 2020/9/11 16:33
 */
@Data
@ApiModel(value="引用价格表对象", description="引用价格表对象")
public class ReferencePriceDto extends PriceExpAddBaseDto{

    @ApiModelProperty(name = "priceMainId" , value = "价格主表id", required = true)
    @Min(value = 0, message = "引用主表id不能小于0")
    @NotNull( message = "引用主表id不能为空")
    private Long priceMainId;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格表id", required = true)
    @Min(value = 0, message = "引用价格表id不能小于0")
    @NotNull( message = "引用价格表id不能为空")
    private Long quotePriceId;

    @ApiModelProperty(name = "priceDataId" , value = "价格数据id", required = true)
    @Min(value = 0, message = "价格数据id不能小于0")
    @NotNull( message = "价格数据不能为空")
    private Long priceDataId;
}
