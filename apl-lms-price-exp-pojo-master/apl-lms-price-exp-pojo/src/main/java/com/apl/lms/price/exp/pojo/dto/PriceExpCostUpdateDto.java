package com.apl.lms.price.exp.pojo.dto;
import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @Classname PriceExpCostPo
 * @Date 2020/8/20 14:43
 */
@Data
@ApiModel(value="成本价格表  持久化对象", description="成本价格表 持久化对象")
public class PriceExpCostUpdateDto extends Model<PriceExpCostUpdateDto> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "成本价格主表Id", required = true)
    @NotNull(message = "成本价格表Id不能为空")
    private Long id;

    @ApiModelProperty(name = "partnerId" , value = "服务商Id", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "服务商Id不能为空")
    private Long partnerId;

    @ApiModelProperty(name = "priceStatus" , value = "成本价格表状态 1正常 2计账 3无效")
    @TypeValidator(value = {"0","1","2","3"} , message = "价格表状态错误")
    private Integer priceStatus;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称", required = true)
    @NotBlank(message = "价格表名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "priceMainId" , value = "主表Id",required = true)
    private Long priceMainId;
}
