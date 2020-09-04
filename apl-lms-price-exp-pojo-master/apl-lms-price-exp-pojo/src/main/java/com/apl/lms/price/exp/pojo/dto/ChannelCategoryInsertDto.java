package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/5 - 18:00
 */
@Data
@ApiModel(value="渠道类型  新增对象", description="渠道类型 新增对象")
public class ChannelCategoryInsertDto extends Model<ChannelCategoryInsertDto> {


    @ApiModelProperty(name = "channelCategory" , value = "渠道类型" , required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "weightWay" , value = "计泡方式" , required = true)
    @NotBlank(message = "计泡方式不能为空")
    private String weightWay;

    @ApiModelProperty(name = "volumeWeightWay" , value = "材积计重方式 1按多件 2按单件及进位 3按单件不进位" , required = true)
    @TypeValidator(value = {"0","1","2","3"} , message = "材积计重值错误")
    @NotNull(message = "材积计重方式不能为空")
    private Integer volumeWeightWay;

    @ApiModelProperty(name = "carrier" , value = "承运商" , required = true)
    @NotBlank(message = "承运商不能为空")
    private String carrier;

    @ApiModelProperty(name = "cargoType" , value = "运件类型 1国际快递 2国际空运 3国际海运 4国际小巴 5空派 6海派" , required = true)
    @TypeValidator(value = {"1","2","3","4","5","6"} , message = "运件类型错误")
    @NotNull(message = "运件类型不能为空")
    private Integer cargoType;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积基数" , required = true)
    @Range(min = 1, message = "体重基数不能小于1")
    @NotNull(message = "体积基数不能为空")
    private Integer volumeDivisor;
}