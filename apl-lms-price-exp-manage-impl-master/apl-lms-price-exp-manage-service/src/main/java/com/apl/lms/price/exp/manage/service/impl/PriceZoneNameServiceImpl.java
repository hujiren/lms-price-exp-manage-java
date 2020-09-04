package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.PriceZoneMapper;
import com.apl.lms.price.exp.manage.service.PriceZoneNameService;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.PriceZoneNamePo;
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
public class PriceZoneNameServiceImpl extends ServiceImpl<PriceZoneMapper, PriceZoneNamePo> implements PriceZoneNameService {

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
    public ResultUtil<Page<PriceZoneVo>> getPriceZoneNameList(PageDto pageDto, PriceZoneNameKeyDto priceZoneNameKeyDto) {

        Page<PriceZoneVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        if(priceZoneNameKeyDto.getChannelCategory() != null)
        priceZoneNameKeyDto.setChannelCategory(priceZoneNameKeyDto.getChannelCategory().toUpperCase());
        List<PriceZoneVo> priceZoneVoList = baseMapper.getPriceZoneNameList(page, priceZoneNameKeyDto);
        page.setRecords(priceZoneVoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }


    /**
     * 批量从删除快递分区名称
     * @param ids
     * @return
     */
    @Override
    public ResultUtil<Boolean> delBatchPriceZoneName(List<Long> ids){

        Integer integer = baseMapper.delPriceZoneName(ids);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新快递分区名称
     * @param priceZoneNamePo
     * @return
     */
    @Override
    public ResultUtil<Boolean> updPriceZoneName(PriceZoneNamePo priceZoneNamePo) {


        Integer integer = baseMapper.updPriceZoneName(priceZoneNamePo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 新增快递分区名称
     * @param priceZoneNamePo
     * @return
     */
    @Override
    public ResultUtil<Long> addPriceZoneName(PriceZoneNamePo priceZoneNamePo) {

        priceZoneNamePo.setId(SnowflakeIdWorker.generateId());
        Integer integer = baseMapper.addPriceZoneName(priceZoneNamePo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceZoneNamePo.getId());
    }


}
