package com.apl.lms.price.exp.lib.cache;

import com.apl.lib.cachebase.BaseCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.join.JoinBase;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.lib.cache.bo.SurchargeCacheBo;
import com.apl.lms.price.exp.lib.feign.PriceExpFeign;
import com.apl.tenant.AplTenantConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hjr start
 * @Classname JoinSurcharge
 * @Date 2020/8/19 10:44
 */
public class JoinSurcharge extends JoinBase<SurchargeCacheBo> {

    @Autowired
    PriceExpFeign priceExpFeign;

    public JoinSurcharge(int joinStyle, PriceExpFeign priceExpFeign, BaseCacheUtil cacheUtil){
        this.priceExpFeign = priceExpFeign;
        this.cacheUtil = cacheUtil;
        this.tabName = "price_surcharge";
        this.joinStyle = joinStyle;

        this.innerOrgId = AplTenantConfig.tenantIdContextHolder.get();
        this.cacheKeyNamePrefix = "JOIN_CACHE:"+this.tabName+"_"+this.innerOrgId.toString()+"_";
    }


    @Override
    public Boolean addCache(String keys, Long minKey, Long maxKey){

        ResultUtil<Boolean> result = priceExpFeign.addSurchargeCache(keys, minKey, maxKey);
        if(result.getCode().equals(CommonStatusCode.SYSTEM_SUCCESS.code))
            return true;



        return false;
    }
}
