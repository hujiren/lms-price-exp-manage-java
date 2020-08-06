package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.pojo.dto.ChannelCateGoryDto;
import com.apl.lms.price.exp.manage.pojo.dto.ChannelCateGoryKeyDto;
import com.apl.lms.price.exp.manage.pojo.dto.ExpListDto;
import com.apl.lms.price.exp.manage.pojo.po.ChannelCateGoryPo;
import com.apl.lms.price.exp.manage.pojo.vo.ChannelCateGoryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface ChannelCategoryService extends IService<ChannelCateGoryPo> {

    /**
     * 分页查询渠道类型列表
     * @param pageDto
     * @param
     * @return
     */
    ResultUtil<Page<ChannelCateGoryVo>> getList(PageDto pageDto, ChannelCateGoryKeyDto channelCateGoryKeyDto);

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
     * @param channelCateGoryDto
     * @return
     */
    ResultUtil<Long> insChannelCategory(ChannelCateGoryDto channelCateGoryDto);



}
