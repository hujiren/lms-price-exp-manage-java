package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:36
 */
@Data
@ApiModel(value="成本价格表  返回对象", description="成本价格表 返回对象")
public class PriceExpCostListVo extends Model<PriceExpCostListVo> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格表id")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "priceMainId" , value = "主表id")
    private Long priceMainId;

    @ApiModelProperty(name = "priceCode" , value = "成本价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "成本价格表名称")
    private String priceName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "startDate" , value = "起始日期")
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
    private Timestamp endDate;

    @ApiModelProperty(name = "currency" , value = "币制")
    private String currency;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "accountType" , value = "账号类型")
    private Integer accountType;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private String specialCommodity;

    @ApiModelProperty(name = "mainStatus" , value = "主表状态")
    private Integer mainStatus;

    @ApiModelProperty(name = "priceStatus" , value = "成本价格状态")
    private Integer priceStatus;



}
