package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.WeightWayMapper;
import com.apl.lms.price.exp.manage.service.WeightWayService;
import com.apl.lms.price.exp.pojo.dto.WeightWayUpdDto;
import com.apl.lms.price.exp.pojo.dto.WeightWayAddDto;
import com.apl.lms.price.exp.pojo.dto.WeightWayKeyDto;
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
public class WeightWayServiceImpl extends ServiceImpl<WeightWayMapper, WeightWayUpdDto> implements WeightWayService {

    enum WeightWayServiceCode {
        ID_DOES_NOT_EXITS("ID_DOES_NOT_EXITS", "id不存在");
        ;

        private String code;
        private String msg;

        WeightWayServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 分页查找附加费
     * @param weightWayKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<WeightWayUpdDto>> getList(PageDto pageDto, WeightWayKeyDto weightWayKeyDto) {

        Page<WeightWayUpdDto> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        if(null == weightWayKeyDto.getCode() || weightWayKeyDto.getCode() < 0){
            weightWayKeyDto.setCode(0);
        }
        List<WeightWayUpdDto> weightWayUpdDtoList = baseMapper.getList(page, weightWayKeyDto);

        page.setRecords(weightWayUpdDtoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据id删除附加费
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delWeightWay(Long id) {
        Integer integer = baseMapper.deleteById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL.code, WeightWayServiceCode.ID_DOES_NOT_EXITS.msg, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新附加费
     * @param weightWayUpdDto
     * @return
     */
    @Override
    public ResultUtil<Boolean>  updWeightWay(WeightWayUpdDto weightWayUpdDto) {

        Integer integer = baseMapper.updateById(weightWayUpdDto);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 批量添加附加费
     * @param weightWayAddDtoList
     * @return
     */
    @Override
    public ResultUtil<Integer> addWeightWay(List<WeightWayAddDto> weightWayAddDtoList) {

        for (WeightWayAddDto weightWayDto : weightWayAddDtoList) {

            weightWayDto.setId(SnowflakeIdWorker.generateId());
        }

        Integer integer = baseMapper.addWeightWay(weightWayAddDtoList);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, integer);
    }

    /**
     * 获取计泡方式详情
     * @param id
     * @return
     */
    @Override
    public ResultUtil<WeightWayUpdDto> getWeightWay(Long id) {
        WeightWayUpdDto weightWayUpdDto = baseMapper.getWeightWay(id);
        if(weightWayUpdDto == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, weightWayUpdDto);
    }
}
