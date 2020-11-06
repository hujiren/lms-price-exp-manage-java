package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpMainPo
 * @Date 2020/8/20 14:36
 */
@Data
@TableName("pgs_lms_exp_price")
@ApiModel(value="快递价格主表-持久化对象", description="快递价格主表-持久化对象")
public class PriceExpMainPo extends Model<PriceExpMainPo> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "价格表Id")
    private Long id;

    @ApiModelProperty(name = "startDate" , value = "起始日期")
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
    private Timestamp endDate;

    @ApiModelProperty(name = "currency" , value = "币制")
    private String currency;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<Integer> specialCommodity;

    @ApiModelProperty(name = "priceFormat" , value = "价格表格式 1横向 2纵向")
    private Integer priceFormat;

    @ApiModelProperty(name = "startWeight" , value = "起始重")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重")
    private Double endWeight;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id")
    private Long pricePublishedId;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "zoneTabId" , value = "分区表Id")
    private Long zoneId;

    @ApiModelProperty(name = "priceDataId" , value = "价格数据表Id")
    private Long priceDataId;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceSaleName" , value = "报价销售名称")
    private String priceSaleName;

    @ApiModelProperty(name = "priceName" , value = "报价名称")
    private String priceName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "priceStatus" , value = "销售价格表状态 1正常 2计账 3无效")
    private Integer priceStatus;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格id")
    private Long quotePriceId;

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    private Long partnerId;

    @ApiModelProperty(name = "partnerName" , value = "服务商名称")
    private String partnerName;

    @ApiModelProperty(name = "customerGroupId" , value = "客户组id")
    private List<Long> customerGroupId;

    @ApiModelProperty(name = "customerGroupName" , value = "客户组名称")
    private String customerGroupName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "updTime" , value = "更新时间")
    private Timestamp updTime;

    @ApiModelProperty(name = "quotePriceUpdTime" , value = "引用价格更新时间")
    private Timestamp quotePriceUpdTime;

    @ApiModelProperty(name = "innerOrgId" , value = "租户id")
    private Long innerOrgId;

    @ApiModelProperty(name = "quoteTenantCode" , value = "引用租户code")
    private String quoteTenantCode;

    @ApiModelProperty(name = "isQuote" , value = "是否引用 0全部 1是 2否")
    private Integer isQuote;

    @ApiModelProperty(name = "synStatus" , value = "同步状态 0未同步 1同步成功 2同步异常 3引用价格表已被删除")
    private Integer synStatus;

    @ApiModelProperty(name = "quotePriceCustomerGroupId" , value = "引用价格客户组id")
    private Long quotePriceCustomerGroupId;

    @ApiModelProperty(name = "quotePriceCustomerId" , value = "引用价格客户id")
    private Long quotePriceCustomerId;

    @ApiModelProperty(name = "addProfitWay", value = "添加利润方式 0不加, 1单独加 2统一加")
    private Integer addProfitWay;

    public String getAccountNo() {
        if(null == accountNo){
            accountNo = "";
        }
        return accountNo;
    }


    public Long getZoneId() {
        if(null == zoneId){
            zoneId = 0L;
        }
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public List<Integer> getSpecialCommodity() {
        return specialCommodity;
    }
}
