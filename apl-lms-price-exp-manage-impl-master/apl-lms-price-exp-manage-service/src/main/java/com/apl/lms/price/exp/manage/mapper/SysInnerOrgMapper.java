package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.SysInnerOrgPo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author hjr start
 * @Classname SysInnerOrgMapper
 * @Date 2020/10/8 15:01
 */
@Repository
public interface SysInnerOrgMapper extends BaseMapper<SysInnerOrgPo> {

    /**
     * 获取信息
     * @param innerOrgId
     * @return
     */
    @SqlParser(filter = true)
    SysInnerOrgPo getSysInnerOrgInfo(Long innerOrgId);
}
