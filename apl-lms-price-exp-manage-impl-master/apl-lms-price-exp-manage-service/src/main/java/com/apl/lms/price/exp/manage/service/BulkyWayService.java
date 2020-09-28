package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.BulkyWayUpdDto;
import com.apl.lms.price.exp.pojo.po.BulkyWayPo;
import com.apl.lms.price.exp.pojo.dto.BulkyWayKeyDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 航空公司 service接口
 * </p>
 *
 * @author hjr
 * @since 2020-08-08
 */
public interface BulkyWayService extends IService<BulkyWayUpdDto> {

    /**
     * @Desc: 分页查找 计泡方式列表
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Page<BulkyWayUpdDto>> getList(PageDto pageDto, BulkyWayKeyDto bulkyWayKeyDto);

    /**
     * @Desc: 根据Id删除计泡方式
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Boolean> del(Long id);

    /**
     * @Desc: 更新计泡方式
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Boolean> upd(BulkyWayUpdDto bulkyWayUpdDto);

    /**
     * @Desc: 批量新增计泡方式
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Integer> add(List<BulkyWayPo> bulkyWayPoList);

    /**
     * 获取计泡方式详情
     * @param id
     * @return
     */
    ResultUtil<BulkyWayUpdDto> get(Long id);
}
