package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostInfoVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostListVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleInfoVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface PriceExpService extends IService<PriceExpMainPo> {

    /**
     * 分页获取销售价格列表(分页)
     * @param pageDto
     * @param priceExpSaleListKeyDto
     * @return
     */
    ResultUtil<Page<PriceExpSaleListVo>> getPriceExpSaleList(PageDto pageDto, PriceExpSaleListKeyDto priceExpSaleListKeyDto);

    /**
     * 分页获取成本价格列表(分页)
     * @param pageDto
     * @param priceExpCostListKeyDto
     * @return
     */
    ResultUtil<Page<PriceExpCostListVo>> getPriceExpCostList(PageDto pageDto, PriceExpCostListKeyDto priceExpCostListKeyDto);

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
     * @param priceExpMainUpdateDto
     * @param priceExpSaleUpdateDto
     * @return
     */
    ResultUtil<Boolean> updateSalePrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                        PriceExpSaleUpdateDto priceExpSaleUpdateDto);

    /**
     * 更新成本价格表
     * @param priceExpMainUpdateDto
     * @param priceExpCostUpdateDto
     * @return
     */
    ResultUtil<Boolean> updateCostPrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                        PriceExpCostUpdateDto priceExpCostUpdateDto);

    /**
     * 更新价格表数据
     * @param priceExpDataAddDto
     * @param priceExpAxisPo
     * @return
     */
    ResultUtil<Boolean> updatePriceData(PriceExpDataAddDto priceExpDataAddDto, PriceExpAxisPo priceExpAxisPo);

    /**
     * 更新备注
     * @param priceExpRemarkPo
     * @return
     */
    ResultUtil<Boolean> updRemark(PriceExpRemarkPo priceExpRemarkPo);

    /**
     * 根据id删除成本价格数据
     * @param id
     * @return
     */
    ResultUtil<Boolean> deleteCostPrice(Long id);

    /**
     * 根据id删除销售价格数据
     * @param id
     * @return
     */
    ResultUtil<Boolean> deleteSalePrice(Long id);


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

}
