package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Pojo.WebUsers;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Service.Normal.OrganizationService;
import com.topsci.qh.webmanagement.Service.Normal.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lzw on 2017/4/18.
 */
@Controller
@RequestMapping("/stv2")
public class StatisticV2Controller extends BasicController{
    @Resource
    private OrganizationService organizationService;
    @Resource
    private UserService userService;

    @RequestMapping("/list.do")
    public ModelAndView list(HttpServletRequest request)
    {
        ModelAndView view = new ModelAndView("/Normal/Stv2/list");
        String path = request.getParameter("path");
        String userid = sessionManager.getSessionAttr(request, Constants.USERID);
        String userdis;
        if(Constants.INIT_ID.equals(userid))
        {
            userdis = "630000000146";
        }
        else {
            WebUsers users = userService.getUser(userid);
            userdis = users.getOrganizationId();
        }
        String discode = organizationService.getUserOrganDis(userdis);
        if(path.contains("?"))
        {
            path+="&code="+discode;
        }
        else
        {
            path+="?code="+discode;
        }
        view.addObject("path",path);
        view.addObject("discode",discode);
        return view;
    }
}
