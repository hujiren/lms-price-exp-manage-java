package com.apl.lms.price.exp.lib.feign.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.lib.feign.PriceExpFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hjr start
 * @Classname PriceExpFeignImpl
 * @Date 2020/8/19 10:36
 */
@Component
public class PriceExpFeignImpl implements PriceExpFeign {

    @Override
    public ResultUtil<Boolean> addPartnerCache(@RequestParam("keys") String keys,
                                               @RequestParam("minKey") Long minKey,
                                               @RequestParam("maxKey") Long maxKey) {
        return ResultUtil.APPRESULT(CommonStatusCode.SERVER_INVOKE_FAIL.getCode() , CommonStatusCode.SERVER_INVOKE_FAIL.getMsg() , null);
    }
}
