package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.po.PriceSurchargePo;
import com.apl.lms.price.exp.pojo.vo.PriceSurchargeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * service接口
 * </p>
 *
 * @author hjr
 * @since 2020-09-28
 */
public interface PriceSurchargeService extends IService<PriceSurchargePo> {


    /**
     * 批量保存
     * @param priceSurchargePos
     * @return
     * @throws Exception
     */
    ResultUtil<Boolean> save(List<PriceSurchargePo> priceSurchargePos) throws Exception;

    /**
     * @Desc: 删除附加费
     * @author hjr
     * @since 2020-09-28
     */
    ResultUtil<Boolean> delById(Long id, Long priceId) throws IOException;


    /**
     * @Desc: 获取附加费详细
     * @author hjr
     * @since 2020-09-28
     */

    List<PriceSurchargeVo> selectById(Long priceId) throws Exception;

    /**
     * @Desc: 获取附加费详细
     * @author hjr
     * @since 2020-09-28
     */
    List<PriceSurchargePo> getById(Long priceId);

    /**
     * 根据价格表id批量查询附加费id
     * @param priceId
     * @return
     */
    List<Long> getIdBatch(Long priceId);

    /**
     * 批量删除
     * @param toString
     * @return
     */
    Integer delBatch(String toString) throws IOException;
}