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

import com.topsci.qh.webmanagement.Pojo.GBICD9REG;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Service.Normal.GBICD9REGService;
import com.topsci.qh.webmanagement.Service.Normal.RoleService;

/**
 *     手术代码管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/gbicdReg")
public class GBICD9REGController extends BasicController implements IBasicController {

	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private GBICD9REGService gbicdService;
	
	@Resource
	private RoleService roleService;
	
	@Override
	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request, PageInfo pageInfo) {
		 ModelAndView model = new ModelAndView("/Normal/GBICD9REG/list");
		 pageInfo = getPageInfo(request, pageInfo);
		 
		 String keyWords = request.getParameter("search");
		 
		 List<GBICD9REG> regs = gbicdService.findPageList(pageInfo,keyWords);
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
		 attr.addAttribute(Constants.CURRENT_PAGE,request.getParameter(Constants.CURRENT_PAGE));
		 try {
				String regId = request.getParameter("regId");
				String status = request.getParameter("status");
				gbicdService.reviewGbicD9Reg(regId, status);
				//审批通过时向主表插入数据
				if("1".equals(status)) {
					GBICD9REG gbReg = gbicdService.getById(regId);
					gbicdService.saveGBICD9(gbReg);
				}
				result.put("ret", 0);
				result.put("msg", "审核完成");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("手术代码审批异常..");
			}
		return result;
	}
	
	@RequestMapping("/edit.do")
	@Override
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/Normal/GBICD9REG/edit");
		String id = request.getParameter("id");
		
		if(StringUtils.isNotBlank(id)) {
			model.addObject("title","修改手术代码");
			GBICD9REG gbicd9reg = gbicdService.getById(id);
			model.addObject("gbicdReg", gbicd9reg);
		}else {
			model.addObject("title", "添加手术代码");
		}
		return model;
	}
	
	@RequestMapping("/saveOrUpdate.do")
	@ResponseBody
	public Map<String,Object> saveOrUpdate(HttpServletRequest request,GBICD9REG gbicdReg) {
		Map<String,Object> result = new HashMap<>();
		
		try {
			gbicdService.saveOrUpdate(gbicdReg);
			result.put("ret", 0);
			result.put("msg", "操作成功");
		}catch(Exception e) {
			result.put("ret", 500);
			result.put("msg", "操作失败");
			e.printStackTrace();
			logger.error("添加,修改手术代码时出现异常");
		}
		
		return result;
	}

}
