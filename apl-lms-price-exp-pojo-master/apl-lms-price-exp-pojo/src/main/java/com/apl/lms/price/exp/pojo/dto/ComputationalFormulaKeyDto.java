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

    @ApiModelProperty(name = "zoneNum" , value = "分区号")
    private String zoneNum;

    @ApiModelProperty(name = "country" , value = "国家")
    private String country;

    @ApiModelProperty(name = "startingWeight" , value = "起始重量")
    private Double startingWeight;

    @ApiModelProperty(name = "endingWeight" , value = "截止重量")
    private Double endingWeight;

    @ApiModelProperty(name = "packageType" , value = "包裹类型")
    private String packageType;

    @ApiModelProperty(name = "keyword" , value = "关键字")
    private String keyword;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }


    public String getZoneNum() {
        if (zoneNum != null && zoneNum.trim().equals(""))
            zoneNum = null;

        return zoneNum;
    }

    public String getCountry() {
        if (country != null && country.trim().equals(""))
            country = null;

        return country;
    }

    public String getPackageType() {
        if (packageType != null && packageType.trim().equals(""))
            packageType = null;

        return packageType;
    }

}
