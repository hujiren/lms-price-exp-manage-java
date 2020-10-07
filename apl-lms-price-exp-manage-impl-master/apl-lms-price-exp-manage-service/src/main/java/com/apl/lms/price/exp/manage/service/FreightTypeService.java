package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.po.FreightTypePo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.apl.lib.utils.ResultUtil;

import java.util.List;

/**
 * <p>
 * service接口
 * </p>
 *
 * @author hjr
 * @since 2020-10-07
 */
public interface FreightTypeService extends IService<FreightTypePo> {

    /**
     * @Desc: 添加一个FreightTypePo实体
     * @author hjr
     * @since 2020-10-07
     */
    ResultUtil<Long> add(List<FreightTypePo> freightTypePoList);


    /**
     * @Desc: 根据id 查找一个FreightTypePo 实体
     * @author hjr
     * @since 2020-10-07
     */
    ResultUtil<Boolean> delById(Long id);


    /**
     * @Desc: 分页查找 FreightTypePo 列表
     * @author hjr
     * @since 2020-10-07
     */
    List<FreightTypePo> getList();

    /**
     * @param innerOrgId
     * @return
     */
    List<FreightTypePo> getListByInnerOrgId(Long innerOrgId);
}