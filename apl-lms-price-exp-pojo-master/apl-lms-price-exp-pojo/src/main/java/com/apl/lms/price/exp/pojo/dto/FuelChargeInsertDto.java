package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@ApiModel(value="燃油费  插入对象", description="燃油费 插入对象")
public class FuelChargeInsertDto extends Model<FuelChargeInsertDto> {


    @ApiModelProperty(name = "startDate" , value = "起始时间" , required = true)
    @NotNull(message = "起始时间不能为空")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止时间" , required = true)
    @NotNull(message = "截止时间不能为空")
    private Long endDate;

    @ApiModelProperty(name = "fuelCharge" , value = "燃油费" , required = true)
    @NotNull(message = "燃油费不能为空")
    private Double fuelCharge;


}
