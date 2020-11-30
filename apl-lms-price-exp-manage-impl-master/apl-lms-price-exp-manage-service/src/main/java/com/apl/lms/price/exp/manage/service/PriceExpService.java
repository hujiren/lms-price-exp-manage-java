package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpCostListVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataObjVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpPriceInfoVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpSaleListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
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
    ResultUtil<Page<PriceExpSaleListVo>> getPriceExpSaleList(PageDto pageDto, PriceExpSaleKeyDto keyDto);

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
    ResultUtil<Boolean> updExpPrice(PriceExpUpdDto priceExpUpdDto) throws IOException;

    /**
     * 更新价格表数据
     * @param priceExpDataUpdDto
     * @return
     */
    ResultUtil<Boolean> updatePriceData(PriceExpDataUpdDto priceExpDataUpdDto) throws IOException;

    /**
     * 新增快递价格
     * @param priceExpAddDto
     * @return
     */
    ResultUtil<Long> addExpPrice(PriceExpAddDto priceExpAddDto) throws Exception;

    /**
     * 同步价格表
     * @param priceIds
     * @return
     */
    ResultUtil<Boolean> syncPrice(List<Long> priceIds) throws Exception;

    /**
     * 根据id批量删除价格表
     * @param priceIds
     * @return
     */
    ResultUtil<Boolean> deletePriceBatch(List<Long> priceIds) throws IOException;


    /**
     * 引用价格表
     * @param priceReferenceDto
     * @return
     */
    ResultUtil<Boolean> referencePrice(PriceReferenceDto priceReferenceDto) throws Exception;


    /**
     * 获取quotePriceId, innerOrgId, priceDataId组合方法
     * @param id
     * @return
     */
    ExpPriceInfoBo getPriceInfo(Long id);

    /**
     * 更新轴数据
     * @param weightSectionUpdDto
     * @return
     */
    List<String> updTransverseWeightSection(WeightSectionUpdDto weightSectionUpdDto) throws IOException;

    /**
     * 批量获取价格表信息
     * @param ids
     * @return
     */
    List<ExpPriceInfoBo> getPriceInfoByIds(List<Long> ids);

    /**
     * 更新价格主表
     * @param priceExpMainPo
     * @return
     */
    Integer updatePriceExpMain(PriceExpMainPo priceExpMainPo);

    /**
     * 获取价格表数据
     * @param priceId
     * @return
     */
    PriceExpDataObjVo getPriceExpData(Long priceId,  Boolean isSaleProfit,  Long customerGroupId) throws Exception;


    /**
     * 检测价格是否被引用
     * @param quotePriceId
     * @return
     */
    ResultUtil<Boolean> isQuoteByExpPrice(Long quotePriceId);

    /**
     * 检测是否引用服务商价格
     * @param priceId
     * @return
     */
    ResultUtil<Boolean> isQuotePartnerPrice(Long priceId);


}
