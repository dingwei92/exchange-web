package com.topsci.qh.webmanagement.Service.Normal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.MongodbService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Tools.Config;
import com.topsci.qh.webmanagement.Tools.DateUtil;
import com.topsci.qh.webmanagement.Tools.ExcelUtil.ExcelUtils;
import com.topsci.qh.webmanagement.Tools.FileTool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lzw.
 * 16-6-27
 */
@Service
@SuppressWarnings("unchecked")
public class CatalogService extends BasicService {

    @Resource
    private ChangeTaskService taskService;
    @Resource
    private BusinessSystemService businessSystemService;
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private RelUserBusinessService relUserBusinessService;
    @Resource
    private FileTool fileTool;
    @Resource
    private Config config;
    @Resource
    private CatalogTemplateService catalogTemplateService;
    @Resource
    private MongodbService mongodbService;

    private Logger log = LoggerFactory.getLogger(CatalogService.class);
    private Map<String, String> status;

    private Map<String, String> type;
    private Map<String, String> pubtype;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected final String AUDITING = "A";
    protected final String AUDITFAIL ="F";



    public final static String PUB_INT = "1";
    public final static String PUB_DB = "2";



    public CatalogService() {
        status = new HashMap<String, String>() {{
            put("Y", "是");
            put("N", "否");
        }};


        type = new HashMap<String,String>(){{
            put(UNDELETED,"审核通过");
            put(DELETED,"已删除");
            put(AUDITING,"审核中");
            put(AUDITFAIL,"审核未通过");
        }};

        pubtype = new HashMap<String,String>(){{
            put(PUB_INT,"接口");
            put(PUB_DB,"数据库");
        }};
    }

    public Map<String, String> getPubtype() {
        return pubtype;
    }

    public Map<String, String> getStatus() {
        return status;
    }


    public void saveCatalog(ServerCatalog catalog, MultipartFile file, HttpServletRequest request) {
        try {
            if(file!=null) {
                catalog.setDocPath(fileTool.saveCatalogDoc(file.getInputStream(), file.getOriginalFilename()));
            }
        }
        catch (Exception ex)
        {
            log.error("保存接口"+catalog.getServerName()+"的文档出错！",ex);
        }
        mongodbService.save(catalog);
       /* save(catalog);
        oprRecord.logOpr(catalog, null, "新建接口", request);*/
        taskService.catalogTask(catalog.getId(), taskService.CREATE);
    }


    /**
     * 批量新建接口模板
     * @param userid
     * @param ifjson
     */
    public void batchSaveTemplate(String userid,String typeuuid, JSONObject ifjson)
    {
        JSONArray array = ifjson.getJSONArray("data");
        for(int i = 0 ; i < array.size(); i++)
        {
            JSONObject obj = array.getJSONObject(i);
            ServerCatalogTemplate template = new ServerCatalogTemplate();
            template.setCreateUser(userid);
            template.setTypeuuid(typeuuid);
            template.setStatus(CatalogTemplateService.PUBLISHED);
            template.setRemark(obj.getString("remark"));
            template.setName(obj.getString("name"));
            template.setCreateTime(LocalDateTime.now());
            template.setTplcode(obj.getString("tplcode"));
            /*save(template);*/
            mongodbService.save(template);

            JSONArray cols = obj.getJSONArray("cols");
            for(int j = 0 ; j < cols.size() ; j++)
            {
                JSONObject subobj = cols.getJSONObject(j);
                ServerCatalogList l = new ServerCatalogList();
                int length = subobj.getString("name").length();
                l.setServerElementName(length < 100 ? subobj.getString("name") : subobj.getString("name").substring(0, 99));
                length = subobj.getString("type").length();
                l.setElementDescribe(length < 50 ? subobj.getString("type") : subobj.getString("type").substring(0, 49));
                l.setKeycol(("Y".equals(subobj.getString("pkey")))?1:0);
                l.setDbkey(("Y".equals(subobj.getString("dkey")))?1:0);
                l.setServerCatalogId(template.getId());
                //l.setUpddatetime(StardTime.format(new Date()));
                length = subobj.getString("desc").length();
                l.setServerElementDescribe(length < 200 ? subobj.getString("desc") : subobj.getString("desc").substring(0, 199));
//                save(l);
                mongodbService.save(l);
            }
        }
    }

