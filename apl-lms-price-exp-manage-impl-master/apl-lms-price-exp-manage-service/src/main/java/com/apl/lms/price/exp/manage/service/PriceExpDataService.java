package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpDataAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataAxisVo;
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
     * 根据价格表id获取主数据信息
     * @param id
     * @return
     */
    ResultUtil<PriceExpDataVo> getPriceExpDataInfoByPriceId(Long id);

    /**
     * 保存价格表数据
     * @param priceMainId
     * @return
     */
    Boolean addPriceExpData(Long priceMainId, PriceExpAddDto priceExpAddDto);

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    Boolean updById(PriceExpDataPo priceExpDataPo);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);

}
