package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.PriceExpMapper;
import com.apl.lms.price.exp.manage.service.*;
import com.apl.lms.price.exp.manage.util.CheckObjFieldINull;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.entity.Customer;
import com.apl.lms.price.exp.pojo.entity.CustomerGroupInfo;
import com.apl.lms.price.exp.pojo.entity.PriceListForDelBatch;
import com.apl.lms.price.exp.pojo.entity.RelevanceForMainData;
import com.apl.lms.price.exp.pojo.po.*;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Service
@Slf4j
public class PriceExpServiceImpl extends ServiceImpl<PriceExpMapper, PriceExpMainPo> implements PriceExpService {

    enum ExpListServiceCode {
        PRICE_EXP_MAIN_SAVE_DATA_FAILED("PRICE_EXP_MAIN_SAVE_DATA_FAILED", "保存价格主表失败"),
        PRICE_EXP_SALE_SAVE_DATA_FAILED("PRICE_EXP_SALE_SAVE_DATA_FAILED", "保存销售价格表失败"),
        PRICE_EXP_COST_SAVE_DATA_FAILED("PRICE_EXP_COST_SAVE_DATA_FAILED", "保存成本价格表失败"),
        PRICE_EXP_AXIS_SAVE_DATA_FAILED("PRICE_EXP_AXIS_SAVE_DATA_FAILED", "保存价格表轴数据失败"),
        PRICE_EXP_DATA_SAVE_DATA_FAILED("PRICE_EXP_DATA_SAVE_DATA_FAILED", "保存价格表数据失败"),
        price_exp_remark_SAVE_DATA_FAILED("price_exp_remark_SAVE_DATA_FAILED", "保存价格表扩展数据失败"),
        ID_IS_NOT_EXITS("ID_IS_NOT_EXITS", "id不存在"),
        CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH("CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH", "客户id和客户名称不匹配"),
        THERE_IS_NO_CORRESPONDING_DATA_FOR_THE_MAIN_TABLE("THERE_IS_NO_CORRESPONDING_DATA_FOR_THE_MAIN_TABLE", "主表没有对应数据"),
        PARTNER_MUST_BE_NOT_ZERO("PARTNER_MUST_BE_NOT_ZERO", "服务商id不能为0"),
        PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP(
                "PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP",
                "服务商, 客户组, 客户, 请至少填写一组"),
        THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE("THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE", "仍有其他数据绑定主表");

        private String code;
        private String msg;

        ExpListServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Autowired
    PriceExpDataService priceExpDataService;

    @Autowired
    PriceExpRemarkService priceExpRemarkService;

    @Autowired
    PriceExpAxisService priceExpAxisService;

    @Autowired
    PriceExpCostService priceExpCostService;

    @Autowired
    PriceExpSaleService priceExpSaleService;

    @Autowired
    DataSource dataSource;

    @Autowired
    ComputationalFormulaService computationalFormulaService;

    @Autowired
    CheckObjFieldINull checkObjFieldINull;

