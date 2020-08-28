package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lib.utils.StringUtil;
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
        PRICE_EXP_MAIN_SAVE_DATA_FAILED("PRICE_EXP_MAIN_SAVE_DATA_FAILED", "保存价格主表数据失败"),
        PRICE_EXP_SALE_SAVE_DATA_FAILED("PRICE_EXP_SALE_SAVE_DATA_FAILED", "保存销售价格表数据失败"),
        PRICE_EXP_COST_SAVE_DATA_FAILED("PRICE_EXP_COST_SAVE_DATA_FAILED", "保存成本价格表数据失败"),
        PRICE_EXP_AXIS_SAVE_DATA_FAILED("PRICE_EXP_AXIS_SAVE_DATA_FAILED", "保存价格表轴数据失败"),
        PRICE_EXP_DATA_SAVE_DATA_FAILED("PRICE_EXP_DATA_SAVE_DATA_FAILED", "保存价格表数据失败"),
        price_exp_remark_SAVE_DATA_FAILED("price_exp_remark_SAVE_DATA_FAILED", "保存价格表扩展数据失败"),
        ID_IS_NOT_EXITS("ID_IS_NOT_EXITS", "id不存在"),
        CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH("CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH", "客户id和客户名称不匹配"),
        SERVICE_PROVIDER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP(
                "SERVICE_PROVIDER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP",
                "服务商, 客户组, 客户, 请至少填写一组");

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
     * @param id 销售价格表主键Id
     * @return
     */
    @Override
    public ResultUtil<PriceExpSaleInfoVo> getPriceExpSaleInfo(Long id) {

        //查询销售价格表并组装
        PriceExpSaleVo priceExpSaleVo = priceExpSaleService.getPriceExpSaleInfoById(id);

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


        //获取扩展信息并组装
        PriceExpRemarkPo priceExpRemarkPo = priceExpRemarkService.getDevelopInfoById(id);
        if(priceExpRemarkPo != null){
            priceExpSaleInfoVo.setRemark(priceExpRemarkPo.getRemark());
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpSaleInfoVo);
    }

    /**
     * 获取成本价格详情
     * @param id 成本价主键id
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

        //查询主表  用priceExpSaleInfoVo接收是为了服用方法
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

        //获取扩展信息并组装
        PriceExpRemarkPo priceExpRemarkPo = priceExpRemarkService.getDevelopInfoById(id);
        if(priceExpSaleInfoVo != null){
            priceExpCostInfoVo.setRemark(priceExpRemarkPo.getRemark());
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpCostInfoVo);
    }


    /**
     * 更新销售价格
     * @param priceExpMainUpdateDto
     * @param priceExpSaleUpdateDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updateSalePrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                               PriceExpSaleUpdateDto priceExpSaleUpdateDto) {

        SecurityUser securityUser = CommonContextHolder.getSecurityUser();

        Long innerOrgId = -1L;

        Boolean saveSuccess = false;

        if(priceExpMainUpdateDto != null){
            innerOrgId = baseMapper.getInnerOrgId(priceExpMainUpdateDto.getId());
            if(innerOrgId == securityUser.getInnerOrgId()){
                //更新主表
                PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
                BeanUtil.copyProperties(priceExpMainUpdateDto, priceExpMainPo);
                saveSuccess = priceExpMainPo.updateById();
                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
                }
            }
        }

        //更新销售价格表
        PriceExpSalePo priceExpSalePo = new PriceExpSalePo();
        BeanUtil.copyProperties(priceExpSaleUpdateDto, priceExpSalePo);
        if(innerOrgId != securityUser.getInnerOrgId()){
            priceExpSalePo.setPriceMainId(null);
        }
        saveSuccess = priceExpSalePo.updateById();
        if (!saveSuccess) {
            throw new AplException(ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.code,
                    ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.msg, null);
        }



        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 更新成本价格表
     * @param priceExpMainUpdateDto
     * @param priceExpCostUpdateDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> updateCostPrice(PriceExpMainUpdateDto priceExpMainUpdateDto,
                                               PriceExpCostUpdateDto priceExpCostUpdateDto) {

        Long innerOrgId = -1L;
        SecurityUser securityUser = CommonContextHolder.getSecurityUser();
        Boolean saveSuccess = false;

        if(null != priceExpMainUpdateDto){

            innerOrgId = baseMapper.getInnerOrgId(priceExpMainUpdateDto.getId());
            if(innerOrgId == securityUser.getInnerOrgId()){
                //更新主表
                PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
                BeanUtil.copyProperties(priceExpMainUpdateDto, priceExpMainPo);
                saveSuccess = priceExpMainPo.updateById();
                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.PRICE_EXP_MAIN_SAVE_DATA_FAILED.msg, null);
                }
            }
        }

        //更新成本价格表
        PriceExpCostPo priceExpCostPo = new PriceExpCostPo();
        BeanUtil.copyProperties(priceExpCostUpdateDto, priceExpCostPo);
        if(innerOrgId != securityUser.getInnerOrgId()){
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
     * @param priceExpDataAddDto
     * @param priceExpAxisPo
     * @return
     */
    @Override
    public ResultUtil<Boolean> updatePriceData(PriceExpDataAddDto priceExpDataAddDto, PriceExpAxisPo priceExpAxisPo) {

        Long innerOrgId = priceExpDataService.getInnerOrgId(priceExpAxisPo.getPriceMainId());

        if(innerOrgId == 0 || innerOrgId == null){
            return ResultUtil.APPRESULT(ExpListServiceCode.ID_IS_NOT_EXITS.code, ExpListServiceCode.ID_IS_NOT_EXITS.msg, false);
        }

        SecurityUser securityUser = CommonContextHolder.getSecurityUser();

        if(innerOrgId == securityUser.getInnerOrgId()){

            PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
            priceExpDataPo.setPriceData(priceExpDataAddDto.getPriceData());
            priceExpDataPo.setPriceMainId(priceExpAxisPo.getPriceMainId());
            Boolean result = priceExpDataPo.updateById();
            if(!result) {
                return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
            }
            Boolean res = priceExpAxisPo.updateById();
            if(!res) {
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
        priceExpRemarkService.deleteById(id);

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
        priceExpRemarkService.deleteById(id);

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

        if(priceExpSaleAddDto != null) {

            //校验客户id与客户是否匹配
            String[] customerName = priceExpSaleAddDto.getCustomerName().split(",");
            if (priceExpSaleAddDto.getCustomerIds().size() != customerName.length) {

                return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                        ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);

            }

            //校验客户组id与客户组是否匹配
            String[] customerGroup = priceExpSaleAddDto.getCustomerGroupsName().split(",");
            if (priceExpSaleAddDto.getCustomerGroupsId().size() != customerGroup.length) {

                return ResultUtil.APPRESULT(ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.code,
                        ExpListServiceCode.CUSTOMER_ID_AND_CUSTOMER_NAME_DO_NOT_MATCH.msg, null);

            }
        }

        //如果服务商id不合法, 或者客户组与客户都为空, 则要求至少填写一处
        if((null==priceExpCostAddDto.getPartnerId() || priceExpCostAddDto.getPartnerId()<1)

                && (null==priceExpSaleAddDto.getCustomerGroupsId() || priceExpSaleAddDto.getCustomerGroupsId().size()==0 )
                &&  (null==priceExpSaleAddDto.getCustomerIds())|| priceExpSaleAddDto.getCustomerIds().size()==0  ){

            return ResultUtil.APPRESULT(ExpListServiceCode.SERVICE_PROVIDER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.code,
                    ExpListServiceCode.SERVICE_PROVIDER_CUSTOMER_GROUP_CUSTOMER_PLEASE_FILL_IN_AT_LEAST_ONE_GROUP.msg, null);

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

        if(priceExpMainAddDto.getIsPublishedPrice() == 1){
            priceExpCostAddDto.setPartnerId(0L);
        }

        //保存成本价和备注
        if((priceExpCostAddDto.getPartnerId()!=null && priceExpCostAddDto.getPartnerId()>=0)) {

            Long costId = SnowflakeIdWorker.generateId();
            saveSuccess = priceExpCostService.addPriceExpCost(priceMainId, priceExpCostAddDto, costId);

            if (!saveSuccess) {
                throw new AplException(ExpListServiceCode.PRICE_EXP_COST_SAVE_DATA_FAILED.code,
                        ExpListServiceCode.PRICE_EXP_COST_SAVE_DATA_FAILED.msg, null);
            }

            PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
            priceExpRemarkPo.setId(costId);
            priceExpRemarkPo.setRemark(priceExpCostAddDto.getRemark());
            saveSuccess = priceExpRemarkPo.insert();

            if(!saveSuccess){
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

        if(priceExpMainAddDto.getIsPublishedPrice() == 2) {

            if (priceExpSaleAddDto != null
                    && ((priceExpSaleAddDto.getCustomerIds() != null && priceExpSaleAddDto.getCustomerIds().size() > 0)
                    || (priceExpSaleAddDto.getCustomerGroupsId() != null && priceExpSaleAddDto.getCustomerGroupsId().size() > 0))) {

                // 有客户组或有客户就是销售价
                Long saleId = SnowflakeIdWorker.generateId();
                saveSuccess = priceExpSaleService.addPriceExpSale(priceExpCostAddDto,priceExpSaleAddDto, priceMainId, saleId);

                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.PRICE_EXP_SALE_SAVE_DATA_FAILED.msg, null);
                }

                //保存备注
                PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
                priceExpRemarkPo.setId(saleId);
                priceExpRemarkPo.setRemark(priceExpSaleAddDto.getRemark());
                saveSuccess = priceExpRemarkPo.insert();
                if (!saveSuccess) {
                    throw new AplException(ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.code,
                            ExpListServiceCode.price_exp_remark_SAVE_DATA_FAILED.msg, null);
                }
            }
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceMainId);
    }


    /**
     * 引用  保存销售价格数据和备注
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
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "销售价格数据保存失败", null);
        }

        PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
        priceExpRemarkPo.setId(SnowflakeIdWorker.generateId());
        priceExpRemarkPo.setRemark(priceExpSaleAddDto.getRemark());
        saveSuccess = priceExpRemarkPo.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "扩展数据保存失败", null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpSalePo.getId());
    }

    /**
     * 引用  保存成本价格数据和备注
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
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "成本价格数据保存失败", null);
        }

        PriceExpRemarkPo priceExpRemarkPo = new PriceExpRemarkPo();
        priceExpRemarkPo.setId(SnowflakeIdWorker.generateId());
        saveSuccess = priceExpRemarkPo.insert();
        if(!saveSuccess){
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, "扩展信息保存失败", null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, priceExpCostPo.getId());
    }
}