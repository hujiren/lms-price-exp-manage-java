package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface PriceExpService extends IService<PriceExpMainPo> {

    /**
     * 分页获取销售价列表
     * @param pageDto
     * @param keyDto
     * @return
     */
    ResultUtil<Page<PriceExpSaleListVo>> getPriceExpSaleList(PageDto pageDto, PriceExpSaleListKeyDto keyDto);

    /**
     * 分页获取成本价列表(分页)
     * @param pageDto
     * @param keyDto
     * @return
     */
    ResultUtil<Page<PriceExpCostListVo>> getPriceExpCostList(PageDto pageDto, PriceExpCostKeyDto keyDto) throws Exception;

    /**
     * 分页公布价列表(分页)
     * @param pageDto
     * @param keyDto
     * @return
     */
    ResultUtil<Page<PriceExpCostListVo>> getPublishedPriceList(PageDto pageDto, PriceExpPublishedKeyDto keyDto);

    /**
     * 获取销售价格详情
     * @param id
     * @return
     */
    ResultUtil<PriceExpPriceInfoVo> getPriceExpInfo(Long id) throws Exception;

    /**
     * 更新价格主表
     * @param priceExpUpdDto
     * @return
     */
    ResultUtil<Boolean> updExpPrice(PriceExpUpdDto priceExpUpdDto);

    /**
     * 更新价格表数据
     * @param priceExpDataUpdDto
     * @return
     */
    ResultUtil<Boolean> updatePriceData(PriceExpDataUpdDto priceExpDataUpdDto);

    /**
     * 新增快递价格
     * @param priceExpAddDto
     * @return
     */
    ResultUtil<Long> addExpPrice(PriceExpAddDto priceExpAddDto);

    /**
     * 同步价格表
     * @param priceIds
     * @return
     */
    ResultUtil<Boolean> syncPrice(List<Long> priceIds);

    /**
     * 根据id批量删除价格数据
     * @param ids
     * @return
     */
    ResultUtil<Boolean> deletePriceBatch(List<Long> ids);


    /**
     * 引用价格表
     * @param referencePriceDto
     * @return
     */
    ResultUtil<Long> referencePrice(ReferencePriceDto referencePriceDto);

    /**
     * 获取价格表数据和轴数据
     * @param id
     * @return
     */
    ResultUtil<PriceExpDataAxisVo> getPriceExpDataAxis(Long id);

    /**
     * 获取价格表数据
     * @param id
     * @return
     */
    ResultUtil<PriceExpDataVo> getPriceExpDataInfoByPriceId(Long id) throws Exception;

    /**
     * 获取quotePriceId, innerOrgId, priceDataId组合方法
     * @param id
     * @return
     */
    ExpPriceInfoBo getInnerOrgIdAndPriceDatId(Long id);

    /**
     * 更新轴数据
     * @param weightSectionUpdDto
     * @return
     */
    List<String> updTransverseWeightSection(WeightSectionUpdDto weightSectionUpdDto);

}
