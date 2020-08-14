package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Pojo.WebMsg;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.PageInfo;

import com.topsci.qh.webmanagement.Service.Normal.MsgService;
import com.topsci.qh.webmanagement.Service.Normal.UserService;
import com.topsci.qh.webmanagement.Tools.Config;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/msgsend")
public class MsgController extends BasicController{
	
	@Resource
    private MsgService msgService;
	@Resource
    private UserService userService;
	@Resource
    private Config config;
	
	private static String ISNOT_DETECTION = "N";

    private Logger log = LoggerFactory.getLogger(MsgController.class);
    
    @RequestMapping("/msglist.do")
    public ModelAndView msglist(HttpServletRequest request,PageInfo pageInfo)
    {
        ModelAndView model = new ModelAndView("/Normal/MsgSend/list");
        pageInfo = getPageInfo(request,pageInfo);
        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl("msglist.do?search="+searchword+"");
        }else{
        	pageInfo.setUrl("msglist.do?");
        }
        Map<String,String> users = userService.getUsersMap(false);
        users.put(Constants.INIT_ID, config.getInit_username());
        List<WebMsg> msgs = msgService.getMsgByPage(pageInfo, searchword);
        
        model.addObject("users",users);
        model.addObject("msgs",msgs);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/msg.do")
    public ModelAndView msg(HttpServletRequest request)
    {
        ModelAndView model = new ModelAndView("/Normal/MsgSend/msg");
        String mesg = request.getParameter("mesg");
        String color = request.getParameter("color");
        String id = request.getParameter("uuid");
        if(StringUtils.isEmpty(id))
        {
            id = "0";
        }
        model.addObject("msg",msgService.getMsg(id));
        model.addObject("mesg",mesg);
        model.addObject("color",color);
        return model;
    }
    
    @RequestMapping("/save.do")
    public RedirectView save(HttpServletRequest request,WebMsg msg,RedirectAttributes attr){
        RedirectView model = new RedirectView("msg.do");
        if(msg != null)
        {
        	msg.setCreateTime(new Timestamp(new Date().getTime()));
        	msg.setCreateUserId((String)sessionManager.getSessionAttrObj(request, Constants.USERID));
        	msg.setStatus(ISNOT_DETECTION);
        	msgService.saveMsg(msg,request);
                
            attr.addAttribute("mesg","消息发布成功！");
            attr.addAttribute("color",Constants.TIP_SUCCESS);
            attr.addAttribute("uuid",msg.getId()+"");
        }
        return model;
    }

    @RequestMapping("/delete.do")
    public RedirectView delete(HttpServletRequest request,RedirectAttributes attr)
    {
        RedirectView model = new RedirectView("msglist.do");
        String uuid = request.getParameter("uuid");
        msgService.deleteMsg(uuid,request);
        attr.addAttribute(Constants.CURRENT_PAGE,request.getParameter(Constants.CURRENT_PAGE));
        return model;
    }
    
    @RequestMapping(value = "/checkrepeat.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkRepeat(HttpServletRequest request)
    {
        return msgService.isRepeat(request.getParameter("content"));
    }
}
