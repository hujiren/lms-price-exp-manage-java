package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
    ResultUtil<List<PriceZoneDataListVo>> getList(Long id) throws Exception;

    /**
     * 批量删除
     * @param ids
     * @return
     */
    ResultUtil<Boolean> deleteBatch(List<Long> ids);

    /**
     * 根据分区id批量删除
     * @param ids
     * @return
     */
    Integer delBatchByZoneId(List<Long> ids);

    /**
     * 组装分区数据
     */
    Map<Long, List<PriceZoneDataListVo>> assemblingZoneData(List<Long> ids) throws Exception;
}
