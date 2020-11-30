package com.apl.lms.price.exp.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpProfitDto
 * @Date 2020/9/11 11:14
 */
@Data
@ApiModel(value = "利润 中转对象", description = "利润 中转对象")
public class PriceExpProfitMergeBo implements Serializable {

    @ApiModelProperty(name = "customerGroup" , value = "客户组", required = true)
    @NotBlank(message = "客户组不能为空")
    private List<CustomerGroupBo> customerGroups;

    @ApiModelProperty(name = "zoneNum", value = "分区号")
    @Length(max = 100, message = "分区号最大长度为100")
    private String zoneNum;

    private List<String> zoneNumList;

    @ApiModelProperty(name = "countryCode", value = "国家简码")
    @Length(max = 500, message = "国家简码最大长度为500")
    private String countryCode;

    private List<String> countryCodeList;

    @ApiModelProperty(name = "startWeight", value = "起始重")
    @NotNull(message = "起始重不能为空")
    @Min(value = 0, message = "起始重不能小于0")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight", value = "截止重")
    @NotNull(message = "截止重不能为空")
    @Min(value = 0, message = "截止重不能小于0")
    private Double endWeight;

    @ApiModelProperty(name = "firstWeightProfit", value = "首重加")
    private Double firstWeightProfit;

    @ApiModelProperty(name = "unitWeightProfit", value = "单位重加")
    private Double unitWeightProfit;

    @ApiModelProperty(name = "proportionProfit", value = "比例加")
    private Double proportionProfit;


}
