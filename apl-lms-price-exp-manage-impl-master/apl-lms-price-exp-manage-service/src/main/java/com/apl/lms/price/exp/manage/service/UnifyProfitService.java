package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.dto.UnifyProfitDto;
import com.apl.lms.price.exp.pojo.po.UnifyExpPricePo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

/**
 * @author hjr start
 * @Classname UnifyProfitService
 * @Date 2020/11/4 9:53
 */
public interface UnifyProfitService extends IService<UnifyExpPricePo> {

    /**
     * 保存统加利润
     * @param unifyProfitDto
     * @return
     * @throws IOException
     */
    Integer saveUnifyProfit(UnifyProfitDto unifyProfitDto) throws IOException;

    /**
     * 批量删除
     * @param ids
     * @return
     * @throws IOException
     */
    Integer del(List<Long> ids) throws IOException;

    /**
     * 查询列表
     * @param customerGroupId
     * @param tenantId
     * @return
     */
    List<UnifyProfitDto> getList(Long customerGroupId, Long tenantId);

}
