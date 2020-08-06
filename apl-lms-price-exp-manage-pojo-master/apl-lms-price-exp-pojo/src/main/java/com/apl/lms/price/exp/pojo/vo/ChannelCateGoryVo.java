package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjr start
 * @date 2020/8/5 - 18:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("price_channel_category")
@ApiModel(value="渠道类型  返回对象", description="渠道类型 返回对象")
public class ChannelCateGoryVo extends Model<ChannelCateGoryVo> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "渠道类型id" , required = true)
    private Long id;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型" , required = true)
    private String channelCategory;

    @ApiModelProperty(name = "weightWay" , value = "计泡方式" , required = true)
    private String weightWay;

    @ApiModelProperty(name = "volumeWeightWay" , value = "材积计重方式" , required = true)
    private Integer volumeWeight;

    @ApiModelProperty(name = "carrier" , value = "承运商" , required = true)
    private String carrier;

    @ApiModelProperty(name = "cargoType" , value = "运件类型" , required = true)
    private Integer cargoType;

    @ApiModelProperty(name = "volumeWeighCardinal" , value = "体积基数" , required = true)
    private String volumeWeighCardinal;
}
