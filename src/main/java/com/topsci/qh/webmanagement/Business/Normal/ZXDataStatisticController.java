package com.topsci.qh.webmanagement.Business.Normal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogDBinfo;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Service.Normal.ZXDataStatisticService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/zxsta")
public class ZXDataStatisticController extends BasicController {
    @Resource
    private ZXDataStatisticService zxDataStatisticService;

    @RequestMapping("/list.do")
    public ModelAndView indexPage(HttpServletRequest request)
    {
        ModelAndView view = new ModelAndView("/Normal/ZXSta/list");
        String starttime = request.getParameter("starttime");
        String curdbs = request.getParameter("dbs");
        if(StringUtils.isEmpty(starttime))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            starttime = sdf.format(new Date().getTime() - (1000 * 60 * 60 * 24 * 2));
        }
        List<ServerCatalogDBinfo> infos = zxDataStatisticService.getDBS();
        view.addObject("starttime",starttime);
        view.addObject("dbs",infos);
        if(infos.size() > 0){
            view.addObject("curdbs",StringUtils.isEmpty(curdbs)?infos.get(0).getUuid():curdbs);
        }
        return view;
    }

    @ResponseBody
    @RequestMapping(value ="/districtdata.do",produces = "application/json; charset=utf-8")
    public String districtData(HttpServletRequest request)
    {
        String organid = request.getParameter("organid");
        String date = request.getParameter("date");
        String tpltype = request.getParameter("tpltype");
        List<Object[]> districts=zxDataStatisticService.getChildDistrict(organid, date,tpltype);
        JSONArray array = new JSONArray();
        for(Object[] strs : districts)
        {
            JSONObject obj = new JSONObject();
            obj.put("key",strs[0]);
            obj.put("code",strs[1]);
            int num = zxDataStatisticService.countDistrictByParentCode((String)strs[1]);
            obj.put("title",strs[2]+ (num!=0?"("+num+")":""));
            obj.put("level",strs[3]);
            obj.put("lazy",true);
            obj.put("inc",strs[5]);
            obj.put("all",strs[6]);
            array.add(obj);
        }
        return array.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/organdata.do")
    public String getOrganData(HttpServletRequest request) {
        String organid = request.getParameter("organid");
        String date = request.getParameter("date");
        String tpltype = request.getParameter("tpltype");
        JSONArray array = new JSONArray();
        List<Object[]> types = zxDataStatisticService.getChildSanitype(organid,date,tpltype);
        for (Object[] strs : types) {
            JSONObject obj = new JSONObject();
            obj.put("key", organid);
            obj.put("title", strs[1]);
            obj.put("scode", strs[0]);
            obj.put("level", "5");
            obj.put("lazy", true);
            obj.put("type", "1");
            obj.put("inc",strs[2]);
            obj.put("all",strs[3]);
            array.add(obj);
        }
        return array.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/organlist.do")
    public String getOrganList(HttpServletRequest request)
    {
        String type = request.getParameter("type");
        String dis = request.getParameter("dis");
        String date = request.getParameter("date");
        String tpltype = request.getParameter("tpltype");
        List<Object[]> orgs = zxDataStatisticService.getOrganBySaniTypeAndDis(type,dis,date,tpltype);
        JSONArray array = new JSONArray();
        orgs.forEach(o->{
            JSONObject obj = new JSONObject();
            obj.put("key",o[0]);
            obj.put("title",o[1]);
            obj.put("level", "6");
            obj.put("inc",o[2]);
            obj.put("all",o[3]);
            array.add(obj);
        });
        return array.toJSONString();
    }

    @RequestMapping(value = "/detaillist.do",produces = "text/html;charset=UTF-8")
    public ModelAndView detailList(HttpServletRequest request, PageInfo pageInfo)
    {
        ModelAndView view = new ModelAndView("/Normal/ZXSta/detail");
        String orgid = request.getParameter("orgid");
        String sdate = request.getParameter("sdate");
        String tpltype = request.getParameter("tpltype");
        String orgname = request.getParameter("orgname");
        String tplname = request.getParameter("typename");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("detaillist.do?orgid="+orgid+"&tpltype="+tpltype+"&sdate="+sdate+"&orgname="+orgname+"&tplname="+tplname);
        List<String[]> res = zxDataStatisticService.getOrgDate(orgid,sdate,tpltype,pageInfo);
        view.addObject("data",res);
        view.addObject("pageinfo",pageInfo);
        view.addObject("sdate",sdate);
        view.addObject("tpltype",tpltype);
        view.addObject("orgid",orgid);
        view.addObject("orgname",orgname);
        view.addObject("tplname",tplname);
        return view;
    }
}
