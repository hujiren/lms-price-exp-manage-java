package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@ApiModel(value="燃油费-插入对象", description="燃油费-插入对象")
public class FuelChargeAddDto {

    @ApiModelProperty(name = "id" , value = "id", required = true)
    @NotNull(message = "id不能为空")
    @Min(value = 0, message = "id不能小于0")
    private Long id;

    @ApiModelProperty(name = "startDate" , value = "起始时间" , required = true)
    @NotNull(message = "起始时间不能为空")
    @Min(value = 0, message = "起始时间不能小于0")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止时间" , required = true)
    @NotNull(message = "截止时间不能为空")
    @Min(value = 0, message = "截止时间不能小于0")
    private Long endDate;

    @ApiModelProperty(name = "fuelCharge" , value = "燃油费" , required = true)
    @NotNull(message = "燃油费不能为空")
    @Range(min = 1, max = 2, message = "燃油费为1-2之间")
    private Double fuelCharge;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型" , required = true)
    @NotBlank(message = "渠道类型不能为空")
    @Length(max = 20, message = "渠道类型长度不能超过20")
    private String channelCategory;

    public String getChannelCategory() {
        if(null != channelCategory)
            channelCategory = channelCategory.toUpperCase();
        return channelCategory;
    }
}