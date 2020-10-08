package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lms.price.exp.manage.mapper.SysInnerOrgMapper;
import com.apl.lms.price.exp.manage.service.SysInnerOrgService;
import com.apl.lms.price.exp.pojo.po.SysInnerOrgPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hjr start
 * @Classname SysInnerOrgServiceImpl
 * @Date 2020/10/8 15:00
 */
@Service
@Slf4j
public class SysInnerOrgServiceImpl extends ServiceImpl<SysInnerOrgMapper, SysInnerOrgPo> implements SysInnerOrgService {

    @Override
    public SysInnerOrgPo getSysInnerOrgInfo(Long innerOrgId) {
        SysInnerOrgPo sysInnerOrgPo = baseMapper.getSysInnerOrgInfo(innerOrgId);
        return sysInnerOrgPo;
    }
}
