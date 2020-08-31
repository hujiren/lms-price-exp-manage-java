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
@ApiModel(value="销售价格表  返回对象", description="销售价格表 返回对象")
public class PriceExpSaleListVo extends Model<PriceExpSaleListVo> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格表id")
    private Long id;

    @ApiModelProperty(name = "priceCode" , value = "销售价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "销售价格表名称")
    private String priceName;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customersName" , value = "客户名称")
    private String customerName;

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

    @ApiModelProperty(name = "accountType" , value = "帐号类型")
    private Integer accountType;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private String specialCommodity;

    @ApiModelProperty(name = "priceExpSaleId" , value = "销售价格表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long priceExpSaleId;

    @ApiModelProperty(name = "mainStatus" , value = "主表状态")
    private Integer mainStatus;

    @ApiModelProperty(name = "priceStatus" , value = "价格表状态")
    private Integer priceStatus;






}
