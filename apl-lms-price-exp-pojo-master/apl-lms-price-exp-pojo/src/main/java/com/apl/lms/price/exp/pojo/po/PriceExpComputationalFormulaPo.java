package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/7 - 17:27
 */
@Data
@TableName("price_computational_formula")
@ApiModel(value="计算公式-持久化对象", description="计算公式-持久化对象")
public class PriceExpComputationalFormulaPo extends Model<PriceExpComputationalFormulaPo> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "计算公式id", required = true)
    @NotNull(message = "计算公式id不能为空")
    @Min(value = 0, message = "计算公式id不能小于0")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceId" , value = "价格清单id", required = true)
    @NotNull(message = "价格清单id不能为空")
    @Min(value = 0, message = "价格清单id不能小于0")
    private Long priceId;

    @ApiModelProperty(name = "formula" , value = "公式")
    private String formula;

    @ApiModelProperty(name = "zoneNum" , value = "分区号", required = true)
    @NotBlank(message = "分区号不能为空")
    @Length(max = 50, message = "分区号长度不能超过50")
    private String zoneNum;

    @ApiModelProperty(name = "country" , value = "国家", required = true)
    @NotBlank(message = "国家不能为空")
    @Length(max = 50, message = "国家长度不能超过50")
    private String country;

    @ApiModelProperty(name = "startingWeight" , value = "起始重量")
    private Double startWeight;

    @ApiModelProperty(name = "endingWeight" , value = "截止重量")
    private Double endWeight;

    @ApiModelProperty(name = "packageType" , value = "包裹类型")
    private String packageType;

}
