package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpProfitSaveDto
 * @Date 2020/11/10 14:14
 */
@Data
@ApiModel(value = "快递报价利润-保存对象", description = "快递报价利润-保存对象")
public class ExpPriceProfitSaveDto {

    @ApiModelProperty(name = "id", value = "价格表id", required = true)
    @TableId(value = "id", type = IdType.INPUT )
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "addProfitWay", value = "添加利润方式 0不加 1单独加 2统一加", required = true)
    private Integer addProfitWay;

    @ApiModelProperty(name = "increaseProfit", value = "上调的利润", required = true)
    private List<PriceExpProfitDto> increaseProfit;

    @ApiModelProperty(name = "costProfit", value = "成本利润", hidden = true)
    private List<PriceExpProfitDto> costProfit;

    private static final long serialVersionUID = 1L;

}
