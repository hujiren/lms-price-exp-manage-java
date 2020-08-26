package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.PartnerKeyDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpPartnerDto;
import com.apl.lms.price.exp.pojo.po.PartnerPo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @Classname PartnerService
 * @Date 2020/8/26 9:53
 */
public interface PartnerService extends IService<PartnerPo> {

    ResultUtil<Page<PartnerPo>> getList(PageDto pageDto, PartnerKeyDto partnerKeyDto);

    ResultUtil<Boolean> delPartner(Long id);

    ResultUtil<Boolean> updPartner(PartnerPo partnerPo);

    ResultUtil<Integer> addPartner(List<PriceExpPartnerDto> priceExpPartnerDtoList);

    ResultUtil<PartnerPo> getPartner(Long id);
}
