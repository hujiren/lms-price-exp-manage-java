package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.PartnerKeyDto;
import com.apl.lms.price.exp.pojo.po.PartnerPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hjr start
 * @Classname PricePartnerMapper
 * @Date 2020/8/26 10:01
 */
@Repository
public interface PartnerMapper extends BaseMapper<PartnerPo> {

    /**
     * 分页获取服务商列表
     * @param page
     * @param partnerKeyDto
     * @return
     */
    List<PartnerPo> getList(Page<PartnerPo> page, @Param("key") PartnerKeyDto partnerKeyDto);

}
