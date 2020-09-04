package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.ChannelCategoryUpdDto;
import com.apl.lms.price.exp.pojo.dto.ChannelCategoryAddDto;
import com.apl.lms.price.exp.pojo.dto.ChannelCategoryKeyDto;
import com.apl.lms.price.exp.pojo.po.ChannelCategoryPo;
import com.apl.lms.price.exp.pojo.vo.ChannelCategoryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface ChannelCategoryService extends IService<ChannelCategoryPo> {

    /**
     * 分页查询渠道类型列表
     * @param pageDto
     * @param
     * @return
     */
    ResultUtil<Page<ChannelCategoryVo>> getList(PageDto pageDto, ChannelCategoryKeyDto channelCateGoryKeyDto);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultUtil<Boolean> delChannelCategory(Long id);

    /**
     * 更新渠道类型
     * @param channelCateGoryUpdDto
     * @return
     */
    ResultUtil<Boolean> updChannelCategory(ChannelCategoryUpdDto channelCateGoryUpdDto);

    /**
     * 新增渠道类型对象
     * @param channelCateGoryAddDto
     * @return
     */
    ResultUtil<String> addChannelCategory(ChannelCategoryAddDto channelCateGoryAddDto);

    /**
     * 获取渠道类型详细
     * @param id
     * @return
     */
    ResultUtil<ChannelCategoryVo> getChannelCategory(Long id);
}
