package com.apl.lms.price.exp.manage.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hjr start
 * @date 2020/8/6 - 11:15
 */
@SpringBootApplication(
        scanBasePackages = {"com.apl.lms.price.exp.manage.service",
                "com.apl.db.abatis",
                "com.apl.db.adb",
                "com.apl.lib",
                "com.apl.cache",
                "com.apl.lms.price.exp.manage.app"})
@MapperScan("com.apl.lms.price.exp.manage.mapper")
@EnableSwagger2
public class LmsPriceExpManageApplication {

    public static void main(String[] args) {

        SpringApplication.run(LmsPriceExpManageApplication.class , args);
    }
}
