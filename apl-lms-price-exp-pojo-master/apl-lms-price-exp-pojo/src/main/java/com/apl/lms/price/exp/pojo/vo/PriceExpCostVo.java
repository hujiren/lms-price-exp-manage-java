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
public class PriceExpCostVo extends Model<PriceExpCostVo> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "成本表id")
    private Long id;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称")
    private String priceName;

    @ApiModelProperty(name = "partnerId" , value = "合作商id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long partnerId;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "priceStatus" , value = "状态 1正常 2计账 3无效")
    private Integer priceStatus;

    @ApiModelProperty(name = "priceMainId" , value = "价格主表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long priceMainId;
}
