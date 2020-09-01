package com.apl.lms.price.exp.manage.service;
import com.apl.lms.price.exp.pojo.dto.PriceExpCostAddDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpSaleAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpSalePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

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
     * 根据ids得到主表ids
     * @param ids
     * @return
     */
    List<Long> getPriceDataIds(List<Long> ids);

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
     * 获取主表Id
     * @param id
     * @return
     */
    Long getMainId(Long id);
}
