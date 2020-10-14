package com.apl.lms.price.exp.manage.service.impl;

import com.apl.cache.AplCacheUtil;
import com.apl.db.adb.AdbHelper;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.join.JoinBase;
import com.apl.lib.join.JoinFieldInfo;
import com.apl.lib.join.JoinUtil;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.common.lib.cache.JoinSpecialCommodity;
import com.apl.lms.common.lib.feign.LmsCommonFeign;
import com.apl.lms.price.exp.manage.mapper.PriceSurchargeMapper;
import com.apl.lms.price.exp.manage.service.PriceSurchargeService;
import com.apl.lms.price.exp.pojo.po.PriceSurchargePo;
import com.apl.lms.price.exp.pojo.vo.PriceSurchargeVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-09-28
 */
@Service
@Slf4j
public class PriceSurchargeServiceImpl extends ServiceImpl<PriceSurchargeMapper, PriceSurchargePo> implements PriceSurchargeService {

    //状态code枚举
    /*enum SurchargeServiceCode {

            ;

            private String code;
            private String msg;

            SurchargeServiceCode(String code, String msg) {
                 this.code = code;
                 this.msg = msg;
            }
        }*/

    @Autowired
    LmsCommonFeign lmsCommonFeign;

    @Autowired
    AplCacheUtil aplCacheUtil;

    @Autowired
    AdbHelper adbHelper;

    static JoinFieldInfo joinSpecialCommodityFieldInfo = null; //跨项目跨库关联 特殊物品 反射字段缓存

    @Override
    @Transactional
    public ResultUtil<Boolean> save(List<PriceSurchargePo> priceSurchargePos) throws Exception {
        adbHelper.saveBatch(priceSurchargePos, "price_surcharge", "id", true);

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }


    @Override
    public ResultUtil<Boolean> delById(Long id) {

        baseMapper.deleteById(id);
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);

    }


    @Override
    public List<PriceSurchargeVo> selectById(Long priceId) throws Exception {

        List<PriceSurchargeVo> priceSurchargeList = baseMapper.getByPriceId(priceId);
        //组装特殊物品
        JoinSpecialCommodity joinSpecialCommodity = new JoinSpecialCommodity(1, lmsCommonFeign, aplCacheUtil);

        List<JoinBase> joinTabs = new ArrayList<>();
        //关联特殊物品字段信息
        if (null != joinSpecialCommodityFieldInfo) {
            joinSpecialCommodity.setJoinFieldInfo(joinSpecialCommodityFieldInfo);
        } else {
            joinSpecialCommodity.addField("specialCommodityCode", Integer.class, "specialCommodityName", String.class);
            joinSpecialCommodityFieldInfo = joinSpecialCommodity.getJoinFieldInfo();
        }
        joinTabs.add(joinSpecialCommodity);
        //执行跨项目跨库关联
        JoinUtil.join(priceSurchargeList, joinTabs);

        return priceSurchargeList;
    }

    @Override
    public List<PriceSurchargePo> getById(Long priceId) {
        List<PriceSurchargePo> priceSurchargeList = baseMapper.selectByPriceId(priceId);
        return priceSurchargeList;
    }

    @Override
    public List<Long> getIdBatch(Long priceId) {
        List<Long> surchargeIds = baseMapper.getIdBatchByPriceId(priceId);
        return surchargeIds;
    }

    @Override
    public Integer delBatch(String ids) {
        Integer res = baseMapper.delBatch(ids);
        return res;
    }
}