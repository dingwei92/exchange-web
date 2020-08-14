package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.ServerCatalog;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Tools.Config;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lzw.
 * 16-7-4
 */
@Service
@SuppressWarnings("unchecked")
public class BaseDataService {
    private Logger log = LoggerFactory.getLogger(BaseDataService.class);

    @Resource
    private CatalogService catalogService;
    @Resource
    private Config config;

    //用于判断应该查询哪个表
    private Map<String, String> tableTypeName;
    //用于导出excel时设定文件名
    private Map<String, String> tableChineseName;
    //用于设置每个表搜索的字段
    private Map<String, List<String>> tableSearchWord;

    private String diseaseCodeJsonStr;
    private int deseaseCodeSize;

    public Map<String, String> getTableChineseName() {
        return tableChineseName;
    }

    public BaseDataService() {
        tableTypeName = new HashMap<String, String>() {{
            put("idtype", "Cv0201101");
            put("officecode", "Cv0810011");
            put("citycode", "GbT2260");
            put("nationcode", "GbT33041991");
            put("diseasecode", "GbT143962001");
            put("gendercode", "GbT226112003");
            put("healthofficer", "HealthOfficer");
            put("population", "Population");
            put("departments", "IrptDepartments");
            put("hospitals", "HospitalsType");
        }};

        tableChineseName = new HashMap<String, String>() {{
            put("idtype", "身份证件类别");
            put("officecode", "科室代码");
            put("citycode", "行政区划代码");
            put("nationcode", "民族代码");
            put("diseasecode", "疾病代码");
            put("gendercode", "性别代码");
            put("healthofficer", "卫生机构代码");
            put("population", "人口标准库");
            put("departments", "卫生机构");
            put("hospitals", "医院类型代码");
        }};

        tableSearchWord = new HashMap<String, List<String>>() {{
            put("idtype", new ArrayList<String>(Arrays.asList("code", "name")));
            put("officecode", new ArrayList<String>(Arrays.asList("code", "name")));
            put("citycode", new ArrayList<String>(Arrays.asList("cityId", "cityCode", "cityName")));
            put("nationcode", new ArrayList<String>(Arrays.asList("code", "meaning")));
            put("diseasecode", new ArrayList<String>(Arrays.asList("code", "name")));
            put("gendercode", new ArrayList<String>(Arrays.asList("code", "name")));
            put("healthofficer", new ArrayList<String>(Arrays.asList("username")));
            put("population", new ArrayList<String>(Arrays.asList("name", "idcard", "healthCard")));
            put("departments", new ArrayList<String>(Arrays.asList("id", "caption")));
            put("hospitals", new ArrayList<String>(Arrays.asList("code", "name")));
        }};
    }

    public List getAllByPage(Class c, PageInfo pageInfo, String type, String searchword) {
       /* String classname = c.getSimpleName();
        String hql = "from " + classname + " cn ";
        boolean flag = false;
        if (StringUtils.isNotEmpty(searchword)) {
            searchword = searchword.trim().toUpperCase();
            List<String> fields = tableSearchWord.get(type);
            if (fields != null) {
                String where = "";
                for (String str : fields) {
                    if (StringUtils.isEmpty(where)) {
                        where += " where cn." + str + " like '%" + searchword + "%' ";
                        flag = true;
                    } else {
                        where += " or cn." + str + " like '%" + searchword + "%' ";
                    }
                }
                hql = hql + where;
            }
        }
        if (!flag && "population".equals(type)) {
            hql += " where cn.status = 'Y' order by cn.id";
        } else if (flag && "population".equals(type)) {
            hql += " and cn.status = 'Y' order by cn.id";
        }
        pageInfo.setTotalResult(getCount(hql));
        return findPageByHQL(hql, pageInfo);*/
        return null;
    }

    public List getAll(String type, String searchword) {
        /*String classname = tableTypeName.get(type);
        String hql = "from " + classname + " cn ";
        boolean flag = false;
        if (StringUtils.isNotEmpty(searchword)) {
            searchword = searchword.trim().toUpperCase();
            List<String> fields = tableSearchWord.get(type);
            if (fields != null) {
                String where = "";
                for (String str : fields) {
                    if (StringUtils.isEmpty(where)) {
                        where += " where cn." + str + " like '%" + searchword + "%' ";
                        flag = true;
                    } else {
                        where += " or cn." + str + " like '%" + searchword + "%' ";
                    }
                }
                hql = hql + where;
            }
        }
        if (!flag && "population".equals(type)) {
            hql += " where cn.status = 'N' ";
        } else if (flag && "population".equals(type)) {
            hql += " and cn.status = 'N' ";
        }
        return findByHQL(hql);*/
        return null;
    }

