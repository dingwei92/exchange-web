package com.topsci.qh.webmanagement.Business.System;

import com.topsci.qh.webmanagement.Pojo.WebFuncsList;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Service.Normal.FunctionService;
import com.topsci.qh.webmanagement.Tools.Config;
import com.topsci.qh.webmanagement.Tools.SessionManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lzw.
 * 16-6-20
 */
@Controller
@RequestMapping("/system")
public class IndexController extends BasicController{

    @Resource
    private SessionManager sessionManager;
    @Resource
    private Config config;
    @Resource
    private FunctionService functionService;

    //跳转到主页
    @RequestMapping("/index.do")
    public ModelAndView goIndex(HttpServletRequest request,HttpServletResponse response)
    {
        //获取用户姓名显示在首页
        String uname = sessionManager.getSessionAttr(request,Constants.USERNAME);
        boolean showCatalog = Boolean.valueOf(config.getShowCatalog());

        ModelAndView model = new ModelAndView("index");
        if(StringUtils.isEmpty(uname))
        {
            return new ModelAndView(new RedirectView("login.do",true,false,false));
        }
        String userid = sessionManager.getSessionAttr(request,Constants.USERID);
        List<WebFuncsList> funcs;
        if(Constants.INIT_USERNAME.equals(sessionManager.getSessionAttr(request,Constants.LOGINNAME)))
        {
            funcs = functionService.getAllFuncList(null);
        }
        else
        {
            funcs = functionService.getAllFuncList(userid);
        }
        model.addObject("funcs",funcs);
        model.addObject("uname",uname);
        model.addObject("showCatalog",showCatalog);
        return model;
    }

    @RequestMapping("/blank.do")
    public ModelAndView goBlank(HttpServletRequest request)
    {
        ModelAndView view = new ModelAndView("/System/blank");
        return view;
    }

}
