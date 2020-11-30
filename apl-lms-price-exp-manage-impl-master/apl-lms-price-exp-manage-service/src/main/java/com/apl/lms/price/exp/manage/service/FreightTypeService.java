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
     * @Desc: 新增运输类型
     * @author hjr
     * @since 2020-10-07
     */
    ResultUtil<Long> add(List<FreightTypePo> freightTypePoList);

    /**
     * @Desc: 删除运输类型
     * @author hjr
     * @since 2020-10-07
     */
    ResultUtil<Boolean> delById(Long id);

    /**
     * @Desc: 获取运输类型列表
     * @author hjr
     * @since 2020-10-07
     */
    List<FreightTypePo> getList();

    /**根据租户id获取运输类型列表
     * @param innerOrgId
     * @return
     */
    List<FreightTypePo> getListByInnerOrgId(Long innerOrgId);
}