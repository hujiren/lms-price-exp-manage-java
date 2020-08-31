package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020/8/7 - 17:27
 */
@Data
@ApiModel(value="计算公式  返回对象", description="计算公式 返回对象")
public class ComputationalFormulaVo extends Model<ComputationalFormulaVo> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "计算公式id", required = true)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceId" , value = "价格清单id", required = true)
    private Long priceId;

    @ApiModelProperty(name = "formula" , value = "公式", required = true)
    private String formula;
    
    @ApiModelProperty(name = "zoneNum" , value = "分区号", required = true)
    private String zoneNum;

    @ApiModelProperty(name = "country" , value = "国家", required = true)
    private String country;

    @ApiModelProperty(name = "startingWeight" , value = "起始重量", required = true)
    private Double startWeight;

    @ApiModelProperty(name = "endingWeight" , value = "截止重量", required = true)
    private Double endWeight;

    @ApiModelProperty(name = "packageType" , value = "包裹类型", required = true)
    private String packageType;

}
