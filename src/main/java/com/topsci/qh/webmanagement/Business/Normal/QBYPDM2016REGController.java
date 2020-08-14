package com.topsci.qh.webmanagement.Business.Normal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.topsci.qh.webmanagement.Pojo.CV0810011REG;
import com.topsci.qh.webmanagement.Pojo.QBYPDM2016REG;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Service.Normal.CV0810011REGService;
import com.topsci.qh.webmanagement.Service.Normal.QBYPDM2016REGService;
import com.topsci.qh.webmanagement.Service.Normal.RoleService;

/**
 * 药品代码
 * @author chenkaixin
 *
 */
@Controller
@RequestMapping("/ypReg")
public class QBYPDM2016REGController extends BasicController implements IBasicController {

	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private QBYPDM2016REGService ypRegService;
	
	@Resource
	private RoleService roleService;
	
	@Override
	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request, PageInfo pageInfo) {
		 ModelAndView model = new ModelAndView("/Normal/QBYPDM2016REG/list");
		 pageInfo = getPageInfo(request, pageInfo);
		 
		 String keyWords = request.getParameter("search");
		 
		 List<QBYPDM2016REG> regs = ypRegService.findPageList(pageInfo,keyWords);
		 model.addObject("pageinfo", pageInfo);
		 model.addObject("ypdms", regs);
		return model;
	}

	@RequestMapping("/review.do")
	@ResponseBody
	public Map<String,Object> review(HttpServletRequest request,RedirectAttributes attr) {
		Map<String,Object> result = new HashMap<>();
		//判断当前登录用户是否属于管理员角色 有此角色才可以审核
		String userUUId = sessionManager.getSessionAttr(request, Constants.USERID);
		String roleUUId = roleService.getUserRole(userUUId);
		
		if(null == roleUUId || !Constants.REVIEW_ROLE_UUID.equalsIgnoreCase(roleUUId) ) {
			result.put("ret", 500);
			result.put("msg", "您当前登录的用户没有审批权限");
			return result;
		}
		
		try {
			String ypId = request.getParameter("ypId");
			String status = request.getParameter("status");
			ypRegService.reviewYpReg(ypId,status);
			//如果审核通过了需要向主表插入数据
			if("1".equals(status)) {
				QBYPDM2016REG model = ypRegService.getById(ypId);
				ypRegService.saveQBYPDM2016(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("药品代码审核异常..");
		}
		result.put("ret", 0);
		result.put("msg", "审核完成");
		return result;
	}
	
	@RequestMapping("/saveOrUpdate.do")
	@ResponseBody
	public Map<String,Object> saveOrUpdate(HttpServletRequest request,QBYPDM2016REG ypReg,RedirectAttributes attr) {
		Map<String,Object> result = new HashMap<>();
		
		try {
			ypRegService.saveOrUpdate(ypReg);
			result.put("ret", 0);
			result.put("msg", "操作成功");
		}catch(Exception e) {
			result.put("ret", 500);
			result.put("msg", "操作失败");
			e.printStackTrace();
			logger.error("添加,修改药品代码时出现异常");
		}
		
		return result;
	}
	
	@Override
	@RequestMapping("/edit.do")
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/Normal/QBYPDM2016REG/edit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)) {
			model.addObject("title", "修改药品代码");
			QBYPDM2016REG ypReg = ypRegService.getById(id);
			model.addObject("ypReg",ypReg);
		}else {
			model.addObject("title", "添加药品代码");
		}
		return model;
	}

}
