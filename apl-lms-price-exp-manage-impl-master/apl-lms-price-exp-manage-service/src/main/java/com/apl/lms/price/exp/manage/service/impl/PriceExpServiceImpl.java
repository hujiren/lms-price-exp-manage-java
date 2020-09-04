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
import java.util.List;

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
     * @param priceExpSaleListKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<PriceExpSaleListVo>> getPriceExpSaleList(PageDto pageDto, PriceExpSaleListKeyDto priceExpSaleListKeyDto) {
        Page<PriceExpSaleListVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        List<PriceExpSaleListVo> priceExpListVoSaleList = baseMapper.getPriceExpSaleList(page, priceExpSaleListKeyDto);

        page.setRecords(priceExpListVoSaleList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }


    /**
     * 分页查询成本价格列表
     *
     * @param pageDto
     * @param priceExpCostListKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<PriceExpCostListVo>> getPriceExpCostList(PageDto pageDto, PriceExpCostListKeyDto priceExpCostListKeyDto) {
        Page<PriceExpCostListVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        List<PriceExpCostListVo> priceExpListVoCostList = baseMapper.getPriceExpCostList(page, priceExpCostListKeyDto);

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

        //查询主表  用priceExpSaleInfoVo接收是为了服用方法
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
        Long innerOrgId = baseMapper.getInnerOrgById(priceExpAxisPo.getPriceMainId());

        if (innerOrgId == securityUser.getInnerOrgId()) {

            PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
            priceExpDataPo.setPriceData(priceExpDataAddDto.getPriceData());
            priceExpDataPo.setPriceMainId(priceExpAxisPo.getPriceMainId());
            Boolean result = priceExpDataService.updateByMainId(priceExpDataPo);
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

        //Boolean isNull = checkObjFieldINull.checkObjFieldIsNull(priceExpSaleAddDto);
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
                && null == priceExpSaleAddDto.getCustomerGroupsId()
                && null == priceExpSaleAddDto.getCustomerIds()) {

            return ResultUtil.APPRESULT(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg, null);
        }

        Boolean saveSuccess = false;

        //主表Id
        Long priceMainId = SnowflakeIdWorker.generateId();

        //安全用户
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();

        //保存价格主表数据
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpMainAddDto, priceExpMainPo);
        priceExpMainPo.setId(priceMainId);
        priceExpMainPo.setInnerOrgId(securityUser.getInnerOrgId());
        priceExpMainPo.setMainStatus(1);
        saveSuccess = priceExpMainPo.insert();

        if (!saveSuccess) {
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
            ComputationalFormulaAddDto computationalFormulaAddDto = new ComputationalFormulaAddDto();
            computationalFormulaAddDto.setPriceId(priceId);
            computationalFormulaAddDto.setFormula("price()*fuel");
            computationalFormulaAddDto.setZoneNum("");
            computationalFormulaAddDto.setCountry("");
            computationalFormulaAddDto.setStartWeight(0.0);
            computationalFormulaAddDto.setEndWeight(0.0);
            computationalFormulaAddDto.setPackageType("");
            computationalFormulaService.addComputationalFormula(computationalFormulaAddDto);
            //保存备注
            PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
            priceExpRemarkPo.setId(priceId);
            priceExpRemarkPo.setRemark(priceExpCostAddDto.getPartnerRemark());
            saveSuccess = priceExpRemarkPo.insert();
            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.msg, null);
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
            ComputationalFormulaAddDto computationalFormulaAddDto = new ComputationalFormulaAddDto();
            computationalFormulaAddDto.setPriceId(priceId);
            computationalFormulaAddDto.setFormula("price()*fuel");
            computationalFormulaAddDto.setZoneNum("");
            computationalFormulaAddDto.setCountry("");
            computationalFormulaAddDto.setStartWeight(0.0);
            computationalFormulaAddDto.setEndWeight(0.0);
            computationalFormulaAddDto.setPackageType("");
            computationalFormulaService.addComputationalFormula(computationalFormulaAddDto);

            //保存备注
            PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
            priceExpRemarkPo.setId(priceId);
            priceExpRemarkPo.setRemark(priceExpSaleAddDto.getSaleRemark());
            saveSuccess = priceExpRemarkPo.insert();
            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.msg, null);
            }
        }

        //保存价格表数据
        saveSuccess = priceExpDataService.addPriceExpData(priceMainId, priceExpDataAddDto);

        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.msg, null);
        }

        //保存价格表轴数据
        saveSuccess = priceExpAxisService.addPriceExpAxis(priceMainId, priceExpAxisAddDto);

        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.msg, null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceId);
    }


    /**
     * 引用  保存销售价格数据和备注
     *
     * @param priceExpSaleAddDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> addSalePrice(PriceExpSaleAddDto priceExpSaleAddDto) {

        Boolean saveSuccess = false;
        PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
        BeanUtil.copyProperties(priceExpSaleAddDto, priceExpSalePo);
        priceExpSalePo.setId(SnowflakeIdWorker.generateId());
        //priceExpSalePo.setQuotePriceFinalId(0L);
        priceExpSalePo.setQuotePriceId(0L);
        priceExpSalePo.setPriceStatus(1);
        saveSuccess = priceExpSalePo.insert();
        if (!saveSuccess) {
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "销售价格数据保存失败", null);
        }

        PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
        priceExpRemarkPo.setId(SnowflakeIdWorker.generateId());
        priceExpRemarkPo.setRemark(priceExpSaleAddDto.getSaleRemark());
        saveSuccess = priceExpRemarkPo.insert();
        if (!saveSuccess) {
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "扩展数据保存失败", null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpSalePo.getId());
    }

    /**
     * 引用  保存成本价格数据和备注
     *
     * @param priceExpCostAddDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> addCostPrice(PriceExpCostAddDto priceExpCostAddDto) {

        Boolean saveSuccess = false;
        PriceExpCostPo priceExpCostPo = new PriceExpCostPo();
        BeanUtil.copyProperties(priceExpCostAddDto, priceExpCostPo);
        priceExpCostPo.setId(SnowflakeIdWorker.generateId());
        priceExpCostPo.setPriceStatus(1);
        priceExpCostPo.setQuotePriceId(0l);
        saveSuccess = priceExpCostPo.insert();
        if (!saveSuccess) {
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "成本价格数据保存失败", null);
        }

        PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
        priceExpRemarkPo.setId(SnowflakeIdWorker.generateId());
        saveSuccess = priceExpRemarkPo.insert();
        if (!saveSuccess) {
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "扩展信息保存失败", null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpCostPo.getId());
    }


    /**
     * 根据id删除成本价格数据
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> deleteCostBatch(List<Long> ids) {

        //先得到主表id
        List<Long> priceMainIds = priceExpCostService.getPriceDataIds(ids);
        if (priceMainIds.size() == 0) {
            throw new AplException(CommonStatusCode.SYSTEM_FAIL.code, "成本价格表Id无效", null);
        }
        //根据id批量删除成本价格表信息
        Integer resInteger = priceExpCostService.deleteById(ids);

        //根据id批量删除备注表信息
        Integer resInteger2 = priceExpRemarkService.deleteById(ids);

        //通过主表ids得到成本价格表中关联数据的累计条数
        Integer priceDataIdCount = priceExpCostService.getPriceDataIdCount(priceMainIds);

        //如果成本表中没有该主表ids对应的数据
        if (priceDataIdCount == 0) {

            //通过主表ids查询销售表中的数据统计
            Integer priceDataIdCount2 = priceExpSaleService.getPriceDataIdCount(priceMainIds);

            //如果销售价格表中也没有该主表ids对应的关联数据
            if (priceDataIdCount2 == 0) {

                //根据主表ids查询租户ids
                List<Long> innerOrgIds = baseMapper.getInnerOrgId(priceMainIds);
                SecurityUser securityUser = CommonContextHolder.getSecurityUser();
                Long innerOrgId = securityUser.getInnerOrgId();
                if (innerOrgIds.size() != 0) {
                    for (Long aLong : innerOrgIds) {
                        if (innerOrgId == aLong) {

                            //如果是庄家, 则批量删除主表数据
                            baseMapper.deleteBatchIds(priceMainIds);
                            //批量删除轴数据
                            priceExpAxisService.deleteByPriceExpMainId(priceMainIds);
                            //批量删除主数据
                            priceExpDataService.deleteByPriceExpMainId(priceMainIds);

                        }
                    }
                }
            }else{
                throw new AplException(ExpListServiceCode.THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE.code,
                        ExpListServiceCode.THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE.msg, null);
            }
        } else {
            throw new AplException(ExpListServiceCode.THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE.code,
                    ExpListServiceCode.THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE.msg, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, true);
    }

    /**
     * 根据id批量删除销售价格表数据
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> deleteSaleBatch(List<Long> ids) {

        //先得到主表id
        List<Long> priceMainIds = priceExpSaleService.getPriceDataIds(ids);
        if (priceMainIds.size() == 0) {
            throw new AplException(CommonStatusCode.SYSTEM_FAIL.code, "成本价格表Id无效", null);
        }
        //根据id删除销售价格表信息
        priceExpSaleService.deleteById(ids);
        //根据id删除备注表信息
        priceExpRemarkService.deleteById(ids);

        //通过主表id得到销售价格表中关联数据的累计条数
        Integer priceDataIdCount = priceExpSaleService.getPriceDataIdCount(priceMainIds);

        //如果销售表中没有该主表id对应的数据
        if (priceDataIdCount == 0) {

            //通过主表id查询成本表中的数据统计
            Integer priceDataIdCount2 = priceExpCostService.getPriceDataIdCount(priceMainIds);

            //如果成本价格表中也没有该主表ids对应的关联数据
            if (priceDataIdCount2 == 0) {

                //根据主表ids查询租户ids
                List<Long> innerOrgIds = baseMapper.getInnerOrgId(priceMainIds);
                SecurityUser securityUser = CommonContextHolder.getSecurityUser();
                Long innerOrgId = securityUser.getInnerOrgId();
                for (Long aLong : innerOrgIds) {
                    if (innerOrgId == aLong) {

                        //如果是庄家, 则批量删除主表数据
                        baseMapper.deleteBatchIds(priceMainIds);
                        //批量删除轴数据
                        priceExpAxisService.deleteByPriceExpMainId(priceMainIds);
                        //批量删除主数据
                        priceExpDataService.deleteByPriceExpMainId(priceMainIds);

                    }
                }
            }else{
                throw new AplException(ExpListServiceCode.THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE.code,
                        ExpListServiceCode.THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE.msg, null);
            }
        }else {
            throw new AplException(ExpListServiceCode.THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE.code,
                    ExpListServiceCode.THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE.msg, null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, true);
    }

}