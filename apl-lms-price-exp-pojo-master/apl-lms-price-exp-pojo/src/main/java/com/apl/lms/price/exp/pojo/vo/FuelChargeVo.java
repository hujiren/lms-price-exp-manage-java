package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.sql.Timestamp;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@ApiModel(value="燃油费  返回对象", description="燃油费 返回对象")
public class FuelChargeVo extends Model<FuelChargeVo> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "燃油费id" , required = true)
    private Long id;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型" , required = true)
    private String channelCategory;

    @ApiModelProperty(name = "startDate" , value = "起始时间" , required = true)
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止时间" , required = true)
    private Timestamp endDate;

    @ApiModelProperty(name = "fuelCharge" , value = "燃油费" , required = true)
    private Double fuelCharge;

}
