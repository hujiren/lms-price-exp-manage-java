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
 * <p>
 * 持久化对象
 * </p>
 *
 * @author hjr
 * @since 2020-09-11
 */
@Data
@TableName("price_exp_profit")
@ApiModel(value = "快递报价利润-持久化对象", description = "快递报价利润-持久化对象")
public class PriceExpProfitPo extends Model<PriceExpProfitPo> {

    @ApiModelProperty(name = "id", value = "价格表id", required = true)
    @TableId(value = "id", type = IdType.INPUT )
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "costProfit", value = "成本利润", hidden = true)
    private String costProfit;

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
