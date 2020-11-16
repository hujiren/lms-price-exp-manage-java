package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @Classname PriceIncreaseProfitPo
 * @Date 2020/11/14 10:15
 */
@Data
@TableName("price_increase_profit")
@ApiModel(value = "增加的利润-持久化对象", description = "增加的利润-持久化对象")
public class PriceIncreaseProfitPo extends Model<PriceIncreaseProfitPo> {

    @ApiModelProperty(name = "id", value = "id")
    private Long id;

    @ApiModelProperty(name = "priceId", value = "价格表Id")
    private Long priceId;

    @ApiModelProperty(name = "customerGroupIds" , value = "客户组id")
    private String customerGroupIds;

    @ApiModelProperty(name = "customerGroupNames" , value = "客户组名称")
    private String customerGroupNames;

    @ApiModelProperty(name = "zoneNum", value = "分区号")
    private String zoneNum;

    @ApiModelProperty(name = "countryCode", value = "国家简码")
    private String countryCode;

    @ApiModelProperty(name = "startWeight", value = "起始重")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight", value = "截止重")
    private Double endWeight;

    @ApiModelProperty(name = "firstWeightProfit", value = "首重加")
    private Double firstWeightProfit;

    @ApiModelProperty(name = "unitWeightProfit", value = "单位重加")
    private Double unitWeightProfit;

    @ApiModelProperty(name = "proportionProfit", value = "比例加")
    private Double proportionProfit;
}
