package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.ChannelCateGoryDto;
import com.apl.lms.price.exp.pojo.dto.ChannelCateGoryInsertDto;
import com.apl.lms.price.exp.pojo.dto.ChannelCateGoryKeyDto;
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
    ResultUtil<Page<ChannelCategoryVo>> getList(PageDto pageDto, ChannelCateGoryKeyDto channelCateGoryKeyDto);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultUtil<Boolean> delChannelCategory(Long id);

    /**
     * 更新渠道类型
     * @param channelCateGoryDto
     * @return
     */
    ResultUtil<Boolean> updChannelCategory(ChannelCateGoryDto channelCateGoryDto);

    /**
     * 新增渠道类型对象
     * @param channelCateGoryInsertDto
     * @return
     */
    ResultUtil<String> addChannelCategory(ChannelCateGoryInsertDto channelCateGoryInsertDto);

    /**
     * 获取渠道类型详细
     * @param id
     * @return
     */
    ResultUtil<ChannelCategoryVo> getChannelCategory(Long id);
}