    public void updateCatalog(ServerCatalog catalog, MultipartFile file, HttpServletRequest request) {
        if(file != null && !file.isEmpty()) {
            try {
                catalog.setDocPath(fileTool.saveCatalogDoc(file.getInputStream(), file.getOriginalFilename()));
            }
            catch (Exception ex)
            {
                log.error("更新接口"+catalog.getServerName()+"的文档出错！",ex);
            }
        }
        //catalog.setUpddatetime(StardTime.format(new Date()));
        mongodbService.save(catalog);
       /* ServerCatalog tmp = get(ServerCatalog.class, catalog.getId());
        evictObj(tmp);
        update(catalog);
        oprRecord.logOpr(catalog, tmp, "修改接口", request);*/
        taskService.catalogTask(catalog.getId(),taskService.UPDATE);
    }

    public List<ServerCatalog> getCatalogsByDBID(String search,String dbid){
        /*String hql = "select sc from ServerCatalog sc,ServerCatalogDbPub scdb where sc.deleted = ? " +
                "and scdb.uuid = sc.dbId and scdb.dbId = ? ";*/
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED).and("entity.dbId").is(dbid);
        if(StringUtils.isNotEmpty(search))
        {
            /*hql += "and (sc.serverName like '%" + search + "%' or sc.serverShort like '%" + search + "%')";*/
            criteria.orOperator(Criteria.where("serverName").regex(search),Criteria.where("serverShort").regex(search));
        }
        /*hql +=" order by sc.upddatetime desc,sc.businessSystemId,sc.id desc";*/
        /*page.setTotalResult(getCountCustom(hql.replace("select sc","select count(sc)"),UNDELETED,dbid));*/

        LookupOperation lookupToLots = LookupOperation.newLookup().
                from("serverCatalogDbPub").//关联表名 lots
                localField("uuid").//关联字段
                foreignField("dbId").//主表关联字段对应的次表字段
                as("entity");//查询结果集合名

        AggregationOperation match = Aggregation.match(criteria);
        AggregationOperation sort = Aggregation.sort(Sort.Direction.DESC, "type", "createdDate");
        Aggregation counts = Aggregation.newAggregation(lookupToLots,match,sort);
        List<ServerCatalog> list = (List<ServerCatalog>)mongodbService.aggregate(counts,"serverCatalog" ,ServerCatalog.class);

