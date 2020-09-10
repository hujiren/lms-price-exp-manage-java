package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpMainPo
 * @Date 2020/8/20 14:36
 */
@Data
@ApiModel(value="快递价格主表  插入对象", description="快递价格主表 插入对象")
public class PriceExpMainAddDto extends Model<PriceExpMainAddDto> {

    @ApiModelProperty(name = "startDate" , value = "起始日期", required = true)
    @Min(value = 1, message = "起始日期不能为空")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期", required = true)
    @Min(value = 1, message = "截止日期不能为空")
    private Long endDate;

    @ApiModelProperty(name = "currency" , value = "币制", required = true)
    @NotEmpty(message = "币制不能为空")
    private String currency;

    @ApiModelProperty(name = "zoneId" , value = "分区表Id")
    @NotNull(message = "分区表Id必须为数字")
    private Long zoneId;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号", required = true)
    @Range(min = 1, max = 3, message = "账号类型错误")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数", required = true)
    @Range(min = 5000, max = 9999, message = "体积除数错误")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<Integer> specialCommodity;

    @ApiModelProperty(name = "priceForm" , value = "价格表格式 1横向 2纵向")
    @Range(min = 1, max = 2, message = "价格表格式错误")
    private Integer priceForm;

    @ApiModelProperty(name = "startWeight" , value = "起始重", required = true)
    @Min(value = 0, message = "起始重量不能为空")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重", required = true)
    @Range(min = 0, max = 100000, message = "截止重量不能为空")
    private Double endWeight;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id")
    @Min(value = 0, message = "公布价最小值为0")
    private Long pricePublishedId;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是", required = true)
    @Range(min = 1, max = 2, message = "是否是公布价错误")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    
}
