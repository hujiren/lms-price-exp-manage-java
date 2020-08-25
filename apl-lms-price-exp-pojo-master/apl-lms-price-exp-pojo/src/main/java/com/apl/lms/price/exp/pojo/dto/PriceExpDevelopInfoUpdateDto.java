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
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @Classname PriceExpDevelopInfoDto
 * @Date 2020/8/20 9:45
 */
@Data
@TableName(value = "price_exp_develop_info")
@ApiModel(value="价格表扩展数据  更新对象", description="价格表扩展数据 更新对象")
public class PriceExpDevelopInfoUpdateDto extends Model<PriceExpDevelopInfoUpdateDto> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "扩展数据表Id")
    @NotNull(message = "扩展数据Id不能为空")
    private Long id;

    @ApiModelProperty(name = "priceId" , value = "价格表主表id", hidden = true)
    private Long priceId;

    @ApiModelProperty(name = "saleRemark" , value = "销售备注")
    private String saleRemark;

    @ApiModelProperty(name = "partnerRemark" , value = "合作商备注")
    private String partnerRemark;

}
