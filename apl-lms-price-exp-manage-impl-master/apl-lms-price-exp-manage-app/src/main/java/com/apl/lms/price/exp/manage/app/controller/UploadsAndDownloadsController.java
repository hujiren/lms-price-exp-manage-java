package com.apl.lms.price.exp.manage.app.controller;

import com.apl.lib.utils.ResultUtil;
import com.apl.lms.price.exp.manage.service.UploadsAndDownLoadsService;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hjr start
 * @Classname UploadsAndDownloadsController
 * @Date 2020/11/19 15:40
 */
@RestController
@Slf4j
@RequestMapping("/excel-manage")
@Api(value = "Excel管理",tags = "Excel管理")
public class UploadsAndDownloadsController {

    @Autowired
    UploadsAndDownLoadsService uploadsAndDownLoadsService;

    @PostMapping(value = "/upload")
    @ApiOperation(value = "上传Excel", notes = "上传Excel")
    public ResultUtil<Boolean> uploadTemplateFile(MultipartFile file) throws IOException {
        return uploadsAndDownLoadsService.uploadTemplateFile(file);
    }

    @PostMapping(value = "/is-exists")
    @ApiOperation(value = "判断excel是否存在", notes = "判断excel是否存在")
    public ResultUtil<Boolean> excelIsExists() {
        return uploadsAndDownLoadsService.excelIsExists();
    }

    @GetMapping(value = "/download")
    @ApiOperation(value = "下载Excel模板", notes = "下载Excel模板")
    public ResultUtil<Boolean> downloadExcel(HttpServletResponse response) throws IOException {
        return uploadsAndDownLoadsService.downloadExcel(response);
    }
}
