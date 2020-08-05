package com.apl.lms.price.exp.manage.service.impl;

import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.price.exp.manage.dao.ExpListMapper;
import com.apl.lms.price.exp.manage.pojo.dto.ExpListDto;
import com.apl.lms.price.exp.manage.pojo.dto.ExpListKeyDto;
import com.apl.lms.price.exp.manage.pojo.po.ExpListPo;
import com.apl.lms.price.exp.manage.pojo.vo.ExpListVo;
import com.apl.lms.price.exp.manage.service.ExpListService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
@Service
@Slf4j
public class ExpListServiceImpl extends ServiceImpl<ExpListMapper, ExpListPo> implements ExpListService {

    enum ExpListServiceCode {

        ;

        private String code;
        private String msg;

        ExpListServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 分页查询
     * @param pageDto
     * @param expListKeyDto
     * @return
     */
    @Override
    public ResultUtil<Page<ExpListVo>> getExpList(PageDto pageDto, ExpListKeyDto expListKeyDto) {

        Page<ExpListVo> page = new Page();
        page.setCurrent(pageDto.getPageIndex());
        page.setSize(pageDto.getPageSize());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        List<ExpListVo> expListVoList = baseMapper.getExpList(page, timestamp, expListKeyDto);

        page.setRecords(expListVoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, page);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delExpList(Long id) {

        Integer integer = baseMapper.delById(id);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.DEL_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);
    }

    /**
     * 修改
     * @param expListDto
     * @return
     */
    @Override
    public ResultUtil<Boolean> updExpList(ExpListDto expListDto) {

        ExpListPo expListPo = new ExpListPo();
        BeanUtils.copyProperties(expListDto, expListPo);

        Integer integer = baseMapper.updExpList(expListPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, false);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 添加
     * @param expListDto
     * @return
     */
    @Override
    public ResultUtil<Long> insExpList(ExpListDto expListDto) {

        ExpListPo expListPo = new ExpListPo();
        BeanUtils.copyProperties(expListDto, expListPo);
        expListPo.setId(SnowflakeIdWorker.generateId());

        Integer integer = baseMapper.insertExpList(expListPo);
        if(integer < 1){
            return ResultUtil.APPRESULT(CommonStatusCode.SAVE_FAIL, null);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, expListPo.getId());
    }
}
