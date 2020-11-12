public PriceExpDataObjVo getPriceExpDataInfoByPriceId2(Long id) throws Exception {
        ExpPriceInfoBo innerOrgIdAndPriceDatId = getPriceInfo(id);
        if(null == innerOrgIdAndPriceDatId){
        throw new AplException(ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.code,
        ExpListServiceCode.THERE_IS_NO_CORRESPONDING_DATA.msg,null);
        }
        DecimalFormat df = new DecimalFormat("#.0");
        //获取价格表数据返回对象
        PriceExpDataVo priceExpDataInfo = priceExpDataService.getPriceExpDataInfoByPriceId(innerOrgIdAndPriceDatId.getPriceDataId());
        //价格表数据
        List<List<String>> priceData = priceExpDataInfo.getPriceData();
        Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
        List<List<Object>> priceDataVo = new ArrayList<>();
        //如果没有引用价格, 则找自己的成本
        if(innerOrgIdAndPriceDatId.getQuotePriceId() < 1 || null == innerOrgIdAndPriceDatId.getQuotePriceId()){
        PriceExpDataObjVo expDataObjVo = new PriceExpDataObjVo();
        for (List<String> priceDatum : priceData) {
        List<Object> priceDataObj = new ArrayList<>();
        for (String str : priceDatum) {
        Matcher isNum = pattern.matcher(str);
        if(isNum.matches()) {
        Double var1 = Double.parseDouble(str);
        String format = df.format(var1);
        Double element = Double.parseDouble(format);
        priceDataObj.add(element);
        }else {
        priceDataObj.add(str);
        }
        }
        priceDataVo.add(priceDataObj);
        }
        expDataObjVo.setPriceData(priceDataVo);
        expDataObjVo.setPriceDataId(priceExpDataInfo.getPriceDataId());
        return expDataObjVo;
        }

        //获取增加的利润
        PriceExpProfitPo profit = priceExpProfitService.getProfit(id);
        if(null == profit || profit.getIncreaseProfit().size() < 1){
        throw new AplException(ExpListServiceCode.THERE_IS_NO_BOUND_INCOME_STATEMENT.code,
        ExpListServiceCode.THERE_IS_NO_BOUND_INCOME_STATEMENT.msg, null);
        }
        List<PriceExpProfitDto> finalProfit = profit.getIncreaseProfit();

        String s = JSONObject.toJSONString(finalProfit);
        List<PriceExpProfitDto> priceExpProfitDtos = JSON.parseArray(s, PriceExpProfitDto.class);

        //将finalProfit增加的利润拆分并重新组装
        List<PriceExpProfitMergeBo> finalProfitBoList = new ArrayList<>();
        for (PriceExpProfitDto priceExpProfitDto : priceExpProfitDtos) {
        PriceExpProfitMergeBo priceExpProfitMergeBo = new PriceExpProfitMergeBo();
        BeanUtil.copyProperties(priceExpProfitDto, priceExpProfitMergeBo);

        //将国家简码组装成List
        String[] countryCodeArray = priceExpProfitMergeBo.getCountryCode().split(",");
        List<String> countryCodeList = strArrayToList(countryCodeArray);
        priceExpProfitMergeBo.setCountryCodeList( countryCodeList);

        //将分区号组装成List
        String[] zoneArray = priceExpProfitMergeBo.getZoneNum().split(",");
        List<String> zoneArrayList = strArrayToList(zoneArray);
        priceExpProfitMergeBo.setZoneNumList( zoneArrayList);

        //如果起始重,截止重为空, 则设为默认值 0.0 ~ 10000.0
        if(null == priceExpProfitMergeBo.getStartWeight())
        priceExpProfitMergeBo.setStartWeight(0.0);
        if(null == priceExpProfitMergeBo.getEndWeight() || priceExpProfitMergeBo.getEndWeight().equals(0.0))
        priceExpProfitMergeBo.setEndWeight(10000.0);

        finalProfitBoList.add(priceExpProfitMergeBo);
        }

        List<List<Object>> priceData2 = new ArrayList<>();

        //获取数据轴
        ResultUtil<PriceExpAxisVo> axisInfoVo = priceExpAxisService.getAxisInfoById(id);
        //轴: 重量段
        List<WeightSectionDto> weightSections = axisInfoVo.getData().getWeightSection();
        //轴: 分区国家
        List<List<String>> zoneAndCountrys  = axisInfoVo.getData().getZoneCountry();
        //价格表格式
        int priceFormat  = innerOrgIdAndPriceDatId.getPriceFormat();

        //起始列下标
        int startColIndex = 0;
        if(priceFormat == 1)
        startColIndex = weightSections.get(0).getIndex();
        else
        startColIndex = 1;

        List<String> zoneAndCountry = null;
        WeightSectionDto weightSectionDto = null;
        List<String> strList = priceData.get(0);
        List<Object> objectList = new ArrayList<>();
        for (String s1 : strList) {
        objectList.add(s1);
        }
        priceData2.add(objectList);

        for (int rowIndex=0; rowIndex < priceData.size() - 1; rowIndex++) {

        if(priceFormat == 1){
        if(rowIndex < zoneAndCountrys.size())
        zoneAndCountry = zoneAndCountrys.get(rowIndex);
        }
        else if(priceFormat == 2){
        if(rowIndex < weightSections.size())
        weightSectionDto = weightSections.get(rowIndex);
        }

        //除首行以外的行
        List<String> cells = priceData.get(rowIndex + 1);
        List<Object> cells2 = new ArrayList<>();

        for (int colIndex = 0; colIndex < startColIndex; colIndex++) {
        Object priceStr = cells.get(colIndex);
        cells2.add(priceStr);
        }
        //代表着重量段中的index属性
        for (int colIndex = startColIndex; colIndex < cells.size(); colIndex++) {

        if(priceFormat==1){
        if(colIndex - 1 < weightSections.size()){
        if(colIndex == 0)
        weightSectionDto = weightSections.get(colIndex);
        else
        weightSectionDto = weightSections.get(colIndex - 1);
        }
        }
        else  if(priceFormat==2) {
        if (colIndex - 1 < zoneAndCountrys.size()) {
        if (colIndex == 0)
        zoneAndCountry = zoneAndCountrys.get(colIndex);
        else
        zoneAndCountry = zoneAndCountrys.get(colIndex - 1);
        }
        }

        String priceStr = cells.get(colIndex);
        if(null!=priceStr && priceStr.length() > 0) {
        Double priceVal = Double.parseDouble(priceStr);
        priceVal = priceExpDataService.priceMergeProfit(priceVal, zoneAndCountry, weightSectionDto, finalProfitBoList);
        String format = df.format(priceVal);
        Double format1 = Double.parseDouble(format);
        priceData.get(rowIndex + 1).set(colIndex, format);
        cells2.add(format1);
        }
        }
        priceData2.add(cells2);
        }
        PriceExpDataObjVo objVo = new PriceExpDataObjVo();
        objVo.setPriceDataId(priceExpDataInfo.getPriceDataId());
        objVo.setPriceData(priceData2);
        return objVo;
        }