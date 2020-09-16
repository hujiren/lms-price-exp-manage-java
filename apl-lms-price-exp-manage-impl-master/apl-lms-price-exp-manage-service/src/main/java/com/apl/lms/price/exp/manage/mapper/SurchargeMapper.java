package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.SurchargeUpdDto;
import com.apl.lms.price.exp.pojo.po.SurchargePo;
import com.apl.lms.price.exp.pojo.dto.SurchargeKeyDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 航空公司 Mapper 接口
 * </p>
 *
 * @author cy
 * @since 2020-04-13
 */
@Mapper
@Repository
public interface SurchargeMapper extends BaseMapper<SurchargeUpdDto> {

    /**
     * @Desc: 查找附加费列表
     * @Author:
     * @Date: 2020-08-08
     */
    List<SurchargePo> getList(Page page, @Param("key") SurchargeKeyDto surchargeKeyDto);

    /**
     * 批量新增附加费
     * @param surchargePoList
     * @return
     */
    Integer addSurcharge(@Param("po") List<SurchargePo> surchargePoList);

    /**
     * 更新
     * @param surchargeUpdDto
     * @return
     */
    Integer updById(@Param("po") SurchargeUpdDto surchargeUpdDto);

    /**
     * 获取详细
     * @param id
     * @return
     */
    SurchargePo getById(@Param("id") Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delById(@Param("id") Long id);
}
