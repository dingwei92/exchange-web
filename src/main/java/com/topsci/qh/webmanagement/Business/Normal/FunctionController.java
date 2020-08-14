package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Pojo.IDModel;
import com.topsci.qh.webmanagement.Pojo.RoleFunctionModel;
import com.topsci.qh.webmanagement.Pojo.WebFuncs;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Service.Normal.FunctionService;
import com.topsci.qh.webmanagement.Tools.ReslutParent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lzw.
 * 16-7-15
 */
@Controller
@RequestMapping("/functions")
@Api(value = "/functions", tags = "菜单管理")
public class FunctionController{
    @Resource
    private FunctionService functionService;

    @RequestMapping("/list")
    @ResponseBody
    @ApiOperation(value = "功能列表", httpMethod = "GET", response = WebFuncs.class, notes = "功能列表")
    public Map list() {
        List<WebFuncs> funcs = functionService.getFuncs();
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(funcs);
        return reslutParent.toBack();
    }

    @RequestMapping("/get")
    @ResponseBody
    @ApiOperation(value = "获取", httpMethod = "POST", response = WebFuncs.class, notes = "功能列表")
    public Map edit(@RequestBody IDModel idModel)
    {
        String uuid = idModel.getId();
        WebFuncs funcs;
        Map<String,String> status = functionService.getStatus();
        String others;
        if(StringUtils.isNotEmpty(uuid))
        {
            funcs = functionService.getFunc(uuid);
            others = functionService.getUuidNameMapJson(new ArrayList<String>(){{add(funcs.getParentUuid());}},true);
        }
        else
        {
            funcs = new WebFuncs();
            funcs.setDeleted(Constants.UNDELETED);
            funcs.setIsParent(functionService.ISNOTPARENT);
            others = functionService.getUuidNameMapJson(new ArrayList<String>(),true);
        }
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(funcs);
        return reslutParent.toBack();
    }

    @RequestMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存", httpMethod = "POST", response = WebFuncs.class, notes = "保存")
    public Map save(@RequestBody WebFuncs func)
    {
        if(StringUtils.isEmpty(func.getParentUuid()))
        {
            func.setParentUuid("0");
        }
        if(StringUtils.isEmpty(func.getUuid()))
        {
            func.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
            functionService.saveFunc(func);
        }
        else
        {
            functionService.updateFunc(func);
        }
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("保存成功");
        return reslutParent.toBack();
    }

    @RequestMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "刪除", httpMethod = "POST", response = WebFuncs.class, notes = "刪除 测试的时候删除新建的")
    public Map delete(@RequestBody IDModel idModel)
    {
        functionService.deleteFunc(idModel.getId());
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("删除成功");
        return reslutParent.toBack();
    }

    @RequestMapping("/tree")
    @ResponseBody
    @ApiOperation(value = "功能编辑 所有菜单 ", httpMethod = "POST", response = WebFuncs.class, notes = "返回全都菜单  树形结构,编辑需要提交 菜单id  ，新建提交 0")
    public Map<String, Object> getChildren(@RequestBody IDModel idModel)
    {
        String parentId = "";
        if(!idModel.getId().equals("0")){
            parentId = functionService.getFunc(idModel.getId()).getParentUuid();
        }
        List<WebFuncs> funcs = functionService.getFuncs();
        List<RoleFunctionModel> modelList = getRoleFunctionModels(parentId, funcs);

        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(modelList);
        return reslutParent.toBack();
    }

    private List<RoleFunctionModel> getRoleFunctionModels(String parentId, List<WebFuncs> funcs) {
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
            if(parentId.equals(f.getUuid())){
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
                if(parentId.equals(fc.getUuid())){
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

    /*@RequestMapping("/getchildren")
    @ResponseBody
    @ApiOperation(value = "获取子目录", httpMethod = "POST", response = WebFuncs.class, notes = "根据父节点获取子目录")
    public Map<String, Object> getChildren(@RequestBody IDModel idModel)
    {
        String uuid = idModel.getId();
        List<WebFuncs> list = functionService.getChildFuncJson(uuid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(list);
        return reslutParent.toBack();
    }
*/

    /*@RequestMapping("/checkrepeat")
    @ResponseBody
    @ApiOperation(value = "名称是否重复", httpMethod = "POST", response = WebFuncs.class, notes = "需要 uuid parentUuid name，c = 1 重复 0 不重复")
    public Map checkRepeat(@RequestBody WebFuncs webFuncs)
    {
        String uuid = webFuncs.getUuid();
        String pid = webFuncs.getParentUuid();
        String name = webFuncs.getName();
        int repeat = functionService.repeatCheck(uuid,pid,name);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(repeat);
        return reslutParent.toBack();
    }*/
}
