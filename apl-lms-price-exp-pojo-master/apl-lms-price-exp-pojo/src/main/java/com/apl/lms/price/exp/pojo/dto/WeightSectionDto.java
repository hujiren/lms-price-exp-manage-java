package com.apl.lms.price.exp.pojo.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

//重量段  price_format=1
public class WeightSectionDto {
    
    // 下标
    private Integer index;

    // 包裹类型 1 DOC / 2 WPX / 3 PAK  
    private Integer packType;

    // 计费方式 1首重  2续重 3累加 4单位重 5计算好
    private Integer chargingWay;

    // 起始重
    private Double weightStart;

    // 截止重
    private Double weightEnd;

    // 单位重
    private Double weightAdd;

    // 首重
    private Double weightFirst;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPackType() {
        return packType;
    }

    public void setPackType(Integer packType) {
        this.packType = packType;
    }

    public Integer getChargingWay() {
        return chargingWay;
    }

    public void setChargingWay(Integer chargingWay) {
        this.chargingWay = chargingWay;
    }

    public Double getWeightStart() {
        return weightStart;
    }

    public void setWeightStart(Double weightStart) {
        this.weightStart = weightStart;
    }

    public Double getWeightEnd() {
        return weightEnd;
    }

    public void setWeightEnd(Double weightEnd) {
        this.weightEnd = weightEnd;
    }

    public Double getWeightAdd() {
        return weightAdd;
    }

    public void setWeightAdd(Double weightAdd) {
        this.weightAdd = weightAdd;
    }

    public Double getWeightFirst() {
        return weightFirst;
    }

    public void setWeightFirst(Double weightFirst) {
        this.weightFirst = weightFirst;
    }

    /**
     * @author hjr start
     * @Classname PriceExpProfitDto
     * @Date 2020/11/3 10:45
     */
    @Data
    @ApiModel(value = "快递报价利润-交互对象", description = "快递报价利润-交互对象")
    public static class PriceExpProfitSaveDto implements Serializable {

        @ApiModelProperty(name = "id", value = "价格表id", required = true)
        @TableId(value = "id", type = IdType.INPUT )
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Long id;

        @ApiModelProperty(name = "increaseProfit", value = "上调的利润", required = true)
        private List<PriceExpProfitDto> increaseProfit;

        @ApiModelProperty(name = "finalProfit", value = "最终利润", hidden = true)
        private List<PriceExpProfitDto> finalProfit;

        private static final long serialVersionUID = 1L;

    }
}
