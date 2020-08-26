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
import java.io.Serializable;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_develop_info")
@ApiModel(value="价格表扩展数据  持久化对象", description="价格表扩展数据 持久化对象")
public class PriceExpDevelopInfoPo extends Model<PriceExpDevelopInfoPo> implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格表扩展数据id", hidden = true)
    private Long id;

    @ApiModelProperty(name = "priceId" , value = "价格表主表id", hidden = true)
    private Long priceId;

    @ApiModelProperty(name = "saleRemark" , value = "销售备注")
    private String saleRemark;

    @ApiModelProperty(name = "partnerRemark" , value = "合作商备注")
    private String partnerRemark;

}
