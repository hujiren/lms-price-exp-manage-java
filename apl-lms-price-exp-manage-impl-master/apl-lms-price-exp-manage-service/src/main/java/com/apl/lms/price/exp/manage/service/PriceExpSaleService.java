package com.apl.lms.price.exp.manage.service;
import com.apl.lms.price.exp.pojo.dto.PriceExpCostAddDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpSaleAddDto;
import com.apl.lms.price.exp.pojo.entity.RelevanceForMainData;
import com.apl.lms.price.exp.pojo.entity.PriceListForDelBatch;
import com.apl.lms.price.exp.pojo.po.PriceExpSalePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpSaleService
 * @Date 2020/8/19 17:30
 */
public interface PriceExpSaleService extends IService<PriceExpSalePo> {

    /**
     * 获取销售价格表的详细信息
     * @param id
     * @return
     */
    PriceExpSaleVo getPriceExpSaleInfoById(Long id);

    /**
     * 根据主表id获取数据统计数量
     * @param ids
     * @return
     */
    Integer getPriceDataIdCount(List<Long> ids);

    /**
     * 根据ids得到主表id和price_data_id
     * @param ids
     * @return
     */
    RelevanceForMainData getPriceDataIds(List<Long> ids);

    /**
     * 根据id删除数据
     * @param ids
     * @return
     */
    Integer deleteById(List<Long> ids);

    /**
     * 保存销售价
     * @param priceExpCostAddDto
     * @param priceExpSaleAddDto
     * @param priceMainId
     * @param saleId
     * @return
     */
    Boolean addPriceExpSale(PriceExpCostAddDto priceExpCostAddDto, PriceExpSaleAddDto priceExpSaleAddDto, Long priceMainId, Long saleId);

    /**
     * 更新
     * @param priceExpSalePo
     * @return
     */
    Boolean updateSaleById(PriceExpSalePo priceExpSalePo);

    /**
     * 获取price_data_id
     * @param id
     * @return
     */
    Long getPriceDataId(Long id);

    /**
     * 添加引用销售价格
     * @param priceExpSalePo
     * @return
     */
    Long addReferenceSale(PriceExpSalePo priceExpSalePo);

    /**
     * 构建批量删除销售价格表条件集合
     * @param ids
     * @return
     */
    List<PriceListForDelBatch> getPriceListForDel(List<Long> ids);
}
