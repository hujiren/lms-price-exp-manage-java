package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.po.ChannelCategoryPo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface ChannelCategoryService extends IService<ChannelCategoryPo> {

    /**
     * 分页查询渠道类型列表
     * @param
     * @param
     * @return
     */
    ResultUtil<List<ChannelCategoryPo>> getList();

    /**
     * 删除
     * @param id
     * @return
     */
    ResultUtil<Boolean> delChannelCategory(Long id);

    /**
     * 更新渠道类型
     * @param channelCategoryPo
     * @return
     */
    ResultUtil<Boolean> updChannelCategory(ChannelCategoryPo channelCategoryPo);

    /**
     * 新增渠道类型对象
     * @param channelCategoryPo
     * @return
     */
    ResultUtil<String> addChannelCategory(ChannelCategoryPo channelCategoryPo);

}
