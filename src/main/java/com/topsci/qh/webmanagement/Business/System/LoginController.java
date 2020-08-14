package com.topsci.qh.webmanagement.Business.System;

import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;

import com.topsci.qh.webmanagement.Resources.MongodbService;
import com.topsci.qh.webmanagement.Service.Normal.FunctionService;
import com.topsci.qh.webmanagement.Service.Normal.RoleService;
import com.topsci.qh.webmanagement.Service.System.LoginService;
import com.topsci.qh.webmanagement.Service.Normal.UserService;
import com.topsci.qh.webmanagement.Tools.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by lzw.
 * 16-6-20
 */
@Controller
@RequestMapping("/system")
@Api(value = "/system", tags = "登录")
public class LoginController extends BasicController{

    @Resource
    private RoleService roleService;
    @Resource
    private SessionManager sessionManager;
    @Resource
    private LoginService loginService;
    @Resource
    private MD5Tool md5Tool;
    @Resource
    private UserService userService;
    @Resource
    private Config config;
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private FunctionService functionService;

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    /*@RequestMapping(value = "/login.do")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,Model mod)
    {
        ModelAndView model = new ModelAndView("/System/login");
        //错误用户名重定向至登陆界面
        Map<String,Object> map = mod.asMap();
        String tip = (String)map.get(Constants.LOGIN_FLAG);
        String un = (String)map.get("un");
        model.addObject("username",un);
        if(StringUtils.isNotEmpty(tip))
        {
            sessionManager.removeSessionAttr(request,Constants.LOGIN_FLAG);
            model.addObject("tip",tip);
            return model;
        }
        //session存在用户名，直接登陆
        String userid = sessionManager.getSessionAttr(request, Constants.USERID);
        if(userid != null)
        {
            WebUsers user;
            if("0".equals(userid))
            {
                user = userService.createSuperUser();
            }
            else
            {
                user = userService.getUser(sessionManager.getSessionAttr(request, Constants.USERID));
            }
            return loginService.doLogin(request,user.getUuid(),user.getUserName(),user.getLoginName(),false,roleService.isSuperUser(userid));
        }
        //记住过用户名密码，直接登陆
        String[] cookies = loginService.remembered(request);
        if(cookies!=null)
        {
            model.addObject("username",cookies[0]);
            model.addObject("password",cookies[1]);
        }
        model.addObject("contact",config.getForget_pass());
        return model;
    }*/

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登录", httpMethod = "POST", response = String.class, notes = "登录 将获取的token 放入到 header中  key 为 token")
    public  Map<String,Object>  checkLogin(@RequestBody LoginModel loginModel)
    {
        ReslutParent reslutParent = new ReslutParent();
        Map<String,Object> map = new HashMap<String, Object>();

        String password = loginModel.getPsw();
        String username = loginModel.getName();
        String md5pass = md5Tool.getDigestPassword(password);
        boolean faultvalid = loginService.userFaultPasswordCheck(username);
        if(!faultvalid)
        {
            reslutParent.setError("账户由于输入错误被锁定！需要提前解锁请联系管理员");
            return reslutParent.toBack();
        }
        Map<String,String> result = loginService.checkUser(username,md5pass,false);
        //验证不正确返回登陆页面，验证正确session添加用户信息
        if(result==null)
        {
            loginService.userFaultPassword(username);
            reslutParent.setError("用户名、密码错误！三次错误将会锁定账户两小时");
            return reslutParent.toBack();
        }
        String token = JWTUtils.getToken(username);
        redisTemplate.opsForValue().set(token,username);
        redisTemplate.expire(token,JWTUtils.TOKEN_TIME, TimeUnit.DAYS);

        map.put("token",token);
        reslutParent.setContent(map);
        return reslutParent.toBack();
    }

    @RequestMapping("/logout")
    @ResponseBody
    @ApiOperation(value = "退出", httpMethod = "GET", response = String.class, notes = "退出")
    public Map logout(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        redisTemplate.delete(token);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("退出成功！");
        return reslutParent.toBack();
    }

    @RequestMapping("/functions")
    @ResponseBody
    @ApiOperation(value = "获取菜单权限", httpMethod = "GET", response = RoleFunctionModel.class, notes = "获取菜单权限")
    public Map func(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        String loginName = JWTUtils.getUserName(token);
        WebUsers webUsers = userService.getUserByLoginName(loginName,true);
        String roleId = webUsers.getRoleId();
        List<String> list = roleService.getAuthFuncs(roleId);
        List<WebFuncs> funcs = functionService.getAllFuncsNotHide();

        List<RoleFunctionModel> modelList = getRoleFunctionModels(list, funcs);

        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(modelList);
        return reslutParent.toBack();
    }

    private List<RoleFunctionModel> getRoleFunctionModels(List<String> list, List<WebFuncs> funcs) {
        List<WebFuncs> listParent = funcs.stream().filter(s->s.getIsParent().equals("Y")).collect(Collectors.toList());
        funcs.removeAll(listParent);
        Map<String,List<WebFuncs>> mapChild = funcs.stream().collect(Collectors.groupingBy(WebFuncs::getParentUuid));
        listParent.sort(new Comparator<WebFuncs>() {
            @Override
            public int compare(WebFuncs o1, WebFuncs o2) {
                return Integer.valueOf(o1.getSort()).compareTo(Integer.valueOf(o2.getSort()));
            }
        });
        List<RoleFunctionModel> modelList = new ArrayList<>();
        listParent.forEach(f->{
            RoleFunctionModel roleFunctionModel = new RoleFunctionModel();
            roleFunctionModel.setIsParent("Y");
            roleFunctionModel.setKey(f.getUuid());
            roleFunctionModel.setTitle(f.getName());
            if(list.contains(f.getUuid())){
                roleFunctionModel.setChecked(true);
            } else {
               return;
            }
            List<WebFuncs> funcsList = mapChild.get(f.getUuid());
            List<RoleFunctionModel> modelChildList = new ArrayList<>();
            if(funcsList == null){
                return;
            }
            funcsList.sort(new Comparator<WebFuncs>() {
                @Override
                public int compare(WebFuncs o1, WebFuncs o2) {
                    return Integer.valueOf(o1.getSort()).compareTo(Integer.valueOf(o2.getSort()));
                }
            });
            funcsList.forEach(fc->{
                RoleFunctionModel roleFunctionModelChild = new RoleFunctionModel();
                roleFunctionModelChild.setIsParent("N");
                roleFunctionModelChild.setKey(fc.getUuid());
                roleFunctionModelChild.setTitle(fc.getName());
                if(list.contains(fc.getUuid())){
                    roleFunctionModelChild.setChecked(true);
                } else {
                    roleFunctionModelChild.setChecked(false);
                }
                modelChildList.add(roleFunctionModelChild);
            });
            roleFunctionModel.setChildren(modelChildList);
            modelList.add(roleFunctionModel);
        });
        return modelList;
    }

   /* @RequestMapping("/ssologin.do")
    public RedirectView ssoLogin(HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr)
    {
        String sessionid = request.getParameter("param");
        if(StringUtils.isEmpty(sessionid))
        {
            return new RedirectView("/system/error.do");
        }
        String[] account = loginService.ssoCheck(sessionid);
        return checkLogin(request,response,account[0],account[1],"0",attr,true);
    }*/

}
