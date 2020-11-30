package com.apl.lms.price.exp.pojo.bo;

import java.io.Serializable;

/**
 * @author hjr start
 * @Classname CustomerGroupBo 客户组组装对象
 * @Date 2020/9/24 15:12
 */
public class CustomerGroupBo implements Serializable {

    private Long customerGroupId;
    private String customerGroupName;

    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public String getCustomerGroupName() {
        return customerGroupName;
    }

    public void setCustomerGroupName(String customerGroupName) {
        this.customerGroupName = customerGroupName;
    }
}
