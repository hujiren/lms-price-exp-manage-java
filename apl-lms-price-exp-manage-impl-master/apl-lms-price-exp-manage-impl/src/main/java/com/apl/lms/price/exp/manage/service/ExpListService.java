package com.apl.lms.price.exp.manage.service;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.pojo.dto.ExpListDto;
import com.apl.lms.price.exp.manage.pojo.dto.ExpListKeyDto;
import com.apl.lms.price.exp.manage.pojo.po.ExpListPo;
import com.apl.lms.price.exp.manage.pojo.vo.ExpListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hjr start
 * @date 2020/8/5 - 10:28
 */
public interface ExpListService extends IService<ExpListPo> {

    /**
     * 分页查询快递价格列表
     * @param pageDto
     * @param expListKeyDto
     * @return
     */
    ResultUtil<Page<ExpListVo>> getExpList(PageDto pageDto, ExpListKeyDto expListKeyDto);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultUtil<Boolean> delExpList(Long id);

    /**
     * 修改
     * @param expListDto
     * @return
     */
    ResultUtil<Boolean> updExpList(ExpListDto expListDto);

    /**
     * 添加
     * @param expListDto
     * @return
     */
    ResultUtil<Long> insExpList(ExpListDto expListDto);
}
