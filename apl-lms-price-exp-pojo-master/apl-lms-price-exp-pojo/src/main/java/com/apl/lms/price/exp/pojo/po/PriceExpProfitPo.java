package com.apl.lms.price.exp.pojo.po;

import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Min;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.util.List;

import lombok.Data;

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

    @ApiModelProperty(name = "id", value = "id", required = true)
    @NotNull(message = "id不能为空")
    @Min(value = 0, message = "id不能小于0")
    @TableId(value = "id", type = IdType.INPUT )
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "priceId", value = "快递价格id不")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long priceId;

    @ApiModelProperty(name = "increaseProfit", value = "上调的利润", required = true)
    @NotEmpty(message = "上调的利润不能为空")
    private List<PriceExpProfitDto> increaseProfit;

    @ApiModelProperty(name = "finalProfit", value = "最终利润", hidden = true)
    private List<PriceExpProfitDto> finalProfit;

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }



}
