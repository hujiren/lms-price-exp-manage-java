package com.apl.lms.price.exp.lib.cache.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hjr start
 * @Classname PartnerCacheBo
 * @Date 2020/8/28 11:42
 */
@Data
public class PartnerCacheBo implements Serializable {

    private String cacheKey;

    @ApiModelProperty(name = "id", value = "服务商id")
    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "partnerCode", value = "服务商简码")
    private String partnerCode;

    @ApiModelProperty(name = "partnerShortName", value = "服务商简称")
    private String partnerShortName;

}
