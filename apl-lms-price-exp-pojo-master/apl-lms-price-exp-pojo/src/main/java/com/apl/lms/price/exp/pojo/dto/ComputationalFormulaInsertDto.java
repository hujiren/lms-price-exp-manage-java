package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/7 - 17:27
 */
@Data
@ApiModel(value="计算公式  插入对象", description="计算公式 插入对象")
public class ComputationalFormulaInsertDto extends Model<ComputationalFormulaInsertDto> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceId" , value = "价格清单id", required = true)
    private Long priceId;

    @ApiModelProperty(name = "formula" , value = "公式", required = true)
    @NotBlank(message = "公式不能为空")
    private String formula;

    @ApiModelProperty(name = "zoneNum" , value = "分区号")
    private String zoneNum;

    @ApiModelProperty(name = "country" , value = "国家")
    private String country;

    @ApiModelProperty(name = "startingWeight" , value = "起始重量")
    private Double startWeight;

    @ApiModelProperty(name = "endingWeight" , value = "截止重量")
    private Double endWeight;

    @ApiModelProperty(name = "packageType" , value = "包裹类型")
    private String packageType;


}
