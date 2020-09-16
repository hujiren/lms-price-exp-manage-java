package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpAxisMapper;
import com.apl.lms.price.exp.manage.service.PriceExpAxisService;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpAxisVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpAxisServiceImpl extends ServiceImpl<PriceExpAxisMapper, PriceExpAxisPo> implements PriceExpAxisService {


    /**
     * 保存价格表轴数据
     * @param priceMainId
     * @param priceExpAddDto
     * @return
     */
    @Override
    public Boolean addPriceExpAxis(Long priceMainId, PriceExpAddDto priceExpAddDto) {
        PriceExpAxisPo priceExpAxisPo = new PriceExpAxisPo();
        priceExpAxisPo.setId(priceMainId);
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
        if(priceExpAxisVo == null){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpAxisVo);
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
