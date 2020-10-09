package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.po.PriceSurchargePo;
import com.apl.lms.price.exp.pojo.vo.PriceSurchargeVo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.apl.lib.utils.ResultUtil;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * <p>
 *  service接口
 * </p>
 *
 * @author hjr
 * @since 2020-09-28
 */
public interface PriceSurchargeService extends IService<PriceSurchargePo> {


        ResultUtil<Boolean> save( List<PriceSurchargePo> priceSurchargePos) throws Exception;

        /**
         * @Desc: 根据id 查找一个SurchargePo 实体
         * @author hjr
         * @since 2020-09-28
         */
        ResultUtil<Boolean> delById(Long id);


        /**
         * @Desc: 根据id 查找一个 SurchargePo 实体
         * @author hjr
         * @since 2020-09-28
         */
        @SqlParser(filter = true)
        List<PriceSurchargeVo> selectById(Long priceId) throws Exception;

}