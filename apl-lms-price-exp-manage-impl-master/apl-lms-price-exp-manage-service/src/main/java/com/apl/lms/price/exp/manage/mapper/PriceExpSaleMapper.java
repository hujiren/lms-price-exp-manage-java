package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.bo.PriceListForDelBatchBo;
import com.apl.lms.price.exp.pojo.po.PriceExpSalePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpSaleMapper
 * @Date 2020/8/21 11:26
 */
@Repository
public interface PriceExpSaleMapper extends BaseMapper<PriceExpSalePo> {


    /**
     * 获取销售价格表详细信息
     * @param id
     * @return
     */
    PriceExpSaleVo getPriceExpSaleInfoById(@Param("id") Long id);

    /**
     * 更新
     * @param priceExpSalePo
     * @return
     */
    Integer updateSaleById(@Param("po") PriceExpSalePo priceExpSalePo);


    /**
     * 新增
     * @param priceExpSalePo
     * @return
     */
    Integer addPriceExpSale(@Param("po") PriceExpSalePo priceExpSalePo);

    /**
     * 构建批量删除销售价格表条件集合
     * @param ids
     * @return
     */
    List<PriceListForDelBatchBo> getPriceListForDel(@Param("ids") List<Long> ids);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(@Param("ids") String ids);

}
