package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpMainPo
 * @Date 2020/8/20 14:36
 */
@Data
@ApiModel(value="快递价格主表  持久化对象", description="快递价格主表 持久化对象")
public class PriceExpMainUpdateDto extends Model<PriceExpMainUpdateDto> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "快递价格主表Id")
    @NotNull(message = "快递价格主表Id不能为空")
    private Long id;

    @ApiModelProperty(name = "startDate" , value = "起始日期")
//    @NotNull(message = "起始日期不能为空")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
//    @NotNull(message = "截止日期不能为空")
    private Long endDate;

    @ApiModelProperty(name = "currency" , value = "币制")
//    @NotBlank(message = "币制不能为空")
    private String currency;

    @ApiModelProperty(name = "zoneId" , value = "分区表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    @NotNull(message = "分区表id不能为空")
    private Long zoneId;

    @ApiModelProperty(name = "volumeWeightCardinal" , value = "体积重基数")
//    @NotNull(message = "体积重基数不能为空")
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
//    @NotNull(message = "账号类型不能为空")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
//    @NotBlank(message = "快递账号不能为空")
    private String accountNo;

    @ApiModelProperty(name = "mainStatus" , value = "主表状态 1正常 2计账 3无效")
//    @NotNull(message = "主表状态不能为空")
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
    private Integer mainStatus;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
//    @NotEmpty(message = "特殊物品不能为空")
    private List<Long> specialCommodity;

    @ApiModelProperty(name = "priceForm" , value = "价格表格式 1横向 2纵向")
//    @NotNull(message = "价格表格式不能为空")
    private Integer priceForm;

    @ApiModelProperty(name = "priceDataId" , value = "价格数据表id", hidden = true)
    @NotNull(message = "价格数据表Id不能为空")
    private Long priceDataId;

    @ApiModelProperty(name = "startWeight" , value = "起始重", required = true)
    @NotNull(message = "起始重量不能为空")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重", required = true)
    @NotNull(message = "截止重量不能为空")
    private Double endWeight;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id", hidden = true)
//    @NotNull(message = "公布价id不能为空")
    private Long pricePublishedId;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是")
    @TypeValidator(value = {"1","2"} , message = "公布价值错误")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "aging" , value = "时效", required = true)
    private String aging;
}
