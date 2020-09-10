package com.apl.lms.price.exp.manage.util;

import com.apl.lms.common.lib.feign.LmsCommonFeign;
import com.apl.lms.common.lib.feign.impl.LmsCommonFeignImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author hjr start
 * @Classname LmsCommonFeignBean
 * @Date 2020/9/9 17:29
 */
@Component
public class LmsCommonFeignBean {
    @Primary
    @Bean
    public LmsCommonFeign getLmsCommonFeignBean(){
        LmsCommonFeignImpl lmsCommonFeign = new LmsCommonFeignImpl();
        return lmsCommonFeign;
    }
}
