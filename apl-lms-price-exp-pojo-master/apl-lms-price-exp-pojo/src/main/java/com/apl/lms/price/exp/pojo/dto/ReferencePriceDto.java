package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname ReferencePriceDto
 * @Date 2020/9/11 16:33
 */
@Data
@ApiModel(value="引用价格表对象", description="引用价格表对象")
public class ReferencePriceDto extends Model<ReferencePriceDto> {

    @ApiModelProperty(name = "startDate" , value = "起始日期", required = true)
    @NotNull(message = "起始日期不能为空")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期", required = true)
    @NotNull(message = "截止日期不能为空")
    private Long endDate;

    @ApiModelProperty(name = "currency" , value = "币制", required = true)
    @NotBlank(message = "币制不能为空")
    private String currency;

    @ApiModelProperty(name = "zoneId" , value = "分区表id", required = true)
    @NotNull(message = "分区表id不能为空")
    private Long zoneId;

    @ApiModelProperty(name = "accountType" , value = "账号类型", required = true)
    @NotNull(message = "账号类型不能为空")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    private Long partnerId;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格id", required = true)
    @Min(value = 0, message = "引用价格id不能小于0")
    @NotNull( message = "引用价格id不能为空")
    private Long quotePriceId;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称", required = true)
    @NotBlank(message = "价格表名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private List<Long> customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "priceMainId" , value = "价格主表id", required = true)
    @Min(value = 0, message = "引用价格主表id不能小于0")
    @NotNull( message = "引用价格主表id不能为空")
    private Long priceMainId;

    @ApiModelProperty(name = "remark" , value = "备注")
    private String remark;

    public List<Long> getCustomerGroupsId() {
        if(null == customerGroupsId)
            customerGroupsId = new ArrayList<>();
        return customerGroupsId;
    }

    public List<Long> getCustomerIds() {
        if(null == customerIds)
            customerIds = new ArrayList<>();
        return customerIds;
    }
}
