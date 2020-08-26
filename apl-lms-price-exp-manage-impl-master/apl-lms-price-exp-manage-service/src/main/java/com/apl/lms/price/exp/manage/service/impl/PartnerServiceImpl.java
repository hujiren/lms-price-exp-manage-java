package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PartnerMapper;
import com.apl.lms.price.exp.manage.service.PartnerService;
import com.apl.lms.price.exp.pojo.dto.PartnerKeyDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpPartnerDto;
import com.apl.lms.price.exp.pojo.po.PartnerPo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @Classname PricePartnerServiceImpl
 * @Date 2020/8/26 10:00
 */
@Service
@Slf4j
public class PartnerServiceImpl extends ServiceImpl<PartnerMapper, PartnerPo> implements PartnerService {

    @Override
    public ResultUtil<Page<PartnerPo>> getList(PageDto pageDto, PartnerKeyDto partnerKeyDto){

        Page<PartnerPo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<PartnerPo> partnerPoList = baseMapper.getList(page, partnerKeyDto);

        page.setRecords(partnerPoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    @Override
    public ResultUtil<Boolean> delPartner(Long id) {

        Integer integer = baseMapper.deleteById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, true);
    }

    @Override
    public ResultUtil<Boolean> updPartner(PartnerPo partnerPo) {

        Integer integer = baseMapper.updateById(partnerPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);

    }

    @Override
    public ResultUtil<Integer> addPartner(List<PriceExpPartnerDto> priceExpPartnerDtoList) {
        return null;
    }

    @Override
    public ResultUtil<PartnerPo> getPartner(Long id) {
        return null;
    }
}
