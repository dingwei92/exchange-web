package com.topsci.qh.webmanagement.Business.Normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Resources.PageInfo;

import com.topsci.qh.webmanagement.Service.Normal.*;
import com.topsci.qh.webmanagement.Tools.Config;
import com.topsci.qh.webmanagement.Tools.DateUtil;
import com.topsci.qh.webmanagement.Tools.ReslutParent;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lzw.
 * 16-7-23
 */
@Controller
@RequestMapping("/subscribe")
public class SubscribeApplyController extends BasicController {
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private BusinessSystemService businessSystemService;
    @Resource
    private CatalogService catalogService;
    @Resource
    private SubscribeApplyService applyService;
    @Resource
    private UserService userService;
    @Resource
    private RelUserBusinessService relUserBusinessService;
    @Resource
    private ServerHistoryApplyService serverHistoryApplyService;
    @Resource
    private Config config;




    @RequestMapping("/apply.do")
    public ModelAndView edit(HttpServletRequest request)
    {
        ModelAndView model = new ModelAndView("/Normal/SubscribeApply/apply");
        ServerSubscribes scribe = new ServerSubscribes();
        String title = "申请订阅";
        String lists = "";
        scribe.setDeleted(subscribeService.APPLY);
        scribe.setEnables(subscribeService.DISABLED);
        scribe.setUpddatetime(StardTime.format(new Date()));
        ServerSubscribeApply apply = new ServerSubscribeApply();
        String duuid = request.getParameter("duuid");
        String tuuid = request.getParameter("tuuid");
        String search = request.getParameter("search");
        String readonly = request.getParameter("readonly");
        Map<Integer, String> catalogs = catalogService.getCatalogIdNameMap(true);
        boolean superuser = (boolean) sessionManager.getSessionAttrObj(request, Constants.SUPERUSER);
        Map<Integer, String> business = new HashMap<>();
        if(superuser){
        	business = businessSystemService.getBusinessSystemIdNameMap(true);
        }else{
        	int bid = relUserBusinessService.getBusinessID(sessionManager.getSessionAttr(request,Constants.USERID));
        	BusinessSystem busi = businessSystemService.getBusinessSystem(bid);
        	business.put(busi.getId(), busi.getSystemName()+" ("+busi.getSystemShort()+")");
        }
        
        Map<String, String> status = subscribeService.getStatus();
        ServerCatalog catalog;
        model.addObject("basedata",false);
        if(StringUtils.isNotEmpty(request.getParameter("cid"))) {
            scribe.setServerCatalogId(Integer.parseInt(request.getParameter("cid")));
        }
        catalog = new ServerCatalog();
        catalog.setId(0);
        model.addObject("readonly",readonly);
        model.addObject("duuid",duuid);
        model.addObject("tuuid",tuuid);
        model.addObject("search",search);
        model.addObject("catalog",catalog);
        model.addObject("lists", lists);
        model.addObject("title", title);
        model.addObject("sb", scribe);
        model.addObject("apply", apply);
        model.addObject("status", status);
        model.addObject("catalogs", catalogs);
        model.addObject("business", business);
        model.addObject("condition", "");
        return model;
    }


