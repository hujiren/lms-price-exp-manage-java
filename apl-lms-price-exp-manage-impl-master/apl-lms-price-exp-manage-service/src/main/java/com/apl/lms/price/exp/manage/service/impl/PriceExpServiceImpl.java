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
import com.apl.lms.price.exp.lib.cache.JoinPartner;
import com.apl.lms.price.exp.lib.cache.bo.PartnerCacheBo;
import com.apl.lms.price.exp.lib.feign.PriceExpFeign;
import com.apl.lms.price.exp.manage.mapper.PriceExpMapper;
import com.apl.lms.price.exp.manage.service.*;
import com.apl.lms.price.exp.manage.util.CheckObjFieldINull;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.dto.CustomerDto;
import com.apl.lms.price.exp.pojo.dto.CustomerGroupDto;
import com.apl.lms.price.exp.pojo.po.*;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        ID_IS_NOT_EXIST("ID_IS_NOT_EXIT", "id不存在"),
        CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH("CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH", "客户id和客户名称不匹配"),
        THERE_IS_NO_CORRESPONDING_DATA("THERE_IS_NO_CORRESPONDING_DATA", "没有对应数据"),
        PARTNER_MUST_BE_NOT_ZERO("PARTNER_MUST_BE_NOT_ZERO", "服务商id不能为0"),
        PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP(
                "PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP",
                "服务商, 客户组, 客户, 请至少填写一组"),
        CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE("CANNOT_MODIFY_DATA_ORIGINATING_ELSEWHERE", "无法修改源于别处的数据"),
        THE_REFERENCED_DATA_DOES_NOT_EXIST("THE_REFERENCED_DATA_DOES_NOT_EXIST", "被引用的数据不存在"),
        THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE("THERE_IS_STILL_DATA_BOUND_TO_THE_PRIMARY_TABLE", "仍有其他数据绑定主表"),
        THE_DATA_FORMAT_MUST_BE_A_TWO_DIMENSIONAL_ARRAY("THE_DATA_FORMAT_MUST_BE_A_TWO_DIMENSIONAL_ARRAY","数据格式必须是二维数组"),
        PARAMETER_ERROR("PARAMETER_ERROR", "参数错误"),
        PLEASE_FILL_IN_THE_DATA_FIRST("PLEASE_FILL_IN_THE_DATA_FIRST", "请先填写数据"),
        THE_MAIN_TABLE_DOES_NOT_EXIST_AND_CANNOT_BE_UPDATED("THE_MAIN_TABLE_DOES_NOT_EXIST_AND_CANNOT_BE_UPDATED","主表不存在,无法更新");

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

    static JoinFieldInfo joinPartnerFieldInfo = null; //关联 服务商 反射字段缓存

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
        if(null != keyDto && keyDto.getChannelCategory() != null){
            keyDto.setChannelCategory(keyDto.getChannelCategory().toUpperCase());
        }
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        keyDto.setInnerOrgId(securityUser.getInnerOrgId());
        List<PriceExpCostListVo> priceExpListVoCostList = baseMapper.getPriceExpCostList(page, keyDto);
        page.setRecords(priceExpListVoCostList);

        JoinPartner joinPartner = new JoinPartner(1, priceExpFeign, aplCacheUtil);
        List<JoinBase> joinTabs = new ArrayList<>();
        //关联服务商
        if (null != joinPartnerFieldInfo) {
            joinPartner.setJoinFieldInfo(joinPartnerFieldInfo);
        } else {
            joinPartner.addField("partnerId", Long.class, "partnerShortName", "partnerName", String.class);
            joinPartnerFieldInfo = joinPartner.getJoinFieldInfo();
        }
        joinTabs.add(joinPartner);
        //执行跨项目跨库关联
        JoinUtil.join(priceExpListVoCostList, joinTabs);
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
     * @param id 价格表主键Id
     * @return
     */
    @Override
    public ResultUtil<PriceExpPriceInfoVo> getPriceExpSaleInfo(Long id) throws Exception {

        //查询客户信息
        PriceExpSaleVo priceExpSaleVo = baseMapper.getCustomerInfo(id);

        //查询价格表
        PriceExpPriceInfoVo priceExpPriceInfoVo = baseMapper.getPriceExpSaleInfoById(id);

        //组装服务商名称
        if(priceExpPriceInfoVo.getPartnerId()>0) {
            JoinPartner joinPartner = new JoinPartner(1, priceExpFeign, aplCacheUtil);
            PartnerCacheBo partnerCacheBo = joinPartner.getEntity(priceExpPriceInfoVo.getPartnerId());
            if(null!=partnerCacheBo)
                priceExpPriceInfoVo.setPartnerName(partnerCacheBo.getPartnerShortName());
        }

        if (priceExpPriceInfoVo == null) {
            throw new AplException(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg, null);
        }

        //组装分区名称
        ResultUtil<String> priceZoneNameResult = priceZoneNameService.getPriceZoneName(priceExpPriceInfoVo.getZoneId());
        if(null != priceZoneNameResult && null != priceZoneNameResult.getData()) {
            priceExpPriceInfoVo.setZoneName(priceZoneNameResult.getData());
        }

        //组装客户List (客户id, 客户名称)
        if (null != priceExpSaleVo && priceExpSaleVo.getCustomerIds() != null && priceExpSaleVo.getCustomerName() != null) {
            String customerIds = priceExpSaleVo.getCustomerIds().replace("[", "").replace("]", "").replaceAll(" ", "");
            String customerNames = priceExpSaleVo.getCustomerName().replace("[", "").replace("]", "").replaceAll(" ", "");

            List<CustomerDto> customerDtoList = new ArrayList<>();

            String[] customerIdArr = customerIds.split(",");
            String[] customerNameArr = customerNames.split(",");
            for (int i = 0; i < customerIdArr.length; i++) {
                CustomerDto customerDto = new CustomerDto();
                if(null != customerIdArr[i] && !customerIdArr[i].equals("")){
                    customerDto.setCustomerId(Long.valueOf(customerIdArr[i]));
                }
                customerDto.setCustomerName(customerNameArr[i]);
                customerDtoList.add(customerDto);
            }
            priceExpPriceInfoVo.setCustomer(customerDtoList);
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
                if(null != customerGroupIdArr[i] && !customerGroupIdArr[i].equals("")) {
                    customerGroupDto.setCustomerGroupsId(Long.valueOf(customerGroupIdArr[i]));
                }
                customerGroupDto.setCustomerGroupsName(customerGroupNameArr[i]);
                customerGroupDtoList.add(customerGroupDto);
            }
            priceExpPriceInfoVo.setCustomerGroup(customerGroupDtoList);
        }

        //组装特殊物品List
        if(priceExpPriceInfoVo.getSpecialCommodityStr()!=null && !priceExpPriceInfoVo.getSpecialCommodityStr().equals("[]")) {
            String specialCommodityStr = priceExpPriceInfoVo.getSpecialCommodityStr().replace("[", "").replace("]", "").replaceAll(" ", "");

            List<SpecialCommodityDto> specialCommodityList = new ArrayList<>();
            String[] specialCommodityArr = specialCommodityStr.replace("{", "").replace("}", "").split(",");

            for (int i = 0; i < specialCommodityArr.length; i++) {
                SpecialCommodityDto specialCommodityDto = new SpecialCommodityDto();
                if(null != specialCommodityArr[i] && !specialCommodityArr[i].equals(""))
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

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpPriceInfoVo);
    }

    /**
     * 获取价格表数据
     * @param id
     * @return
     */
    @Override
    public ResultUtil<PriceExpDataVo> getPriceExpDataInfoByPriceId(Long id) {
        ExpPriceInfoBo innerOrgIdAndPriceDatId = getInnerOrgIdAndPriceDatId(id);
        if(null == innerOrgIdAndPriceDatId){
            return ResultUtil.APPRESULT(ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.code,
                    ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.msg,null);
        }
        ResultUtil<PriceExpDataVo> resultUtil = priceExpDataService.getPriceExpDataInfoByPriceId(innerOrgIdAndPriceDatId.getPriceDataId());
        return resultUtil;
    }

    /**
     * 获取quotePriceId, innerOrgId, priceDataId
     * @param id
     * @return
     */
    @Override
    public ExpPriceInfoBo getInnerOrgIdAndPriceDatId(Long id) {
        return baseMapper.getInnerOrgIdAndPriceDatId(id);
    }

    /**
     * 获取价格表数据和轴数据
     * @param id 价格表id
     * @return
     */
    @Override
    public ResultUtil<PriceExpDataAxisVo> getPriceExpDataAxis(Long id) {

        ExpPriceInfoBo innerOrgIdAndPriceDatId = baseMapper.getInnerOrgIdAndPriceDatId(id);

        if(null == innerOrgIdAndPriceDatId || innerOrgIdAndPriceDatId.getPriceDataId() == 0){
            return  ResultUtil.APPRESULT(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg, null);
        }
        //获取价格表数据
        ResultUtil<PriceExpDataVo> priceExpDataInfo = priceExpDataService.getPriceExpDataInfoByPriceId(innerOrgIdAndPriceDatId.getPriceDataId());
        PriceExpDataVo priceExpDataVo = priceExpDataInfo.getData();

        //获取轴数据
        ResultUtil<PriceExpAxisVo> axisInfoVo = priceExpAxisService.getAxisInfoById(id);
        if(null == priceExpDataVo && null == axisInfoVo.getData()){
            return  ResultUtil.APPRESULT(ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.code,
                    ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.msg, null);
        }
        PriceExpDataAxisVo priceExpDataAxisVo = new PriceExpDataAxisVo();
        priceExpDataAxisVo.setPriceDataId(innerOrgIdAndPriceDatId.getPriceDataId());
        priceExpDataAxisVo.setPriceData(priceExpDataVo.getPriceData());
        priceExpDataAxisVo.setAxisVo(axisInfoVo.getData());

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpDataAxisVo);
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

        List<Long> emptyList = new ArrayList<>();

        Boolean saveSuccess = false;
        //构建主表持久化对象
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpUpdDto, priceExpMainPo);

        //处理特殊物品
        List<Integer> specialCommodityCodeList = new ArrayList<>();
        if (null != priceExpUpdDto.getSpecialCommodity() && priceExpUpdDto.getSpecialCommodity().size() > 0) {
            List<SpecialCommodityDto> specialCommodity = priceExpUpdDto.getSpecialCommodity();
            for (SpecialCommodityDto specialCommodityDto : specialCommodity) {
                specialCommodityCodeList.add(specialCommodityDto.getCode());
            }
        }
        priceExpMainPo.setSpecialCommodity(specialCommodityCodeList);

        if (priceExpUpdDto.getIsPublishedPrice().equals(1)) {
            // 公布价, 客户、 客户组、服务商设为空
            priceExpMainPo.setCustomerGroupsId(emptyList);
            priceExpMainPo.setCustomerGroupsName("");

            priceExpMainPo.setCustomerIds(emptyList);
            priceExpMainPo.setCustomerName("");

            priceExpMainPo.setPartnerId(0l);
        } else {
            //处理客户组
            if (null != priceExpUpdDto.getCustomerGroup() && priceExpUpdDto.getCustomerGroup().size() > 0) {
                List<Long> customerGroupsId = new ArrayList<>();
                StringBuffer customerGroupsName = new StringBuffer();
                for (CustomerGroupDto customerGroupDto : priceExpUpdDto.getCustomerGroup()) {
                    customerGroupsId.add(customerGroupDto.getCustomerGroupsId());
                    if (customerGroupsName.length() > 0)
                        customerGroupsName.append(", ");
                    customerGroupsName.append(customerGroupDto.getCustomerGroupsName());
                }
                priceExpMainPo.setCustomerGroupsId(customerGroupsId);
                priceExpMainPo.setCustomerGroupsName(customerGroupsName.toString());
            }

            //处理客户
            if (null != priceExpUpdDto.getCustomer() && priceExpUpdDto.getCustomer().size() > 0) {
                List<Long> customerIds = new ArrayList<>();
                StringBuffer customerName = new StringBuffer();
                for (CustomerDto customerDto : priceExpUpdDto.getCustomer()) {
                    customerIds.add(customerDto.getCustomerId());
                    if (customerName.length() > 0)
                        customerName.append(", ");
                    customerName.append(customerDto.getCustomerName());
                }
                priceExpMainPo.setCustomerIds(customerIds);
                priceExpMainPo.setCustomerName(customerName.toString());
            }
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

        ExpPriceInfoBo expPriceInfoBo = baseMapper.getInnerOrgIdAndPriceDatId(priceExpDataUpdDto.getId());

        if(null == expPriceInfoBo || 0 == expPriceInfoBo.getInnerOrgId() || 0 == expPriceInfoBo.getPriceDataId()){
            throw new AplException(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg);
        }

        if (securityUser.getInnerOrgId() == expPriceInfoBo.getInnerOrgId()) {

            //更新主表
            PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
            priceExpMainPo.setId(priceExpDataUpdDto.getId());
            priceExpMainPo.setStartWeight(priceExpDataUpdDto.getStartWeight());
            priceExpMainPo.setEndWeight(priceExpDataUpdDto.getEndWeight());
            priceExpMainPo.setPriceFormat(priceExpDataUpdDto.getPriceFormat());
            Integer resInt = baseMapper.updData(priceExpMainPo);
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
     * @param
     * @return
     */
    @Override
    public Boolean updTransverseWeightSection(WeightSectionUpdDto weightSectionUpdDto) {

        if(null == weightSectionUpdDto || weightSectionUpdDto.getWeightSection().size() == 0){
            throw new AplException(ExpListServiceCode.PLEASE_FILL_IN_THE_DATA_FIRST.code, ExpListServiceCode.PLEASE_FILL_IN_THE_DATA_FIRST.msg);
        }

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
        }
        priceExpAxisPo.setAxisTransverse(axisPortrait);
        priceExpAxisPo.setId(weightSectionUpdDto.getPriceDataId());

        Long checkMainId = baseMapper.getMainIdByPriceDataId(weightSectionUpdDto.getPriceDataId());

        if(checkMainId == null || checkMainId.equals(0))
            throw new AplException(ExpListServiceCode.THE_MAIN_TABLE_DOES_NOT_EXIST_AND_CANNOT_BE_UPDATED.code,
                    ExpListServiceCode.THE_MAIN_TABLE_DOES_NOT_EXIST_AND_CANNOT_BE_UPDATED.msg);
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        priceExpMainPo.setId(checkMainId);

        Boolean result = priceExpAxisService.updateByMainId(priceExpAxisPo);
        if (!result) {
            throw new AplException(ExpListServiceCode.ID_IS_NOT_EXIST.code, ExpListServiceCode.ID_IS_NOT_EXIST.msg);
        }
        return true;
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

        Long priceDataId = SnowflakeIdWorker.generateId();

        ResultUtil<Long> longResultUtil = addExpPriceBase(priceExpAddDto, 0l, priceDataId);

            //保存价格表数据
            Boolean saveSuccess = priceExpDataService.addPriceExpData(priceDataId,priceExpAddDto);
            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.msg, null);
            }
            //保存价格表轴数据
            saveSuccess = priceExpAxisService.addPriceExpAxis(priceDataId,priceExpAddDto);
            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.msg, null);
            }


        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, longResultUtil.getData());
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

        ResultUtil<Long> longResultUtil = addExpPriceBase(referencePriceDto, referencePriceDto.getQuotePriceId(), referencePriceDto.getPriceDataId());

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, longResultUtil.getData());
    }


    ResultUtil<Long> addExpPriceBase(PriceExpAddBaseDto priceExpAddDto, Long quotePriceId, Long priceDataId) {

        //不是公布价且不是成本价且不是销售价, 有服务商即成本价, 允许既是公布价又是成本价
        if (priceExpAddDto.getIsPublishedPrice().equals(2)
                && (null == priceExpAddDto.getPartnerId() || priceExpAddDto.getPartnerId() < 1)
                && (null == priceExpAddDto.getCustomerGroup() || priceExpAddDto.getCustomerGroup().size()<1)
                && (null == priceExpAddDto.getCustomer() || priceExpAddDto.getCustomer().size()<1)) {

            throw new AplException(ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.PARTNER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg);
        }


        if(null == priceExpAddDto.getPriceSaleName() || priceExpAddDto.getPriceSaleName().length()<1 || priceExpAddDto.getPriceSaleName().equals(" "))
            priceExpAddDto.setPriceSaleName(priceExpAddDto.getPriceName());

        Long priceId = SnowflakeIdWorker.generateId();
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpAddDto, priceExpMainPo);
        priceExpMainPo.setId(priceId);
        priceExpMainPo.setPriceStatus(1);
        priceExpMainPo.setQuotePriceId(quotePriceId);
        priceExpMainPo.setPriceDataId(priceDataId);
        if(priceExpAddDto.getPricePublishedId() == null){
            priceExpMainPo.setPricePublishedId(0L);
        }
        if(priceExpAddDto.getPartnerId() == null){
            priceExpMainPo.setPartnerId(0L);
        }

        //处理特殊物品
        List<Integer> specialCommodityCodeList = new ArrayList<>();
        if(null != priceExpAddDto.getSpecialCommodity() && priceExpAddDto.getSpecialCommodity().size()>0) {
            List<SpecialCommodityDto> specialCommodity = priceExpAddDto.getSpecialCommodity();
            for (SpecialCommodityDto specialCommodityDto : specialCommodity) {
                specialCommodityCodeList.add(specialCommodityDto.getCode());
            }
        }
        priceExpMainPo.setSpecialCommodity(specialCommodityCodeList);

        List<Long> emptyLongList = new ArrayList<>();

        if (priceExpAddDto.getIsPublishedPrice().equals(1)){
            // 公布价, 客户、 客户组、服务商设为空
            priceExpMainPo.setCustomerGroupsId(emptyLongList);
            priceExpMainPo.setCustomerGroupsName("");

            priceExpMainPo.setCustomerIds(emptyLongList);
            priceExpMainPo.setCustomerName("");

            priceExpMainPo.setPartnerId(0l);
        }
        else {
            //处理客户组
            if (null != priceExpAddDto.getCustomerGroup() && priceExpAddDto.getCustomerGroup().size() > 0) {
                List<Long> customerGroupsId = new ArrayList<>();
                StringBuffer customerGroupsName = new StringBuffer();
                for (CustomerGroupDto customerGroupDto : priceExpAddDto.getCustomerGroup()) {
                    customerGroupsId.add(customerGroupDto.getCustomerGroupsId());
                    if (customerGroupsName.length() > 0)
                        customerGroupsName.append(", ");
                    customerGroupsName.append(customerGroupDto.getCustomerGroupsName());
                }
                priceExpMainPo.setCustomerGroupsId(customerGroupsId);
                priceExpMainPo.setCustomerGroupsName(customerGroupsName.toString());
            } else {
                priceExpMainPo.setCustomerGroupsId(emptyLongList);
                priceExpMainPo.setCustomerGroupsName("");
            }

            //处理客户
            if (null != priceExpAddDto.getCustomer() && priceExpAddDto.getCustomer().size() > 0) {
                List<Long> customerIds = new ArrayList<>();
                StringBuffer customerName = new StringBuffer();
                for (CustomerDto customerDto : priceExpAddDto.getCustomer()) {
                    customerIds.add(customerDto.getCustomerId());
                    if (customerName.length() > 0)
                        customerName.append(", ");
                    customerName.append(customerDto.getCustomerName());
                }
                priceExpMainPo.setCustomerIds(customerIds);
                priceExpMainPo.setCustomerName(customerName.toString());
            } else {
                priceExpMainPo.setCustomerIds(emptyLongList);
                priceExpMainPo.setCustomerName("");
            }
        }

        Integer saveResult = baseMapper.addExpPrice(priceExpMainPo);
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
            Boolean saveSuccess = priceExpRemarkPo.insert();
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
    @Transactional
    public ResultUtil<Boolean> deletePriceBatch(List<Long> priceIdList) {

        StringBuffer sbPriceIds = new StringBuffer();
        for (Long id : priceIdList) {
            if(sbPriceIds.length() > 0){
                sbPriceIds.append(",");
            }
            sbPriceIds.append(id);
        }

        //获取价格表数据id
        List<Long> priceDataIdList = baseMapper.getPriceDataIds(sbPriceIds.toString());
        StringBuffer sbPriceDataIds = new StringBuffer();
        for (Long priceDataId : priceDataIdList) {
            if(sbPriceDataIds.length()>0){
                sbPriceDataIds.append(",");
            }
            sbPriceDataIds.append(priceDataId);
        }

        //删除价格表
        baseMapper.delBatchs(sbPriceIds.toString());

        if(sbPriceDataIds.length() > 0){
            //删除轴数据
            priceExpAxisService.delBatch(sbPriceDataIds.toString());
            //删除价格表数据
            priceExpDataService.delBatch(sbPriceDataIds.toString());
        }

        //根据id删除备注
        priceExpRemarkService.delBatch(sbPriceIds.toString());
        //删除公式
        computationalFormulaService.delBatch(sbPriceIds.toString());

        //删除利润
        priceExpProfitService.delBatch(sbPriceIds.toString());

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

}