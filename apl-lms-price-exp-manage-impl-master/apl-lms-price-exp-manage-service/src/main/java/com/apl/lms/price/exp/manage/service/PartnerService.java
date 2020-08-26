package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.PartnerKeyDto;
import com.apl.lms.price.exp.pojo.dto.PartnerDto;
import com.apl.lms.price.exp.pojo.po.PartnerPo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @Classname PartnerService
 * @Date 2020/8/26 9:53
 */
public interface PartnerService extends IService<PartnerPo> {

    ResultUtil<Page<PartnerPo>> getList(PageDto pageDto, PartnerKeyDto partnerKeyDto);

    ResultUtil<Boolean> delPartner(Long id);

    ResultUtil<Boolean> updPartner(PartnerPo partnerPo);

    ResultUtil<Integer> addPartner(PartnerDto partnerDto);

    ResultUtil<PartnerPo> getPartner(Long id);
}
