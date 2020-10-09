package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.FreightTypeMapper;
import com.apl.lms.price.exp.manage.service.FreightTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lms.price.exp.pojo.po.FreightTypePo;
import java.util.List;


/**
 * <p>
 *  service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-10-07
 */
@Service
@Slf4j
public class FreightTypeServiceImpl extends ServiceImpl<FreightTypeMapper, FreightTypePo> implements FreightTypeService {

    //状态code枚举
    enum FreightTypeServiceCode {

        DATA_CANNOT_BE_ADDED_REPEATEDLY("DATA_CANNOT_BE_ADDED_REPEATEDLY", "数据不能重复添加:");

            private String code;
            private String msg;

            FreightTypeServiceCode(String code, String msg) {
                 this.code = code;
                 this.msg = msg;
            }
        }

    @Override
    public ResultUtil<Long> add(List<FreightTypePo> freightTypePoList){

        List<FreightTypePo> resultList = getList();

        for (FreightTypePo typePo : freightTypePoList) {
            if(null != resultList && resultList.size() > 0){
                for (FreightTypePo freightTypePo : resultList) {
                    if(freightTypePo.getFreightTypeName().equals(typePo.getFreightTypeName())){
                        return ResultUtil.APPRESULT(FreightTypeServiceCode.DATA_CANNOT_BE_ADDED_REPEATEDLY.code,
                                FreightTypeServiceCode.DATA_CANNOT_BE_ADDED_REPEATEDLY.msg + " " +typePo.getFreightTypeName(), null);
                    }
                    if(freightTypePo.getCode().equals(typePo.getCode())){
                        return ResultUtil.APPRESULT(FreightTypeServiceCode.DATA_CANNOT_BE_ADDED_REPEATEDLY.code,
                                FreightTypeServiceCode.DATA_CANNOT_BE_ADDED_REPEATEDLY.msg + " " +typePo.getCode(), null);
                    }
                }
            }
            typePo.setId(SnowflakeIdWorker.generateId());
        }

        Integer flag = baseMapper.addBatch(freightTypePoList);
        if(flag>0){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS , freightTypePoList.get(0).getId());
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL , null);
    }

    @Override
    public ResultUtil<Boolean> delById(Long id){

        baseMapper.deleteById(id);

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS , true);
    }


    @Override
    public List<FreightTypePo> getList(){

        List<FreightTypePo> list = baseMapper.getList();

        return list;
    }

    @Override
    public List<FreightTypePo> getListByInnerOrgId(Long innerOrgId) {

        List<FreightTypePo> list = baseMapper.getListByInnerOrgId(innerOrgId);

        return list;
    }
}