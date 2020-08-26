package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @Classname PriceExpPartner
 * @Date 2020/8/26 9:32
 */

@Data
@TableName("price_partner")
@ApiModel(value="服务商  持久化对象", description="服务商 持久化对象")
public class PartnerPo extends Model<PartnerPo> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "服务商id", required = true)
    @NotNull(message = "服务商id不能为空")
    private Long id;

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
