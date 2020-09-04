package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.PriceExpCostKeyDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpPublishedKeyDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpSaleListKeyDto;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostListVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleInfoVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleListVo;
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
    List<PriceExpSaleListVo> getPriceExpSaleList(Page<PriceExpSaleListVo> page, @Param("key") PriceExpSaleListKeyDto keyDto);

    /**
     * 分页查询成本价格列表
     * @param page
     * @param keyDto
     * @return
     */
    List<PriceExpCostListVo> getPriceExpCostList(Page<PriceExpCostListVo> page, @Param("key") PriceExpCostKeyDto keyDto);


    /**
     * 分页查询公布价列表
     * @param page
     * @param keyDto
     * @return
     */
    List<PriceExpCostListVo> getPublishedPriceList(Page<PriceExpCostListVo> page, @Param("key") PriceExpPublishedKeyDto keyDto);


    /**
     * 校验id是否存在
     * @param id
     * @return
     */
    Long getExpListById(@Param("id") Long id);

    /**
     * 新增价格表主表
     * @param priceExpMainPo
     * @return
     */
    Boolean insertPriceExpMain(@Param("po") PriceExpMainPo priceExpMainPo);

    /**
     * 获取主表详情
     * @param id
     * @return
     */
    PriceExpSaleInfoVo getPriceExpMainInfoById(@Param("id") Long id);

    /**
     * 根据主表id查询多租户id
     * @param priceMainIds
     * @return
     */
    List<Long> getInnerOrgId(@Param("ids") List<Long> priceMainIds);

    /**
     * 更新
     * @param priceExpMainPo
     * @return
     */
    Integer updateMainById(@Param("po") PriceExpMainPo priceExpMainPo);

    /**
     * 获取多租户id
     * @param priceMainId
     * @return
     */
    Long getInnerOrgById(@Param("id") Long priceMainId);

}
