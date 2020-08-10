package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:36
 */
@Data
@ApiModel(value="快递价格表  插入对象", description="快递价格表 插入对象")
public class PriceExpListInsertDto extends Model<PriceExpListInsertDto> {


    @ApiModelProperty(name = "priceCode" , value = "价格表代码" , required = true)
    @NotBlank(message = "价格表代码不能为空")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称" , required = true)
    @NotBlank(message = "价格表名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "saleName" , value = "销售名称")
//    @NotBlank(message = "销售名称不能为空")
    private String saleName;

    @ApiModelProperty(name = "startDate" , value = "起始日期")
//    @NotNull(message = "起始日期不能为空")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期")
//    @NotNull(message = "截止日期不能为空")
    private Long endDate;

    @ApiModelProperty(name = "currency" , value = "币制" , required = true)
    @NotBlank(message = "币制不能为空")
    private String currency;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型" , required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "zoneTabId" , value = "分区表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    @NotNull(message = "分区表Id类型不能为空")
    private Long zoneTabId;

    @ApiModelProperty(name = "volumeWeightCardinal" , value = "体积重基数" , required = true)
    @NotNull(message = "体积重基数不能为空")
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
//    @NotNull(message = "账号类型账号类型不能为空")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
//    @NotBlank(message = "快递账号不能为空")
    private String accountNo;

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id" , required = true)
    @NotEmpty(message = "客户组id不能为空")
    private List<Long> customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称" , required = true)
    @NotEmpty(message = "客户组不能为空")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
//    @NotEmpty(message = "客户ids不能为空")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerIdsName" , value = "客户ids名称")
//    @NotEmpty(message = "客户ids名称不能为空")
    private String customerIdsName;

    @ApiModelProperty(name = "forwarderId" , value = "货代id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long forwarderId;

    @ApiModelProperty(name = "priceStatus" , value = "价格表状态 1正常 2计账 3无效" , required = true)
    @TypeValidator(value = {"1","2","3"} , message = "价格表状态错误")
    @NotNull(message = "价格表状态不能为空")
    private Integer priceStatus;

    @ApiModelProperty(name = "aging" , value = "时效")
//    @NotBlank(message = "时效不能为空")
    private String aging;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
//    @NotEmpty(message = "特殊物品不能为空")
    private List<String> specialCommodity;

    @ApiModelProperty(name = "saleRemark" , value = "销售备注")
    private String saleRemark;

    @ApiModelProperty(name = "forwarderRemark" , value = "货代备注")
    private String forwarderRemark;

}
