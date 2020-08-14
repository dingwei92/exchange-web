package com.topsci.qh.webmanagement.Business.Normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Service.Normal.CatalogService;
import com.topsci.qh.webmanagement.Service.Normal.CatalogTemplateService;
import com.topsci.qh.webmanagement.Service.Normal.DatabasePublishService;
import com.topsci.qh.webmanagement.Tools.DateUtil;
import com.topsci.qh.webmanagement.Tools.EncryptTool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/catalog")
public class DatabasePublishController extends BasicController {
    private static Logger logger = LoggerFactory.getLogger(DatabasePublishController.class);
    @Resource
    private DatabasePublishService databasePublishService;
    @Resource
    private CatalogService catalogService;
    @Resource
    private CatalogTemplateService catalogTemplateService;


    @RequestMapping(value = "/dbpublish_validate.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object checkDBConnect(HttpServletRequest request)
    {
        JSONObject obj = new JSONObject();
        String dbuuid = request.getParameter("d");
        obj.put("result", databasePublishService.validateDB(dbuuid));
        return obj;
    }

    @RequestMapping(value = "/dbpublish_gettables.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object getTables(HttpServletRequest request)
    {
        String dbtype = request.getParameter("d");
        Map<String,String> tables = databasePublishService.getTables(dbtype);
        JSONObject obj = new JSONObject();
        JSONObject obj1 = JSON.parseObject(JSON.toJSONString(tables), Feature.OrderedField);
        obj.put("tbls",obj1);
        return obj;
    }

    @RequestMapping(value = "/dbpublish_getcols.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object getColumns(HttpServletRequest request)
    {
        String dbuuid = request.getParameter("dbid");
        String tplid = request.getParameter("tplid");
        String sql = request.getParameter("sql");
        String tblname = request.getParameter("tblname");
        Map<String,String> tables = databasePublishService.getColumns(dbuuid,sql,tblname,tplid);
        JSONObject obj = new JSONObject();
        JSONObject obj1 = JSON.parseObject(JSON.toJSONString(tables),Feature.OrderedField);
        obj.put("cols",obj1);
        return obj;
    }


    @RequestMapping("/dbinfolist.do")
    public ModelAndView typelist(HttpServletRequest request, PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/DatabasePublish/list");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("dbinfolist.do");
        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
        List<ServerCatalogDBinfo> types = databasePublishService.getDBInfoByPage(pageInfo,searchword);
        model.addObject("dbtypes",databasePublishService.getDbtypes());
        model.addObject("types",types);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/dbinfoedit.do")
    public ModelAndView typeedit(HttpServletRequest request)
    {
        ModelAndView model = new ModelAndView("/Normal/DatabasePublish/edit");
        ServerCatalogDBinfo type = new ServerCatalogDBinfo();
        String uuid=request.getParameter("uuid");
        String mesg = request.getParameter("mesg");
        String color = request.getParameter("color");
        String title;
        if(StringUtils.isNotEmpty(uuid))
        {
            title = "修改数据源";
            type = databasePublishService.getDBInfo(uuid);

        }
        else
        {
            type.setCreateTime(new Timestamp(new Date().getTime()));
            title = "新建数据源";
        }
        model.addObject("dbtypes",databasePublishService.getDbtypes());
        model.addObject("title",title);
        model.addObject("type",type);
        model.addObject("mesg",mesg);
        model.addObject("color",color);
        return model;
    }

    @RequestMapping("/dbinfosave.do")
    public RedirectView save(HttpServletRequest request, ServerCatalogDBinfo type, RedirectAttributes attr)
    {
        RedirectView model = new RedirectView("dbinfoedit.do");
        if(StringUtils.isEmpty(type.getUuid()))
        {
            databasePublishService.saveDBInfo(type, request);
        } else
        {
            databasePublishService.updateDBInfo(type, request);
        }
        attr.addAttribute("mesg","保存成功!");
        attr.addAttribute("color", Constants.TIP_SUCCESS);
        attr.addAttribute("uuid",type.getUuid());
        return model;
    }

    @RequestMapping("/dbinfodelete.do")
    public RedirectView deleteBusinessSystem(HttpServletRequest request,RedirectAttributes attr)
    {
        RedirectView model = new RedirectView("dbinfolist.do");
        databasePublishService.deleteDBInfo(request.getParameter("uuid"), request);
        attr.addAttribute(Constants.CURRENT_PAGE, request.getParameter(Constants.CURRENT_PAGE));
        return model;
    }

    @RequestMapping(value = "/dbinfocheckrepeat.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    protected String checkRepeat(HttpServletRequest request)
    {
        return databasePublishService.checkDBInfoRepeated(request.getParameter("uuid"),
                request.getParameter("sourcename"));
    }

    @RequestMapping(value = "/dbinfocheckuse.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String templateTypeCheckUser(HttpServletRequest request) {
        return databasePublishService.checkDBInfoUse(request.getParameter("id"));
    }

    @RequestMapping("/dbbrowser.do")
    public ModelAndView browser(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/CatalogList/dbbrowserType");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("dbbrowser.do");
        List<ServerCatalogTemplateType> types = catalogTemplateService.getTypesByPage(null);
        model.addObject("types",types);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/dbbrowserdb.do")
    public ModelAndView browserdb(HttpServletRequest request, PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/CatalogList/dbbrowserDB");
        String tuuid = request.getParameter("tuuid");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("dbbrowserdb.do?tuuid="+tuuid);
        List<ServerCatalogDBinfo> types = databasePublishService.getDBInfoByTemplateTypePage(pageInfo,tuuid);
        model.addObject("dbtypes",databasePublishService.getDbtypes());
        model.addObject("types",types);
        model.addObject("pageinfo",pageInfo);
        model.addObject("tuuid",tuuid);
        return model;
    }

    @RequestMapping("/dbpubstatus.do")
    public ModelAndView dbPubStatus(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView view = new ModelAndView("/Normal/DatabasePublish/dbpubstatus");
        String search = request.getParameter("search");
        String duuid = request.getParameter("duuid");
        String tuuid = request.getParameter("tuuid");
        pageInfo = getPageInfo(request, pageInfo);
        if(StringUtils.isNotEmpty(search)) {
            pageInfo.setUrl("dbpubstatus.do?duuid=" + duuid + "&tuuid=" + tuuid+"&search="+search);
        }
        else{
            pageInfo.setUrl("dbpubstatus.do?duuid=" + duuid + "&tuuid=" + tuuid);
        }
        List<ServerCatalog> catalogs = catalogService.getCatalogByPublishTypeByPage(CatalogService.PUB_DB,search,duuid,pageInfo);
        List<ServerCatalogDbPub> dBinfos = catalogService.getCatalogDbPubs(catalogs);
        Map<String,String> catalogmap = new HashMap<>();
        Map<String,String> catalogsmap = new HashMap<>();
        Map<String,String> dbtimemap = new HashMap<>();
        Map<String,Integer> dbcountmap = new HashMap<>();
        Map<String,Integer> dbcountallmap = new HashMap<>();
        for(ServerCatalog sc : catalogs){
            catalogmap.put(sc.getId()+"",sc.getServerName());
            catalogsmap.put(sc.getId()+"",sc.getServerShort());
            boolean find = false;
            for(ServerCatalogDbPub db : dBinfos){
                if(db.getUuid().equals(sc.getDbId())) {
					dbtimemap.put(sc.getId()+"", db.getLastRun() == null ? "未执行" : DateUtil.DateToString(db.getLastRun()));
					dbcountmap.put(sc.getId()+"",db.getLastCount()==null?0:db.getLastCount());
					dbcountallmap.put(sc.getId()+"",db.getAllCount()==null?0:db.getAllCount());
                    find = true;
                    break;
                }
            }
            if(!find)
            {
                dbtimemap.put(sc.getId()+"","未执行");
                dbcountmap.put(sc.getId()+"",0);
            }
        }
        if (StringUtils.isNotEmpty(search)) {
            view.addObject("search", search);
            pageInfo.setUrl(pageInfo.getUrl() + "?search=" + search + "");
        }
        view.addObject("catalogs",catalogmap);
        view.addObject("catalogshort",catalogsmap);
        view.addObject("dbs",dbtimemap);
        view.addObject("dbcs",dbcountmap);
        view.addObject("dbcsa",dbcountallmap);
        view.addObject("pageinfo",pageInfo);
        view.addObject("tuuid",tuuid);
        view.addObject("duuid",duuid);
        return view;
    }

    @RequestMapping("/dbpubactive.do")
    public ModelAndView dbActivePub(HttpServletRequest request)
    {
        ModelAndView view = new ModelAndView("/Normal/DatabasePublish/dbactivepub");
        String clid=request.getParameter("clid");
        String duuid = request.getParameter("duuid");
        String tuuid = request.getParameter("tuuid");
        ServerCatalog catalog = catalogService.getCatalog(clid);
        view.addObject("title","手动触发接口："+catalog.getServerName()+"("+catalog.getServerShort()+")");
        view.addObject("clid",clid);
        view.addObject("startTime", DateUtil.getNow2());
        view.addObject("endTime",DateUtil.getNow2());
        view.addObject("tuuid",tuuid);
        view.addObject("duuid",duuid);
        return view;
    }

    @RequestMapping("/dbpubactivesave.do")
    public RedirectView dbActivePubSave(HttpServletRequest request)
    {
        RedirectView view = new RedirectView("dbpubstatus.do?duuid="+request.getParameter("duuid")+"&tuuid="+request.getParameter("tuuid"));
        String clid=request.getParameter("clid");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        try {
            databasePublishService.activepubsave(clid, startTime, endTime,request);
        }
        catch (Exception ex)
        {
            logger.error("存储接口数据发布手动触发任务错误",ex);
        }
        return view;
    }
}
