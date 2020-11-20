package com.apl.lms.price.exp.manage.service;

import com.apl.lib.utils.ResultUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author hjr start
 * @Classname ExportService
 * @Date 2020/10/15 16:13
 */
public interface UploadsAndDownLoadsService {

    /**
     * 下载价格表Excel
     * @param response
     * @param ids
     * @param customerGroupId
     * @throws Exception
     */
    void exportExpPrice(HttpServletResponse response, List<Long> ids, Long customerGroupId) throws Exception;

    /**
     * 上传Excel模板
     * @param file
     * @return
     * @throws IOException
     */
    ResultUtil<Boolean> uploadTemplateFile(MultipartFile file) throws IOException;

    /**
     * 判断Excel模板是否存在
     * @return
     */
    ResultUtil<Boolean> excelIsExists();

    /**
     * 下载模板
     * @return
     */
    ResultUtil<Boolean> downloadExcel(HttpServletResponse response) throws IOException;
}
