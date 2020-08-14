package com.topsci.qh.webmanagement.Business.Normal;

import com.alibaba.fastjson.JSON;
import com.topsci.qh.webmanagement.Pojo.BusinessSystem;
import com.topsci.qh.webmanagement.Pojo.ServerMessageDetail;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Service.Normal.BusinessSystemService;
import com.topsci.qh.webmanagement.Service.Normal.CatalogService;
import com.topsci.qh.webmanagement.Service.Normal.SystemMonitorService;
import com.topsci.qh.webmanagement.Tools.Config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lzw.
 * 16-7-27
 */
@Controller
@RequestMapping("/systemmonitor")
public class ServerMonitorController extends BasicController {

    @Resource
    private BusinessSystemService businessSystemService;
    @Resource
    private SystemMonitorService systemMonitorService;
    @Resource
    private CatalogService catalogService;
    @Resource
    private Config config;

    @RequestMapping("/monitor.do")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/Normal/SystemMonitor/list");
        Map<String,String> business = businessSystemService.getShortNameMap();
        Map<String,String> catalog = catalogService.getShortNameMap();
        business.put("ALL","所有");
        String businessjson = JSON.toJSONString(business);
        String catalogjson = JSON.toJSONString(catalog);
        List<ServerMessageDetail> details = systemMonitorService.getDetails();
        model.addObject("details",details);
        model.addObject("title","服务监控");
        model.addObject("business",businessjson);
        model.addObject("catalog",catalogjson);
        model.addObject("nbusiness",business);
        model.addObject("ncatalog",catalog);
        model.addObject("logShowNum",Integer.valueOf(config.getLogShowNum()));
        return model;
    }

    @RequestMapping(value = "/json.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkRepeat(HttpServletRequest request) {
        return systemMonitorService.getDetailsJson();
    }

}
