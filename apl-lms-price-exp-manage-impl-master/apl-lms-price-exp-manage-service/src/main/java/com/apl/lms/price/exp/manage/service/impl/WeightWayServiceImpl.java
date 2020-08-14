package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.common.mapper.WeightWayMapper;
import com.apl.lms.common.query.manage.dto.*;
import com.apl.lms.common.service.WeightWayService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author hjr
 * @since 2020-08-08
 */
@Service
@Slf4j
public class WeightWayServiceImpl extends ServiceImpl<WeightWayMapper, WeightWayDto> implements WeightWayService {

    /**
     * 分页查找附加费
     * @param weightWayKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<WeightWayDto>> getList(PageDto pageDto, WeightWayKeyDto weightWayKeyDto) {

        Page<WeightWayDto> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<WeightWayDto> weightWayDtoList = baseMapper.getList(page, weightWayKeyDto);

        page.setRecords(weightWayDtoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据id删除附加费
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delWeightWay(Long id) {
        Integer integer = baseMapper.delById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新附加费
     * @param weightWayDto
     * @return
     */
    @Override
    public ResultUtil<Boolean>  updWeightWay(WeightWayDto weightWayDto) {

        Integer integer = baseMapper.updWeightWay(weightWayDto);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 添加附加费
     * @param weightWayInsertDto
     * @return
     */
    @Override
    public ResultUtil<Long> addWeightWay(WeightWayInsertDto weightWayInsertDto) {

        WeightWayDto weightWayDto = new WeightWayDto();
        BeanUtils.copyProperties(weightWayInsertDto, weightWayDto);
        weightWayDto.setId(SnowflakeIdWorker.generateId());

        Integer integer = baseMapper.addWeightWay(weightWayDto);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, weightWayDto.getId());
    }
}
