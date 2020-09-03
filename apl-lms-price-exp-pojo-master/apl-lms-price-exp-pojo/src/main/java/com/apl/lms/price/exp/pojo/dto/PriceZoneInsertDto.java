package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@ApiModel(value="快递分区名称  插入对象", description="快递分区名称 插入对象")
public class PriceZoneInsertDto extends Model<PriceZoneInsertDto> {

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型" , required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "zoneName" , value = "快递分区名称" , required = true)
    @NotBlank(message = "快递分区名称不能为空")
    private String zoneName;



}
