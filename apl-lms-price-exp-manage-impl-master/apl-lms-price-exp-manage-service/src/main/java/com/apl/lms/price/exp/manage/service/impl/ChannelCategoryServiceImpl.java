package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.ChannelCategoryMapper;
import com.apl.lms.price.exp.manage.service.ChannelCategoryService;
import com.apl.lms.price.exp.pojo.dto.ChannelCateGoryDto;
import com.apl.lms.price.exp.pojo.dto.ChannelCateGoryInsertDto;
import com.apl.lms.price.exp.pojo.dto.ChannelCateGoryKeyDto;
import com.apl.lms.price.exp.pojo.po.ChannelCateGoryPo;
import com.apl.lms.price.exp.pojo.vo.ChannelCateGoryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Service
@Slf4j
public class ChannelCategoryServiceImpl extends ServiceImpl<ChannelCategoryMapper, ChannelCateGoryPo> implements ChannelCategoryService {

    enum ChannelCategoryServiceCode {

        ;

        private String code;
        private String msg;

        ChannelCategoryServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 分页查询
     * @param pageDto
     * @param channelCateGoryKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<ChannelCateGoryVo>> getList(PageDto pageDto, ChannelCateGoryKeyDto channelCateGoryKeyDto) {

        Page<ChannelCateGoryVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<ChannelCateGoryVo> channelCateGoryVoList = baseMapper.getList(page, channelCateGoryKeyDto);

        page.setRecords(channelCateGoryVoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }


    /**
     * 根据id删除渠道类型数据
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delChannelCategory(Long id) {

        Integer integer = baseMapper.delById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 修改
     * @param channelCateGoryDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updChannelCategory(ChannelCateGoryDto channelCateGoryDto) {

        ChannelCateGoryPo channelCateGoryPo = new ChannelCateGoryPo();
        BeanUtils.copyProperties(channelCateGoryDto, channelCateGoryPo);

        ChannelCateGoryVo channelCateGoryVo = baseMapper.getChannelCateGory(channelCateGoryDto.getId());

        if(channelCateGoryVo == null){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL.getCode(), "id不存在", null);
        }
        Integer integer = baseMapper.updChannelCategory(channelCateGoryPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 添加
     * @param channelCateGoryInsertDto
     * @return
     */
    @Override
    public ResultUtil<String> addChannelCategory(ChannelCateGoryInsertDto channelCateGoryInsertDto) {

        ChannelCateGoryPo channelCateGoryPo = new ChannelCateGoryPo();
        BeanUtils.copyProperties(channelCateGoryInsertDto, channelCateGoryPo);
        channelCateGoryPo.setId(SnowflakeIdWorker.generateId());
        Integer integer = baseMapper.addChannelCategory(channelCateGoryPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, channelCateGoryPo.getId().toString());
    }

    /**
     * 获取渠道类型详细
     * @param id
     * @return
     */
    @Override
    public ResultUtil<ChannelCateGoryVo> getChannelCategory(Long id) {

        ChannelCateGoryVo channelCateGory = baseMapper.getChannelCateGory(id);

        if(channelCateGory == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, channelCateGory);
    }
}
