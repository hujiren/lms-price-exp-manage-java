package com.apl.lms.price.exp.pojo.dto;

import com.apl.lib.validate.TypeValidator;
import com.apl.lms.common.query.manage.dto.SpecialCommodityDto;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpAddDto
 * @Date 2020/9/1 15:48
 */
@Data
@ApiModel(value="快递成本价-修改对象", description="快递成本价-修改对象")
public class PriceExpCostUpdDto extends PriceExpBaseUpdDto  {

    @ApiModelProperty(name = "partnerId" , value = "服务商id", required = true)
    @Min(value = 0, message = "服务商id不能为空")
    private Long partnerId;

}
