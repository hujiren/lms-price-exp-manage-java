package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hjr start
 * @Classname PriceExpPartner
 * @Date 2020/8/26 9:32
 */

@Data
@TableName("partner")
@ApiModel(value="服务商-持久化对象", description="服务商-持久化对象")
public class PartnerPo extends Model<PartnerPo> implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "服务商id", required = true)
    @NotNull(message = "服务商id不能为空")
    @Min(value = 0, message = "id不能小于0")
    private Long id;

    @ApiModelProperty(name = "partnerCode" , value = "服务商简码", required = true)
    @NotBlank(message = "服务商简码不能为空")
    @Length(max = 20, message = "服务商简码长度不能超过20")
    private String partnerCode;

    @ApiModelProperty(name = "partnerShortName" , value = "服务商简称", required = true)
    @NotBlank(message = "服务商简称不能为空")
    @Length(max = 20, message = "服务商简称长度不能超过20")
    private String partnerShortName;

    @ApiModelProperty(name = "partnerFullName" , value = "服务商全称", required = true)
    @NotBlank(message = "服务商全称不能为空")
    @Length(max = 40, message = "服务商全称长度不能超过40")
    private String partnerFullName;
}
