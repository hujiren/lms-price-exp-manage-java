package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.common.query.manage.po.CommonCarrierPo;
import com.apl.lms.price.exp.pojo.po.CarrierPo;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

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
     * @Desc: 查找列表
     * @Author: ${cfg.author}
     * @Date: 2020-10-07
     */
    List<CommonCarrierPo> getList();

    /**
     * 查询列表
     * @param innerOrgId
     * @return
     */
    @SqlParser(filter = true)
    List<CommonCarrierPo> getListByInnerOrgId(Long innerOrgId);
}