package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpMainPo
 * @Date 2020/8/20 14:36
 */
@Data
@ApiModel(value="销售价格主表  持久化对象", description="销售价格主表 持久化对象")
public class PriceExpMainInsertDto extends Model<PriceExpMainInsertDto> {

    @ApiModelProperty(name = "startDate" , value = "起始日期")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
    private Long endDate;

    @ApiModelProperty(name = "currency" , value = "币制")
    private String currency;

    @ApiModelProperty(name = "zoneId" , value = "分区表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long zoneId;

    @ApiModelProperty(name = "volumeWeightCardinal" , value = "体积重基数")
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "mainStatus" , value = "主表状态 1正常 2计账 3无效", hidden = true)
    @TypeValidator(value = {"0","1","2","3"} , message = "价格表状态错误")
    private Integer mainStatus;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<Long> specialCommodity;

    @ApiModelProperty(name = "priceForm" , value = "价格表格式 1横向 2纵向")
    private Integer priceForm;

    @ApiModelProperty(name = "priceDataId" , value = "价格数据表id")
    @NotNull(message = "价格数据表Id不能为空")
    private Long priceDataId;

    @ApiModelProperty(name = "startWeight" , value = "起始重")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重")
    private Double endWeight;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id")
    @NotNull(message = "公布价不能为空")
    private Long pricePublishedId;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是")
    @TypeValidator(value = {"1","2"} , message = "公布价格错误")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;
}
