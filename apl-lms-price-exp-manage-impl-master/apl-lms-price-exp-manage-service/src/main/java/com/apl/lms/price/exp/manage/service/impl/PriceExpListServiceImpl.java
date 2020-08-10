package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.PriceExpListMapper;
import com.apl.lms.price.exp.manage.service.PriceExpListService;
import com.apl.lms.price.exp.pojo.dto.PriceExpListDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpListInsertDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpListKeyDto;
import com.apl.lms.price.exp.pojo.po.PriceExpListPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Service
@Slf4j
public class PriceExpListServiceImpl extends ServiceImpl<PriceExpListMapper, PriceExpListPo> implements PriceExpListService {

    enum ExpListServiceCode {
        ;

        private String code;
        private String msg;

        ExpListServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 分页查询快递价格
     * @param pageDto
     * @param priceExpListKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<PriceExpListVo>> getExpList(PageDto pageDto, PriceExpListKeyDto priceExpListKeyDto) {

        Page<PriceExpListVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        List<PriceExpListVo> priceExpListVoList = baseMapper.getExpList(page, priceExpListKeyDto);

        page.setRecords(priceExpListVoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据ids批量删除快递价格
     * @param ids
     * @return
     */
    @Override
    public ResultUtil<Boolean> delExpList(List<Long> ids) {

        Integer integer = baseMapper.delById(ids);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 更新快递价格
     * @param priceExpListDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updExpList(PriceExpListDto priceExpListDto) {

        PriceExpListPo priceExpListPo = new PriceExpListPo();
        priceExpListPo.setAccountNo(priceExpListDto.getAccountNo());
        priceExpListPo.setAging(priceExpListDto.getAging());
        priceExpListPo.setAccountType(priceExpListDto.getAccountType());
        priceExpListPo.setChannelCategory(priceExpListDto.getChannelCategory());
        priceExpListPo.setCurrency(priceExpListDto.getCurrency());
        priceExpListPo.setEndDate(new Timestamp(priceExpListDto.getEndDate()));
        priceExpListPo.setForwarderId(priceExpListDto.getForwarderId());
        priceExpListPo.setForwarderRemark(priceExpListDto.getForwarderRemark());
        priceExpListPo.setPriceCode(priceExpListDto.getPriceCode());
        priceExpListPo.setPriceForm(null);
        priceExpListPo.setPriceName(priceExpListDto.getPriceName());
        priceExpListPo.setPriceStatus(priceExpListDto.getPriceStatus());
        priceExpListPo.setQuotePriceFinalId(null);
        priceExpListPo.setQuotePriceId(null);
        priceExpListPo.setSaleRemark(priceExpListDto.getSaleRemark());
        priceExpListPo.setStartDate(new Timestamp(priceExpListDto.getStartDate()));
        priceExpListPo.setVolumeWeightCardinal(priceExpListDto.getVolumeWeightCardinal());
        priceExpListPo.setZoneTabId(priceExpListDto.getZoneTabId());
        priceExpListPo.setCustomerGroupsName(priceExpListDto.getCustomerGroupsName());
        priceExpListPo.setCustomerIdsName(priceExpListDto.getCustomerIdsName());

        priceExpListPo.setSpecialCommodity(priceExpListDto.getSpecialCommodity());

        List<String> stringList = new ArrayList<>();
        List<String> stringList2 = new ArrayList<>();

        List<Long> customerGroupsId = priceExpListDto.getCustomerGroupsId();
        for (Long aLong : customerGroupsId) {
            stringList.add(aLong.toString());
        }

        List<Long> customerIds = priceExpListDto.getCustomerIds();
        for (Long customerId : customerIds) {
            stringList2.add(customerId.toString());
        }
        priceExpListPo.setCustomerGroupsId(stringList);
        priceExpListPo.setCustomerIds(stringList2);
        priceExpListPo.setId(SnowflakeIdWorker.generateId());
        Integer integer = baseMapper.updExpList(priceExpListPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 新增快递价格
     * @param priceExpListInsertDto
     * @return
     */
    @Override
    public ResultUtil<Long> insExpList(PriceExpListInsertDto priceExpListInsertDto) {

        PriceExpListPo priceExpListPo = new PriceExpListPo();
        priceExpListPo.setAccountNo(priceExpListInsertDto.getAccountNo());
        priceExpListPo.setAging(priceExpListInsertDto.getAging());
        priceExpListPo.setAccountType(priceExpListInsertDto.getAccountType());
        priceExpListPo.setChannelCategory(priceExpListInsertDto.getChannelCategory());
        priceExpListPo.setCurrency(priceExpListInsertDto.getCurrency());
        priceExpListPo.setEndDate(new Timestamp(priceExpListInsertDto.getEndDate()));
        priceExpListPo.setForwarderId(priceExpListInsertDto.getForwarderId());
        priceExpListPo.setForwarderRemark(priceExpListInsertDto.getForwarderRemark());
        priceExpListPo.setPriceCode(priceExpListInsertDto.getPriceCode());
        priceExpListPo.setPriceForm(null);
        priceExpListPo.setPriceName(priceExpListInsertDto.getPriceName());
        priceExpListPo.setPriceStatus(priceExpListInsertDto.getPriceStatus());
        priceExpListPo.setQuotePriceFinalId(null);
        priceExpListPo.setQuotePriceId(null);
        priceExpListPo.setSaleRemark(priceExpListInsertDto.getSaleRemark());
        priceExpListPo.setStartDate(new Timestamp(priceExpListInsertDto.getStartDate()));
        priceExpListPo.setVolumeWeightCardinal(priceExpListInsertDto.getVolumeWeightCardinal());
        priceExpListPo.setZoneTabId(priceExpListInsertDto.getZoneTabId());
        priceExpListPo.setCustomerGroupsName(priceExpListInsertDto.getCustomerGroupsName());
        priceExpListPo.setCustomerIdsName(priceExpListInsertDto.getCustomerIdsName());

        priceExpListPo.setSpecialCommodity(priceExpListInsertDto.getSpecialCommodity());

        List<String> stringList = new ArrayList<>();
        List<String> stringList2 = new ArrayList<>();

        List<Long> customerGroupsId = priceExpListInsertDto.getCustomerGroupsId();
        for (Long aLong : customerGroupsId) {
            stringList.add(aLong.toString());
        }

        List<Long> customerIds = priceExpListInsertDto.getCustomerIds();
        for (Long customerId : customerIds) {
            stringList2.add(customerId.toString());
        }
        priceExpListPo.setCustomerGroupsId(stringList);
        priceExpListPo.setCustomerIds(stringList2);
        priceExpListPo.setId(SnowflakeIdWorker.generateId());

        Integer integer = baseMapper.insertExpList(priceExpListPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpListPo.getId());
    }
}
