package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpDataMapper;
import com.apl.lms.price.exp.manage.service.PriceExpCostService;
import com.apl.lms.price.exp.manage.service.PriceExpDataService;
import com.apl.lms.price.exp.manage.service.PriceExpSaleService;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddDto;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpDataServiceImpl extends ServiceImpl<PriceExpDataMapper, PriceExpDataPo> implements PriceExpDataService {

    enum PriceExpDataServiceCode {
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有对应数据");

        private String code;
        private String msg;

        PriceExpDataServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Autowired
    PriceExpCostService priceExpCostService;

    @Autowired
    PriceExpSaleService priceExpSaleService;


    /**
     * 根据价格表Id获取详细
     * @param id
     * @return
     */
    @Override
    public ResultUtil<PriceExpDataVo> getPriceExpDataInfoByPriceId(Long id) {
        Long resMainId = priceExpCostService.getMainId(id);
        Long resMainId2 = priceExpSaleService.getMainId(id);

        Long mainId = 0L;
        if(resMainId != null && resMainId != 0){
            mainId = resMainId;
        }else if(resMainId2 != null && resMainId2 != 0){
            mainId = resMainId2;
        }

        PriceExpDataVo priceExpDataVo = baseMapper.getPriceExpDataInfoById(mainId);

        if (null == priceExpDataVo.getId()) {
            return ResultUtil.APPRESULT(PriceExpDataServiceCode.NO_CORRESPONDING_DATA.code,
                    PriceExpDataServiceCode.NO_CORRESPONDING_DATA.msg, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceExpDataVo);
    }


    /**
     * 保存价格表数据
     * @param priceMainId
     * @return
     */
    @Override
    public Boolean addPriceExpData(Long priceMainId, PriceExpAddDto priceExpAddDto) {

        PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
        priceExpDataPo.setPriceData(priceExpAddDto.getPriceData());
        priceExpDataPo.setId(priceMainId);
        Integer saveSuccess = baseMapper.insertData(priceExpDataPo);
        return saveSuccess > 0 ? true :false;
    }

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    @Override
    public Boolean updById(PriceExpDataPo priceExpDataPo) {
        Integer integer = baseMapper.updById(priceExpDataPo);
        return integer > 0 ? true : false;
    }

    /**
     * 批量删除
     */
    @Override
    public Integer delBatch(String ids) {
        return baseMapper.delBatch(ids);
    }

}
