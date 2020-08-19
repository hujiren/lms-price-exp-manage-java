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
@ApiModel(value="快递价格表  返回对象", description="快递价格表 返回对象")
public class PriceExpInfoVo extends Model<PriceExpInfoVo> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
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

    @ApiModelProperty(name = "zoneTabName" , value = "分区表名称")
    private String zoneTabName;

    @ApiModelProperty(name = "volumeWeightCardinal" , value = "体积重基数")
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private String customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private String customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "forWarderId" , value = "货代id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long forwarderId;

    @ApiModelProperty(name = "priceStatus" , value = "状态 1正常 2计账 3无效")
    private Integer priceStatus;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private String specialCommodity;

    @ApiModelProperty(name = "saleRemark" , value = "销售备注")
    private String saleRemark;

    @ApiModelProperty(name = "forwarderRemark" , value = "货代备注")
    private String forwarderRemark;

    @ApiModelProperty(name = "priceForm" , value = "格式")
    private Integer priceForm;

    @ApiModelProperty(name = "customerGroupInfo", value = "客户组")
    private List<CustomerGroupInfo> customerGroupInfo;

    @ApiModelProperty(name = "customers", value = "客户")
    private List<Customer> customers;
}
