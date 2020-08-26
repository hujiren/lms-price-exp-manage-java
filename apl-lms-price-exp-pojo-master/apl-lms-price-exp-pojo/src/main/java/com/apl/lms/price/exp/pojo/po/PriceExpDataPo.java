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
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_data")
@ApiModel(value="价格表数据  持久化对象", description="价格表数据 持久化对象")
public class PriceExpDataPo extends Model<PriceExpDataPo> implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格表数据id")
    private Long id;

    @ApiModelProperty(name = "priceId" , value = "价格表主表id")
    private Long priceId;

    @ApiModelProperty(name = "priceData" , value = "价格表数据")
    @NotBlank(message = "价格表数据不能为空")
    private List priceData;
}
