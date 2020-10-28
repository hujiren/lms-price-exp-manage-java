package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceZoneNamePo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface PriceZoneNameService extends IService<PriceZoneNamePo> {

    /**
     * 获取快递分区名称
     * @param id
     * @return
     */
    @SqlParser(filter = true)
    String getPriceZoneName(Long id);

    /**
     * 分页查询快递分区名称列表
     * @param pageDto
     * @param priceZoneNameKeyDto
     * @return
     */
    ResultUtil<Page<PriceZoneNamePo>> getPriceZoneNameList(PageDto pageDto, PriceZoneNameKeyDto priceZoneNameKeyDto);

    /**
     * 批量从删除快递分区名称
     * @param ids
     * @return
     */
    ResultUtil<Boolean> delBatchPriceZoneName(List<Long> ids);

    /**
     * 更新快递分区
     * @param priceZoneNamePo
     * @return
     */
    ResultUtil<Boolean> updPriceZoneName(PriceZoneNamePo priceZoneNamePo);

    /**
     * 新增快递分区
     * @param priceZoneNamePo
     * @return
     */
    ResultUtil<Long> addPriceZoneName(PriceZoneNamePo priceZoneNamePo);

    /**
     * 批量获取分区名称
     * @param zoneIds
     * @return
     */
    Map<Long, PriceZoneNamePo> getPriceZoneNameBatch(List<Long> zoneIds);

}
