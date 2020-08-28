package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpMainPo
 * @Date 2020/8/20 14:36
 */
@Data
@ApiModel(value="快递价格主表  插入对象", description="快递价格主表 插入对象")
public class PriceExpMainAddDto extends Model<PriceExpMainAddDto> {

    @ApiModelProperty(name = "startDate" , value = "起始日期")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
    private Long endDate;

    @ApiModelProperty(name = "currency" , value = "币制")
    private String currency;

    @ApiModelProperty(name = "zoneId" , value = "分区表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long zoneId;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<Long> specialCommodity;

    @ApiModelProperty(name = "priceForm" , value = "价格表格式 1横向 2纵向")
    @TypeValidator(value = {"1","2"} , message = "价格表格式错误")
    private Integer priceForm;

    @ApiModelProperty(name = "startWeight" , value = "起始重")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重")
    private Double endWeight;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id")
    private Long pricePublishedId;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是")
    @TypeValidator(value = {"1","2"} , message = "公布价格错误")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;
}
