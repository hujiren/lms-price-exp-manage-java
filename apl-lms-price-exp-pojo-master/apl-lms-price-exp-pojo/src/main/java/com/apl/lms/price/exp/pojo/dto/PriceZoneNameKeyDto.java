package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@ApiModel(value="快递分区名称-条件查询对象", description="快递分区名称-条件查询对象")
public class PriceZoneNameKeyDto {

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "keyword" , value = "关键字")
    private String keyword;

    public void setChannelCategory(String channelCategory) {
        this.channelCategory = channelCategory;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }

    public String getChannelCategory() {
        if (channelCategory != null && channelCategory.trim().equals(""))
            channelCategory = null;

        return channelCategory;
    }

}
