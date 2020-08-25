package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataInsertDto
 * @Date 2020/8/20 9:38
 */
@Data
@ApiModel(value="价格表数据  持久化对象", description="价格表数据 持久化对象")
public class PriceExpDataInsertDto extends Model<PriceExpDataInsertDto> {

    @ApiModelProperty(name = "priceId" , value = "价格表主表id", hidden = true)
    private Long priceId;

    @ApiModelProperty(name = "priceData" , value = "价格表数据")
    @NotEmpty(message = "价格表数据不能为空")
    private List priceData;
}
