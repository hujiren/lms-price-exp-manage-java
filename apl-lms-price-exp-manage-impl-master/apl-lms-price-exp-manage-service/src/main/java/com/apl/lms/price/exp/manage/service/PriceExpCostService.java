package com.apl.lms.price.exp.manage.service;


import com.apl.lms.price.exp.pojo.dto.PriceExpCostAddDto;
import com.apl.lms.price.exp.pojo.entity.PriceListForDelBatch;
import com.apl.lms.price.exp.pojo.entity.RelevanceForMainData;
import com.apl.lms.price.exp.pojo.po.PriceExpCostPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataService
 * @Date 2020/8/19 17:30
 */
public interface PriceExpCostService extends IService<PriceExpCostPo> {


    /**
     * 根据主表id获取成本价格详情
     * @param id
     * @return
     */
    PriceExpCostVo getPriceExpCostInfo(Long id);

    /**
     * 根据ids得到主表ids和数据表ids
     * @param ids
     * @return
     */
    RelevanceForMainData getPriceDataIds(List<Long> ids);

    /**
     * 通过主表id获取统计条数
     * @param priceMainIds
     * @return
     */
    Integer getPriceDataIdCount(List<Long> priceMainIds);

    /**
     * 保存成本价格表数据
     * @param priceMainId
     * @param priceExpCostAddDto
     * @return
     */
    Boolean addPriceExpCost(Long priceMainId, PriceExpCostAddDto priceExpCostAddDto, Long costId);

    /**
     * 根据id删除
     * @param ids
     * @return
     */
    Integer deleteById(List<Long> ids);

    /**
     * 获取priceDataId
     * @param id
     * @return
     */
    Long getPriceDataId(Long id);

    /**
     * 添加引用成本价格
     * @param priceExpCostPo
     * @return
     */
    Long addReferenceCost(PriceExpCostPo priceExpCostPo);

    /**
     * 组装批量删除条件集合
     * @param priceIdList
     * @return
     */
    List<PriceListForDelBatch> getPriceListForDel(List<Long> priceIdList);
}
