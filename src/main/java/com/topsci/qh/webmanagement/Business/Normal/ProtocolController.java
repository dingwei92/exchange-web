package com.topsci.qh.webmanagement.Business.Normal;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.topsci.qh.webmanagement.Pojo.UploadFiles;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;

import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Service.Normal.ProtocolService;
import com.topsci.qh.webmanagement.Service.Normal.UserService;
import com.topsci.qh.webmanagement.Tools.Config;

/**
 * @ClassName: ProtocolController  
 * @Description: TODO 系统协议controller
 * @author tgeng
 * @date 2016-12-22 上午11:32:06  
 *
 */
@Controller
@RequestMapping("/aboutsystem")
public class ProtocolController extends BasicController implements IBasicController {
	private Logger log = LoggerFactory.getLogger(ProtocolController.class);

	@Resource
	private ProtocolService protocolService;
	@Resource
	private UserService userService;
	@Resource
	private Config config;
	
	@Override
	public ModelAndView list(HttpServletRequest request,PageInfo pageInfo) {
		return null;
	}

	@RequestMapping("/protocol.do")
	public ModelAndView protocol(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/Normal/AboutSystem/protocol");
		boolean superuser = (boolean) sessionManager.getSessionAttrObj(request, Constants.SUPERUSER);
		List<UploadFiles> protocols;
		protocols = protocolService.getProtocols();
		Map<String,String> users = userService.getUsersMap(false);
		users.put(Constants.INIT_ID, config.getInit_username());
		model.addObject("users", users);
		model.addObject("protocols", protocols);
		model.addObject("superuser", superuser);
		return model;
	}

	@RequestMapping("/upload.do")
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/Normal/AboutSystem/upload");
		String mesg = request.getParameter("mesg");
		String color = request.getParameter("color");
		String title = "上传系统协议";
		model.addObject("title", title);
		model.addObject("mesg", mesg);
		model.addObject("color", color);
		return model;
	}

	@RequestMapping("/save.do")
	public RedirectView save(MultipartFile file, HttpServletRequest request, UploadFiles protocol, RedirectAttributes attr) {
		RedirectView model = new RedirectView("protocol.do");
		if (protocol != null) {
			protocol.setFileType(UploadFiles.Protocol);
			protocol.setUploadUserId((String)sessionManager.getSessionAttrObj(request, Constants.USERID));
			protocolService.saveProtocol(protocol, file, request);
			attr.addAttribute("mesg", "保存成功！");
			attr.addAttribute("color", Constants.TIP_SUCCESS);
		}
		return model;
	}

	@RequestMapping("/delete.do")
	public RedirectView delete(HttpServletRequest request,RedirectAttributes attr) {
		RedirectView model = new RedirectView("protocol.do");
		protocolService.deleteProtocol(request.getParameter("uuid"), request);
		return model;
	}

	@RequestMapping(value = "/checkrepeat.do", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String checkRepeat(HttpServletRequest request) {
		return protocolService.checkRepeated(request.getParameter("uuid"),request.getParameter("docName"));
	}

	@RequestMapping("/getdoc.do")
	public ResponseEntity<byte[]> getdoc(HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		Map result = protocolService.getDoc(uuid);
		byte[] f = (byte[]) result.get("b");
		HttpHeaders headers = new HttpHeaders();
		byte[] tips = new byte[0];
		try
		{
			tips = "没有找到文档".getBytes("UTF-8");
		}
		catch (Exception ex)
		{
			log.error("下载系统文档错误",ex);
		}
		if (f == null) {
			headers.setContentType(MediaType.TEXT_PLAIN);
			return new ResponseEntity<byte[]>(tips, headers,
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
			log.warn("下载协议{}文档错误!{}", uuid, ex);
		}
		return new ResponseEntity<byte[]>(tips, headers,
				HttpStatus.OK);
	}
}
