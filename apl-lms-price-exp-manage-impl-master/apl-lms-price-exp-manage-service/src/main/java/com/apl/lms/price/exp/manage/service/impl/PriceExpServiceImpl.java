package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.PriceExpMapper;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.pojo.dto.PriceExpListDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpListInsertDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpListKeyDto;
import com.apl.lms.price.exp.pojo.entity.Customer;
import com.apl.lms.price.exp.pojo.entity.CustomerGroupInfo;
import com.apl.lms.price.exp.pojo.po.PriceExpListPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpInfoVo;
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
public class PriceExpServiceImpl extends ServiceImpl<PriceExpMapper, PriceExpListPo> implements PriceExpService {

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
    public ResultUtil<Page<PriceExpListVo>> getPriceExpList(PageDto pageDto, PriceExpListKeyDto priceExpListKeyDto) {

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
    public ResultUtil<Boolean> delPriceExp(List<Long> ids) {

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
    public ResultUtil<Boolean> updPriceExp(PriceExpListDto priceExpListDto) {

        Long id = baseMapper.getExpListById(priceExpListDto.getId());
        if(id == null){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL.code, "id不存在", null);
        }

        PriceExpListPo priceExpListPo = new PriceExpListPo();
        priceExpListPo.setId(priceExpListDto.getId());
        priceExpListPo.setPriceCode(priceExpListDto.getPriceCode());
        priceExpListPo.setPriceName(priceExpListDto.getPriceName());
        priceExpListPo.setSaleName(priceExpListDto.getSaleName());
        priceExpListPo.setStartDate(new Timestamp(priceExpListDto.getStartDate()));
        priceExpListPo.setEndDate(new Timestamp(priceExpListDto.getEndDate()));
        priceExpListPo.setCurrency(priceExpListDto.getCurrency());
        priceExpListPo.setChannelCategory(priceExpListDto.getChannelCategory());
        priceExpListPo.setZoneTabId(priceExpListDto.getZoneTabId());
        priceExpListPo.setVolumeWeightCardinal(priceExpListDto.getVolumeWeightCardinal());
        priceExpListPo.setAccountType(priceExpListDto.getAccountType());
        priceExpListPo.setAccountNo(priceExpListDto.getAccountNo());
        priceExpListPo.setCustomerGroupsId( priceExpListDto.getCustomerGroupsId());
        priceExpListPo.setCustomerGroupsName(priceExpListDto.getCustomerGroupsName());
        priceExpListPo.setCustomerIds(priceExpListDto.getCustomerIds());
        priceExpListPo.setCustomerName(priceExpListDto.getCustomerName());
        priceExpListPo.setForwarderId(priceExpListDto.getForwarderId());
        priceExpListPo.setPriceStatus(priceExpListDto.getPriceStatus());
        priceExpListPo.setAging(priceExpListDto.getAging());
        priceExpListPo.setSpecialCommodity(priceExpListDto.getSpecialCommodity());
        priceExpListPo.setSaleRemark(priceExpListDto.getSaleRemark());
        priceExpListPo.setForwarderRemark(priceExpListDto.getForwarderRemark());

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
    public ResultUtil<Long> insPriceExp(PriceExpListInsertDto priceExpListInsertDto) {

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
        priceExpListPo.setPriceName(priceExpListInsertDto.getPriceName());
        priceExpListPo.setPriceStatus(priceExpListInsertDto.getPriceStatus());
        priceExpListPo.setSaleRemark(priceExpListInsertDto.getSaleRemark());
        priceExpListPo.setStartDate(new Timestamp(priceExpListInsertDto.getStartDate()));
        priceExpListPo.setVolumeWeightCardinal(priceExpListInsertDto.getVolumeWeightCardinal());
        priceExpListPo.setZoneTabId(priceExpListInsertDto.getZoneTabId());
        priceExpListPo.setCustomerGroupsName(priceExpListInsertDto.getCustomerGroupsName());
        priceExpListPo.setCustomerName(priceExpListInsertDto.getCustomerName());
        priceExpListPo.setSpecialCommodity(priceExpListInsertDto.getSpecialCommodity());
        priceExpListPo.setCustomerIds(priceExpListInsertDto.getCustomerIds());
        priceExpListPo.setCustomerGroupsId( priceExpListInsertDto.getCustomerGroupsId());
        priceExpListPo.setId(SnowflakeIdWorker.generateId());
        priceExpListPo.setSaleName(priceExpListInsertDto.getSaleName());
        priceExpListPo.setPriceForm(0);
        priceExpListPo.setQuotePriceFinalId(0);
        priceExpListPo.setQuotePriceId(0);
        Integer integer = baseMapper.insertExpList(priceExpListPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpListPo.getId());
    }

    /**
     * 获取快递价格详情
     * @param id
     * @return
     */
    @Override
    public ResultUtil<PriceExpInfoVo> getPriceExpInfo(Long id) {

        PriceExpInfoVo priceExpInfoVo = baseMapper.getExpListInfo(id);

        priceExpInfoVo.setCustomerGroupsId(priceExpInfoVo.getCustomerGroupsId().replace("[", "").replace("]", "")
        .replaceAll(" ",""));

        priceExpInfoVo.setCustomerIds(priceExpInfoVo.getCustomerIds().replace("[", "").replace("]", "")
        .replaceAll(" ",""));

        priceExpInfoVo.setSpecialCommodity(priceExpInfoVo.getSpecialCommodity().replace("[", "").replace("]", "")
        .replaceAll(" ",""));

        if(priceExpInfoVo == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.getCode(), "id不正确", null);
        }

        if (priceExpInfoVo.getCustomerIds() != null && priceExpInfoVo.getCustomerName() != null) {
            String customerIds = priceExpInfoVo.getCustomerIds();
            String customerNames = priceExpInfoVo.getCustomerName();

            List<Customer> customerList = new ArrayList<>();

            String[] customerIdArr = customerIds.split(",");
            String[] customerNameArr = customerNames.split(",");
            for(int i = 0; i < customerIdArr.length; i++){
                Customer customer = new Customer();
                customer.setCustomerId(Long.valueOf(customerIdArr[i]));
                customer.setCustomerName(customerNameArr[i]);
                customerList.add(customer);
            }
            priceExpInfoVo.setCustomers(customerList);
        }

        if (priceExpInfoVo.getCustomerGroupsId() != null && priceExpInfoVo.getCustomerGroupsName() != null) {
            String customerGroupsIds = priceExpInfoVo.getCustomerGroupsId();
            String customerGroupsName = priceExpInfoVo.getCustomerGroupsName();

            List<CustomerGroupInfo> customerGroupInfoList = new ArrayList<>();

            String[] customerGroupIdArr = customerGroupsIds.split(",");
            String[] customerGroupNameArr = customerGroupsName.split(",");

            for (int i = 0; i < customerGroupIdArr.length; i++) {
                CustomerGroupInfo customerGroupInfo = new CustomerGroupInfo();
                customerGroupInfo.setCustomerGroupsId(Long.valueOf(customerGroupIdArr[i]));
                customerGroupInfo.setCustomerGroupsName(customerGroupNameArr[i]);
                customerGroupInfoList.add(customerGroupInfo);
            }
            priceExpInfoVo.setCustomerGroupInfo(customerGroupInfoList);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpInfoVo);
    }
}
