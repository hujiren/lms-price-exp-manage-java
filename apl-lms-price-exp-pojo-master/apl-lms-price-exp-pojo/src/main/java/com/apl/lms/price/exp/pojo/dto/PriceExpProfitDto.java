package com.apl.lms.price.exp.pojo.dto;

import com.apl.lms.price.exp.pojo.bo.CustomerGroupBo;
import com.apl.sys.lib.cache.bo.CustomerGroupCacheBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpProfitDto
 * @Date 2020/9/11 11:14
 */
@ApiModel(value = "快递销售价格利润-组装对象", description = "快递销售价格利润-组装对象")
public class PriceExpProfitDto {

    @ApiModelProperty(name = "customerGroup" , value = "客户组", required = true)
    @NotBlank(message = "客户组不能为空")
    private List<CustomerGroupBo> customerGroups;

    @ApiModelProperty(name = "zoneNum", value = "分区号")
    @Length(max = 100, message = "分区号最大长度为100")
    private String zoneNum;

    @ApiModelProperty(name = "countryCode", value = "国家简码")
    @Length(max = 500, message = "国家简码最大长度为500")
    private String countryCode;

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

    // 客户组,  分区号,  国家简码, 起始重, 截止重, 首重加, 单位重加, 比例加
    public PriceExpProfitDto(List<CustomerGroupBo> customerGroups, String zoneNum,  String countryCode, Double startWeight, Double endWeight, Double firstWeightProfit, Double unitWeightProfit, Double proportionProfit) {
        this.customerGroups = customerGroups;
        this.zoneNum = zoneNum;
        this.countryCode = countryCode;
        this.startWeight = startWeight;
        this.endWeight = endWeight;
        this.firstWeightProfit = firstWeightProfit;
        this.unitWeightProfit = unitWeightProfit;
        this.proportionProfit = proportionProfit;
    }

    public PriceExpProfitDto(){}

    public List<CustomerGroupBo> getCustomerGroups() {
        return customerGroups;
    }

    public void setCustomerGroups(List<CustomerGroupBo> customerGroups) {
        this.customerGroups = customerGroups;
    }

    public String getZoneNum() {
        return zoneNum;
    }

    public void setZoneNum(String zoneNum) {
        this.zoneNum = zoneNum;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(Double startWeight) {
        this.startWeight = startWeight;
    }

    public Double getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(Double endWeight) {
        this.endWeight = endWeight;
    }

    public Double getFirstWeightProfit() {
        return firstWeightProfit;
    }

    public void setFirstWeightProfit(Double firstWeightProfit) {
        this.firstWeightProfit = firstWeightProfit;
    }

    public Double getUnitWeightProfit() {
        return unitWeightProfit;
    }

    public void setUnitWeightProfit(Double unitWeightProfit) {
        this.unitWeightProfit = unitWeightProfit;
    }

    public Double getProportionProfit() {
        return proportionProfit;
    }

    public void setProportionProfit(Double proportionProfit) {
        this.proportionProfit = proportionProfit;
    }
}
