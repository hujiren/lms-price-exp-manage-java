package com.apl.lms.price.exp.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.apl.db.adb.AdbHelper;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceIncreaseProfitMapper;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.manage.service.PriceIncreaseProfitService;
import com.apl.lms.price.exp.pojo.bo.CustomerGroupBo;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.IncreaseProfitDto;
import com.apl.lms.price.exp.pojo.dto.PriceExpProfitDto;
import com.apl.lms.price.exp.pojo.po.PriceExpMainPo;
import com.apl.lms.price.exp.pojo.po.PriceIncreaseProfitPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceIncreaseProfitServiceImpl
 * @Date 2020/11/14 10:18
 */
@Service
@Slf4j
public class PriceIncreaseProfitServiceImpl extends ServiceImpl<PriceIncreaseProfitMapper, PriceIncreaseProfitPo> implements PriceIncreaseProfitService {

    enum PriceIncreaseProfitEnum{

        WITHOUT_THE_PRICE("Without the price", "无此价格");

        private String code;
        private String msg;

        PriceIncreaseProfitEnum(String code, String msg){
            this.code = code;
            this.msg = msg;
        }
    }

    @Autowired
    AdbHelper adbHelper;

    @Autowired
    PriceExpService priceExpService;
    /**
     * 根据价格表id获取增加的利润列表
     * @param priceId
     * @return
     */
    @Override
    public ResultUtil<IncreaseProfitDto> getList(Long priceId) {

        IncreaseProfitDto increaseProfitVo = new IncreaseProfitDto();
        ExpPriceInfoBo priceInfo = priceExpService.getPriceInfo(priceId);
        if(null == priceInfo)
            return ResultUtil.APPRESULT(PriceIncreaseProfitEnum.WITHOUT_THE_PRICE.code, PriceIncreaseProfitEnum.WITHOUT_THE_PRICE.msg, null);

        List<PriceIncreaseProfitPo> increaseProfitPoList = baseMapper.getList(priceId);
        List<PriceExpProfitDto> newIncreaseProfitList = new ArrayList<>();

        //处理客户组
        if(null != increaseProfitPoList && increaseProfitPoList.size() > 0){
            for (PriceIncreaseProfitPo increaseProfitPo : increaseProfitPoList) {

                PriceExpProfitDto priceExpProfitDto = new PriceExpProfitDto();
                BeanUtil.copyProperties(increaseProfitPo, priceExpProfitDto);

                String customerGroupIds = increaseProfitPo.getCustomerGroupIds();
                String customerGroupNames = increaseProfitPo.getCustomerGroupNames();
                String[] customerGroupIdArr = customerGroupIds.split(",");
                String[] customerGroupNameArr = customerGroupNames.split(",");

                List<CustomerGroupBo> customerGroupBoList = new ArrayList<>();
                for(int i = 0; i < customerGroupIdArr.length; i++){
                    CustomerGroupBo customerGroupBo = new CustomerGroupBo();
                    String customerGroupIdStr = customerGroupIdArr[i];
                    String customerGroupNameStr = customerGroupNameArr[i];
                    customerGroupBo.setCustomerGroupId(Long.parseLong(customerGroupIdStr));
                    customerGroupBo.setCustomerGroupName(customerGroupNameStr);
                    customerGroupBoList.add(customerGroupBo);
                }
                priceExpProfitDto.setCustomerGroups(customerGroupBoList);
                newIncreaseProfitList.add(priceExpProfitDto);
            }
        }
        increaseProfitVo.setAddProfitWay(priceInfo.getAddProfitWay());
        increaseProfitVo.setIncreaseProfit(newIncreaseProfitList);
        increaseProfitVo.setId(priceId);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, increaseProfitVo);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public ResultUtil<Boolean> deleteBatch(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 批量保存
     * @param increaseProfitDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> saveBatchIncreaseProfit(IncreaseProfitDto increaseProfitDto) throws Exception {

        Integer addProfitWay = increaseProfitDto.getAddProfitWay();
        if(null != addProfitWay){
            PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
            priceExpMainPo.setId(increaseProfitDto.getId());
            priceExpMainPo.setAddProfitWay(addProfitWay);
            priceExpService.updatePriceExpMain(priceExpMainPo);
        }

        List<PriceExpProfitDto> increaseProfitList = increaseProfitDto.getIncreaseProfit();
        List<PriceIncreaseProfitPo> priceIncreaseProfitPoList = new ArrayList<>();
        for (PriceExpProfitDto profitDto : increaseProfitList) {

            PriceIncreaseProfitPo priceIncreaseProfitPo = new PriceIncreaseProfitPo();
            BeanUtil.copyProperties(profitDto, priceIncreaseProfitPo);
            priceIncreaseProfitPo.setPriceId(increaseProfitDto.getId());

            List<CustomerGroupBo> customerGroups = profitDto.getCustomerGroups();

            StringBuffer sbCustomerGroupId = new StringBuffer();
            StringBuffer sbCustomerGroupName = new StringBuffer();
            for (CustomerGroupBo customerGroup : customerGroups) {

                Long getCustomerGroupId = customerGroup.getCustomerGroupId();
                String customerGroupName = customerGroup.getCustomerGroupName();

                if(sbCustomerGroupId.length() > 0){
                    sbCustomerGroupId.append(",");
                    sbCustomerGroupName.append(",");
                }
                sbCustomerGroupId.append(getCustomerGroupId);
                sbCustomerGroupName.append(customerGroupName);
            }

            priceIncreaseProfitPo.setCustomerGroupIds(sbCustomerGroupId.toString());
            priceIncreaseProfitPo.setCustomerGroupNames(sbCustomerGroupName.toString());
            priceIncreaseProfitPoList.add(priceIncreaseProfitPo);
        }
        adbHelper.saveBatch(priceIncreaseProfitPoList, "price_increase_profit", "id", true);

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }
}
