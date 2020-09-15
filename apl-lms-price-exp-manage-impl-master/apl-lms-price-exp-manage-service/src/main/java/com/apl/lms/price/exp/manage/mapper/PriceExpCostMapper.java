package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.entity.PriceListForDelBatch;
import com.apl.lms.price.exp.pojo.entity.RelevanceForMainData;
import com.apl.lms.price.exp.pojo.po.PriceExpCostPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpCostMapper
 * @Date 2020/8/21 10:50
 */
@Repository
public interface PriceExpCostMapper extends BaseMapper<PriceExpCostPo> {

    /**
     * 根据主表Id获取详情
     * @param id
     * @return
     */
    PriceExpCostVo getPriceExpCostInfo(@Param("id") Long id);


    /**
     * 通过主表ids获取统计条数
     * @param priceMainIds
     * @return
     */
    Integer getPriceDataIdCount(@Param("ids") List<Long> priceMainIds);


    /**
     * 批量获取主表Id
     * @param ids
     * @return
     */
    RelevanceForMainData getPriceDataIds(@Param("ids") List<Long> ids);

    /**
     * 获取主表id
     * @param id
     * @return
     */
    Long getPriceDataId(@Param("id") Long id);

    /**
     * 组装批量删除条件集合
     * @param ids
     * @return
     */
    List<PriceListForDelBatch> getPriceListForDel(@Param("ids") List<Long> ids);
}
