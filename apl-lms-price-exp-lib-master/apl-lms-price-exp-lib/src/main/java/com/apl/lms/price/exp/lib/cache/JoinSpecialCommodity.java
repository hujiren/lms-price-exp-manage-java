package com.apl.lms.price.exp.lib.cache;

import com.apl.db.abatis.MyBatisPlusConfig;
import com.apl.lib.cachebase.BaseCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.join.JoinBase;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.lib.feign.PriceExpFeign;
import com.apl.lms.price.exp.lib.cache.bo.SpecialCommodityCacheBo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hjr start
 * @Classname JoinSpecialCommodity
 * @Date 2020/8/19 10:33
 */
public class JoinSpecialCommodity extends JoinBase<SpecialCommodityCacheBo> {

    @Autowired
    PriceExpFeign priceExpFeign;

    public JoinSpecialCommodity(int joinStyle, PriceExpFeign priceExpFeign, BaseCacheUtil cacheUtil){
        this.priceExpFeign = priceExpFeign;
        this.cacheUtil = cacheUtil;
        this.tabName = "price_special_commodity";
        this.joinStyle = joinStyle;

        this.innerOrgId = MyBatisPlusConfig.tenantIdContextHolder.get();
        this.cacheKeyNamePrefix = "JOIN_CACHE:"+this.tabName+"_"+this.innerOrgId.toString()+"_";
    }


    @Override
    public Boolean addCache(String keys, Long minKey, Long maxKey){

        ResultUtil<Boolean> result = priceExpFeign.addSpecialCommodityCache(keys, minKey, maxKey);
        if(result.getCode().equals(CommonStatusCode.SYSTEM_SUCCESS.code))
            return true;



        return false;
    }
}