package com.topsci.qh.webmanagement.Business.Normal;

import com.topsci.qh.webmanagement.Resources.BasicController;
import com.topsci.qh.webmanagement.Service.Normal.BaseDataService;
import com.topsci.qh.webmanagement.Tools.Cache;
import com.topsci.qh.webmanagement.Tools.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by lzw.
 * 16-7-4
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/basedata")
public class BaseDataController extends BasicController {
    private Logger log = LoggerFactory.getLogger(BaseDataController.class);

    @Resource
    private BaseDataService baseDataService;
    @Resource
    private Config config;
    @Resource
    private Cache cache;

    /*@RequestMapping("/idtype.do")
    public ModelAndView list_idtype(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("/Normal/BaseData/Cv0201101");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("idtype.do");

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
        List<Cv0201101> list = baseDataService.getAllByPage(Cv0201101.class,pageInfo,"idtype",searchword);
        ServerCatalog catalog = baseDataService.getCorrespondCatalog("idtype");
        model.addObject("catalog",catalog);
        model.addObject("list",list);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/officecode.do")
    public ModelAndView list_office(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("/Normal/BaseData/Cv0810011");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("officecode.do");

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
//        List<Cv0810011> list = baseDataService.getAllByPage(Cv0810011.class,pageInfo,"officecode",searchword);
        ServerCatalog catalog = baseDataService.getCorrespondCatalog("office");
        String[] jsonstring = baseDataService.getAllJson("officecode");
        model.addObject("catalog",catalog);
        model.addObject("size",jsonstring[1]);
//        model.addObject("list",list);
        model.addObject("jsonstring",jsonstring[0]);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/citycode.do")
    public ModelAndView list_city(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("/Normal/BaseData/GbT2260");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("citycode.do");

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
//        List<GbT2260> list = baseDataService.getAllByPage(GbT2260.class,pageInfo,"citycode",searchword);
        ServerCatalog catalog = baseDataService.getCorrespondCatalog("city");
        String[] jsonstring = baseDataService.getAllJson("citycode");
        model.addObject("catalog",catalog);
        model.addObject("jsonstring",jsonstring[0]);
        model.addObject("size",jsonstring[1]);
//        model.addObject("list",list);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/nationcode.do")
    public ModelAndView list_nation(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("Normal/BaseData/GbT33041991");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("nationcode.do");

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
        List<GbT33041991> list = baseDataService.getAllByPage(GbT33041991.class,pageInfo,"nationcode",searchword);
        ServerCatalog catalog = baseDataService.getCorrespondCatalog("nation");
        model.addObject("catalog",catalog);
        model.addObject("list",list);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/diseasecode.do")
    public ModelAndView list_disease(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("Normal/BaseData/GbT143962001");
        ServerCatalog catalog = baseDataService.getCorrespondCatalog("disease");
        String[] jsonstring = baseDataService.getAllJson("diseasecode");
        model.addObject("catalog",catalog);
        model.addObject("jsonstring",jsonstring[0]);
        model.addObject("size",jsonstring[1]);
        return model;
    }


    @RequestMapping("/gendercode.do")
    public ModelAndView list_gendercode(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("Normal/BaseData/GbT226112003");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("gendercode.do");

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
        List<GbT226112003> list = baseDataService.getAllByPage(GbT226112003.class,pageInfo,"gendercode",searchword);
        ServerCatalog catalog = baseDataService.getCorrespondCatalog("gender");
        model.addObject("catalog",catalog);
        model.addObject("list",list);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/healthofficer.do")
    public ModelAndView list_healthofficer(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("Normal/BaseData/HealthOfficer");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("healthofficer.do");

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
        List<HealthOfficer> list = baseDataService.getAllByPage(HealthOfficer.class,pageInfo,"healthofficer",searchword);
        ServerCatalog catalog = baseDataService.getCorrespondCatalog("healthofficer");
        model.addObject("catalog",catalog);
        model.addObject("list",list);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/population.do")
    public ModelAndView list_population(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("Normal/BaseData/Population");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("population.do");

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
        List<Population> list = baseDataService.getAllByPage(Population.class,pageInfo,"population",searchword);

        Map<String,String> nation;
        Map<String,String> gender;
        Map<String,String> idmap;
        if(cache.getAttr(Cache.BASE_NATION) == null)
        {
            nation = baseDataService.getIdNameMap("nationcode");
            cache.setAttr(Cache.BASE_NATION,nation);
        }
        else
        {
            nation = (Map)cache.getAttr(Cache.BASE_NATION);
        }
        if(cache.getAttr(Cache.BASE_GENDER) == null)
        {
            gender = baseDataService.getIdNameMap("gendercode");
            cache.setAttr(Cache.BASE_GENDER,gender);
        }
        else
        {
            gender = (Map)cache.getAttr(Cache.BASE_GENDER);
        }
        if(cache.getAttr(Cache.BASE_ID) == null) {
            idmap = baseDataService.getIdNameMap("idtype");
            cache.setAttr(Cache.BASE_ID,idmap);
        }
        else
        {
            idmap = (Map)cache.getAttr(Cache.BASE_ID);
        }
        ServerCatalog catalog;
        if(cache.getAttr(Cache.BASE_CATA_POP) == null) {
            catalog = baseDataService.getCorrespondCatalog("population");
            cache.setAttr(Cache.BASE_CATA_POP,catalog);
        }
        else
        {
            catalog = (ServerCatalog)cache.getAttr(Cache.BASE_CATA_POP);
        }
        model.addObject("catalog",catalog);
        model.addObject("gender",gender);
        model.addObject("nation",nation);
        model.addObject("idmap",idmap);
        model.addObject("list",list);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/departments.do")
    public ModelAndView list_departments(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("Normal/BaseData/IrptDepartments");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("departments.do");

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
        List<IrptDepartments> list = baseDataService.getAllByPage(IrptDepartments.class,pageInfo,"departments",searchword);
        ServerCatalog catalog = baseDataService.getCorrespondCatalog("department");
        model.addObject("catalog",catalog);
        model.addObject("list",list);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/hospitals.do")
    public ModelAndView list_hospital(HttpServletRequest request, PageInfo pageInfo) {
        ModelAndView model = new ModelAndView("Normal/BaseData/HospitalsType");
        pageInfo = getPageInfo(request,pageInfo);
        pageInfo.setUrl("hospitals.do");

        String searchword = request.getParameter("search");
        if(StringUtils.isNotEmpty(searchword)) {
            model.addObject("search", searchword);
            pageInfo.setUrl(pageInfo.getUrl()+"?search="+searchword+"");
        }
//        List<IrptDepartments> list = baseDataService.getAllByPage(HospitalsType.class,pageInfo,"hospitals",searchword);
        ServerCatalog catalog = baseDataService.getCorrespondCatalog("hospital");
        String[] jsonstring = baseDataService.getAllJson("hospitals");
        model.addObject("catalog",catalog);
        model.addObject("jsonstring",jsonstring[0]);
        model.addObject("size",jsonstring[1]);
//        model.addObject("list",list);
        model.addObject("pageinfo",pageInfo);
        return model;
    }

    @RequestMapping("/getExcel.do")
    public ResponseEntity<byte[]> getExcel(HttpServletRequest request,HttpServletResponse response)
    {
        String type = request.getParameter("type");
        String ctxpath = request.getSession().getServletContext().getRealPath("/")+"tmp/";
        String filePath = config.getTmpPathExcel();
        String searchword = request.getParameter("search");
        byte[] f = baseDataService.getExcel(type,filePath,request,searchword);
        HttpHeaders headers = new HttpHeaders();
        if(f ==null)
        {
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<byte[]>("没有查询到数据".getBytes(),headers, HttpStatus.OK);
        }
        Map map = baseDataService.getTableChineseName();
        try {
                String filename = (String)map.get(type);
                String datetime = StardTime.format(new Date());
                filename = filename+"_"+datetime+".xls";
                String fileUTF8Name=new String(filename.getBytes("UTF-8"),"iso-8859-1");
                headers.setContentDispositionFormData("attachment", fileUTF8Name);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                return new ResponseEntity<byte[]>(f,headers, HttpStatus.OK);
            }
            catch (Exception ex)
            {
                log.warn("生成excel文件下载流失败!",ex);
            }
        return new ResponseEntity<byte[]>("没有查询到数据".getBytes(),headers, HttpStatus.OK);
    }*/


}
