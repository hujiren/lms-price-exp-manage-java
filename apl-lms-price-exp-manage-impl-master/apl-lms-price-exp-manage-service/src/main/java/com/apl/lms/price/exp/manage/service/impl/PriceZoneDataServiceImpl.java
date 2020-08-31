package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceZoneDataMapper;
import com.apl.lms.price.exp.manage.service.PriceZoneDataService;
import com.apl.lms.price.exp.pojo.dto.PriceZoneNameKeyDto;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceZoneDataServiceImpl
 * @Date 2020/8/31 15:16
 */
@Service
@Slf4j
public class PriceZoneDataServiceImpl extends ServiceImpl<PriceZoneDataMapper, PriceZoneDataListVo> implements PriceZoneDataService {

    /**
     * 获取列表
     * @param priceZoneNameKeyDto
     * @return
     */
    @Override
    public ResultUtil<List<PriceZoneDataListVo>> getList(PriceZoneNameKeyDto priceZoneNameKeyDto) {
        List<PriceZoneDataListVo> priceZoneDataListVo = baseMapper.getList(priceZoneNameKeyDto);
        if(priceZoneDataListVo.size() == 0){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceZoneDataListVo);
    }

    @Override
    public ResultUtil<Boolean> deleteBatch(List<Long> ids) {
        Integer resInteger = baseMapper.deleteBatchIds(ids);
        if(resInteger < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, resInteger);
    }
}
