package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.apl.cache.AplCacheUtil;
import com.apl.db.adb.AdbPageVo;
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
import com.apl.lms.price.exp.manage.dao.PriceExpMainDao;
import com.apl.lms.price.exp.manage.mapper.PriceExpMapper;
import com.apl.lms.price.exp.manage.mapper.PriceExpMapper2;
import com.apl.lms.price.exp.manage.service.*;
import com.apl.lms.price.exp.manage.util.CheckObjFieldINull;
import com.apl.lms.price.exp.pojo.bo.PriceListForDelBatchBo;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.*;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PriceExpServiceImpl2 extends ServiceImpl<PriceExpMapper2, PriceExpMainPo> implements PriceExpService2 {

    enum ExpListServiceCode {
        PRICE_EXP_MAIN_SAVE_DATA_FAILED("PRICE_EXP_MAIN_SAVE_DATA_FAILED", "保存价格主表失败"),
        PRICE_EXP_SALE_SAVE_DATA_FAILED("PRICE_EXP_SALE_SAVE_DATA_FAILED", "保存销售价格表失败"),
        PRICE_EXP_COST_SAVE_DATA_FAILED("PRICE_EXP_COST_SAVE_DATA_FAILED", "保存成本价格表失败"),
        PRICE_EXP_AXIS_SAVE_DATA_FAILED("PRICE_EXP_AXIS_SAVE_DATA_FAILED", "保存价格表轴数据失败"),
        PRICE_EXP_DATA_SAVE_DATA_FAILED("PRICE_EXP_DATA_SAVE_DATA_FAILED", "保存价格表数据失败"),
        price_exp_remark_SAVE_DATA_FAILED("price_exp_remark_SAVE_DATA_FAILED", "保存价格表扩展数据失败"),
        ID_IS_NOT_EXITS("ID_IS_NOT_EXITS", "id不存在"),
        CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH("CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH", "客户id和客户名称不匹配"),
        THERE_IS_NO_CORRESPONDING_DATA("THERE_IS_NO_CORRESPONDING_DATA", "没有对应数据"),
        PARTNER_MUST_BE_NOT_ZERO("PARTNER_MUST_BE_NOT_ZERO", "服务商id不能为0"),
        PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP(
                "PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP",
                "服务商, 客户组, 客户, 请至少填写一组"),
        CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE("CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE", "无法修改源于别处的数据"),
        THE_REFERENCED_DATA_DOES_NOT_EXIST("THE_REFERENCED_DATA_DOES_NOT_EXIST", "被引用的数据不存在"),
        THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE("THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE", "仍有其他数据绑定主表"),;

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
    ComputationalFormulaService computationalFormulaService;

    @Autowired
    CheckObjFieldINull checkObjFieldINull;

    @Autowired
    PriceExpProfitService priceExpProfitService;

    @Autowired
    PriceZoneNameService priceZoneNameService;

    @Autowired
    PriceExpMainDao priceExpMainDao;

    /**
     * 分页查询销售价格列表
     *
     * @param pageDto
     * @param keyDto
     * @return
     */
    @Override
    public ResultUtil<Page<PriceExpSaleListVo>> getPriceExpSaleList(PageDto pageDto, PriceExpSaleListKeyDto keyDto) {

        Page<PriceExpSaleListVo> pageVo = new Page();
        pageVo.setCurrent(pageDto.getPageIndex());
        pageVo.setSize(pageDto.getPageSize());

        AdbPageVo<PriceExpSaleListVo> adbPageVo = priceExpMainDao.getPriceExpSaleList(keyDto, pageDto.getPageIndex(), pageDto.getPageSize());

        pageVo.setTotal(adbPageVo.getRsCount());
        pageVo.setRecords(adbPageVo.getList());

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, pageVo);
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
        if(null != keyDto && keyDto.getChannelCategory() != null){
            keyDto.setChannelCategory(keyDto.getChannelCategory().toUpperCase());
        }

//        if(null != keyDto && keyDto.getSpecialCommodity() != null){
//            keyDto.setSpecialCommodityCopy(keyDto.getSpecialCommodity().toString());
//        }

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
        if(null != keyDto && keyDto.getChannelCategory() != null){
            keyDto.setChannelCategory(keyDto.getChannelCategory().toUpperCase());
        }
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
            throw new AplException(ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.code,
                    ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.msg, null);
        }
        priceExpSaleInfoVo.setId(priceExpSaleVo.getId());
        priceExpSaleInfoVo.setPriceCode(priceExpSaleVo.getPriceCode());
        priceExpSaleInfoVo.setPriceName(priceExpSaleVo.getPriceName());
        priceExpSaleInfoVo.setChannelCategory(priceExpSaleVo.getChannelCategory());
        priceExpSaleInfoVo.setPriceStatus(priceExpSaleVo.getPriceStatus());
        priceExpSaleInfoVo.setMainId(priceExpSaleVo.getPriceMainId());

        //获取分区名称
        ResultUtil<String> priceZoneNameResult = priceZoneNameService.getPriceZoneName(priceExpSaleInfoVo.getZoneId());
        priceExpSaleInfoVo.setZoneName(priceZoneNameResult.getData());

        //组装客户List (客户id, 客户名称)
        if (priceExpSaleVo.getCustomerIds() != null && priceExpSaleVo.getCustomerName() != null) {
            String customerIds = priceExpSaleVo.getCustomerIds().replace("[", "").replace("]", "").replaceAll(" ", "");
            String customerNames = priceExpSaleVo.getCustomerName().replace("[", "").replace("]", "").replaceAll(" ", "");

            List<CustomerDto> customerDtoList = new ArrayList<>();

            String[] customerIdArr = customerIds.split(",");
            String[] customerNameArr = customerNames.split(",");
            for (int i = 0; i < customerIdArr.length; i++) {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setCustomerId(Long.valueOf(customerIdArr[i]));
                customerDto.setCustomerName(customerNameArr[i]);
                customerDtoList.add(customerDto);
            }
            priceExpSaleInfoVo.setCustomer(customerDtoList);
        }

        //组装客户组List(客户组id, 客户组名称)
        if (priceExpSaleVo.getCustomerGroupsId() != null && priceExpSaleVo.getCustomerGroupsName() != null) {
            String customerGroupsIds = priceExpSaleVo.getCustomerGroupsId().replace("[", "").replace("]", "").replaceAll(" ", "");
            String customerGroupsName = priceExpSaleVo.getCustomerGroupsName().replace("[", "").replace("]", "").replaceAll(" ", "");

            List<CustomerGroupDto> customerGroupDtoList = new ArrayList<>();
            String[] customerGroupIdArr = customerGroupsIds.split(",");
            String[] customerGroupNameArr = customerGroupsName.split(",");

            for (int i = 0; i < customerGroupIdArr.length; i++) {
                CustomerGroupDto customerGroupDto = new CustomerGroupDto();
                customerGroupDto.setCustomerGroupsId(Long.valueOf(customerGroupIdArr[i]));
                customerGroupDto.setCustomerGroupsName(customerGroupNameArr[i]);
                customerGroupDtoList.add(customerGroupDto);
            }
            priceExpSaleInfoVo.setCustomerGroup(customerGroupDtoList);
        }

        //组装特殊物品List
        if(priceExpSaleInfoVo.getSpecialCommodityStr()!=null) {
            String specialCommodityStr = priceExpSaleInfoVo.getSpecialCommodityStr().replace("[", "").replace("]", "").replaceAll(" ", "");

            List<SpecialCommodityDto> specialCommodityList = new ArrayList<>();
            String[] specialCommodityArr = specialCommodityStr.replace("{", "").replace("}", "").split(",");

            for (int i = 0; i < specialCommodityArr.length; i++) {
                SpecialCommodityDto specialCommodityDto = new SpecialCommodityDto();
                specialCommodityDto.setCode(Integer.valueOf(specialCommodityArr[i]));
                specialCommodityList.add(specialCommodityDto);
            }

            JoinSpecialCommodity joinSpecialCommodity = new JoinSpecialCommodity(1, lmsCommonFeign, aplCacheUtil);
            List<JoinBase> joinTabs = new ArrayList<>();
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
            priceExpSaleInfoVo.setSpecialCommodityStr(null);
        }


//        //获取扩展信息并组装
//        PriceExpRemarkPo priceExpRemarkPo = priceExpRemarkService.getDevelopInfoById(id);
//        if (priceExpRemarkPo != null) {
//            priceExpSaleInfoVo.setRemark(priceExpRemarkPo.getRemark());
//        }

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

        if (null == priceExpCostVo) {
            return ResultUtil.APPRESULT(ExpListServiceCode.ID_IS_NOT_EXITS.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg, null);
        }

        //查询主表  用priceExpSaleInfoVo接收是为了复用方法
        PriceExpSaleInfoVo priceExpSaleInfoVo = baseMapper.getPriceExpMainInfoById(priceExpCostVo.getPriceMainId());

        if (null != priceExpSaleInfoVo) {

            BeanUtil.copyProperties(priceExpSaleInfoVo, priceExpCostInfoVo);

        }

        priceExpCostInfoVo.setPartnerId(priceExpCostVo.getPartnerId());
        priceExpCostInfoVo.setPriceStatus(priceExpCostVo.getPriceStatus());
        priceExpCostInfoVo.setPriceCode(priceExpCostVo.getPriceCode());
        priceExpCostInfoVo.setPriceName(priceExpCostVo.getPriceName());
        priceExpCostInfoVo.setChannelCategory(priceExpCostVo.getChannelCategory());
        priceExpCostInfoVo.setMainId(priceExpCostVo.getPriceMainId());
        priceExpCostInfoVo.setId(priceExpCostVo.getId());

        //获取分区名称
        ResultUtil<String> priceZoneNameResult = priceZoneNameService.getPriceZoneName(priceExpCostInfoVo.getZoneId());
        priceExpCostInfoVo.setZoneName(priceZoneNameResult.getData());

        if(null != priceExpSaleInfoVo && null != priceExpSaleInfoVo.getSpecialCommodityStr()) {
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
        if (null != priceExpRemarkPo) {
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

        //不是销售价且不是客户价
        if (null == priceExpSaleUpdDto.getCustomerGroup()
                && null == priceExpSaleUpdDto.getCustomer()) {

            return ResultUtil.APPRESULT(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg, null);
        }

        Boolean saveSuccess = false;
        //更新主表
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpSaleUpdDto, priceExpMainPo);

        if(null != priceExpMainPo.getSpecialCommodity()) {
            //处理特殊物品
            List<SpecialCommodityDto> specialCommodity = priceExpSaleUpdDto.getSpecialCommodity();
            List<Integer> specialCommodityCodeList = new ArrayList<>();
            for (SpecialCommodityDto specialCommodityDto : specialCommodity) {
                specialCommodityCodeList.add(specialCommodityDto.getCode());
            }
            priceExpMainPo.setSpecialCommodity(specialCommodityCodeList);
        }

        priceExpMainPo.setId(priceExpSaleUpdDto.getMainId());
        priceExpMainPo.setIsPublishedPrice(null);
        Integer integer = baseMapper.updateMainById(priceExpMainPo);
        if (integer < 1) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
        }

        List<Long> customerGroupsId = new ArrayList<>();
        StringBuffer customerGroupsName = new StringBuffer();
        for (CustomerGroupDto customerGroupDto : priceExpSaleUpdDto.getCustomerGroup()) {
            customerGroupsId.add(customerGroupDto.getCustomerGroupsId());
            if(customerGroupsName.length()>0)
                customerGroupsName.append(", ");
            customerGroupsName.append(customerGroupDto.getCustomerGroupsName());
        }

        List<Long> customerIds = new ArrayList<>();
        StringBuffer customerName= new StringBuffer();
        for (CustomerDto customerDto : priceExpSaleUpdDto.getCustomer()) {
            customerIds.add(customerDto.getCustomerId());
            if(customerName.length()>0)
                customerName.append(", ");
            customerName.append(customerDto.getCustomerName());
        }

        //更新销售价格表
        PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
        BeanUtil.copyProperties(priceExpSaleUpdDto, priceExpSalePo);
        priceExpSalePo.setCustomerGroupsId(customerGroupsId);
        priceExpSalePo.setCustomerGroupsName(customerGroupsName.toString());
        priceExpSalePo.setCustomerIds(customerIds);
        priceExpSalePo.setCustomerName(customerName.toString());
        priceExpSalePo.setPriceMainId(priceExpSaleUpdDto.getMainId());
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
        //更新主表
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpCostUpdDto, priceExpMainPo);
        priceExpMainPo.setId(priceExpCostUpdDto.getMainId());

        if(null != priceExpMainPo.getSpecialCommodity()){
            //处理特殊物品
            List<SpecialCommodityDto> specialCommodity = priceExpCostUpdDto.getSpecialCommodity();
            List<Integer> specialCommodityCodeList = new ArrayList<>();
            for (SpecialCommodityDto specialCommodityDto : specialCommodity) {
                specialCommodityCodeList.add(specialCommodityDto.getCode());
            }
            priceExpMainPo.setSpecialCommodity(specialCommodityCodeList);
        }

        Integer integer = baseMapper.updateMainById(priceExpMainPo);
        if (integer < 1) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
        }

        //更新成本价格表
        PriceExpCostPo priceExpCostPo = new PriceExpCostPo();
        BeanUtil.copyProperties(priceExpCostUpdDto, priceExpCostPo);
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
     * @param priceExpDataUpdDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updatePriceData(PriceExpDataUpdDto priceExpDataUpdDto) {
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();

        Long mainId = 0L;
        Long mainIdRes = priceExpSaleService.getMainId(priceExpDataUpdDto.getId());
        Long mainIdRes2 = priceExpCostService.getMainId(priceExpDataUpdDto.getId());

        if(mainIdRes != null && mainIdRes != 0){
            mainId = mainIdRes;
        }else{
            mainId = mainIdRes2;
        }
        if(mainId == null || mainId == 0){
            throw new AplException(ExpListServiceCode.ID_IS_NOT_EXITS.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg);
        }

        Long innerOrgById = baseMapper.getInnerOrgById(mainId);

        if (securityUser.getInnerOrgId() == innerOrgById) {

            //更新价格表数据
            PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
            priceExpDataPo.setPriceData(priceExpDataUpdDto.getPriceData());
            priceExpDataPo.setId(mainId);
            Boolean result = priceExpDataService.updById(priceExpDataPo);
            if (!result) {
                throw new AplException(ExpListServiceCode.ID_IS_NOT_EXITS.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg);
            }
            //更新轴数据
            PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
            priceExpAxisPo.setAxisTransverse(priceExpDataUpdDto.getAxisTransverse().toString());
            priceExpAxisPo.setAxisPortrait(priceExpDataUpdDto.getAxisPortrait().toString());
            priceExpAxisPo.setId(mainId);
            Boolean res = priceExpAxisService.updateByMainId(priceExpAxisPo);
            if (!res) {
                throw new AplException(ExpListServiceCode.ID_IS_NOT_EXITS.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg);
            }
            //更新主表数据
            PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
            priceExpMainPo.setId(mainId);
            priceExpMainPo.setStartWeight(priceExpDataUpdDto.getStartWeight());
            priceExpMainPo.setEndWeight(priceExpDataUpdDto.getEndWeight());
            priceExpMainPo.setPriceFormat(priceExpDataUpdDto.getPriceFormat());
            Integer resInt = baseMapper.updateById(priceExpMainPo);
            if (resInt < 1) {
                throw new AplException(ExpListServiceCode.ID_IS_NOT_EXITS.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg);
            }

        } else {
            throw new AplException(ExpListServiceCode.CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE.code,
                    ExpListServiceCode.CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE.msg, null);
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

        //不是公布价且不是成本价且不是销售价, 有服务商即成本价, 允许既是公布价又是成本价
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

//            priceExpMainDao.createRealTable();

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
            if (null != priceExpAddDto.getPartnerRemark()) {
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
        //如果既是公布价又有客户或客户组, 则算是公布价,不插入销售价
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

        List<PriceListForDelBatchBo> priceList = null;
        if (priceType == 1) {
            //销售价 id, quote_price_id price_main_id
            priceList = priceExpSaleService.getPriceListForDel(priceIdList);
        } else {
            //成本价 id, quote_price_id price_main_id
            priceList = priceExpCostService.getPriceListForDel(priceIdList);
        }
        if(priceList.isEmpty()){
            throw new AplException(ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.code, ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.msg,null);
        }

        Map<Long, Long> costPriceMaps = new HashMap<>();
        Map<Long, Long> salePriceMaps = new HashMap<>();

        //成本表id, 拼接可变字符串
        StringBuilder sbTogethorDelSalePriceIds = new StringBuilder();
        StringBuilder sbTogethorDelCostPriceIds = new StringBuilder();
        StringBuilder sbQuotePriceIds = new StringBuilder();
        StringBuilder sbMainPriceIds = new StringBuilder();
        for (PriceListForDelBatchBo priceListForDelBatchBo : priceList) {
//            if (sbQuotePriceIds.length() > 0)
//                sbQuotePriceIds.append(",");
//            //别的价格引用这个价格
//            sbQuotePriceIds.append(priceListForDelBatchBo.getId());
//
//            if(priceListForDelBatchBo.getQuotePriceId() > 0) {
//                sbQuotePriceIds.append(",");
//                //别的价格和这个价格, 引用相同的价格
//                sbQuotePriceIds.append(priceListForDelBatchBo.getQuotePriceId());
//            }

            if (sbMainPriceIds.length() > 0)
                sbMainPriceIds.append(",");
            sbMainPriceIds.append(priceListForDelBatchBo.getPriceMainId());
        }

        if (priceType.equals(1)) {  //如果选择删除销售价格表
            //  costPriceList = select cost.id, cost.main_price_id from join price_exp_cost  where price_main_id in (mainPriceList)
            //同时查出成本价id及price_main_id
            List<PriceListForDelBatchBo> costPriceList = baseMapper.getCostPriceListByMainIds(sbMainPriceIds.toString());
            if(null!=costPriceList && costPriceList.size()>0) {
                for (PriceListForDelBatchBo priceExpCostListVo : costPriceList) {
                    //map中添加成本表的price_main_id

                    costPriceMaps.put(priceExpCostListVo.getPriceMainId(), priceExpCostListVo.getPriceMainId());

//                    if (sbTogethorDelCostPriceIds.length() > 0)
//                        sbTogethorDelCostPriceIds.append(",");
//                    sbTogethorDelCostPriceIds.append(priceExpCostListVo.getId());
                }
            }
        } else if (priceType.equals(2)){
            //List  salePriceList = select sale.id, sale.main_price_id from  price_exp_sale  where price_main_id in (mainPriceList)
            //同时查出销售价id及price_main_id
            List<PriceListForDelBatchBo> salePriceList = baseMapper.getSalePriceListByMainIds(sbMainPriceIds.toString());
            if(null!=salePriceList && salePriceList.size()>0) {
                for (PriceListForDelBatchBo priceExpSaleListVo : salePriceList) {
                    //map中添加销售表的主表id
                    salePriceMaps.put(priceExpSaleListVo.getPriceMainId(), priceExpSaleListVo.getPriceMainId());

//                    if (sbTogethorDelSalePriceIds.length() > 0)
//                        sbTogethorDelSalePriceIds.append(",");
//                    sbTogethorDelSalePriceIds.append(priceExpSaleListVo.getId());
                }
            }
        }

        //拼接主表id
        StringBuffer sbTogethorDelPriceMainIds = new StringBuffer();
        for (PriceListForDelBatchBo priceListForDelBatchBo : priceList) {

            if (priceListForDelBatchBo.getQuotePriceId()>0)
                continue;

            if (delSaleAndCost) {
                //如果同时删两张表
                if (sbTogethorDelPriceMainIds.length() > 0)
                    sbTogethorDelPriceMainIds.append(",");
                sbTogethorDelPriceMainIds.append(priceListForDelBatchBo.getPriceMainId());
            }
            else {
                //如果删除销售价 并且 map中不含有成本价对应的主表id
                if (priceType.equals(1)) {
                    if (!costPriceMaps.containsKey(priceListForDelBatchBo.getPriceMainId())) {

                        if (sbTogethorDelPriceMainIds.length() > 0)
                            sbTogethorDelPriceMainIds.append(",");
                        sbTogethorDelPriceMainIds.append(priceListForDelBatchBo.getPriceMainId());
                    }
                } else if (priceType.equals(2)) {
                    if (!salePriceMaps.containsKey(priceListForDelBatchBo.getPriceMainId())) {

                        if (sbTogethorDelPriceMainIds.length() > 0)
                            sbTogethorDelPriceMainIds.append(",");
                        sbTogethorDelPriceMainIds.append(priceListForDelBatchBo.getPriceMainId());
                    }
                }
            }
        }

        if (sbTogethorDelPriceMainIds.length() > 0) {
            //  delete price_exp_main  where id in (sbTogethorDelPriceMainIds)
            baseMapper.delBatchs(sbTogethorDelPriceMainIds.toString());
        }

        //价格表没有引用其他价格时 sbDelPriceMainIds才会拼接主表id 删除轴和价格表数据
        if (sbTogethorDelPriceMainIds.length() > 0) {
            priceExpAxisService.delBatch(sbTogethorDelPriceMainIds.toString());
            priceExpDataService.delBatch(sbTogethorDelPriceMainIds.toString());
        }

        //拼接价格表ids
        StringBuffer sbPriceIds = new StringBuffer();
        for (PriceListForDelBatchBo priceListForDelBatchBo : priceList) {
            if (sbPriceIds.length() > 0)
                sbPriceIds.append(",");
            sbPriceIds.append(priceListForDelBatchBo.getId());
        }
        //根据ids删除价格表
        if (priceType == 1)
            priceExpSaleService.delBatch(sbPriceIds.toString());
        else
            priceExpCostService.delBatch(sbPriceIds.toString());

        // 同时删除销售价和成本价
        if (delSaleAndCost) {
            if (priceType == 1 && sbTogethorDelCostPriceIds.length()>0) {
                priceExpCostService.delBatch(sbTogethorDelCostPriceIds.toString());
            }
            else if (priceType == 2 && sbTogethorDelSalePriceIds.length()>0) {
                priceExpSaleService.delBatch(sbTogethorDelSalePriceIds.toString());
            }
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

    /**
     * 获取价格表数据和轴数据
     * @param id 价格表id
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<PriceExpDataAxisVo> getPriceExpDataAxis(Long id) {

        ResultUtil<PriceExpDataVo> priceExpDataInfo = priceExpDataService.getPriceExpDataInfoByPriceId(id);
        PriceExpDataVo priceExpDataVo = priceExpDataInfo.getData();
        ResultUtil<PriceExpAxisVo> axisInfo = null;
        if(null != priceExpDataVo) {
            axisInfo = priceExpAxisService.getAxisInfoById(priceExpDataVo.getId());
        }else{
            return ResultUtil.APPRESULT(ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.code,
                    ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.msg, null);
        }
        PriceExpDataAxisVo priceExpDataAxisVo = new PriceExpDataAxisVo();
        BeanUtil.copyProperties(axisInfo.getData(), priceExpDataAxisVo);
        priceExpDataAxisVo.setPriceData(priceExpDataVo.getPriceData());

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpDataAxisVo);
    }
}