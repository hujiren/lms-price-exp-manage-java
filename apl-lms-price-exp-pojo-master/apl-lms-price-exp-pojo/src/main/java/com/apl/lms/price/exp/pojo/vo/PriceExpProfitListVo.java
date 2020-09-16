package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "priceId", value = "价格表id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long priceId;

    @ApiModelProperty(name = "increaseProfit", value = "上调的利润")
    private String increaseProfit;

    @ApiModelProperty(name = "finalProfit", value = "最终利润")
    private String finalProfit;

    @ApiModelProperty(name = "startWeight", value = "起始重")
    private String startWeight;


    private static final long serialVersionUID = 1L;


}