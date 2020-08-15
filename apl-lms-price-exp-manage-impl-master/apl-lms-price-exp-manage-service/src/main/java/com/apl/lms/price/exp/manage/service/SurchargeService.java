package com.apl.lms.price.exp.manage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.pojo.dto.SurchargeDto;
import com.apl.lms.price.exp.pojo.dto.SurchargeInsertDto;
import com.apl.lms.price.exp.pojo.dto.SurchargeKeyDto;
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
public interface SurchargeService extends IService<SurchargeDto> {

        /**
         * @Desc: 分页查找 附加费列表
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Page<SurchargeDto>>getList(PageDto pageDto, SurchargeKeyDto surchargeKeyDto);

        /**
         * @Desc: 根据Id删除附加费
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Boolean> delSurcharge(Long id);

        /**
         * @Desc: 更新附加费
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Boolean> updSurcharge(SurchargeDto surchargeDto);

        /**
         * @Desc: 批量新增新增附加费
         * @author hjr
         * @since 2020-08-08
         */
        ResultUtil<Integer> addSurcharge(List<SurchargeInsertDto> surchargeInsertDtoList);

}
