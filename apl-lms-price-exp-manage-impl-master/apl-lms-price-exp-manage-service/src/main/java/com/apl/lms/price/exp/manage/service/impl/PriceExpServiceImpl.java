package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lib.validate.ApiParamValidate;
import com.apl.lms.price.exp.manage.mapper.PriceExpMapper;
import com.apl.lms.price.exp.manage.service.*;
import com.apl.lms.price.exp.pojo.dto.*;
import com.apl.lms.price.exp.pojo.entity.Customer;
import com.apl.lms.price.exp.pojo.entity.CustomerGroupInfo;
import com.apl.lms.price.exp.pojo.po.*;
import com.apl.lms.price.exp.pojo.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
        PRICE_EXP_MAIN_SAVE_DATA_FAILED("PRICE_EXP_MAIN_SAVE_DATA_FAILED", "保存价格主表数据失败"),
        PRICE_EXP_SALE_SAVE_DATA_FAILED("PRICE_EXP_SALE_SAVE_DATA_FAILED", "保存销售价格表数据失败"),
        PRICE_EXP_COST_SAVE_DATA_FAILED("PRICE_EXP_COST_SAVE_DATA_FAILED", "保存成本价格表数据失败"),
        PRICE_EXP_AXIS_SAVE_DATA_FAILED("PRICE_EXP_AXIS_SAVE_DATA_FAILED", "保存价格表轴数据失败"),
        PRICE_EXP_DATA_SAVE_DATA_FAILED("PRICE_EXP_DATA_SAVE_DATA_FAILED", "保存价格表数据失败"),
        PRICE_EXP_DEVELOP_INFO_SAVE_DATA_FAILED("PRICE_EXP_DEVELOP_INFO_SAVE_DATA_FAILED", "保存价格表扩展数据失败"),
        ID_IS_NOT_EXITS("ID_IS_NOT_EXITS", "id不存在");

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
    PriceExpDevelopInfoService priceExpDevelopInfoService;

    @Autowired
    PriceExpAxisService priceExpAxisService;

    @Autowired
    PriceExpCostService priceExpCostService;

    @Autowired
    PriceExpSaleService priceExpSaleService;

    /**
     * 分页查询销售价格列表
     * @param pageDto
     * @param priceExpSaleListKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<PriceExpSaleListVo>> getPriceExpSaleList(PageDto pageDto, PriceExpSaleListKeyDto priceExpSaleListKeyDto) {

        Page<PriceExpSaleListVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());
//        if(priceExpSaleListKeyDto.getCustomerId() != null){
//            priceExpSaleListKeyDto.setCustomerId("'" + priceExpSaleListKeyDto.getCustomerId() + "'");
//        }
//        if(priceExpSaleListKeyDto.getCustomerGroupsId() != null){
//            priceExpSaleListKeyDto.setCustomerGroupsId("'" + priceExpSaleListKeyDto.getCustomerGroupsId() + "'");
//        }
        List<PriceExpSaleListVo> priceExpListVoSaleList = baseMapper.getPriceExpSaleList(page, priceExpSaleListKeyDto);

        page.setRecords(priceExpListVoSaleList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }


    /**
     * 分页查询成本价格列表
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
     * @param id
     * @return
     */
    @Override
    public ResultUtil<PriceExpSaleInfoVo> getPriceExpSaleInfo(Long id) {

        //查询销售价格表并组装
        PriceExpSaleVo priceExpSaleVo = priceExpSaleService.getPriceExpSaleInfoByMainId(id);

        if(priceExpSaleVo == null){
            throw new AplException(CommonStatusCode.GET_FAIL.code, "id不存在", null);
        }

        //查询主表
        PriceExpSaleInfoVo priceExpSaleInfoVo = baseMapper.getPriceExpMainInfoById(priceExpSaleVo.getPriceMainId());

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
                String customerNames = priceExpSaleVo.getCustomerName().replace("[", "").replace("]", "").replaceAll(" ", "");;

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
                String customerGroupsIds = priceExpSaleVo.getCustomerGroupsId().replace("[", "").replace("]", "").replaceAll(" ", "");;
                String customerGroupsName = priceExpSaleVo.getCustomerGroupsName().replace("[", "").replace("]", "").replaceAll(" ", "");;

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

        //获取轴数据信息并组装
        PriceExpAxisPo priceExpAxisPo = priceExpAxisService.getPriceExpAxisInfoByMainId(priceExpSaleVo.getPriceMainId());
        if(priceExpSaleInfoVo != null){
            priceExpSaleInfoVo.setAxisTransverse(priceExpAxisPo.getAxisTransverse());
            priceExpSaleInfoVo.setAxisPortrait(priceExpAxisPo.getAxisPortrait());
        }

        //获取扩展信息并组装
        PriceExpDevelopInfoPo priceExpDevelopInfoPo = priceExpDevelopInfoService.getDevelopInfoByMainId(id);
        if(priceExpSaleInfoVo != null){
            priceExpSaleInfoVo.setSaleRemark(priceExpDevelopInfoPo.getSaleRemark());
            priceExpSaleInfoVo.setPartnerRemark(priceExpDevelopInfoPo.getPartnerRemark());
        }

        //获取主数据信息并组装
        PriceExpDataVo priceExpDataVo = priceExpDataService.getPriceExpDataInfoByMainId(priceExpSaleVo.getPriceMainId());
        if(priceExpDataVo != null){
            priceExpSaleInfoVo.setPriceData(priceExpDataVo.getPriceData().replace("[", "").replace("]", "")
                    .replaceAll(" ", ""));
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpSaleInfoVo);
    }

    /**
     * 获取成本价格详情
     * @param id
     * @return
     */
    @Override
    public ResultUtil<PriceExpCostInfoVo> getPriceExpCostInfo(Long id) {

        PriceExpCostInfoVo priceExpCostInfoVo = new PriceExpCostInfoVo();

        //查询成本价格表并组装
        PriceExpCostVo priceExpCostVo = priceExpCostService.getPriceExpCostInfo(id);

        if(priceExpCostVo == null){
            ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL.code, "主表Id不正确",  null);
        }

        //查询主表
        PriceExpSaleInfoVo priceExpSaleInfoVo = baseMapper.getPriceExpMainInfoById(priceExpCostVo.getPriceMainId());

        if(priceExpSaleInfoVo != null){

        BeanUtil.copyProperties(priceExpSaleInfoVo, priceExpCostInfoVo);

        priceExpCostInfoVo.setSpecialCommodity(priceExpCostInfoVo.getSpecialCommodity().replace("[", "").replace("]", "")
                .replaceAll(" ", ""));
        }

        priceExpCostInfoVo.setPartnerId(priceExpCostVo.getPartnerId());
        priceExpCostInfoVo.setPriceStatus(priceExpCostVo.getPriceStatus());
        priceExpCostInfoVo.setPriceCode(priceExpCostVo.getPriceCode());
        priceExpCostInfoVo.setPriceName(priceExpCostVo.getPriceName());
        priceExpCostInfoVo.setChannelCategory(priceExpCostVo.getChannelCategory());

        //获取轴数据信息并组装
        PriceExpAxisPo priceExpAxisPo = priceExpAxisService.getPriceExpAxisInfoByMainId(priceExpCostVo.getPriceMainId());
        if(priceExpSaleInfoVo != null){
            priceExpCostInfoVo.setAxisTransverse(priceExpAxisPo.getAxisTransverse());
            priceExpCostInfoVo.setAxisPortrait(priceExpAxisPo.getAxisPortrait());
        }

        //获取扩展信息并组装
        PriceExpDevelopInfoPo priceExpDevelopInfoPo = priceExpDevelopInfoService.getDevelopInfoByMainId(id);
        if(priceExpSaleInfoVo != null){
            priceExpCostInfoVo.setSaleRemark(priceExpDevelopInfoPo.getSaleRemark());
            priceExpCostInfoVo.setPartnerRemark(priceExpDevelopInfoPo.getPartnerRemark());
        }

        //获取主数据信息并组装
        PriceExpDataVo priceExpDataVo = priceExpDataService.getPriceExpDataInfoByMainId(priceExpCostVo.getPriceMainId());
        if(priceExpDataVo != null){
            priceExpCostInfoVo.setPriceData(priceExpDataVo.getPriceData().replace("[", "").replace("]", "")
                    .replaceAll(" ", ""));
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpCostInfoVo);
    }


    /**
     * 更新销售价格
     * @param priceExpMainUpdateDto
     * @param priceExpSaleUpdateDto
     * @param priceExpAxisUpdateDto
     * @param priceExpDevelopInfoUpdateDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updateSalePrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                               PriceExpSaleUpdateDto priceExpSaleUpdateDto,
                                               PriceExpAxisUpdateDto priceExpAxisUpdateDto,
                                               PriceExpDevelopInfoUpdateDto priceExpDevelopInfoUpdateDto) {

        Boolean saveSuccess = false;

        //更新销售价格表
        PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
        BeanUtil.copyProperties(priceExpSaleUpdateDto, priceExpSalePo);
        saveSuccess = priceExpSaleService.updateById(priceExpSalePo);
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.msg, null);
        }

        if(priceExpDevelopInfoUpdateDto != null){
            //更新价格表扩展数据
            PriceExpDevelopInfoPo priceExpDevelopInfoPo = new PriceExpDevelopInfoPo();
            BeanUtil.copyProperties(priceExpDevelopInfoUpdateDto, priceExpDevelopInfoPo);
            priceExpDevelopInfoPo.setPriceId(priceExpSaleUpdateDto.getId());
            saveSuccess = priceExpDevelopInfoService.updateById(priceExpDevelopInfoPo);
            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_DEVELOP_INFO_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_DEVELOP_INFO_SAVE_DATA_FAILED.msg, null);
            }
        }

        if(priceExpMainUpdateDto != null){
            Long innerOrgId = baseMapper.getInnerOrgId(priceExpMainUpdateDto.getId());
            SecurityUser securityUser = CommonContextHolder.getSecurityUser();
            if(innerOrgId == securityUser.getInnerOrgId()){
                //更新主表
                PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
                BeanUtil.copyProperties(priceExpMainUpdateDto, priceExpMainPo);
                saveSuccess = priceExpMainPo.updateById();
                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
                }

                if(priceExpAxisUpdateDto != null) {
                    //更新价格表轴数据
                    PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
                    BeanUtil.copyProperties(priceExpAxisUpdateDto, priceExpAxisPo);
                    priceExpAxisPo.setPriceId(priceExpMainUpdateDto.getId());
                    saveSuccess = priceExpAxisService.updateById(priceExpAxisPo);
                    if (!saveSuccess) {
                        throw new AplException(ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.code,
                                ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.msg, null);
                    }
                }
            }
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 更新成本价格表
     * @param priceExpMainUpdateDto
     * @param priceExpCostUpdateDto
     * @param priceExpAxisUpdateDto
     * @param priceExpDevelopInfoUpdateDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updateCostPrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                               @Validated PriceExpCostUpdateDto priceExpCostUpdateDto,
                                               PriceExpAxisUpdateDto priceExpAxisUpdateDto,
                                               PriceExpDevelopInfoUpdateDto priceExpDevelopInfoUpdateDto) {

        Boolean saveSuccess = false;

        //更新成本价格表
        PriceExpCostPo priceExpCostPo = new PriceExpCostPo();
        BeanUtil.copyProperties(priceExpCostUpdateDto, priceExpCostPo);
        saveSuccess = priceExpCostService.updateById(priceExpCostPo);
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.msg, null);
        }

        if(priceExpDevelopInfoUpdateDto != null){
            //更新价格表扩展数据
            PriceExpDevelopInfoPo priceExpDevelopInfoPo = new PriceExpDevelopInfoPo();
            BeanUtil.copyProperties(priceExpDevelopInfoUpdateDto, priceExpDevelopInfoPo);
            priceExpDevelopInfoPo.setPriceId(priceExpCostUpdateDto.getId());
            saveSuccess = priceExpDevelopInfoService.updateById(priceExpDevelopInfoPo);
            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_DEVELOP_INFO_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_DEVELOP_INFO_SAVE_DATA_FAILED.msg, null);
            }
        }

        if(priceExpMainUpdateDto != null){
            Long innerOrgId = baseMapper.getInnerOrgId(priceExpMainUpdateDto.getId());
            SecurityUser securityUser = CommonContextHolder.getSecurityUser();
            if(innerOrgId == securityUser.getInnerOrgId()){
                //更新主表
                PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
                BeanUtil.copyProperties(priceExpMainUpdateDto, priceExpMainPo);
                saveSuccess = priceExpMainPo.updateById();
                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
                }

                if(priceExpAxisUpdateDto != null) {
                    //更新价格表轴数据
                    PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
                    BeanUtil.copyProperties(priceExpAxisUpdateDto, priceExpAxisPo);
                    priceExpAxisPo.setPriceId(priceExpMainUpdateDto.getId());
                    saveSuccess = priceExpAxisService.updateById(priceExpAxisPo);
                    if (!saveSuccess) {
                        throw new AplException(ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.code,
                                ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.msg, null);
                    }
                }
            }
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 更新价格表数据
     * @param priceExpMainUpdateDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updatePriceData(PriceExpDataUpdateDto priceExpMainUpdateDto) {

        Long innerOrgId = priceExpDataService.getInnerOrgId(priceExpMainUpdateDto.getId());

        if(innerOrgId == 0 || innerOrgId == null){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL.code, "id不正确", false);
        }

        SecurityUser securityUser = CommonContextHolder.getSecurityUser();

        if(innerOrgId == securityUser.getInnerOrgId()){

            PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
            BeanUtil.copyProperties(priceExpMainUpdateDto, priceExpDataPo);

            Boolean result = priceExpDataService.updatePriceExpData(priceExpDataPo);

            if(!result) {
                return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
            }
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 根据id删除成本价格数据
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> deleteCostPrice(Long id) {

        //先得到主表id
        Long priceMainId = priceExpCostService.getPriceDataId(id);
        if(priceMainId == 0){
            throw new AplException(CommonStatusCode.SYSTEM_FAIL.code, "成本价格表Id无效", null);
        }
        //根据id删除成本价格表信息
        priceExpCostService.deleteById(id);
        //根据id删除备注表信息
        priceExpDevelopInfoService.deleteByPriceId(id);

        //通过主表id得到成本价格表中关联数据的累计条数
        Integer priceDataIdCount = priceExpCostService.getPriceDataIdCount(priceMainId);

        //如果成本表中没有该主表id对应的数据
        if(priceDataIdCount == 0){

            //通过主表id查询销售表中的数据统计
            Integer priceDataIdCount2 = priceExpSaleService.getPriceDataIdCount(priceMainId);

            //如果销售价格表中也没有该主表id对应的关联数据
            if(priceDataIdCount2 == 0){

                //根据主表id查询租户id
                Long innerOrgId = baseMapper.getInnerOrgId(priceMainId);
                SecurityUser securityUser = CommonContextHolder.getSecurityUser();

                if( securityUser.getInnerOrgId() == innerOrgId){

                    //如果是庄家, 则删除主表数据
                    baseMapper.deleteById(priceMainId);
                    //删除轴数据
                    priceExpAxisService.deleteByPriceExpMainId(priceMainId);
                    //删除主数据
                    priceExpDataService.deleteByPriceExpMainId(priceMainId);

                }
            }
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, true);
    }

    /**
     * 根据id删除销售价格表数据
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> deleteSalePrice(Long id) {

        //先得到主表id
        Long priceMainId = priceExpSaleService.getPriceDataId(id);
        if(priceMainId == 0){
            throw new AplException(CommonStatusCode.SYSTEM_FAIL.code, "成本价格表Id无效", null);
        }
        //根据id删除销售价格表信息
        priceExpSaleService.deleteById(id);
        //根据id删除备注表信息
        priceExpDevelopInfoService.deleteByPriceId(id);

        //通过主表id得到销售价格表中关联数据的累计条数
        Integer priceDataIdCount = priceExpSaleService.getPriceDataIdCount(priceMainId);

        //如果销售表中没有该主表id对应的数据
        if(priceDataIdCount == 0){

            //通过主表id查询成本表中的数据统计
            Integer priceDataIdCount2 = priceExpCostService.getPriceDataIdCount(priceMainId);

            //如果成本价格表中也没有该主表id对应的关联数据
            if(priceDataIdCount2 == 0){

                //根据主表id查询租户id
                Long innerOrgId = baseMapper.getInnerOrgId(priceMainId);
                SecurityUser securityUser = CommonContextHolder.getSecurityUser();

                if( securityUser.getInnerOrgId() == innerOrgId){

                    //如果是庄家, 则删除主表数据
                    baseMapper.deleteById(priceMainId);
                    //删除轴数据
                    priceExpAxisService.deleteByPriceExpMainId(priceMainId);
                    //删除主数据
                    priceExpDataService.deleteByPriceExpMainId(priceMainId);

                }
            }
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, true);
    }

    /**
     * 新增快递价格
     * @param priceExpMainInsertDto
     * @param priceExpSaleAddDto
     * @param priceExpCostAddDto
     * @param priceExpAxisInsertDto
     * @param priceExpDataInsertDto
     * @return
     */
    @Override
    public ResultUtil<Long> addExpPrice(PriceExpMainInsertDto priceExpMainInsertDto,
                                        PriceExpSaleAddDto priceExpSaleAddDto,
                                        PriceExpCostAddDto priceExpCostAddDto,
                                        PriceExpAxisInsertDto priceExpAxisInsertDto,
                                        PriceExpDataInsertDto priceExpDataInsertDto) {

        Boolean saveSuccess = false;

        Long priceId = SnowflakeIdWorker.generateId();

        //保存价格表内数据
        PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
        BeanUtil.copyProperties(priceExpDataInsertDto, priceExpDataPo);
        priceExpDataPo.setId(SnowflakeIdWorker.generateId());
        priceExpDataPo.setPriceId(priceId);
        saveSuccess = priceExpDataPo.insert();
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_DATA_SAVE_DATA_FAILED.msg, null);
        }

        //保存价格主表数据
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        BeanUtil.copyProperties(priceExpMainInsertDto, priceExpMainPo);
        priceExpMainPo.setId(priceId);
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        priceExpMainPo.setInnerOrgId(securityUser.getInnerOrgId());
        priceExpMainPo.setMainStatus(1);
        priceExpMainPo.setPriceDataId(priceExpDataPo.getId());
        saveSuccess = priceExpMainPo.insert();
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
        }
        //,com.apl.lms.price.exp.manage.mybatisTypeHandler.PriceExpCustomerGroupsIdTypeHandler
        //保存销售价格表数据
        PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
        BeanUtil.copyProperties(priceExpSaleAddDto, priceExpSalePo);
        priceExpSalePo.setId(SnowflakeIdWorker.generateId());
        priceExpSalePo.setQuotePriceFinalId(0L);
        priceExpSalePo.setQuotePriceId(0L);
        priceExpSalePo.setPriceStatus(1);
        priceExpSalePo.setPriceMainId(priceId);
        priceExpSalePo.setPriceCode(priceExpSaleAddDto.getSalePriceCode());
        priceExpSalePo.setPriceName(priceExpSaleAddDto.getSalePriceName());
        priceExpSalePo.setChannelCategory(priceExpSaleAddDto.getSaleChannelCategory());
        saveSuccess = priceExpSalePo.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "销售价格数据保存失败", null);
        }

        //保存销售价格扩展数据失败
        PriceExpDevelopInfoPo priceExpDevelopInfoPo = new PriceExpDevelopInfoPo();
        priceExpDevelopInfoPo.setId(SnowflakeIdWorker.generateId());
        priceExpDevelopInfoPo.setPriceId(priceExpSalePo.getId());
        priceExpDevelopInfoPo.setPartnerRemark(priceExpSaleAddDto.getPartnerRemark());
        priceExpDevelopInfoPo.setSaleRemark(priceExpSaleAddDto.getSaleRemark());
        saveSuccess = priceExpDevelopInfoPo.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "扩展数据保存失败", null);
        }

        //保存成本价格数据
        PriceExpCostPo priceExpCostPo = new PriceExpCostPo();
        BeanUtil.copyProperties(priceExpCostAddDto, priceExpCostPo);
        priceExpCostPo.setId(SnowflakeIdWorker.generateId());
        priceExpCostPo.setPriceStatus(1);
        priceExpCostPo.setQuotePriceFinalId(0l);
        priceExpCostPo.setQuotePriceId(0l);
        priceExpCostPo.setPriceMainId(priceId);
        priceExpCostPo.setPriceCode(priceExpCostAddDto.getCostPriceCode());
        priceExpCostPo.setPriceName(priceExpCostAddDto.getCostPriceName());
        priceExpCostPo.setChannelCategory(priceExpCostAddDto.getCostChannelCategory());
        saveSuccess = priceExpCostPo.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "成本价格数据保存失败", null);
        }

        //保存成本价格扩展价格数据
        PriceExpDevelopInfoPo priceExpDevelopInfoPo1 = new PriceExpDevelopInfoPo();
        priceExpDevelopInfoPo1.setId(SnowflakeIdWorker.generateId());
        priceExpDevelopInfoPo1.setPriceId(priceExpCostPo.getId());
        priceExpDevelopInfoPo1.setPartnerRemark(priceExpCostAddDto.getCostPartnerRemark());
        priceExpDevelopInfoPo1.setSaleRemark(priceExpCostAddDto.getCostSaleRemark());
        saveSuccess = priceExpDevelopInfoPo1.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "扩展信息保存失败", null);
        }

        //保存价格表轴数据
        PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
        BeanUtil.copyProperties(priceExpAxisInsertDto, priceExpAxisPo);
        priceExpAxisPo.setId(SnowflakeIdWorker.generateId());
        priceExpAxisPo.setPriceId(priceId);
        saveSuccess = priceExpAxisPo.insert();
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_AXIS_SAVE_DATA_FAILED.msg, null);
        }


        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceId);
    }

    /**
     * 插入销售价格数据和备注
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
        priceExpSalePo.setQuotePriceFinalId(0L);
        priceExpSalePo.setQuotePriceId(0L);
        priceExpSalePo.setPriceStatus(1);
        saveSuccess = priceExpSalePo.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "销售价格数据保存失败", null);
        }

        PriceExpDevelopInfoPo priceExpDevelopInfoPo = new PriceExpDevelopInfoPo();
        priceExpDevelopInfoPo.setId(SnowflakeIdWorker.generateId());
        priceExpDevelopInfoPo.setPriceId(priceExpSalePo.getId());
        priceExpDevelopInfoPo.setPartnerRemark(priceExpSaleAddDto.getPartnerRemark());
        priceExpDevelopInfoPo.setSaleRemark(priceExpSaleAddDto.getSaleRemark());
        saveSuccess = priceExpDevelopInfoPo.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "扩展数据保存失败", null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpSalePo.getId());
    }

    /**
     * 插入成本价格数据和备注
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
        priceExpCostPo.setQuotePriceFinalId(0l);
        priceExpCostPo.setQuotePriceId(0l);
        saveSuccess = priceExpCostPo.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "成本价格数据保存失败", null);
        }

        PriceExpDevelopInfoPo priceExpDevelopInfoPo = new PriceExpDevelopInfoPo();
        priceExpDevelopInfoPo.setId(SnowflakeIdWorker.generateId());
        priceExpDevelopInfoPo.setPriceId(priceExpCostPo.getId());
        priceExpDevelopInfoPo.setPartnerRemark(priceExpCostAddDto.getCostPartnerRemark());
        priceExpDevelopInfoPo.setSaleRemark(priceExpCostAddDto.getCostSaleRemark());
        saveSuccess = priceExpDevelopInfoPo.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "扩展信息保存失败", null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpCostPo.getId());
    }
}