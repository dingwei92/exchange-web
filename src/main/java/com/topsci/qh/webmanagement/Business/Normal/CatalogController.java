package com.topsci.qh.webmanagement.Business.Normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Resources.PageInfo;

import com.topsci.qh.webmanagement.Service.Normal.*;
import com.topsci.qh.webmanagement.Tools.ReslutParent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lzw. 16-6-27
 */
@Controller
@RequestMapping("/catalog")
@Api(value = "/catalog", tags = "订阅管理")
public class CatalogController extends BasicController{

	@Resource
	private CatalogService catalogService;
	@Resource
	private BusinessSystemService businessSystemService;
	@Resource
    private RelUserBusinessService relUserBusinessService;
	@Resource
	private RoleService roleService;
	@Resource
	private DatabasePublishService databasePublishService;
	@Resource
	private CatalogTemplateService catalogTemplateService;

	@Resource
	private CatalogAuditService catalogAuditService;


	private Logger log = LoggerFactory.getLogger(CatalogController.class);

	@RequestMapping("/browser")
	@ResponseBody
	@ApiOperation(value = "接口订阅-接口类别", httpMethod = "GET", response = WebFuncs.class, notes = "接口订阅-接口类别")
	public Map browser()
	{
		List<ServerCatalogTemplateType> types = catalogTemplateService.getTypesByPage(null);
		types.forEach(t->t.setTypename(t.getTypename().replace("标准","")));
		ReslutParent reslutParent = new ReslutParent();
		reslutParent.setContent(types);
		return reslutParent.toBack();
	}

	/*@RequestMapping("/browserdb.do")
	public ModelAndView browserdb(HttpServletRequest request, PageInfo pageInfo)
	{
		ModelAndView model = new ModelAndView("/Normal/CatalogList/browserDB");
		String tuuid = request.getParameter("tuuid");
		String readonly = request.getParameter("readonly");
		pageInfo = getPageInfo(request,pageInfo);
		pageInfo.setUrl("browserdb.do?tuuid="+tuuid+"&readonly="+readonly);
		List<ServerCatalogDBinfo> types = databasePublishService.getDBInfoByTemplateTypePage(pageInfo,tuuid);
		types.forEach(t->t.setSourcename(t.getSourcename().substring(t.getSourcename().indexOf("-")+1)));
		pageInfo.setTotalResult(pageInfo.getTotalResult()+1);
		if(types.size()!=pageInfo.getPageSize()){
			ServerCatalogDBinfo scdb = new ServerCatalogDBinfo();
			scdb.setUuid("-1");
			scdb.setSourcename("其他");
			types.add(scdb);
		}
		model.addObject("dbtypes",databasePublishService.getDbtypes());
		model.addObject("types",types);
		model.addObject("pageinfo",pageInfo);
		model.addObject("tuuid",tuuid);
		model.addObject("readonly",readonly);
		return model;
	}*/

	@RequestMapping("/list")
	@ResponseBody
	@ApiOperation(value = "接口订阅-》接口类别-》接口订阅列表(目前去掉接口类别)", httpMethod = "POST", response = ServerCatalog.class, notes = "接口订阅-》接口类别-》接口订阅列表")
	public Map list(@RequestBody ServerCatalogSearchModel serverCatalogSearchModel) {
		String search = serverCatalogSearchModel.getSearch();
		//String duuid = serverCatalogSearchModel.getDuuid();
		String tuuid = serverCatalogSearchModel.getTuuid();
		List<ServerCatalog> catalogs = catalogService.getCatalogsByTemplateAndNullDB(search, tuuid);
		ReslutParent reslutParent = new ReslutParent();
		reslutParent.setContent(catalogs);
		return reslutParent.toBack();
	}


	@RequestMapping("/batchadd.do")
	public ModelAndView batchadd(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/Normal/CatalogTemplate/batchadd");
		ServerCatalog catalog = new ServerCatalog();
		String title = "批量新建数据接口模板";
		model.addObject("tuuid",request.getParameter("tuuid"));
		model.addObject("catalog", catalog);
		model.addObject("title", title);
		model.addObject("types", catalogTemplateService.getTypes());
		return model;
	}


