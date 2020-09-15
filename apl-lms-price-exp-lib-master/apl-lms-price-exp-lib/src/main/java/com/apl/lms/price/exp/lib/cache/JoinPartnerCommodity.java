package com.apl.lms.price.exp.lib.cache;

import com.apl.lib.cachebase.BaseCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.join.JoinBase;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.lib.cache.bo.PartnerCacheBo;
import com.apl.lms.price.exp.lib.feign.PriceExpFeign;
import com.apl.tenant.AplTenantConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hjr start
 * @Classname JoinPartnerCommodity
 * @Date 2020/9/15 15:40
 */
public class JoinPartnerCommodity extends JoinBase<PartnerCacheBo> {

    @Autowired
    PriceExpFeign priceExpFeign;

    public JoinPartnerCommodity(int joinStyle, PriceExpFeign priceExpFeign, BaseCacheUtil cacheUtil){
        this.priceExpFeign = priceExpFeign;
        this.cacheUtil = cacheUtil;
        this.tabName = "partner";
        this.joinStyle = joinStyle;

        this.innerOrgId = AplTenantConfig.tenantIdContextHolder.get();
        this.cacheKeyNamePrefix = "JOIN_CACHE:"+this.tabName+"_"+this.innerOrgId.toString()+"_";
    }

    @Override
    public Boolean addCache(String keys, Long minKey, Long maxKey){

        ResultUtil<Boolean> result = priceExpFeign.addPartnerCache(keys, minKey, maxKey);
        if(result.getCode().equals(CommonStatusCode.SYSTEM_SUCCESS.code))
            return true;

        return false;
    }
}
