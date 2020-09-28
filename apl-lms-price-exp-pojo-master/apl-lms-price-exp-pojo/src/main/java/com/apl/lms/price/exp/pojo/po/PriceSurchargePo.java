package com.apl.lms.price.exp.pojo.po;

import com.apl.lib.validate.TypeValidator;
import com.apl.lms.common.query.manage.dto.SpecialCommodityDto;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceSurchargePo
 * @Date 2020/9/28 11:19
 */
@Data
@ApiModel(value = "价格附加费-持久化对象", description = "价格附加费-持久化对象")
@TableName("price_surcharge")
public class PriceSurchargePo implements Serializable {
    private static final long serialVersionUID = -1815949531976033547L;

    @TableId("id")
    @ApiModelProperty(value = "id", name = "id", required = true)
    @NotNull(message = "Id不能为空")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceId", value = "价格表id")
    @NotNull(message = "价格表id不能为空")
    private Long priceId;

    @ApiModelProperty(name = "chargeName", value = "费用名 1超重费 2超值费 3电池费", required = true)
    @NotNull(message = "费用名不能为空")
    private Integer chargeName;

    @TypeValidator(value = {"0", "1", "2"}, message = "费用方式值为0-2之间")
    @ApiModelProperty(name = "chargeWay", value = "费用方式 1按件价 2按票价")
    private Integer chargeWay;

    @TypeValidator(value = {"0", "1", "2", "3"}, message = "重量方式值为0-3之间=")
    @ApiModelProperty(name = "weightWay", value = "重量方式 1实重>=  2体积重>=  3计费重>=")
    private Integer weightWay;

    @ApiModelProperty(name = "overWeight", value = "超重")
    private Double overWeight;

    @ApiModelProperty(name = "overLength", value = "超长")
    private Double overLength;

    @ApiModelProperty(name = "overDeclaredValue", value = "超值")
    private Double overDeclaredValue;

    @ApiModelProperty(name = "specialCommodity", value = "特殊物品")
    private Integer specialCommodity;

    @ApiModelProperty(name = "formula", value = "公式", required = true)
    @NotBlank(message = "公式不能为空")
    private String formula;

    @ApiModelProperty(name = "currency", value = "币制", required = true)
    @NotBlank(message = "币制不能为空")
    private String currency;

    @ApiModelProperty(name = "remark", value = "说明")
    private String remark;

}
