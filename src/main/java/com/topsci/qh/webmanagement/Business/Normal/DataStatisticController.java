package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.PageInfo;

import com.topsci.qh.webmanagement.Service.Normal.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lzw.
 * 16-7-27
 */
@Controller
@RequestMapping("/datastatistic")
public class DataStatisticController extends BasicController {

    @Resource
    private DataStatisticService dataStatisticService;
    @Resource
    private BusinessSystemService businessSystemService;
    @Resource
    private CatalogService catalogService;
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private CatalogTemplateService catalogTemplateService;

    private String starttimer;
    private String endtimer;
    private String starttime;
    private String endtime;

    @RequestMapping("/clbrowser.do")
    public ModelAndView browser(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/bscataloglistType");
        model = attrsRender(model,request,pageInfo,"数据量统计-模板类别");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("clbrowser.do?starttime="+starttimer+"&endtime="+endtimer);
        List<Map<String,Object>> result = dataStatisticService.getCatalogTemplateTypeResultTab(pageInfo, starttime,endtime);
        result.forEach(r->{
            if(r.get("bid").equals("22b3521e269d40fcac0df6504d7fb415") || r.get("bid").equals("9ecb324ef82740e8864cca7d305c7e7e"))
            {
                int count = (int)r.get("data");
                r.put("data",count/2);
            }
        });
        model.addObject("result",result);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/clbrowserdb.do")
    public ModelAndView browserdb(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/bscataloglistDB");
        String tid = request.getParameter("tid");
        model = attrsRender(model,request,pageInfo,"数据量统计-数据源");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("clbrowserdb.do?starttime="+starttimer+"&endtime="+endtimer);
        List<Map<String,Object>> result = dataStatisticService.getCatalogDBResultTab(pageInfo, starttime,endtime,tid);
        for(Map<String,Object> r : result){
            String b = (String)r.get("bid");
            if(StringUtils.isEmpty(b)){
                r.put("bid","__OTHER__");
            }
        }
        model.addObject("sourcenames",catalogService.getDBInfoUuidNameMap());
        model.addObject("result",result);
        model.addObject("pageinfo",pageInfo);
        model.addObject("tid",tid);
        return model;
    }
    
    @RequestMapping("/bscatalog.do")
    public ModelAndView bscataloglist(HttpServletRequest request, PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/bscataloglist");
        String title = "业务系统接口发布统计";
        model = attrsRender(model,request,pageInfo,title);
        pageInfo = getPageInfo(request, pageInfo);
        pageInfo.setUrl("bscataloglist.do?starttime="+starttimer+"&endtime="+endtimer);
        List<Map<String,Object>> result = dataStatisticService.getBussinessResult(pageInfo, starttime,endtime);
        model.addObject("result",result);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/catalog.do")
    public ModelAndView cataloglist(HttpServletRequest request, PageInfo pageInfo)
    {
    	String tid = request.getParameter("tid");
    	String did = request.getParameter("did");
    	if(StringUtils.isEmpty(did)){
    		did = "0";
    	}
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/cataloglist");
        String title = "数据量统计-接口";
        model = attrsRender(model,request,pageInfo,title);
        pageInfo = getPageInfo(request, pageInfo);
        pageInfo.setUrl("catalog.do?tid="+tid+"&did="+did+"&starttime="+starttimer+"&endtime="+endtimer);
        List<Map<String,Object>> result = dataStatisticService.getCatalogResultTab(pageInfo, starttime,endtime,did);
        model.addObject("result",result);
        model.addObject("pageinfo",pageInfo);
        model.addObject("did",did);
        model.addObject("tid",tid);
        return model;
    }

    @RequestMapping("/mclbrowser.do")
    public ModelAndView browserMesg(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/bscataloglistTypeMsg");
        model = attrsRender(model,request,pageInfo,"消息发布统计-模板类别");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("mclbrowser.do?starttime="+starttimer+"&endtime="+endtimer);
        List<Map<String,Object>> result = dataStatisticService.getCatalogTemplateTypeResult(pageInfo, starttime,endtime);
        result.forEach(r->{
            if(r.get("bid").equals("22b3521e269d40fcac0df6504d7fb415") || r.get("bid").equals("9ecb324ef82740e8864cca7d305c7e7e"))
            {
                int count = (int)r.get("data");
                r.put("data",count/2);
            }
        });
        model.addObject("result",result);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/mclbrowserdb.do")
    public ModelAndView browserdbMesg(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/bscataloglistDBMsg");
        String tid = request.getParameter("tid");
        model = attrsRender(model,request,pageInfo,"消息发布统计-数据源");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("mclbrowserdb.do?starttime="+starttimer+"&endtime="+endtimer);
        List<Map<String,Object>> result = dataStatisticService.getCatalogDBResult(pageInfo, starttime,endtime,tid);
        for(Map<String,Object> r : result){
            String b = (String)r.get("bid");
            if(StringUtils.isEmpty(b)){
                r.put("bid","__OTHER__");
            }
        }
        model.addObject("sourcenames",catalogService.getDBInfoUuidNameMap());
        model.addObject("result",result);
        model.addObject("pageinfo",pageInfo);
        model.addObject("tid",tid);
        return model;
    }

    @RequestMapping("/mcatalog.do")
    public ModelAndView cataloglistMesg(HttpServletRequest request, PageInfo pageInfo)
    {
        String tid = request.getParameter("tid");
        String did = request.getParameter("did");
        if(StringUtils.isEmpty(did)){
            did = "0";
        }
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/cataloglistMsg");
        String title = "消息发布统计-接口";
        model = attrsRender(model,request,pageInfo,title);
        pageInfo = getPageInfo(request, pageInfo);
        pageInfo.setUrl("mcatalog.do?tid="+tid+"&did="+did+"&starttime="+starttimer+"&endtime="+endtimer);
        List<Map<String,Object>> result = dataStatisticService.getCatalogResult(pageInfo, starttime,endtime,did);
        model.addObject("result",result);
        model.addObject("pageinfo",pageInfo);
        model.addObject("did",did);
        model.addObject("tid",tid);
        return model;
    }

    @RequestMapping("/bssubscribe.do")
    public ModelAndView bssubscribelist(HttpServletRequest request, PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/bssubscribelist");
        String title = "消息订阅统计-按订阅者";
        model = attrsRender(model,request,pageInfo,title);
        pageInfo = getPageInfo(request, pageInfo);
        pageInfo.setUrl("bssubscribe.do?starttime="+starttimer+"&endtime="+endtimer);

        List<Map<String,Object>> result = dataStatisticService.getBussinessSubscribeResult(pageInfo,starttime,endtime);

        model.addObject("result",result);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/subscribe.do")
    public ModelAndView subscribelist(HttpServletRequest request, PageInfo pageInfo)
    {
    	String bid = request.getParameter("bid");
    	if(StringUtils.isEmpty(bid)){
    		bid = "0";
    	}
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/subscribelist");
        String title = "消息订阅统计-按接口";
        model = attrsRender(model,request,pageInfo,title);
        pageInfo = getPageInfo(request, pageInfo);
        pageInfo.setUrl("subscribe.do?bid="+bid+"&starttime="+starttimer+"&endtime="+endtimer);

        List<Map<String,Object>> result = dataStatisticService.getSubscribeResult(pageInfo,starttime,endtime,Integer.valueOf(bid));

        model.addObject("result",result);
        model.addObject("pageinfo",pageInfo);
        model.addObject("bid",bid);
        return model;
    }
    
    @RequestMapping("/chartStatistic.do")
    public ModelAndView chartStatistic(HttpServletRequest request, PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/chartstatistic");
        String title = "统计图表";
        model = attrsRender(model,request,pageInfo,title);
        String cid = request.getParameter("cid");
        String tid = request.getParameter("tid");
        String did = request.getParameter("did");
        List<Map<String,Object>> result = dataStatisticService.getDayCatalogResultTab(starttime,endtime,cid);
        Map<Integer,String> catalog = catalogService.getCatalogIdNameMap(true);
        model.addObject("catalog",catalog);
        model.addObject("cid",Integer.parseInt(cid));
        model.addObject("tid",tid);
        model.addObject("did",did);
        model.addObject("result",result);
        return model;
    }

    @RequestMapping("/mchartStatistic.do")
    public ModelAndView chartStatisticMsg(HttpServletRequest request, PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/DataStatistic/chartstatisticMsg");
        String title = "统计图表";
        model = attrsRender(model,request,pageInfo,title);
        String cid = request.getParameter("cid");
        String tid = request.getParameter("tid");
        String did = request.getParameter("did");
        List<Map<String,Object>> result = dataStatisticService.getDayCatalogResult(starttime,endtime,cid);
        Map<Integer,String> catalog = catalogService.getCatalogIdNameMap(true);
        model.addObject("catalog",catalog);
        model.addObject("cid",Integer.parseInt(cid));
        model.addObject("tid",tid);
        model.addObject("did",did);
        model.addObject("result",result);
        return model;
    }

    private ModelAndView attrsRender(ModelAndView model,HttpServletRequest request,PageInfo pageInfo,String title)
    {
        Map<String,String> business = businessSystemService.getBusinessSystemIdNameMap(true);
        Map<Integer,String> catalog = catalogService.getCatalogIdNameMap(true);
        Map<String,String> ttypes = catalogTemplateService.getTypeUuidNameMap();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE,c.get(Calendar.DATE)-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        starttimer = request.getParameter("starttime");
        endtimer = request.getParameter("endtime");
        if (StringUtils.isEmpty(starttimer)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            starttime = format.format(c.getTime());
            starttimer = starttime;
        }
        starttime = starttimer+ " 00:00:00";
        if (StringUtils.isEmpty(endtimer)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            endtime = format.format(c.getTime());
            endtimer = endtime;
        }
        endtime = endtimer + " 23:59:59";

        String end = sdf.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH,-30);
        String begin = sdf.format(c.getTime());
        model.addObject("starttime",starttimer);
        model.addObject("endtime",endtimer);
        model.addObject("begin",begin);
        model.addObject("end",end);
        model.addObject("title",title);
        model.addObject("business",business);
        model.addObject("catalog",catalog);
        model.addObject("ttypes",ttypes);
        return model;
    }

}
