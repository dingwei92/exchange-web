package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Pojo.IDModel;
import com.topsci.qh.webmanagement.Pojo.SearchModel;
import com.topsci.qh.webmanagement.Pojo.ServerType;
import com.topsci.qh.webmanagement.Pojo.WebUsers;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Resources.PageInfo;

import com.topsci.qh.webmanagement.Service.Normal.BusinessSystemTypeService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lzw.
 * 16-7-22
 */
@Controller
@RequestMapping("/businesstype")
@Api(value = "/businesstype", tags = "交换类型")
public class BusinessSystemTypeController extends BasicController {

    @Resource
    private BusinessSystemTypeService typeService;

    @RequestMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表", httpMethod = "POST", response = ServerType.class, notes = "列表")
    public Map list(@RequestBody SearchModel searchModel)
    {
        String searchword = searchModel.getSearch();
        List<ServerType> types = typeService.getTypesByPage(searchword);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(types);
        return reslutParent.toBack();
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    @ApiOperation(value = "获取类型", httpMethod = "GET", response = ServerType.class, notes = "获取类型")
    public Map get(@ApiParam("类型ID")@PathVariable("id")int id)
    {
        ServerType type = typeService.getType(id);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(type);
        return reslutParent.toBack();
    }

    @RequestMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存", httpMethod = "POST", response = String.class, notes = "保存")
    public Map<String, Object> save(@RequestBody  ServerType type)
    {
        typeService.saveType(type);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("保存成功！");
        return reslutParent.toBack();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    @ApiOperation(value = "刪除", httpMethod = "GET", response = String.class, notes = "刪除")
    public Map deleteBusinessSystem(@ApiParam("id")@PathVariable("id")int id)
    {
        typeService.deleteType(id);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("操作成功！");
        return reslutParent.toBack();
    }

    @RequestMapping("/checkrepeat")
    @ResponseBody
    @ApiOperation(value = "是否重复", httpMethod = "POST", response = String.class, notes = "1 重复 0 不重复，只需提交id 和name")
    protected Map checkRepeat(@RequestBody ServerType serverType)
    {
        int a = typeService.checkrepeat(serverType.getId(),serverType.getTypeName());
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(a);
        return reslutParent.toBack();
    }
}
