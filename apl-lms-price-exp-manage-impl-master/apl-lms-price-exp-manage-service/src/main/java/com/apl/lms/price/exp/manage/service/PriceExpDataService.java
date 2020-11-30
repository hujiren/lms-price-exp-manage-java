package com.apl.lms.price.exp.manage.service;

import com.apl.lms.price.exp.pojo.bo.ExpPriceInfoBo;
import com.apl.lms.price.exp.pojo.dto.PriceExpAddDto;
import com.apl.lms.price.exp.pojo.dto.WeightSectionUpdDto;
import com.apl.lms.price.exp.pojo.po.PriceExpDataPo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataObjVo;
import com.apl.lms.price.exp.pojo.vo.PriceExpDataVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

/**
 * @author hjr start
 * @Classname PriceExpDataService
 * @Date 2020/8/19 17:30
 */
public interface PriceExpDataService extends IService<PriceExpDataPo> {

    /**
     * 为价格表数据添加利润
     * @param expPriceInfoBo
     * @param priceId
     * @param isSaleProfit
     * @param customerGroupId
     * @return
     */
    PriceExpDataObjVo getPriceExpData(ExpPriceInfoBo expPriceInfoBo, Long priceId, Boolean isSaleProfit, Long customerGroupId, boolean isExport) throws Exception;

    /**
     * 根据价格表id获取主数据信息
     * @param id
     * @return
     */
    PriceExpDataVo getPriceExpDataInfoByPriceId(Long id);

    /**
     * 保存价格表数据
     * @param priceDataId
     * @return
     */
    Boolean addPriceExpData(Long priceDataId, PriceExpAddDto priceExpAddDto);

    /**
     * 更新
     * @param priceExpDataPo
     * @return
     */
    Boolean updById(PriceExpDataPo priceExpDataPo) throws IOException;

    /**
     * 批量删除
     * @param priceDataIds
     * @return
     */
    Integer delBatch(String priceDataIds) throws IOException;

    /**
     * 更新表头
     * @param weightSectionUpdDto
     * @return
     */
    List<String> updHeadCells(WeightSectionUpdDto weightSectionUpdDto,List<String> headCells) throws IOException;

}
