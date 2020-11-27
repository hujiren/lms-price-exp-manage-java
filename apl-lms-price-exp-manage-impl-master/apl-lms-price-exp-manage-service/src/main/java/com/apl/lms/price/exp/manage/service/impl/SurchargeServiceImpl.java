package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.SurchargeMapper;
import com.apl.lms.price.exp.manage.service.SurchargeService;
import com.apl.lms.price.exp.pojo.po.SurchargePo;
import com.apl.lms.price.exp.pojo.dto.SurchargeKeyDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author hjr
 * @since 2020-08-08
 */
@Service
@Slf4j
public class SurchargeServiceImpl extends ServiceImpl<SurchargeMapper, SurchargePo> implements SurchargeService {

    enum SurchargeServiceCode {
        ID_DOES_NOT_EXITS("ID_DOES_NOT_EXITS", "id不存在");
        ;

        private String code;
        private String msg;

        SurchargeServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 分页查找附加费
     * @param surchargeKeyDto
     * @return
     */
    @Override
    public ResultUtil<List<SurchargePo>> getList(SurchargeKeyDto surchargeKeyDto) {
        if(null == surchargeKeyDto.getCode() || surchargeKeyDto.getCode() < 0){
            surchargeKeyDto.setCode(0);
        }
        List<SurchargePo> surchargeUpdDtoList = baseMapper.getList(surchargeKeyDto);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, surchargeUpdDtoList);
    }

    /**
     * 根据id删除附加费
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delSurcharge(Long id) {
        Integer resultNum = baseMapper.delById(id);
        if(resultNum < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL.code, SurchargeServiceCode.ID_DOES_NOT_EXITS.msg, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 添加附加费
     * @param surchargePoList
     * @return
     */
    @Override
    public ResultUtil<Integer> addSurcharge(List<SurchargePo> surchargePoList) {

        for (SurchargePo surchargeDtoList : surchargePoList) {

            surchargeDtoList.setId(SnowflakeIdWorker.generateId());
        }

        Integer resultNum = baseMapper.addSurcharge(surchargePoList);
        if(resultNum < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, resultNum);
    }

}
