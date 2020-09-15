package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataPo
 * @Date 2020/8/19 15:12
 */
@Data
@ApiModel(value="价格表主数据  返回对象", description="价格表主数据 返回对象")
public class PriceExpDataVo extends Model<PriceExpDataVo> implements Serializable {

//    @TableId(value = "id", type = IdType.INPUT)
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    @ApiModelProperty(name = "id" , value = "价格表数据id")
//    private Long id;

    @ApiModelProperty(name = "priceData" , value = "价格表数据")
    private String priceData;
}
