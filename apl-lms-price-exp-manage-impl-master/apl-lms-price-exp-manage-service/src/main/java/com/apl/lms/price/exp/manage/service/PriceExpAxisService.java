package com.apl.lms.price.exp.manage.service;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpAxisVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @Classname PriceExpDataService
 * @Date 2020/8/19 17:30
 */
public interface PriceExpAxisService extends IService<PriceExpAxisPo> {



    /**
     * 保存价格表轴数据
     * @param priceDataId
     * @return
     */
    Boolean addPriceExpAxis(Long priceDataId, PriceExpAddDto priceExpAddDto);

    /**
     * 更新
     * @param priceExpAxisPo
     * @return
     */
    Boolean updateByMainId(PriceExpAxisPo priceExpAxisPo);

    /**
     * 获取详细
     * @param priceId
     * @return
     */
    ResultUtil<PriceExpAxisVo> getAxisInfoById(Long priceId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer delBatch(String ids);


}
