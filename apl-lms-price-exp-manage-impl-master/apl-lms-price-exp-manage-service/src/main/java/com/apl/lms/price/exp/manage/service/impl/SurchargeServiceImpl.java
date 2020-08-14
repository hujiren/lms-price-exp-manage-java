package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.common.mapper.SurchargeMapper;
import com.apl.lms.common.query.manage.dto.*;
import com.apl.lms.common.service.SurchargeService;
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
public class SurchargeServiceImpl extends ServiceImpl<SurchargeMapper, SurchargeDto> implements SurchargeService {

    /**
     * 分页查找附加费
     * @param surchargeKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<SurchargeDto>> getList(PageDto pageDto, SurchargeKeyDto surchargeKeyDto) {

        Page<SurchargeDto> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<SurchargeDto> surchargeDtoList = baseMapper.getList(page, surchargeKeyDto);

        page.setRecords(surchargeDtoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据id删除附加费
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delSurcharge(Long id) {
        Integer integer = baseMapper.delById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新附加费
     * @param surchargeDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updSurcharge(SurchargeDto surchargeDto) {

        Integer integer = baseMapper.updSurcharge(surchargeDto);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 添加附加费
     * @param surchargeInsertDto
     * @return
     */
    @Override
    public ResultUtil<Long> addSurcharge(SurchargeInsertDto surchargeInsertDto) {

        SurchargeDto surchargeDto = new SurchargeDto();
        BeanUtils.copyProperties(surchargeInsertDto, surchargeDto);
        surchargeDto.setId(SnowflakeIdWorker.generateId());

        Integer integer = baseMapper.addSurcharge(surchargeDto);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, surchargeDto.getId());
    }
}
