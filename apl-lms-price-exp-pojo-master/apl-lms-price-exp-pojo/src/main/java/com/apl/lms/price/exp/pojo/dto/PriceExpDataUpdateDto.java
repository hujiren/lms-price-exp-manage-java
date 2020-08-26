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
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataInsertDto
 * @Date 2020/8/20 9:38
 */
@Data
@ApiModel(value="价格表数据  更新对象", description="价格表数据 更新对象")
public class PriceExpDataUpdateDto extends Model<PriceExpDataUpdateDto> {


    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "价格表数据Id", required = true)
    @NotNull(message = "价格表数据Id不能为空")
    private Long id;

    @ApiModelProperty(name = "priceId" , value = "价格表主表id", hidden = true)
    private Long priceId;

    @ApiModelProperty(name = "priceData" , value = "价格表数据",required = true)
    @NotBlank(message = "价格表数据不能为空")
    private List priceData;
}
