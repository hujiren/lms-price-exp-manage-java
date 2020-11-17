package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.po.CarrierPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.apl.lib.utils.ResultUtil;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  service接口
 * </p>
 *
 * @author hjr
 * @since 2020-10-07
 */
public interface CarrierService extends IService<CarrierPo> {

        /**
         * @Desc: 添加一个CarrierPo实体
         * @author hjr
         * @since 2020-10-07
         */
        ResultUtil<Long> add(CarrierPo carrierPo);


        /**
         * @Desc: 根据id 更新一个CarrierPo 实体
         * @author hjr
         * @since 2020-10-07
         */
        ResultUtil<Boolean> updById(CarrierPo carrierPo);


        /**
         * @Desc: 根据id 查找一个CarrierPo 实体
         * @author hjr
         * @since 2020-10-07
         */
        ResultUtil<Boolean> delById(Long id);


        /**
         * @Desc: 分页查找 CarrierPo 列表
         * @author hjr
         * @since 2020-10-07
         */
        ResultUtil<List<CarrierPo>>getList();

        /**
         * 查询列表
         * @param innerOrgId
         * @return
         */
        ResultUtil<List<CarrierPo>> getListByInnerOrgId(Long innerOrgId) throws IOException, Exception;
}