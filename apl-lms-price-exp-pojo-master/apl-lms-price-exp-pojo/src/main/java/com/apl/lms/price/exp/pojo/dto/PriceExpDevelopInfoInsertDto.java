package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hjr start
 * @Classname PriceExpDevelopInfoDto
 * @Date 2020/8/20 9:45
 */
@Data
@TableName(value = "price_exp_develop_info")
@ApiModel(value="价格表扩展数据  持久化对象", description="价格表扩展数据 持久化对象")
public class PriceExpDevelopInfoInsertDto extends Model<PriceExpDevelopInfoInsertDto> {

    @ApiModelProperty(name = "priceId" , value = "价格表主表id", hidden = true)
    private Long priceId;

    @ApiModelProperty(name = "saleRemark" , value = "销售备注")
    private String saleRemark;

    @ApiModelProperty(name = "partnerRemark" , value = "合作商备注")
    private String partnerRemark;
}
