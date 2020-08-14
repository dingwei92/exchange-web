package com.topsci.qh.webmanagement.Business.Normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.Interface.IBasicController;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Service.Normal.*;
import com.topsci.qh.webmanagement.Tools.ReslutParent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lzw.
 * 16-6-30
 */
@Controller
@RequestMapping("/subscribe")
@Api(value = "/subscribe", tags = "订阅详情")
public class SubscribeController extends BasicController{

    @Resource
    private SubscribeService subscribeService;
    @Resource
    private SubscribeApplyService applyService;
    @Resource
    private BusinessSystemService businessSystemService;
    @Resource
    private CatalogService catalogService;
    @Resource
    private RelUserBusinessService relUserBusinessService;
    @Resource
    private BusinessSystemService bsService;
    @Resource
    private BusinessSystemTypeService typeService;

    public final String APPLY = "A";
    public final String UNDELETE = "N";

    @RequestMapping("/browser")
    @ResponseBody
    @ApiOperation(value = "订阅详情-业务详情", httpMethod = "POST", response = BusinessSystem.class, notes = "订阅详情-业务详情")
    public Map browser(@RequestBody SearchModel searchModel)
    {
        String searchword = searchModel.getSearch();
        List<BusinessSystem>  businessSystems = bsService.getBusinessSystems(searchword);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(businessSystems);
        return reslutParent.toBack();
    }

    @RequestMapping("/list/{id}")
    @ResponseBody
    @ApiOperation(value = "订阅详情-业务详情-查看", httpMethod = "POST", response = SubDetail.class, notes = "订阅详情-业务详情-查看")
    public Map list(@ApiParam("业务详情ID")@PathVariable("id")String bsid,@RequestBody SearchModel searchModel) {
        String searchword = searchModel.getSearch();
        List<ServerSubscribes> scribes = subscribeService.getSubscribes(searchword, bsid);
        Map<Integer, ServerSubscribeApply>  applies = applyService.getAllSubscribesApply();
        Map<Integer, String> catalogs = catalogService.getCatalogIdNameMap(false);
        Map<Integer, String> business = businessSystemService.getBusinessSystemIdNameMap(false);

        List<SubDetail> list = new ArrayList<>();
        scribes.stream().forEach(s->{
            SubDetail subDetail = new SubDetail();
            subDetail.setId(s.getId());
            subDetail.setBsid(Integer.parseInt(bsid));
            subDetail.setEnable(s.getEnables());
            subDetail.setRemark(s.getRemark());
            subDetail.setServiceName(catalogs.get(s.getServerCatalogId()));
            subDetail.setStatus(s.getDeleted());
            subDetail.setSubName(business.get(bsid));
            subDetail.setSubUser(applies.get(s.getId()).getName());
            subDetail.setSubUserId(applies.get(s.getId()).getId());
            subDetail.setTel(applies.get(s.getId()).getPhone());
            subDetail.setUpdateTime(s.getUpddatetime());
            list.add(subDetail);
        });
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(list);
        return reslutParent.toBack();
    }

