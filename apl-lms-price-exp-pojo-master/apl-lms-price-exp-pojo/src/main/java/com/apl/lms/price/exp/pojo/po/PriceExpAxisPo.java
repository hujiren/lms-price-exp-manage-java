package com.apl.lms.price.exp.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@TableName(value = "price_exp_axis")
@ApiModel(value="轴  持久化对象", description="轴 持久化对象")
public class PriceExpAxisPo extends Model<PriceExpAxisPo> implements Serializable {

    @ApiModelProperty(name = "id" , value = "id", required = true)
    private Long id;

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据", required = true)
    @NotBlank(message = "x轴数据不能为空")
    private String axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据", required = true)
    @NotBlank(message = "y轴数据不能为空")
    private String axisPortrait;

//    @ApiModelProperty(name = "innerOrgId" , value = "多租户id", required = true)
//    private Long innerOrgId;
}
