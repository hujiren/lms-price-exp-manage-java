package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpSalePo
 * @Date 2020/8/20 14:31
 */
@Data
@TableName("price_exp_sale")
@ApiModel(value="销售价格表  持久化对象", description="销售价格表 持久化对象")
public class PriceExpSalePo extends Model<PriceExpSalePo> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "销售价格表Id")
    private Long id;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称")
    private String priceName;

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private List<Long> customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "priceStatus" , value = "销售价格表状态 1正常 2计账 3无效")
    private Integer priceStatus;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格id")
    private Long quotePriceId;

    //@ApiModelProperty(name = "quotePriceFinalId" , value = "引用价格最终id")
    //private Long quotePriceFinalId;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "priceMainId" , value = "价格主表id")
    private Long priceMainId;

}
