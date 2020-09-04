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
    ResultUtil<PriceExpSaleInfoVo> getPriceExpSaleInfo(Long id);

    /**
     * 获取成本价格详情
     * @param id
     * @return
     */
    ResultUtil<PriceExpCostInfoVo> getPriceExpCostInfo(Long id);


    /**
     * 更新销售价格
     * @param priceExpMainUpdDto
     * @param priceExpSaleUpdateDto
     * @return
     */
    ResultUtil<Boolean> updateSalePrice(PriceExpMainUpdDto priceExpMainUpdDto,
                                        PriceExpSaleUpdateDto priceExpSaleUpdateDto);

    /**
     * 更新成本价格表
     * @param priceExpMainUpdDto
     * @param priceExpCostUpdDto
     * @return
     */
    ResultUtil<Boolean> updateCostPrice(PriceExpMainUpdDto priceExpMainUpdDto,
                                        PriceExpCostUpdDto priceExpCostUpdDto);

    /**
     * 更新价格表数据
     * @param priceExpDataAddDto
     * @param priceExpAxisPo
     * @return
     */
    ResultUtil<Boolean> updatePriceData(PriceExpDataAddDto priceExpDataAddDto, PriceExpAxisPo priceExpAxisPo);


    /**
     * 新增快递价格
     * @param priceExpMainAddDto
     * @param priceExpSaleAddDto
     * @param priceExpCostAddDto
     * @param priceExpAxisAddDto
     * @param priceExpDataAddDto
     * @return
     */
    ResultUtil<Long> addExpPrice(PriceExpMainAddDto priceExpMainAddDto,
                                 PriceExpCostAddDto priceExpCostAddDto,
                                 PriceExpSaleAddDto priceExpSaleAddDto,
                                 PriceExpAxisAddDto priceExpAxisAddDto,
                                 PriceExpDataAddDto priceExpDataAddDto);


    /**
     * 新增销售价格
     * @param priceExpSaleAddDto
     * @return
     */
    ResultUtil<Long> addSalePrice(PriceExpSaleAddDto priceExpSaleAddDto);

    /**
     * 新增成本价格
     * @param priceExpCostAddDto
     * @return
     */
    ResultUtil<Long> addCostPrice(PriceExpCostAddDto priceExpCostAddDto);


    /**
     * 根据id批量删除成本价格数据
     * @param ids
     * @return
     */
    ResultUtil<Boolean> deleteCostBatch(List<Long> ids);

    /**
     * 根据id批量删除销售价格数据
     * @param ids
     * @return
     */
    ResultUtil<Boolean> deleteSaleBatch(List<Long> ids);
}
