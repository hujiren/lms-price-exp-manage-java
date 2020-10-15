package com.apl.lms.price.exp.manage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hjr start
 * @Classname ExportPriceServiceImpl
 * @Date 2020/10/15 11:08
 */
public class ExportServiceImpl {

    @Autowired
    static PriceExpService priceExpService;

    public static void main(String[] args) throws Exception {

        // 模板存储位置
        String templateFileName = "G:/temp/template2.xlsx";

//        PriceExpDataVo priceExpDataInfo = priceExpService.getPriceExpDataInfoByPriceId(476292131913392128L);
//        List<List<String>> priceData = priceExpDataInfo.getPriceData();

        // 表头
        List<List<String>> headList = new ArrayList<>();
        List<String> head0 = Arrays.asList("按揭分类");
        List<String> head1 = Arrays.asList("欠款分类");
        List<String> head3 = Arrays.asList("欠款金额小计");
        headList.add(head0);
        headList.add(head1);
        headList.add(head3);

        // 内容
        List<List<Object>> list = new ArrayList<>();
        List<Object> list1 = Arrays.asList("{a}","{b}","{c}");
        list.add(list1);

        // 这里有个参数inMemory(true),是为了解决生成模板后，填充时模板中的字段未替换，填充数据的时候不要使用该参数，可能会造成OOM
        EasyExcel.write(templateFileName).head(headList).sheet("test").doWrite(list);

        //要填充的模板路径
        String fileName = "G:/temp/test.xlsx";
        List<String> excData = new ArrayList<>();
        excData.add("单元格1");
        excData.add("单元格2");
        excData.add("单元格3");
//.withTemplate(templateFileName)
        ExcelWriter excelWriter = EasyExcel.write(fileName, List.class).withTemplate(templateFileName).inMemory(Boolean.TRUE).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.write(excData, writeSheet);
        excelWriter.finish();

//        EasyExcel.write(fileName, List.class).sheet("模板").doWrite(excData);

    }



//        List<TestBo> listWrite = new ArrayList<>();
//        TestBo row = new TestBo();
//        row.setA(1);
//        row.setB(2);
//        row.setC(3);
//        listWrite.add(row);
//
//        row = new TestBo();
//        row.setA(11);
//        row.setB(22);
//        row.setC(33);
//        listWrite.add(row);

    static class TestBo{
        Integer a;

        Integer b;

        Integer c;

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public Integer getB() {
            return b;
        }

        public void setB(Integer b) {
            this.b = b;
        }

        public Integer getC() {
            return c;
        }

        public void setC(Integer c) {
            this.c = c;
        }
    }


}
