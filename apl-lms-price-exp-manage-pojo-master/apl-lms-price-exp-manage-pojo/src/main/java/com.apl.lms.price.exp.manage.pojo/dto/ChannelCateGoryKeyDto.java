package com.apl.lms.price.exp.manage.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author hjr start
 * @date 2020/8/5 - 18:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="渠道类型  查询对象", description="渠道类型 查询对象")
public class ChannelCateGoryKeyDto extends Model<ChannelCateGoryKeyDto> {


    @ApiModelProperty(name = "keyword" , value = "渠道类型" , required = true)
    @NotBlank(message = "关键字不能为空")
    private String keyword;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }
}
