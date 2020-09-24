package com.apl.lms.price.exp.manage.service.impl;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.mapper.PriceExpAxisMapper;
import com.apl.lms.price.exp.manage.service.PriceExpAxisService;
import com.apl.lms.price.exp.manage.service.PriceExpService;
import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddDto;
import com.apl.lms.price.exp.pojo.dto.WeightSectionDto;
import com.apl.lms.price.exp.pojo.dto.WeightSectionUpdDto;
import com.apl.lms.price.exp.pojo.po.PriceExpAxisPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpAxisVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpAxisServiceImpl extends ServiceImpl<PriceExpAxisMapper, PriceExpAxisPo> implements PriceExpAxisService {

    enum PriceExpAxisServiceCode {
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有对应数据"),
        ID_IS_NOT_EXIST("ID_IS_NOT_EXIST", "id不存在"),
        PLEASE_FILL_IN_THE_DATA_FIRST("PLEASE_FILL_IN_THE_DATA_FIRST", "请先填写数据");

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
        priceExpAxisPo.setAxisPortrait(priceExpAddDto.getAxisPortrait());
        priceExpAxisPo.setAxisTransverse(priceExpAddDto.getAxisTransverse());
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

        Integer result = 0;
        Long checkId = baseMapper.exists(priceExpAxisPo.getId());
        if(null == checkId || checkId.equals(0)){
            result = baseMapper.insertAxis(priceExpAxisPo);
        }
        else {
            result = baseMapper.updateByMainId(priceExpAxisPo);
        }


        return result > 0 ? true : false;
    }


    /**
     * 获取详细
     * @param priceId
     * @return
     */
    @Override
    public ResultUtil<PriceExpAxisVo> getAxisInfoById(Long priceId) {
        ExpPriceInfoBo expPriceInfoBo = priceExpService.getInnerOrgIdAndPriceDatId(priceId);
        if (null==expPriceInfoBo) {
            throw new AplException(PriceExpAxisServiceCode.NO_CORRESPONDING_DATA.code,
                    PriceExpAxisServiceCode.NO_CORRESPONDING_DATA.msg, null);
        }
        PriceExpAxisPo priceExpAxisPo = baseMapper.getAxisInfoById(expPriceInfoBo.getPriceDataId());
        if(null == priceExpAxisPo){
            throw new AplException(PriceExpAxisServiceCode.NO_CORRESPONDING_DATA.code,
                    PriceExpAxisServiceCode.NO_CORRESPONDING_DATA.msg, null);
        }

        PriceExpAxisVo priceExpAxisVo = new PriceExpAxisVo();
        priceExpAxisVo.setPriceDataId(priceExpAxisPo.getId());
        priceExpAxisVo.setPriceFormat(expPriceInfoBo.getPriceFormat());

        List<List<String>> zoneCountry = null;
        List<List<String>> weightSectionObjs = null;
        if(expPriceInfoBo.getPriceFormat().equals(1)){
            weightSectionObjs =  priceExpAxisPo.getAxisTransverse();
            zoneCountry = priceExpAxisPo.getAxisPortrait();

        }else{
            weightSectionObjs =  priceExpAxisPo.getAxisPortrait();
            zoneCountry = priceExpAxisPo.getAxisTransverse();
        }

        List<WeightSectionDto> weightSectionDtos  = new ArrayList<>();
        for (List<String> cells : weightSectionObjs) {
            WeightSectionDto weightSectionDto = new WeightSectionDto();

            int index = Integer.parseInt(cells.get(0));
            weightSectionDto.setIndex(index);

            int pageType = Integer.parseInt(cells.get(1));
            weightSectionDto.setPackType(pageType);

            int chargingWay = Integer.parseInt(cells.get(2));
            weightSectionDto.setChargingWay(chargingWay);

            Double weightStart = Double.parseDouble(cells.get(3));
            weightSectionDto.setWeightStart(weightStart);

            Double weightEnd = Double.parseDouble(cells.get(4));
            weightSectionDto.setWeightEnd(weightEnd);

            Double weightAdd = Double.parseDouble(cells.get(5));
            weightSectionDto.setWeightAdd(weightAdd);

            Double weightFirst = Double.parseDouble(cells.get(6));
            weightSectionDto.setWeightFirst(weightFirst);

            weightSectionDtos.add(weightSectionDto);
        }

        priceExpAxisVo.setWeightSection(weightSectionDtos);
        priceExpAxisVo.setZoneCountry(zoneCountry);

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
