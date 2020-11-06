package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.apl.lms.price.exp.pojo.dto.UnifyProfitDto;
import com.apl.lms.price.exp.pojo.po.UnifyExpPricePo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @Classname UnifyProfitService
 * @Date 2020/11/4 9:53
 */
public interface UnifyProfitService extends IService<UnifyExpPricePo> {

    //添加 OR 修改
    Integer saveUnifyProfit(UnifyProfitDto unifyProfitDto);

    //批量删除
    Integer del(List<Long> ids);

    //获取列表
    List<UnifyProfitDto> getList(Long customerGroupId);

    //根据客户组id, 租户id进行筛选
    List<PriceExpProfitDto> getListForTenant(Long customerGroupId, Long innerOrgId);
}
