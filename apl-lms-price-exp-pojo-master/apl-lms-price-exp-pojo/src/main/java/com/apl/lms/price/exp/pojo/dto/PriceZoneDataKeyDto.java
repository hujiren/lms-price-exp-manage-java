package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @Classname PriceZoneDataKeyDto
 * @Date 2020/8/31 15:13
 */
@Data
@ApiModel(value="销售价格表  返回对象", description="销售价格表 返回对象")
public class PriceZoneDataKeyDto extends Model<PriceZoneDataKeyDto> {

    @ApiModelProperty(name = "keyword" , value = "关键字")
    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }
}