    /**
     * 分页查询销售价格列表
     *
     * @param pageDto
     * @param keyDto
     * @return
     */
    @Override
    public ResultUtil<Page<PriceExpSaleListVo>> getPriceExpSaleList(PageDto pageDto, PriceExpSaleListKeyDto keyDto) {
        Page<PriceExpSaleListVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        List<PriceExpSaleListVo> priceExpListVoSaleList = baseMapper.getPriceExpSaleList(page, keyDto);

        page.setRecords(priceExpListVoSaleList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }


    /**
     * 分页查询成本价格列表
     *
     * @param pageDto
     * @param keyDto
     * @return
     */
    @Override
    public ResultUtil<Page<PriceExpCostListVo>> getPriceExpCostList(PageDto pageDto, PriceExpCostKeyDto keyDto) {
        Page<PriceExpCostListVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        List<PriceExpCostListVo> priceExpListVoCostList = baseMapper.getPriceExpCostList(page, keyDto);

        page.setRecords(priceExpListVoCostList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }


    /**
     * 分页查询成本价格列表
     *
     * @param pageDto
     * @param keyDto
     * @return
     */
    @Override
    public ResultUtil<Page<PriceExpCostListVo>> getPublishedPriceList(PageDto pageDto, PriceExpPublishedKeyDto keyDto){
        Page<PriceExpCostListVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        List<PriceExpCostListVo> priceExpListVoCostList = baseMapper.getPublishedPriceList(page, keyDto);

        page.setRecords(priceExpListVoCostList);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 获取销售价格详情
     *
     * @param id 销售价格表主键Id
     * @return
     */
    @Override
    public ResultUtil<PriceExpSaleInfoVo> getPriceExpSaleInfo(Long id) {

        //查询销售价格表并组装
        PriceExpSaleVo priceExpSaleVo = priceExpSaleService.getPriceExpSaleInfoById(id);

        if (priceExpSaleVo == null) {
            throw new AplException(ExpListServiceCode.ID_IS_NOT_EXITS.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg, null);
        }

        //查询主表
        PriceExpSaleInfoVo priceExpSaleInfoVo = baseMapper.getPriceExpMainInfoById(priceExpSaleVo.getPriceMainId());
        if (priceExpSaleInfoVo == null) {
            throw new AplException(ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA_FOR_THE_MAIN_TABLE.code,
                    ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA_FOR_THE_MAIN_TABLE.msg, null);
        }
        priceExpSaleInfoVo.setMainId(priceExpSaleVo.getPriceMainId());
        priceExpSaleInfoVo.setId(priceExpSaleVo.getId());
        priceExpSaleInfoVo.setSpecialCommodity(priceExpSaleInfoVo.getSpecialCommodity().replace("[", "").replace("]", "")
                .replaceAll(" ", ""));
        priceExpSaleInfoVo.setPriceCode(priceExpSaleVo.getPriceCode());
        priceExpSaleInfoVo.setPriceName(priceExpSaleVo.getPriceName());
        priceExpSaleInfoVo.setPriceStatus(priceExpSaleVo.getPriceStatus());
        priceExpSaleInfoVo.setChannelCategory(priceExpSaleVo.getChannelCategory());
        priceExpSaleInfoVo.setCustomerGroupsName(priceExpSaleVo.getCustomerGroupsName());
        priceExpSaleInfoVo.setCustomerName(priceExpSaleVo.getCustomerName());

        priceExpSaleInfoVo.setCustomerGroupsId(priceExpSaleVo.getCustomerGroupsId().replace("[", "").replace("]", "")
                .replaceAll(" ", ""));

        priceExpSaleInfoVo.setCustomerIds(priceExpSaleVo.getCustomerIds().replace("[", "").replace("]", "")
                .replaceAll(" ", ""));

        //组装客户List (客户id, 客户名称)
        if (priceExpSaleVo.getCustomerIds() != null && priceExpSaleVo.getCustomerName() != null) {
            String customerIds = priceExpSaleVo.getCustomerIds().replace("[", "").replace("]", "").replaceAll(" ", "");
            String customerNames = priceExpSaleVo.getCustomerName().replace("[", "").replace("]", "").replaceAll(" ", "");
            ;

            List<Customer> customerList = new ArrayList<>();

            String[] customerIdArr = customerIds.split(",");
            String[] customerNameArr = customerNames.split(",");
            for (int i = 0; i < customerIdArr.length; i++) {
                Customer customer = new Customer();
                customer.setCustomerId(Long.valueOf(customerIdArr[i]));
                customer.setCustomerName(customerNameArr[i]);
                customerList.add(customer);
            }
            priceExpSaleInfoVo.setCustomers(customerList);
        }
        //组装客户组List(客户组id, 客户组名称)
        if (priceExpSaleVo.getCustomerGroupsId() != null && priceExpSaleVo.getCustomerGroupsName() != null) {
            String customerGroupsIds = priceExpSaleVo.getCustomerGroupsId().replace("[", "").replace("]", "").replaceAll(" ", "");

            String customerGroupsName = priceExpSaleVo.getCustomerGroupsName().replace("[", "").replace("]", "").replaceAll(" ", "");


            List<CustomerGroupInfo> customerGroupInfoList = new ArrayList<>();

            String[] customerGroupIdArr = customerGroupsIds.split(",");
            String[] customerGroupNameArr = customerGroupsName.split(",");

            for (int i = 0; i < customerGroupIdArr.length; i++) {
                CustomerGroupInfo customerGroupInfo = new CustomerGroupInfo();
                customerGroupInfo.setCustomerGroupsId(Long.valueOf(customerGroupIdArr[i]));
                customerGroupInfo.setCustomerGroupsName(customerGroupNameArr[i]);
                customerGroupInfoList.add(customerGroupInfo);
            }
            priceExpSaleInfoVo.setCustomerGroupInfo(customerGroupInfoList);
        }


        //获取扩展信息并组装
        PriceExpRemarkPo priceExpRemarkPo = priceExpRemarkService.getDevelopInfoById(id);
        if (priceExpRemarkPo != null) {
            priceExpSaleInfoVo.setRemark(priceExpRemarkPo.getRemark());
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpSaleInfoVo);
    }

    /**
     * 获取成本价格详情
     *
     * @param id 成本价主键id
     * @return
     */
    @Override
    public ResultUtil<PriceExpCostInfoVo> getPriceExpCostInfo(Long id) {

        PriceExpCostInfoVo priceExpCostInfoVo = new PriceExpCostInfoVo();

        //查询成本价格表并组装
        PriceExpCostVo priceExpCostVo = priceExpCostService.getPriceExpCostInfo(id);

        if (priceExpCostVo == null) {
            ResultUtil.APPRESULT(ExpListServiceCode.ID_IS_NOT_EXITS.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg, null);
        }

        //查询主表  用priceExpSaleInfoVo接收是为了复用方法
        PriceExpSaleInfoVo priceExpSaleInfoVo = baseMapper.getPriceExpMainInfoById(priceExpCostVo.getPriceMainId());

        if (priceExpSaleInfoVo != null) {

            BeanUtil.copyProperties(priceExpSaleInfoVo, priceExpCostInfoVo);
            if(priceExpCostInfoVo.getSpecialCommodity() != null)
            priceExpCostInfoVo.setSpecialCommodity(priceExpCostInfoVo.getSpecialCommodity().replace("[", "").replace("]", "")
                    .replaceAll(" ", ""));
        }

        priceExpCostInfoVo.setPartnerId(priceExpCostVo.getPartnerId());
        priceExpCostInfoVo.setPriceStatus(priceExpCostVo.getPriceStatus());
        priceExpCostInfoVo.setPriceCode(priceExpCostVo.getPriceCode());
        priceExpCostInfoVo.setPriceName(priceExpCostVo.getPriceName());
        priceExpCostInfoVo.setChannelCategory(priceExpCostVo.getChannelCategory());
        priceExpCostInfoVo.setMainId(priceExpCostVo.getPriceMainId());
        priceExpCostInfoVo.setId(priceExpCostVo.getId());
        //获取扩展信息并组装
        PriceExpRemarkPo priceExpRemarkPo = priceExpRemarkService.getDevelopInfoById(id);
        if (priceExpRemarkPo != null) {
            priceExpCostInfoVo.setRemark(priceExpRemarkPo.getRemark());
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpCostInfoVo);
    }

    /**
     * 更新销售价格
     *
     * @param priceExpMainUpdDto
     * @param priceExpSaleUpdateDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updateSalePrice(PriceExpMainUpdDto priceExpMainUpdDto,
                                               PriceExpSaleUpdateDto priceExpSaleUpdateDto) {

        String[] customerName = null;
        String[] customerGroup = null;

        //Boolean isNull = checkObjFieldINull.checkObjFieldIsNull(priceExpSaleAddDto);
        //校验客户id与客户是否匹配
        if (priceExpSaleUpdateDto.getCustomerGroupsId() != null && priceExpSaleUpdateDto.getCustomerGroupsId().size() > 0) {

            //校验客户组id与客户组是否匹配
            customerGroup = priceExpSaleUpdateDto.getCustomerGroupsName().split(",");
            if (priceExpSaleUpdateDto.getCustomerGroupsId().size() != customerGroup.length) {

                return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                        ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);
            }
        } else {
            priceExpSaleUpdateDto.setCustomerGroupsId(null);
            priceExpSaleUpdateDto.setCustomerGroupsName(null);
        }

        //校验客户id与客户是否匹配
        if (priceExpSaleUpdateDto.getCustomerIds() != null && priceExpSaleUpdateDto.getCustomerIds().size() > 0) {

            customerName = priceExpSaleUpdateDto.getCustomerName().split(",");
            if (priceExpSaleUpdateDto.getCustomerIds().size() != customerName.length) {

                return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                        ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);

            }
        } else {
            priceExpSaleUpdateDto.setCustomerIds(null);
            priceExpSaleUpdateDto.setCustomerName(null);
        }

        //不是销售价且不是客户价
        if (null == priceExpSaleUpdateDto.getCustomerGroupsId()
                && null == priceExpSaleUpdateDto.getCustomerIds()) {

            return ResultUtil.APPRESULT(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg, null);
        }

        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        Long innerOrgId = baseMapper.getInnerOrgById(priceExpSaleUpdateDto.getPriceMainId());

        Boolean saveSuccess = false;
        if (innerOrgId == securityUser.getInnerOrgId()) {
            //更新主表
            PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
            BeanUtil.copyProperties(priceExpMainUpdDto, priceExpMainPo);
            priceExpMainPo.setId(priceExpSaleUpdateDto.getPriceMainId());
            priceExpMainPo.setIsPublishedPrice(null);
            Integer integer = baseMapper.updateMainById(priceExpMainPo);
            if (integer < 1) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
            }
        }

        //更新销售价格表
        PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
        BeanUtil.copyProperties(priceExpSaleUpdateDto, priceExpSalePo);
        saveSuccess = priceExpSaleService.updateSaleById(priceExpSalePo);
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.msg, null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 更新成本价格表
     *
     * @param priceExpMainUpdDto
     * @param priceExpCostUpdDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updateCostPrice(PriceExpMainUpdDto priceExpMainUpdDto,
                                               PriceExpCostUpdDto priceExpCostUpdDto) {

        if (priceExpMainUpdDto.getIsPublishedPrice() != 1 && priceExpCostUpdDto.getPartnerId() < 1) {
            //不是公布价且服务商id小于1
            throw new AplException(ExpListServiceCode.PARTNER_MUST_BE_NOT_ZERO.code,
                    ExpListServiceCode.PARTNER_MUST_BE_NOT_ZERO.msg, null);
        }
        if (priceExpMainUpdDto.getIsPublishedPrice() == 1)
            priceExpCostUpdDto.setPartnerId(0l);

        Boolean saveSuccess = false;
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        Long innerOrgId = baseMapper.getInnerOrgById(priceExpCostUpdDto.getPriceMainId());
        if (innerOrgId == securityUser.getInnerOrgId()) {
            //更新主表
            PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
            BeanUtil.copyProperties(priceExpMainUpdDto, priceExpMainPo);
            priceExpMainPo.setId(priceExpCostUpdDto.getPriceMainId());
            Integer integer = baseMapper.updateMainById(priceExpMainPo);
            if (integer < 1) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
            }
        }

        //更新成本价格表
        PriceExpCostPo priceExpCostPo = new PriceExpCostPo();
        BeanUtil.copyProperties(priceExpCostUpdDto, priceExpCostPo);
        if (innerOrgId != securityUser.getInnerOrgId()) {
            priceExpCostPo.setPriceMainId(null);
        }
        saveSuccess = priceExpCostPo.updateById();
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.msg, null);
        }


        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 更新价格表数据
     *
     * @param priceExpDataAddDto
     * @param priceExpAxisPo
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updatePriceData(PriceExpDataAddDto priceExpDataAddDto, PriceExpAxisPo priceExpAxisPo) {

        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        Long innerOrgId = baseMapper.getInnerOrgById(priceExpAxisPo.getId());

        if (innerOrgId == securityUser.getInnerOrgId()) {

            PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
            priceExpDataPo.setPriceData(priceExpDataAddDto.getPriceData());
            priceExpDataPo.setId(priceExpAxisPo.getId());
            Boolean result = priceExpDataService.updById(priceExpDataPo);
            if (!result) {
                throw new AplException(CommonStatusCode.SYSTEM_FAIL, null);
            }
            Boolean res = priceExpAxisService.updateByMainId(priceExpAxisPo);
            if (!res) {
                throw new AplException(CommonStatusCode.SYSTEM_FAIL, null);
            }
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }



    /**
     * 新增快递价格
     *
     * @param priceExpMainAddDto
     * @param priceExpSaleAddDto
     * @param priceExpCostAddDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> addExpPrice(PriceExpMainAddDto priceExpMainAddDto,
                                        PriceExpCostAddDto priceExpCostAddDto,
                                        PriceExpSaleAddDto priceExpSaleAddDto,
                                        PriceExpAxisAddDto priceExpAxisAddDto,
                                        PriceExpDataAddDto priceExpDataAddDto) {

        String[] customerName = null;
        String[] customerGroup = null;

        //校验客户id与客户是否匹配
        if (null != priceExpSaleAddDto.getCustomerGroupsId()) {

            //校验客户组id与客户组是否匹配
            customerGroup = priceExpSaleAddDto.getCustomerGroupsName().split(",");

            if (!(customerGroup[0].equals("") && priceExpSaleAddDto.getCustomerGroupsId().size() == 0)) {

                if (priceExpSaleAddDto.getCustomerGroupsId().size() != customerGroup.length) {

                    return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                            ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);
                }
            }
        } else {
            priceExpSaleAddDto.setCustomerGroupsId(null);
            priceExpSaleAddDto.setCustomerGroupsName(null);
        }

        //校验客户id与客户是否匹配
        if (null != priceExpSaleAddDto.getCustomerIds()) {

            customerName = priceExpSaleAddDto.getCustomerName().split(",");
            if(!(customerName[0].equals("") && priceExpSaleAddDto.getCustomerIds().size() == 0)) {
                if (priceExpSaleAddDto.getCustomerIds().size() != customerName.length) {

                    return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                            ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);
                }
            }
        } else {
            priceExpSaleAddDto.setCustomerIds(null);
            priceExpSaleAddDto.setCustomerName(null);
        }

        //不是公布价且不是成本价且不是销售价
        if (priceExpMainAddDto.getIsPublishedPrice() == 2
                && priceExpCostAddDto.getPartnerId() < 1
                && 0 == priceExpSaleAddDto.getCustomerGroupsId().size()
                && 0 == priceExpSaleAddDto.getCustomerIds().size()) {

            return ResultUtil.APPRESULT(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg, null);
        }

        Boolean saveSuccess = false;

        //数据表Id & 主表Id
        Long priceDataId  = SnowflakeIdWorker.generateId();
        Long priceMainId = SnowflakeIdWorker.generateId();

        //安全用户
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();

        //保存价格主表数据

        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpMainAddDto, priceExpMainPo);
        priceExpMainPo.setId(priceMainId);
        priceExpMainPo.setPriceDataId(priceDataId);
        priceExpMainPo.setInnerOrgId(securityUser.getInnerOrgId());
        priceExpMainPo.setMainStatus(1);
        save(priceExpMainPo);

//        saveSuccess = priceExpMainPo.insert();
//        if (!saveSuccess) {
//            throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
//                    ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
//        }
        Integer integer = baseMapper.addPriceExpMain(priceExpMainPo);
        if(integer<1){
            throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
        }
        if (priceExpMainAddDto.getIsPublishedPrice() == 1) {
            priceExpCostAddDto.setPartnerId(0L);
        }


        Long priceId = 0l;
        if (priceExpMainAddDto.getIsPublishedPrice() == 1 || priceExpCostAddDto.getPartnerId() > 0) {
            //公布价(IsPublishedPrice=1) 或 成本价(有服务商)
            priceId = SnowflakeIdWorker.generateId();
            saveSuccess = priceExpCostService.addPriceExpCost(priceMainId, priceExpCostAddDto, priceId);
            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_COST_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_COST_SAVE_DATA_FAILED.msg, null);
            }


            //保存计算公式
            PriceExpComputationalFormulaPo computationalFormulaPo = new PriceExpComputationalFormulaPo();
            computationalFormulaPo.setPriceId(priceId);
            computationalFormulaPo.setFormula("price()*fuel");
            computationalFormulaPo.setZoneNum("");
            computationalFormulaPo.setCountry("");
            computationalFormulaPo.setStartWeight(0.0);
            computationalFormulaPo.setEndWeight(0.0);
            computationalFormulaPo.setPackageType("");
            computationalFormulaService.addComputationalFormula(computationalFormulaPo);


            //保存备注
            if(priceExpCostAddDto.getPartnerRemark() != null) {
                PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
                priceExpRemarkPo.setId(priceId);
                priceExpRemarkPo.setRemark(priceExpCostAddDto.getPartnerRemark());
                saveSuccess = priceExpRemarkPo.insert();
                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.msg, null);
                }
            }
        }

        if (priceExpMainAddDto.getIsPublishedPrice() == 2 && priceExpSaleAddDto != null
                && ((priceExpSaleAddDto.getCustomerIds() != null && priceExpSaleAddDto.getCustomerIds().size() > 0)
                || (priceExpSaleAddDto.getCustomerGroupsId() != null && priceExpSaleAddDto.getCustomerGroupsId().size() > 0))) {

            // 有客户组或有客户就是销售价
            priceId = SnowflakeIdWorker.generateId();
            saveSuccess = priceExpSaleService.addPriceExpSale(priceExpCostAddDto, priceExpSaleAddDto, priceMainId, priceId);
            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.msg, null);
            }


            //保存计算公式
            PriceExpComputationalFormulaPo computationalFormulaPo = new PriceExpComputationalFormulaPo();
            computationalFormulaPo.setPriceId(priceId);
            computationalFormulaPo.setFormula("price()*fuel");
            computationalFormulaPo.setZoneNum("");
            computationalFormulaPo.setCountry("");
            computationalFormulaPo.setStartWeight(0.0);
            computationalFormulaPo.setEndWeight(0.0);
            computationalFormulaPo.setPackageType("");
            computationalFormulaService.addComputationalFormula(computationalFormulaPo);


            //保存备注
            if(priceExpSaleAddDto.getSaleRemark() != null) {
                PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
                priceExpRemarkPo.setId(priceId);
                priceExpRemarkPo.setRemark(priceExpSaleAddDto.getSaleRemark());
                saveSuccess = priceExpRemarkPo.insert();
                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.msg, null);
                }
            }
        }

        //保存价格表数据
        saveSuccess = priceExpDataService.addPriceExpData(priceDataId, priceExpDataAddDto);
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.msg, null);
        }

        //保存价格表轴数据
        saveSuccess = priceExpAxisService.addPriceExpAxis(priceDataId, priceExpAxisAddDto);
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.msg, null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceId);
    }


    /**
     * 根据id批量删除价格表数据
     *
     * @param
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> deletePriceBatch(List<Long> priceIdList,  Integer priceType, Boolean delSaleAndCost) {

        List<PriceListForDelBatch> priceList = null;
        if (priceType==1) {
            priceList = priceExpSaleService.getPriceListForDel(priceIdList);
        } else {
            priceList = priceExpCostService.getPriceListForDel(priceIdList);
        }

        StringBuffer sbDelPriceDataIds = new StringBuffer();
        StringBuffer sbPriceIds  = new StringBuffer();
        List<Long> mainPriceList = new ArrayList<>();

        for (PriceListForDelBatch priceListForDelBatch : priceList) {
            if(priceListForDelBatch.getQuotePriceId() == 0){
                //没有引用其他表的表Id
                if(sbDelPriceDataIds.length()>0)
                    sbDelPriceDataIds.append(",");
                sbDelPriceDataIds.append(priceListForDelBatch.getPriceDataId());
            }

            if(sbPriceIds.length()>0)
                sbPriceIds.append(",");
            sbPriceIds.append(priceListForDelBatch.getId());

            mainPriceList.add(priceListForDelBatch.getPriceMainId());
        }

        Map<Long, Long> costPriceMaps = new HashMap<>();
        Map<Long, Long> salePriceMaps = new HashMap<>();
        StringBuilder costPriceIds = new StringBuilder();
        StringBuilder salePriceIds = new StringBuilder();
        if(!delSaleAndCost ) {
            //String mainIds = priceMainIds.toString();
            //mainIds = mainIds.substring(1, mainIds.length()-1);
            List<PriceExpCostListVo> list = null;
            if (priceType ==1) {
                // key main_price_id
                //  costPriceList = select cost.id, sale.main_price_id  price_exp_main inner join price_exp_cost  where main.id in (mainPriceList)
                for (PriceExpCostListVo priceExpCostListVo : list) {
                    costPriceMaps.put(priceExpCostListVo.getPriceMainId(), priceExpCostListVo.getPriceMainId());
                    if(costPriceIds.length()>0)
                        costPriceIds.append(",");
                    costPriceIds.append(priceExpCostListVo.getId());
                }
            } else {
                // key main_price_id
                //List  salePriceList = select sale.id, cost.main_price_id  price_exp_main inner join  price_exp_cost  where main.id in (mainPriceList)
                for (PriceExpCostListVo priceExpCostListVo : list) {
                    salePriceMaps.put(priceExpCostListVo.getPriceMainId(), priceExpCostListVo.getPriceMainId());
                    if(salePriceIds.length()>0)
                        salePriceIds.append(",");
                    salePriceIds.append(priceExpCostListVo.getId());
                }
            }
        }

        String strDelMainPriceIds = null;
        if(delSaleAndCost) {
            strDelMainPriceIds = mainPriceList.toString();
        }
        else {
            StringBuilder delPriceMainids = new StringBuilder();
            for (Long mainPriceId : mainPriceList) {
                if ((priceType == 1 && !costPriceMaps.containsKey(mainPriceId)) ||
                        (priceType == 2 && !salePriceMaps.containsKey(mainPriceId))) {

                        if(delPriceMainids.length()>0)
                            delPriceMainids.append(",");
                        delPriceMainids.append(mainPriceId);
                }
            }

            if(delPriceMainids.length()>0){
                strDelMainPriceIds = delPriceMainids.toString();
            }
        }

        if(null!=strDelMainPriceIds && strDelMainPriceIds.length()>0) {
            //  delete price_exp_main  where id in (strDelMainPriceIds)
        }

        if (sbDelPriceDataIds.length()>0) {
            // delete price_exp_axis where id (sbDelPriceDataIds.toString())

            // delete price_exp_data where id (sbDelPriceDataIds.toString())
        }

        //if(priceType==1)
        //   sql = delete from price_exp_sale where id (sbPriceIds.toString())
        //else
        //    sql = delete from price_exp_cost where id (sbPriceIds.toString())

        //根据id删除价格表
        if(delSaleAndCost) {
            //if(priceType==1)
            //  sql = delete from price_exp_cost where id (costPriceIds.toString())
            //else
            //  sql = delete from price_exp_sale where id (salePriceIds.toString())
        }

        // 根据id删除备注
        // sql = delete from price_exp_remark where id  in (sbPriceIds.toString())

        // delete price_computational_formula  where price_id in (sbPriceIds.toString())

        // delete price_exp_profit  where price_id in (sbPriceIds.toString())


        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, true);
    }


    /**
     * 引用价格表
     * @param referencePriceDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> referencePrice(ReferencePriceDto referencePriceDto) {

        Long priceId = 0L;
        String[] customerName = null;
        String[] customerGroup = null;

        Long id = SnowflakeIdWorker.generateId();

        //服务商, 客户,客户组至少要填写一组
        if((referencePriceDto.getPartnerId() == null || referencePriceDto.getPartnerId() < 1)
        && referencePriceDto.getCustomerName() == null
        && referencePriceDto.getCustomerGroupsName() == null){
            return ResultUtil.APPRESULT(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg, null);
        }

        //校验客户id与客户是否匹配
        if (null != referencePriceDto.getCustomerGroupsId()) {

            //校验客户组id与客户组是否匹配
            customerGroup = referencePriceDto.getCustomerGroupsName().split(",");

            if (!(customerGroup[0].equals("") && referencePriceDto.getCustomerGroupsId().size() == 0)) {

                if (referencePriceDto.getCustomerGroupsId().size() != customerGroup.length) {

                    return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                            ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);
                }
            }
        } else {
            referencePriceDto.setCustomerGroupsId(null);
            referencePriceDto.setCustomerGroupsName(null);
        }
        //校验客户id与客户是否匹配
        if (null != referencePriceDto.getCustomerIds()) {

            customerName = referencePriceDto.getCustomerName().split(",");
            if(!(customerName[0].equals("") && referencePriceDto.getCustomerIds().size() == 0)) {
                if (referencePriceDto.getCustomerIds().size() != customerName.length) {

                    return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                            ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);
                }
            }
        } else {
            referencePriceDto.setCustomerIds(null);
            referencePriceDto.setCustomerName(null);
        }

        if(referencePriceDto.getPartnerId() != null && referencePriceDto.getPartnerId() > 0){

            //保存引用成本价
            PriceExpCostPo priceExpCostPo = new PriceExpCostPo();
            BeanUtil.copyProperties(referencePriceDto, priceExpCostPo);
            priceExpCostPo.setId(id);
            priceExpCostPo.setPriceStatus(1);
            priceId = priceExpCostService.addReferenceCost(priceExpCostPo);

            if(priceId == 0) {
                return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, 0);
            }

        }else if(referencePriceDto.getCustomerGroupsName() != null || referencePriceDto.getCustomerName() != null){

            //保存引用销售价
            PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
            BeanUtil.copyProperties(referencePriceDto,priceExpSalePo);
            priceExpSalePo.setId(id);
            priceExpSalePo.setPriceStatus(1);
            priceId = priceExpSaleService.addReferenceSale(priceExpSalePo);

            if(priceId == 0) {
                return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, 0);
            }
        }

        if(referencePriceDto.getRemark() != null){

            //保存备注
            PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
            priceExpRemarkPo.setId(id);
            priceExpRemarkPo.setRemark(referencePriceDto.getRemark());
            priceExpRemarkPo.insert();

        }

        //保存计算公式
        PriceExpComputationalFormulaPo computationalFormulaPo = new PriceExpComputationalFormulaPo();
        computationalFormulaPo.setPriceId(id);
        computationalFormulaPo.setFormula("price()*fuel");
        computationalFormulaPo.setZoneNum("");
        computationalFormulaPo.setCountry("");
        computationalFormulaPo.setStartWeight(0.0);
        computationalFormulaPo.setEndWeight(0.0);
        computationalFormulaPo.setPackageType("");
        computationalFormulaService.addComputationalFormula(computationalFormulaPo);

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceId);
    }





}