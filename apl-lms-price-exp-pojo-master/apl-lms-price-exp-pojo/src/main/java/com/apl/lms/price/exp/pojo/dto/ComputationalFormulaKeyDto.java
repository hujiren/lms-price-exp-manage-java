package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020/8/7 - 17:27
 */
@Data
@ApiModel(value="计算公式  查询对象", description="计算公式 查询对象")
public class ComputationalFormulaKeyDto extends Model<ComputationalFormulaKeyDto> {

    @ApiModelProperty(name = "priceZone" , value = "分区")
    private String priceZone;

    @ApiModelProperty(name = "country" , value = "国家")
    private String country;

    @ApiModelProperty(name = "startingWeight" , value = "起始重量")
    private Double startingWeight;

    @ApiModelProperty(name = "endingWeight" , value = "截止重量")
    private Double endingWeight;

    @ApiModelProperty(name = "packingType" , value = "包裹类型")
    private String packingType;

    @ApiModelProperty(name = "keyword" , value = "关键字")
    private String keyword;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }


    public String getPriceZone() {
        if (priceZone != null && priceZone.trim().equals(""))
            priceZone = null;

        return priceZone;
    }

    public String getCountry() {
        if (country != null && country.trim().equals(""))
            country = null;

        return country;
    }

    public String getPackingType() {
        if (packingType != null && packingType.trim().equals(""))
            packingType = null;

        return packingType;
    }

}
