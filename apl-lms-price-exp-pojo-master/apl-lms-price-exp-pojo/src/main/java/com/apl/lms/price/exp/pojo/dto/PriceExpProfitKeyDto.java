package com.apl.lms.price.exp.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * <p>
 *  分页查询对象
 * </p>
 *
 * @author hjr
 * @since 2020-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value=" 分页查询对象", description=" 分页查询对象")
public class PriceExpProfitKeyDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(name = "keyword", value = "关键词")
    private String keyword;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }
}