    @RequestMapping("/viewapply.do")
    public ModelAndView view(HttpServletRequest request)
    {
        ModelAndView model = new ModelAndView("/Normal/SubscribeApply/view");
        String id = request.getParameter("uuid");

        String title = "审核订阅";

        ServerSubscribes scribe = subscribeService.getScribe(Integer.parseInt(id));
        ServerSubscribeApply apply = applyService.getApplyBySubscribe(scribe.getId());
        ServerCatalog catalog = catalogService.getCatalog(scribe.getServerCatalogId() + "");
        BusinessSystem system = businessSystemService.getBusinessSystem(scribe.getBusinessSystemId());
        
        if (scribe.getParameter() !=null && !"".equals(scribe.getParameter())) {
        	scribe.setParameter(scribe.getParameter().replace("<", "&lt").replace(">", "&gt").replace("&gt&lt", "&gt</br>&lt")); //页面格式
		}

        Map<String, String> status = subscribeService.getStatus();
        String lists = subscribeService.getScribeListsComma(scribe.getId(), request);
        model.addObject("lists",lists);
        model.addObject("title", title);
        model.addObject("bid", system.getId());
        model.addObject("cid", catalog.getId());
        model.addObject("bsname",system.getSystemName()+"("+system.getSystemShort()+")" );
        model.addObject("apply", apply);
        model.addObject("scribe", scribe);
        model.addObject("clname",catalog.getServerName()+"("+catalog.getServerShort()+")");
        model.addObject("status", status);
        return model;
    }


    
    /**
     * 历史数据订阅
     * @param request
     * @return
     */
    @RequestMapping("/historyapply.do")
    public ModelAndView historyApply(HttpServletRequest request)
    {
        ModelAndView model = new ModelAndView("/Normal/SubscribeApply/history");
        ServerSubscribes scribe = new ServerSubscribes();
        String title = "申请订阅历史数据";
        String lists = "";
        Map<Integer, String> business = null;
        List<ServerSubscribes> ssList = null;
        Map<Integer, String> catalogs = null;
        
        
        //获取到用户的uuid
        String userUUID = sessionManager.getSessionAttr(request,Constants.USERID);
        Integer businessSystemId = null;
        
        //获取对应的业务id
        RelUserBusiness	relUser = relUserBusinessService.getRelUserBusinessByUserId(userUUID);
        if (relUser != null) { //普通用户
        	businessSystemId = relUser.getBusinessId();
        	//获取可订阅服务的id集合 
            if (businessSystemId != null && businessSystemId != 0) {
            	business = businessSystemService.getBusinessSystemIdNameMapBayId(businessSystemId);
            	//通过业务id，查找出已经订阅的所有服务
            	ssList = subscribeService.getScribesList(businessSystemId);
    		}
            
            //通过获取可订阅的业务名称map集合
            if (ssList != null) {
    			String ids = "";
            	for (ServerSubscribes serverSubscribes : ssList) {
            		ids += serverSubscribes.getServerCatalogId()+",";
    			}
            	catalogs = catalogService.getCatalogIdNameMapById(ids);
    		}
		}else { //管理员
			business = businessSystemService.getBusinessSystemIdNameMap(false);
			catalogs = new HashMap<Integer, String>();
		}
        scribe.setEnables(subscribeService.DISABLED);
        scribe.setDeleted(subscribeService.APPLY);
        scribe.setUpddatetime(StardTime.format(new Date()));
        ServerSubscribeApply apply = new ServerSubscribeApply();
        Map<String, String> status = subscribeService.getStatus();
        ServerCatalog catalog = new ServerCatalog();
        catalog.setId(0);
        
        model.addObject("basedata",false);
        model.addObject("readonly",false);
        model.addObject("catalog",catalog);
        model.addObject("lists", lists);
        model.addObject("title", title);
        model.addObject("sb", scribe);
        model.addObject("apply", apply);
        model.addObject("status", status);
        model.addObject("catalogs", catalogs);
        model.addObject("business", business);
        model.addObject("mesg",request.getParameter("mesg"));
        model.addObject("color",request.getParameter("color"));
        return model;
    }
    
    @RequestMapping("/listhistoryapply.do")
    public ModelAndView listHistoryApply(HttpServletRequest request, PageInfo pageInfo) {
    	
    	//获取到用户的uuid
        String userUUID = sessionManager.getSessionAttr(request,Constants.USERID);
        
    	ModelAndView model = new ModelAndView("/Normal/SubscribeApply/historylist");
        pageInfo = getPageInfo(request, pageInfo);
        final String searchword = request.getParameter("search");
        pageInfo.setUrl("listhistoryapply.do");
        if (StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl() + "?search=" + searchword + "");
        }
        
        Map<String,Object> result = serverHistoryApplyService.getServerHistoryApplys(pageInfo, request,searchword,userUUID);
        Map<String,ServerSubscribes> scribes = (Map<String,ServerSubscribes>)result.get("scribes");
        List<ServerHistoryApply> applies = (List<ServerHistoryApply>)result.get("applies");
        
        Map<String, String> status = new HashMap<String, String>(){{
        	//N:未请求，Y已请求，E失败的
        	put("N", "未请求");
        	put("Y", "已请求");
        	put("E", "失败");
        }};
        
