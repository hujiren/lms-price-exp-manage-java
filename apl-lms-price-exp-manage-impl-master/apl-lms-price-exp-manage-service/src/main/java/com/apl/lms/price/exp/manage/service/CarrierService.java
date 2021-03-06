package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.po.CarrierPo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  公共运输方service接口
 * </p>
 *
 * @author hjr
 * @since 2020-10-07
 */
public interface CarrierService extends IService<CarrierPo> {

        /**
         * @Desc: 添加运输方
         * @author hjr
         * @since 2020-10-07
         */
        ResultUtil<Long> add(CarrierPo carrierPo);


        /**
         * @Desc: 更新运输方
         * @author hjr
         * @since 2020-10-07
         */
        ResultUtil<Boolean> updById(CarrierPo carrierPo);


        /**
         * @Desc: 删除运输方
         * @author hjr
         * @since 2020-10-07
         */
        ResultUtil<Boolean> delById(Long id);


        /**
         * @Desc: 查找列表
         * @author hjr
         * @since 2020-10-07
         */
        ResultUtil<List<CarrierPo>>getList();

        /**
         * 查询列表(选择器: 租户Id)
         * @param innerOrgId
         * @return
         */
        ResultUtil<List<CarrierPo>> getListByInnerOrgId(Long innerOrgId) throws Exception;
}