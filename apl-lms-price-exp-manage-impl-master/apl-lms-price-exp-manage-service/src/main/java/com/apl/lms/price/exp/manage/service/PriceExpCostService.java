package com.apl.lms.price.exp.manage.service;


import com.apl.lms.price.exp.pojo.dto.PriceExpAddBaseDto;
import com.apl.lms.price.exp.pojo.entity.PriceListForDelBatch;
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
     * 保存成本价格表数据
     * @param priceMainId
     * @return
     */
    Boolean addPriceExpCost(PriceExpAddBaseDto priceExpAddDto, Long costPriceId, Long priceMainId,   Long quotePriceId);

    /**
     * 根据id删除
     * @param ids
     * @return
     */
    Integer deleteById(List<Long> ids);


    /**
     * 组装批量删除条件集合
     * @param priceIdList
     * @return
     */
    List<PriceListForDelBatch> getPriceListForDel(List<Long> priceIdList);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);

}
