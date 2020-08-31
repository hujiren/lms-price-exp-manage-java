package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataMapper
 * @Date 2020/8/19 17:32
 */
@Repository
public interface PriceExpAxisMapper extends BaseMapper<PriceExpAxisPo> {

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

    /**
     * 更新
     * @param priceExpAxisPo
     * @return
     */
    Integer updateByMainId(@Param("po") PriceExpAxisPo priceExpAxisPo);

    /**
     * 根据主表id批量删除
     * @param priceExpMainIds
     * @return
     */
    Integer deleteByMainIds(@Param("ids") List<Long> priceExpMainIds);

    /**
     * 获取详细
     * @param id
     * @return
     */
    PriceExpAxisPo getAxisInfoById(@Param("id") Long id);
}