    public String[] getAllJson(String type) {
        List list;
        int size;
        //疾病代码缓存过就不去查库了
        if("diseasecode".equals(type) && StringUtils.isNotEmpty(diseaseCodeJsonStr))
        {
            list = new ArrayList();
            size = deseaseCodeSize;
        }
        else {
            list = getAll(type, null);
            size = list.size();
        }
        if ("officecode".equals(type)) {
            return new String[]{officeJson(list),size+""};
        } else if ("hospitals".equals(type)) {
            return new String[]{hospitalsJson(list),size+""};
        } else if ("citycode".equals(type)) {
            return new String[]{citycodeJson(list),size+""};
        }
        else if("diseasecode".equals(type))
        {
            return new String[]{diseasecodeJson(list),size+""};
        }
        return new String[]{"","0"};
    }

    private String diseasecodeJson(List lists)
    {
       /* if(StringUtils.isNotEmpty(diseaseCodeJsonStr))
        {
            return diseaseCodeJsonStr;
        }
        Map<String, GbT143962001> mappings = new TreeMap<>();
        Set<String> firstlevel = new TreeSet<>();
        Set<String> secondlevel = new TreeSet<>();
        for (GbT143962001 type : (List<GbT143962001>) lists) {
            mappings.put(type.getCode(), type);
            if (type.getCode().length()==3) {
                firstlevel.add(type.getCode().substring(0,1));
                secondlevel.add(type.getCode());
            }
        }
        StringBuilder result = new StringBuilder();
        for (String strf : firstlevel) {
            if (result.length() > 0) {
                result.append(",");
            }
//            GbT143962001 typef = mappings.get(strf);
            result.append("{text:'<span style=\"margin-right:30px;\">").append(strf).append("</span>")
                   .append("',selectable:false,state:{expanded:false},nodes:[");
            StringBuilder secondl = new StringBuilder();
            boolean find = false;
            for (String strs : secondlevel) {
                if (find) {
                    //遍历完成就退出，进行下一轮循环，减少遍历次数
                    if (!strs.startsWith(strf)) {
                        break;
                    }
                }
                if (!strs.startsWith(strf)) {
                    continue;
                }
                find = true;
                if (secondl.length() > 0) {
                    secondl.append(",");
                }
                GbT143962001 types = mappings.get(strs);
                secondl.append("{text:'<span style=\"margin-right:30px;\">").append(types.getCode()).append("</span>")
                        .append("<span>").append(types.getName()).append("</span>',selectable:false,state:{expanded:false},nodes:[");
                StringBuilder child = new StringBuilder();
                boolean findc = false;
                for (Map.Entry<String, GbT143962001> entry : mappings.entrySet()) {
                    GbT143962001 typec = entry.getValue();
                    if (findc) {
                        //遍历完成就退出，进行下一轮循环，减少遍历次数
                        if (!entry.getKey().startsWith(strs)) {
                            break;
                        }
                    }
                    if (entry.getKey().equals(strs) || !entry.getKey().startsWith(strs)) {
                        continue;
                    }
                    findc = true;
                    if (child.length() > 0) {
                        child.append(",");
                    }
                    child.append("{text:'<span style=\"margin-right:30px;\">").append(typec.getCode()).append("</span>")
                            .append("<span>").append(typec.getName()).append("</span>',selectable:false,state:{expanded:false}}");
                }

                if (child.length() == 0) child = new StringBuilder("{}");
                secondl.append(child).append("]}");
            }
            if (secondl.length() == 0) secondl = new StringBuilder("{}");
            result.append(secondl).append("]}");
        }
        if (result.length() == 0) {
            result = new StringBuilder("{}");
        }
        diseaseCodeJsonStr = result.toString();
        deseaseCodeSize = lists.size();
        return result.toString();*/
        return null;
    }

