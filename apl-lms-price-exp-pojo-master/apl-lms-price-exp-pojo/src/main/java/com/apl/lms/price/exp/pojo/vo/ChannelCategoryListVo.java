package com.apl.lms.price.exp.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020/8/5 - 18:00
 */
@Data
@TableName("channel_category")
@ApiModel(value="渠道类型-列表返回对象", description="渠道类型-列表返回对象")
public class ChannelCategoryListVo extends Model<ChannelCategoryListVo> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "渠道类型id")
    private Long id;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;
}
