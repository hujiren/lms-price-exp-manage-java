package com.apl.lms.price.exp.manage.mapper2;

import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.PriceExpCostKeyDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpPublishedKeyDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpSaleListKeyDto;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Mapper
@Repository
public interface PriceExpMapper extends BaseMapper<PriceExpMainPo> {

    /**
     * 分页查询销售价格列表
     * @param page
     * @param keyDto
     * @return
     */
    @SqlParser(filter = true)
    List<PriceExpSaleListVo> getPriceExpSaleList(Page<PriceExpSaleListVo> page, @Param("key") PriceExpSaleListKeyDto keyDto);

    /**
     * 分页查询成本价格列表
     * @param page
     * @param keyDto
     * @return
     */
    @SqlParser(filter = true)
    List<PriceExpCostListVo> getPriceExpCostList(Page<PriceExpCostListVo> page, @Param("key") PriceExpCostKeyDto keyDto);


    /**
     * 分页查询公布价列表
     * @param page
     * @param keyDto
     * @return
     */
    List<PriceExpCostListVo> getPublishedPriceList(Page<PriceExpCostListVo> page, @Param("key") PriceExpPublishedKeyDto keyDto);

    /**
     * 获取销售价格详情
     * @param id
     * @return
     */
    PriceExpPriceInfoVo getPriceExpSaleInfoById(@Param("id") Long id);

    /**
     * 获取客户信息并组装
     * @param id
     * @return
     */
    PriceExpSaleVo getCustomerInfo(@Param("id") Long id);

    /**
     * 更新
     * @param priceExpMainPo
     * @return
     */
    Integer updById(@Param("po") PriceExpMainPo priceExpMainPo);

    /**
     * 获取多租户id和价格表数据Id
     * @param priceId
     * @return
     */
    ExpPriceInfoBo getInnerOrgIdAndPriceDatId(@Param("id") Long priceId);

    /**
     * 新增
     * @param priceExpMainPo
     * @return
     */
    Integer addExpPrice(@Param("po") PriceExpMainPo priceExpMainPo);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatchs(@Param("ids") String ids);

    /**
     * 获取价格表数据Ids
     * @param ids
     * @return
     */
    List<Long> getPriceDataIds(@Param("ids") String ids);

    /**
     * 更新数据表数据
     * @param priceExpMainPo
     * @return
     */
    Integer updData(@Param("po") PriceExpMainPo priceExpMainPo);

    /**
     * 通过价格表数据id获取主表id
     * @param priceDataId
     * @return
     */
    Long getMainIdByPriceDataId(Long priceDataId);

    /**
     * 更新
     * @param priceExpMainPo
     * @return
     */
    Integer upd(@Param("po") PriceExpMainPo priceExpMainPo);
}
