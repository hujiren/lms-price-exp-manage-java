package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
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
     * 分页获取销售价列表(分页)
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
    ResultUtil<Page<PriceExpCostListVo>> getPriceExpCostList(PageDto pageDto, PriceExpCostKeyDto keyDto);


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
    ResultUtil<PriceExpSaleInfoVo> getPriceExpSaleInfo(Long id) throws Exception;

    /**
     * 获取成本价格详情
     * @param id
     * @return
     */
    ResultUtil<PriceExpCostInfoVo> getPriceExpCostInfo(Long id) throws Exception;


    /**
     * 更新销售价格
     * @param priceExpSaleUpdDto
     * @return
     */
    ResultUtil<Boolean> updateSalePrice(PriceExpSaleUpdDto priceExpSaleUpdDto);

    /**
     * 更新成本价格表
     * @param priceExpCostUpdDto
     * @return
     */
    ResultUtil<Boolean> updateCostPrice(PriceExpCostUpdDto priceExpCostUpdDto);

    /**
     * 更新价格表数据
     * @param priceExpDataAddDto
     * @return
     */
    ResultUtil<Boolean> updatePriceData(PriceExpDataAddDto priceExpDataAddDto);


    /**
     * 新增快递价格
     * @param priceExpAddDto
     * @return
     */
    ResultUtil<Long> addExpPrice(PriceExpAddDto priceExpAddDto);



    /**
     * 根据id批量删除价格数据
     * @param ids
     * @return
     */
    ResultUtil<Boolean> deletePriceBatch(List<Long> ids, Integer priceType, Boolean delSaleAndCost);


    /**
     * 引用价格表
     * @param referencePriceDto
     * @return
     */
    ResultUtil<Long> referencePrice(ReferencePriceDto referencePriceDto);
}
