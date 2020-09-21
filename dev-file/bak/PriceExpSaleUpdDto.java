package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpSalePo
 * @Date 2020/8/20 14:31
 */
@Data
@ApiModel(value="快递销售价格表-更新对象", description="快递销售价格表-更新对象")
public class PriceExpSaleUpdDto extends PriceExpUpdDto {


    @ApiModelProperty(name = "customerGroup" , value = "客户组")
    private List<CustomerGroupDto> customerGroup;

    @ApiModelProperty(name = "customer" , value = "客户")
    private List<CustomerDto> customer;

}
