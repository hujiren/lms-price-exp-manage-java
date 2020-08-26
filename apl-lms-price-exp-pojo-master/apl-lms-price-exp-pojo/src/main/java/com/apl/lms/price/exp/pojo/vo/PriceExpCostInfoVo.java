package com.apl.lms.price.exp.pojo.vo;
import com.apl.lms.price.exp.pojo.entity.Customer;
import com.apl.lms.price.exp.pojo.entity.CustomerGroupInfo;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:36
 */
@Data
@ApiModel(value="成本价格表  返回对象", description="成本价格表 返回对象")
public class PriceExpCostInfoVo extends Model<PriceExpCostInfoVo> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格主表id")
    private Long id;

    @ApiModelProperty(name = "startDate" , value = "起始日期")
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
    private Timestamp endDate;

    @ApiModelProperty(name = "currency" , value = "币制")
    private String currency;

    @ApiModelProperty(name = "zoneTabId" , value = "分区表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long zoneId;

    @ApiModelProperty(name = "volumeWeightCardinal" , value = "体积重基数")
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private String specialCommodity;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "mainStatus" , value = "价格主表状态 1正常 2计账 3无效")
    private Integer mainStatus;

    @ApiModelProperty(name = "priceForm" , value = "价格表格式")
    private Integer priceForm;

    @ApiModelProperty(name = "startWeight" , value = "起始重")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重")
    private Double endWeight;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    //成本表
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

    //轴数据
    @ApiModelProperty(name = "axisTransverse" , value = "表头横向数据")
    private String axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "表头纵向数据")
    private String axisPortrait;

    //扩展数据
    @ApiModelProperty(name = "saleRemark" , value = "销售备注")
    private String saleRemark;

    @ApiModelProperty(name = "partnerRemark" , value = "合作商备注")
    private String partnerRemark;

    //主要数据
    @ApiModelProperty(name = "priceData" , value = "价格主表数据")
    private String priceData;


}