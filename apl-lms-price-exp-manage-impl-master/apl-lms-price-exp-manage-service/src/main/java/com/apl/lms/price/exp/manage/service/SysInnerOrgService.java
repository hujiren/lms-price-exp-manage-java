package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.po.SysInnerOrgPo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @Classname SysInnerOrgService
 * @Date 2020/10/8 14:58
 */
public interface SysInnerOrgService extends IService<SysInnerOrgPo> {

    SysInnerOrgPo getSysInnerOrgInfo(Long innerOrgId);
}
