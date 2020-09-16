package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.apl.cache.AplCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.join.JoinBase;
import com.apl.lib.join.JoinFieldInfo;
import com.apl.lib.join.JoinUtil;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.common.lib.cache.JoinSpecialCommodity;
import com.apl.lms.common.lib.feign.LmsCommonFeign;
import com.apl.lms.common.query.manage.dto.SpecialCommodityDto;
import com.apl.lms.price.exp.manage.mapper.PriceExpMapper;
import com.apl.lms.price.exp.manage.service.*;
import com.apl.lms.price.exp.manage.util.CheckObjFieldINull;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.entity.Customer;
import com.apl.lms.price.exp.pojo.entity.CustomerGroupInfo;
import com.apl.lms.price.exp.pojo.entity.PriceListForDelBatch;
import com.apl.lms.price.exp.pojo.po.*;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
        CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE("CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE", "无法修改源于别处的数据"),
        THE_REFERENCED_DATA_DOES_NOT_EXIST("THE_REFERENCED_DATA_DOES_NOT_EXIST", "被引用的数据不存在"),
        THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE("THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE", "仍有其他数据绑定主表");

        private String code;
        private String msg;

        ExpListServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Autowired
    LmsCommonFeign lmsCommonFeign;

    @Autowired
    AplCacheUtil aplCacheUtil;

    static JoinFieldInfo joinSpecialCommodityFieldInfo = null; //跨项目跨库关联 特殊物品 反射字段缓存

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

    @Autowired
    PriceExpProfitService priceExpProfitService;

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
    public ResultUtil<Page<PriceExpCostListVo>> getPublishedPriceList(PageDto pageDto, PriceExpPublishedKeyDto keyDto) {
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
    public ResultUtil<PriceExpSaleInfoVo> getPriceExpSaleInfo(Long id) throws Exception {

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
        priceExpSaleInfoVo.setPriceCode(priceExpSaleVo.getPriceCode());
        priceExpSaleInfoVo.setPriceName(priceExpSaleVo.getPriceName());
        priceExpSaleInfoVo.setPriceStatus(priceExpSaleVo.getPriceStatus());
        priceExpSaleInfoVo.setChannelCategory(priceExpSaleVo.getChannelCategory());

        //组装客户List (客户id, 客户名称)
        if (priceExpSaleVo.getCustomerIds() != null && priceExpSaleVo.getCustomerName() != null) {
            String customerIds = priceExpSaleVo.getCustomerIds().replace("[", "").replace("]", "").replaceAll(" ", "");
            String customerNames = priceExpSaleVo.getCustomerName().replace("[", "").replace("]", "").replaceAll(" ", "");

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


        if(priceExpSaleInfoVo.getSpecialCommodityStr()!=null) {
            String specialCommodityStr = priceExpSaleInfoVo.getSpecialCommodityStr().replace("[", "").replace("]", "").replaceAll(" ", "");

            List<SpecialCommodityDto> specialCommodityList = new ArrayList<>();
            String[] specialCommodityArr = specialCommodityStr.split(",");

            for (int i = 0; i < specialCommodityArr.length; i++) {
                SpecialCommodityDto specialCommodityDto = new SpecialCommodityDto();
                specialCommodityDto.setCode(Integer.valueOf(specialCommodityArr[i]));
                specialCommodityList.add(specialCommodityDto);
            }

            JoinSpecialCommodity joinSpecialCommodity = new JoinSpecialCommodity(1, lmsCommonFeign, aplCacheUtil);
            List<JoinBase> joinTabs = new ArrayList<>();
            joinTabs = new ArrayList<>();
            //关联特殊物品字段信息
            if (null != joinSpecialCommodityFieldInfo) {
                joinSpecialCommodity.setJoinFieldInfo(joinSpecialCommodityFieldInfo);
            } else {
                joinSpecialCommodity.addField("code", Integer.class, "specialCommodityName", String.class);
                joinSpecialCommodity.addField("specialCommodityNameEn", String.class);
                joinSpecialCommodityFieldInfo = joinSpecialCommodity.getJoinFieldInfo();
            }
            joinTabs.add(joinSpecialCommodity);
            //执行跨项目跨库关联
            JoinUtil.join(specialCommodityList, joinTabs);

            priceExpSaleInfoVo.setSpecialCommodity(specialCommodityList);
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
    public ResultUtil<PriceExpCostInfoVo> getPriceExpCostInfo(Long id) throws Exception {

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

        }

        priceExpCostInfoVo.setPartnerId(priceExpCostVo.getPartnerId());
        priceExpCostInfoVo.setPriceStatus(priceExpCostVo.getPriceStatus());
        priceExpCostInfoVo.setPriceCode(priceExpCostVo.getPriceCode());
        priceExpCostInfoVo.setPriceName(priceExpCostVo.getPriceName());
        priceExpCostInfoVo.setChannelCategory(priceExpCostVo.getChannelCategory());
        priceExpCostInfoVo.setMainId(priceExpCostVo.getPriceMainId());
        priceExpCostInfoVo.setId(priceExpCostVo.getId());

        if(priceExpSaleInfoVo.getSpecialCommodityStr()!=null) {
            String specialCommodityStr = priceExpSaleInfoVo.getSpecialCommodityStr().replace("[", "").replace("]", "").replaceAll(" ", "");

            List<SpecialCommodityDto> specialCommodityList = new ArrayList<>();
            String[] specialCommodityArr = specialCommodityStr.split(",");

            for (int i = 0; i < specialCommodityArr.length; i++) {
                SpecialCommodityDto specialCommodityDto = new SpecialCommodityDto();
                specialCommodityDto.setCode(Integer.valueOf(specialCommodityArr[i]));
                specialCommodityList.add(specialCommodityDto);
            }

            JoinSpecialCommodity joinSpecialCommodity = new JoinSpecialCommodity(1, lmsCommonFeign, aplCacheUtil);
            List<JoinBase> joinTabs = new ArrayList<>();
            joinTabs = new ArrayList<>();
            //关联特殊物品字段信息
            if (null != joinSpecialCommodityFieldInfo) {
                joinSpecialCommodity.setJoinFieldInfo(joinSpecialCommodityFieldInfo);
            } else {
                joinSpecialCommodity.addField("code", Integer.class, "specialCommodityName", String.class);
                joinSpecialCommodity.addField("specialCommodityNameEn", String.class);
                joinSpecialCommodityFieldInfo = joinSpecialCommodity.getJoinFieldInfo();
            }
            joinTabs.add(joinSpecialCommodity);
            //执行跨项目跨库关联
            JoinUtil.join(specialCommodityList, joinTabs);

            priceExpSaleInfoVo.setSpecialCommodity(specialCommodityList);
        }

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
     * @param priceExpSaleUpdDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updateSalePrice(PriceExpSaleUpdDto priceExpSaleUpdDto) {

        String[] customerName = null;
        String[] customerGroup = null;

        //Boolean isNull = checkObjFieldINull.checkObjFieldIsNull(priceExpSaleAddDto);
        //校验客户id与客户是否匹配
        if (priceExpSaleUpdDto.getCustomerGroupsId() != null && priceExpSaleUpdDto.getCustomerGroupsId().size() > 0) {

            //校验客户组id与客户组是否匹配
            customerGroup = priceExpSaleUpdDto.getCustomerGroupsName().split(",");
            if (priceExpSaleUpdDto.getCustomerGroupsId().size() != customerGroup.length) {

                return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                        ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);
            }
        } else {
            priceExpSaleUpdDto.setCustomerGroupsId(null);
            priceExpSaleUpdDto.setCustomerGroupsName(null);
        }

        //校验客户id与客户是否匹配
        if (priceExpSaleUpdDto.getCustomerIds() != null && priceExpSaleUpdDto.getCustomerIds().size() > 0) {

            customerName = priceExpSaleUpdDto.getCustomerName().split(",");
            if (priceExpSaleUpdDto.getCustomerIds().size() != customerName.length) {

                return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                        ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);

            }
        } else {
            priceExpSaleUpdDto.setCustomerIds(null);
            priceExpSaleUpdDto.setCustomerName(null);
        }

        //不是销售价且不是客户价
        if (null == priceExpSaleUpdDto.getCustomerGroupsId()
                && null == priceExpSaleUpdDto.getCustomerIds()) {

            return ResultUtil.APPRESULT(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg, null);
        }

        SecurityUser securityUser = CommonContextHolder.getSecurityUser();

        Boolean saveSuccess = false;
        //更新主表
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpSaleUpdDto, priceExpMainPo);
        priceExpMainPo.setId(priceExpSaleUpdDto.getPriceMainId());
        priceExpMainPo.setIsPublishedPrice(null);
        Integer integer = baseMapper.updateMainById(priceExpMainPo);
        if (integer < 1) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
        }

        //更新销售价格表
        PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
        BeanUtil.copyProperties(priceExpSaleUpdDto, priceExpSalePo);
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
     * @param priceExpCostUpdDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updateCostPrice(PriceExpCostUpdDto priceExpCostUpdDto) {

        if (priceExpCostUpdDto.getIsPublishedPrice() != 1 && priceExpCostUpdDto.getPartnerId() < 1) {
            //不是公布价且服务商id小于1
            throw new AplException(ExpListServiceCode.PARTNER_MUST_BE_NOT_ZERO.code,
                    ExpListServiceCode.PARTNER_MUST_BE_NOT_ZERO.msg, null);
        }
        if (priceExpCostUpdDto.getIsPublishedPrice() == 1)
            priceExpCostUpdDto.setPartnerId(0l);

        Boolean saveSuccess = false;
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        Long innerOrgId = baseMapper.getInnerOrgById(priceExpCostUpdDto.getPriceMainId());
        //更新主表
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpCostUpdDto, priceExpMainPo);
        priceExpMainPo.setId(priceExpCostUpdDto.getPriceMainId());
        Integer integer = baseMapper.updateMainById(priceExpMainPo);
        if (integer < 1) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
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
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updatePriceData(PriceExpDataAddDto priceExpDataAddDto) {
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        Long innerOrgById = baseMapper.getInnerOrgById(priceExpDataAddDto.getId());

        if (securityUser.getInnerOrgId() == innerOrgById) {
            PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
            priceExpDataPo.setPriceData(priceExpDataAddDto.getPriceData());
            priceExpDataPo.setId(priceExpDataAddDto.getId());
            Boolean result = priceExpDataService.updById(priceExpDataPo);
            if (!result) {
                throw new AplException(CommonStatusCode.SYSTEM_FAIL, null);
            }
            PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
            BeanUtil.copyProperties(priceExpDataAddDto, priceExpAxisPo);
            Boolean res = priceExpAxisService.updateByMainId(priceExpAxisPo);
            if (!res) {
                throw new AplException(ExpListServiceCode.CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE.code,
                        ExpListServiceCode.CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE.msg, null);
            }
        } else {
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }


    /**
     * 新增快递价格
     *
     * @param priceExpAddDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> addExpPrice(PriceExpAddDto priceExpAddDto) {

        AddPriceResultBo addPriceResultBo = addExpPriceBase(priceExpAddDto, 0l, 0l);

        //保存价格表数据
        boolean saveSuccess = priceExpDataService.addPriceExpData(addPriceResultBo.priceMainId, priceExpAddDto);
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.msg, null);
        }

        //保存价格表轴数据
        saveSuccess = priceExpAxisService.addPriceExpAxis(addPriceResultBo.priceMainId, priceExpAddDto);
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.msg, null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, addPriceResultBo.priceId);
    }


    /**
     * 引用价格表
     *
     * @param referencePriceDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> referencePrice(ReferencePriceDto referencePriceDto) {

        AddPriceResultBo addPriceResultBo = addExpPriceBase(referencePriceDto,
                referencePriceDto.getQuotePriceId(),
                referencePriceDto.getPriceMainId());

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, addPriceResultBo.priceMainId);
    }


    AddPriceResultBo addExpPriceBase(PriceExpAddBaseDto priceExpAddDto, Long quotePriceId, Long priceMainId) {

        String[] customerName = null;
        String[] customerGroup = null;

        //校验客户id与客户是否匹配
        if (null != priceExpAddDto.getCustomerGroupsId()) {

            //校验客户组id与客户组是否匹配
            customerGroup = priceExpAddDto.getCustomerGroupsName().split(",");

            if (!(customerGroup[0].equals("") && priceExpAddDto.getCustomerGroupsId().size() == 0)) {

                if (priceExpAddDto.getCustomerGroupsId().size() != customerGroup.length) {

                    throw new AplException(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                            ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);
                }
            }
        } else {
            priceExpAddDto.setCustomerGroupsId(null);
            priceExpAddDto.setCustomerGroupsName(null);
        }

        //校验客户id与客户是否匹配
        if (null != priceExpAddDto.getCustomerIds()) {

            customerName = priceExpAddDto.getCustomerName().split(",");
            if (!(customerName[0].equals("") && priceExpAddDto.getCustomerIds().size() == 0)) {
                if (priceExpAddDto.getCustomerIds().size() != customerName.length) {

                    throw new AplException(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                            ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg);
                }
            }
        } else {
            priceExpAddDto.setCustomerIds(null);
            priceExpAddDto.setCustomerName(null);
        }

        //不是公布价且不是成本价且不是销售价
        if (priceExpAddDto.getIsPublishedPrice() == 2
                && priceExpAddDto.getPartnerId() < 1
                && 0 == priceExpAddDto.getCustomerGroupsId().size()
                && 0 == priceExpAddDto.getCustomerIds().size()) {

            throw new AplException(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg);
        }

        Boolean saveSuccess = false;

        //数据表Id & 主表Id
        Long tenantPriceMainId = 0l;
        if (priceMainId > 0) {
            // 引用价格表
            tenantPriceMainId = baseMapper.getPriceMainId(priceMainId);

            if(null == tenantPriceMainId || tenantPriceMainId == 0){
                throw new AplException(ExpListServiceCode.THE_REFERENCED_DATA_DOES_NOT_EXIST.code,
                        ExpListServiceCode.THE_REFERENCED_DATA_DOES_NOT_EXIST.msg, null);
            }
        } else {
            priceMainId = SnowflakeIdWorker.generateId();
            tenantPriceMainId = priceMainId;
        }

        //安全用户
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        Integer saveResultId = 0;

        //保存价格主表数据
        if (null == tenantPriceMainId || tenantPriceMainId.equals(0)) {

            PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
            BeanUtil.copyProperties(priceExpAddDto, priceExpMainPo);
            priceExpMainPo.setId(priceMainId);
            save(priceExpMainPo);
            saveResultId = baseMapper.addPriceExpMain(priceExpMainPo);

            if (saveResultId < 1) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
            }
        }

        if (priceExpAddDto.getIsPublishedPrice() == 1) {
            priceExpAddDto.setPartnerId(0L);
        }

        Long priceId = 0l;
        if (priceExpAddDto.getIsPublishedPrice() == 1 || priceExpAddDto.getPartnerId() > 0) {
            //公布价(IsPublishedPrice=1) 或 成本价(有服务商)
            priceId = SnowflakeIdWorker.generateId();
            saveSuccess = priceExpCostService.addPriceExpCost(priceExpAddDto, priceId, priceMainId, quotePriceId);
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
            if (priceExpAddDto.getPartnerRemark() != null) {
                PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
                priceExpRemarkPo.setId(priceId);
                priceExpRemarkPo.setRemark(priceExpAddDto.getPartnerRemark());
                saveSuccess = priceExpRemarkPo.insert();
                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.msg, null);
                }
            }
        }

        if (priceExpAddDto.getIsPublishedPrice() == 2 && priceExpAddDto != null
                && ((priceExpAddDto.getCustomerIds() != null && priceExpAddDto.getCustomerIds().size() > 0)
                || (priceExpAddDto.getCustomerGroupsId() != null && priceExpAddDto.getCustomerGroupsId().size() > 0))) {

            // 有客户组或有客户就是销售价
            priceId = SnowflakeIdWorker.generateId();
            saveSuccess = priceExpSaleService.addPriceExpSale(priceExpAddDto, priceId, quotePriceId, priceMainId);
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
            if (priceExpAddDto.getRemark() != null) {
                PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
                priceExpRemarkPo.setId(priceId);
                priceExpRemarkPo.setRemark(priceExpAddDto.getRemark());
                saveSuccess = priceExpRemarkPo.insert();
                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.msg, null);
                }
            }
        }

        AddPriceResultBo addPriceResultBo = new AddPriceResultBo();
        addPriceResultBo.priceId = priceId;
        addPriceResultBo.priceMainId = priceMainId;

        return addPriceResultBo;
    }


    /**
     * 根据id批量删除价格表数据
     *
     * @param
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> deletePriceBatch(List<Long> priceIdList, Integer priceType, Boolean delSaleAndCost) {

        List<PriceListForDelBatch> priceList = null;
        if (priceType == 1) {
            priceList = priceExpSaleService.getPriceListForDel(priceIdList);
        } else {
            priceList = priceExpCostService.getPriceListForDel(priceIdList);
        }

        StringBuffer sbDelPriceMainIds = new StringBuffer();
        StringBuffer sbPriceIds = new StringBuffer();
        List<Long> mainPriceList = new ArrayList<>();

        for (PriceListForDelBatch priceListForDelBatch : priceList) {
            if (priceListForDelBatch.getQuotePriceId() == 0) {
                //没有引用其他表的表Id
                if (sbDelPriceMainIds.length() > 0)
                    sbDelPriceMainIds.append(",");
                sbDelPriceMainIds.append(priceListForDelBatch.getPriceMainId());
            }

            if (sbPriceIds.length() > 0)
                sbPriceIds.append(",");
            sbPriceIds.append(priceListForDelBatch.getId());

            mainPriceList.add(priceListForDelBatch.getPriceMainId());
        }

        Map<Long, Long> costPriceMaps = new HashMap<>();
        Map<Long, Long> salePriceMaps = new HashMap<>();
        StringBuilder costPriceIds = new StringBuilder();
        StringBuilder salePriceIds = new StringBuilder();

        if (!delSaleAndCost) {

            List<PriceExpCostListVo> list = null;
            if (priceType == 1) {
                // key main_price_id
                //  costPriceList = select cost.id, sale.main_price_id  price_exp_main inner join price_exp_cost  where main.id in (mainPriceList)
                List<PriceListForDelBatch> costPriceList = baseMapper.getCostPriceList(mainPriceList);
                for (PriceListForDelBatch priceExpCostListVo : costPriceList) {
                    costPriceMaps.put(priceExpCostListVo.getPriceMainId(), priceExpCostListVo.getPriceMainId());
                    if (costPriceIds.length() > 0)
                        costPriceIds.append(",");
                    costPriceIds.append(priceExpCostListVo.getId());
                }
            } else {
                // key main_price_id
                //List  salePriceList = select sale.id, cost.main_price_id  price_exp_main inner join  price_exp_cost  where main.id in (mainPriceList)
                List<PriceListForDelBatch> salePriceList = baseMapper.getSalePriceList(mainPriceList);
                for (PriceListForDelBatch priceExpSaleListVo : salePriceList) {
                    salePriceMaps.put(priceExpSaleListVo.getPriceMainId(), priceExpSaleListVo.getPriceMainId());
                    if (salePriceIds.length() > 0)
                        salePriceIds.append(",");
                    salePriceIds.append(priceExpSaleListVo.getId());
                }
            }
        }

        String strDelMainPriceIds = null;
        if (delSaleAndCost) {
            strDelMainPriceIds = mainPriceList.toString();
        } else {
            StringBuilder delPriceMainIds = new StringBuilder();
            for (Long mainPriceId : mainPriceList) {
                if ((priceType == 1 && !costPriceMaps.containsKey(mainPriceId)) ||
                        (priceType == 2 && !salePriceMaps.containsKey(mainPriceId))) {

                    if (delPriceMainIds.length() > 0)
                        delPriceMainIds.append(",");
                    delPriceMainIds.append(mainPriceId);
                }
            }

            if (delPriceMainIds.length() > 0) {
                strDelMainPriceIds = delPriceMainIds.toString();
            }
        }

        if (null != strDelMainPriceIds && strDelMainPriceIds.length() > 0) {
            //  delete price_exp_main  where id in (strDelMainPriceIds)
            baseMapper.delBatchs(strDelMainPriceIds);
        }

        if (sbDelPriceMainIds.length() > 0) {
            // delete price_exp_axis where id (sbDelPriceMainIds.toString())
            priceExpAxisService.delBatch(sbDelPriceMainIds.toString());
            // delete price_exp_data where id (sbDelPriceMainIds.toString())
            priceExpDataService.delBatch(sbDelPriceMainIds.toString());
        }

        if (priceType == 1)
            //   sql = delete from price_exp_sale where id (sbPriceIds.toString())
            priceExpSaleService.delBatch(salePriceIds.toString());
        else
            //    sql = delete from price_exp_cost where id (sbPriceIds.toString())
            priceExpCostService.delBatch(costPriceIds.toString());

        //根据id删除价格表
        if (delSaleAndCost) {
            if (priceType == 1)
                //  sql = delete from price_exp_sale where id (salePriceIds.toString())
                priceExpSaleService.delBatch(salePriceIds.toString());
            else
                //  sql = delete from price_exp_cost where id (costPriceIds.toString())
                priceExpCostService.delBatch(costPriceIds.toString());

        }

        // 根据id删除备注
        // sql = delete from price_exp_remark where id  in (sbPriceIds.toString())
        priceExpRemarkService.delBatch(sbPriceIds.toString());
        // delete price_computational_formula  where price_id in (sbPriceIds.toString())
        computationalFormulaService.delBatch(sbPriceIds.toString());
        // delete price_exp_profit  where price_id in (sbPriceIds.toString())
        priceExpProfitService.delBatch(sbPriceIds.toString());

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }


    class AddPriceResultBo {
        public Long priceMainId;
        public Long priceId;
    }


}