        /*return findPageByHQL(hql,page,UNDELETED,dbid);*/
        return list;
    }

    public List<ServerCatalog> getCatalogsByTemplateAndNullDB(String search,String tuuid){
        /*String hql = "select sc from ServerCatalog sc,ServerCatalogTemplate sct,ServerCatalogDbPub scdp where sc.deleted = ? " +
                "and scdp.dbId is null and sct.typeuuid = ? and sct.id = sc.templateId and scdp.uuid = sc.dbId ";*/
        LookupOperation lookupServerCatalogTemplate = LookupOperation.newLookup().
                from("serverCatalogTemplate").//关联表名 lots
                localField("id").//关联字段
                foreignField("templateId").//主表关联字段对应的次表字段
                as("serverCatalogTemplate");//查询结果集合名
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED).and("serverCatalogTemplate.typeuuid").is(tuuid);
        if(StringUtils.isNotEmpty(search))
        {
            //hql += "and (sc.serverName like '%" + search + "%' or sc.serverShort like '%" + search + "%')";
            criteria.orOperator(Criteria.where("serverName").regex(search),Criteria.where("serverShort").regex(search));
        }
        //hql +=" order by sc.upddatetime desc,sc.businessSystemId,sc.id desc";
        //page.setTotalResult(getCountCustom(hql.replace("select sc","select count(sc)"),UNDELETED,tuuid));
        //return findPageByHQL(hql,page,UNDELETED,tuuid);
        AggregationOperation match = Aggregation.match(criteria);
        AggregationOperation sort = Aggregation.sort(Sort.Direction.DESC, "upddatetime","businessSystemId", "id");
        Aggregation counts = Aggregation.newAggregation(lookupServerCatalogTemplate,match,sort);
        List<ServerCatalog> list = (List<ServerCatalog>)mongodbService.aggregate(counts,"serverCatalog" ,ServerCatalog.class);
        return list;
    }

    public ServerCatalog getCatalog(String id) {
        //return get(ServerCatalog.class, Integer.parseInt(id));
        String[] key = {"id"};
        Object[] val = {Integer.parseInt(id)};
        ServerCatalog bs = (ServerCatalog)mongodbService.findOne(ServerCatalog.class,key,val);
        return bs;
    }

    public boolean isShortRepeated(int id, String sht) {
        //String hql;
        int result;
        if (id != 0) {
            /*hql = "from ServerCatalog sc where sc.deleted != ? and sc.serverShort = ? and sc.id != ?";
            result = getCount(hql, DELETED, sht, id);*/
            Criteria criteria = new Criteria();
            criteria.and("deleted").ne(DELETED).and("serverShort").ne(sht).and("id").ne(id);
            result = mongodbService.count(ServerCatalog.class,criteria);
        } else {
            /*hql = "from ServerCatalog sc where sc.deleted != ? and sc.serverShort = ? ";
            result = getCount(hql, DELETED, sht);*/
            Criteria criteria = new Criteria();
            criteria.and("deleted").ne(DELETED).and("serverShort").ne(sht);
            result = mongodbService.count(ServerCatalog.class,criteria);
        }
        return result > 0;
    }

    public boolean isNameRepeated(int id, String name) {
        String hql;
        int result;
        if (id != 0) {
           /* hql = "from ServerCatalog sc where sc.deleted != ? and sc.serverName = ? and sc.id != ?";
            result = getCount(hql, DELETED, name, id);*/
            Criteria criteria = new Criteria();
            criteria.and("deleted").ne(DELETED).and("serverName").is(name).and("id").ne(id);
            result = mongodbService.count(ServerCatalog.class,criteria);
        } else {
          /*  hql = "from ServerCatalog sc where sc.deleted != ? and sc.serverName = ? ";
            result = getCount(hql, DELETED, name);*/
            Criteria criteria = new Criteria();
            criteria.and("deleted").ne(DELETED).and("serverName").is(name);
            result = mongodbService.count(ServerCatalog.class,criteria);
        }
        return result > 0;
    }


    public void deleteCatalog(String id) {
       /* ServerCatalog tmp = getCatalog(id);
        evictObj(tmp);
        execUpdateHQL("update ServerCatalog sc set sc.deleted = ? where sc.id = ?", DELETED, Integer.parseInt(id));
        oprRecord.logOpr(null, tmp, "删除接口" + id, request);
        execUpdateHQL("delete ServerCatalogList scl where scl.serverCatalogId = " + id);*/
        String[] key = {"deleted"};
        Object[] val = {DELETED};
        mongodbService.updateFirst("id",Integer.parseInt(id),key,val,"serverCatalog");
        mongodbService.remove("serverCatalogId",Integer.parseInt(id),"serverCatalogList");
        taskService.catalogTask(Integer.parseInt(id), taskService.DELETE);
    }

    public List<ServerCatalog> getAllCatalog() {
      /*  String hql = "from ServerCatalog sc where sc.deleted = ? order by sc.serverShort asc,sc.id desc ";
        return findByHQL(hql, UNDELETED);*/
        String[] key = {"deleted"};
        Object[] val = {UNDELETED};
        List<ServerCatalog> rels = (List<ServerCatalog>)mongodbService.find(ServerCatalog.class,key,val);
        return  rels;
    }

    public Map<Integer, String> getCatalogIdNameMap(boolean string) {
        List<ServerCatalog> catalogs = getAllCatalog();
        Map<Integer, String> tmp = new LinkedHashMap<>();
        if (!string) {
            for (ServerCatalog catalog : catalogs) {
                tmp.put(catalog.getId(), catalog.getServerName());
            }
        } else {
            for (ServerCatalog catalog : catalogs) {
                tmp.put(catalog.getId(), catalog.getServerName() + "(" + catalog.getServerShort() + ")");
            }
        }
        return tmp;
    }
    
    public Map<Integer, String> getCatalogIdNameMapByBsid(int bid) {
    	/*String hql = "from ServerCatalog sc where sc.deleted = ? and BUSINESS_SYSTEM_ID = "+bid+" order by sc.serverShort asc,sc.id desc ";
        List<ServerCatalog> catalogs = findByHQL(hql, UNDELETED);*/

        String[] key = {"deleted","businessSystemId"};
        Object[] val = {UNDELETED,bid};
        List<ServerCatalog> catalogs = (List<ServerCatalog>)mongodbService.find(ServerCatalog.class,key,val,"id");

        Map<Integer, String> tmp = new LinkedHashMap<>();
        for (ServerCatalog catalog : catalogs) {
            tmp.put(catalog.getId(), catalog.getServerName() + "(" + catalog.getServerShort() + ")");
        }
        return tmp;
    }

    
    /**
     * 通过id集合查询对应的map集合
     * @param ids
     * @return
     */
    public Map<Integer, String> getCatalogIdNameMapById(String ids) {
        List<ServerCatalog> catalogs = getAllCatalog();
        Map<Integer, String> tmp = new LinkedHashMap<>();
        if (ids!=null && !"".equals(ids)) {
        	String[] arr = ids.split(",");
        	List<String> list = Arrays.asList(arr);
            //30,50,48
        	for (ServerCatalog catalog : catalogs) {
            	if (list.contains(catalog.getId()+"")) {
            		tmp.put(catalog.getId(), catalog.getServerName() + "(" + catalog.getServerShort() + ")");
    			}
			}
        }
        return tmp;
    }
    
    public List<ServerCatalog> getCatalogIdName(int id) {
        List<ServerCatalog> catalogs = getAllCatalog();
        catalogs = catalogs.stream().filter(s->s.getBusinessSystemId() == id).collect(Collectors.toList());
        return catalogs;
    }

    public List<ServerCatalog> getCatalogByPublishTypeByPage(String type,String search,String dbid,PageInfo pageInfo)
    {
        /*String hql = "select sc from ServerCatalog sc,ServerCatalogDbPub scdb where sc.deleted = ? and sc.publishType = ? " +
                "and scdb.dbId = ? and scdb.uuid = sc.dbId ";*/
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED).and("entity.dbId").is(dbid).and("publishType").is(type);
        if(StringUtils.isNotEmpty(search))
        {
            //hql += " and (sc.serverName like '%"+search+"%' or sc.serverShort like '%"+search+"%') ";
            criteria.orOperator(Criteria.where("serverName").regex(search),Criteria.where("serverShort").regex(search));
        }
        //hql += "order by sc.serverShort asc,sc.id desc ";
        LookupOperation lookupToLots = LookupOperation.newLookup().
                from("serverCatalogDbPub").//关联表名 lots
                localField("uuid").//关联字段
                foreignField("dbId").//主表关联字段对应的次表字段
                as("entity");//查询结果集合名

        AggregationOperation match = Aggregation.match(criteria);
        AggregationOperation sort = Aggregation.sort(Sort.Direction.DESC, "serverShort", "id");
        Aggregation counts = Aggregation.newAggregation(lookupToLots,match,sort);
        List<ServerCatalog> list = (List<ServerCatalog>)mongodbService.aggregate(counts,"serverCatalog" ,ServerCatalog.class);
        return list;
        //pageInfo.setTotalResult(getCountCustom(hql.replace("select sc","select count(sc)"), UNDELETED,type,dbid));
        //return findPageByHQL(hql,pageInfo, UNDELETED,type,dbid);
    }

    public String getSubscriptionCatalog(String bsid) {
        String result = "{\"result\":[";
        int count = 0;
        
        if (bsid != null && !"".equals(bsid)) {
        	//通过业务id，查找出已经订阅的所有服务
        	List<ServerSubscribes> ssList = subscribeService.getScribesList(Integer.parseInt(bsid));
        	//通过获取可订阅的业务名称map集合
            if (ssList != null) {
    			String ids = "";
            	for (ServerSubscribes serverSubscribes : ssList) {
            		ids += serverSubscribes.getServerCatalogId()+",";
    			}
            	
            	Map<Integer, String> catalogs = getCatalogIdNameMapById(ids);
            	if (!catalogs.isEmpty()) {
                    for (Map.Entry<Integer, String> entry : catalogs.entrySet()) {
                    	if (count > 0) {
                            result += ",";
                        } 
                    	result += "{\"k\":\"" + entry.getKey() + "\",\"v\":\"" + entry.getValue() + "\"}";
                        count++;
                    }
                }
    		}
		}
        result += "]}";
        return result;
    }
    
    /**
     * 获取是否是基础数据的列表  
     * @param search
     * @param basic--true 基础数据   false 业务数据
     * @return
     */
    public List<ServerCatalog> getCatalogsIsOrNotBasic(String search, boolean basic) {
        List<ServerCatalog> catalogs = new ArrayList<>();
        Map basicbs = businessSystemService.getBusinessSystemIdNameMapBasic(basic); //获取对应的服务集合（基础或者非基础）
        List<ServerCatalog> tmplist = getAllCatalog();
        for (ServerCatalog entry : tmplist) {
            if (basicbs.get(entry.getBusinessSystemId() + "") == null) {
                continue;
            }
            if(StringUtils.isNotEmpty(search)){
                if(!entry.getServerName().contains(search) && !entry.getServerShort().contains(search))
                {
                    continue;
                }
            }
            catalogs.add(entry);
        }

        return catalogs;
    }



    public void deleteByBusiness(int id) {
      /*  String hql = "from ServerCatalog sc where sc.businessSystemId = ? and sc.deleted != ?";
        List<ServerCatalog> list = findByHQL(hql, id, DELETED);*/
        Criteria criteria = new Criteria();
        criteria.and("businessSystemId").is(id).and("deleted").ne(DELETED);
        List<ServerCatalog> list = (List<ServerCatalog>)mongodbService.find(ServerCatalog.class,criteria);

        if (list != null) {
            for (ServerCatalog sc : list) {
                deleteCatalog(sc.getId() + "");
            }
        }
    }

    public String checkRepeated(String uuid, String name, String sht) {
        if (StringUtils.isEmpty(uuid)) {
            uuid = "0";
        }
        if (isNameRepeated(Integer.parseInt(uuid), name)) {
            return "{\"result\":\"1\"}";
        }
        if (isShortRepeated(Integer.parseInt(uuid), sht)) {
            return "{\"result\":\"2\"}";
        }
        return "{\"result\":\"0\"}";
    }


    public Map<String, Object> getDoc(String id) {
        Map<String, Object> result = new HashMap<>();
        ServerCatalog catalog = getCatalog(id);
        if (catalog == null) {
            return result;
        }
        ServerCatalogTemplate template = catalogTemplateService.getTemplate(catalog.getTemplateId());
        if(template == null)
        {
            return result;
        }
        //List<ServerCatalogList> list = findByHQL("from ServerCatalogList sct where sct.serverCatalogId = ? order by sct.id", template.getId());

        String[] key = {"serverCatalogId"};
        Object[] val = {id};
        List<ServerCatalogList> list = (List<ServerCatalogList>)mongodbService.find(ServerCatalogList.class,key,val,"id");


        Object[] titles = new Object[5];
        List<Object[]> datas = new ArrayList<>();

        titles[0]="序号";
        titles[1]="名称";
        titles[2]="字段类型";
        titles[3]="是否筛选";
        titles[4]="备注";

        for(int i = 0 ; i < list.size();i++) {
            ServerCatalogList scl = list.get(i);
            Object[] objs = new Object[5];
            int j = 0;
            objs[j++] = i;
            objs[j++] = scl.getServerElementName();
            objs[j++] = scl.getElementDescribe();
            objs[j++] = scl.getKeycol() ==0?"否":"是";
            objs[j++] = scl.getServerElementDescribe();
            datas.add(objs);
        }
        datas.add(new Object[]{"备注",StringUtils.isNotEmpty(catalog.getRemark())?catalog.getRemark():""});

        List multititle = new ArrayList<>();
        multititle.add(titles);
        List multidata = new ArrayList();
        multidata.add(datas);
        String filepath = config.getTmpPathDocs()+catalog.getServerName()+"("+catalog.getServerShort()+")接口文档_"+ DateUtil.getNow4()+".xlsx";
        File f;
        byte[] b = null;
        try {
            ExcelUtils.writeToFile(filepath, new String[]{"sheet1"}, multititle, multidata);
            f = new File(filepath);
            try {
                b = FileUtils.readFileToByteArray(f);
            } catch (Exception ex) {
                log.warn("读取文件{}错误!{}", filepath, ex);
            }
        }
        catch (Exception ex)
        {
            log.error("接口"+catalog.getId()+"的文档生成失败！",ex);
        }

        result.put("b", b);
        result.put("f", filepath.substring(filepath.lastIndexOf("/")+1));
        return result;
    }
    
    public Map<String, Object> getHelpDoc(String type) {
        Map<String, Object> result = new HashMap<>();
        String filepath;
        String dir = this.getClass().getClassLoader().getResource("/").getPath();
        if(type.equals("1")){	
        	filepath = dir+ "file/guanliyuan.pdf";
        }else{
        	filepath = dir+ "file/putong.pdf";
        }
        File f = new File(filepath);
        byte[] b = null;
        try {
            b = FileUtils.readFileToByteArray(f);
        } catch (Exception ex) {
            log.warn("读取文件{}错误!{}", filepath, ex);
        }
        result.put("b", b);
        String downname;
        if (type.equals("1")) {
            downname = "UG.10035.001a数据交换集成平台操作手册（管理员）.pdf";
        } else {
            downname = "UG.10036.001a数据交换集成平台操作手册（普通用户）.pdf";
        }
        result.put("f", downname);
        return result;
    }

    public Map<String, String> getShortNameMap() {
        List<ServerCatalog> catalogs = getAllCatalog();
        Map<String, String> map = new HashMap<>();
        for (ServerCatalog bs : catalogs) {
            map.put(bs.getServerShort(), bs.getServerName());
        }
        return map;
    }
    
    public String alloweditdel(String uuid)
    {
        /*String hql = "from ServerSubscribes where serverCatalogId = ? and (deleted = ? or deleted = ?)";
        List<ServerSubscribes> res = findByHQL(hql,Integer.parseInt(uuid),UNDELETED,AUDITING);*/

        Criteria criteria = new Criteria();
        criteria.and("serverCatalogId").is(uuid);
        criteria.andOperator(Criteria.where("deleted").is(UNDELETED).orOperator(Criteria.where("deleted").is(AUDITING)));
        int count = mongodbService.count(ServerSubscribes.class,criteria);
        if(count > 0){
        	return "{\"result\":\"false\"}";
        }else{
        	return "{\"result\":\"true\"}";
        }
    }

	public Map<String, String> getType() {
		return type;
	}



    /**
     * 获取所有发布的接口模板
     * @return
     */
    public List<ServerCatalogTemplate> getPublishedTemplates()
    {
       /* String hql = "from ServerCatalogTemplate sc where sc.status = '"+CatalogTemplateService.PUBLISHED+"' order by sc.id";
        return findByHQL(hql);*/
        Criteria criteria = new Criteria();
        criteria.and("status").is(CatalogTemplateService.PUBLISHED);
        List<ServerCatalogTemplate> list = (List<ServerCatalogTemplate>)mongodbService.find(ServerCatalogTemplate.class,criteria, Sort.Direction.DESC,"id");
        return list;
    }

    /**
     * 获取所有发布的接口模板
     * @return
     */
    public List<ServerCatalogTemplate> getPublishedTemplatesByTypeUuid(String uuid)
    {
       /* String hql = "from ServerCatalogTemplate sc where sc.status = '"+CatalogTemplateService.PUBLISHED+"' " +
                "and sc.typeuuid = '"+uuid+"' order by sc.id";
        return findByHQL(hql);*/
        Criteria criteria = new Criteria();
        criteria.and("status").is(CatalogTemplateService.PUBLISHED).and("typeuuid").is(uuid);
        List<ServerCatalogTemplate> list = (List<ServerCatalogTemplate>)mongodbService.find(ServerCatalogTemplate.class,criteria, Sort.Direction.DESC,"id");
        return list;
    }



    /**
     * 获取接口使用数据库发布的数据库信息
     * @param uuid
     * @return
     */
    public String getDBDetailInfo(String uuid)
    {
        JSONObject json = new JSONObject();
        if(StringUtils.isNotEmpty(uuid)) {
            /*ServerCatalogDbPub pub = get(ServerCatalogDbPub.class,uuid);*/
            String[] key = {"uuid"};
            Object[] val = {uuid};
            ServerCatalogDbPub pub = (ServerCatalogDbPub)mongodbService.findOne(ServerCatalogDbPub.class,key,val);
            if(pub !=null) {
                json.put("dbid", pub.getDbId());
                json.put("tblname", pub.getTableName());
                json.put("tblwhere", pub.getTableWhere());
                json.put("tbltype", pub.getSelectType());
                json.put("sql", pub.getSelectSql());
                json.put("datecol", pub.getDateCol());
                json.put("cols", JSON.parseObject(pub.getCols().trim()));
                json.put("priority",pub.getPriority());
            }
            else
            {
                log.info("未找到uuid为"+uuid+"的接口数据库信息");
            }
        }
        return json.toJSONString();
    }

    /**
     * 保存接口使用数据库发布的数据库信息
     * @param dbinfo
     * @return
     */
    public String saveDBInfo(String dbinfo,String dbid)
    {
        ServerCatalogDbPub pub = new ServerCatalogDbPub();
        if(StringUtils.isNotEmpty(dbid))
        {
            pub.setUuid(dbid);
        }
        else
        {
            pub.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        }
        try {
            JSONObject json = JSON.parseObject(dbinfo);
            pub.setDbId(json.getString("dbid"));
            pub.setTableName(json.getString("tblname"));
            pub.setTableWhere(json.getString("tblwhere"));
            pub.setSelectType(json.getString("tbltype"));
            pub.setSelectSql(json.getString("sql"));
            pub.setDateCol(json.getString("datecol"));
            pub.setDateColType(json.getString("datecoltype"));
            pub.setCols(json.getString("cols"));
            pub.setPriority(json.getString("priority"));
           mongodbService.save(pub);
        }
        catch (Exception ex)
        {
            log.error("保存数据库信息错误！："+dbinfo);
        }
        return pub.getUuid();
    }



    public List<ServerCatalogDbPub> getCatalogDbPubs(List<ServerCatalog> catalogs)
    {
        List<ServerCatalogDbPub> dbp = new ArrayList<>();
        for(ServerCatalog sc : catalogs)
        {
           /* ServerCatalogDbPub info = get(ServerCatalogDbPub.class,sc.getDbId());*/
            String[] key = {"uuid"};
            Object[] val = {sc.getDbId()};
            ServerCatalogDbPub info = (ServerCatalogDbPub)mongodbService.findOne(ServerCatalogDbPub.class,key,val);
            if(info != null) {
                dbp.add(info);
            }
        }
        return dbp;
    }


    public Map<String,String> getDBInfoUuidNameMap(){
       /* String hql = "from ServerCatalogDBinfo";
        List<ServerCatalogDBinfo> infos = findByHQL(hql);*/

        List<ServerCatalogDBinfo> infos = (List<ServerCatalogDBinfo>)mongodbService.findAll(ServerCatalogDBinfo.class);

        Map<String,String> map = new HashMap<>();
        if(infos != null && infos.size()>0){
            infos.forEach(o->map.put(o.getUuid(),o.getSourcename()));
        }
        return map;
    }

}
