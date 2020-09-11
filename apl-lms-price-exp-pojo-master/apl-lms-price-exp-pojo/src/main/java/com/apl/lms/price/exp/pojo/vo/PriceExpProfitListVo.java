package com.apl.lms.price.exp.pojo.vo;

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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分页返回对象
 * </p>
 *
 * @author hjr
 * @since 2020-09-11
 */
@Data
public class PriceExpProfitListVo implements Serializable {


    @TableId(value = "id", type = IdType.UUID)
    private Long id;

    @ApiModelProperty(name = "priceSaleId", value = "销售价id")
    @NotNull(message = "销售价id不能为空")
    @Min(value = 0, message = "销售价id不不合法")
    private Long priceSaleId;

    @ApiModelProperty(name = "priceCostId", value = "成本价id")
    @NotNull(message = "成本价id不能为空")
    @Min(value = 0, message = "成本价id不不合法")
    private Long priceCostId;

    @ApiModelProperty(name = "increaseProfit", value = "上调的利润")
    @NotEmpty(message = "上调的利润不能为空")
    private String increaseProfit;

    @ApiModelProperty(name = "finalProfit", value = "最终利润")
    @NotEmpty(message = "最终利润不能为空")
    private String finalProfit;

    @ApiModelProperty(name = "startWeight", value = "起始重")
    @NotEmpty(message = "起始重不能为空")
    private String startWeight;


    private static final long serialVersionUID = 1L;


}