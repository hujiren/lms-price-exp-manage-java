package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpAddDto
 * @Date 2020/9/1 15:48
 */
@Data
@ApiModel(value="快递价格表  插入对象", description="快递价格表 插入对象")
public class PriceExpAddDto extends Model<PriceExpAddDto> implements Serializable {

    @ApiModelProperty(name = "startDate" , value = "起始日期", required = true)
    @NotNull(message = "起始日期不能为空")
    @Min(value = 1, message = "起始日期最小值为1")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期", required = true)
    @NotNull(message = "截止日期不能为空")
    @Min(value = 1, message = "截止日期最小值为1")
    private Long endDate;

    @ApiModelProperty(name = "currency" , value = "币制", required = true)
    @NotBlank(message = "币制不能为空")
    private String currency;

    @ApiModelProperty(name = "zoneId" , value = "分区表Id")
    @Min(value = 0, message = "分区表id最小值为0")
    private Long zoneId;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号", required = true)
    @Range(min = 1, max = 3, message = "账号类型值只能为1或2或3")
    @NotNull(message = "账号类型不能为空")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数", required = true)
    @Range(min = 5000, max = 9999, message = "体积除数错误")
    @NotNull(message = "体积除数不能为空")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<Integer> specialCommodity;

    @ApiModelProperty(name = "priceForm" , value = "价格表格式 1横向 2纵向")
    @Range(min = 1, max = 2, message = "价格表格式值只能为1或2")
    private Integer priceForm;

    @ApiModelProperty(name = "startWeight" , value = "起始重", required = true)
    @Min(value = 0, message = "起始重量最小值为0")
    @NotNull(message = "起始重量不能为空")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重", required = true)
    @Range(min = 0, max = 100000, message = "截止重量最小值为0, 最大值为100000")
    @NotNull(message = "截止重量不能为空")
    private Double endWeight;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id")
    @Min(value = 0, message = "公布价最小值为0")
    private Long pricePublishedId;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是", required = true)
    @NotNull(message = "是否是公布价不能为空")
    @Range(min = 1, max = 2, message = "是否是公布价错误")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    @ApiModelProperty(name = "partnerId" , value = "服务商id", required = true)
    @Min(value = 0, message = "服务商id不能为空")
    private Long partnerId;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码")
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称", required = true)
    @NotBlank(message = "价格表名称不能为空")
    private String priceName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "partnerRemark", value = "服务商备注")
    private String partnerRemark;

    @ApiModelProperty(name = "customerGroupsId" , value = "客户组id")
    private List<Long> customerGroupsId;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称")
    private String customerGroupsName;

    @ApiModelProperty(name = "customerIds" , value = "客户ids")
    private List<Long> customerIds;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "saleRemark" , value = "销售备注")
    private String saleRemark;

    @ApiModelProperty(name = "axisTransverse" , value = "x轴数据", required = true)
    @NotEmpty(message = "x轴数据不能为空")
    private List<Object> axisTransverse;

    @ApiModelProperty(name = "axisPortrait" , value = "y轴数据", required = true)
    @NotEmpty(message = "y轴数据不能为空")
    private List<Object> axisPortrait;

    @ApiModelProperty(name = "priceData" , value = "价格表数据", required = true)
    @NotEmpty(message = "价格表数据不能为空")
    private List<Object> priceData;

}
