package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.SpecialCommodityMapper;
import com.apl.lms.price.exp.manage.service.SpecialCommodityService;
import com.apl.lms.price.exp.pojo.dto.SpecialCommodityKeyDto;
import com.apl.lms.price.exp.pojo.po.SpecialCommodityPo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author hjr
 * @since 2020-08-08
 */
@Service
@Slf4j
public class SpecialCommodityServiceImpl extends ServiceImpl<SpecialCommodityMapper, SpecialCommodityPo> implements SpecialCommodityService {

    enum SpecialCommodityServiceCode {
        ID_DOES_NOT_EXITS("ID_DOES_NOT_EXITS", "id不存在");
        ;

        private String code;
        private String msg;

        SpecialCommodityServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 分页查找
     * @param specialCommodityKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<SpecialCommodityPo>> getList(PageDto pageDto, SpecialCommodityKeyDto specialCommodityKeyDto) {

        Page<SpecialCommodityPo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        if(null == specialCommodityKeyDto.getCode() || specialCommodityKeyDto.getCode() < 0){
            specialCommodityKeyDto.setCode(0);
        }
        List<SpecialCommodityPo> specialCommodityVoList = baseMapper.getList(page, specialCommodityKeyDto);

        page.setRecords(specialCommodityVoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据id删除特殊物品
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delSpecialCommodity(Long id) {
        Integer integer = baseMapper.deleteById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL.code, SpecialCommodityServiceCode.ID_DOES_NOT_EXITS.msg, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 批量添加特殊物品
     * @param specialCommodityPoList
     * @return
     */
    @Override
    public ResultUtil<Integer> addSpecialCommodity(List<SpecialCommodityPo> specialCommodityPoList) {

        for (SpecialCommodityPo specialCommodityPo : specialCommodityPoList) {
            specialCommodityPo.setId(SnowflakeIdWorker.generateId());
        }

        Integer integer = baseMapper.addSpecialCommodity(specialCommodityPoList);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, integer);
    }

    /**
     * 获取特殊物品详细
     * @param id
     * @return
     */
    @Override
    public ResultUtil<SpecialCommodityPo> getSpecialCommodity(Long id) {

        SpecialCommodityPo specialCommodityPo = baseMapper.getSpecialCommodity(id);
        if(specialCommodityPo == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, specialCommodityPo);
    }
}
