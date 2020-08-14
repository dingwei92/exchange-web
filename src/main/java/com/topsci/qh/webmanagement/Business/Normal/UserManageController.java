package com.topsci.qh.webmanagement.Business.Normal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.Constants;

import com.topsci.qh.webmanagement.Service.Normal.*;
import com.topsci.qh.webmanagement.Service.System.LoginService;
import com.topsci.qh.webmanagement.Tools.MD5Tool;
import com.topsci.qh.webmanagement.Tools.ReslutParent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by lzw.
 * 16-6-22
 */
@Controller
@Api(value = "/user", tags = "人员管理")
@RequestMapping("/user")
public class UserManageController{
    private Logger log = LoggerFactory.getLogger(UserManageController.class);

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private BusinessSystemService businessSystemService;
    @Resource
    private RelUserBusinessService relUserBusinessService;
    @Resource
    private MD5Tool MD5Tool;
    @Resource
    private LoginService loginService;
/*

    @RequestMapping("/list")
    @ResponseBody
    @ApiOperation(value = "用户业务系统关系", httpMethod = "GET", response = WebUsers.class, notes = "用户业务系统关系")
    public Map<String,Object> relUserBusiness() {

        businessSystemService.getBusinessSystemIdNameMapBayId();
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(users);
        return reslutParent.toBack();
    }
*/

    @RequestMapping("/list")
    @ResponseBody
    @ApiOperation(value = "用户列表", httpMethod = "POST", response = WebUsers.class, notes = "用户列表")
    public Map<String,Object> list(@RequestBody SearchModel searchModel) {
        String searchword = searchModel.getSearch();
        List<WebUsers> users = userService.getUsersSearchWord(searchword);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(users);
        return reslutParent.toBack();
    }


    @RequestMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "删除", httpMethod = "POST", response = String.class, notes = "删除")
    public Map<String,Object> delete(@RequestBody IDModel idModel) {
        String uuid = idModel.getId();
        userService.deleteUser(uuid);
        relUserBusinessService.deleteByUserId(uuid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("ok");
        return reslutParent.toBack();
    }

    @RequestMapping("/activate")
    @ResponseBody
    @ApiOperation(value = "启用禁用", httpMethod = "POST", response = String.class, notes = "激活")
    public Map<String,Object> checkActivate(@RequestBody IDModel idModel) {
        String uuid = idModel.getId();
        userService.activateUser(uuid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("ok");
        return reslutParent.toBack();
    }
    @RequestMapping("/unlockfaultpass")
    @ResponseBody
    @ApiOperation(value = "解除锁定", httpMethod = "POST", response = String.class, notes = "解除锁定")
    public Map unlockfaultpass(@RequestBody IDModel idModel) {
        String uuid = idModel.getId();
        loginService.unlockFaultPassword(uuid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("ok");
        return reslutParent.toBack();
    }
    @RequestMapping("/get")
    @ResponseBody
    @ApiOperation(value = "获取用户", httpMethod = "POST", response = WebUsers.class, notes = "获取用户")
    public Map<String,Object> edit(@RequestBody IDModel idModel) {
        String uuid = idModel.getId();
        WebUsers user = userService.getUser(uuid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(user);
        return reslutParent.toBack();
    }

    @RequestMapping("/save")
    @ResponseBody
    @ApiOperation(value = "用户保存", httpMethod = "POST", response = String.class, notes = "用户保存")
    public Map<String,Object> save(@RequestBody WebUsers user){
        int userBusi = user.getBusinessId();
        String userRole = user.getRoleId();

        WebRoles webRoles = this.getUserRoles().get(userRole);
        user.setRoleName(webRoles.getRoleName());
        user.setRoleId(webRoles.getUuid());

        BusinessSystem businessSystem = this.getUserBusiness().get(userBusi);
        String busName = businessSystem.getSystemName()+" ("+businessSystem.getSystemShort()+")";
        user.setBusinessName(busName);
        user.setBusinessId(userBusi);

        if(StringUtils.isEmpty(user.getUuid()))
            {
                user.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
                user.setPasswd(MD5Tool.getDigestPassword(user.getPasswd()));
                user.setCreateTime(LocalDateTime.now());
                user.setDeleted("N");
                userService.saveUser(user);
            } else {
                userService.updateUser(user);
            }
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(user);
        return reslutParent.toBack();
    }

  /*  @RequestMapping("/changepass.do")
    public ModelAndView changePass(HttpServletRequest request)
    {
        String uuid = request.getParameter("uuid");
        String mesg = request.getParameter("mesg");
        String color = request.getParameter("color");
        model.addObject("uuid",uuid);
        model.addObject("mesg",mesg);
        model.addObject("color",color);
        return model;
    }*/

    @RequestMapping("/savepass")
    @ResponseBody
    @ApiOperation(value = "密码修改", httpMethod = "POST", response = String.class, notes = "密码修改")
    public Map savePass(@RequestBody ChangePswModel changePswModel)
    {
        ReslutParent reslutParent = new ReslutParent();
        String uuid = changePswModel.getUuid();
        String pass1 = changePswModel.getPsw1();
        String pass2 = changePswModel.getPsw2();
        if(!pass1.equals(pass2))
        {
            reslutParent.setError("两次输入的密码不一致！");
        }
        else
        {
            userService.changePass(uuid,pass1);
            reslutParent.setContent("修改成功！");
        }
        return reslutParent.toBack();
    }

    @RequestMapping(value = "/checkrepeat")
    @ResponseBody
    @ApiOperation(value = "登录名是否冲突", httpMethod = "POST", response = Integer.class, notes = "登录名是否冲突 0 不冲突")
    public Map checkRepeat(@RequestBody WebUsers webUsers)
    {
        int reslue = userService.isRepeatLoginName(webUsers.getUuid(),webUsers.getLoginName());
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(reslue);
        return reslutParent.toBack();
    }


    private Map<Integer, BusinessSystem> getUserBusiness(){
        List<BusinessSystem> listBS = businessSystemService.getAllBusinessSystems();
        Map<Integer, BusinessSystem> mapBS = listBS.stream().collect(Collectors.toMap(BusinessSystem::getId,BusinessSystem->BusinessSystem));
        return mapBS;
    }

    private Map<String,WebRoles> getUserRoles(){
        List<WebRoles> list = roleService.getAllRoles();
        Map<String, WebRoles> mapRoles = list.stream().collect(Collectors.toMap(WebRoles::getUuid,WebRoles->WebRoles));
        return mapRoles;
    }
}
