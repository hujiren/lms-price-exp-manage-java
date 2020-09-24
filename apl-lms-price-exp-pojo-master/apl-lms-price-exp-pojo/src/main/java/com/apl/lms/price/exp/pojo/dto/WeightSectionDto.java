package com.apl.lms.price.exp.pojo.dto;


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
}
