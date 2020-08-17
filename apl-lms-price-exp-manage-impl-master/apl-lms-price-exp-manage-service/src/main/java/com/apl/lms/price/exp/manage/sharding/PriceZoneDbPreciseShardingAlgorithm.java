package com.apl.lms.price.exp.manage.sharding;


import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component
public class PriceZoneDbPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {


    /**
     *
     * @param collection  库名集合
     * @param preciseShardingValue 分片列
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {

        // 分片字段值
        Long value = preciseShardingValue.getValue();


        Long n = value % 2 + 1;

        return "pricezone"+n;
        //throw new UnsupportedOperationException();
    }

}
