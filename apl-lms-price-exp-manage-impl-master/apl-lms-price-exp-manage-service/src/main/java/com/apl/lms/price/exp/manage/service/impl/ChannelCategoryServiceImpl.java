package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.ChannelCategoryMapper;
import com.apl.lms.price.exp.manage.service.ChannelCategoryService;
import com.apl.lms.price.exp.pojo.dto.ChannelCategoryUpdDto;
import com.apl.lms.price.exp.pojo.dto.ChannelCategoryAddDto;
import com.apl.lms.price.exp.pojo.dto.ChannelCategoryKeyDto;
import com.apl.lms.price.exp.pojo.po.ChannelCategoryPo;
import com.apl.lms.price.exp.pojo.vo.ChannelCategoryVo;
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
public class ChannelCategoryServiceImpl extends ServiceImpl<ChannelCategoryMapper, ChannelCategoryPo> implements ChannelCategoryService {

    enum ChannelCategoryServiceCode {
        ID_IS_NOT_EXITS("ID_IS_NOT_EXITS","id不存在")
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
    public ResultUtil<Page<ChannelCategoryVo>> getList(PageDto pageDto, ChannelCategoryKeyDto channelCateGoryKeyDto) {

        Page<ChannelCategoryVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<ChannelCategoryVo> channelCategoryVoList = baseMapper.getList(page, channelCateGoryKeyDto);

        page.setRecords(channelCategoryVoList);
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
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL.code,ChannelCategoryServiceCode.ID_IS_NOT_EXITS.msg, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 修改
     * @param channelCateGoryUpdDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updChannelCategory(ChannelCategoryUpdDto channelCateGoryUpdDto) {

        ChannelCategoryPo channelCateGoryPo = new ChannelCategoryPo();
        BeanUtils.copyProperties(channelCateGoryUpdDto, channelCateGoryPo);

        ChannelCategoryVo channelCateGoryVo = baseMapper.getChannelCateGory(channelCateGoryUpdDto.getId());

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
     * @param channelCateGoryAddDto
     * @return
     */
    @Override
    public ResultUtil<String> addChannelCategory(ChannelCategoryAddDto channelCateGoryAddDto) {

        ChannelCategoryPo channelCateGoryPo = new ChannelCategoryPo();
        BeanUtils.copyProperties(channelCateGoryAddDto, channelCateGoryPo);
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
    public ResultUtil<ChannelCategoryVo> getChannelCategory(Long id) {

        ChannelCategoryVo channelCateGory = baseMapper.getChannelCateGory(id);

        if(channelCateGory == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, channelCateGory);
    }
}
