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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        List<PriceZoneDataListVo> newZoneDataList = sortForList(priceZoneDataListVo);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, newZoneDataList);
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
        List oneZoneDataList;

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
        Map<String, PriceZoneDataListVo> temporaryZoneMap;
        List<PriceZoneDataListVo> zoneDataListVo;
        List<PriceZoneDataListVo> zoneDataFromMapList;
        List<PriceZoneDataListVo> zoneDataListVos = null;
        //遍历map,每一个map代表一个分区id对应的所有分区数据
        for (Map.Entry<Long, List<PriceZoneDataListVo>> longListEntry : zoneTabMaps.entrySet()) {

            zoneDataFromMapList = longListEntry.getValue();//存储map的value的list集合
            zoneDataListVo = new ArrayList<>();//中转对象
            temporaryZoneMap = new HashMap<>();//用于做数据中转的临时对象
            String zoneNum;

            //将分区号相同的对象合并到新的map中,并将国家的中英文名用逗号拼接组装成新的属性
            for (PriceZoneDataListVo vo : zoneDataFromMapList) {
                zoneNum = vo.getZoneNum();
                if(temporaryZoneMap.containsKey(zoneNum)){
                    PriceZoneDataListVo zoneData = temporaryZoneMap.get(zoneNum);
                    zoneData.setCountryNameCn(zoneData.getCountryNameCn() + "," + vo.getCountryNameCn());
                    zoneData.setCountryNameEn(zoneData.getCountryNameEn() + "," + vo.getCountryNameEn());
                    temporaryZoneMap.put(zoneNum, zoneData);
                }else{
                    temporaryZoneMap.put(zoneNum, vo);
                }
            }

            //将中转map中的数据按分区号进行升序排序,得到新的List
            for (Map.Entry<String, PriceZoneDataListVo> entry : temporaryZoneMap.entrySet()) {
                zoneDataListVo.add(entry.getValue());
                zoneDataListVos = sortForList(zoneDataListVo);
            }
            //最后将主map的分区id对应的list数据集替换为全新组装好的list数据集
            longListEntry.setValue(zoneDataListVos);
        }

        return zoneTabMaps;
    }

    public List<PriceZoneDataListVo> sortForList(List<PriceZoneDataListVo> list){
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//判断是否是数字
        List<PriceZoneDataListVo> finalNumList = new ArrayList<>();
        List<PriceZoneDataListVo> finalStrList = new ArrayList<>();
        for (PriceZoneDataListVo zoneDataListVo : list) {
            Matcher isNum = pattern.matcher(zoneDataListVo.getZoneNum());
            if(isNum.matches()){
                finalNumList.add(zoneDataListVo);
            }
            else
                finalStrList.add(zoneDataListVo);
        }

        Integer[] zoneNumArrays = new Integer[finalNumList.size()];
        for (int i = 0; i < finalNumList.size(); i++) {
            zoneNumArrays[i] = Integer.parseInt(finalNumList.get(i).getZoneNum());
        }
        Arrays.sort(zoneNumArrays);
        List<PriceZoneDataListVo> newZoneDataList = new ArrayList<>();

        for (int i = 0; i < zoneNumArrays.length; i++) {
            for (PriceZoneDataListVo zoneDataListVo : finalNumList) {
                if(zoneNumArrays[i].equals(Integer.parseInt(zoneDataListVo.getZoneNum()))){
                    newZoneDataList.add(zoneDataListVo);
                }
            }
        }

        Collections.sort(finalStrList, Comparator.comparing(PriceZoneDataListVo::getZoneNum));
        for (PriceZoneDataListVo zoneDataListVo : finalStrList) {
            newZoneDataList.add(zoneDataListVo);
        }
        return newZoneDataList;
    }
}
