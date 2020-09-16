package com.apl.lms.price.exp.manage.service;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddBaseDto;
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
     * 根据id删除数据
     * @param ids
     * @return
     */
    Integer deleteById(List<Long> ids);

    /**
     * 保存销售价
     * @param priceMainId
     * @param salePriceId
     * @return
     */
    Boolean addPriceExpSale(PriceExpAddBaseDto priceExpAddDto, Long salePriceId, Long priceMainId, Long quotePriceId);

    /**
     * 更新
     * @param priceExpSalePo
     * @return
     */
    Boolean updateSaleById(PriceExpSalePo priceExpSalePo);


    /**
     * 构建批量删除销售价格表条件集合
     * @param ids
     * @return
     */
    List<PriceListForDelBatch> getPriceListForDel(List<Long> ids);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);

}
