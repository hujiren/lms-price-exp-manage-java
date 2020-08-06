package com.apl.lms.price.exp.manage.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springfox.documentation.spring.web.json.Json;

import java.sql.Timestamp;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("price_exp_list")
@ApiModel(value="快递价格表  修改对象", description="快递价格表 修改对象")
public class ExpListDto extends Model<ExpListDto> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格表id", required = true)
    private Long id;

    @ApiModelProperty(name = "priceCode" , value = "价格表代码" , required = true)
    private String priceCode;

    @ApiModelProperty(name = "priceName" , value = "价格表名称" , required = true)
    private String priceName;

    @ApiModelProperty(name = "saleName" , value = "销售名称" , required = true)
    private String saleName;

    @ApiModelProperty(name = "startDate" , value = "起始日期" , required = true)
    private Timestamp startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期" , required = true)
    private Timestamp endDate;

    @ApiModelProperty(name = "currency" , value = "币制" , required = true)
    private String currency;

    @ApiModelProperty(name = "channelCateGory" , value = "渠道类型" , required = true)
    private String channelCateGory;

    @ApiModelProperty(name = "zoneTabId" , value = "分区表Id" , required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long zoneTabId;

    @ApiModelProperty(name = "volumeWeightCardinal" , value = "体积重基数" , required = true)
    private Integer volumeWeightCardinal;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号" , required = true)
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号" , required = true)
    private String accountNo;

    @ApiModelProperty(name = "customerGroups" , value = "客户组" , required = true)
    private Json customerGroups;

    @ApiModelProperty(name = "customerIds" , value = "客户ids" , required = true)
    private Json customerIds;

    @ApiModelProperty(name = "forWarderId" , value = "货代id" , required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long forWarderId;

    @ApiModelProperty(name = "priceStatus" , value = "价格表状态 1正常 2计账 3无效" , required = true)
    @TypeValidator(value = {"0","1","2","3"} , message = "价格表状态错误")
    private Integer priceStatus;

    @ApiModelProperty(name = "aging" , value = "时效" , required = true)
    private String aging;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品" , required = true)
    private Json specialCommodity;

    @ApiModelProperty(name = "saleRemark" , value = "销售备注" , required = true)
    private String saleRemark;

    @ApiModelProperty(name = "forwarderRemark" , value = "货代备注" , required = true)
    private String forwarderRemark;

}
