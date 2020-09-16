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
 * 返回对象
 * </p>
 *
 * @author hjr
 * @since 2020-09-11
 */
@Data
@ApiModel(value = "快递报价利润-返回对象", description = "快递报价利润-返回对象")
public class PriceExpProfitInfoVo implements Serializable {

    @ApiModelProperty(name = "id", value = "id")
    private Long id;

    @ApiModelProperty(name = "priceSaleId", value = "销售价id")
    private Long priceSaleId;

    @ApiModelProperty(name = "priceCostId", value = "成本价id")
    private Long priceCostId;

    @ApiModelProperty(name = "increaseProfit", value = "上调的利润")
    private String increaseProfit;

    @ApiModelProperty(name = "startWeight", value = "起始重")
    private String startWeight;


    private static final long serialVersionUID = 1L;


}
