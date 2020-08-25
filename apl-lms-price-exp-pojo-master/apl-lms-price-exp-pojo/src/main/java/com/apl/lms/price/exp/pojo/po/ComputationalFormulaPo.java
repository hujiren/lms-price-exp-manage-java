package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/7 - 17:27
 */
@Data
@TableName("price_computational_formula")
@ApiModel(value="计算公式  持久化对象", description="计算公式 持久化对象")
public class ComputationalFormulaPo extends Model<ComputationalFormulaPo> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "计算公式id", required = true)
    @NotNull(message = "计算公式id不能为空")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceId" , value = "价格清单id", required = true)
    private Long priceId;

    @ApiModelProperty(name = "formula" , value = "公式")
    private String formula;

    @ApiModelProperty(name = "priceZone" , value = "分区", required = true)
    private String priceZone;

    @ApiModelProperty(name = "country" , value = "国家", required = true)
    private String country;

    @ApiModelProperty(name = "startingWeight" , value = "起始重量")
    private Double startingWeight;

    @ApiModelProperty(name = "endingWeight" , value = "截止重量")
    private Double endingWeight;

    @ApiModelProperty(name = "packingType" , value = "包裹类型", required = true)
    private String packingType;

}
