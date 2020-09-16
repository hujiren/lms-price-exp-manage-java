package com.apl.lms.price.exp.pojo.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@ApiModel(value="燃油费-返回对象", description="燃油费-返回对象")
public class FuelChargeVo{

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "id")
    private Long id;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "startDate" , value = "起始时间")
    private Date startDate;

    @ApiModelProperty(name = "endDate" , value = "截止时间")
    private Date endDate;

    @ApiModelProperty(name = "fuelCharge" , value = "燃油费" )
    private Double fuelCharge;

}
