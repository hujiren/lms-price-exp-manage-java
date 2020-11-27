package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.po.CarrierPo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hjr
 * @since 2020-10-07
 */
@Repository
public interface CarrierMapper extends BaseMapper<CarrierPo> {

    
    /**
     * @Desc: 查找公共运输方列表
     * @Date: 2020-10-07
     */
    List<CarrierPo> getList();

    /**
     * 根据租户id查询公共运输方列表
     * @param innerOrgId
     * @return
     */
    @SqlParser(filter = true)
    List<CarrierPo> getListByInnerOrgId(Long innerOrgId);
}