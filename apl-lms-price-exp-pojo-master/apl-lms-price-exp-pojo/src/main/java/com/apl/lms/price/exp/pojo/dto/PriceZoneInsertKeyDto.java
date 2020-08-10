package com.apl.lms.price.exp.pojo.dto;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hjr start
 * @date 2020/8/6 - 14:13
 */
@Data
@ApiModel(value="快递分区  查询对象", description="快递分区 查询对象")
public class PriceZoneInsertKeyDto extends Model<PriceZoneInsertKeyDto> {

    @ApiModelProperty(name = "keyword" , value = "关键字")
    private String keyword;

    public String getKeyword() {
        if (keyword != null && keyword.trim().equals(""))
            keyword = null;

        return keyword;
    }

}
