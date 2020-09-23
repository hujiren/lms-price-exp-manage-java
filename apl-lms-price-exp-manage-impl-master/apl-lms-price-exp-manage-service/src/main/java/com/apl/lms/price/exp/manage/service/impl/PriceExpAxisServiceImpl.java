package com.apl.lms.price.exp.manage.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpAxisMapper;
import com.apl.lms.price.exp.manage.service.PriceExpAxisService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpAxisAVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpAxisVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpAxisServiceImpl extends ServiceImpl<PriceExpAxisMapper, PriceExpAxisPo> implements PriceExpAxisService {

    enum PriceExpAxisServiceCode {
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有对应数据");

        private String code;
        private String msg;

        PriceExpAxisServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
    @Autowired
    PriceExpService priceExpService;
    /**
     * 保存价格表轴数据
     * @param priceDataId
     * @param priceExpAddDto
     * @return
     */
    @Override
    public Boolean addPriceExpAxis(Long priceDataId, PriceExpAddDto priceExpAddDto) {
        PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
        priceExpAxisPo.setId(priceDataId);
        priceExpAxisPo.setAxisPortrait(priceExpAddDto.getAxisPortrait().toString());
        priceExpAxisPo.setAxisTransverse(priceExpAddDto.getAxisTransverse().toString());
        Integer saveSuccess = baseMapper.insertAxis(priceExpAxisPo);
        return saveSuccess > 0 ? true : false;
    }

    /**
     * 更新
     * @param priceExpAxisPo
     * @return
     */
    @Override
    public Boolean updateByMainId(PriceExpAxisPo priceExpAxisPo) {
        Integer integer = baseMapper.updateByMainId(priceExpAxisPo);
        return integer > 0 ? true : false;
    }

    /**
     * 获取详细
     * @param id
     * @return
     */
    @Override
    public ResultUtil<PriceExpAxisVo> getAxisInfoById(Long id) {

        PriceExpAxisVo priceExpAxisVo = baseMapper.getAxisInfoById(id);
        if(null == priceExpAxisVo){
            return ResultUtil.APPRESULT(PriceExpAxisServiceCode.NO_CORRESPONDING_DATA.code,
                    PriceExpAxisServiceCode.NO_CORRESPONDING_DATA.msg, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpAxisVo);
    }

    /**
     * 获取详细
     * @param id
     * @return
     */
    @Override
    public PriceExpAxisAVo getPriceAxisInfoById(Long id) {
        ExpPriceInfoBo expPriceInfoBo = priceExpService.getInnerOrgIdAndPriceDatId(id);
        PriceExpAxisAVo priceExpAxisAVo = new PriceExpAxisAVo();
        if(null != expPriceInfoBo && (expPriceInfoBo.getPriceDataId() != null || expPriceInfoBo.getPriceDataId() != 0)){
            PriceExpAxisVo priceExpAxisVo = baseMapper.getAxisInfoById(expPriceInfoBo.getPriceDataId());

            if(expPriceInfoBo.getPriceFormat().equals(1)){

            }else{
                List<Object> list = JSON.parseArray(priceExpAxisVo.getAxisPortrait(), Object.class);
                List<Object> list2 = JSON.parseArray(priceExpAxisVo.getAxisTransverse(), Object.class);
            }
        }else{
            throw new AplException(PriceExpAxisServiceCode.NO_CORRESPONDING_DATA.code,
                    PriceExpAxisServiceCode.NO_CORRESPONDING_DATA.msg, null);
        }

        return null;
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
