package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.mapper.SpecialCommodityMapper;
import com.apl.lms.price.exp.manage.service.SpecialCommodityService;
import com.apl.lms.price.exp.pojo.po.SpecialCommodityPo;
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
public class SpecialCommodityServiceImpl extends ServiceImpl<SpecialCommodityMapper, SpecialCommodityPo> implements SpecialCommodityService {

    enum SpecialCommodityServiceCode {
        ID_DOES_NOT_EXITS("ID_DOES_NOT_EXITS", "id不存在"),
        THE_DATA_ALREADY_EXISTS("THE_DATA_ALREADY_EXISTS", "该数据已经存在:");
        ;

        private String code;
        private String msg;

        SpecialCommodityServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 分页查找
     * @return
     */
    @Override
    public List<SpecialCommodityPo> getList() {

        List<SpecialCommodityPo> specialCommodityVoList = baseMapper.getList();

        return specialCommodityVoList;
    }

    /**
     * 根据id删除特殊物品
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delSpecialCommodity(Long id) {
        Integer integer = baseMapper.deleteById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL.code, SpecialCommodityServiceCode.ID_DOES_NOT_EXITS.msg, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 批量添加特殊物品
     * @param specialCommodityPoList
     * @return
     */
    @Override
    public ResultUtil<Integer> addSpecialCommodity(List<SpecialCommodityPo> specialCommodityPoList) {

        List<SpecialCommodityPo> resultList = getList();

            for (SpecialCommodityPo specialCommodityPo : specialCommodityPoList) {
                if(null != resultList && resultList.size() > 0) {
                    for (SpecialCommodityPo commodityPo : resultList) {
                        if (specialCommodityPo.getCode().equals(commodityPo.getCode())) {
                            return ResultUtil.APPRESULT(SpecialCommodityServiceCode.THE_DATA_ALREADY_EXISTS.code,
                                    SpecialCommodityServiceCode.THE_DATA_ALREADY_EXISTS.msg + specialCommodityPo.getCode().toString(), null);
                        }
                    }
                }
            specialCommodityPo.setId(SnowflakeIdWorker.generateId());
            }

        Integer integer = baseMapper.addSpecialCommodity(specialCommodityPoList);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, integer);
    }

}
