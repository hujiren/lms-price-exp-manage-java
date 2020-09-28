package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.BulkyWayMapper;
import com.apl.lms.price.exp.manage.service.BulkyWayService;
import com.apl.lms.price.exp.pojo.dto.BulkyWayUpdDto;
import com.apl.lms.price.exp.pojo.po.BulkyWayPo;
import com.apl.lms.price.exp.pojo.dto.BulkyWayKeyDto;
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
public class BulkyWayServiceImpl extends ServiceImpl<BulkyWayMapper, BulkyWayUpdDto> implements BulkyWayService {

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
     * @param bulkyWayKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<BulkyWayUpdDto>> getList(PageDto pageDto, BulkyWayKeyDto bulkyWayKeyDto) {

        Page<BulkyWayUpdDto> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        if(null == bulkyWayKeyDto.getCode() || bulkyWayKeyDto.getCode() < 0){
            bulkyWayKeyDto.setCode(0);
        }
        List<BulkyWayUpdDto> bulkyWayUpdDtoList = baseMapper.getList(page, bulkyWayKeyDto);

        page.setRecords(bulkyWayUpdDtoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据id删除附加费
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> del(Long id) {
        Integer integer = baseMapper.deleteById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL.code, WeightWayServiceCode.ID_DOES_NOT_EXITS.msg, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新附加费
     * @param bulkyWayUpdDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> upd(BulkyWayUpdDto bulkyWayUpdDto) {

        Integer integer = baseMapper.updateById(bulkyWayUpdDto);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 批量添加附加费
     * @param bulkyWayPoList
     * @return
     */
    @Override
    public ResultUtil<Integer> add(List<BulkyWayPo> bulkyWayPoList) {

        for (BulkyWayPo weightWayDto : bulkyWayPoList) {

            weightWayDto.setId(SnowflakeIdWorker.generateId());
        }

        Integer integer = baseMapper.add(bulkyWayPoList);
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
    public ResultUtil<BulkyWayUpdDto> get(Long id) {
        BulkyWayUpdDto bulkyWayUpdDto = baseMapper.get(id);
        if(bulkyWayUpdDto == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, bulkyWayUpdDto);
    }
}
