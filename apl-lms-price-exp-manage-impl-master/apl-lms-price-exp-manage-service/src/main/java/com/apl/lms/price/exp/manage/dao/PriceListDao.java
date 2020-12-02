package com.apl.lms.price.exp.manage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import org.springframework.stereotype.Component;

@Component
public class PriceListDao {

    AdbHelper adbHelperReal;

    public PriceListDao(){
        // 创建真实数据源JDBC
        adbHelperReal = new AdbHelper("pricelist");
    }

    public void createRealTable() {
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String sql = "CREATE TABLE  IF NOT EXISTS" + " " + securityUser.getInnerOrgCode() + "_lms_exp_price (" +
                "  id bigint(20) NOT NULL,\n" +
                "  price_cod varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '价格表代码',\n" +
                "  price_name varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务商名称',\n"+
                "  price_sale_name varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '销售名称',\n" +
                "  partner_id bigint(20) NOT NULL DEFAULT 0 COMMENT '服务商id',\n"+
                "  partner_name varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务商名称',\n"+
                "  customer_group_ids varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户组id',\n"+
                "  customer_group_names varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户组名称',\n"+
                "  customer_ids varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户id',\n"+
                "  customer_names varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',\n"+
                "  special_commodity varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊物品',\n" +
                "  channel_category varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '渠道类型',\n"+
                "  currency varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币制',\n" +
                "  price_data_id bigint(20) NOT NULL DEFAULT 0 COMMENT '价格表数据id',\n"+
                "  quote_price_id bigint(20) NOT NULL DEFAULT 0 COMMENT '引用价格表id',\n"+
                "  zone_id bigint(20) NOT NULL DEFAULT 0 COMMENT '分区id',\n" +
                "  price_published_id bigint(20) NOT NULL DEFAULT 0 COMMENT '关联公布价id',\n" +
                "  is_published_price smallint(6) NOT NULL COMMENT '是否是公布价 1是 2不是',\n" +
                "  price_status smallint(6) NOT NULL COMMENT '价格表状态 1正常 2计账 3无效',\n"+
                "  price_format smallint(6) NULL DEFAULT NULL COMMENT '价格表格式 1横向 2纵向',\n" +
                "  start_date datetime(0) NOT NULL COMMENT '起始日期',\n" +
                "  end_date datetime(0) NOT NULL COMMENT '截止日期',\n" +
                "  account_type smallint(6) NOT NULL COMMENT '账户类型 1代理 2贸易 3第三方',\n" +
                "  account_no varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '快递账号',\n" +
                "  start_weight float NOT NULL COMMENT '起始重',\n" +
                "  end_weight float NOT NULL COMMENT '截止重',\n" +
                "  aging varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时效',\n" +
                "  volume_divisor int(11) NOT NULL COMMENT '体积重基数',\n" +
                "  upd_time datetime(0) NULL DEFAULT NULL COMMENT '更新时间',\n"+
                "  quote_price_upd_time datetime(0) NULL DEFAULT NULL COMMENT '引用价格更新时间',\n"+
                "  quote_tenant_code varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '引用价格租户code',\n"+
                "  is_quote smallint(6) NOT NULL COMMENT '是否引用 1 是 2否',\n"+
                "  syn_status smallint(6) NULL DEFAULT NULL COMMENT '同步状态 0未同步 1同步成功 2同步异常 3引用价格表已被删除',\n"+
                "  source_table_status smallint(6) NULL DEFAULT NULL COMMENT '源表状态',\n" +
                "  add_profit_way smallint(6) NOT NULL COMMENT '添加利润方式 0不加 1单独加 2统一加',\n" +
                "  inner_org_id bigint(20) NOT NULL COMMENT '多租户id'\n"+
                ")";

        //创建租户price_exp_main物理表
        adbHelperReal.execut(sql);

    }


    public Integer getPriceProfitWay(Long id, String tenantCode){
        adbHelperReal.setEnableTenant(false);
        String sql = "select add_profit_way from " + tenantCode + "_lms_exp_price where id = " + id;
        Integer addProfitWay = adbHelperReal.queryBaseVal(sql, null, Integer.class);

        return addProfitWay;
    }

    public PriceExpMainPo getQuotePriceUpdateTime(Long id, String tenantCode){
        String sql = "select inner_org_id, upd_time from" + " " + tenantCode + "_lms_exp_price where id = " + id;
        PriceExpMainPo priceExpMainPo = adbHelperReal.queryObj(sql, id, PriceExpMainPo.class);
        return priceExpMainPo;
    }

    public PriceExpMainPo getSourcePriceInfo(Long id, String tenantCode) {
        String sql = "select special_commodity, currency, price_data_id, zone_id, price_published_id, price_status, price_format," +
                "start_date, end_date, account_type, account_no, start_weight, end_weight, aging, volume_divisor,add_profit_way, upd_time" + " " +
                "from" + " " + tenantCode + "_lms_exp_price where id = " + id;
        PriceExpMainPo priceExpMainPo = adbHelperReal.queryObj(sql, id, PriceExpMainPo.class);
        return priceExpMainPo;
    }

    public PriceExpMainPo getRealPriceInfo(Long id, String orgCode){
        String sql = "select\n" +
                "            id,\n" +
                "            start_date,\n" +
                "            end_date,\n" +
                "            currency,\n" +
                "            account_type,\n" +
                "            account_no,\n" +
                "            special_commodity,\n" +
                "            price_format,\n" +
                "            start_weight,\n" +
                "            end_weight,\n" +
                "            price_published_id,\n" +
                "            is_published_price,\n" +
                "            aging,\n" +
                "            volume_divisor,\n" +
                "            zone_id,\n" +
                "            price_data_id,\n" +
                "            price_code,\n" +
                "            price_sale_name,\n" +
                "            channel_category,\n" +
                "            price_status,\n" +
                "            partner_id,\n" +
                "            customer_group_ids,\n"+
                "            customer_group_names,\n"+
                "            customer_ids,\n"+
                "            customer_names,\n"+
                "            price_name,\n" +
                "            partner_name,\n" +
                "            upd_time\n"+
                "            from\n" +
                            orgCode + "_lms_exp_price\n" +
                "        where\n" +
                "            id = " + id;
        adbHelperReal.setTenantValue(null);
        PriceExpMainPo priceExpMainPo = adbHelperReal.queryObj(sql, id, PriceExpMainPo.class);

        return priceExpMainPo;
    }

}
