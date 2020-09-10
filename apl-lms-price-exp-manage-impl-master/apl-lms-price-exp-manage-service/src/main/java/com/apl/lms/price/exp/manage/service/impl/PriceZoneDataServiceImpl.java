package com.apl.lms.price.exp.manage.service.impl;
import com.apl.cache.AplCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.join.JoinBase;
import com.apl.lib.join.JoinFieldInfo;
import com.apl.lib.join.JoinUtil;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.common.lib.cache.JoinCountry;
import com.apl.lms.common.lib.feign.LmsCommonFeign;
import com.apl.lms.price.exp.manage.mapper.PriceZoneDataMapper;
import com.apl.lms.price.exp.manage.service.PriceZoneDataService;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceZoneDataServiceImpl
 * @Date 2020/8/31 15:16
 */
@Service
@Slf4j
public class PriceZoneDataServiceImpl extends ServiceImpl<PriceZoneDataMapper, PriceZoneDataListVo> implements PriceZoneDataService {

    JoinFieldInfo joinCountryFieldInfo = null;

    @Autowired
    AplCacheUtil redisTemplate;

    @Autowired
    LmsCommonFeign lmsCommonFeign;

    enum PriceZoneDataServiceCode {
        ID_DOES_NOT_EXITS("ID_DOES_NOT_EXITS", "id不存在");

        private String code;
        private String msg;

        PriceZoneDataServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 获取列表
     * @param id
     * @return
     */
    @Override
    public ResultUtil<List<PriceZoneDataListVo>> getList(Long id) throws Exception {
        List<PriceZoneDataListVo> priceZoneDataListVo = baseMapper.getList(id);
        if(priceZoneDataListVo.size() == 0){
            return ResultUtil.APPRESULT(PriceZoneDataServiceCode.ID_DOES_NOT_EXITS.code, PriceZoneDataServiceCode.ID_DOES_NOT_EXITS.msg, null);
        }

        List<JoinBase> joinTabs = new ArrayList<>();
        JoinCountry joinCountry = new JoinCountry(1, lmsCommonFeign, redisTemplate);

        if(null!=joinCountryFieldInfo) {
            //已经缓存国家反射字段
            joinCountry.setJoinFieldInfo(joinCountryFieldInfo);
        }
        else{
            joinCountry.addField("countryCode",  String.class, "nameCn", "countryNameCn",String.class);
            joinCountry.addField("countryCode", String.class, "nameEn", "countryNameEn",String.class);

            joinCountryFieldInfo = joinCountry.getJoinFieldInfo();
        }

        joinTabs.add(joinCountry);

        JoinUtil.join(priceZoneDataListVo, joinTabs);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceZoneDataListVo);
    }

    @Override
    public ResultUtil<Boolean> deleteBatch(List<Long> ids) {
        Integer resInteger = baseMapper.deleteByZoneId(ids);
        if(resInteger < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL.code, PriceZoneDataServiceCode.ID_DOES_NOT_EXITS.msg, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, resInteger);
    }
}
