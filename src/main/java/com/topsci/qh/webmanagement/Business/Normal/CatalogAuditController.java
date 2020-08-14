package com.topsci.qh.webmanagement.Business.Normal;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.topsci.qh.webmanagement.Pojo.ServerCatalogList;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.topsci.qh.webmanagement.Pojo.BusinessSystem;
import com.topsci.qh.webmanagement.Pojo.ServerCatalog;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.PageInfo;

import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Service.Normal.BusinessSystemService;
import com.topsci.qh.webmanagement.Service.Normal.CatalogAuditService;
import com.topsci.qh.webmanagement.Service.Normal.CatalogService;
/**
 * @ClassName: CatalogAuditController  
 * @author tgeng
 * @date 2016-11-21 下午3:39:42  
 *
 */
@Controller
@RequestMapping("/catalog")
public class CatalogAuditController  extends BasicController implements IBasicController{
	
	@Resource
    private CatalogAuditService catalogAuditService;
	
	@Resource
	private CatalogService catalogService;
	
	@Resource
	private BusinessSystemService businessSystemService;

	@Override
	@RequestMapping("/listaudit.do")
	public ModelAndView list(HttpServletRequest request,PageInfo pageInfo) {
		ModelAndView model = new ModelAndView("/Normal/CatalogAudit/list");
        pageInfo = getPageInfo(request, pageInfo);
        final String searchword = request.getParameter("search");
        pageInfo.setUrl("listaudit.do");
        if (StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl() + "?search=" + searchword + "");
        }
        Map<String, String> status = catalogService.getStatus();
        Map<String, String> type = catalogService.getType();
        boolean superuser = (boolean) sessionManager.getSessionAttrObj(request, Constants.SUPERUSER);
		List<ServerCatalog> catalogs;
		if(superuser){
			catalogs = catalogAuditService.getCatalogsAuditing(searchword,pageInfo);
		}else{
			String userId = (String)sessionManager.getSessionAttrObj(request, Constants.USERID);
			catalogs = catalogAuditService.getCatalogsAuditingByUser(searchword,pageInfo,userId);
		}
		Map<Integer, String> bslist = businessSystemService.getBusinessSystemIdNameMap(false);
		String searchpath = "listaudit.do";
		model.addObject("searchpath", searchpath);
		model.addObject("title", "发布审核");
		model.addObject("catalog", catalogs);
		model.addObject("pageinfo", pageInfo);
		model.addObject("status", status);
		model.addObject("history",false);
		model.addObject("type", type);
		model.addObject("bslist", bslist);
		model.addObject("superuser", superuser);
		return model;
	}
	
	@RequestMapping("/audithistory.do")
	public ModelAndView audithistory(HttpServletRequest request,PageInfo pageInfo) {
		ModelAndView model = new ModelAndView("/Normal/CatalogAudit/list");
        pageInfo = getPageInfo(request, pageInfo);
        final String searchword = request.getParameter("search");
        pageInfo.setUrl("audithistory.do");
        if (StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl() + "?search=" + searchword + "");
        }
        Map<String, String> status = catalogService.getStatus();
        Map<String, String> type = catalogService.getType();
        boolean superuser = (boolean) sessionManager.getSessionAttrObj(request, Constants.SUPERUSER);
		List<ServerCatalog> catalogs;
		if(superuser){
			//catalogs = catalogAuditService.getCatalogsAll(searchword,pageInfo);
		}else{
			String userId = (String)sessionManager.getSessionAttrObj(request, Constants.USERID);
			catalogs = catalogAuditService.getCatalogsAllByUser(searchword,pageInfo,userId);
		}
		Map<Integer, String> bslist = businessSystemService.getBusinessSystemIdNameMap(false);
		String searchpath = "audithistory.do";
		model.addObject("searchpath", searchpath);
		model.addObject("title", "发布审核历史");
		//model.addObject("catalog", catalogs);
		model.addObject("pageinfo", pageInfo);
		model.addObject("status", status);
		model.addObject("history",true);
		model.addObject("type", type);
		model.addObject("bslist", bslist);
		model.addObject("superuser", superuser);
		return model;
	}

	@Override
	public ModelAndView edit(HttpServletRequest request) {
		return null;
	}
	
	@RequestMapping("/auditone.do")
    public ModelAndView auditone(HttpServletRequest request){
		 ModelAndView model = new ModelAndView("/Normal/CatalogAudit/audit");
	     String id = request.getParameter("uuid");

	     String title = "审核发布";
	     
	     ServerCatalog catalog = catalogService.getCatalog(id);
	     BusinessSystem system = businessSystemService.getBusinessSystem(catalog.getBusinessSystemId());
	     Map<String, String> status = catalogService.getStatus();
	     List<ServerCatalogList> list = catalogAuditService.getCatalogLists(catalog.getId(),request);
	     List<String> bslist = catalogAuditService.getBsLists(catalog.getSpecifySystemIdList(), request);
	     
	     model.addObject("list",list);
	     model.addObject("bslist",bslist);
	     model.addObject("bsname",system.getSystemName()+"("+system.getSystemShort()+")" );
	     model.addObject("title", title);
	     model.addObject("status", status);
	     model.addObject("catalog", catalog);
	     return model;
	}
	
	@RequestMapping("/acceptaudit.do")
    public RedirectView accept(HttpServletRequest request)
    {
		catalogAuditService.acceptCatalog(Integer.parseInt(request.getParameter("id")),request);
        return new RedirectView("listaudit.do");
    }

    @RequestMapping("/denyaudit.do")
    public RedirectView deny(HttpServletRequest request)
    {
    	catalogAuditService.denyCatalog(Integer.parseInt(request.getParameter("id")),request);
        return new RedirectView("listaudit.do");
    }
	
	
}
