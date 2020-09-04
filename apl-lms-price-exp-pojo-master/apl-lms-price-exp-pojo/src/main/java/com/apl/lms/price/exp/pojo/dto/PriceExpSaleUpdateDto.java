package com.apl.lms.price.exp.pojo.dto;
import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpSalePo
 * @Date 2020/8/20 14:31
 */
@Data
@ApiModel(value="销售价格表  持久化对象", description="销售价格表 持久化对象")
public class PriceExpSaleUpdateDto extends Model<PriceExpSaleUpdateDto> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "销售价格表Id", required = true)
    @NotNull(message = "销售价格表id不能为空")
    private Long id;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称",required = true)
    @NotBlank(message = "价格表名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private List<Long> customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "priceStatus" , value = "销售价格表状态 1正常 2计账 3无效")
    @TypeValidator(value = {"0","1","2","3"} , message = "销售价格表状态错误")
    private Integer priceStatus;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型", required = true)
    private String channelCategory;

    @ApiModelProperty(name = "priceMainId" , value = "主表Id")
    private Long priceMainId;
}
