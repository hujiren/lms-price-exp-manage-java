package com.apl.lms.price.exp.pojo.dto;
import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpSalePo
 * @Date 2020/8/20 14:31
 */
@Data
@ApiModel(value="销售价格表  持久化对象", description="销售价格表 持久化对象")
public class PriceExpSaleAddDto extends Model<PriceExpSaleAddDto> {

    @ApiModelProperty(name = "salePriceCode" , value = "销售价格表代码")
    private String salePriceCode;

    @ApiModelProperty(name = "salePriceName" , value = "销售价格表名称")
    @NotBlank(message = "价格表名称不能为空")
    private String salePriceName;

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private List<Long> customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "priceStatus" , value = "销售价格表状态 1正常 2计账 3无效", hidden = true)
    @TypeValidator(value = {"0","1","2","3"} , message = "销售价格表状态错误")
    private Integer priceStatus;

    @ApiModelProperty(name = "quotePriceId" , value = "引用价格id", hidden = true)
    private Long quotePriceId;

    @ApiModelProperty(name = "quotePriceFinalId" , value = "引用价格最终id", hidden = true)
    private Long quotePriceFinalId;

    @ApiModelProperty(name = "saleChannelCategory" , value = "销售价格表渠道类型")
    private String saleChannelCategory;

    @ApiModelProperty(name = "salePriceMainId", value = "销售价格-主表Id")
    private Long salePriceMainId;

    @ApiModelProperty(name = "saleRemark", value = "销售价格表销售备注")
    private String saleRemark;

    @ApiModelProperty(name = "partnerRemark", value = "销售价格表合作商备注")
    private String partnerRemark;


}
