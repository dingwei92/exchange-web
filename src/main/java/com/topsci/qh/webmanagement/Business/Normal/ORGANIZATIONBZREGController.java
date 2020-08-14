package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Service.Normal.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 *     机构管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/orgReg")
public class ORGANIZATIONBZREGController {

	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ORGANIZATIONBZREGService orgRegService;
	
	@Resource
	private RoleService roleService;
	
	@Autowired
	private OrganizationService orgService;
	
	@Resource
	private DZXZQYBZService xzqyService;
	
	@Resource
	private CV0810001Service cv08001Service;
	
	@Resource
	private GBT12402Service gbt12402Service;
	

	@Autowired
	private OrganizationService standardOrgService;
	
	/*@Override
	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request, PageInfo pageInfo) {
		 ModelAndView model = new ModelAndView("/Normal/ORGANIZATIONBZREG/list");
		 pageInfo = getPageInfo(request, pageInfo);
		 String keyWords = request.getParameter("search");
		 List<ORGANIZATIONBZREG> regs = orgRegService.findPageList(pageInfo,keyWords);
		 model.addObject("pageinfo", pageInfo);
		 model.addObject("orgs", regs);
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
		 attr.addAttribute(Constants.CURRENT_PAGE,request.getParameter(Constants.CURRENT_PAGE));
		 
		try {
			String indexId = request.getParameter("indexId");
			String status = request.getParameter("status");
			
			ORGANIZATIONBZREG orgReg = orgRegService.getById(Long.valueOf(indexId));
			ORGANIZATIONBZREG parent = orgRegService.getById(Long.valueOf(orgReg.getParent().getOrgId()));
			if(parent == null) {
				Organizationbz standardParent = standardOrgService.findByOrgId(orgReg.getParent().getOrgId());
				if(standardParent == null) {
					result.put("ret", 500);
					result.put("msg", "您选择的父级机构无效,请重新选择!");
					return result;
				}
			}
			orgRegService.reviewOrgReg(indexId, status);
			
			//审批通过时向主表插入数据
			if("1".equals(status)) {
				orgRegService.saveORGANIZATIONBZ(orgReg);
			}
			result.put("ret", 0);
			result.put("msg", "审核完成");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("机构审批异常..");
		}
		return result;
	}
	
	@RequestMapping("/edit.do")
	@Override
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/Normal/ORGANIZATIONBZREG/edit");
		String indexId = request.getParameter("indexId");
		//查询所有机构 用作表单页面的上级机构下拉框
		List<Organizationbz> orgList = orgService.findAll();
		model.addObject("orgs",orgList);
		//查询行政区域
		List<DZXZQYBZ> xzqyList = xzqyService.findListByCondition(null);
		
		//机构类型
		List<CV0810001> cv0810001s = cv08001Service.findAll();
		model.addObject("orgTypes",cv0810001s);
		
		//经济类型
		List<GBT12402> gbt12402s = gbt12402Service.findAll();
		model.addObject("ecoTypes", gbt12402s);
		
		//医疗机构类别
		List<HospitalsType> hospticalTypes = hospticalTypeService.findAll();
		model.addObject("hospticalTypes",hospticalTypes);
		
		//所属县 level=4
		List<DZXZQYBZ> countryList = xzqyList.stream().filter(c -> {
			if ("4".equals(c.getDj()))
				return true;
			return false;
		}).collect(Collectors.toList());
		model.addObject("countryList",countryList);
		
		//所属镇 level=5
		List<DZXZQYBZ> townList = xzqyList.stream().filter(c -> {
			if ("5".equals(c.getDj()))
				return true;
			return false;
		}).collect(Collectors.toList());
		model.addObject("townList",townList);
		
		//所属村 level=6
		List<DZXZQYBZ> villageList = xzqyList.stream().filter(c -> {
			if ("6".equals(c.getDj()))
				return true;
			return false;
		}).collect(Collectors.toList());
		model.addObject("villageList",villageList);
		
		if(StringUtils.isNotBlank(indexId)) {
			model.addObject("title", "修改机构");
			model.addObject("orgReg", orgRegService.getById(Long.valueOf(indexId)));
		}else {
			model.addObject("title", "添加机构");
		}
		return model;
	}
	
	@RequestMapping("/saveOrUpdate.do")
	@ResponseBody
	public Map<String,Object> saveOrUpdate(HttpServletRequest request,ORGANIZATIONBZREG orgReg,RedirectAttributes attr) {
		Map<String,Object> result = new HashMap<>();
		
		try {
			orgRegService.saveOrUpdate(orgReg);
			result.put("ret", 0);
			result.put("msg", "操作成功");
		}catch(Exception e) {
			result.put("ret", 500);
			result.put("msg", "操作失败");
			e.printStackTrace();
			logger.error("添加,修改机构信息时出现异常");
		}
		
		return result;
	}*/

}
