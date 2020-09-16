package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020/8/5 - 18:00
 */
@Data
@ApiModel(value="渠道类型-条件查询对象", description="渠道类型-条件查询对象")
public class ChannelCategoryKeyDto {


    @ApiModelProperty(name = "keyword" , value = "关键字")
    private String keyword;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }
}
