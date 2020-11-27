package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.PartnerKeyDto;
import com.apl.lms.price.exp.pojo.po.PartnerPo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @Classname PartnerService
 * @Date 2020/8/26 9:53
 */
public interface PartnerService extends IService<PartnerPo> {

    /**
     * 分页获取服务商列表
     * @param pageDto
     * @param partnerKeyDto
     * @return
     */
    ResultUtil<Page<PartnerPo>> getList(PageDto pageDto, PartnerKeyDto partnerKeyDto);

    /**
     * 删除服务商
     * @param id
     * @return
     */
    ResultUtil<Boolean> delPartner(Long id);

    /**
     * 更新服务商
     * @param partnerPo
     * @return
     */
    ResultUtil<Boolean> updPartner(PartnerPo partnerPo);

    /**
     * 新增服务商
     * @param partnerPo
     * @return
     */
    ResultUtil<Integer> addPartner(PartnerPo partnerPo);

    /**
     * 获取服务商详细
     * @param id
     * @return
     */
    ResultUtil<PartnerPo> getPartner(Long id);
}
