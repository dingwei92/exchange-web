package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Pojo.ErrorMsg7Day;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Service.Normal.ErrorMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("/datastatistic")
@Controller
public class ErrorMsgController extends BasicController {

    @Resource
    private ErrorMsgService errorMsgService;

    @RequestMapping("/errmsglist.do")
    public ModelAndView list(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView view = new ModelAndView("/Normal/ErrorMsg/list");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("errmsglist.do");
        Map<String,Object> result = errorMsgService.getData(pageInfo);
        List<ErrorMsg7Day> list = (List<ErrorMsg7Day>)result.get("data");
        List<String> dates = (List<String>)result.get("date");
        view.addObject("list",list);
        view.addObject("dates",dates);
        view.addObject("pageinfo",pageInfo);
        view.addObject("title","错误消息");
        return view;
    }

    @RequestMapping("/errmsgdetail.do")
    public ModelAndView detail(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView view = new ModelAndView("/Normal/ErrorMsg/detail");
        pageInfo = getPageInfo(request,pageInfo);
        String msgtype = request.getParameter("msgtype");
        String date = request.getParameter("date");
        pageInfo.setUrl("errmsgdetail.do?msgtype="+msgtype+"&date="+date);
        List<List<Object>> data = errorMsgService.getDetail(msgtype,date,pageInfo);
        view.addObject("data",data);
        view.addObject("title",msgtype+"错误消息");
        view.addObject("pageinfo",pageInfo);
        return view;
    }
}
