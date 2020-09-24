package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpRemarkMapper;
import com.apl.lms.price.exp.manage.service.PriceExpRemarkService;
import com.apl.lms.price.exp.pojo.po.PriceExpRemarkPo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpRemarkServiceImpl extends ServiceImpl<PriceExpRemarkMapper, PriceExpRemarkPo> implements PriceExpRemarkService {

    enum PriceExpRemarkEnum{
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA","没有对应数据"),
        ID_IS_NOT_EXISTS("ID_IS_NOT_EXISTS", "id不存在");

        private String code;
        private String msg;

        PriceExpRemarkEnum(String code, String msg){
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    @Override
    public ResultUtil<PriceExpRemarkPo> getPriceExpRemark(Long id) {
        PriceExpRemarkPo priceExpRemarkPo = baseMapper.getById(id);
        if (null == priceExpRemarkPo) {
            return ResultUtil.APPRESULT(PriceExpRemarkEnum.NO_CORRESPONDING_DATA.code, PriceExpRemarkEnum.NO_CORRESPONDING_DATA.msg, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpRemarkPo);
    }

    /**
     * 更新
     * @param priceExpRemarkPo
     * @return
     */
    @Override
    public Boolean updateRemark(PriceExpRemarkPo priceExpRemarkPo) {
        Long checkId = baseMapper.exists(priceExpRemarkPo.getId());
        if(null == checkId || checkId.equals(0))
        throw new AplException(PriceExpRemarkEnum.ID_IS_NOT_EXISTS.code, PriceExpRemarkEnum.ID_IS_NOT_EXISTS.msg);
        Integer integer =  baseMapper.updateById(priceExpRemarkPo);

        return  integer>0;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public Integer delBatch(String ids) {
        Integer res = baseMapper.delBatch(ids);
        return res;
    }
}