    /*@RequestMapping("/get/{uuid}")
    @ResponseBody
    @ApiOperation(value = "订阅详情-业务详情-查看", httpMethod = "POST", response = BusinessSystem.class, notes = "订阅详情-业务详情-查看")
    public ModelAndView edit(HttpServletRequest request) {
        ServerSubscribeApply apply = new ServerSubscribeApply();
        String uuid = request.getParameter("uuid");
        String bsid = request.getParameter("bsid");
        String title;
        String lists = "";
        if (StringUtils.isNotEmpty(uuid)) {
            title = "修改数据订阅";
            scribe = subscribeService.getScribe(Integer.parseInt(uuid));
            scribe.setUpddatetime(StardTime.format(new Date()));
            apply = applyService.getApplyBySubscribe(scribe.getId());
            lists = subscribeService.getScribeListsComma(scribe.getId(), request);
            *//*JSONObject obj = subscribeService.getScribeConditions(scribe.getId());
            JSONObject result = (obj == null ? null : obj.getJSONObject("result"));
            model.addObject("condition", result == null ? "" : result.toJSONString());*//*
        } else {
            scribe.setEnables(subscribeService.ENABLED);
            scribe.setDeleted(Constants.UNDELETED);
            scribe.setUpddatetime(StardTime.format(new Date()));
            title = "新建数据订阅";
        }
        //拼接filed_result
        JSONObject json = new JSONObject();
        String param = scribe.getParameter();
        if (StringUtils.isNotEmpty(param)) {
            String[] params = param.split("><");
            int count = params.length;
            for (int i = 0; i < count; i++) {
                JSONObject jsonTmp = new JSONObject();
                String paramTmp = params[i];
                jsonTmp.put("id", String.valueOf(i));
                jsonTmp.put("desc", paramTmp.substring(paramTmp.indexOf(">") + 1, paramTmp.indexOf("<", paramTmp.indexOf(">"))));
                jsonTmp.put("name", paramTmp.substring(paramTmp.indexOf("/") + 1).replace(">", ""));
                json.put(String.valueOf(i), jsonTmp);
            }
            json.put("count", String.valueOf(count));
        } else {
            json.put("count", "0");
        }

        Map<Integer, String> catalogs = catalogService.getCatalogIdNameMap(true);
        Map<Integer, String> business = new HashMap<>();
        BusinessSystem busi = businessSystemService.getBusinessSystem(Integer.parseInt(bsid));
        business.put(busi.getId(), busi.getSystemName() + " (" + busi.getSystemShort() + ")");
        Map<String, String> status = subscribeService.getStatus();
        if (StringUtils.isEmpty(type)) {
            model.addObject("type", "");
        } else {
            model.addObject("type", type);
        }
        model.addObject("fail",subscribeService.FAILED);
        model.addObject("bsid", bsid);
        model.addObject("lists", lists);
        model.addObject("apply", apply);
        model.addObject("mesg", mesg);
        model.addObject("color", color);
        model.addObject("title", title);
        model.addObject("sb", scribe);
        model.addObject("status", status);
        model.addObject("catalogs", catalogs);
        model.addObject("search",request.getParameter("search"));
        model.addObject("business", business);
        model.addObject("field_result", json.toJSONString().replace("\"", "\'"));
        return model;
    }*/

    @RequestMapping("/update")
    @ResponseBody
    @ApiOperation(value = "申请订阅更新", httpMethod = "POST", response = String.class, notes = "申请订阅更新")
    public Map<String, Object> update(@RequestBody SubApplyModel subApplyModel){
        ServerSubscribes subscribes = subApplyModel.getSubscribe();
        ServerSubscribeApply apply = subApplyModel.getApply();
        int applyid = subApplyModel.getApply().getId();
        String condition_result = subApplyModel.getConditionResult();//条件集合
        /*         String parameter = null;
           String field_result = request.getParameter("field_result"); //条件集合
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(field_result) && field_result != null && !"\"{'count':'0'}\"".equals(field_result)) {
            JSONObject json = (JSONObject) JSON.parse(field_result);
            int count = Integer.parseInt((String) json.get("count"));
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    JSONObject subjson = (JSONObject) json.get(i + "");
                    String name = subjson.getString("name");
                    String desc = subjson.getString("desc");
                    sb.append("<" + name.replace(" ", "") + ">" + desc.replace(" ", "") + "</" + name.replace(" ", "") + ">");
                }
            }
        }
        parameter = sb.toString();
        subscribes.setParameter(parameter);
        */
        List<Integer> cataloglist = subApplyModel.getCataloglist();
        if (subscribes.getId() == 0) {
            subscribeService.saveSubscribe(subscribes, true);
        } else {
            if (subscribes.getDeleted().equals(UNDELETE)) {
                subscribes.setDeleted(UNDELETE);
            } else {
                subscribes.setDeleted(APPLY);
            }
            subscribeService.updateSubscribe(subscribes);
        }
        apply.setId(applyid);
        if (apply.getId() == 0) {
            apply.setCreateTime(StardTime.format(new Date()));
            apply.setUserUuid("");
            apply.setSid(subscribes.getId());
            applyService.saveApply(apply);
        } else {
            applyService.updateApply(apply);
        }
        JSONObject oldobj = JSON.parseObject(condition_result);
        JSONObject saveobj = new JSONObject();
        if (oldobj != null) {
            for (Map.Entry<String, Object> entry : oldobj.entrySet()) {
                saveobj.put(((JSONObject) entry.getValue()).getString("cid"), entry.getValue());
            }
        }
        subscribeService.saveScribeList(cataloglist, subscribes.getId(), saveobj);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("保存成功！");
        return reslutParent.toBack();
    }
    @RequestMapping("/delete/{id}")
    @ResponseBody
    @ApiOperation(value = "刪除", httpMethod = "GET", response = String.class, notes = "刪除")
    public Map delete(@ApiParam("id")@PathVariable("id")int id){
        subscribeService.deleteScribe(id);
        applyService.deleteApply(id);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("删除成功！");
        return reslutParent.toBack();
    }

