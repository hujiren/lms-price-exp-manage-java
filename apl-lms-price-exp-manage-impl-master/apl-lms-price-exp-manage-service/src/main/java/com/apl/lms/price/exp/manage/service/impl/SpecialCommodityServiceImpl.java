package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.common.mapper.SpecialCommodityMapper;
import com.apl.lms.common.query.manage.dto.*;
import com.apl.lms.common.service.SpecialCommodityService;
import com.apl.lms.price.exp.manage.mapper.SpecialCommodityMapper;
import com.apl.lms.price.exp.manage.service.SpecialCommodityService;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityDto;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityInsertDto;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityKeyDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author hjr
 * @since 2020-08-08
 */
@Service
@Slf4j
public class SpecialCommodityServiceImpl extends ServiceImpl<SpecialCommodityMapper, SpecialCommodityDto> implements SpecialCommodityService {

    /**
     * 分页查找
     * @param specialCommodityKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<SpecialCommodityDto>> getList(PageDto pageDto, SpecialCommodityKeyDto specialCommodityKeyDto) {

        Page<SpecialCommodityDto> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<SpecialCommodityDto> specialCommodityDtoList = baseMapper.getList(page, specialCommodityKeyDto);

        page.setRecords(specialCommodityDtoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据id删除特殊物品
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delSpecialCommodity(Long id) {
        Integer integer = baseMapper.delById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新特殊物品
     * @param specialCommodityDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updSpecialCommodity(SpecialCommodityDto specialCommodityDto) {

        Integer integer = baseMapper.updSpecialCommodity(specialCommodityDto);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 添加特殊物品
     * @param specialCommodityInsertDto
     * @return
     */
    @Override
    public ResultUtil<Long> addSpecialCommodity(SpecialCommodityInsertDto specialCommodityInsertDto) {

        SpecialCommodityDto specialCommodityDto = new SpecialCommodityDto();
        specialCommodityDto.setId(SnowflakeIdWorker.generateId());
        specialCommodityDto.setSpecialCommodityName(specialCommodityInsertDto.getSpecialCommodityName());

        Integer integer = baseMapper.addSpecialCommodity(specialCommodityDto);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, specialCommodityDto.getId());
    }
}
