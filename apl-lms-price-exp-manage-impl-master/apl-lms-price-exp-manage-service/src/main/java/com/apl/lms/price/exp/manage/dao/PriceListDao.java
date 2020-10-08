package com.apl.lms.price.exp.manage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.db.adb.AdbPageVo;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lms.price.exp.pojo.dto.PriceExpSaleListKeyDto;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceListDao {

    AdbHelper adbHelpeReal;

    public PriceListDao(){
        // 创建真实数据源JDBC
        adbHelpeReal = new AdbHelper("pricelist");
    }

    public void createRealTable() {
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String sql = "CREATE TABLE  IF NOT EXISTS \"public\".\"" + securityUser.getInnerOrgCode() + "_lms_exp_price\" (\n" +
                "  \"id\" int8 NOT NULL,\n" +
                "  \"start_date\" timestamp(6) NOT NULL,\n" +
                "  \"end_date\" timestamp(6) NOT NULL,\n" +
                "  \"currency\" varchar(5) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n" +
                "  \"account_type\" int2 NOT NULL,\n" +
                "  \"account_no\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL DEFAULT 0,\n" +
                "  \"source_table_status\" int2 DEFAULT 0,\n" +
                "  \"special_commodity\" jsonb,\n" +
                "  \"price_format\" int2 DEFAULT 0,\n" +
                "  \"start_weight\" float4 NOT NULL ,\n" +
                "  \"end_weight\" float4 NOT NULL ,\n" +
                "  \"price_published_id\" int8 NOT NULL DEFAULT 0,\n" +
                "  \"is_published_price\" int2 NOT NULL,\n" +
                "  \"aging\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"volume_divisor\" int4 NOT NULL,\n" +
                "  \"zone_id\" int8 NOT NULL,\n" +
                "  \"price_data_id\" int8 NOT NULL DEFAULT 0,\n"+
                "  \"price_code\" varchar(50) COLLATE \"pg_catalog\".\"default\",\n" +
                "  \"price_sale_name\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n" +
                "  \"channel_category\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n"+
                "  \"price_status\" int2 NOT NULL,\n"+
                "  \"quote_price_id\" int8 NOT NULL DEFAULT 0,\n"+
                "  \"partner_id\" int8 NOT NULL DEFAULT 0,\n"+
                "  \"customer_groups_id\" jsonb,\n"+
                "  \"customer_groups_name\" varchar(100) COLLATE \"pg_catalog\".\"default\",\n"+
                "  \"customer_ids\" jsonb,\n"+
                "  \"customer_name\" varchar(255) COLLATE \"pg_catalog\".\"default\",\n"+
                "  \"inner_org_id\" int8 NOT NULL DEFAULT 0,\n"+
                "  \"price_name\" varchar(50) COLLATE \"pg_catalog\".\"default\" NOT NULL,\n"+
                "  \"partner_name\" varchar(50) COLLATE \"pg_catalog\".\"default\" "+
                ")";

        //创建租户price_exp_main物理表
        adbHelpeReal.execut(sql);

//        adbHelpeReal.saveBatch() 公式/附加费批量更新用这个
    }
    public PriceExpMainPo getRealUpdTime(Long id, String orgCode){
        String sql = "select inner_org_id, upd_time from" + orgCode + "_lms_exp_price where id = #{id}";
//        adbHelpeReal.execut(sql);
        PriceExpMainPo priceExpMainPo = adbHelpeReal.queryObj(sql, id, PriceExpMainPo.class);
        return priceExpMainPo;
    }

    public PriceExpMainPo getRealPriceInfo(Long id, String orgCode){
        String sql = "select\n" +
                "            id,\n" +
                "            price_code,\n" +
                "            price_sale_name,\n" +
                "            price_name,\n" +
                "            partner_id,\n" +
                "            partner_name,\n" +
                "            price_status,\n" +
                "            channel_category,\n" +
                "            start_date,\n" +
                "            end_date,\n" +
                "            currency,\n" +
                "            zone_id,\n" +
                "            volume_divisor,\n" +
                "            account_type,\n" +
                "            account_no,\n" +
                "            special_commodity,\n" +
                "            is_published_price,\n" +
                "            price_format,\n" +
                "            start_weight,\n" +
                "            end_weight,\n" +
                "            aging,\n" +
                "            price_published_id,\n" +
                "            price_data_id,\n" +
                "            upd_time\n"+
                "        from\n" + orgCode +
                "            _lms_exp_price\n" +
                "        where\n" +
                "            id = #{id}";
        PriceExpMainPo priceExpMainPo = adbHelpeReal.queryObj(sql, id, PriceExpMainPo.class);
        return priceExpMainPo;
    }



}
