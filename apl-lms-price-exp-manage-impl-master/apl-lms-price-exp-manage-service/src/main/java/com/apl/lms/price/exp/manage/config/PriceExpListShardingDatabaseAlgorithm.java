package com.apl.lms.price.exp.manage.config;

import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.shardingjdbc.algorithm.TenantShardingDatabaseAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author hjr start
 * @Classname PriceExpListShardingDatabaseAlgorithm
 * @Date 2020/9/17 23:21
 */
public class PriceExpListShardingDatabaseAlgorithm implements PreciseShardingAlgorithm {
    private static final Logger log = LoggerFactory.getLogger(TenantShardingDatabaseAlgorithm.class);

    public PriceExpListShardingDatabaseAlgorithm() {
    }

    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        String nameOne = null;
        Iterator var5 = collection.iterator();
        if (var5.hasNext()) {
            Object name = var5.next();
            nameOne = name.toString();
        }

        nameOne = nameOne.replace("{tenant}", securityUser.getInnerOrgCode());
        return nameOne;
    }


}