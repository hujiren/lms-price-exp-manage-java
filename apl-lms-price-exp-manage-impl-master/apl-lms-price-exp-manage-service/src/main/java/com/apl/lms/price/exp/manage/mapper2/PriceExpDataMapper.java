package com.apl.lms.price.exp.manage.mapper2;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataStringVo;
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
     * @param id
     * @return
     */
    @SqlParser(filter = true)
    PriceExpDataVo getPriceExpDataInfoById(@Param("id") Long id);


    //检测价格表数据是否存在-
    Long exists(@Param("id") Long id);

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

    /**
     * 获取数据
     * @param priceDataId
     * @return
     */
    PriceExpDataStringVo getHeadCells(Long priceDataId);

    /**
     * 更新表头
     * @param priceDataId
     * @param allHeadCell
     * @return
     */
    Integer updateData(@Param("priceDataId") Long priceDataId, @Param("allHeadCell") List<List<String>> allHeadCell);

}
