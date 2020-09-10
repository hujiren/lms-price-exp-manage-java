package com.apl.lms.price.exp.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value="分区表数据  返回对象", description="分区表数据 返回对象")
public class PriceZoneDataListVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "id" , value = "价格主表id")
    private Long id;

    @ApiModelProperty(name = "zoneNum" , value = "分区号")
    private String zoneNum;

    @ApiModelProperty(name = "countryCode" , value = "国家简码")
    private String countryCode;

    @ApiModelProperty(name = "countryNameCn" , value = "中文名称")
    private String countryNameCn;

    @ApiModelProperty(name = "countryNameEn" , value = "英文名称")
    private String countryNameEn;

//    public String getNameCn() {
//        return nameCn;
//    }
//
//    public void setNameCn(String nameCn) {
//        this.nameCn = nameCn;
//    }
//
//    public String getNameEn() {
//        return nameEn;
//    }
//
//    public void setNameEn(String nameEn) {
//        this.nameEn = nameEn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZoneNum() {
        return zoneNum;
    }

    public void setZoneNum(String zoneNum) {
        this.zoneNum = zoneNum;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryNameCn() {
        return countryNameCn;
    }

    public void setCountryNameCn(String countryNameCn) {
        this.countryNameCn = countryNameCn;
    }

    public String getCountryNameEn() {
        return countryNameEn;
    }

    public void setCountryNameEn(String countryNameEn) {
        this.countryNameEn = countryNameEn;
    }
}
