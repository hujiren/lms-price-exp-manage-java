package com.apl.lms.price.exp.manage.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;

/**
 * @author hjr start
 * @date 2020/8/6 - 11:15
 */
@SpringBootApplication(
        scanBasePackages = {"com.apl.lms.price.exp.manage",
                "com.apl.db.abatis",
                "com.apl.lib",
                "com.apl.cache"})
@MapperScan("com.apl.lms.price.exp.manage.mapper")
@EnableSwagger2
public class LmsPriceExpManageApplication {



    public static void main(String[] args) {

        //com.apl.db.datasource.DataSourceConfig
        //com.apl.db.adb.AdbContext
        //com.apl.lib.config.SwaggerConfig

        SpringApplication.run(LmsPriceExpManageApplication.class , args);
    }
}
