package com.apl.lms.price.exp.manage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lms.price.exp.pojo.bo.PriceListForDelBatchBo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceExpCostDao {

    AdbHelper adbHelpeReal;

    public PriceExpCostDao(){
        // 创建真实数据源JDBC
        adbHelpeReal = new AdbHelper("explist");
    }

    public void createRealTable(){
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String sql = "CREATE TABLE IF NOT EXISTS \"public\".\""+securityUser.getInnerOrgCode()+"_price_exp_cost\" (\n" +
                "  \"id\" int8 NOT NULL,\n" +
                "  \"partner_id\" int8 NOT NULL DEFAULT 0,\n" +
                "  \"quote_price_id\" int8 NOT NULL DEFAULT 0,\n" +
                "  \"quote_price_final_id\" int8 NOT NULL DEFAULT 0,\n" +
                "  \"price_status\" int2 NOT NULL,\n" +
                "  \"price_code\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"price_name\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n" +
                "  \"channel_category\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n" +
                "  \"price_main_id\" int8 NOT NULL DEFAULT 0,\n" +
                "  \"inner_org_id\" int8 NOT NULL\n" +
                ")";

        //创建租户price_exp_cost物理表
        adbHelpeReal.execut(sql);
    }


}
