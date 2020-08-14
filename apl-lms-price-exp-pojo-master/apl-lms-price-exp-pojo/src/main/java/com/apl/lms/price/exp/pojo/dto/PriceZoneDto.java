package com.apl.lms.price.exp.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@ApiModel(value="快递分区  更新对象", description="快递分区 更新对象")
public class PriceZoneDto extends Model<PriceZoneDto> {

    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "快递分区id", required = true)
    @NotNull(message = "快递分区id不能为空")
    private Long id;
    
    @ApiModelProperty(name = "channelCategory" , value = "渠道类型" , required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "zoneName" , value = "快递分区名称" , required = true)
    @NotBlank(message = "快递分区名称不能为空")
    private String zoneName;



}
