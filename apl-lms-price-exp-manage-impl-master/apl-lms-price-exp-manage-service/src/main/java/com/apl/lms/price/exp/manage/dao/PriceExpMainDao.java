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
public class PriceExpMainDao {

    AdbHelper adbHelpeReal;

    public PriceExpMainDao(){
        // 创建真实数据源JDBC
        adbHelpeReal = new AdbHelper("explist");
    }

    public void createRealTable() {
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String sql = "CREATE TABLE  IF NOT EXISTS \"public\".\"" + securityUser.getInnerOrgCode() + "_price_exp_main\" (\n" +
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

    public AdbPageVo<PriceExpSaleListVo> getPriceExpSaleList(PriceExpSaleListKeyDto keyDto, Integer pageIndex, Integer pageSize) {
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        StringBuffer sql = new StringBuffer();
        sql.append("select main.id as priceMainId, main.start_date, main.end_date, main.currency, main.volume_divisor, main.account_type," +
                "main.special_commodity, sale.id, sale.price_code, sale.price_name, sale.customer_groups_name, sale.customer_name," +
                "sale.channel_category, sale.price_status\n" +
                " from  " + securityUser.getInnerOrgCode() +"_price_exp_sale as sale " +
                " left join " + securityUser.getInnerOrgCode() +"_price_exp_main as main " +
                " on main.id = sale.price_main_id\n");
        sql.append(" where sale.inner_org_id={tenantValue}");
        if (keyDto.getPriceType().equals(1))
            sql.append(" and sale.customer_groups_name is not null");

        if (keyDto.getPriceType().equals(2))
            sql.append(" and sale.customer_name is not null");

        if (keyDto.getVolumeDivisor() != null)
            sql.append(" and main.volume_divisor = :volumeDivisor");

        if (keyDto.getAccountType() != null)
            sql.append(" and main.account_type = :accountType");

        if(null!=keyDto.getPriceStatus()) {
            if (keyDto.getPriceStatus() > 0 && keyDto.getPriceStatus() < 4)
                sql.append(" and sale.price_status = :priceStatus");

            if (keyDto.getPriceStatus().equals(4))
                sql.append(" and main.end_date > now()");

            if (keyDto.getPriceStatus().equals(5))
                sql.append(" and main.end_date - now() > '0 day' and main.end_date - now() <= '7 day'");

            if (keyDto.getPriceStatus().equals(6))
                sql.append(" and now() > main.end_date");
        }

        if (null!=keyDto.getSpecialCommodity() && keyDto.getSpecialCommodity() > 0)
            sql.append(" and main.special_commodity @> '"+keyDto.getSpecialCommodity()+"'");

        if (null!=keyDto.getCustomerGroupsId() && keyDto.getCustomerGroupsId()>0)
            sql.append(" and sale.customer_groups_id @> '"+keyDto.getCustomerGroupsId()+"'");

        if (null!=keyDto.getCustomerId() && keyDto.getCustomerId()>0)
            sql.append(" and sale.customer_ids @> '"+keyDto.getCustomerId()+"'");

        if (keyDto.getChannelCategory() != null)
            sql.append(" and sale.channel_category = :channelCategory");

        if (keyDto.getKeyword() != null) {
            sql.append(" and (sale.customer_name LIKE CONCAT( '%' , :keyword , '%' )");
            sql.append(" or sale.price_name LIKE CONCAT( '%' , :keyword , '%' )");
            sql.append(" or sale.customer_groups_name LIKE CONCAT( '%' , :keyword , '%' ))");
        }

        AdbPageVo<PriceExpSaleListVo> adbPageVo = adbHelpeReal.queryPage(sql.toString(), keyDto, PriceExpSaleListVo.class, "id", pageIndex, pageSize);

        return adbPageVo;
    }

}
