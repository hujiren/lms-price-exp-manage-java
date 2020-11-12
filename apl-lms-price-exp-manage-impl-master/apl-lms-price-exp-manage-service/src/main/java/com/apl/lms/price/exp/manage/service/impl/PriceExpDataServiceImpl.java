package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lms.price.exp.manage.mapper2.PriceExpDataMapper;
import com.apl.lms.price.exp.manage.service.PriceExpDataService;
import com.apl.lms.price.exp.pojo.bo.PriceExpProfitMergeBo;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddDto;
import com.apl.lms.price.exp.pojo.dto.WeightSectionDto;
import com.apl.lms.price.exp.pojo.dto.WeightSectionUpdDto;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataStringVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataServiceImpl
 * @Date 2020/8/19 17:31
 */
@Service
public class PriceExpDataServiceImpl extends ServiceImpl<PriceExpDataMapper, PriceExpDataPo> implements PriceExpDataService {

    enum PriceExpDataServiceCode {
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有对应数据"),
        ID_IS_NOT_EXIST("ID_IS_NOT_EXIST","id不存在");

        private String code;
        private String msg;

        PriceExpDataServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 根据价格表Id获取详细
     * @param id
     * @return
     */
    @Override
    public PriceExpDataVo getPriceExpDataInfoByPriceId(Long id) {

        PriceExpDataVo priceExpDataVo = baseMapper.getPriceExpDataInfoById(id);

        if (null == priceExpDataVo || null == priceExpDataVo.getPriceDataId()) {
            throw new AplException(PriceExpDataServiceCode.NO_CORRESPONDING_DATA.code,
                    PriceExpDataServiceCode.NO_CORRESPONDING_DATA.msg, null);
        }
        return priceExpDataVo;
    }


    /**
     * 保存价格表数据
     * @param priceDataId
     * @return
     */
    @Override
    public Boolean addPriceExpData(Long priceDataId, PriceExpAddDto priceExpAddDto) {

        PriceExpDataPo priceExpDataPo = new PriceExpDataPo();
        priceExpDataPo.setPriceData(priceExpAddDto.getPriceData());
        priceExpDataPo.setId(priceDataId);
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

        Integer saveResult = 0;
        Long checkId = baseMapper.exists(priceExpDataPo.getId());
        if(null==checkId || checkId.equals(0))
            saveResult = baseMapper.insertData(priceExpDataPo);
        else
            saveResult = baseMapper.updById(priceExpDataPo);

        return saveResult > 0 ? true : false;
    }

    /**
     * 批量删除
     */
    @Override
    public Integer delBatch(String ids) {
        return baseMapper.delBatch(ids);
    }

    /**
     * 更新表头
     * @param weightSectionUpdDto
     * @return
     */
    @Override
    @Transactional
    public List<String> updHeadCells(WeightSectionUpdDto weightSectionUpdDto,List<String> headCells) {

//        List<List<String>> allHeadCell = baseMapper.getHeadCells(weightSectionUpdDto.getPriceDataId());
        PriceExpDataStringVo priceExpDataStringVo = baseMapper.getHeadCells(weightSectionUpdDto.getPriceDataId());
        List<List<String>> allHeadCell = priceExpDataStringVo.getPriceData();
        if(null == allHeadCell)
            throw new AplException(PriceExpDataServiceCode.ID_IS_NOT_EXIST.code, PriceExpDataServiceCode.ID_IS_NOT_EXIST.msg);

        List<String> newHeadCells = new ArrayList<>();
        //读取之前价格表数据第一行
        List<String> oldHeadCells = allHeadCell.get(0);//select ...
        Integer weightCellIndex =  weightSectionUpdDto.getWeightSection().get(0).getIndex();
        for(int i=0; i<weightCellIndex; i++){
            newHeadCells.add(oldHeadCells.get(i));
        }
        for(int i=0; i<headCells.size(); i++){
            newHeadCells.add(headCells.get(i));
        }
        allHeadCell.remove(0);
        allHeadCell.add(0, newHeadCells);
        //更新保存新的表头
        Integer integer = baseMapper.updateData(weightSectionUpdDto.getPriceDataId(), allHeadCell);
        if(integer < 1)
            throw new AplException(CommonStatusCode.SAVE_FAIL,null);
        return newHeadCells;
    }

    /**
     * 合并利润
     * @param priceVal
     * @param zoneAndCountry
     * @param weightSectionDto
     * @param profitBoList
     * @return
     */
    @Override
    public Double priceMergeProfit(Double priceVal, List<String> zoneAndCountry, WeightSectionDto weightSectionDto, List<PriceExpProfitMergeBo> profitBoList){

        if(null==priceVal || priceVal.equals(0)){
            return  priceVal;
        }

        int countryStartIndex = 0;
        String zone = null;
        if(zoneAndCountry.get(0).matches("^\\d")){
            zone = zoneAndCountry.get(0);
            countryStartIndex = 1;
        }

        Double startWeight = weightSectionDto.getWeightStart();
        Double endWeight = weightSectionDto.getWeightEnd();
        Double priceValResult = priceVal;
        PriceExpProfitMergeBo findProfitMergeBo = null;

        //查找利润
        boolean isFind = false;
        for (PriceExpProfitMergeBo profitMergeBo : profitBoList) {

            if(profitMergeBo.getEndWeight()>0) {
                if (endWeight <= profitMergeBo.getStartWeight()  ||  startWeight>=profitMergeBo.getEndWeight())
                    continue;

                if (profitMergeBo.getStartWeight()>startWeight)
                    break;
            }

            if (null != zone && zone.length() > 0) {
                if (null == profitMergeBo.getZoneNumList() || profitMergeBo.getZoneNumList().size() == 0 || profitMergeBo.getZoneNumList().get(0).equals("") || profitMergeBo.getZoneNumList().contains(zone))
                    isFind = true;
            }

            if (!isFind) {
                if (profitMergeBo.getCountryCodeList().size() == 0 || profitMergeBo.getCountryCodeList().get(0).equals(""))
                    isFind = true;
                else {
                    for (int i = countryStartIndex; i < zoneAndCountry.size(); i++) {
                        String countryCode = zoneAndCountry.get(i);
                        if (profitMergeBo.getCountryCodeList().contains(countryCode)) {
                            isFind = true;
                            break;
                        }
                    }
                }
            }

            if (isFind) {
                findProfitMergeBo = profitMergeBo;
                break;
            }
        }

        if(null!=findProfitMergeBo){
            if(null == findProfitMergeBo.getFirstWeightProfit())
                findProfitMergeBo.setFirstWeightProfit(0.0);
            if(null == findProfitMergeBo.getProportionProfit())
                findProfitMergeBo.setProportionProfit(0.0);
            if(null == findProfitMergeBo.getUnitWeightProfit())
                findProfitMergeBo.setUnitWeightProfit(0.0);
            //找到利润，相加
            if(weightSectionDto.getChargingWay()==1){
                //首重加
                priceValResult += findProfitMergeBo.getFirstWeightProfit();

            } else if(weightSectionDto.getChargingWay()==2 || weightSectionDto.getChargingWay()==3 || weightSectionDto.getChargingWay()==4){
                // 单位重加
                priceValResult += findProfitMergeBo.getUnitWeightProfit();
            }

            if(findProfitMergeBo.getProportionProfit()>0) {
                //比例加
                priceValResult *= findProfitMergeBo.getProportionProfit();
            }
        }

        return priceValResult;
    }

}
