package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_remark")
@ApiModel(value="快递价格备注-持久化对象", description="快递价格备注-持久化对象")
public class PriceExpRemarkPo extends Model<PriceExpRemarkPo> implements Serializable {

    @TableId(value = "价格表id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "价格表id", required = true)
    private Long id;

    @ApiModelProperty(name = "remark" , value = "备注", required = true)
    private String remark;

}
