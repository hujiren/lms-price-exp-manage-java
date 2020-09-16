package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@ApiModel(value="价格表主数据  返回对象", description="价格表主数据 返回对象")
public class PriceExpDataVo extends Model<PriceExpDataVo> implements Serializable {

    @ApiModelProperty(name = "priceData" , value = "价格表数据")
    private String priceData;
}
