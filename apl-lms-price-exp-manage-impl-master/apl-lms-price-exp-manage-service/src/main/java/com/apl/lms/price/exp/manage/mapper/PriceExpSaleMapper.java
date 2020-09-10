package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.po.PriceExpSalePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;
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
     * 根据主表id获取数据统计数量
     * @param ids
     * @return
     */
    Integer getPriceDataIdCount(@Param("ids") List<Long> ids);

    /**
     * 根据id得到主表id
     * @param ids
     * @return
     */
    List<Long> getPriceDataIds(@Param("ids") List<Long> ids);

    /**
     * 更新
     * @param priceExpSalePo
     * @return
     */
    Integer updateSaleById(@Param("po") PriceExpSalePo priceExpSalePo);

    /**
     * 获取主表id
     * @param id
     * @return
     */
    Long getMainId(@Param("id") Long id);

    /**
     * 新增
     * @param priceExpSalePo
     * @return
     */
    Integer addPriceExpSale(@Param("po") PriceExpSalePo priceExpSalePo);
}
