public ResultUtil<Long> saveProfit2(PriceExpProfitPo priceExpProfitPo1) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(priceExpProfitPo1);
        PriceExpProfitPo priceExpProfitPo = objectMapper.readValue(json, PriceExpProfitPo.class);

        List<PriceExpProfitDto> emptyProfitList = new ArrayList<>();
        List<PriceExpProfitDto> increaseProfit = null;
        if(null == priceExpProfitPo.getIncreaseProfit()){
        priceExpProfitPo.setIncreaseProfit(emptyProfitList);
        }

        if(priceExpProfitPo1.getAddProfitWay().equals(1)){
        //上调的利润
        increaseProfit = priceExpProfitPo.getIncreaseProfit();
        if(increaseProfit.size() < 1)
        return ResultUtil.APPRESULT(PriceExpProfitServiceCode.HAVE_AT_LEAST_ONE_PROFIT.code, PriceExpProfitServiceCode.HAVE_AT_LEAST_ONE_PROFIT.msg, 0L);
        }

        //将添加利润方式更新到价格表
        PriceExpMainPo priceExpMainPo = new PriceExpMainPo();
        priceExpMainPo.setId(priceExpProfitPo.getId());
        priceExpMainPo.setAddProfitWay(priceExpProfitPo.getAddProfitWay());
        priceExpService.updatePriceExpMain(priceExpMainPo);

        // TODO: 2020/11/4 满足 addProfitWay=2 的条件时,算法还没有写, 先直接返回保存成功
        if(priceExpProfitPo1.getAddProfitWay().equals(0) || priceExpProfitPo1.getAddProfitWay().equals(2))
        ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, 0L);

        if(increaseProfit.size() >0){
        //遍历上调的利润
        for (PriceExpProfitDto priceExpSaleProfitDto : increaseProfit) {
        //将空属性设为默认值
        if(null != priceExpSaleProfitDto.getCountryCode()){
        priceExpSaleProfitDto.setCountryCode(priceExpSaleProfitDto.getCountryCode().toUpperCase());
        }else{
        priceExpSaleProfitDto.setCountryCode("");
        }

        if(null == priceExpSaleProfitDto.getZoneNum())
        priceExpSaleProfitDto.setZoneNum("");

        if(null == priceExpSaleProfitDto.getUnitWeightProfit())
        priceExpSaleProfitDto.setUnitWeightProfit(0.0);

        if(null == priceExpSaleProfitDto.getFirstWeightProfit())
        priceExpSaleProfitDto.setFirstWeightProfit(0.0);

        if(null == priceExpSaleProfitDto.getProportionProfit())
        priceExpSaleProfitDto.setProportionProfit(0.0);

        if(null == priceExpSaleProfitDto.getStartWeight())
        priceExpSaleProfitDto.setStartWeight(0.0);

        if(null == priceExpSaleProfitDto.getEndWeight())
        priceExpSaleProfitDto.setEndWeight(0.0);
        }
        }

        List<PriceExpProfitDto> quoteProfit = null;
        if(priceExpProfitPo.getId() > 0){
        //根据priceId找到引用价格id
        ExpPriceInfoBo innerOrgIdAndPriceDatId = priceExpService.getPriceInfo(priceExpProfitPo.getId());
        if(innerOrgIdAndPriceDatId.getQuotePriceId() > 0) {
        //如果引用价格id大于0, 则根据引用价格id获取 <最终利润>
        quoteProfit = getQuoteProfit(innerOrgIdAndPriceDatId.getQuotePriceId());

        }
        }

        if(null == quoteProfit)//表示没有引用价格id 或没有引用价格对应的利润
        quoteProfit = emptyProfitList;//设置为空
        if(null != quoteProfit && quoteProfit.size() > 0){
        String str = JSON.toJSONString(quoteProfit);
        quoteProfit = JSONObject.parseArray(str, PriceExpProfitDto.class);
        }
        //合并
        Long customerGroupId =0l;//
        List<PriceExpProfitDto> finalProfit = mergeProfit(increaseProfit, quoteProfit, customerGroupId);

        Integer flag = 0;
        Long checkId = baseMapper.exists(priceExpProfitPo.getId());

        priceExpProfitPo.setIncreaseProfit(increaseProfit);
        priceExpProfitPo.setFinalProfit(finalProfit);
        if(null!=checkId && checkId>0){
        //如果有相同id则更新
        flag = baseMapper.updProfit(priceExpProfitPo);
        }
        else {
        //如果没有相同id则是添加 id采用价格表id
        flag = baseMapper.addProfit(priceExpProfitPo);
        }

        if(flag.equals(0)){
        throw new AplException(CommonStatusCode.SYSTEM_FAIL , null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SYSTEM_SUCCESS, priceExpProfitPo.getId());
        }
