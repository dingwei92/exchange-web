package com.topsci.qh.webmanagement.Resources;

import com.topsci.qh.webmanagement.Service.Normal.UserService;
import com.topsci.qh.webmanagement.Tools.MD5Tool;
import com.topsci.qh.webmanagement.Tools.SessionManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lzw.
 * 16-6-21
 */

public class BasicController {
    private Logger log = LoggerFactory.getLogger(BasicController.class);

    @Resource
    protected MD5Tool md5Tool;
    @Resource
    protected SessionManager sessionManager;
    protected SimpleDateFormat StardTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 添加异常处理函数，使用日志记录异常并将页面重定向到错误页面
     * @param ex
     * @param request
     * @param response
     */
    @ExceptionHandler(Exception.class)
    protected void exceptionHandler(Exception ex,HttpServletRequest request,HttpServletResponse response)
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintWriter(buf, true));
        String  msg = buf.toString();
        try {
            buf.close();
            response.sendRedirect(request.getContextPath()+"/system/error.do");
        }
        catch (Exception e)
        {
            log.error(e.getCause().getMessage());
        }
        log.error("{}",msg);
    }

    protected PageInfo getPageInfo(HttpServletRequest request,PageInfo pageinfo)
    {
        if(pageinfo == null)
        {
            pageinfo = new PageInfo();
        }
        pageinfo.setUrl("list.do");
        String page = request.getParameter(Constants.CURRENT_PAGE);
        if(StringUtils.isNotEmpty(page))
        {
            int current = Integer.parseInt(page);
            pageinfo.setCurrentPage(current);
        }
        return pageinfo;
    }
}
