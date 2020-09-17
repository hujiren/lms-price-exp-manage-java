package com.apl.lms.price.exp.manage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PriceZoneDao {

    AdbHelper adbHelpeReal;

    public void createRealTable(){
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String sql = "CREATE TABLE IF NOT EXISTS \"public\".\""+securityUser.getInnerOrgCode()+"_price_zone\" (" +
                "  \"id\" int8 NOT NULL PRIMARY KEY," +
                "  \"channel_category\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL," +
                "  \"zone_name\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL," +
                "  \"inner_org_id\" int8 NOT NULL" +
                ")";

        if(null==adbHelpeReal) {
            // 创建真实数据源JDBC
            adbHelpeReal = new AdbHelper("zone");
        }
        //创建租户price_zone物理表
        adbHelpeReal.execut(sql);
    }
}
