package com.apl.lms.price.exp.manage.service;
import com.apl.lms.price.exp.pojo.po.PriceExpSalePo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * @author hjr start
 * @Classname PriceExpSaleService
 * @Date 2020/8/19 17:30
 */
public interface PriceExpSaleService extends IService<PriceExpSalePo> {

    /**
     * 根据主表id更新数据
     * @param priceExpSalePo
     * @return
     */
    Boolean updateByPriceExpMainId(PriceExpSalePo priceExpSalePo);

    /**
     * 获取销售价格表的详细信息
     * @param id
     * @return
     */
    PriceExpSaleVo getPriceExpSaleInfoByMainId(Long id);

    /**
     * 根据主表id获取数据统计数量
     * @param priceExpMainId
     * @return
     */
    Integer getPriceDataIdCount(Long priceExpMainId);

    /**
     * 根据id得到主表id
     * @param id
     * @return
     */
    Long getPriceDataId(Long id);

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    Integer deleteById(Long id);
}
