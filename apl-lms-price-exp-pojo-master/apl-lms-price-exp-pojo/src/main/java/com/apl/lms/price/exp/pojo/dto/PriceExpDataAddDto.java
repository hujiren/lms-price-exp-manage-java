package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_data")
@ApiModel(value="价格表数据  插入对象", description="价格表数据 插入对象")
public class PriceExpDataAddDto extends Model<PriceExpDataAddDto> implements Serializable {

    @ApiModelProperty(name = "priceData" , value = "价格表数据", required = true)
    @NotEmpty(message = "价格表数据不能为空")
    private List<List<Object>> priceData;
}
