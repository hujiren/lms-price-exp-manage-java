package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpSalePo
 * @Date 2020/8/20 14:31
 */
@Data
@ApiModel(value="销售价格表  持久化对象", description="销售价格表 持久化对象")
public class PriceExpSaleAddDto extends Model<PriceExpSaleAddDto> {

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private List<Long> customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "saleRemark" , value = "销售备注")
    private String saleRemark;

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