    private String citycodeJson(List lists) {
        /*Map<String, GbT2260> mappings = new TreeMap<>();
        Set<String> firstlevel = new TreeSet<>();
        Set<String> secondlevel = new TreeSet<>();
        for (GbT2260 type : (List<GbT2260>) lists) {
            mappings.put(type.getCityId(), type);
            if (type.getCityId().endsWith("0000")) {
                firstlevel.add(type.getCityId());
            } else if (type.getCityId().endsWith("00")) {
                secondlevel.add(type.getCityId());
            }
        }


        StringBuilder result = new StringBuilder();
        for (String strf : firstlevel) {
            if (result.length() > 0) {
                result.append(",");
            }
            GbT2260 typef = mappings.get(strf);
            result.append("{text:'<span style=\"margin-right:30px;\">" + typef.getCityId() + "</span>" +
                    "<span>" + typef.getCityName() + "</span>" +
                    "<span style=\"margin-left:30px;\">"+(StringUtils.isEmpty(typef.getCityCode())?"":typef.getCityCode())+"</span>',selectable:false,state:{expanded:false},nodes:[");
            StringBuilder secondl = new StringBuilder();
            boolean find = false;
            for (String strs : secondlevel) {
                if (find) {
                    //遍历完成就退出，进行下一轮循环，减少遍历次数
                    if (!strs.startsWith(strf.substring(0, 2))) {
                        break;
                    }
                }
                if (!strs.startsWith(strf.substring(0, 2))) {
                    continue;
                }
                find = true;
                if (secondl.length() > 0) {
                    secondl.append(",");
                }
                GbT2260 types = mappings.get(strs);
                secondl.append("{text:'<span style=\"margin-right:30px;\">" + types.getCityId() + "</span>" +
                        "<span>" + types.getCityName() + "</span>" +
                        "<span style=\"margin-left:30px;\">"+(StringUtils.isEmpty(types.getCityCode())?"":types.getCityCode())+ "</span>',selectable:false,state:{expanded:false},nodes:[");
                StringBuilder child = new StringBuilder();
                boolean findc = false;
                for (Map.Entry<String, GbT2260> entry : mappings.entrySet()) {
                    GbT2260 typec = entry.getValue();
                    if (findc) {
                        //遍历完成就退出，进行下一轮循环，减少遍历次数
                        if (!entry.getKey().startsWith(strs.substring(0, 4))) {
                            break;
                        }
                    }
                    if (entry.getKey().equals(strs) || !entry.getKey().startsWith(strs.substring(0, 4))) {
                        continue;
                    }
                    findc = true;
                    if (child.length() > 0) {
                        child.append(",");
                    }
                    child.append("{text:'<span style=\"margin-right:30px;\">" + typec.getCityId() + "</span>" +
                            "<span>" + typec.getCityName() + "</span>" +
                            "<span style=\"margin-left:30px;\">"+(StringUtils.isEmpty(typec.getCityCode())?"":typec.getCityCode())+ "</span>',selectable:false,state:{expanded:false}}");
                }

                if (child.length() == 0) child = new StringBuilder("{}");
                secondl.append(child).append("]}");
            }
            if (secondl.length() == 0) secondl = new StringBuilder("{}");
            result.append(secondl).append("]}");
        }
        if (result.length() == 0) {
            result = new StringBuilder("{}");
        }
        return result.toString();*/
        return null;
    }

    private String hospitalsJson(List lists) {
        /*Map<String, HospitalsType> mappings = new TreeMap<>();
        Set<String> firstlevel = new TreeSet<>();
        Set<String> secondlevel = new TreeSet<>();
        for (HospitalsType type : (List<HospitalsType>) lists) {
            mappings.put(type.getCode(), type);
            if (type.getCode().length() == 1) {
                firstlevel.add(type.getCode());
            } else if (type.getCode().length() == 2) {
                secondlevel.add(type.getCode());
            }
        }

        StringBuilder result = new StringBuilder();
        for (String strf : firstlevel) {
            if (result.length() > 0) {
                result.append(",");
            }
            HospitalsType typef = mappings.get(strf);
            result.append("{text:'<span style=\"margin-right:30px;\">" + typef.getCode() + "</span>" +
                    "<span>" + typef.getName() + "</span>',selectable:false,state:{expanded:false},nodes:[");
            StringBuilder secondl = new StringBuilder();
            boolean find = false;
            for (String strs : secondlevel) {
                if (find) {
                    //遍历完成就退出，进行下一轮循环，减少遍历次数
                    if (!strs.startsWith(strf)) {
                        break;
                    }
                }
                if (!strs.startsWith(strf)) {
                    continue;
                }
                find = true;
                if (secondl.length() > 0) {
                    secondl.append(",");
                }
                HospitalsType types = mappings.get(strs);
                secondl.append("{text:'<span style=\"margin-right:30px;\">" + types.getCode() + "</span>" +
                        "<span>" + types.getName() + "</span>',selectable:false,state:{expanded:false},nodes:[");
                StringBuilder child = new StringBuilder();
                boolean findc = false;
                for (Map.Entry<String, HospitalsType> entry : mappings.entrySet()) {
                    HospitalsType typec = entry.getValue();
                    if (findc) {
                        //遍历完成就退出，进行下一轮循环，减少遍历次数
                        if (!entry.getKey().startsWith(strs)) {
                            break;
                        }
                    }
                    if (entry.getKey().equals(strs) || !entry.getKey().startsWith(strs)) {
                        continue;
                    }
                    findc = true;
                    if (child.length() > 0) {
                        child.append(",");
                    }
                    child.append("{text:'<span style=\"margin-right:30px;\">" + typec.getCode() + "</span>" +
                            "<span>" + typec.getName() + "</span>',selectable:false,state:{expanded:false}}");
                }

                if (child.length() == 0) child = new StringBuilder("{}");
                secondl.append(child).append("]}");
            }
            if (secondl.length() == 0) secondl = new StringBuilder("{}");
            result.append(secondl).append("]}");
        }
        if (result.length() == 0) {
            result = new StringBuilder("{}");
        }
        return result.toString();*/
        return null;
    }

