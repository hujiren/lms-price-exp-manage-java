package com.apl.lms.price.exp.manage.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author hjr start
 * @Classname ExportService
 * @Date 2020/10/15 16:13
 */
public interface ExportPriceService {

    void exportExpPrice(HttpServletResponse response, List<Long> ids) throws Exception;
}
