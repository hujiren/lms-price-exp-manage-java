package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("price_fuel_charge")
@ApiModel(value="燃油费  更新对象", description="燃油费 更新对象")
public class FuelChargeDto extends Model<FuelChargeDto> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "燃油费id")
    private Long id;
    
    @ApiModelProperty(name = "startDate" , value = "起始时间" , required = true)
    @NotNull(message = "起始时间不能为空")
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止时间" , required = true)
    @NotNull(message = "截止时间不能为空")
    private Timestamp endDate;

    @ApiModelProperty(name = "fuelCharge" , value = "燃油费" , required = true)
    @NotNull(message = "燃油费不能为空")
    private Double fuelCharge;


}
