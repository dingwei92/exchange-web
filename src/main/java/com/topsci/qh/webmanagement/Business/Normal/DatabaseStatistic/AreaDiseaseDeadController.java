package com.topsci.qh.webmanagement.Business.Normal.DatabaseStatistic;

import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Service.Normal.DatabaseStatistic.AreaDiseaseDeadService;
import com.topsci.qh.webmanagement.Tools.Cache;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @ClassName: AreaDiseaseDeadController  
 * @Description: TODO
 * @author tgeng
 * @date 2016-12-7 下午1:12:29  
 *
 */
@Controller
@RequestMapping("/statistic")
public class AreaDiseaseDeadController extends BasicController {
    @Resource
    private AreaDiseaseDeadService areaDiseaseDeadService;
    
    @Resource
    private Cache cache;
    
    @RequestMapping("/areadiseasedead.do")
    public ModelAndView areadiseasedead(HttpServletRequest request)
    {
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        String areacode = request.getParameter("areacode");
        
        List<String> years = areaDiseaseDeadService.getYears();

        if(StringUtils.isEmpty(starttime) || StringUtils.isEmpty(endtime)) {
            starttime = years.get(years.size()-5);
            endtime = years.get(years.size()-1);
        }

        ModelAndView model = new ModelAndView("/Normal/Statistic/stAreaDiseaseDead");
        Map<String,JSONObject> result = areaDiseaseDeadService.getDiseaseDeadData(starttime,endtime,areacode);
        
        
        model.addObject("chart",result.get("chart").toJSONString());
        model.addObject("table",result.get("table").toJSONString());
        
        model.addObject("years",years);

        model.addObject("starttime",starttime);
        model.addObject("endtime",endtime);
        model.addObject("areaoptions",cache.getAttr(Cache.STATISTIC_AREA_CODE));
        return model;
    }

    @RequestMapping(value = "/areadiseasedead_json.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String areadiseasedead_json(HttpServletRequest request)
    {
    	String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        String areacode = request.getParameter("areacode");

        List<String> years = areaDiseaseDeadService.getYears();

        if(StringUtils.isEmpty(starttime) || StringUtils.isEmpty(endtime)) {
            starttime = years.get(years.size()-5);
            endtime = years.get(years.size()-1);
        }

        Map<String,JSONObject> result = areaDiseaseDeadService.getDiseaseDeadData(starttime,endtime,areacode);
        JSONObject object = new JSONObject();
        object.put("chart",result.get("chart"));
        object.put("table",result.get("table"));
        return object.toJSONString();
    }
}
