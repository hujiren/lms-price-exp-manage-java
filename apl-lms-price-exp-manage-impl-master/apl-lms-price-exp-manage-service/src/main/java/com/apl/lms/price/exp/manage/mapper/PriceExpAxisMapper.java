package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author hjr start
 * @Classname PriceExpDataMapper
 * @Date 2020/8/19 17:32
 */
@Repository
public interface PriceExpAxisMapper extends BaseMapper<PriceExpAxisPo> {

    /**
     * 根据主表id删除数据
     * @param priceExpMainId
     * @return
     */
    Integer deleteByPriceExpMainId(@Param("id") Long priceExpMainId);

    /**
     * 根据主表id获取详细数据
     * @param id
     * @return
     */
    PriceExpAxisPo getPriceExpAxisInfoByMainId(@Param("id") Long id);

    /**
     * 新增
     * @param priceExpAxisPo
     * @return
     */
    Integer insertAxis(@Param("po") PriceExpAxisPo priceExpAxisPo);
}
