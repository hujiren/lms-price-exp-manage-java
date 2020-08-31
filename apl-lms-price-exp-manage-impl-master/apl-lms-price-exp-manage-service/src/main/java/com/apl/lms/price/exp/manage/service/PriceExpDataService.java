package com.apl.lms.price.exp.manage.service;

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
     * @param priceExpMainIds
     * @return
     */
    Integer deleteByPriceExpMainId(List<Long> priceExpMainIds);

    /**
     * 根据主表id获取主数据信息
     * @param id
     * @return
     */
    PriceExpDataVo getPriceExpDataInfoByMainId(Long id);

    /**
     * 获取多租户id
     * @param id
     * @return
     */
    Long getInnerOrgId(Long id);


    /**
     * 保存价格表数据
     * @param priceMainId
     * @param priceExpDataAddDto
     * @return
     */
    Boolean addPriceExpData(Long priceMainId, PriceExpDataAddDto priceExpDataAddDto);

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    Boolean updateByMainId(PriceExpDataPo priceExpDataPo);
}