        Map<Integer, String> catalogs = catalogService.getCatalogIdNameMap(false);
        Map<Integer, String> business = businessSystemService.getBusinessSystemIdNameMap(false);
        Map<String, String> users = userService.getUsersMap(false);
        boolean superuser = (boolean) sessionManager.getSessionAttrObj(request, Constants.SUPERUSER);
        String searchpath = "listhistoryapply.do";
        model.addObject("title","申请历史数据列表");
        model.addObject("searchpath",searchpath);
        model.addObject("history",true);
        model.addObject("superuser", superuser);
        model.addObject("status", status);
        model.addObject("scribes", scribes);
        model.addObject("applies", applies);
        model.addObject("users", users);
        model.addObject("pageinfo", pageInfo);
        model.addObject("catalog", catalogs);
        model.addObject("business", business);
        return model;
    }
    
    @RequestMapping("/savehistoryapply.do")
    public RedirectView saveHistoryapply(HttpServletRequest request,ServerSubscribes subscribe,RedirectAttributes attr)
    {
    	 RedirectView model = new RedirectView("historyapply.do");
        	int businessSystemId = subscribe.getBusinessSystemId();
        	int serverCatalogId = subscribe.getServerCatalogId();
        	int ssid = subscribeService.getServerSubscribesid(serverCatalogId, businessSystemId);
        	String userUUID = sessionManager.getSessionAttr(request,Constants.USERID);
        	
        	String parameter = null;
//        	String startTime = subscribe.getStartTime();
//        	String endTime = subscribe.getEndTime();
        	String field_result = request.getParameter("field_result"); //条件集合
        	String params = request.getParameter("params"); //条件集合

        	StringBuilder sb = new StringBuilder();
//            if (startTime != null && endTime!=null && !",".equals(startTime) && !",".equals(endTime)) { //拼接请求参数
//            	sb.append("<StartDate>"+startTime.replace(",", "")+"</StartDate>");
//            	sb.append("<EndDate>"+endTime.replace(",", "")+"</EndDate>");
//            }
            if (StringUtils.isNotEmpty(field_result)  && !"\"{'count':'0'}\"".equals(field_result)) {
            	//{"0":{"name":"ADRESS","desc":"63000000000","id":"0"},"count":"1"}
            	JSONObject json = (JSONObject) JSON.parse(field_result);
            	int count = Integer.parseInt((String) json.get("count"));
            	if (count > 0) {
            		 for (int i = 0; i < count; i++) {
                         JSONObject subjson = (JSONObject) json.get(i + "");
                         String name = subjson.getString("name");
                         String desc = subjson.getString("desc");
                         sb.append("<"+name.replace(" ", "")+">"+desc.replace(" ", "")+"</"+name.replace(" ", "")+">");
            		 }    
            	}
            }
            sb.append(params);
            
            parameter = sb.toString();
            
            if (ssid != 0 && userUUID != null &&!"".equals(userUUID)) { //封装一个保存参数类
//            	String startDate = startTime!=null&&!",".equals(startTime)?startTime.substring(1,11):null;
//            	String endDate = endTime!=null&&!",".equals(endTime)?endTime.substring(1,11):null;
//            	boolean b = serverHistoryApplyService.hasServerHistoryApply(userUUID, ssid, null, null);
            	boolean b = false;
            	if (!b) { //不存在
            		ServerHistoryApply serverHistoryApply = new ServerHistoryApply();
                	serverHistoryApply.setWebUserId(userUUID);
                	serverHistoryApply.setServerSubscribesId(ssid+"");
                	serverHistoryApply.setParameter(parameter);
                	serverHistoryApply.setStatus("N");
                	serverHistoryApply.setCreateTime(DateUtil.DateToString(new Date()));
//                	serverHistoryApply.setStartDate(startDate);
//                	serverHistoryApply.setEndDate(endDate);
                	serverHistoryApplyService.saveServerHistoryApply(serverHistoryApply);
                	
                	attr.addAttribute("mesg", "提交成功！");
                    attr.addAttribute("color", Constants.TIP_SUCCESS);
    			}else {
    				attr.addAttribute("mesg", "已存在相同订阅条件，请不要重复订阅！");
                    attr.addAttribute("color", Constants.TIP_ERROR);
    			}
			}else {
				attr.addAttribute("mesg", "提交失败！");
                attr.addAttribute("color", Constants.TIP_ERROR);
			}
            	
        return model;
    }
}
