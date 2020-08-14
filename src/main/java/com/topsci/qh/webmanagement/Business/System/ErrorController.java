package com.topsci.qh.webmanagement.Business.System;

import com.topsci.qh.webmanagement.Resources.BasicController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lzw.
 * 16-6-21
 */
@Controller
@RequestMapping("/system")
public class ErrorController extends BasicController {

    @RequestMapping("/error.do")
    public String goError()
    {
        return "/System/error";
    }

    @RequestMapping("/accessdeny.do")
    public ModelAndView goDeny(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("/System/accessdeny");
        view.addObject("url",request.getAttribute("originurl"));
        return view;
    }
}