    @RequestMapping(value = "/getcataloglist/{id}")
    @ResponseBody
    @ApiOperation(value = "订阅字段", httpMethod = "GET", response = ServerCatalogList.class, notes = "订阅字段")
    public Map getCatalog(@ApiParam("订阅服务")@PathVariable("id")int id) {
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(subscribeService.getScribeLists(id));
        return reslutParent.toBack();
    }

    @RequestMapping(value = "/getscribecond/{bid}/{cid}")
    @ResponseBody
    @ApiOperation(value = "详情查看的-选择的订阅字段", httpMethod = "GET", response = ServerSubscribesList.class, notes = "详情查看的-选择的订阅字段")
    public Map getSubscribeCondition(@ApiParam("订阅者ID")@PathVariable("bid")int bid,@ApiParam("订阅服务ID")@PathVariable("cid")int cid) {
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(subscribeService.getScribeConditions(bid, cid));
        return reslutParent.toBack();
    }

    @RequestMapping(value = "/getscribelistcomma.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getSubscribeListComma(HttpServletRequest request) {
        String bid = request.getParameter("bid");
        String cid = request.getParameter("cid");
        JSONObject object = new JSONObject();
        if (StringUtils.isNotEmpty(bid) || StringUtils.isNotEmpty(cid)) {
            object.put("result", subscribeService.getScribeListsCommaJson(bid, cid));
        } else {
            object.put("result", new JSONArray());
        }
        return object.toJSONString();
    }

    @RequestMapping(value = "/getothercatalog/{id}")
    @ResponseBody
    @ApiOperation(value = "订阅服务", httpMethod = "GET", response = ServerSubscribesList.class, notes = "订阅服务")
    public Map getOtherCatalog(@ApiParam("接口类别id")@PathVariable("id")int id) {
        List<ServerCatalog> list = catalogService.getCatalogIdName(id);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(list);
        return reslutParent.toBack();
    }

    @RequestMapping(value = "/getsubscriptioncatalog.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getSubscriptionCatalog(HttpServletRequest request) {
        return catalogService.getSubscriptionCatalog(request.getParameter("bsid"));
    }