	@RequestMapping("/edit.do")
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/Normal/CatalogList/edit");
		ServerCatalog catalog = new ServerCatalog();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map pubtype = catalogService.getPubtype();
		boolean superuser = (boolean) sessionManager.getSessionAttrObj(request, Constants.SUPERUSER);
		List<BusinessSystem> bslist = new ArrayList<>();
		if(superuser){
			bslist = businessSystemService.getAllBusinessSystems();
		}else{
			String userId = (String)sessionManager.getSessionAttrObj(request, Constants.USERID);
			bslist.add(businessSystemService.getBusinessSystem(relUserBusinessService.getRelUserBusinessByUserId(userId).getBusinessId()));
		}
		Map<String, String> status = catalogService.getStatus();
		String uuid = request.getParameter("uuid");
		String mesg = request.getParameter("mesg");
		String color = request.getParameter("color");
		String tuuid = request.getParameter("tuuid");
		String duuid = request.getParameter("duuid");
		String search = request.getParameter("search");
		String title;
		String dbinfo = "{}";
		if (StringUtils.isNotEmpty(uuid)) {
			catalog = catalogService.getCatalog(uuid);
			catalog.setUpddatetime(sdf.format(new Date()));
			dbinfo = catalogService.getDBDetailInfo(catalog.getDbId());
			title = "修改数据接口";
		} else {
			title = "新建数据接口";
			catalog.setUpddatetime(sdf.format(new Date()));
			catalog.setSpecifySystem("N");
			if(superuser){
				catalog.setDeleted(Constants.UNDELETED);
			}else{
				catalog.setDeleted(Constants.AUDITING);
			}

		}
		ServerCatalogTemplate template = catalogTemplateService.getTemplate(catalog.getTemplateId());
		List<ServerCatalogTemplate> sctlist;
		if(template != null)
		{
			sctlist = catalogService.getPublishedTemplatesByTypeUuid(template.getTypeuuid());
		}
		else
		{
			sctlist = new ArrayList<>();
		}
		model.addObject("duuid",duuid);
		model.addObject("tuuid",tuuid);
		model.addObject("search",search);
		model.addObject("dbinfo",dbinfo);
		model.addObject("title", title);
		model.addObject("catalog", catalog);
		model.addObject("mesg", mesg);
		model.addObject("business", bslist);
		model.addObject("status", status);
		model.addObject("color", color);
		model.addObject("sctlist", sctlist);
		model.addObject("pubtype", pubtype);
		model.addObject("pint", CatalogService.PUB_INT);
		model.addObject("dbsources", databasePublishService.getDBInfos());
		model.addObject("tpltypes", catalogTemplateService.getTypes());
		return model;
	}

	@RequestMapping("/save.do")
	public RedirectView save(MultipartFile file, HttpServletRequest request,
			ServerCatalog catalog, RedirectAttributes attr) {
		RedirectView model = new RedirectView("edit.do");
			if ("N".equals(catalog.getSpecifySystem())) {
				catalog.setSpecifySystemIdList("");
			}
			String dbinfo = request.getParameter("dbinfo_ok");
			String duuid = request.getParameter("duuid");
			String tuuid = request.getParameter("tuuid");
			String search = request.getParameter("search");
            String dbid = catalogService.saveDBInfo(dbinfo,catalog.getDbId());
            catalog.setDbId(dbid);
			if (catalog.getId() != 0) {
				catalogService.updateCatalog(catalog, file, request);
			} else {
				catalogService.saveCatalog(catalog, file, request);
			}
			attr.addAttribute("mesg", "保存成功！");
			attr.addAttribute("color", Constants.TIP_SUCCESS);
			attr.addAttribute("uuid", catalog.getId());
			attr.addAttribute("duuid", duuid);
			attr.addAttribute("tuuid", tuuid);
			attr.addAttribute("search", search);
		return model;
	}

	@RequestMapping("/saveexcel.do")
	public RedirectView saveExcel(HttpServletRequest request, String ifjson)
	{
		JSONObject obj = JSONObject.parseObject(ifjson);
		catalogService.batchSaveTemplate(sessionManager.getSessionAttr(request,Constants.USERID)
				,request.getParameter("typeuuid"),obj);
		return new RedirectView("tlist.do?tuuid="+request.getParameter("tuuid"));
	}

	@RequestMapping("/delete.do")
	public RedirectView delete(HttpServletRequest request,
			RedirectAttributes attr) {
		RedirectView model = new RedirectView("list.do");
		catalogService.deleteCatalog(request.getParameter("uuid"));
		attr.addAttribute(Constants.CURRENT_PAGE,
				request.getParameter(Constants.CURRENT_PAGE));
		attr.addAttribute("tuuid",request.getParameter("tuuid"));
		attr.addAttribute("duuid",request.getParameter("duuid"));
		attr.addAttribute("search",request.getParameter("search"));
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/getlist.do", produces = "application/json; charset=utf-8")
	public String getList() {
		Map map = businessSystemService.getBusinessSystemIdNameMap(true);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "/checkrepeat.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checkRepeat(HttpServletRequest request) {
		return catalogService.checkRepeated(request.getParameter("id"),
				request.getParameter("name"), request.getParameter("sht"));
	}

	@RequestMapping("/getdoc.do")
	public ResponseEntity<byte[]> getdoc(HttpServletRequest request) {
		String id = request.getParameter("id");
		Map result = catalogService.getDoc(id);
		byte[] f = (byte[]) result.get("b");
		HttpHeaders headers = new HttpHeaders();
		if (f == null) {
			headers.setContentType(MediaType.TEXT_PLAIN);
			return new ResponseEntity<byte[]>("没有找到文档".getBytes(), headers,
					HttpStatus.OK);
		}
		try {
			String filename = (String) result.get("f");
			String fileUTF8Name = new String(filename.getBytes("UTF-8"),
					"iso-8859-1");
			headers.setContentDispositionFormData("attachment", fileUTF8Name);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(f, headers, HttpStatus.OK);
		} catch (Exception ex) {
			log.warn("下载接口{}文档错误!{}", id, ex);
		}
		return new ResponseEntity<byte[]>("没有找到文档".getBytes(), headers,
				HttpStatus.OK);
	}

	@RequestMapping("/gethelpdoc.do")
	public ResponseEntity<byte[]> gethelpdoc(HttpServletRequest request) {
		String type = request.getParameter("type");
		Map result = catalogService.getHelpDoc(type);
		byte[] f = (byte[]) result.get("b");
		HttpHeaders headers = new HttpHeaders();
		if (f == null) {
			headers.setContentType(MediaType.TEXT_PLAIN);
			return new ResponseEntity<byte[]>("没有找到文档".getBytes(), headers,
					HttpStatus.OK);
		}
		try {
			String filename = (String) result.get("f");
			String fileUTF8Name = new String(filename.getBytes("UTF-8"),
					"iso-8859-1");
			headers.setContentDispositionFormData("attachment", fileUTF8Name);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(f, headers, HttpStatus.OK);
		} catch (Exception ex) {
			log.warn("下载用户使用手册错误!{}", ex);
		}
		return new ResponseEntity<byte[]>("没有找到文档".getBytes(), headers,
				HttpStatus.OK);
	}

	/**
	 * 标准订阅列表
	 * 
	 * @param request
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping("/liststandard.do")
	public ModelAndView liststandard(HttpServletRequest request, PageInfo pageInfo) {
		ModelAndView model = new ModelAndView("/Normal/CatalogList/list");
		pageInfo = getPageInfo(request, pageInfo);
		Map<String, String> status = catalogService.getStatus();
		String search = request.getParameter("search");
		if (StringUtils.isNotEmpty(search)) {
			model.addObject("search", search);
			pageInfo.setUrl(pageInfo.getUrl() + "?search=" + search + "");
		}

		List<ServerCatalog> catalogs = catalogService.getCatalogsIsOrNotBasic(search, true);
		Map<Integer, String> bslist = businessSystemService.getBusinessSystemIdNameMap(false);
		String uid = sessionManager.getSessionAttr(request,Constants.USERID);
		WebRoles role = roleService.getRole(roleService.getUserRole(uid));
		pageInfo.setUrl("liststandard.do");
		String searchpath = "liststandard.do";
		model.addObject("searchpath", searchpath);
		model.addObject("pageinfo", pageInfo);
		model.addObject("readonly", true);
		model.addObject("title", "标准订阅");
		model.addObject("catalog", catalogs);
		model.addObject("status", status);
		model.addObject("bslist", bslist);
		model.addObject("normal", role.getNormalStatus());
		return model;
	}
	
	@RequestMapping(value = "/alloweditdel.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String alloweditdel(HttpServletRequest request){
		return catalogService.alloweditdel(request.getParameter("uuid"));
	}

	@RequestMapping("/audithistory")
	@ResponseBody
	@ApiOperation(value = "发布记录", httpMethod = "POST", response = WebFuncs.class, notes = "发布记录")
	public Map audithistory(@RequestBody SearchModel searchModel) {
		String searchword = searchModel.getSearch();
		List<ServerCatalog> catalogs  = catalogAuditService.getCatalogsAll(searchword);
		ReslutParent reslutParent = new ReslutParent();
		reslutParent.setContent(catalogs);
		return reslutParent.toBack();
	}
}
