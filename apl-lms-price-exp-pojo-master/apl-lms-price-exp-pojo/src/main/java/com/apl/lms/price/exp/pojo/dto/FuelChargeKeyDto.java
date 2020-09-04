package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@ApiModel(value="燃油费  查询对象", description="燃油费 查询对象")
public class FuelChargeKeyDto extends Model<FuelChargeKeyDto> {


    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;
}
