package com.apl.lms.price.exp.pojo.dto;

import com.apl.lms.price.exp.pojo.bo.CustomerGroupBo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpProfitDto
 * @Date 2020/9/11 11:14
 */
@ApiModel(value = "统一利润-组装对象", description = "统一利润-组装对象")
public class UnifyProfitDto {

    @ApiModelProperty(name = "id", value = "id", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(name = "customerGroup" , value = "客户组", required = true)
    @NotEmpty(message = "客户组不能为空")
    private List<CustomerGroupBo> customerGroups;

    @ApiModelProperty(name = "startWeight", value = "起始重")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight", value = "截止重")
    private Double endWeight;

    @ApiModelProperty(name = "firstWeightProfit", value = "首重加")
    private Double firstWeightProfit;

    @ApiModelProperty(name = "unitWeightProfit", value = "单位重加")
    private Double unitWeightProfit;

    @ApiModelProperty(name = "proportionProfit", value = "比例加")
    private Double proportionProfit;

    // 客户组, 起始重, 截止重, 首重加, 单位重加, 比例加
    public UnifyProfitDto(Long id, List<CustomerGroupBo> customerGroups, String zoneNum, String countryCode, Double startWeight, Double endWeight, Double firstWeightProfit, Double unitWeightProfit, Double proportionProfit) {
        this.id = id;
        this.customerGroups = customerGroups;
        this.startWeight = startWeight;
        this.endWeight = endWeight;
        this.firstWeightProfit = firstWeightProfit;
        this.unitWeightProfit = unitWeightProfit;
        this.proportionProfit = proportionProfit;
    }

    public UnifyProfitDto(){}

    public List<CustomerGroupBo> getCustomerGroups() {
        return customerGroups;
    }

    public void setCustomerGroups(List<CustomerGroupBo> customerGroups) {
        this.customerGroups = customerGroups;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
