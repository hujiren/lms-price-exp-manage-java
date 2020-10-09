package com.apl.lms.price.exp.manage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lms.price.exp.pojo.po.PriceExpComputationalFormulaPo;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.vo.PriceSurchargeVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Develop2Dao {

    AdbHelper adbHelperReal;

    public Develop2Dao(){
        // 创建真实数据源JDBC
        adbHelperReal = new AdbHelper("develop2");
    }

//        adbHelpeReal.saveBatch() 公式/附加费批量更新用这个
    public Integer updSurcharge(List<PriceSurchargeVo> newList, List<PriceSurchargeVo> oldList) throws Exception {
        Integer result = adbHelperReal.saveBatch(newList, oldList, "price_surcharge", "id", true);
        return result;
    }

    public Integer updComputation(List<PriceExpComputationalFormulaPo> newList, List<PriceExpComputationalFormulaPo> oldList) throws Exception {
        Integer result = adbHelperReal.saveBatch(newList, oldList, "price_computational_formula", "id", true);
        return result;
    }

}
