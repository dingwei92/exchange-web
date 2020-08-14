package com.topsci.qh.webmanagement.Business.Normal;

import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Service.Normal.StatisticService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * Created by lzw.
 * 16-10-14
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController extends BasicController {
    @Resource
    private StatisticService statisticService;

    @RequestMapping("/show.do")
    public ModelAndView showPage(HttpServletRequest request)
    {
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");

        if(StringUtils.isEmpty(starttime) || StringUtils.isEmpty(endtime)) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            String year = c.get(Calendar.YEAR) + "";
            String month = (c.get(Calendar.MONTH)) + "";
            starttime = year+"-"+month;
            endtime = year+"-"+month;
        }

        ModelAndView model = new ModelAndView("/Normal/Statistic/show");
        Map<String,JSONObject> result = statisticService.getTestData(starttime,endtime);
        model.addObject("chart",result.get("chart").toJSONString());
        model.addObject("table",result.get("table").toJSONString());



        model.addObject("starttime",starttime);
        model.addObject("endtime",endtime);
        return model;
    }

    @RequestMapping(value = "/show_json.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String showPage_json(HttpServletRequest request)
    {
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");

        if(StringUtils.isEmpty(starttime) || StringUtils.isEmpty(endtime)) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            String year = c.get(Calendar.YEAR) + "";
            String month = (c.get(Calendar.MONTH)) + "";
            starttime = year+"-"+month;
            endtime = year+"-"+month;
        }

        Map<String,JSONObject> result = statisticService.getTestData(starttime,endtime);
        JSONObject object = new JSONObject();
        object.put("chart",result.get("chart"));
        object.put("table",result.get("table"));
        return object.toJSONString();
    }
}
