package com.topsci.qh.webmanagement.Business.System;

import com.topsci.qh.webmanagement.Resources.BasicController;

import com.topsci.qh.webmanagement.Tools.Config;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lzw.
 * 16-7-7
 */
@Controller
public class FirstPageController extends BasicController {

    @Resource
    private Config config;

    @RequestMapping("/")
    protected ModelAndView index(HttpServletRequest request)
    {
        //配置首页模块链接--删除了
        ModelAndView model = new ModelAndView(new RedirectView("system/index.do"));
        return model;
    }
}
