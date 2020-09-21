package com.apl.lms.price.exp.manage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceExpSaleDao {


    //真实数据源JDBC
    AdbHelper adbHelpeReal;

    public PriceExpSaleDao(){
        // 创建真实数据源JDBC
        adbHelpeReal = new AdbHelper("explist");
    }

    public void createRealTable(){
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String sql = "CREATE TABLE IF NOT EXISTS \"public\".\""+securityUser.getInnerOrgCode()+"_price_exp_sale\" (\n" +
                "  \"id\" int8 NOT NULL,\n" +
                "  \"price_code\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"price_name\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n" +
                "  \"customer_groups_id\" jsonb,\n" +
                "  \"customer_groups_name\" varchar(100) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"customer_ids\" jsonb,\n" +
                "  \"customer_name\" varchar(100) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"price_status\" int4 NOT NULL,\n" +
                "  \"quote_price_id\" int8 NOT NULL DEFAULT 0,\n" +
                "  \"quote_price_final_id\" int8 NOT NULL DEFAULT 0,\n" +
                "  \"channel_category\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"price_main_id\" int8 NOT NULL,\n" +
                "  \"inner_org_id\" int8\n" +
                ")";

        //创建租户price_exp_sale物理表
        adbHelpeReal.execut(sql);
    }






}
