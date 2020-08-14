package com.topsci.qh.webmanagement.Business.Normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogTemplate;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogTemplateType;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Service.Normal.CatalogTemplateService;
import com.topsci.qh.webmanagement.Service.Normal.DatabasePublishService;
import com.topsci.qh.webmanagement.Tools.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/catalog")
public class CatalogTemplateController extends BasicController{
    private static Logger log = LoggerFactory.getLogger(CatalogTemplateController.class);

    @Autowired
    private CatalogTemplateService catalogTemplateService;
    @Autowired
    private DatabasePublishService databasePublishService;

    @RequestMapping("/tbrowser.do")
    public ModelAndView tbrowser(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/CatalogTemplate/templatebrowser");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("tbrowser.do");
        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
        List<ServerCatalogTemplateType> types = catalogTemplateService.getTypesByPage(searchword);
        model.addObject("types",types);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("tlist.do")
    public ModelAndView templateList(HttpServletRequest request, PageInfo pageInfo)
    {
        ModelAndView view = new ModelAndView("/Normal/CatalogTemplate/templatelist");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("tlist.do");
        Map<String, String> status = catalogTemplateService.getTemplatestatus();
        String search = request.getParameter("search");
        String tuuid = request.getParameter("tuuid");
        if (StringUtils.isNotEmpty(search)) {
            view.addObject("search", search);
            pageInfo.setUrl(pageInfo.getUrl() + "?tuuid="+tuuid+"&search=" + search);
        }
        else{
            pageInfo.setUrl(pageInfo.getUrl()+"?tuuid="+tuuid);
        }
        boolean sysuser = Constants.INIT_USERNAME.equals(sessionManager.getSessionAttrObj(request, Constants.LOGINNAME));
        List<ServerCatalogTemplate> tpls = catalogTemplateService.getTemplateByTypePage(search,tuuid);
        view.addObject("tpls",tpls);
        view.addObject("pageinfo",pageInfo);
        view.addObject("status",status);
        view.addObject("search",search);
        view.addObject("tuuid",tuuid);
        view.addObject("sysuser",sysuser);
        view.addObject("title","接口模板管理");
        view.addObject("draft", CatalogTemplateService.DRAFT);
        return view;
    }

    @RequestMapping("/tedit.do")
    public ModelAndView templateEdit(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/Normal/CatalogTemplate/templateedit");
        ServerCatalogTemplate template = new ServerCatalogTemplate();
        boolean sysuser = Constants.INIT_USERNAME.equals(sessionManager.getSessionAttrObj(request, Constants.LOGINNAME));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String begin = sdf.format(new Date())+":00";
        Map<String, String> status = catalogTemplateService.getTemplatestatus();
        Map<String, String> statusdraft = new HashMap<>();
        String uuid = request.getParameter("uuid");
        String mesg = request.getParameter("mesg");
        String color = request.getParameter("color");
        String search = request.getParameter("search");
        String tuuid = request.getParameter("tuuid");
        String title;
        String fields;
        if (StringUtils.isNotEmpty(uuid)) {
            template = catalogTemplateService.getTemplate(uuid);
            title = "修改接口模板";
        } else {
            title = "新建接口模板";
            template.setCreateTime(LocalDateTime.now());
            template.setInvalidTime(LocalDateTime.now());
            statusdraft.putAll(status);
            if(!sysuser)
            {
                status = statusdraft;
            }
        }
        boolean allowmodify  = sysuser || (!CatalogTemplateService.PUBLISHED.equals(template.getStatus()));
        Map<String,String> r = catalogTemplateService.getListsJson(template.getId());
        fields = r.get("jsonstr");
        model.addObject("title", title);
        model.addObject("tpl", template);
        model.addObject("mesg", mesg);
        model.addObject("status", status);
        model.addObject("fields", fields);
        model.addObject("color", color);
        model.addObject("allowmodify", allowmodify);
        model.addObject("search", search);
        model.addObject("tuuid", tuuid);
        model.addObject("begin", begin);
        model.addObject("types", catalogTemplateService.getType(tuuid));
        model.addObject("draft",CatalogTemplateService.DRAFT);
        model.addObject("invalid",CatalogTemplateService.TLPINVALID);
        try {
            String oldlist = URLEncoder.encode(r.get("beanmap"), "UTF-8");
            model.addObject("oldlist", oldlist);
        }
        catch (Exception ex)
        {
            log.warn("接口字段列表编码错误！");
        }
        return model;
    }

    @RequestMapping("/tsave.do")
    public RedirectView templateSave(HttpServletRequest request,
                                     ServerCatalogTemplate template, RedirectAttributes attr) {
        RedirectView model = new RedirectView("tedit.do");
        String beanmap = request.getParameter("oldlist");
        String fields = request.getParameter("field_result");
        String search = request.getParameter("search");
        String tuuid = request.getParameter("tuuid");
        String oldstatus = request.getParameter("oldstatus");
        boolean copy = Boolean.valueOf(request.getParameter("copy"));
        copy = false;
        if (template.getId() != 0) {
            if(copy)
            {
                template.setId(0);
                template.setStatus(CatalogTemplateService.DRAFT);
                template.setCreateTime(LocalDateTime.now());
                template.setName(template.getName()+"_"+ DateUtil.getNow3());
                beanmap = "";
                JSONObject obj = JSON.parseObject(fields);
                for(int i = 0 ; i  < Integer.parseInt(obj.getString("count"));i++)
                {
                    JSONObject objcol = obj.getJSONObject(i+"");
                    objcol.put("id",0);
                }
                fields = obj.toJSONString();
                catalogTemplateService.saveTemplate(template);
            }
            else {
                catalogTemplateService.updateTemplate(template);
//                if((!template.getStatus().equals(oldstatus)) && template.getStatus().equals(CatalogTemplateService.TLPINVALID))
//                {
//                    catalogTemplateService.templateInvalidNofity(template);
//                }
            }
        } else {
            template.setStatus(CatalogTemplateService.PUBLISHED);
            catalogTemplateService.saveTemplate(template);
        }
        try {
            catalogTemplateService.saveTemplateLists(template, fields, URLDecoder.decode(beanmap, "UTF-8"));
        }
        catch (Exception ex)
        {
            log.warn("接口字段列表编码错误！");
            attr.addAttribute("mesg", "保存失败！");
            attr.addAttribute("color", Constants.TIP_ERROR);
            attr.addAttribute("uuid", template.getId());
            return model;
        }
        attr.addAttribute("mesg", "保存成功！");
        attr.addAttribute("color", Constants.TIP_SUCCESS);
        attr.addAttribute("uuid", template.getId());
        attr.addAttribute("search", search);
        attr.addAttribute("tuuid", tuuid);
        return model;
    }

    @RequestMapping("/tdelete.do")
    public RedirectView templateDelete(HttpServletRequest request,RedirectAttributes attr) {
        RedirectView model = new RedirectView("tlist.do?tuuid="+request.getParameter("tuuid"));
        catalogTemplateService.deleteTemplate(request.getParameter("uuid"), request);
        attr.addAttribute(Constants.CURRENT_PAGE,request.getParameter(Constants.CURRENT_PAGE));
        return model;
    }

    @RequestMapping("/tpublish.do")
    public RedirectView templatePublish(HttpServletRequest request,RedirectAttributes attr) {
        RedirectView model = new RedirectView("tlist.do?tuuid="+request.getParameter("tuuid"));
        catalogTemplateService.publishTemplate(request.getParameter("uuid"), request);
        attr.addAttribute(Constants.CURRENT_PAGE,request.getParameter(Constants.CURRENT_PAGE));
        return model;
    }

    @RequestMapping(value = "/tcheckrepeat.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String templateCheckRepeat(HttpServletRequest request) {
        catalogTemplateService.checkTemplateRepeated(request.getParameter("id"),request.getParameter("name"));
        return "";
    }

    @RequestMapping(value = "/tcheckuse.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String templateCheckUser(HttpServletRequest request) {
        catalogTemplateService.checkTemplateUse(request.getParameter("id"));
        return "";
    }

    @RequestMapping(value = "/tcollist.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object getTemplateList(HttpServletRequest request)
    {
        String tid = request.getParameter("tid");
        Map<String,String> r = catalogTemplateService.getListsJson(Integer.parseInt(tid));
        JSONObject obj = JSON.parseObject(r.get("jsonstr"));
        obj.remove("count");
        return obj;
    }

    @RequestMapping(value = "/gettpl.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getTemplates(HttpServletRequest request) {
        List<ServerCatalogTemplate> list = catalogTemplateService.getTemplatesByType(request.getParameter("uuid"));
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
        return array.toJSONString();
    }

    @RequestMapping("/typelist.do")
    public ModelAndView typelist(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/CatalogTemplate/typelist");
        pageInfo = getPageInfo(request,pageInfo);

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
        List<ServerCatalogTemplateType> types = catalogTemplateService.getTypesByPage(searchword);
        model.addObject("types",types);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/typeedit.do")
    public ModelAndView typeedit(HttpServletRequest request)
    {
        ModelAndView model = new ModelAndView("/Normal/CatalogTemplate/typeedit");
        ServerCatalogTemplateType type = new ServerCatalogTemplateType();
        String uuid=request.getParameter("uuid");
        String mesg = request.getParameter("mesg");
        String color = request.getParameter("color");
        String title;
        if(StringUtils.isNotEmpty(uuid))
        {
            title = "修改模板类别";
            type = catalogTemplateService.getType(uuid);
        }
        else
        {
            type.setCreatetime(LocalDateTime.now());
            title = "新建模板类别";
        }
        model.addObject("dbs",databasePublishService.getDBInfos());
        model.addObject("title",title);
        model.addObject("type",type);
        model.addObject("mesg",mesg);
        model.addObject("color",color);
        return model;
    }

    @RequestMapping("/typesave.do")
    public RedirectView save(HttpServletRequest request,ServerCatalogTemplateType type,RedirectAttributes attr)
    {
        RedirectView model = new RedirectView("typeedit.do");
        if(StringUtils.isEmpty(type.getUuid()))
        {
            catalogTemplateService.saveType(type);
        } else
        {
            catalogTemplateService.udpateType(type);
        }
        attr.addAttribute("mesg","保存成功!");
        attr.addAttribute("color",Constants.TIP_SUCCESS);
        attr.addAttribute("uuid",type.getUuid());
        return model;
    }

    @RequestMapping("/typedelete.do")
    public RedirectView deleteBusinessSystem(HttpServletRequest request,RedirectAttributes attr)
    {
        RedirectView model = new RedirectView("typelist.do");
        catalogTemplateService.deleteType(request.getParameter("uuid"));
        attr.addAttribute(Constants.CURRENT_PAGE, request.getParameter(Constants.CURRENT_PAGE));
        return model;
    }

    @RequestMapping(value = "/typecheckrepeat.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    protected String checkRepeat(HttpServletRequest request)
    {
        catalogTemplateService.checkrepeatType(request.getParameter("id"), request.getParameter("name"));
        return "";
    }

    @RequestMapping(value = "/typecheckuse.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String templateTypeCheckUser(HttpServletRequest request) {
        catalogTemplateService.checkTemplateTypeUse(request.getParameter("id"));
        return "";
    }


}
