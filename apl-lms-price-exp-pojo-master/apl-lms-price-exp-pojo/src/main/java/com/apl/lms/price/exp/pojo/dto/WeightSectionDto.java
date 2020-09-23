package com.apl.lms.price.exp.pojo.dto;

import lombok.Data;

//重量段  price_format=1
@Data
public class WeightSectionDto {
    
    // 下标
    private int index;

    // 包裹类型 1 DOC / 2 WPX / 3 PAK  
    private int packType;

    // 计费方式 1首重      3累加 4单位重 5计算好
    private int chargingWay;

    // 起始重
    private Double weightStart;

    // 截止重
    private Double weightEnd;

    // 单位重
    private Double weightAdd;

    // 首重
    private Double weightFirst;

    // List<List<Ojbect>>  list
    // List<WeightSectionDto> list
    
    // "[[1,1, 1, 0, 0.5, 0.5, 0.5], [2,1, 1,0.5, 5, 0.5, 0.5], [1,2, 1, 0, 0.5, 0.5, 0.5] ]"

    
}
