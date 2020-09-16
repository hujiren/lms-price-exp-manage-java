package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpAxisVo;
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
public interface PriceExpAxisMapper extends BaseMapper<PriceExpAxisPo> {

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
     * 根据id批量删除
     * @param priceExpMainIds
     * @return
     */
    Integer deleteByIds(@Param("ids") List<Long> priceExpMainIds);

    /**
     * 获取详细
     * @param id
     * @return
     */
    @SqlParser(filter = true)
    PriceExpAxisVo getAxisInfoById(@Param("id") Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(@Param("ids") String ids);
}
