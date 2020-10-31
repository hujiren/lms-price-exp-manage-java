package com.apl.lms.price.exp.manage.service.impl;

import com.apl.cache.AplCacheUtil;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.join.JoinBase;
import com.apl.lib.join.JoinFieldInfo;
import com.apl.lib.join.JoinUtil;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.common.lib.cache.JoinCountry;
import com.apl.lms.common.lib.feign.LmsCommonFeign;
import com.apl.lms.price.exp.manage.mapper.PriceZoneDataMapper;
import com.apl.lms.price.exp.manage.service.PriceZoneDataService;
import com.apl.lms.price.exp.pojo.vo.PriceZoneDataListVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author hjr start
 * @Classname PriceZoneDataServiceImpl
 * @Date 2020/8/31 15:16
 */
@Service
@Slf4j
public class PriceZoneDataServiceImpl extends ServiceImpl<PriceZoneDataMapper, PriceZoneDataListVo> implements PriceZoneDataService {

    JoinFieldInfo joinCountryFieldInfo = null;

    @Autowired
    AplCacheUtil aplCacheUtil;

    @Autowired
    LmsCommonFeign lmsCommonFeign;

    enum PriceZoneDataServiceCode {
        ID_DOES_NOT_EXITS("ID_DOES_NOT_EXITS", "id不存在");

        private String code;
        private String msg;

        PriceZoneDataServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 获取列表
     * @param id
     * @return
     */
    @Override
    public ResultUtil<List<PriceZoneDataListVo>> getList(Long id) throws Exception {
        List<PriceZoneDataListVo> priceZoneDataListVo = baseMapper.getList(id);

        if(priceZoneDataListVo.size() == 0){
            return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceZoneDataListVo);
        }

        List<JoinBase> joinTabs = new ArrayList<>();
        JoinCountry joinCountry = new JoinCountry(1, lmsCommonFeign, aplCacheUtil);

        if(null!=joinCountryFieldInfo) {
            //已经缓存国家反射字段
            joinCountry.setJoinFieldInfo(joinCountryFieldInfo);
        }
        else{
            joinCountry.addField("countryCode",  String.class, "nameCn", "countryNameCn",String.class);
            joinCountry.addField("countryCode", String.class, "nameEn", "countryNameEn",String.class);

            joinCountryFieldInfo = joinCountry.getJoinFieldInfo();
        }
        joinTabs.add(joinCountry);
        JoinUtil.join(priceZoneDataListVo, joinTabs);

