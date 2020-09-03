package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceZonePo;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.apl.lms.price.exp.pojo.vo.PriceZoneVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface PriceZoneService extends IService<PriceZonePo> {

    /**
     * 分页查询快递分区名称列表
     * @param pageDto
     * @param priceZoneNameKeyDto
     * @return
     */
    ResultUtil<Page<PriceZoneVo>> getPriceZoneNameList(PageDto pageDto, PriceZoneNameKeyDto priceZoneNameKeyDto);

    /**
     * 批量从删除快递分区名称
     * @param ids
     * @return
     */
    ResultUtil<Boolean> delBatchPriceZoneName(List<Long> ids);

    /**
     * 更新快递分区
     * @param priceZoneDto
     * @return
     */
    ResultUtil<Boolean> updPriceZoneName(PriceZoneDto priceZoneDto);

    /**
     * 新增快递分区
     * @param priceZoneInsertDto
     * @return
     */
    ResultUtil<Long> addPriceZoneName(PriceZoneInsertDto priceZoneInsertDto);


}
