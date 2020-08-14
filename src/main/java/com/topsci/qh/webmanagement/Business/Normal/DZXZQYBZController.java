package com.topsci.qh.webmanagement.Business.Normal;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.topsci.qh.webmanagement.Pojo.CV0810011REG;
import com.topsci.qh.webmanagement.Pojo.DZXZQYBZ;
import com.topsci.qh.webmanagement.Pojo.DZXZQYBZREG;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Service.Normal.DZXZQYBZService;
import com.topsci.qh.webmanagement.Service.Normal.RoleService;

/**
 *     区划管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/dzxzqyReg")
public class DZXZQYBZController extends BasicController implements IBasicController {

	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private DZXZQYBZService dzxzqyRegService;
	
	@Resource
	private RoleService roleService;
	
	@Override
	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request, PageInfo pageInfo) {
		 ModelAndView model = new ModelAndView("/Normal/DZXZQYBZREG/list");
		 pageInfo = getPageInfo(request, pageInfo);
		 
		 String keyWords = request.getParameter("search");
		 
		 List<DZXZQYBZREG> regs = dzxzqyRegService.findPageList(pageInfo,keyWords);
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
				dzxzqyRegService.reviewDzxzqybzReg(regId, status);
				//审批通过时向主表插入数据
				if("1".equals(status)) {
					DZXZQYBZREG dzxzqyReg = dzxzqyRegService.getById(regId);
					dzxzqyRegService.saveDZXZQYBZ(dzxzqyReg);
				}
				result.put("ret", 0);
				result.put("msg", "审核完成");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("区划审批异常..");
			}
		return result;
	}
	
	@RequestMapping("/edit.do")
	@Override
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/Normal/DZXZQYBZREG/edit");
		
		String id = request.getParameter("id");
		
		//获取所有区划用于做区划上级下拉框
		long start = System.currentTimeMillis();
		List<DZXZQYBZ> dzxzqys = dzxzqyRegService.findListByCondition(" and dj < 6");
		model.addObject("dzxzqys", dzxzqys);
		
		if(StringUtils.isNotBlank(id)) {
			model.addObject("title","修改区划");
			DZXZQYBZREG dzReg = dzxzqyRegService.getById(id);
			model.addObject("dzReg", dzReg);
		}else {
			model.addObject("title", "添加区划");
		}
		
		return model;
	}

	
	@RequestMapping("/saveOrUpdate.do")
	@ResponseBody
	public Map<String,Object> saveOrUpdate(HttpServletRequest request,DZXZQYBZREG dzReg,RedirectAttributes attr) {
		Map<String,Object> result = new HashMap<>();
		
		try {
			dzxzqyRegService.saveOrUpdate(dzReg);
			result.put("ret", 0);
			result.put("msg", "操作成功");
		}catch(Exception e) {
			result.put("ret", 500);
			result.put("msg", "操作失败");
			e.printStackTrace();
			logger.error("添加,修改区域时出现异常");
		}
		
		return result;
	}
	
	@RequestMapping("/synchronousXZQY.do")
	public ModelAndView synchronousXZQY() {
		ModelAndView model = new ModelAndView("/Normal/DZXZQYBZREG/sync");
		
		return model;
	}
	
	@RequestMapping("/sync.do")
	@ResponseBody
	public Map<String,Object> sync(@RequestParam String fileString){
		Map<String,Object> result = new HashMap<>();
		String [] contents = fileString.split("\r\n");
		if(contents == null || contents.length != Integer.valueOf(contents[0]) + 2) {
			result.put("ret", 500);
			result.put("msg", "请检查文件内容格式和数量是否正确.");
		}else {
			try {
				contents = (String[]) ArrayUtils.removeElement(contents,contents[0]);
				contents = (String[]) ArrayUtils.removeElement(contents,contents[0]);
				List<String> allBhs = Arrays.asList(contents).stream().map(s -> "'"+ s.split(",")[1] +"'").collect(Collectors.toList());
				List<DZXZQYBZ> dzxzqys = dzxzqyRegService.queryByBhs(allBhs);
				DZXZQYBZ temp = null;
				DZXZQYBZ parent = new DZXZQYBZ();
				for (int i =0;i<contents.length;i++) {
					String [] dzInfo = contents[i].split(",");//第一位是名称,第二位是编码
					String mc = dzInfo[0];
					String bh = dzInfo[1];
					//判断如果数据库中已经有了就修改信息 如果数据库中没有就插入信息
					for (DZXZQYBZ dz : dzxzqys) {
						if(dz == null || StringUtils.isBlank(dz.getBh())) continue;
						if(dz.getBh().equals(dzInfo[1])) {
							temp = dz;
							break;
						}
					}
					if(temp == null) {
						temp = new DZXZQYBZ();
						temp.setBh(bh);
					}
					temp.setMc(mc);
					if(bh.endsWith("0000000000")) {
						temp.setDj("2");//省级
						parent.setBh("910000000000");
					}else if(bh.endsWith("00000000")) {
						temp.setDj("3");//市级
						parent.setBh(bh.replace(bh.charAt(2),'0').replace(bh.charAt(3),'0'));//青海省
					}else if(bh.endsWith("000000")) {
						temp.setDj("4");//区,县
						parent.setBh(bh.replace(bh.charAt(4),'0').replace(bh.charAt(5),'0'));
					}else if(bh.endsWith("000")) {//乡、镇、街道办事处
						temp.setDj("5");
						parent.setBh(bh.replace(bh.charAt(6),'0').replace(bh.charAt(7),'0'));
					}
					temp.setUpdateTime(new Timestamp(new Date().getTime()));
					temp.setSj(parent);
					temp.setSourceType(3);//数据同步
					dzxzqyRegService.saveOrUpdate(temp);
					temp = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("区划信息同步失败");
				result.put("ret", 500);
				result.put("msg", "区划信息同步失败");
				return result;
			}
		}
		result.put("ret", 0);
		result.put("msg", "同步成功");
		return result;
	}
}
