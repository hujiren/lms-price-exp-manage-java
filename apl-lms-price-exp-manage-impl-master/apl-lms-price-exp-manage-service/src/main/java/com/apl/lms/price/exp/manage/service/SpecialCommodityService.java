package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.common.query.manage.dto.*;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityDto;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityInsertDto;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityKeyDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 航空公司 service接口
 * </p>
 *
 * @author hjr
 * @since 2020-08-08
 */
public interface SpecialCommodityService extends IService<SpecialCommodityDto> {


        /**
         * @Desc: 分页查找 特殊物品 列表
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Page<SpecialCommodityDto>>getList(PageDto pageDto, SpecialCommodityKeyDto specialCommodityKeyDto);

        /**
         * @Desc: 根据Id删除特殊物品
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Boolean> delSpecialCommodity(Long id);


        /**
         * @Desc: 更新特殊物品
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Boolean> updSpecialCommodity(SpecialCommodityDto specialCommodityDto);


        /**
         * @Desc: 新增特殊物品
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Long> addSpecialCommodity(SpecialCommodityInsertDto specialCommodityInsertDto);


}
