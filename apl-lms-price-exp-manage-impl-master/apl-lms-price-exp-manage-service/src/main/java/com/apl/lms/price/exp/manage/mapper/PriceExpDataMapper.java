package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
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
public interface PriceExpDataMapper extends BaseMapper<PriceExpDataPo> {

    /**
     * 根据主表id获取详细数据
     * @param id
     * @return
     */
    PriceExpDataVo getPriceExpDataInfoByMainId(@Param("id") Long id);

    /**
     * 根据id查询d多租户id
     * @param id
     * @return
     */
    Long getInnerOrgId(@Param("id") Long id);

    /**
     * 新增
     * @param priceExpDataPo
     * @return
     */
    Integer insertData(@Param("po") PriceExpDataPo priceExpDataPo);

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    Integer updateByMainId(@Param("po") PriceExpDataPo priceExpDataPo);

    /**
     * 批量删除
     * @param priceExpMainIds
     * @return
     */
    Integer deleteByMainIds(@Param("ids") List<Long> priceExpMainIds);
}
