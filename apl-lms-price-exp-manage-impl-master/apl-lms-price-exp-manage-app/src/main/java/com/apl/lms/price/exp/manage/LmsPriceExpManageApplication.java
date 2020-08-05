package com.apl.lms.price.exp.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hjr start
 * @date 2020/8/5 - 9:45
 */
@SpringBootApplication(
        scanBasePackages = {"com.apl.lms.price.exp.manage.*" ,
                "com.apl.db.abatis",
                "com.apl.db.adb",
                "com.apl.lib",
                "com.apl.cache",
                "com.apl.amqp"}
)

@MapperScan({"com.apl.lms.price.exp.manage.dao"})
@EnableDiscoveryClient
@EnableSwagger2
public class LmsPriceExpManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsPriceExpManageApplication.class, args);
    }
}
