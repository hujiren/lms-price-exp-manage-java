package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.PriceExpListDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpListInsertDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpListKeyDto;
import com.apl.lms.price.exp.pojo.po.PriceExpListPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface PriceExpListService extends IService<PriceExpListPo> {

    /**
     * 分页查询快递价格列表
     * @param pageDto
     * @param priceExpListKeyDto
     * @return
     */
    ResultUtil<Page<PriceExpListVo>> getExpList(PageDto pageDto, PriceExpListKeyDto priceExpListKeyDto);

    /**
     * 批量删除快递价格
     * @param ids
     * @return
     */
    ResultUtil<Boolean> delExpList(List<Long> ids);

    /**
     * 更新快递价格
     * @param priceExpListDto
     * @return
     */
    ResultUtil<Boolean> updExpList(PriceExpListDto priceExpListDto);

    /**
     * 新增快递价格
     * @param priceExpListInsertDto
     * @return
     */
    ResultUtil<Long> insExpList(PriceExpListInsertDto priceExpListInsertDto);
}