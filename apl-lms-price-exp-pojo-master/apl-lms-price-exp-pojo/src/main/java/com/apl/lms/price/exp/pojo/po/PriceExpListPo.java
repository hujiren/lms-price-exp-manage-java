package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("price_exp_list")
@ApiModel(value="快递价格表  持久化对象", description="快递价格表 持久化对象")
public class PriceExpListPo extends Model<PriceExpListPo> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "价格表id")
    private Long id;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称")
    private String priceName;

    @ApiModelProperty(name = "saleName" , value = "销售名称")
    private String saleName;

    @ApiModelProperty(name = "startDate" , value = "起始日期")
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
    private Timestamp endDate;

    @ApiModelProperty(name = "currency" , value = "币制")
    private String currency;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "zoneTabId" , value = "分区表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long zoneTabId;

    @ApiModelProperty(name = "volumeWeightCardinal" , value = "体积重基数")
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private List<Long> customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "forWarderId" , value = "货代id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long forwarderId;

    @ApiModelProperty(name = "priceStatus" , value = "价格表状态 1正常 2计账 3无效")
    private Integer priceStatus;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<Long> specialCommodity;

    @ApiModelProperty(name = "saleRemark" , value = "销售备注")
    private String saleRemark;

    @ApiModelProperty(name = "forwarderRemark" , value = "货代备注")
    private String forwarderRemark;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格id")
    private Integer quotePriceId;

    @ApiModelProperty(name = "quotePriceFinalId" , value = "引用价格最终id")
    private Integer quotePriceFinalId;

    @ApiModelProperty(name = "priceForm" , value = "价格表格式")
    private Integer priceForm;
}
