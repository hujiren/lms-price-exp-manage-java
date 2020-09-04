package com.apl.lms.price.exp.pojo.vo;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@ApiModel(value="快递分区  返回对象", description="快递分区 返回对象")
public class PriceZoneNameVo extends Model<PriceZoneNameVo> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "快递分区id", required = true)
    private Long id;
    
    @ApiModelProperty(name = "channelCategory" , value = "渠道类型" , required = true)
    private String channelCategory;

    @ApiModelProperty(name = "zoneName" , value = "快递分区名称" , required = true)
    private String zoneName;

}
