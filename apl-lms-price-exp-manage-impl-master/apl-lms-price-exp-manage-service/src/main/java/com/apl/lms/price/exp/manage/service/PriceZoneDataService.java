package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.PriceZoneNameKeyDto;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceZoneDataService
 * @Date 2020/8/31 15:15
 */
public interface PriceZoneDataService extends IService<PriceZoneDataListVo> {

    /**
     * 获取列表
     * @param id
     * @return
     */
    ResultUtil<List<PriceZoneDataListVo>> getList(Long id);

    /**
     * 批量删除
     * @param id
     * @return
     */
    ResultUtil<Boolean> deleteBatch(Long id);
}
