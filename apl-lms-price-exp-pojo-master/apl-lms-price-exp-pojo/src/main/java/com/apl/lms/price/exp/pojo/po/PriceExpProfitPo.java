package com.apl.lms.price.exp.pojo.po;

import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    @ApiModelProperty(name = "addProfitWay", value = "添加利润方式 0不加 1单独加 2统一加", required = true)
    private Integer addProfitWay;

    @ApiModelProperty(name = "unifyProfitId", value = "统一利润id")
    private Long unifyProfitId;

    @ApiModelProperty(name = "increaseProfit", value = "上调的利润", required = true)
    private List<PriceExpProfitDto> increaseProfit;

    @ApiModelProperty(name = "finalProfit", value = "最终利润", hidden = true)
    private List<PriceExpProfitDto> finalProfit;

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
