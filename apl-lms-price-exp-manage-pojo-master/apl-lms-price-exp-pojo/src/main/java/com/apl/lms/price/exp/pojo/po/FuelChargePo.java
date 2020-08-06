package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("price_fuel_charge")
@ApiModel(value="燃油费  持久化对象", description="燃油费 持久化对象")
public class FuelChargePo extends Model<FuelChargePo> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "燃油费id" , required = true)
    private Long id;

    @ApiModelProperty(name = "startDate" , value = "起始时间" , required = true)
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止时间" , required = true)
    private Timestamp endDate;

    @ApiModelProperty(name = "saleName" , value = "销售名称" , required = true)
    private String saleName;

    @ApiModelProperty(name = "startDate" , value = "起始日期" , required = true)
    private Double fuelCharge;


}
