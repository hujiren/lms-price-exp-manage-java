package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.WeightWayUpdDto;
import com.apl.lms.price.exp.pojo.dto.WeightWayAddDto;
import com.apl.lms.price.exp.pojo.dto.WeightWayKeyDto;
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
public interface WeightWayService extends IService<WeightWayUpdDto> {

    /**
     * @Desc: 分页查找 计泡方式列表
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Page<WeightWayUpdDto>> getList(PageDto pageDto, WeightWayKeyDto weightWayKeyDto);

    /**
     * @Desc: 根据Id删除计泡方式
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Boolean> delWeightWay(Long id);

    /**
     * @Desc: 更新计泡方式
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Boolean> updWeightWay(WeightWayUpdDto weightWayUpdDto);

    /**
     * @Desc: 批量新增计泡方式
     * @author hjr
     * @since 2020-08-08
     */
    ResultUtil<Integer> addWeightWay(List<WeightWayAddDto> weightWayAddDtoList);

    /**
     * 获取计泡方式详情
     * @param id
     * @return
     */
    ResultUtil<WeightWayUpdDto> getWeightWay(Long id);
}
