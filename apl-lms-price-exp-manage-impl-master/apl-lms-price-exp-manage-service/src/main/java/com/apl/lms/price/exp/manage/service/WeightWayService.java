package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.WeightWayDto;
import com.apl.lms.price.exp.pojo.dto.WeightWayInsertDto;
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
public interface WeightWayService extends IService<WeightWayDto> {

        /**
         * @Desc: 分页查找 计泡方式列表
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Page<WeightWayDto>> getList(PageDto pageDto, WeightWayKeyDto weightWayKeyDto);

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
        ResultUtil<Boolean> updWeightWay(WeightWayDto weightWayDto);

        /**
         * @Desc: 批量新增计泡方式
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Integer> addWeightWay(List<WeightWayInsertDto> weightWayInsertDtoList);

}
