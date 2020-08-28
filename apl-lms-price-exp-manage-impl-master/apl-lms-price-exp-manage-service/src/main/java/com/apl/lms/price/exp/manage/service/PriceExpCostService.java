package com.apl.lms.price.exp.manage.service;


import com.apl.lms.price.exp.pojo.dto.PriceExpCostAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpCostPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

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
     * 根据id得到主表id
     * @param priceExpMainId
     * @return
     */
    Long getPriceDataId(@Param("id") Long priceExpMainId);

    /**
     * 通过主表id获取统计条数
     * @param priceMainId
     * @return
     */
    Integer getPriceDataIdCount(Long priceMainId);

    /**
     * 保存成本价格表数据
     * @param priceMainId
     * @param priceExpCostAddDto
     * @return
     */
    Boolean addPriceExpCost(Long priceMainId, PriceExpCostAddDto priceExpCostAddDto, Long costId);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    Integer deleteById(Long id);
}
