package com.apl.lms.price.exp.manage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.db.adb.AdbPageVo;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lms.price.exp.pojo.dto.PriceExpSaleListKeyDto;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceExpDao {

    AdbHelper adbHelpeReal;

    public PriceExpDao(){
        // 创建真实数据源JDBC
        adbHelpeReal = new AdbHelper("explist");
    }

    public void createRealTable() {
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String sql = "CREATE TABLE  IF NOT EXISTS \"public\".\"" + securityUser.getInnerOrgCode() + "_exp_price\" (\n" +
                "  \"id\" int8 NOT NULL,\n" +
                "  \"start_date\" timestamp(6) NOT NULL,\n" +
                "  \"end_date\" timestamp(6) NOT NULL,\n" +
                "  \"currency\" varchar(5) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n" +
                "  \"zone_id\" int8 NOT NULL DEFAULT 0,\n" +
                "  \"account_type\" int2 NOT NULL,\n" +
                "  \"account_no\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL DEFAULT 0,\n" +
                "  \"source_table_status\" int4 DEFAULT 0,\n" +
                "  \"special_commodity\" jsonb,\n" +
                "  \"price_format\" int2 DEFAULT 0,\n" +
                "  \"start_weight\" float4 NOT NULL DEFAULT 0,\n" +
                "  \"end_weight\" float4 NOT NULL DEFAULT 0,\n" +
                "  \"price_published_id\" int8 NOT NULL DEFAULT 0,\n" +
                "  \"is_published_price\" int2 NOT NULL,\n" +
                "  \"aging\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"volume_divisor\" int4 NOT NULL,\n" +
                "  \"inner_org_id\" int8 NOT NULL\n" +
                ")";

        //创建租户price_exp_main物理表
        adbHelpeReal.execut(sql);
    }



}
