package com.apl.lms.price.exp.manage.mapper;
import com.apl.lms.price.exp.pojo.dto.ChannelCateGoryKeyDto;
import com.apl.lms.price.exp.pojo.po.ChannelCategoryPo;
import com.apl.lms.price.exp.pojo.vo.ChannelCategoryVo;
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
public interface ChannelCategoryMapper extends BaseMapper<ChannelCategoryPo> {

    /**
     * 获取渠道类型分页信息列表
     * @param page
     * @param channelCateGoryKeyDto
     * @return
     */

    List<ChannelCategoryVo> getList(Page<ChannelCategoryVo> page, @Param("key") ChannelCateGoryKeyDto channelCateGoryKeyDto);

    /**
     * 根据Id删除渠道类型数据
     * @param id
     * @return
     */
    Integer delById(@Param("id") Long id);

    /**
     * 更新渠道类型
     * @param channelCateGoryPo
     * @return
     */
    Integer updChannelCategory(@Param("po") ChannelCategoryPo channelCateGoryPo);

    /**
     * 新增渠道类型
     * @param channelCateGoryPo
     * @return
     */
    Integer addChannelCategory(@Param("po") ChannelCategoryPo channelCateGoryPo);

    /**
     * 根据id查询对应列表
     * @param id
     * @return
     */
    ChannelCategoryVo getChannelCateGory(@Param("id") Long id);
}
