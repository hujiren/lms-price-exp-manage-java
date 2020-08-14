package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.PriceZoneMapper;
import com.apl.lms.price.exp.manage.service.PriceZoneService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceZonePo;
import com.apl.lms.price.exp.pojo.vo.PriceZoneVo;
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
public class PriceZoneServiceImpl extends ServiceImpl<PriceZoneMapper, PriceZonePo> implements PriceZoneService {

    enum ExpListServiceCode {
        ;

        private String code;
        private String msg;

        ExpListServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 分页查询快递价格
     * @param pageDto
     * @param
     * @return
     */
    @Override
    public ResultUtil<Page<PriceZoneVo>> getList(PageDto pageDto, PriceZoneInsertKeyDto priceZoneInsertKeyDto) {

        Page<PriceZoneVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<PriceZoneVo> priceZoneVoList = baseMapper.getList(page, priceZoneInsertKeyDto);

        page.setRecords(priceZoneVoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 获取详细
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Page<PriceZoneVo>> get(Long id) {

        PriceZoneVo priceZoneVo =  baseMapper.getById(id);
        if(priceZoneVo == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL, null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceZoneVo);
    }

    /**
     * 根据id删除燃油费
     * @param ids
     * @return
     */
    @Override
    public ResultUtil<Boolean> delBatchPriceZone(List<Long> ids){

        Integer integer = baseMapper.delById(ids);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新快递分区
     * @param priceZoneDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updPriceZone(PriceZoneDto priceZoneDto) {

        PriceZonePo priceZonePo = new PriceZonePo();
        BeanUtils.copyProperties(priceZoneDto, priceZonePo);
        Integer integer = baseMapper.updPriceZone(priceZonePo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 新增快递分区
     * @param priceZoneInsertDto
     * @return
     */
    @Override
    public ResultUtil<Long> addPriceZone(PriceZoneInsertDto priceZoneInsertDto) {

        PriceZonePo priceZonePo = new PriceZonePo();
        BeanUtils.copyProperties(priceZoneInsertDto, priceZonePo);
        priceZonePo.setId(SnowflakeIdWorker.generateId());

        Integer integer = baseMapper.addPriceZone(priceZonePo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceZonePo.getId());
    }
}
