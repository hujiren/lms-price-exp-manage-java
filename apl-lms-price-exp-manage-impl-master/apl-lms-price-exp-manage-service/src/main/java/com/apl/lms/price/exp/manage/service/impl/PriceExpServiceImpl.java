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
import com.apl.lms.net.PartnerNetService;
import com.apl.lms.net.pojo.bo.PartnerApiInfoBo;
import com.apl.lms.price.exp.lib.feign.PriceExpFeign;
import com.apl.lms.price.exp.manage.dao.DevelopDao;
import com.apl.lms.price.exp.manage.dao.PriceListDao;
import com.apl.lms.price.exp.manage.mapper.PriceExpMapper;
import com.apl.lms.price.exp.manage.service.*;
import com.apl.lms.price.exp.manage.util.CheckObjFieldINull;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.po.*;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
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
        PRICE_EXP_AXIS_SAVE_DATA_FAILED("PRICE_EXP_AXIS_SAVE_DATA_FAILED", "保存价格表轴数据失败"),
        PRICE_EXP_DATA_SAVE_DATA_FAILED("PRICE_EXP_DATA_SAVE_DATA_FAILED", "保存价格表数据失败"),
        price_exp_remark_SAVE_DATA_FAILED("price_exp_remark_SAVE_DATA_FAILED", "保存价格表扩展数据失败"),
        ID_IS_NOT_EXIST("ID_IS_NOT_EXIT", "id不存在"),
        THERE_IS_NO_CORRESPONDING_DATA("THERE_IS_NO_CORRESPONDING_DATA", "没有对应数据"),
        PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP(
                "PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP",
                "服务商, 客户组, 客户, 请至少填写一组"),
        CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE("CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE", "无法修改源于别处的数据"),
        PLEASE_FILL_IN_THE_DATA_FIRST("PLEASE_FILL_IN_THE_DATA_FIRST", "请先填写数据"),
        THE_MAIN_TABLE_DOES_NOT_EXIST_AND_CANNOT_BE_UPDATED("THE_MAIN_TABLE_DOES_NOT_EXIST_AND_CANNOT_BE_UPDATED", "主表不存在,无法更新"),
        SYNCHRONOUS_SUCCESS("SYNCHRONOUS_SUCCESS", "同步成功"),
        UNBOUND_PROFIT_RETURN_NAKED_PRICE("UNBOUND_PROFIT_RETURN_NAKED_PRICE", "没有绑定利润, 返回裸价格"),
        THIS_PRICE_HAS_BEEN_QUOTED_PLEASE_SYNCHRONIZE_DIRECTLY("THIS_PRICE_HAS_BEEN_QUOTED_PLEASE_SYNCHRONIZE_DIRECTLY", "该价格已经被引用过, 请直接同步"),
        THE_REFERENCE_PRICE_HAS_BEEN_REMOVED("THE_REFERENCE_PRICE_HAS_BEEN_REMOVED", "引用价格已被删除");

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

    @Autowired
    PriceExpDataService priceExpDataService;

    @Autowired
    PriceExpRemarkService priceExpRemarkService;

    @Autowired
    PriceExpAxisService priceExpAxisService;

    @Autowired
    ComputationalFormulaService computationalFormulaService;

    @Autowired
    CheckObjFieldINull checkObjFieldINull;

    @Autowired
    PriceExpProfitService priceExpProfitService;

    @Autowired
    PriceZoneNameService priceZoneNameService;

    @Autowired
    PriceExpFeign priceExpFeign;

    @Autowired
    PriceListDao priceListDao;

    @Autowired
    SysInnerOrgService sysInnerOrgService;

    @Autowired
    PriceSurchargeService priceSurchargeService;

    @Autowired
    DevelopDao developDao;

    @Autowired
    PriceZoneDataService priceZoneDataService;

    @Autowired
    UnifyProfitService unifyProfitService;

    static JoinFieldInfo joinSpecialCommodityFieldInfo = null; //跨项目跨库关联 特殊物品 反射字段缓存

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
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        keyDto.setInnerOrgId(securityUser.getInnerOrgId());
        List<PriceExpSaleListVo> priceExpSaleList = baseMapper.getPriceExpSaleList(page, keyDto);

        page.setRecords(priceExpSaleList);

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
    public ResultUtil<Page<PriceExpCostListVo>> getPriceExpCostList(PageDto pageDto, PriceExpCostKeyDto keyDto) throws Exception {
        Page<PriceExpCostListVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
        if (null != keyDto && keyDto.getChannelCategory() != null) {
            keyDto.setChannelCategory(keyDto.getChannelCategory().toUpperCase());
        }
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        keyDto.setInnerOrgId(securityUser.getInnerOrgId());
        List<PriceExpCostListVo> priceExpListVoCostList = baseMapper.getPriceExpCostList(page, keyDto);
        page.setRecords(priceExpListVoCostList);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }


    /**
     * 分页查询公布价格列表
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
        if (null != keyDto && keyDto.getChannelCategory() != null) {
            keyDto.setChannelCategory(keyDto.getChannelCategory().toUpperCase());
        }
        List<PriceExpCostListVo> priceExpListVoCostList = baseMapper.getPublishedPriceList(page, keyDto);

        page.setRecords(priceExpListVoCostList);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 获取价格详情
     *
     * @param id 价格表主键Id
     * @return
     */
    @Override
    public ResultUtil<PriceExpPriceInfoVo> getPriceExpInfo(Long id) throws Exception {

        SecurityUser securityUser = CommonContextHolder.getSecurityUser();

        //查询客户信息
        PriceExpSaleVo priceExpSaleVo = baseMapper.getCustomerInfo(id);

        //查询价格表
        PriceExpPriceInfoVo priceExpPriceInfoVo = baseMapper.getPriceExpInfoById(id);

        //查询公布价名称
        if (null != priceExpPriceInfoVo.getPricePublishedId() && priceExpPriceInfoVo.getPricePublishedId() > 0) {
            PriceExpPriceInfoVo priceExpPriceInfoVo2 = baseMapper.getPriceExpInfoById(priceExpPriceInfoVo.getPricePublishedId());
            priceExpPriceInfoVo.setPublishedName(priceExpPriceInfoVo2.getPriceName());
        }


        priceExpPriceInfoVo.setOrgCode(securityUser.getInnerOrgCode());
        Integer customerIsNull = 0;
        Integer customerGroupIsNull = 0;
        if (null == priceExpSaleVo.getCustomerIds() || priceExpSaleVo.getCustomerIds().equals("")) {
            customerIsNull = 1;
        }
        if (null == priceExpSaleVo.getCustomerGroupId() || priceExpSaleVo.getCustomerGroupId().equals("")) {
            customerGroupIsNull = 1;
        }
        if (priceExpPriceInfoVo == null) {
            throw new AplException(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg, null);
        }

        //组装分区名称
        String priceZoneName = priceZoneNameService.getPriceZoneName(priceExpPriceInfoVo.getZoneId());
        if (null != priceZoneName) {
            priceExpPriceInfoVo.setZoneName(priceZoneName);
        }

        //组装客户List (客户id, 客户名称)
        if (null != priceExpSaleVo && !priceExpSaleVo.getCustomerIds().equals("") && priceExpSaleVo.getCustomerIds() != null && priceExpSaleVo.getCustomerName() != null) {


            String customerIds = priceExpSaleVo.getCustomerIds().replaceAll(" ", "");
            String customerNames = priceExpSaleVo.getCustomerName().replaceAll(" ", "");

            List<CustomerDto> customerDtoList = new ArrayList<>();

            String[] customerIdArr = customerIds.split(",");
            String[] customerNameArr = customerNames.split(",");
            for (int i = 0; i < customerIdArr.length; i++) {
                CustomerDto customerDto = new CustomerDto();
                if (null != customerIdArr[i] && !customerIdArr[i].equals("")) {
                    customerDto.setCustomerId(Long.valueOf(customerIdArr[i]));
                }
                customerDto.setCustomerName(customerNameArr[i]);
                customerDtoList.add(customerDto);
            }
            priceExpPriceInfoVo.setCustomer(customerDtoList);
        }

        //组装客户组List(客户组id, 客户组名称)
        if (priceExpSaleVo.getCustomerGroupId() != null && !priceExpSaleVo.getCustomerGroupName().equals("") && priceExpSaleVo.getCustomerGroupName() != null) {
            String customerGroupIds = priceExpSaleVo.getCustomerGroupId().replaceAll(" ", "");
            String customerGroupName = priceExpSaleVo.getCustomerGroupName().replaceAll(" ", "");

            List<CustomerGroupDto> customerGroupDtoList = new ArrayList<>();
            String[] customerGroupIdArr = customerGroupIds.split(",");
            String[] customerGroupNameArr = customerGroupName.split(",");

            for (int i = 0; i < customerGroupIdArr.length; i++) {
                CustomerGroupDto customerGroupDto = new CustomerGroupDto();
                if (null != customerGroupIdArr[i] && !customerGroupIdArr[i].equals("")) {
                    customerGroupDto.setCustomerGroupId(Long.valueOf(customerGroupIdArr[i]));
                }
                customerGroupDto.setCustomerGroupName(customerGroupNameArr[i]);
                customerGroupDtoList.add(customerGroupDto);
            }
            priceExpPriceInfoVo.setCustomerGroup(customerGroupDtoList);
        }

        //组装特殊物品List
        if (priceExpPriceInfoVo.getSpecialCommodityStr() != null && !priceExpPriceInfoVo.getSpecialCommodityStr().equals("") && !priceExpPriceInfoVo.getSpecialCommodityStr().equals("")) {
            String specialCommodityStr = priceExpPriceInfoVo.getSpecialCommodityStr().replaceAll(" ", "");

            List<SpecialCommodityDto> specialCommodityList = new ArrayList<>();
            //JoinCustomer
            String[] specialCommodityArr = specialCommodityStr.replace("{", "").replace("}", "").split(",");

            for (int i = 0; i < specialCommodityArr.length; i++) {
                SpecialCommodityDto specialCommodityDto = new SpecialCommodityDto();
                if (null != specialCommodityArr[i] && !specialCommodityArr[i].equals(""))
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

            priceExpPriceInfoVo.setSpecialCommodity(specialCommodityList);
            priceExpPriceInfoVo.setSpecialCommodityStr(null);

        }
        if (customerIsNull == 1) {
            priceExpPriceInfoVo.setCustomer(null);
        }
        if (customerGroupIsNull == 1) {
            priceExpPriceInfoVo.setCustomerGroup(null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpPriceInfoVo);
    }



    public PriceExpDataObjVo getPriceExpData(Long priceId,  Boolean isSaleProfit,  Long customerGroupId) throws Exception {

        ExpPriceInfoBo expPriceInfoBo = getPriceInfo(priceId);

        if (null == expPriceInfoBo) {
            throw new AplException(ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.code,
                    ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.msg, null);
        }

        PriceExpDataObjVo priceExpData = priceExpDataService.getPriceExpData(expPriceInfoBo, priceId, isSaleProfit, customerGroupId, false);

        return priceExpData;
    }


    /**
     * 获取quotePriceId, innerOrgId, priceDataId
     *
     * @param id
     * @return
     */
    @Override
    public ExpPriceInfoBo getPriceInfo(Long id) {
        return baseMapper.getPriceInfo(id);
    }


    /**
     * 更新价格主表
     *
     * @param priceExpUpdDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updExpPrice(PriceExpUpdDto priceExpUpdDto) {

        if (priceExpUpdDto.getIsPublishedPrice() != 1) {

            //不是销售价且不是客户价且没有服务商(不是成本价)
            if (priceExpUpdDto.getIsPublishedPrice().equals(2)
                    && (null == priceExpUpdDto.getPartnerId() || priceExpUpdDto.getPartnerId() < 1)
                    && (null == priceExpUpdDto.getCustomerGroup() || priceExpUpdDto.getCustomerGroup().size() < 1)
                    && (null == priceExpUpdDto.getCustomer() || priceExpUpdDto.getCustomer().size() < 1)) {

                throw new AplException(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                        ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg);
            }
        }

        if (null == priceExpUpdDto.getPriceSaleName() || priceExpUpdDto.getPriceSaleName().length() < 1)
            priceExpUpdDto.setPriceSaleName(priceExpUpdDto.getPriceName());

        //构建主表持久化对象
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpUpdDto, priceExpMainPo);
        if (null == priceExpMainPo.getPriceCode())
            priceExpMainPo.setPriceCode("");
        if (null == priceExpMainPo.getPartnerId()
                || priceExpMainPo.getPartnerId() < 1
                || null == priceExpMainPo.getPartnerName()
                || priceExpMainPo.getPartnerName().equals("")) {
            priceExpMainPo.setPartnerId(0L);
            priceExpMainPo.setPartnerName("");
        }
        if (null == priceExpMainPo.getPricePublishedId())
            priceExpMainPo.setPricePublishedId(0L);

        //处理特殊物品
        StringBuffer sbSpecialCommodity = new StringBuffer();
        if (null != priceExpUpdDto.getSpecialCommodity() && priceExpUpdDto.getSpecialCommodity().size() > 0) {
            List<SpecialCommodityDto> specialCommodity = priceExpUpdDto.getSpecialCommodity();
            for (SpecialCommodityDto specialCommodityDto : specialCommodity) {
                if (sbSpecialCommodity.length() > 0)
                    sbSpecialCommodity.append(",");
                sbSpecialCommodity.append(specialCommodityDto.getCode());
            }
        }
        priceExpMainPo.setSpecialCommodity(sbSpecialCommodity.toString());

        if (priceExpUpdDto.getIsPublishedPrice().equals(1)) {
            // 公布价, 客户、 客户组、服务商设为空
            priceExpMainPo.setCustomerGroupId("");
            priceExpMainPo.setCustomerGroupName("");

            priceExpMainPo.setCustomerIds("");
            priceExpMainPo.setCustomerName("");

            priceExpMainPo.setPartnerId(0l);
            priceExpMainPo.setPartnerName("");
        } else {
            //处理客户和客户组
            ExpPriceInnerClass expPriceInnerClass = disposeCustomerGroupAndCustomer(priceExpUpdDto.getCustomerGroup(), priceExpUpdDto.getCustomer());
            priceExpMainPo.setCustomerGroupId(expPriceInnerClass.customerGroupId);
            priceExpMainPo.setCustomerGroupName(expPriceInnerClass.customerGroupName);
            priceExpMainPo.setCustomerIds(expPriceInnerClass.customerIds);
            priceExpMainPo.setCustomerName(expPriceInnerClass.customerName);
        }

        priceExpMainPo.setUpdTime(new Timestamp(System.currentTimeMillis()));
        if (null != priceExpMainPo.getQuotePriceId() && priceExpMainPo.getQuotePriceId() > 0) {
            Long id = priceExpMainPo.getQuotePriceId();
            String tenantCode = priceExpUpdDto.getQuoteTenantCode();

            PriceExpMainPo quotePriceExpMainPo = priceListDao.getQuotePriceUpdateTime(id, tenantCode);
            priceExpMainPo.setQuotePriceUpdTime(quotePriceExpMainPo.getUpdTime());
        }
        //更新价格表
        Integer integer = baseMapper.updById(priceExpMainPo);
        if (integer < 1) {
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, true);
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

        ExpPriceInfoBo expPriceInfoBo = baseMapper.getPriceInfo(priceExpDataUpdDto.getId());

        if (null == expPriceInfoBo || 0 == expPriceInfoBo.getInnerOrgId() || 0 == expPriceInfoBo.getPriceDataId()) {
            throw new AplException(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg);
        }

        if (securityUser.getInnerOrgId().equals(expPriceInfoBo.getInnerOrgId())) {

            //更新主表
            PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
            priceExpMainPo.setId(priceExpDataUpdDto.getId());
            priceExpMainPo.setStartWeight(priceExpDataUpdDto.getStartWeight());
            priceExpMainPo.setEndWeight(priceExpDataUpdDto.getEndWeight());
            priceExpMainPo.setPriceFormat(priceExpDataUpdDto.getPriceFormat());
            priceExpMainPo.setUpdTime(new Timestamp(System.currentTimeMillis()));
            Integer resInt = baseMapper.updateById(priceExpMainPo);
            if (resInt < 1) {
                throw new AplException(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg);
            }

            //更新价格表数据
            PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
            priceExpDataPo.setPriceData(priceExpDataUpdDto.getPriceData());
            priceExpDataPo.setId(expPriceInfoBo.getPriceDataId());
            Boolean result = priceExpDataService.updById(priceExpDataPo);
            if (!result) {
                throw new AplException(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg);
            }

            //更新数据轴
            PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
            priceExpAxisPo.setAxisTransverse(priceExpDataUpdDto.getAxisTransverse());
            priceExpAxisPo.setAxisPortrait(priceExpDataUpdDto.getAxisPortrait());
            priceExpAxisPo.setId(expPriceInfoBo.getPriceDataId());
            Boolean res = priceExpAxisService.updateByMainId(priceExpAxisPo);
            if (!res) {
                throw new AplException(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg);
            }

        } else {
            throw new AplException(ExpListServiceCode.CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE.code,
                    ExpListServiceCode.CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE.msg, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 更新表头(横向数据)
     *
     * @param
     * @return
     */
    @Override
    @Transactional
    public List<String> updTransverseWeightSection(WeightSectionUpdDto weightSectionUpdDto) {

        if (null == weightSectionUpdDto || weightSectionUpdDto.getWeightSection().size() == 0) {
            throw new AplException(ExpListServiceCode.PLEASE_FILL_IN_THE_DATA_FIRST.code, ExpListServiceCode.PLEASE_FILL_IN_THE_DATA_FIRST.msg);
        }

        //表格数据第一行拼接
        StringBuffer sbHeadCellVal = new StringBuffer();
        List<String> headCells = new ArrayList<>();
        Integer packType0 = 0;
        Double weightAdd0 = 0.0;
        Double weightStart;

        Double startWeight = 0.0;
        Double endWeight = 0.0;
        //更新轴数据
        PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
        List<List<String>> axisPortrait = new ArrayList<>();
        for (WeightSectionDto weightSectionDto : weightSectionUpdDto.getWeightSection()) {
            List<String> weightSectionList = new ArrayList<>();
            weightSectionList.add(String.valueOf(weightSectionDto.getIndex()));
            weightSectionList.add(String.valueOf(weightSectionDto.getPackType()));
            weightSectionList.add(String.valueOf(weightSectionDto.getChargingWay()));
            weightSectionList.add(String.valueOf(weightSectionDto.getWeightStart()));
            weightSectionList.add(String.valueOf(weightSectionDto.getWeightEnd()));
            weightSectionList.add(String.valueOf(weightSectionDto.getWeightAdd()));
            weightSectionList.add(String.valueOf(weightSectionDto.getWeightFirst()));
            axisPortrait.add(weightSectionList);
            sbHeadCellVal.setLength(0);

            //包裹类型
            if (!weightSectionDto.getPackType().equals(packType0)) {
                if (weightSectionDto.getPackType().equals(1)) {
                    sbHeadCellVal.append("DOC ");
                } else if (weightSectionDto.getPackType().equals(2)) {
                    if (packType0 > 0) {
                        sbHeadCellVal.append("WPX ");
                    }
                } else if (weightSectionDto.getPackType().equals(3)) {
                    sbHeadCellVal.append("PAK ");
                }

                weightAdd0 = 0.0;
            }

            //重量区间　chargingWay 1首重 2续重  3累加  4单位重  5计算好
            if (weightSectionDto.getWeightStart() > 0.5 || weightSectionDto.getChargingWay().equals(4)) {
                if (weightAdd0.equals(0.0)) {
                    if (weightSectionDto.getChargingWay().equals(2) || weightSectionDto.getChargingWay().equals(3))
                        weightAdd0 = 0.5;
                    else if (weightSectionDto.getChargingWay().equals(4))
                        weightAdd0 = 1.0;
                }
                weightStart = weightSectionDto.getWeightStart() + weightAdd0;
                sbHeadCellVal.append(String.format("%.2f", weightStart));
            }

            if (weightSectionDto.getChargingWay().equals(4)) {
                if (weightSectionDto.getWeightEnd() < 10000.0) {
                    sbHeadCellVal.append("-");
                    sbHeadCellVal.append(String.format("%.2f", weightSectionDto.getWeightEnd()));
                }
            }

            //单位重和计算好, 加上KG
            if (weightSectionDto.getChargingWay().equals(4) || weightSectionDto.getChargingWay().equals(5)) {
                sbHeadCellVal.append("KG");
                if (weightSectionDto.getWeightEnd() >= 10000.0)
                    sbHeadCellVal.append("+");
            }

            //首续累加
            if (weightSectionDto.getChargingWay().equals(1)) {
                sbHeadCellVal.append("首");
                sbHeadCellVal.append(String.format("%.2f", weightSectionDto.getWeightFirst()));
            } else if (weightSectionDto.getChargingWay().equals(2)) {
                sbHeadCellVal.append("续");
                sbHeadCellVal.append(String.format("%.2f", weightSectionDto.getWeightAdd()));
            } else if (weightSectionDto.getChargingWay().equals(3)) {
                sbHeadCellVal.append("累加");
                sbHeadCellVal.append(String.format("%.2f", weightSectionDto.getWeightAdd()));
            }
            headCells.add(sbHeadCellVal.toString()
                    .replace(".50", ".5")
                    .replace(".00", "")
                    .replace(".0", ""));

            packType0 = weightSectionDto.getPackType();

            if (weightSectionDto.getWeightAdd() > 0)
                weightAdd0 = weightSectionDto.getWeightAdd();
        }

        priceExpAxisPo.setAxisTransverse(axisPortrait);
        priceExpAxisPo.setId(weightSectionUpdDto.getPriceDataId());
        Long checkMainId = baseMapper.getMainIdByPriceDataId(weightSectionUpdDto.getPriceDataId());

        if (checkMainId == null || checkMainId.equals(0))
            throw new AplException(ExpListServiceCode.THE_MAIN_TABLE_DOES_NOT_EXIST_AND_CANNOT_BE_UPDATED.code,
                    ExpListServiceCode.THE_MAIN_TABLE_DOES_NOT_EXIST_AND_CANNOT_BE_UPDATED.msg);

        //更新主表的
        startWeight = weightSectionUpdDto.getWeightSection().get(0).getWeightStart();
        endWeight = weightSectionUpdDto.getWeightSection().get(weightSectionUpdDto.getWeightSection().size() - 1).getWeightEnd();

        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        priceExpMainPo.setId(checkMainId);
        priceExpMainPo.setStartWeight(startWeight);
        priceExpMainPo.setEndWeight(endWeight);
        Integer resInteger = baseMapper.upd(priceExpMainPo);

        if (resInteger < 1)
            throw new AplException(CommonStatusCode.SAVE_FAIL, null);
        //构建新的表头
        List<String> newHeadCells = priceExpDataService.updHeadCells(weightSectionUpdDto, headCells);
        if (newHeadCells.size() < 1)
            throw new AplException(CommonStatusCode.SAVE_FAIL, null);

        Boolean result = priceExpAxisService.updateByMainId(priceExpAxisPo);
        if (!result) {
            throw new AplException(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg);
        }
        return newHeadCells;
    }

    /**
     * 新增快递价格
     *
     * @param priceExpAddDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Long> addExpPrice(PriceExpAddDto priceExpAddDto) throws Exception {

        Long priceDataId = SnowflakeIdWorker.generateId();

        ResultUtil<Long> longResultUtil = addExpPriceBase(priceExpAddDto, 0l, priceDataId, null);

        //保存价格表数据
        Boolean saveSuccess = priceExpDataService.addPriceExpData(priceDataId, priceExpAddDto);
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.msg, null);
        }
        //保存价格表轴数据
        saveSuccess = priceExpAxisService.addPriceExpAxis(priceDataId, priceExpAddDto);
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.msg, null);
        }


        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, longResultUtil.getData());
    }

    /**
     * 同步价格表
     *
     * @param priceIds
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> syncPrice(List<Long> priceIds) throws Exception {

        //根据ids获取将要更新的主表列表 id, quote_price_id, quote_price_upd_time, upd_time, inner_org_id, quote_tenant_code
        List<PriceExpMainPo> priceExpMainList = baseMapper.getList(priceIds);

        if (null != priceExpMainList && priceExpMainList.size() > 0) {

            for (PriceExpMainPo priceExpMainPo : priceExpMainList) {
                if (priceExpMainPo.getQuotePriceId() < 1) {
                    continue;
                }

                //获取主表信息
                PriceExpMainPo quotePriceInfo = priceListDao.getRealPriceInfo(priceExpMainPo.getQuotePriceId(), priceExpMainPo.getQuoteTenantCode());
                Long priceId = priceExpMainPo.getId();
                Long quotePriceId = quotePriceInfo.getId();
                if (null == quotePriceInfo || quotePriceInfo.getId() < 1 || quotePriceInfo.getId() == null) {
                    //更新同步状态为3 引用的价格表已被删除
                    PriceExpMainPo priceExpMainPo1 = new PriceExpMainPo();
                    priceExpMainPo1.setId(priceExpMainPo.getId());
                    priceExpMainPo1.setUpdTime(new Timestamp(System.currentTimeMillis()));
                    priceExpMainPo1.setSynStatus(3);
                    baseMapper.updateById(priceExpMainPo1);
                    continue;
                }
                //说明已经同步过, 则该价格不需要同步
                if (quotePriceInfo.getUpdTime().equals(priceExpMainPo.getQuotePriceUpdTime()))
                    continue;

                PartnerApiInfoBo partnerApiInfoBo = PartnerNetService.getSysApiInfo(priceExpMainPo.getPartnerId());
                //组装新的主表并执行更新()
                PriceExpMainPo myPriceExpMainPo = new PriceExpMainPo();
                BeanUtil.copyProperties(quotePriceInfo, myPriceExpMainPo);
                myPriceExpMainPo.setId(priceId);
                myPriceExpMainPo.setPartnerId(priceExpMainPo.getPartnerId());
                myPriceExpMainPo.setPartnerName(priceExpMainPo.getPartnerName());
                myPriceExpMainPo.setCustomerIds(priceExpMainPo.getCustomerIds());
                myPriceExpMainPo.setCustomerName(priceExpMainPo.getCustomerName());
                myPriceExpMainPo.setCustomerGroupId(priceExpMainPo.getCustomerGroupId());
                myPriceExpMainPo.setCustomerGroupName(priceExpMainPo.getCustomerGroupName());
                myPriceExpMainPo.setChannelCategory(priceExpMainPo.getChannelCategory());
                myPriceExpMainPo.setQuotePriceId(quotePriceId);
                myPriceExpMainPo.setIsPublishedPrice(2);
                myPriceExpMainPo.setUpdTime(new Timestamp(System.currentTimeMillis()));
                myPriceExpMainPo.setQuotePriceUpdTime(quotePriceInfo.getUpdTime());
                myPriceExpMainPo.setIsQuote(1);
                myPriceExpMainPo.setSynStatus(1);
                myPriceExpMainPo.setAddProfitWay(priceExpMainPo.getAddProfitWay());

                //租户利润的客户组id
                Long quotePriceCustomerGroupId = 0l;
                Long quotePriceCustomerId = 0L;
                if (priceExpMainPo.getPartnerId() > 0) {
                    quotePriceCustomerGroupId = partnerApiInfoBo.getCustomerGroupId();
                    quotePriceCustomerId = partnerApiInfoBo.getCustomerId();
                }
                //myPriceExpMainPo.setQuotePriceCustomerGroupId(quotePriceCustomerGroupId);
                //myPriceExpMainPo.setQuotePriceCustomerId(quotePriceCustomerId);
                baseMapper.updateById(myPriceExpMainPo);

                //将引用价格的销售备注改为本价格服务商备注 ok
                PriceExpRemarkPo quotePriceExpRemark = priceExpRemarkService.getTenantPriceRemark(quotePriceId);
                PriceExpRemarkPo myPriceRemarkPo = new PriceExpRemarkPo();
                myPriceRemarkPo.setId(myPriceExpMainPo.getId());
                myPriceRemarkPo.setRemark(quotePriceExpRemark.getSaleRemark());
                priceExpRemarkService.updateRemark(myPriceRemarkPo);

                //获取及更新附加费:多行 替换掉自己的附加费 ok
                List<PriceSurchargePo> quotePriceSurchargeList = priceSurchargeService.getById(quotePriceId);
                if (null != quotePriceSurchargeList && quotePriceSurchargeList.size() > 0) {
                    for (PriceSurchargePo priceSurchargePo : quotePriceSurchargeList) {
                        priceSurchargePo.setPriceId(priceId);
                        priceSurchargePo.setId(SnowflakeIdWorker.generateId());
                    }
                    List<Long> surchargeIds = priceSurchargeService.getIdBatch(priceId);
                    developDao.updSurcharge(quotePriceSurchargeList, surchargeIds);
                }

                //获取及更新公式并替换掉自己的公式:多行
                List<PriceExpComputationalFormulaPo> quoteComputationList = computationalFormulaService.getTenantComputationalFormula(quotePriceId);
                if (null != quoteComputationList && quoteComputationList.size() > 0) {
                    for (PriceExpComputationalFormulaPo priceExpComputationalFormulaPo : quoteComputationList) {
                        priceExpComputationalFormulaPo.setPriceId(myPriceExpMainPo.getId());
                        priceExpComputationalFormulaPo.setId(SnowflakeIdWorker.generateId());
                    }
                    List<Long> computationIds = computationalFormulaService.getIdBatch(priceId);
                    developDao.updComputation(quoteComputationList, computationIds);
                }

                //获取引用价格利润
                ExpPriceProfitDto profitDto = priceExpProfitService.getProfit(quotePriceId,
                        quotePriceCustomerGroupId,
                        quotePriceInfo.getAddProfitWay(),
                        partnerApiInfoBo.getSysTenantId());

                //合并引用价格利润为本价格的成本利润
                List<PriceExpProfitDto> costProfitList = priceExpProfitService.assembleSaleProfit(profitDto);

                //更新成本利润
                ExpPriceProfitDto expPriceProfitDto = new ExpPriceProfitDto();
                expPriceProfitDto.setId(priceId);
                expPriceProfitDto.setCostProfit(costProfitList);
                priceExpProfitService.saveProfit(expPriceProfitDto);
            }
        }

        return ResultUtil.APPRESULT(ExpListServiceCode.SYNCHRONOUS_SUCCESS.code, ExpListServiceCode.SYNCHRONOUS_SUCCESS.msg, true);
    }

    /**
     * 引用价格表
     * 引用即新建
     * @param priceReferenceDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> referencePrice(PriceReferenceDto priceReferenceDto) throws Exception {
        try {
            PartnerApiInfoBo partnerApiInfoBo = PartnerNetService.getSysApiInfo(priceReferenceDto.getPartnerId());
            if (null == partnerApiInfoBo)
                return ResultUtil.APPRESULT(ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.code,
                        ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.msg, false);

            String quoteTenantCode = partnerApiInfoBo.getSysTenantCode();

            //从主表中copy代码过来
            Long quotePriceId = priceReferenceDto.getQuotePriceId();
            Long priceId = SnowflakeIdWorker.generateId();
            PriceExpMainPo sourcePriceMainInfo = priceListDao.getSourcePriceInfo(quotePriceId, quoteTenantCode);
            if(null == sourcePriceMainInfo)
                return ResultUtil.APPRESULT(ExpListServiceCode.THE_REFERENCE_PRICE_HAS_BEEN_REMOVED.code,
                        ExpListServiceCode.THE_REFERENCE_PRICE_HAS_BEEN_REMOVED.msg, null);
            PriceExpMainPo newPriceExpMainPo = new PriceExpMainPo();
            BeanUtil.copyProperties(sourcePriceMainInfo, newPriceExpMainPo);
            newPriceExpMainPo.setId(priceId);
            newPriceExpMainPo.setIsPublishedPrice(2);
            newPriceExpMainPo.setUpdTime(new Timestamp(System.currentTimeMillis()));
            newPriceExpMainPo.setIsQuote(1);
            newPriceExpMainPo.setSynStatus(1);
            newPriceExpMainPo.setQuotePriceId(quotePriceId);

            newPriceExpMainPo.setZoneId(sourcePriceMainInfo.getZoneId());
            newPriceExpMainPo.setPartnerId(priceReferenceDto.getPartnerId());
            newPriceExpMainPo.setPartnerName(priceReferenceDto.getPartnerName());
            newPriceExpMainPo.setPriceCode(priceReferenceDto.getPriceCode());
            newPriceExpMainPo.setQuotePriceUpdTime(sourcePriceMainInfo.getUpdTime());

            if (null == newPriceExpMainPo.getPriceCode())
                newPriceExpMainPo.setPriceCode("");
            newPriceExpMainPo.setPriceName(priceReferenceDto.getPriceName());
            if (null == priceReferenceDto.getPriceSaleName())
                newPriceExpMainPo.setPriceSaleName(priceReferenceDto.getPriceName());
            else
                newPriceExpMainPo.setPriceSaleName(priceReferenceDto.getPriceSaleName());
            newPriceExpMainPo.setChannelCategory(priceReferenceDto.getChannelCategory());
            newPriceExpMainPo.setAddProfitWay(priceReferenceDto.getAddProfitWay());
            newPriceExpMainPo.setQuoteTenantCode(quoteTenantCode);

            //处理客户和客户组
            ExpPriceInnerClass expPriceInnerClass = disposeCustomerGroupAndCustomer(priceReferenceDto.getCustomerGroup(), priceReferenceDto.getCustomer());
            newPriceExpMainPo.setCustomerName(expPriceInnerClass.customerName);
            newPriceExpMainPo.setCustomerIds(expPriceInnerClass.customerIds);
            newPriceExpMainPo.setCustomerGroupName(expPriceInnerClass.customerGroupName);
            newPriceExpMainPo.setCustomerGroupId(expPriceInnerClass.customerGroupId);

            Long quotePriceCustomerGroupId = partnerApiInfoBo.getCustomerGroupId();
            Long quotePriceCustomerId = partnerApiInfoBo.getCustomerId();
            Long quotePriceTenantId = partnerApiInfoBo.getSysTenantId();
            //newPriceExpMainPo.setQuotePriceCustomerGroupId(quotePriceCustomerGroupId);
            //newPriceExpMainPo.setQuotePriceCustomerId(quotePriceCustomerId);

            //创建租户真实表
            priceListDao.createRealTable();

            //保存
            Integer saveResult = baseMapper.addExpPrice(newPriceExpMainPo);
            if (saveResult < 1) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
            }

            //数据表数据,轴数据不需要复制 已排除租户id DB: price_exp_data, price_exp_axis

            //copy并保存备注 DB: price_exp_remark
            PriceExpRemarkPo priceExpRemarkPo = priceExpRemarkService.getTenantPriceRemark(quotePriceId);
            if (null != priceExpRemarkPo) {
                priceExpRemarkPo.setId(priceId);
                priceExpRemarkService.updateRemark(priceExpRemarkPo);
            }

            //copy并保存公式 DB: price_computational_formula
            List<PriceExpComputationalFormulaPo> ComputationalFormulaList = computationalFormulaService.getTenantComputationalFormula(quotePriceId);
            if (ComputationalFormulaList.size() > 0) {
                for (PriceExpComputationalFormulaPo priceExpComputationalFormulaPo : ComputationalFormulaList) {
                    priceExpComputationalFormulaPo.setId(SnowflakeIdWorker.generateId());
                    priceExpComputationalFormulaPo.setPriceId(priceId);
                }
                computationalFormulaService.saveBatch(ComputationalFormulaList);
            }

            //copy附加费
            List<PriceSurchargePo> surchargeList = priceSurchargeService.getById(quotePriceId);
            if (null != surchargeList && surchargeList.size() > 0) {
                for (PriceSurchargePo priceSurchargePo : surchargeList) {
                    priceSurchargePo.setId(SnowflakeIdWorker.generateId());
                    priceSurchargePo.setPriceId(priceId);
                }
                priceSurchargeService.save(surchargeList);
            }

            //获取引用价格利润
            ExpPriceProfitDto quotePriceProfit = priceExpProfitService.getProfit(priceReferenceDto.getQuotePriceId(),
                    quotePriceCustomerGroupId,
                    sourcePriceMainInfo.getAddProfitWay(),
                    quotePriceTenantId);

            //合并引用价格利润为本价格的成本利润
            List<PriceExpProfitDto> costProfitList = priceExpProfitService.assembleSaleProfit(quotePriceProfit);

            //保存成本利润
            ExpPriceProfitDto expPriceProfitDto = new ExpPriceProfitDto();
            expPriceProfitDto.setId(priceId);
            expPriceProfitDto.setCostProfit(costProfitList);
            priceExpProfitService.saveProfit(expPriceProfitDto);

        } catch (AplException e) {
            return ResultUtil.APPRESULT(e.getCode(), e.getMessage(), false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, true);
    }

    /**
     * 新增价格表
     * @param priceExpAddDto
     * @param quotePriceId
     * @param priceDataId
     * @param quoteTenantCode
     * @return
     * @throws Exception
     */
    ResultUtil<Long> addExpPriceBase(PriceExpAddBaseDto priceExpAddDto, Long quotePriceId, Long priceDataId, String quoteTenantCode) throws Exception {

        //不是公布价且不是成本价且不是销售价, 有服务商即成本价, 允许既是公布价又是成本价
        if (priceExpAddDto.getIsPublishedPrice().equals(2)
                && (null == priceExpAddDto.getPartnerId() || priceExpAddDto.getPartnerId() < 1)
                && (null == priceExpAddDto.getCustomerGroup() || priceExpAddDto.getCustomerGroup().size() < 1)
                && (null == priceExpAddDto.getCustomer() || priceExpAddDto.getCustomer().size() < 1)) {

            throw new AplException(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg);
        }

        if (null == priceExpAddDto.getPriceSaleName() || priceExpAddDto.getPriceSaleName().length() < 1 || priceExpAddDto.getPriceSaleName().equals(" "))
            priceExpAddDto.setPriceSaleName(priceExpAddDto.getPriceName());

        Long priceId = SnowflakeIdWorker.generateId();
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpAddDto, priceExpMainPo);
        priceExpMainPo.setId(priceId);
        priceExpMainPo.setPriceStatus(1);
        priceExpMainPo.setQuotePriceId(quotePriceId);
        priceExpMainPo.setPriceDataId(priceDataId);
        priceExpMainPo.setIsQuote(2);

        if (null == priceExpMainPo.getPriceCode())
            priceExpMainPo.setPriceCode("");
        if (null == priceExpMainPo.getAddProfitWay())
            priceExpMainPo.setAddProfitWay(1);
        if (priceExpMainPo.getStartWeight() < 0)
            priceExpMainPo.setStartWeight(0d);
        if (priceExpAddDto.getPricePublishedId() == null)
            priceExpMainPo.setPricePublishedId(0L);
        if (null == priceExpMainPo.getQuoteTenantCode())
            priceExpMainPo.setQuoteTenantCode("");
        if (null == priceExpMainPo.getSynStatus())
            priceExpMainPo.setSynStatus(0);

        if (null == priceExpAddDto.getPartnerId() || priceExpAddDto.getPartnerId() < 1
                || null == priceExpAddDto.getPartnerName() || priceExpAddDto.getPartnerName().equals("")) {
            priceExpMainPo.setPartnerId(0L);
            priceExpMainPo.setPartnerName("");
        }

        //处理特殊物品
        StringBuffer sbSpecialCommodity = new StringBuffer();
        if (null != priceExpAddDto.getSpecialCommodity() && priceExpAddDto.getSpecialCommodity().size() > 0) {
            List<SpecialCommodityDto> specialCommodity = priceExpAddDto.getSpecialCommodity();
            for (SpecialCommodityDto specialCommodityDto : specialCommodity) {
                if (sbSpecialCommodity.length() > 0)
                    sbSpecialCommodity.append(",");
                sbSpecialCommodity.append(specialCommodityDto.getCode());
            }
        }
        priceExpMainPo.setSpecialCommodity(sbSpecialCommodity.toString());


        if (priceExpAddDto.getIsPublishedPrice().equals(1)) {
            // 公布价, 客户、 客户组、服务商设为空
            priceExpMainPo.setCustomerGroupId("");
            priceExpMainPo.setCustomerGroupName("");

            priceExpMainPo.setCustomerIds("");
            priceExpMainPo.setCustomerName("");

            priceExpMainPo.setPartnerId(0l);
            priceExpMainPo.setPartnerName("");
        } else {
            //处理客户和客户组
            ExpPriceInnerClass expPriceInnerClass = disposeCustomerGroupAndCustomer(priceExpAddDto.getCustomerGroup(), priceExpAddDto.getCustomer());
            priceExpMainPo.setCustomerGroupId(expPriceInnerClass.customerGroupId);
            priceExpMainPo.setCustomerGroupName(expPriceInnerClass.customerGroupName);
            priceExpMainPo.setCustomerIds(expPriceInnerClass.customerIds);
            priceExpMainPo.setCustomerName(expPriceInnerClass.customerName);
        }

        //创建租户真实表
        priceListDao.createRealTable();

        priceExpMainPo.setUpdTime(new Timestamp(System.currentTimeMillis()));
        Integer saveResult = baseMapper.insert(priceExpMainPo);
        if (saveResult < 1) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
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
            priceExpRemarkPo.setSaleRemark(priceExpAddDto.getSaleRemark());
            Boolean saveSuccess = priceExpRemarkService.updateRemark(priceExpRemarkPo);
            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.msg, null);
            }
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
    public ResultUtil<Boolean> deletePriceBatch(List<Long> priceIdList) {

        StringBuffer sbPriceIds = new StringBuffer();
        for (Long id : priceIdList) {
            if (sbPriceIds.length() > 0) {
                sbPriceIds.append(",");
            }
            sbPriceIds.append(id);
        }

        //获取价格表数据id
        List<Long> priceDataIdList = baseMapper.getPriceDataIds(sbPriceIds.toString());
        StringBuffer sbPriceDataIds = new StringBuffer();
        for (Long priceDataId : priceDataIdList) {
            if (sbPriceDataIds.length() > 0) {
                sbPriceDataIds.append(",");
            }
            sbPriceDataIds.append(priceDataId);
        }

        //删除价格表
        baseMapper.delBatchs(sbPriceIds.toString());

        if (sbPriceDataIds.length() > 0) {
            //删除轴数据
            priceExpAxisService.delBatch(sbPriceDataIds.toString());
            //删除价格表数据
            priceExpDataService.delBatch(sbPriceDataIds.toString());
        }

        //根据id删除备注
        priceExpRemarkService.delBatch(sbPriceIds.toString());
        //删除公式
        computationalFormulaService.delBatch(sbPriceIds.toString());
        //删除附加费
        priceSurchargeService.delBatch(sbPriceIds.toString());
        //删除利润
        priceExpProfitService.delBatch(sbPriceIds.toString());

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 批量获取价格表信息
     *
     * @param ids
     * @return
     */
    @Override
    public List<ExpPriceInfoBo> getPriceInfoByIds(List<Long> ids) {
        List<ExpPriceInfoBo> priceInfoByIds = baseMapper.getPriceInfoByIds(ids);
        List<Long> zoneIds = new ArrayList<>();
        for (ExpPriceInfoBo priceInfo : priceInfoByIds) {
            if (null != priceInfo.getZoneId() && priceInfo.getZoneId() > 0)
                zoneIds.add(priceInfo.getZoneId());
        }
        if (zoneIds.size() > 0) {
            Map<Long, PriceZoneNamePo> priceZoneMap = priceZoneNameService.getPriceZoneNameBatch(zoneIds);

            for (Map.Entry<Long, PriceZoneNamePo> PriceZoneNamePoEntry : priceZoneMap.entrySet()) {
                for (ExpPriceInfoBo priceInfo : priceInfoByIds) {
                    if (PriceZoneNamePoEntry.getKey().equals(priceInfo.getZoneId()))
                        priceInfo.setZoneName(PriceZoneNamePoEntry.getValue().getZoneName());
                }
            }
        }
        return priceInfoByIds;
    }

    @Override
    public Integer updatePriceExpMain(PriceExpMainPo priceExpMainPo) {

//        String innerOrgCode = CommonContextHolder.getSecurityUser().getInnerOrgCode();
        return baseMapper.updPrice(priceExpMainPo);
    }

    /**
     * 处理客户和客户组
     * @param customerGroup
     * @param customer
     * @return
     */
    public ExpPriceInnerClass disposeCustomerGroupAndCustomer(List<CustomerGroupDto> customerGroup, List<CustomerDto> customer) {

        ExpPriceInnerClass expPriceInnerClass = new ExpPriceInnerClass();

        //处理客户组
        if (null != customerGroup && customerGroup.size() > 0
                && null != customerGroup.get(0).getCustomerGroupId()
                && null != customerGroup.get(0).getCustomerGroupName()) {
            StringBuffer sbCustomerGroupId = new StringBuffer();
            StringBuffer sbCustomerGroupName = new StringBuffer();
            for (CustomerGroupDto customerGroupDto : customerGroup) {
                if (sbCustomerGroupId.length() > 0)
                    sbCustomerGroupId.append(",");
                sbCustomerGroupId.append(customerGroupDto.getCustomerGroupId());
                if (sbCustomerGroupName.length() > 0)
                    sbCustomerGroupName.append(",");
                sbCustomerGroupName.append(customerGroupDto.getCustomerGroupName());
            }
            expPriceInnerClass.customerGroupId = sbCustomerGroupId.toString();
            expPriceInnerClass.customerGroupName = sbCustomerGroupName.toString();
        } else {
            expPriceInnerClass.customerGroupId = "";
            expPriceInnerClass.customerGroupName = "";
        }

        //处理客户
        if (null != customer && customer.size() > 0 && null != customer.get(0).getCustomerId() && null != customer.get(0).getCustomerName()) {

            StringBuffer sbCustomerId = new StringBuffer();
            StringBuffer sbCustomerName = new StringBuffer();
            for (CustomerDto customerDto : customer) {
                if (sbCustomerId.length() > 0)
                    sbCustomerId.append(",");
                sbCustomerId.append(customerDto.getCustomerId());
                if (sbCustomerName.length() > 0)
                    sbCustomerName.append(",");
                sbCustomerName.append(customerDto.getCustomerName());
            }
            expPriceInnerClass.customerIds = sbCustomerId.toString();
            expPriceInnerClass.customerName = sbCustomerName.toString();
        } else {
            expPriceInnerClass.customerIds = "";
            expPriceInnerClass.customerName = "";
        }

        return expPriceInnerClass;
    }

    @Override
    public ResultUtil<Boolean> isQuoteByExpPrice(Long quotePriceId) {
        Integer countNum = baseMapper.isQuoteByExpPrice(quotePriceId);
        if(countNum > 0)
            return ResultUtil.APPRESULT(ExpListServiceCode.THIS_PRICE_HAS_BEEN_QUOTED_PLEASE_SYNCHRONIZE_DIRECTLY.code,
                    ExpListServiceCode.THIS_PRICE_HAS_BEEN_QUOTED_PLEASE_SYNCHRONIZE_DIRECTLY.msg, false);

        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, true);
    }

    class ExpPriceInnerClass {
        public String customerGroupId;
        public String customerGroupName;
        public String customerIds;
        public String customerName;
    }
}