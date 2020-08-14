package com.apl.lms.price.exp.manage.mapper;

import com.apl.lms.price.exp.pojo.dto.PriceExpListKeyDto;
import com.apl.lms.price.exp.pojo.po.PriceExpListPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Mapper
@Repository
public interface PriceExpListMapper extends BaseMapper<PriceExpListPo> {

    /**
     * 获取快递价格分页信息列表
     * @param page
     * @param priceExpListKeyDto
     * @return
     */
    List<PriceExpListVo> getExpList(Page page, @Param("key") PriceExpListKeyDto priceExpListKeyDto);

    /**
     * 根据Ids批删除快递价格
     * @param ids
     * @return
     */
    Integer delById(@Param("ids") List<Long> ids);

    /**
     * 更新快递价格
     * @param priceExpListPo
     * @return
     */
    Integer updExpList(@Param("po") PriceExpListPo priceExpListPo);

    /**
     * 新增快递价格
     * @param priceExpListPo
     * @return
     */
    Integer insertExpList(@Param("po") PriceExpListPo priceExpListPo);
}
