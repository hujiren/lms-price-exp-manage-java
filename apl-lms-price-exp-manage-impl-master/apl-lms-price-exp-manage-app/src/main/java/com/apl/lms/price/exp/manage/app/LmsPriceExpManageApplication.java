package com.apl.lms.price.exp.manage.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hjr start
 * @date 2020/8/6 - 11:15
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.apl.lib", //APL基本工具类
                "com.apl.tenant", //多租户
                //"com.apl.abatis", // sqlSession封装
                "com.apl.db.adb", // adb数据库操作助手
                //"com.apl.db.dynamicdb", //动态数据源
                "com.apl.cache", // redis代理
                "com.apl.lms.price.exp.manage",
                "com.apl.shardingjdbc", // 分库
                "com.apl.lms.price.exp.lib",
                "com.apl.lms.common.lib"},
        exclude = {
            JtaAutoConfiguration.class
        })
//@MapperScan("com.apl.lms.price.exp.manage.mapper")
@MapperScan(basePackages = "com.apl.lms.price.exp.manage.mapper", sqlSessionFactoryRef = "sqlSessionFactoryForShardingjdbc")
//@MapperScan(basePackages = "com.apl.lms.price.exp.manage.mapper2", sqlSessionFactoryRef = "sqlSessionFactoryForShardingjdbc2")
@EnableSwagger2
public class LmsPriceExpManageApplication {

    public static void main(String[] args) {
        //com.apl.shardingjdbc.mybatis.ShardingJdbcMybatisConfig
        //mybatis-plus.pagination.tenantTableFilter
        //AbstractDataSourceAdapter
        SpringApplication.run(LmsPriceExpManageApplication.class , args);
    }
}
