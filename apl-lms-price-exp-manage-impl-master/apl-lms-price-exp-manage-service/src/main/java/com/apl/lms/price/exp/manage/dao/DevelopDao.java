package com.apl.lms.price.exp.manage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.lms.price.exp.pojo.po.PriceExpComputationalFormulaPo;
import com.apl.lms.price.exp.pojo.po.PriceSurchargePo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DevelopDao {

    AdbHelper adbHelperReal;

    public DevelopDao(){
        // 创建真实数据源JDBC
        adbHelperReal = new AdbHelper("develop");
    }

//        adbHelpeReal.saveBatch() 公式/附加费批量更新用这个
    public Integer updSurcharge(List<PriceSurchargePo> newList, List<Long> oldSurchargeIds) throws Exception {
        Integer result = adbHelperReal.saveBatch(newList, oldSurchargeIds, "price_surcharge", "id", false);

        return result;
    }

    public Integer updComputation(List<PriceExpComputationalFormulaPo> newList, List<Long> oldComputationIds) throws Exception {
        Integer result = adbHelperReal.saveBatch(newList, oldComputationIds, "price_computational_formula", "id", false);

        return result;
    }

}
