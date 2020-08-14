package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Resources.PageInfo;

import com.topsci.qh.webmanagement.Service.Normal.FunctionService;
import com.topsci.qh.webmanagement.Service.Normal.RoleService;
import com.topsci.qh.webmanagement.Tools.ReslutParent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lzw.
 * 16-7-19
 */
@Controller
@RequestMapping("/roles")
@Api(value = "/roles", tags = "角色管理")
public class RoleManageController extends BasicController{

    @Resource
    private RoleService roleService;
    @Resource
    private FunctionService functionService;

    @RequestMapping("/list")
    @ResponseBody
    @ApiOperation(value = "角色列表", httpMethod = "POST", response = WebRoles.class, notes = "角色列表")
    public Map list() {
        List<WebRoles> roles = roleService.getRoles();
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(roles);
        return reslutParent.toBack();
    }

    @RequestMapping("/get")
    @ResponseBody
    @ApiOperation(value = "角色列表", httpMethod = "POST", response = WebRoles.class, notes = "角色列表")
    public Map edit(@RequestBody IDModel idModel)
    {
        String uuid = idModel.getId();
        WebRoles role = roleService.getRole(uuid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(role);
        return reslutParent.toBack();
    }

    @RequestMapping("/functions")
    @ResponseBody
    @ApiOperation(value = "角色对应的功能", httpMethod = "POST", response = RoleFunctionModel.class, notes = "角色对应的功能 返回 角色下面功能的uuids")
    public Map edit1(@RequestBody IDModel idModel)
    {
        String uuid = idModel.getId();
        List<String> list = roleService.getAuthFuncs(uuid);
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
                roleFunctionModel.setChecked(false);
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

    @RequestMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存", httpMethod = "POST", response = String.class, notes = "保存")
    public Map<String, Object> save(@RequestBody RolesFunsModel rolesFunsModel)
    {
            if(StringUtils.isEmpty(rolesFunsModel.getWebRoles().getUuid()))
            {
                rolesFunsModel.getWebRoles().setUuid(UUID.randomUUID().toString().replaceAll("-",""));
                roleService.saveRole(rolesFunsModel.getWebRoles());
                roleService.saveFuncRelations(rolesFunsModel.getFunIds(),rolesFunsModel.getWebRoles().getUuid());
            }
            else
            {
                roleService.updateRole(rolesFunsModel.getWebRoles());
                roleService.updateFuncRelations(rolesFunsModel.getFunIds(),rolesFunsModel.getWebRoles().getUuid());
            }
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("保存成功！");
        return reslutParent.toBack();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    @ApiOperation(value = "删除", httpMethod = "GET", response = String.class, notes = "删除")
    public Map<String, Object> delete(@ApiParam("id")@PathVariable("id")String uuid)
    {
        roleService.deleteRole(uuid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("删除成功！");
        return reslutParent.toBack();
    }

    /*@RequestMapping(value = "/getfuncsjson.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getFuncsJson(HttpServletRequest request)
    {
        return functionService.getUuidNameMapJson(new ArrayList<String>(),false);
    }*/

    @RequestMapping("/checkrepeat")
    @ResponseBody
    @ApiOperation(value = "是否重复", httpMethod = "POST", response = Integer.class, notes = "1 重复 0 不重复，只需提交id 和name")
    public Map<String, Object> checkRepeat(@RequestBody WebRoles webRoles)
    {
        int i = roleService.checkRepeat(webRoles.getUuid(),webRoles.getRoleName());
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(i);
        return reslutParent.toBack();
    }
    
    @RequestMapping(value = "/issuper.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String isSuper(HttpServletRequest request)
    {
        boolean b = roleService.isSuperUserByRid(request.getParameter("rid"));
        if(b){
        	return "{\"result\":\"true\"}";
        }else{
        	return "{\"result\":\"false\"}";
        }
    }
}
