package com.apl.lms.price.exp.pojo.vo;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.sql.Timestamp;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:36
 */
@Data
@ApiModel(value="快递价格表  返回对象", description="快递价格表 返回对象")
public class PriceExpListVo extends Model<PriceExpListVo> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格表id" , hidden = true)
    private Long id;

    @ApiModelProperty(name = "priceName" , value = "价格表名称" , required = true)
    private String priceName;

    @ApiModelProperty(name = "saleName" , value = "销售名称" , required = true)
    private String saleName;

    @ApiModelProperty(name = "startDate" , value = "起始日期" , required = true)
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期" , required = true)
    private Timestamp endDate;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型" , required = true)
    private String channelCategory;

    @ApiModelProperty(name = "volumeWeightCardinal" , value = "体积重基数" , required = true)
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "customerGroupsName" , value = "客户组名称" , required = true)
    private String customerGroupsName;

    @ApiModelProperty(name = "customersName" , value = "客户名称" , required = true)
    private String customersName;

    @ApiModelProperty(name = "forwarderName" , value = "货代名称" , required = true)
    private String forwarderName;

    @ApiModelProperty(name = "priceStatus" , value = "价格表状态 1正常 2计账 3无效" , required = true)
    private Integer priceStatus;

    @ApiModelProperty(name = "aging" , value = "时效" , required = true)
    private String aging;


}