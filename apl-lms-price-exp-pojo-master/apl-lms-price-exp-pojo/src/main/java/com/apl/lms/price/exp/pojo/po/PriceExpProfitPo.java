package com.apl.lms.price.exp.pojo.po;

import com.apl.lms.price.exp.pojo.entity.PriceExpSaleProfit;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Min;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value = "报价利润持久化对象", description = "报价利润持久化对象")
public class PriceExpProfitPo extends Model<PriceExpProfitPo> {

    @ApiModelProperty(name = "priceSaleId", value = "销售价id", required = true)
    @NotNull(message = "id不能为空")
    @Min(value = 0, message = "销售价id不不合法")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(name = "priceId", value = "价格表id")
    @Min(value = 0, message = "销售价id不不合法")
    private Long priceId;

    @ApiModelProperty(name = "increaseProfit", value = "上调的利润", required = true)
    @NotEmpty(message = "上调的利润不能为空")
    private List<PriceExpSaleProfit> increaseProfit;

    @ApiModelProperty(name = "finalProfit", value = "最终利润", hidden = true)
    private List<PriceExpSaleProfit> finalProfit;

    @ApiModelProperty(name = "startWeight", value = "起始重", required = true)
    @NotNull(message = "起始重不能为空")
    private Double startWeight;

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
