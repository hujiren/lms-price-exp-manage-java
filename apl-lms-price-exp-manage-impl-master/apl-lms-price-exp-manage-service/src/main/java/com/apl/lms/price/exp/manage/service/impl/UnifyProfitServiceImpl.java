package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.apl.cache.AplCacheHelper;
import com.apl.lib.security.SecurityUser;
import com.apl.lib.utils.CommonContextHolder;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.net.SecurityUserNetService;
import com.apl.lms.price.exp.manage.mapper.UnifyProfitMapper;
import com.apl.lms.price.exp.manage.service.UnifyProfitService;
import com.apl.lms.price.exp.pojo.bo.CustomerGroupBo;
import com.apl.lms.price.exp.pojo.dto.UnifyProfitDto;
import com.apl.lms.price.exp.pojo.po.UnifyExpPricePo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname UnifyProfitServiceImpl
 * @Date 2020/11/4 9:54
 */
@Slf4j
@Service
public class UnifyProfitServiceImpl extends ServiceImpl<UnifyProfitMapper, UnifyExpPricePo> implements UnifyProfitService {

    @Autowired
    AplCacheHelper aplCacheHelper;

    /**
     * 添加 or 修改 统一利润
     * @param unifyProfitDto
     * @return
     */
    @Override
    public Integer saveUnifyProfit(UnifyProfitDto unifyProfitDto) throws IOException {

        //将Dto客户组转换成Po客户组
        UnifyExpPricePo unifyExpPricePo = handCustomerGroupDtoToPo(unifyProfitDto);
        BeanUtil.copyProperties(unifyProfitDto, unifyExpPricePo);

        //id大于0就是修改  小于0就是删除
        if(null != unifyProfitDto.getId() && unifyProfitDto.getId() > 0){
            Integer resultNum = baseMapper.updateUnifyProfit(unifyExpPricePo);
            SecurityUser securityUser = SecurityUserNetService.getSecurityUser(aplCacheHelper);
            aplCacheHelper.opsForKey("exp-price-unify-profit").delByBucket(securityUser.getInnerOrgId());

            return resultNum;
        }else{
            unifyExpPricePo.setId(SnowflakeIdWorker.generateId());
            if(null == unifyExpPricePo.getStartWeight())
                unifyExpPricePo.setStartWeight(0d);
            if(null == unifyExpPricePo.getEndWeight())
                unifyExpPricePo.setEndWeight(10000d);
            if(null == unifyExpPricePo.getFirstWeightProfit())
                unifyExpPricePo.setFirstWeightProfit(0d);
            if(null == unifyExpPricePo.getUnitWeightProfit())
                unifyExpPricePo.setUnitWeightProfit(0d);
            if(null == unifyExpPricePo.getProportionProfit())
                unifyExpPricePo.setProportionProfit(0d);

            return baseMapper.insertUnifyProfit(unifyExpPricePo);
        }

    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public Integer del(List<Long> ids) throws IOException {

        SecurityUser securityUser = SecurityUserNetService.getSecurityUser(aplCacheHelper);
        aplCacheHelper.opsForKey("exp-price-unify-profit").delByBucket(securityUser.getInnerOrgId());

        return baseMapper.deleteBatch(ids);
    }

    /**
     * 获取列表, 可以按客户组id进行查询
     * @param customerGroupId
     * @return
     */
    @Override
    public List<UnifyProfitDto> getList(Long customerGroupId, Long tenantId) {

        if(tenantId < 1){
            SecurityUser securityUser =  CommonContextHolder.getSecurityUser();
            tenantId = securityUser.getInnerOrgId();
        }

        List<UnifyProfitDto> priceExpProfitDtoList = new ArrayList<>();
        if(null == customerGroupId)
            customerGroupId = 0L;
        List<UnifyExpPricePo> unifyProfit = baseMapper.getUnifyProfit(customerGroupId, tenantId);

        for (UnifyExpPricePo unifyExpPricePo : unifyProfit) {
            UnifyProfitDto unifyProfitDto = handCustomerGroupPoToDto(unifyExpPricePo);
            BeanUtil.copyProperties(unifyExpPricePo, unifyProfitDto);
            priceExpProfitDtoList.add(unifyProfitDto);
        }

        return priceExpProfitDtoList;
    }

    /**
     * 获取单个统一利润, 不对前端提供接口
     * @param id
     * @return
     */
    public UnifyProfitDto get(Long id) {

        UnifyExpPricePo unifyExpPricePo = baseMapper.selectById(id);
        UnifyProfitDto unifyProfitDto = handCustomerGroupPoToDto(unifyExpPricePo);
        BeanUtils.copyProperties(unifyExpPricePo, unifyProfitDto);

        return unifyProfitDto;
    }


    /**
     * 处理客户组 将Po 转换成 Dto
     * @param unifyExpPricePo
     * @return
     */
    public UnifyProfitDto handCustomerGroupPoToDto(UnifyExpPricePo unifyExpPricePo){

        UnifyProfitDto unifyProfitDto = null;
        if (unifyExpPricePo.getCustomerGroupIds() != null && !unifyExpPricePo.getCustomerGroupIds().equals("")
                && !unifyExpPricePo.getCustomerGroupNames().equals("") && unifyExpPricePo.getCustomerGroupNames() != null) {

            String customerGroupIds = unifyExpPricePo.getCustomerGroupIds().replace("[", "")
                    .replace("]", "").replaceAll(" ", "");

            String customerGroupName = unifyExpPricePo.getCustomerGroupNames().replace("[", "")
                    .replace("]", "").replaceAll(" ", "");

            List<CustomerGroupBo> customerGroupDtoList = new ArrayList<>();
            String[] customerGroupIdArr = customerGroupIds.split(",");
            String[] customerGroupNameArr = customerGroupName.split(",");

            for (int i = 0; i < customerGroupIdArr.length; i++) {
                CustomerGroupBo customerGroupBo = new CustomerGroupBo();
                if(null != customerGroupIdArr[i] && !customerGroupIdArr[i].equals("")) {
                    customerGroupBo.setCustomerGroupId(Long.valueOf(customerGroupIdArr[i]));
                }
                customerGroupBo.setCustomerGroupName(customerGroupNameArr[i]);
                customerGroupDtoList.add(customerGroupBo);
            }
            unifyProfitDto = new UnifyProfitDto();
            unifyProfitDto.setCustomerGroups(customerGroupDtoList);
        }

        return unifyProfitDto;
    }

    /**
     * 处理客户组 将Dto 转换成 Po
     * @param unifyProfitDto
     * @return
     */
    public UnifyExpPricePo handCustomerGroupDtoToPo(UnifyProfitDto unifyProfitDto){

        UnifyExpPricePo unifyExpPricePo = new UnifyExpPricePo();
        if (null != unifyProfitDto.getCustomerGroups() && unifyProfitDto.getCustomerGroups().size() > 0) {
            StringBuffer customerGroupId = new StringBuffer();
            StringBuffer customerGroupName = new StringBuffer();
            for (CustomerGroupBo customerGroupDto : unifyProfitDto.getCustomerGroups()) {
                if (customerGroupId.length() > 0)
                    customerGroupId.append(",");
                if (customerGroupName.length() > 0)
                    customerGroupName.append(", ");
                customerGroupId.append(customerGroupDto.getCustomerGroupId());
                customerGroupName.append(customerGroupDto.getCustomerGroupName());
            }
            unifyExpPricePo.setCustomerGroupIds(customerGroupId.toString());
            unifyExpPricePo.setCustomerGroupNames(customerGroupName.toString());
        } else {
            unifyExpPricePo.setCustomerGroupIds("");
            unifyExpPricePo.setCustomerGroupNames("");
        }

        return unifyExpPricePo;
    }
}
