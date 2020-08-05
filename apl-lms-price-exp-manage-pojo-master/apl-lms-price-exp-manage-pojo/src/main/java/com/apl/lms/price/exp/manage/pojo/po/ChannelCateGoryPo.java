package com.apl.lms.price.exp.manage.pojo.po;

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
@ApiModel(value="渠道类型  持久化对象", description="渠道类型 持久化对象")
public class ChannelCateGoryPo extends Model<ChannelCateGoryPo> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "渠道类型id" , required = true)
    private Long id;

    @ApiModelProperty(name = "name" , value = "渠道类型名称" , required = true)
    private String name;


}
