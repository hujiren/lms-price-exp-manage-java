package com.apl.lms.price.exp.manage.app;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
                "com.apl.shardingjdbc", // 分库
                "com.apl.lms.common.lib",
                "com.apl.lms.price.exp.manage",
                "com.apl.lms.price.exp.lib",
                "com.apl.sys.lib"
                },
        exclude = {
            DruidDataSourceAutoConfigure.class
        })
//@MapperScan("com.apl.lms.price.exp.manage.mapper")
@MapperScan(basePackages = "com.apl.lms.price.exp.manage.mapper", sqlSessionFactoryRef = "sqlSessionFactoryForShardingjdbc")
//@MapperScan(basePackages = "com.apl.lms.price.exp.manage.mapper2", sqlSessionFactoryRef = "sqlSessionFactoryForShardingjdbc2")
@EnableFeignClients(basePackages = {"com.apl.lms.common.lib.feign","com.apl.lms.price.exp.lib.feign","com.apl.sys.lib.feign"})
@EnableSwagger2
public class LmsPriceExpManageApplication {

    public static void main(String[] args) {
        //com.apl.shardingjdbc.ShardingDataSourceConfig
        //com.apl.shardingjdbc.mybatis.ShardingJdbcMybatisConfig
        //mybatis-plus.pagination.tenantTableFilter
        //AbstractDataSourceAdapter
        //com.apl.shardingjdbc.config.ShardingDataSourceConfig
        //com.apl.tenant.AplTenantSqlParser
        //com.apl.shardingjdbc.algorithm.TenantShardingTableAlgorithm

        SpringApplication.run(LmsPriceExpManageApplication.class , args);
    }
}
