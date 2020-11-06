package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.UnifyProfitMapper;
import com.apl.lms.price.exp.manage.service.UnifyProfitService;
import com.apl.lms.price.exp.pojo.bo.CustomerGroupBo;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.apl.lms.price.exp.pojo.dto.UnifyProfitDto;
import com.apl.lms.price.exp.pojo.po.UnifyExpPricePo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    /**
     * 添加 or 修改 统一利润
     * @param unifyProfitDto
     * @return
     */
    @Override
    public Integer saveUnifyProfit(UnifyProfitDto unifyProfitDto) {

        //将Dto客户组转换成Po客户组
        UnifyExpPricePo unifyExpPricePo = handCustomerGroupDtoToPo(unifyProfitDto);
        BeanUtil.copyProperties(unifyProfitDto, unifyExpPricePo);

        //id大于0就是修改  小于0就是删除
        if(null != unifyProfitDto.getId() && unifyProfitDto.getId() > 0){
            return baseMapper.updateUnifyProfit(unifyExpPricePo);
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
    public Integer del(List<Long> ids) {
        return baseMapper.deleteBatch(ids);
    }

    /**
     * 获取列表, 可以按客户组id进行查询
     * @param customerGroupId
     * @return
     */
    @Override
    public List<UnifyProfitDto> getList(Long customerGroupId) {

        List<UnifyProfitDto> priceExpProfitDtoList = new ArrayList<>();
        if(null == customerGroupId)
            customerGroupId = 0L;
        List<UnifyExpPricePo> unifyProfit = baseMapper.getUnifyProfit(customerGroupId);

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
     * 根据客户组id和租户id进行筛选
     * @param customerGroupId
     * @param innerOrgId
     * @return
     */
    @Override
    public List<PriceExpProfitDto> getListForTenant(Long customerGroupId, Long innerOrgId) {
        if(customerGroupId == null)
            customerGroupId = 0L;
        if(innerOrgId == null)
            innerOrgId = 0L;
        List<PriceExpProfitDto> profitDtoList = new ArrayList<>();
        List<UnifyExpPricePo> listForTenant = baseMapper.getListForTenant(customerGroupId, innerOrgId);
        for (UnifyExpPricePo unifyExpPricePo : listForTenant) {
            UnifyProfitDto unifyProfitDto = handCustomerGroupPoToDto(unifyExpPricePo);
            PriceExpProfitDto profitDto = new PriceExpProfitDto();
            BeanUtil.copyProperties(unifyProfitDto,profitDto);
            profitDtoList.add(profitDto);
        }

        return profitDtoList;
    }

    /**
     * 处理客户组 将Po 转换成 Dto
     * @param unifyExpPricePo
     * @return
     */
    public UnifyProfitDto handCustomerGroupPoToDto(UnifyExpPricePo unifyExpPricePo){

        UnifyProfitDto unifyProfitDto  = new UnifyProfitDto();
        if (unifyExpPricePo.getCustomerGroupId() != null && !unifyExpPricePo.getCustomerGroupId().equals("") && !unifyExpPricePo.getCustomerGroupName().equals("") && unifyExpPricePo.getCustomerGroupName() != null) {
            String customerGroupIds = unifyExpPricePo.getCustomerGroupId().replace("[", "").replace("]", "").replaceAll(" ", "");
            String customerGroupName = unifyExpPricePo.getCustomerGroupName().replace("[", "").replace("]", "").replaceAll(" ", "");

            List<CustomerGroupBo> customerGroupDtoList = new ArrayList<>();
            String[] customerGroupIdArr = customerGroupIds.split(",");
            String[] customerGroupNameArr = customerGroupName.split(",");

            for (int i = 0; i < customerGroupIdArr.length; i++) {
                CustomerGroupBo customerGroupBo = new CustomerGroupBo();
                if(null != customerGroupIdArr[i] && !customerGroupIdArr[i].equals("")) {
                    customerGroupBo.setId(Long.valueOf(customerGroupIdArr[i]));
                }
                customerGroupBo.setCustomerGroupName(customerGroupNameArr[i]);
                customerGroupDtoList.add(customerGroupBo);
            }
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
                customerGroupId.append(customerGroupDto.getId());
                customerGroupName.append(customerGroupDto.getCustomerGroupName());
            }
            unifyExpPricePo.setCustomerGroupId(customerGroupId.toString());
            unifyExpPricePo.setCustomerGroupName(customerGroupName.toString());
        } else {
            unifyExpPricePo.setCustomerGroupId("");
            unifyExpPricePo.setCustomerGroupName("");
        }
        return unifyExpPricePo;
    }
}
