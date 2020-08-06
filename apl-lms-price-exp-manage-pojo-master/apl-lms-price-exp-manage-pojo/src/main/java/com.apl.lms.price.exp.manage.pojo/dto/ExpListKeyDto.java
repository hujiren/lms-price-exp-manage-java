package com.apl.lms.price.exp.manage.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import springfox.documentation.spring.web.json.Json;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/5 - 12:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="快递价格表  按条件查询对象", description="快递价格表  按条件查询对象")
public class ExpListKeyDto {

    @ApiModelProperty(name = "channelCategory", value = "渠道类型")
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "volumeWeightCardinal", value = "体积基数")
    @Range(min = 1, message = "体积基数不能小于1")
    @NotNull(message = "体积基数不能为空")
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "accountType", value = "账号类型 1代理账号 2贸易账号 3第三方账号")
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
    @NotNull(message = "账号类型不能为空")
    private Integer accountType;

    @ApiModelProperty(name = "priceStatus", value = "价格表状态 1正常 2计账 3无效 4即将过期 5已过期")
    @NotNull(message = "价格表状态不能为空")
    @TypeValidator(value = {"0","1","2","3","4","5"} , message = "价格表状态错误")
    private Integer priceStatus;

    @ApiModelProperty(name = "customerGroups", value = "客户组")
    @NotBlank(message = "客户组不能为空")
    private Json customerGroups;

    @ApiModelProperty(name = "keyword", value = "关键词")
    @NotBlank(message = "关键词不能为空")
    private String keyword;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }
}
