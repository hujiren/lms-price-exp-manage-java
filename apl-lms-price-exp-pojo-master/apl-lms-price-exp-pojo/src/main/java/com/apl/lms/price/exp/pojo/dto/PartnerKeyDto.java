package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @Classname PartnerKeyDto
 * @Date 2020/8/26 9:46
 */
@Data
@ApiModel(value = "服务商-条件查询对象", description = "服务商-条件查询对象")
public class PartnerKeyDto {

    @ApiModelProperty(name = "keyword", value = "关键字")
    private String keyword;

    @ApiModelProperty(name = "code", value = "服务商简码")
    private String code;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }
}
