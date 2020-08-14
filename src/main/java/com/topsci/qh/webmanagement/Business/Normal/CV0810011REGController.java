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
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Service.Normal.CV0810011REGService;
import com.topsci.qh.webmanagement.Service.Normal.RoleService;

/**
 *     科室管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/cvReg")
public class CV0810011REGController extends BasicController implements IBasicController {

	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CV0810011REGService cvRegService;
	
	@Resource
	private RoleService roleService;
	
	@Override
	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request, PageInfo pageInfo) {
		 ModelAndView model = new ModelAndView("/Normal/CV0810011REG/list");
		 pageInfo = getPageInfo(request, pageInfo);
		 
		 String keyWords = request.getParameter("search");
		 
		 List<CV0810011REG> regs = cvRegService.findPageList(pageInfo,keyWords);
		 model.addObject("pageinfo", pageInfo);
		 model.addObject("regs", regs);
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
			String regId = request.getParameter("regId");
			String status = request.getParameter("status");
			cvRegService.reviewCvReg(regId,status);
			//如果审核通过了需要向主表插入数据
			if("1".equals(status)) {
				CV0810011REG model = cvRegService.getById(regId);
				cvRegService.saveCV0810011(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("科室审核异常..");
		}
		result.put("ret", 0);
		result.put("msg", "审核完成");
		return result;
	}
	
	@RequestMapping("/saveOrUpdate.do")
	@ResponseBody
	public Map<String,Object> saveOrUpdate(HttpServletRequest request,CV0810011REG cvReg,RedirectAttributes attr) {
		Map<String,Object> result = new HashMap<>();
		
		try {
			cvRegService.saveOrUpdate(cvReg);
			result.put("ret", 0);
			result.put("msg", "操作成功");
		}catch(Exception e) {
			result.put("ret", 500);
			result.put("msg", "操作失败");
			e.printStackTrace();
			logger.error("添加,修改科室时出现异常");
		}
		
		return result;
	}
	
	@Override
	@RequestMapping("/edit.do")
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/Normal/CV0810011REG/edit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)) {
			model.addObject("title", "修改科室");
			CV0810011REG cvReg = cvRegService.getById(id);
			model.addObject("cvReg",cvReg);
		}else {
			model.addObject("title", "添加科室");
		}
		return model;
	}

}
