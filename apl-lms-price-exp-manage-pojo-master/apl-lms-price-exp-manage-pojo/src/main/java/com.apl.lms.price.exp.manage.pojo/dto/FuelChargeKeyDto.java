package com.apl.lms.price.exp.manage.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("price_fuel_charge")
@ApiModel(value="燃油费  查询对象", description="燃油费 查询对象")
public class FuelChargeKeyDto extends Model<FuelChargeKeyDto> {

    @ApiModelProperty(name = "startDate" , value = "起始时间")
    @NotNull(message = "起始时间不能为空")
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止时间")
    @NotNull(message = "截止时间不能为空")
    private Timestamp endDate;

    @ApiModelProperty(name = "fuelCharge" , value = "燃油费")
    @NotNull(message = "燃油费不能为空")
    @Range(min = 0, message = "燃油费不能小于0")
    private Double fuelCharge;


}
