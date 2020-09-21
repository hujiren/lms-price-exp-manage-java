package com.apl.lms.price.exp.pojo.po;
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
 * @Classname PriceExpCostPo
 * @Date 2020/8/20 14:43
 */
@Data
@TableName("price_exp_cost")
@ApiModel(value="快递成本价格表-持久化对象", description="快递成本价格表-持久化对象")
public class PriceExpCostPo extends Model<PriceExpCostPo> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "成本价格主表Id")
    private Long id;

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long partnerId;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格id")
    private Long quotePriceId;

    //@ApiModelProperty(name = "quotePriceFinalId" , value = "引用价格最终id")
    //private Long quotePriceFinalId;

    @ApiModelProperty(name = "priceStatus" , value = "成本价格表状态 1正常 2计账 3无效")
    private Integer priceStatus;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称")
    private String priceName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "priceMainId" , value = "价格主表id")
    private Long priceMainId;
}