    private String officeJson(List lists) {
        /*Map<String, List<Cv0810011>> mappings = new TreeMap<>();
        for (Cv0810011 ofce : (List<Cv0810011>) lists) {
            String dict = ofce.getCode().substring(0, 1);
            List<Cv0810011> tmplist = mappings.get(dict);
            if (tmplist == null) {
                tmplist = new ArrayList<>();
            }
            tmplist.add(ofce);
            mappings.put(dict, tmplist);
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<Cv0810011>> entry : mappings.entrySet()) {
            if (result.length() > 0) {
                result.append(",");
            }
            result.append("{text:'" + entry.getKey() + "',selectable:false,state:{expanded:false},nodes:[");
            List<Cv0810011> list = entry.getValue();
            StringBuilder node = new StringBuilder();
            for (Cv0810011 cv : list) {
                if (node.length() > 0) {
                    node.append(",");
                }
                node.append("{text:'<span style=\"margin-right:30px;\">" + cv.getCode() + "</span>" +
                        "<span>" + cv.getName() + "</span>',selectable:false}");
            }
            result.append(node).append("]}");
        }
        return result.length() > 0 ? result.toString() : "{}";*/
        return null;
    }

    public byte[] getExcel(String type, String tmpdirpath, HttpServletRequest request, String searchword) {
        /*String filename = sessionManager.getSessionAttr(request, Constants.USERID) + ".xls";
        String filepath = tmpdirpath + filename;
        String tablename = tableTypeName.get(type);
        File jtest = new File(filepath);
        if (!jtest.exists() && jtest.isDirectory()) {
            if (!jtest.mkdirs()) {
                log.error("创建目录{}失败！", filepath);
            }
        }

        if (StringUtils.isEmpty(tablename)) {
            return null;
        }


        ExcelDataFormatter edf = null;
        if ("population".equals(type)) {
            edf = new ExcelDataFormatter();
            Map<String, String> nation = getIdNameMap("nationcode");
            Map<String, String> gender = getIdNameMap("gendercode");
            Map<String, String> idmap = getIdNameMap("idtype");
            edf.set("sex", gender);
            edf.set("nation", nation);
            edf.set("idtype", idmap);
        }

        List list = getAll(type, searchword);
        try {
            if (list != null && list.size() > 0) {
                ExcelUtils.writeToFile(list, edf, filepath);
                File f = new File(filepath);
                byte[] tmp = FileUtils.readFileToByteArray(f);
                if (!f.delete()) {
                    log.info("文件{}未被正常删除", filepath);
                }
                return tmp;
            }
        } catch (Exception ex) {
            log.warn("生成excel文件错误", ex);
        }*/
        return null;
    }

    public Map getIdNameMap(String type) {
        /*Map<String, String> idmap = new HashMap<>();
        if ("nationcode".equals(type)) {
            List<GbT33041991> nation = getAll(type, "");
            for (GbT33041991 n : nation) {
                idmap.put(n.getCode(), n.getMeaning());
            }
        } else if ("gendercode".equals(type)) {
            List<GbT226112003> gender = getAll(type, "");
            for (GbT226112003 g : gender) {
                idmap.put(g.getCode(), g.getName());
            }
        } else if ("idtype".equals(type)) {
            List<Cv0201101> ids = getAll(type, "");
            for (Cv0201101 c : ids) {
                idmap.put(c.getCode(), c.getName());
            }
        }
        return idmap;*/
        return null;
    }

    public ServerCatalog getCorrespondCatalog(String type) {
//        ServerCatalog catalog = catalogService.getCatalogByShort(config.getProperty(Constants.BASE_SUFFIX + type, ""));
        ServerCatalog catalog = null;
        if (catalog == null) {
            return new ServerCatalog();
        } else {
            return catalog;
        }
    }
}
