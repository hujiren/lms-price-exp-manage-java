package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hjr start
 * @Classname PriceExpPartner
 * @Date 2020/8/26 9:32
 */

@Data
@TableName("price_partner")
@ApiModel(value="服务商  持久化对象", description="服务商 持久化对象")
public class PriceExpPartnerDto extends Model<PriceExpPartnerDto> {

    @ApiModelProperty(name = "partnerCode" , value = "服务商简码", required = true)
    @NotBlank(message = "服务商简码不能为空")
    private String partnerCode;

    @ApiModelProperty(name = "partnerShortName" , value = "服务商简称", required = true)
    @NotBlank(message = "服务商简称不能为空")
    private String partnerShortName;

    @ApiModelProperty(name = "partnerFullName" , value = "服务商全称", required = true)
    @NotBlank(message = "服务商全称不能为空")
    private String partnerFullName;
}
