package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpMainPo
 * @Date 2020/8/20 14:36
 */
@Data
@ApiModel(value="快递价格主表  持久化对象", description="快递价格主表 持久化对象")
public class PriceExpMainUpdDto extends Model<PriceExpMainUpdDto> {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "快递价格主表Id", hidden = true)
    private Long id;

    @ApiModelProperty(name = "startDate" , value = "起始日期", required = true)
    @NotNull(message = "起始日期不能为空")
    private Long startDate;

    @ApiModelProperty(name = "endDate" , value = "截止日期", required = true)
    @NotNull(message = "截止日期不能为空")
    private Long endDate;

    @ApiModelProperty(name = "currency" , value = "币制", required = true)
    @NotBlank(message = "币制")
    @Range(max = 5, message = "最大长度5")
    private String currency;

    @ApiModelProperty(name = "zoneId" , value = "分区表Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long zoneId;

    @ApiModelProperty(name = "accountType" , value = "账号类型 1代理账号 2贸易账号 3第三方账号", required = true)
    @NotNull(message = "账号类型不能为空")
    private Integer accountType;

    @ApiModelProperty(name = "accountNo" , value = "快递账号")
    private String accountNo;

    @ApiModelProperty(name = "volumeDivisor" , value = "体积除数", required = true)
    @NotNull(message = "体积除数不能为空")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "specialCommodity" , value = "特殊物品")
    private List<Integer> specialCommodity;

    @ApiModelProperty(name = "priceForm" , value = "价格表格式 1横向 2纵向")
    @TypeValidator(value = {"1","2"} , message = "价格表格式错误")
    private Integer priceForm;

    @ApiModelProperty(name = "startWeight" , value = "起始重", required = true)
    @NotNull(message = "起始重量不能为空")
    private Double startWeight;

    @ApiModelProperty(name = "endWeight" , value = "截止重", required = true)
    @NotNull(message = "截止重量不能为空")
    private Double endWeight;

    @ApiModelProperty(name = "pricePublishedId" , value = "公布价id", required = true)
    @NotNull(message = "公布价不能为空")
    private Long pricePublishedId;

    @ApiModelProperty(name = "isPublishedPrice" , value = "是否是公布价 1是 2不是", required = true)
    @TypeValidator(value = {"1","2"} , message = "公布价格错误")
    @NotNull(message = "公布价不能为空")
    private Integer isPublishedPrice;

    @ApiModelProperty(name = "aging" , value = "时效")
    private String aging;

    @ApiModelProperty(name = "mainStatus" , value = "主表状态 1正常 2计账 3无效")
    @TypeValidator(value = {"0","1","2","3"} , message = "账号类型错误")
    private Integer mainStatus;

    public List<Integer> getSpecialCommodity() {
        if(null==specialCommodity)
            specialCommodity = new ArrayList<>();

        return specialCommodity;
    }
}