        zoneNumSort(priceZoneDataListVo);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, priceZoneDataListVo);
    }

    @Override
    public ResultUtil<Boolean> deleteBatch(List<Long> ids) {
        Integer resInteger = baseMapper.deleteByZoneId(ids);
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, resInteger);
    }

    /**
     * 根据分区表id批量删除
     * @param ids
     * @return
     */
    @Override
    public Integer delBatchByZoneId(List<Long> ids) {
        return baseMapper.delBatchByZoneId(ids);
    }

    /**
     * 组装分区数据
     */
    @Override
    public Map<Long, List<PriceZoneDataListVo>> assemblingZoneData(List<Long> zoneIds) throws Exception {
        //分区Id去重
        HashSet<Long> idSet = new HashSet<>();
        for (Long zoneId : zoneIds) {
            idSet.add(zoneId);
        }
        //根据zoneId获取所有数据
        List<PriceZoneDataListVo> allZoneDataList = baseMapper.getListByZoneIds(idSet);

        //将国家中英文名称通过缓存组装好
        List<JoinBase> joinTabs = new ArrayList<>();
        JoinCountry joinCountry = new JoinCountry(1, lmsCommonFeign, aplCacheUtil);

        if(null!=joinCountryFieldInfo) {
            //已经缓存国家反射字段
            joinCountry.setJoinFieldInfo(joinCountryFieldInfo);
        }
        else{
            joinCountry.addField("countryCode",  String.class, "nameCn", "countryNameCn",String.class);
            joinCountry.addField("countryCode", String.class, "nameEn", "countryNameEn",String.class);

            joinCountryFieldInfo = joinCountry.getJoinFieldInfo();
        }
        joinTabs.add(joinCountry);
        JoinUtil.join(allZoneDataList, joinTabs);

        Map<Long, List<PriceZoneDataListVo>> zoneTabMaps = new HashMap<>();
        List<PriceZoneDataListVo> oneZoneDataList;

        //遍历所有数据
        for (PriceZoneDataListVo priceZoneDataVo : allZoneDataList) {

            //根据分区id将数据进行归类,放入map中
            oneZoneDataList = zoneTabMaps.get(priceZoneDataVo.getZoneId());
            if (null == oneZoneDataList) {
                oneZoneDataList = new ArrayList();
                zoneTabMaps.put(priceZoneDataVo.getZoneId(), oneZoneDataList);
            }
            oneZoneDataList.add(priceZoneDataVo);
        }
        List<PriceZoneDataListVo> sourceZoneDataList;
        List<PriceZoneDataListVo> newZoneDataList;

        StringBuilder sbNameCn = new StringBuilder();
        StringBuilder sbNameEn = new StringBuilder();

        //遍历map,每一个map代表一个分区id对应的所有分区数据
        for (Map.Entry<Long, List<PriceZoneDataListVo>> zoneTabEntry : zoneTabMaps.entrySet()) {

            sourceZoneDataList = zoneTabEntry.getValue();//存储map的value的list集合
            zoneNumSort(sourceZoneDataList);

            newZoneDataList = new ArrayList<>();
            //将分区号相同的对象合并到新的map中,并将国家的中英文名用逗号拼接组装成新的属性
            String zoneNum ="";
            String zoneNum0 = "";
            for (PriceZoneDataListVo vo : sourceZoneDataList) {
                zoneNum = vo.getZoneNum();
                if(!zoneNum0.equals(zoneNum))  {
                    if(sbNameCn.length()>0){
                        PriceZoneDataListVo  priceZoneDataListVo = new  PriceZoneDataListVo();
                        newZoneDataList.add(priceZoneDataListVo);
                        priceZoneDataListVo.setZoneNum(zoneNum0);
                        priceZoneDataListVo.setCountryNameCn(sbNameCn.toString());
                        priceZoneDataListVo.setCountryNameEn(sbNameEn.toString());
                    }
                    sbNameCn.setLength(0);
                    sbNameEn.setLength(0);
                }

                if(sbNameCn.length()>0){
                    sbNameCn.append(", ");
                    sbNameEn.append(", ");
                }
                sbNameCn.append(vo.getCountryNameCn());
                sbNameEn.append(vo.getCountryNameEn());

                zoneNum0 = zoneNum;
            }

            if(sbNameCn.length()>0){
                PriceZoneDataListVo  priceZoneDataListVo = new  PriceZoneDataListVo();
                newZoneDataList.add(priceZoneDataListVo);
                priceZoneDataListVo.setZoneNum(zoneNum);
                priceZoneDataListVo.setCountryNameCn(sbNameCn.toString());
                priceZoneDataListVo.setCountryNameEn(sbNameEn.toString());
            }

            zoneTabEntry.setValue(newZoneDataList);

            sbNameCn.setLength(0);
            sbNameEn.setLength(0);
        }

        return zoneTabMaps;
    }


    String numPattern = "^-?\\d+(\\.\\d+)?$";

    //分区号排序
    void zoneNumSort(List<PriceZoneDataListVo> list) {

        /*for (PriceZoneDataListVo row : list) {
            if(row.getZoneNum().length()<2 && row.getZoneNum().matches(numPattern)){
                row.setZoneNum("0"+row.getZoneNum());
            }
        }*/

        //list.sort(Comparator.comparing(PriceZoneDataListVo::getZoneNum));

        Collections.sort(list, new Comparator<PriceZoneDataListVo>() {

            @Override
            public int compare(PriceZoneDataListVo o1, PriceZoneDataListVo o2) {

                if(o1.getZoneNum().matches(numPattern) && o2.getZoneNum().matches(numPattern)) {
                    if (o1.getZoneNum().length() < o2.getZoneNum().length())
                        return -1;
                    else if (o1.getZoneNum().length() > o2.getZoneNum().length())
                        return 1;
                }

                return o1.getZoneNum().compareTo(o2.getZoneNum());
            }
        });

        /*for (PriceZoneDataListVo row : list) {
            if(row.getZoneNum().indexOf("0")==0)  {
                row.setZoneNum(row.getZoneNum().substring(1));
            }
        }*/
    }

}
