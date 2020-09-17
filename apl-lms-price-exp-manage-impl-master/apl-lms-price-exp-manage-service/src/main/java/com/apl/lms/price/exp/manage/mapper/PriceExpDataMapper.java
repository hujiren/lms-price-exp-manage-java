package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.annotation.SqlParser;
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
     * 根据价格表id获取详细数据
     * @param priceId
     * @return
     */
    @SqlParser(filter = true)
    PriceExpDataVo getPriceExpDataInfoById(@Param("id") Long priceId);

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
    Integer updById(@Param("po") PriceExpDataPo priceExpDataPo);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(@Param("ids") String ids);

}
