package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.PriceExpComputationalFormulaPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Mapper
@Repository
public interface ComputationalFormulaMapper extends BaseMapper<PriceExpComputationalFormulaPo> {

    /**
     * 获取快递价格计算公式
     * @param priceId
     * @return
     */
    List<PriceExpComputationalFormulaPo> getList(@Param("priceId") Long priceId);


    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(@Param("ids") String ids);

    /**
     * 根据价格表id获取计算公式Ids
     * @param priceId
     * @return
     */
    List<Long> getIdBatchByPriceId(Long priceId);
}
