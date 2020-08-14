package com.topsci.qh.webmanagement.Service.Normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.ServerCatalog;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogList;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogTemplate;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogTemplateType;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.MongodbService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CatalogTemplateService extends BasicService{
    private Map<String, String> templatestatus;
    public final static String DRAFT="A";
    public final static String PUBLISHED = "Y";
    public final static String TLPDEL = "N";
    public final static String TLPINVALID = "V";
    private static Logger log = LoggerFactory.getLogger(CatalogTemplateService.class);

    @Autowired
    private ChangeTaskService taskService;
    @Autowired
    private BusinessSystemService businessSystemService;
    @Resource
    private MongodbService mongodbService;

    public CatalogTemplateService()
    {
        templatestatus = new HashMap<String, String>() {{
            put("Y", "已发布");
            put("A", "草稿");
            put("V", "失效");
        }};
    }


    public Map<String, String> getTemplatestatus() {
        return templatestatus;
    }


    public List<ServerCatalogTemplate> getTemplateByTypePage(String search,String typeuuid)
    {
        Criteria criteria = new Criteria();
//        String hql = "from ServerCatalogTemplate tpl where tpl.typeuuid = ? and (tpl.status = '"+PUBLISHED+"' or tpl.status = '"+DRAFT+"' or tpl.status = '"+TLPINVALID+"' )";
        criteria.and("typeuuid").is(typeuuid);
        if(StringUtils.isNotEmpty(search))
        {
//          hql += " and tpl.name like '%"+search+"%'";
            criteria.orOperator(Criteria.where("status").regex(PUBLISHED),Criteria.where("status").regex(DRAFT),Criteria.where("status").regex(TLPINVALID));
        }
//        hql += "order by tpl.createTime desc,tpl.id";
        List<ServerCatalogTemplate> list = (List<ServerCatalogTemplate>)mongodbService.find(ServerCatalogTemplate.class,criteria, Sort.Direction.DESC,"createTime,id");
        return list;
    }

    /**
     * 根据ID获取接口模板
     * @param id
     * @return
     */
    public ServerCatalogTemplate getTemplate(String id)
    {
        if(StringUtils.isNotEmpty(id)) {
           /* return get(ServerCatalogTemplate.class, Integer.valueOf(id));*/
            String[] key = {"id"};
            Object[] val = {id};
            mongodbService.findOne(ServerCatalogTemplate.class,key,val);
        }
        return null;
    }

    public List<ServerCatalogTemplate> getTemplatesByType(String uuid)
    {
        String hql = "from ServerCatalogTemplate tpl where tpl.typeuuid = ? and tpl.status = ?";

        Criteria criteria = new Criteria();
        criteria.and("typeuuid").is(uuid).and("status").is(PUBLISHED);
        List<ServerCatalogTemplate> list = (List<ServerCatalogTemplate>)mongodbService.find(ServerCatalogTemplate.class,criteria);
        return list;
    }

    public Map<String,String> getListsJson(int id) {
        Map<String,String> result = new HashMap<>();
        if (id == 0) {
            result.put("jsonstr","{}");
            return result;
        }
    /*    List<ServerCatalogList> list = findByHQL(
                "from ServerCatalogList sct where sct.serverCatalogId = ? order by sct.serverElementName desc, sct.id", id);*/

        Criteria criteria = new Criteria();
        criteria.and("serverCatalogId").is(id);
        List<ServerCatalogList> list = (List<ServerCatalogList>)mongodbService.find(ServerCatalogList.class,criteria, Sort.Direction.DESC,"serverElementName");

        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("{");
        Map<Integer, ServerCatalogList> map = new LinkedHashMap<>();
        String uuid = UUID.randomUUID().toString();
        for (ServerCatalogList l : list) {
            sb.append("'").append(i).append("':{'name':'").append(l.getServerElementName()).append("','desc':'")
                    .append(l.getServerElementDescribe()).append("','type':'").append(l.getElementDescribe()).append("','id':'").append(l.getId())
                    .append("','pkey':'").append(l.getKeycol()).append("','dbkey':'").append(l.getDbkey()).append("'},");
            i++;
            map.put(l.getId(), l);
        }
        result.put("beanmap", JSONObject.parseObject(JSON.toJSONString(map)).toJSONString());
        sb.append("'count':'").append(list.size()).append("'}");
        result.put("jsonstr",sb.toString());
        return result;
    }

    public Map<String,String> getTypeUuidNameMap(){
       /* List<ServerCatalogTemplateType> temps = findByHQL("from ServerCatalogTemplateType");*/
        List<ServerCatalogTemplateType> temps = (List<ServerCatalogTemplateType>)mongodbService.findAll(ServerCatalogTemplateType.class);
        Map<String,String> map = new HashMap<>();
        if(temps != null && temps.size()>0){
            temps.forEach(o->map.put(o.getUuid(),o.getTypename()));
        }
        return map;
    }

    /**
     * 更新接口模板
     * @param template
     * @param request
     */
    public void updateTemplate(ServerCatalogTemplate template)
    {
       /* ServerCatalogTemplate sct = getTemplate(template.getId()+"");
        evictObj(sct);
        update(template);
        oprRecord.logOpr(template, sct, "修改接口模板", request);*/
        taskService.templateTask(template.getId(), taskService.UPDATE);
    }

    /**
     * 更新接口模板
     * @param template
     * @param request
     */
    public void saveTemplate(ServerCatalogTemplate template)
    {
        mongodbService.save(template);
       /* oprRecord.logOpr(template, null, "新建接口模板", request);*/
        taskService.templateTask(template.getId(),taskService.UPDATE);
    }

    /**
     * 删除接口模板
     * @param id
     * @param request
     */
    public void deleteTemplate(String id, HttpServletRequest request) {
       /* ServerCatalogTemplate tmp = getTemplate(id);
        evictObj(tmp);
        execUpdateHQL("update ServerCatalogTemplate sc set sc.status = ? where sc.id = ?", CatalogTemplateService.TLPDEL, Integer.parseInt(id));*/
        /*oprRecord.logOpr(null, tmp, "删除接口模板" + id, request);*/
        //execUpdateHQL("delete ServerCatalogList scl where scl.serverCatalogId = " + id);
        String[] key = {"status"};
        Object[] val = {CatalogTemplateService.TLPDEL};
        mongodbService.updateFirst("id",Integer.parseInt(id),key,val,"serverCatalogTemplate");
        mongodbService.remove("serverCatalogId",id,"serverCatalogList");
        taskService.templateTask(Integer.parseInt(id),taskService.DELETE);
    }

    /**
     * 发布模板接口
     * @param id
     * @param request
     */
    public void publishTemplate(String id, HttpServletRequest request)
    {
        //execUpdateHQL("update ServerCatalogTemplate sc set sc.status = ? where sc.id = ?", CatalogTemplateService.PUBLISHED, Integer.parseInt(id));
        /*  oprRecord.logOpr(null, null, "发布接口模板" + id, request);*/
        String[] key = {"status"};
        Object[] val = {CatalogTemplateService.PUBLISHED};
        mongodbService.updateFirst("id",Integer.parseInt(id),key,val,"serverCatalogTemplate");
        taskService.templateTask(Integer.parseInt(id),taskService.UPDATE);
    }

    /**
     * 检查是否有接口在使用模板
     * @param id  模板ID
     * @return
     */
    public int checkTemplateUse(String id)
    {
        /*String hql = "from ServerCatalog sc where sc.deleted != ? and sc.templateId = ?";
        int r = getCount(hql,DELETED,id);*/
        Criteria criteria = new Criteria();
        criteria.and("deleted").ne(DELETED).and("templateId").is(id);
        int r = mongodbService.count(ServerCatalog.class,criteria);
        JSONObject obj = new JSONObject();
        if(r>0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public int checkTemplateRepeated(String uuid, String name) {
        if (StringUtils.isEmpty(uuid)) {
            uuid = "0";
        }
        if (isTemplateNameRepeated(Integer.parseInt(uuid), name)) {
            return 1;
        }
        return 0;
    }

    public void saveTemplateLists(ServerCatalogTemplate template, String json, String beanlist) {
        List<ServerCatalogList> list = new ArrayList<>();
        if (StringUtils.isEmpty(json) || template == null || json.equals("{}")) {
            return;
        }
        List<Integer> listIds = new ArrayList<>();
        try {
            JSONObject object = (JSONObject) JSON.parse(json);
            int count = Integer.parseInt((String) object.get("count"));
            if (count == 0) {
               /* String hql = "delete ServerCatalogList scl where scl.serverCatalogId = " + template.getId();
                execUpdateHQL(hql);*/
                mongodbService.remove("serverCatalogId",template.getId(),"serverCatalogList");
                return;
            }

            Map<String, JSONObject> map = JSONObject.toJavaObject(JSONObject.parseObject(beanlist),Map.class);
            for (int i = 0; i < count; i++) {
                JSONObject subobj = (JSONObject) object.get(i + "");
                int listid = Integer.parseInt(subobj.getString("id"));

                ServerCatalogList l = null;
                if (map != null) {
                    JSONObject tmpl =  map.get(listid+"");
                    l = JSONObject.toJavaObject(tmpl,ServerCatalogList.class) ;
                }
                if (l == null) {
                    l = new ServerCatalogList();
                }

                int length = subobj.getString("name").length();
                l.setServerElementName(length < 100 ? subobj.getString("name") : subobj.getString("name").substring(0, 99));
                length = subobj.getString("type").length();
                l.setElementDescribe(length < 50 ? subobj.getString("type") : subobj.getString("type").substring(0, 49));
                l.setId(listid);
                l.setKeycol(Integer.parseInt(subobj.getString("pkey")));
                l.setDbkey(Integer.parseInt(subobj.getString("dbkey")));
                l.setServerCatalogId(template.getId());
                //l.setUpddatetime(StardTime.format(new Date()));
                length = subobj.getString("desc").length();
                l.setServerElementDescribe(length < 200 ? subobj.getString("desc") : subobj.getString("desc").substring(0, 199));
                list.add(l);
                listIds.add(l.getId());
                mongodbService.save(l);
            }
        } catch (Exception ex) {
            log.warn("转换接口模板{}的字段错误", template.getId(), ex);
        }
        //batchSaveOrUpdateList(list);

        if (listIds.size() > 0) {
           /* String hql = "delete ServerCatalogList scl where scl.serverCatalogId = " + template.getId() + " and scl.id not in(" + idlist + ")";
            execUpdateHQL(hql);*/
           Criteria criteria = new Criteria();
           criteria.and("serverCatalogId").is(template.getId()).and("id").nin(listIds);
           mongodbService.remove(ServerCatalogList.class,criteria);
        }
    }


    public boolean isTemplateNameRepeated(int id, String name) {
        String hql;
        int result;
        if (id != 0) {
            /*hql = "from ServerCatalogTemplate sc where sc.status != ? and sc.name = ? and sc.id != ?";
            result = getCount(hql, CatalogTemplateService.TLPDEL, name, id);*/

            Criteria criteria = new Criteria();
            criteria.and("status").ne(CatalogTemplateService.TLPDEL).and("name").is(name).and("id").is(id);
            result = mongodbService.count(ServerCatalogTemplate.class,criteria);
        } else {
          /*  hql = "from ServerCatalogTemplate sc where sc.status != ? and sc.name = ? ";
            result = getCount(hql, DELETED, name);*/
            Criteria criteria = new Criteria();
            criteria.and("status").ne(DELETED).and("name").is(name);
            result = mongodbService.count(ServerCatalogTemplate.class,criteria);
        }
        return result > 0;
    }


    public List<ServerCatalogTemplateType> getTypes()
    {
       /* String hql = "from ServerCatalogTemplateType";
        return findByHQL(hql);*/
       return (List<ServerCatalogTemplateType>)mongodbService.findAll(ServerCatalogTemplateType.class);
    }

    public List<ServerCatalogTemplateType> getTypesByPage(String searchword)
    {
     /*   String hql;*/
        Criteria criteria = new Criteria();

        if(StringUtils.isNotEmpty(searchword)) {
            //hql = "from ServerCatalogTemplateType st where st.typeName like '%"+searchword+"%'";
            criteria.and("typeName").regex(searchword);
        }
        //hql += " order by st.uuid desc";
        List<ServerCatalogTemplateType> list = (List<ServerCatalogTemplateType>)mongodbService.find(ServerCatalogTemplateType.class,criteria, Sort.Direction.DESC,"uuid");
        return list;
    }

    public ServerCatalogTemplateType getType(String uuid)
    {
        String[] key = {"uuid"};
        Object[] val = {uuid};
        return  (ServerCatalogTemplateType)mongodbService.findOne(ServerCatalogTemplateType.class,key,val);
    }

    public void saveType(ServerCatalogTemplateType type)
    {
        type.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        type.setCreatetime(LocalDateTime.now());
        mongodbService.save(type);
        /*save(type);*/
        /*oprRecord.logOpr(type,null,"新建模板类别",request);*/
    }

    public void udpateType(ServerCatalogTemplateType type)
    {
        mongodbService.save(type);
       /* ServerCatalogTemplateType tmp = getType(type.getUuid());
        evictObj(tmp);*/
        /*update(type);
        oprRecord.logOpr(type,null,"修改模板类别",request);*/
    }

    public void deleteType(String uuid)
    {
       /* ServerCatalogTemplateType tmp = getType(uuid);
        evictObj(tmp);
        String hql = "delete ServerCatalogTemplateType st where st.uuid = ? ";
        execUpdateHQL(hql,uuid);
        oprRecord.logOpr(null,tmp,"删除业务类别",request);*/
       mongodbService.remove("uuid",uuid,"serverCatalogTemplateType");
    }

    public int checkrepeatType(String id,String name)
    {
        //String hql;
        int i = 0;
        if(StringUtils.isNotEmpty(id)) {
            //hql = "from ServerCatalogTemplateType st where st.typename = ? and st.uuid != '" + id+"'";
            Criteria criteria = new Criteria();
            criteria.and("typename").ne(name).and("uuid").ne(id);
            i = mongodbService.count(ServerCatalog.class,criteria);
        }
        else
        {
            //hql = "from ServerCatalogTemplateType st where st.typename = ?";
            Criteria criteria = new Criteria();
            criteria.and("typename").ne(name);
            i = mongodbService.count(ServerCatalog.class,criteria);
        }
        if(i >= 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public int checkTemplateTypeUse(String id)
    {
       /* String hql = "from ServerCatalogTemplate sc where sc.status != ? and sc.typeuuid = ?";
        int r = getCount(hql,DELETED,id);
        JSONObject obj = new JSONObject();*/
        Criteria criteria = new Criteria();
        criteria.and("typeuuid").is(id).and("status").ne(DELETED);
        int r = mongodbService.count(ServerCatalog.class,criteria);
        if(r>0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

}
