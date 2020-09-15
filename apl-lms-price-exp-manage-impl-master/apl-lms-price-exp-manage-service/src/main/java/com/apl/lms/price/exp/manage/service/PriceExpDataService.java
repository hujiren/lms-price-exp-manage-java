package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.PriceExpDataAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataService
 * @Date 2020/8/19 17:30
 */
public interface PriceExpDataService extends IService<PriceExpDataPo> {

    /**
     * 根据价格主表删除数据
     * @param ids
     * @return
     */
    Integer deleteBatchById(List<Long> ids);

    /**
     * 根据价格表id获取主数据信息
     * @param priceId
     * @return
     */
    ResultUtil<PriceExpDataVo> getPriceExpDataInfoByPriceId(Long priceId);



    /**
     * 保存价格表数据
     * @param priceDataId
     * @param priceExpDataAddDto
     * @return
     */
    Boolean addPriceExpData(Long priceDataId, PriceExpDataAddDto priceExpDataAddDto);

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    Boolean updById(PriceExpDataPo priceExpDataPo);
}