    @RequestMapping(value = "/checkrepeat/{id}/{bid}/{cid}")
    @ResponseBody
    @ApiOperation(value = "是否重复", httpMethod = "GET", response = Integer.class, notes = "是否重复 1 是 0 否")
    public Map<String, Object> checkrepeat(@ApiParam("新建 为 0  applyid")@PathVariable("id")int id, @ApiParam("bid 业务系统id")@PathVariable("bid")int bid, @ApiParam("cid 订阅服务ID")@PathVariable("cid")int cid) {
        boolean isRepeat =  subscribeService.isRepeated(id, cid, bid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(isRepeat ? 1 : 0);
        return reslutParent.toBack();
    }


    @RequestMapping("/picfile.do")
    public ModelAndView picfile(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("/Normal/Subscribe/pic");
        String searchword = request.getParameter("search");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("picfile.do");
        if (StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl() + "?search=" + searchword);
        }
        List<UploadFiles> files = subscribeService.getPics(pageInfo,searchword);
        model.addObject("pics", files);
        model.addObject("search", searchword);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/uploadpic.do")
    public ModelAndView uploadpic(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/Normal/Subscribe/uploadpic");
        String mesg = request.getParameter("mesg");
        String color = request.getParameter("color");
        String title = "上传审核凭证";
        model.addObject("title", title);
        model.addObject("mesg", mesg);
        model.addObject("color", color);
        return model;
    }

    @RequestMapping("/savepic.do")
    public RedirectView savepic(MultipartFile file, HttpServletRequest request, UploadFiles protocol, RedirectAttributes attr) {
        RedirectView model = new RedirectView("picfile.do");
        if (protocol != null) {
            protocol.setUploadUserId((String)sessionManager.getSessionAttrObj(request, Constants.USERID));
            subscribeService.savePic(protocol, file, request);
            attr.addAttribute("mesg", "保存成功！");
            attr.addAttribute("color", Constants.TIP_SUCCESS);
        }
        return model;
    }

    @RequestMapping("/deletepic.do")
    public RedirectView deletepic(HttpServletRequest request,RedirectAttributes attr) {
        RedirectView model = new RedirectView("picfile.do");
        subscribeService.deletePic(request.getParameter("uuid"), request);
        return model;
    }


    @RequestMapping("/saveapply")
    @ResponseBody
    @ApiOperation(value = "申请订阅保存", httpMethod = "POST", response = String.class, notes = "申请订阅保存")
    public Map<String, Object> save(@RequestBody SubApplyModel subApplyModel)
    {
        ServerSubscribes subscribe = subApplyModel.getSubscribe();
        ServerSubscribeApply apply = subApplyModel.getApply();
        apply.setUserUuid("");
        List<Integer> cataloglist = subApplyModel.getCataloglist();
        String condition_result = subApplyModel.getConditionResult();//条件集合

        /*StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(field_result) && field_result != null && !"\"{'count':'0'}\"".equals(field_result)) {
            JSONObject json = (JSONObject) JSON.parse(field_result);
            int count = Integer.parseInt((String) json.get("count"));
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    JSONObject subjson = (JSONObject) json.get(i + "");
                    String name = subjson.getString("name");
                    String desc = subjson.getString("desc");
                    sb.append("<"+name.replace(" ", "")+">"+desc.replace(" ", "")+"</"+name.replace(" ", "")+">");
                }
            }
        }
        parameter = sb.toString();
        subscribe.setParameter(parameter);*/
        applyService.saveSubscribe(subscribe,apply);
        subscribeService.saveScribeList(cataloglist,subscribe.getId(),JSON.parseObject(condition_result));
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("保存成功！");
        return reslutParent.toBack();
    }


    @RequestMapping("/listapply/{type}")
    @ResponseBody
    @ApiOperation(value = "订阅审核&订阅记录", httpMethod = "POST", response = SubDetail.class, notes = "订阅审核&订阅记录")
    public Map list(@ApiParam("type 1 审核  2 记录")@PathVariable("type")int type,@RequestBody SearchModel searchModel) {
        String searchword = searchModel.getSearch();
        Map<String,Object> result = applyService.getSubscribesApply(new ArrayList<String>(){{add(searchword);}},type);
        List<ServerSubscribes> scribes = (List<ServerSubscribes>)result.get("scribes");
        Map<Integer,ServerSubscribeApply> applies = (Map<Integer,ServerSubscribeApply>)result.get("applies");
        Map<Integer, String> catalogs = catalogService.getCatalogIdNameMap(false);
        Map<Integer, String> business = businessSystemService.getBusinessSystemIdNameMap(false);

        List<SubDetail> list = new ArrayList<>();
        scribes.stream().forEach(s->{
            SubDetail subDetail = new SubDetail();
            subDetail.setId(s.getId());
            subDetail.setBsid(s.getBusinessSystemId());
            subDetail.setEnable(s.getEnables());
            subDetail.setRemark(s.getRemark());
            subDetail.setServiceName(catalogs.get(s.getServerCatalogId()));
            subDetail.setStatus(s.getDeleted());
            subDetail.setSubName(business.get(s.getBusinessSystemId()));
            subDetail.setSubUser(applies.get(s.getId()).getName());
            subDetail.setSubUserId(applies.get(s.getId()).getId());
            subDetail.setTel(applies.get(s.getId()).getPhone());
            subDetail.setUpdateTime(s.getUpddatetime());
            list.add(subDetail);
        });
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent(list);
        return reslutParent.toBack();
    }
    @RequestMapping("/acceptapply/{sid}")
    @ResponseBody
    @ApiOperation(value = "通过审核", httpMethod = "GET", response = String.class, notes = "通过审核")
    public Map<String, Object> accept(@ApiParam("订阅申请ID")@PathVariable("sid")int sid)
    {
        subscribeService.acceptSubscribeApply(sid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("审核通过！");
        return reslutParent.toBack();
    }

    @RequestMapping("/deletepic/{sid}")
    @ResponseBody
    @ApiOperation(value = "拒绝审核", httpMethod = "GET", response = String.class, notes = "拒绝审核")
    public Map<String, Object> deny(@ApiParam("订阅申请ID")@PathVariable("sid")int sid)
    {
        subscribeService.denySubscribeApply(sid);
        ReslutParent reslutParent = new ReslutParent();
        reslutParent.setContent("审核通过！");
        return reslutParent.toBack();
    }

}
