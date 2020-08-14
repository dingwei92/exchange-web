package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Pojo.BusinessSystem;
import com.topsci.qh.webmanagement.Pojo.SearchModel;
import com.topsci.qh.webmanagement.Pojo.ServerType;
import com.topsci.qh.webmanagement.Pojo.WebUsers;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Resources.PageInfo;

import com.topsci.qh.webmanagement.Service.Normal.BusinessSystemService;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lzw.
 * 16-6-25
 */
@Controller
@RequestMapping("/businesssystem")
@Api(value = "/businesssystem", tags = "业务详情")
public class BusinessSystemController extends BasicController {
    @Resource
    private BusinessSystemService bsService;
    @Resource
    private BusinessSystemTypeService typeService;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/list")
    @ResponseBody
    @ApiOperation(value = "列表", httpMethod = "POST", response = BusinessSystem.class, notes = "列表")
    public Map list(@RequestBody SearchModel searchModel)
    {

        String searchword = searchModel.getSearch();
        List<BusinessSystem> businessSystems = bsService.getBusinessSystems(searchword);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(businessSystems);
        return reslutParent.toBack();
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    @ApiOperation(value = "详情", httpMethod = "GET", response = BusinessSystem.class, notes = "详情")
    public Map get(@ApiParam("业务ID")@PathVariable("id")int id)
    {
        BusinessSystem businessSystem  = bsService.getBusinessSystem(id);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(businessSystem);
        return reslutParent.toBack();
    }

    @RequestMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存", httpMethod = "POST", response = String.class, notes = "保存")
    public Map save(@RequestBody BusinessSystem businessSystem)
    {
        if(businessSystem.getId() == 0)
        {
            bsService.saveBusinessSystem(businessSystem);
        } else
        {
            bsService.updateBusinessSystem(businessSystem);
        }
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("保存成功！");
        return reslutParent.toBack();
    }


    @RequestMapping("/delete/{id}")
    @ResponseBody
    @ApiOperation(value = "保存", httpMethod = "GET", response = String.class, notes = "保存")
    public Map deleteBusinessSystem(@ApiParam("业务ID")@PathVariable("id")int id)
    {
        bsService.deleteBusinessSystem(id);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("删除成功！");
        return reslutParent.toBack();
    }

    @RequestMapping(value = "/checkrepeat")
    @ResponseBody
    @ApiOperation(value = "是否重复", httpMethod = "POST", response = Integer.class, notes = "是否重复 1 名称重复 2 业务ID重复 0 不重复 传 ID name system_short")
    protected Map<String, Object> checkRepeat(@RequestBody BusinessSystem businessSystem)
    {
        int i = bsService.checkRepeat(businessSystem.getId(),businessSystem.getSystemName(),businessSystem.getSystemShort());
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(i);
        return reslutParent.toBack();
    }

    @RequestMapping("/map")
    @ResponseBody
    @ApiOperation(value = "业务详情 转化 Key value", httpMethod = "POST", response = BusinessSystem.class, notes = "业务详情 转化 Key value")
    public Map list()
    {
        List<BusinessSystem> businessSystems = bsService.getBusinessSystems("");
        Map<Integer, BusinessSystem> mapBS = businessSystems.stream().collect(Collectors.toMap(BusinessSystem::getId, BusinessSystem->BusinessSystem));
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(mapBS);
        return reslutParent.toBack();
    }



}
