package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname ReferenceSaleDto
 * @Date 2020/9/11 16:29
 */
@Data
@ApiModel(value="引用销售价格对象", description="引用销售价格对象")
public class ReferenceSaleDto extends Model<ReferenceSaleDto> {

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private List<Long> customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

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
