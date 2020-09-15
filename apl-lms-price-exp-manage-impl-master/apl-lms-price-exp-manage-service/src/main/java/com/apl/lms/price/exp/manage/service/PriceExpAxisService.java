package com.apl.lms.price.exp.manage.service;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.PriceExpAxisAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpAxisVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataService
 * @Date 2020/8/19 17:30
 */
public interface PriceExpAxisService extends IService<PriceExpAxisPo> {


    /**
     * 根据主表id删除数据
     * @param priceExpMainIds
     * @return
     */
    Integer deleteByIds(List<Long> priceExpMainIds);


    /**
     * 保存价格表轴数据
     * @param priceDataId
     * @param priceExpAxisAddDto
     * @return
     */
    Boolean addPriceExpAxis(Long priceDataId, PriceExpAxisAddDto priceExpAxisAddDto);

    /**
     * 更新
     * @param priceExpAxisPo
     * @return
     */
    Boolean updateByMainId(PriceExpAxisPo priceExpAxisPo);

    /**
     * 获取详细
     * @param id
     * @return
     */
    ResultUtil<PriceExpAxisVo> getAxisInfoById(Long id);
}
