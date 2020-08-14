package com.topsci.qh.webmanagement.Service.Normal;

import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.MongodbService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Tools.FileTool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lzw.
 * 16-6-30
 */
@Service
@SuppressWarnings("unchecked")
public class SubscribeService extends BasicService {
    public static Logger logger = LoggerFactory.getLogger(SubscribeService.class);
    @Resource
    private ChangeTaskService taskService;
    @Resource
    private BusinessSystemService businessSystemService;
    @Resource
    private CatalogService catalogService;
    @Resource
    private FileTool fileTool;
    @Resource
    private MongodbService mongodbService;

    private Map<String,String> status;
    private Map<String,String> type;
    private Map<String,String> enable;
    public final String FAILED = "F";
    public final String APPLY = "A";
    public final String ENABLED = "Y";
    public final String DISABLED = "N";

    public SubscribeService()
    {
        status = new LinkedHashMap<String,String>(){{
            put(DELETED,"是");
            put(UNDELETED,"否");
        }};
        enable = new LinkedHashMap<String,String>(){{
            put(ENABLED,"启用");
            put(DISABLED,"禁用");
        }};
        type = new HashMap<String,String>(){{
            put(UNDELETED,"审核通过");
            put(DELETED,"已删除");
            put(APPLY,"审核中");
            put(FAILED,"审核未通过");
        }};

    }

    public Map<String, String> getType() {
        return type;
    }

    public Map<String, String> getEnable() {
        return enable;
    }

    public void saveSubscribe(ServerSubscribes subscribes, boolean task)
    {
        if("Y".equals(subscribes.getIsFull()))
        {
            subscribes.setEndTime(StardTime.format(new Date()));
        }else{
        	 subscribes.setStartTime("");
        }
        //save(subscribes);
        mongodbService.save(subscribes);
       /* oprRecord.logOpr(subscribes,null,"新建订阅"+subscribes.getId(),request);*/
        if(task) {
            taskService.subscribeTask(subscribes.getId(), taskService.CREATE);
        }
    }


    public void updateSubscribe(ServerSubscribes subscribes)
    {
       /* ServerSubscribes tmp = get(ServerSubscribes.class,subscribes.getId());
        evictObj(tmp);*/
        subscribes.setUpddatetime(StardTime.format(new Date()));
        /*update(subscribes);*/
        mongodbService.save(subscribes);
        //oprRecord.logOpr(subscribes,tmp,"修改订阅"+subscribes.getId(),request);
        taskService.subscribeTask(subscribes.getId(),taskService.UPDATE);
    }

    /**
     * 根据ID获取订阅字段
     * @param id
     * @return
     */
    public List<ServerSubscribesList> getScribeListsById(int id)
    {
        /*String hql = "from ServerSubscribesList ssl where ssl.serverSubscribesId = ? order by ssl.id";
        return findByHQL(hql,id);*/
        Criteria criteria = new Criteria();
        if(id != 0) {
            criteria.and("serverSubscribesId").is(id);
        }
        List<ServerSubscribesList> list = (List<ServerSubscribesList>)mongodbService.find(ServerSubscribesList.class,criteria,Sort.Direction.DESC,"id");
        return list;
    }

    public List<ServerSubscribes> getScribesList(int businessSystemId)
    {
        /*String hql = "from ServerSubscribes ss where ss.deleted =? and ss.businessSystemId =? order by ss.id";
        return findByHQL(hql,UNDELETED,businessSystemId);*/
        Criteria criteria = new Criteria();
        criteria.and("businessSystemId").is(businessSystemId).and("deleted").is(UNDELETED);
        List<ServerSubscribes> list = (List<ServerSubscribes>)mongodbService.find(ServerSubscribes.class,criteria,Sort.Direction.DESC,"id");
        return list;
    }

    public  List<ServerSubscribesList> getScribeConditions(int bid,int cid)
    {
        int sid = 0;
        try {
            sid = getServerSubscribesid(cid,bid);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            logger.warn("未找到订阅记录，bid:"+bid+"  cid:"+cid);
            return null;
        }
        return getScribeConditions(sid);
    }

    public List<ServerSubscribesList> getScribeConditions(int sid)
    {
        List<ServerSubscribesList> lists = getScribeListsById(sid);
        return lists;
    }

    public List<ServerSubscribes> getSubscribes(String search, String bsid)
    {
        String hql;
        Criteria criteria = new Criteria();
        if (StringUtils.isNotEmpty(search)) {
            List<BusinessSystem> bslist = businessSystemService.getAllBusinessSystems();
            List<ServerCatalog> cslist = catalogService.getAllCatalog();
            List<Integer> listBss = new ArrayList<>();
            List<Integer> listCss = new ArrayList<>();
           /* for(BusinessSystem bs:bslist)
            {
                if(bs.getSystemName().contains(search))
                {
                    listBss.add(bs.getId());
                }
            }*/
            for(ServerCatalog cs:cslist)
            {
                if(cs.getServerName().contains(search))
                {
                    listCss.add(cs.getId());
                }
            }
            String where="";
           if(listBss.size() > 0)
            {
                criteria.and("businessSystemId").in(listBss);
            }
            if(listCss.size() > 0)
            {
                criteria.and("serverCatalogId").in(listCss);
            }
          /*  hql = "from ServerSubscribes ss where ss.deleted !=? and ss.businessSystemId = ? " +
                    where + "  order by ss.businessSystemId,ss.upddatetime desc,ss.id";*/
            criteria.and("deleted").ne(DELETED).and("businessSystemId").is(Integer.parseInt(bsid));
            if(listBss.size() == 0 && listCss.size() == 0)
            {
                criteria = new Criteria();
                criteria.and("id").is(-1);
                List<ServerSubscribes> list = (List<ServerSubscribes>)mongodbService.find(ServerSubscribes.class,criteria,Sort.Direction.DESC,"businessSystemId,upddatetime");
               return list;
            }

        } else {
            //hql = "from ServerSubscribes ss where ss.deleted !=? and ss.businessSystemId = ?  order by ss.upddatetime desc,ss.businessSystemId,ss.id desc";
            criteria.and("deleted").ne(DELETED).and("businessSystemId").is(Integer.parseInt(bsid));
        }
       /* page.setTotalResult(getCount(hql, DELETED,Integer.parseInt(bsid)));
        List<ServerSubscribes> res = findPageByHQL(hql, page, DELETED,Integer.parseInt(bsid));*/
        List<ServerSubscribes> res = (List<ServerSubscribes>)mongodbService.find(ServerSubscribes.class,criteria,Sort.Direction.DESC,"upddatetime,businessSystemId,id");
        for(ServerSubscribes ss : res){
        	if (ss.getParameter() !=null && !"".equals(ss.getParameter())) {
        		ss.setParameter(ss.getParameter().replace("<", "&lt").replace(">", "&gt").replace("&gt&lt", "&gt</br>&lt")); //页面格式
			}
        }
        return res;
    }



    public Map<String, String> getStatus() {
        return status;
    }

    public ServerSubscribes getScribe(int id)
    {
        Criteria criteria = new Criteria();
        criteria.and("id").is(id);
        return (ServerSubscribes)mongodbService.find(ServerSubscribes.class,criteria);
    }

    public void deleteScribe(int id)
    {
        /*ServerSubscribes tmp = getScribe(id);
        evictObj(tmp);
        String hql = "update ServerSubscribes ss set ss.deleted = ? where ss.id = ?";*/
        //execUpdateHQL(hql,DELETED,id);
        String[] key = {"deleted"};
        Object[] val = {DELETED};
        mongodbService.updateFirst("id",id,key,val,"serverSubscribes");
       /* hql = "delete ServerSubscribesList scl where scl.serverSubscribesId = " + id ;
        execUpdateHQL(hql);*/
        //oprRecord.logOpr(null,tmp,"删除订阅"+id,request);
        mongodbService.remove("serverSubscribesId",id,"serverSubscribesList");
        taskService.subscribeTask(id,taskService.DELETE);
    }

    public List<ServerCatalogList> getScribeLists(int id)
    {
        ServerCatalog scribe = catalogService.getCatalog(id+"");
        if(scribe == null)
        {
            return null;
        }
        id = Integer.valueOf(scribe.getTemplateId());
        /*String hql = "from ServerCatalogList sc where sc.serverCatalogId = ? order by sc.serverElementName asc,sc.id";
        List<ServerCatalogList> tmplist = findByHQL(hql,id);*/

        Criteria criteria = new Criteria();
        criteria.and("serverCatalogId").is(id);
        List<ServerCatalogList> tmplist = (List<ServerCatalogList>)mongodbService.find(ServerCatalogList.class,criteria,Sort.Direction.ASC,"serverElementName");
        return tmplist;
    }

    public String getScribeListsCommaJson(String bid,String cid)
    {
        int id;
        try {
            id = getServerSubscribesid(Integer.parseInt(cid), Integer.parseInt(bid));
        }
        catch (Exception ex)
        {
            logger.warn("未找到订阅记录，bid:"+bid+"  cid:"+cid);
            return "";
        }
        return getScribeListsComma(id,null);
    }

    public String getScribeListsComma(int id,HttpServletRequest request)
    {
       /* List<ServerSubscribesList> list = getScribeListsById(id);
        Map<Integer,ServerSubscribesList> map = new LinkedHashMap<>();
        StringBuilder sb = new StringBuilder();
        for(ServerSubscribesList l : list)
        {
            map.put(l.getId(),l);
            if(sb.length() != 0)
            {
                sb.append(",");
            }
            sb.append(l.getServerCatalogListId());
        }
        if(request != null) {
            sessionManager.setSessionAttr(request, "list", map);
        }
        return sb.toString();*/
        return null;
    }

    public void saveScribeList(List<Integer> cataloglist,int scribeid,JSONObject condition)
    {
        if(cataloglist.size() > 0 && scribeid != 0)
        {
            List<ServerSubscribesList> list = getScribeListsById(0);
            Map<Integer,ServerSubscribesList> map = list.stream().collect(Collectors.toMap(ServerSubscribesList::getId, s->s));
            for(int catalogId : cataloglist)
            {
                ServerSubscribesList scl=null;
                if(map!=null)
                {
                    scl = map.get(catalogId);
                }
                if(scl == null) {
                    scl = new ServerSubscribesList();
                }
                int id =  mongodbService.count(ServerSubscribesList.class,new Criteria())+1;
                scl.setId(id);
                JSONObject subcon = condition.getJSONObject(catalogId+"");
                if(subcon != null && StringUtils.isNotEmpty(subcon.getString("type")))
                {
                    String type = subcon.getString("type");
                    scl.setConType(Integer.parseInt(type));
                    scl.setConValue("");
                    if("1".equals(type) || "3".equals(type) ||"4".equals(type))
                    {
                        scl.setConValue(subcon.getString("con1"));
                    }
                    else if("2".equals(type))
                    {
                        String c1 = StringUtils.isEmpty(subcon.getString("con1"))?"":subcon.getString("con1");
                        String c2 = StringUtils.isEmpty(subcon.getString("con2"))?"":subcon.getString("con2");
                        scl.setConValue(c1+","+c2);
                    }
                }
                scl.setUpddatetime(StardTime.format(new Date()));
                scl.setServerCatalogListId(catalogId);
                scl.setServerSubscribesId(scribeid);
                mongodbService.save(scl);
            }
            //batchSaveOrUpdateList(list);
            List<Integer> listIds = new ArrayList<>();
            for(ServerSubscribesList l : list)
            {
                listIds.add(l.getId());
            }
            if(listIds.size() > 0) {
                /*String hql = "delete ServerSubscribesList scl where scl.serverSubscribesId = " + scribeid + " and scl.id not in(" + sb.toString() + ")";
                execUpdateHQL(hql);*/
                Criteria criteria = new Criteria();
                criteria.and("serverSubscribesId").is(scribeid).and("id").in(listIds);
                mongodbService.remove(ServerSubscribesList.class,criteria);
            }
        }
        else
        {
            /*String hql = "delete ServerSubscribesList scl where scl.serverSubscribesId = " + scribeid ;
            execUpdateHQL(hql);*/
            Criteria criteria = new Criteria();
            criteria.and("serverSubscribesId").is(scribeid);
            mongodbService.remove(ServerSubscribesList.class,criteria);
        }
    }

    public void deleteByBusiness(int id)
    {
       /* String hql = "from ServerSubscribes sc where sc.businessSystemId = ? and sc.deleted != ?";
        List<ServerSubscribes> list = findByHQL(hql,id,DELETED);*/
        Criteria criteria = new Criteria();
        criteria.and("businessSystemId").is(id).and("deleted").ne(DELETED);
        List<ServerSubscribes> list = (List<ServerSubscribes>)mongodbService.find(ServerSubscribes.class,criteria,Sort.Direction.ASC,"serverElementName");
        if(list != null)
        {
            for(ServerSubscribes sc : list)
            {
                deleteScribe(sc.getId());
            }
        }
    }

    public boolean isRepeated(ServerSubscribes subscribes)
    {
        /*String hql = "from ServerSubscribes ss where ss.deleted != ? and ss.deleted != ? and ss.id != ? and ss.serverCatalogId = ? and ss.businessSystemId = ?";
        List list = findByHQL(hql,DELETED,FAILED,subscribes.getId(),subscribes.getServerCatalogId(),subscribes.getBusinessSystemId());*/
        Criteria criteria = new Criteria();
        criteria.and("id").ne(subscribes.getId()).andOperator(Criteria.where("deleted").ne(DELETED),Criteria.where("deleted").ne(FAILED)).and("serverCatalogId").is(subscribes.getServerCatalogId())
                .and("businessSystemId").is(subscribes.getBusinessSystemId());
        List<ServerSubscribes> list = (List<ServerSubscribes>)mongodbService.find(ServerSubscribes.class,criteria,Sort.Direction.ASC,"serverElementName");
        if(list == null || list.size() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean isRepeated(Integer id,Integer catalogid,Integer businessid)
    {
        ServerSubscribes subscribes = new ServerSubscribes();
        subscribes.setServerCatalogId(catalogid);
        subscribes.setBusinessSystemId(businessid);
        subscribes.setId(id);
        return isRepeated(subscribes);
    }

    public void denySubscribeApply(int id)
    {
       /* String hql = "update ServerSubscribes ss set ss.deleted = ?,ss.upddatetime = ? where ss.id = ?";
        execUpdateHQL(hql,FAILED,StardTime.format(new Date()),id);
        oprRecord.logOpr(null,null,"订阅审核失败"+id,request);*/
        String[] key = {"id"};
        Object[] val = {id};
        ServerSubscribes serverSubscribes = (ServerSubscribes)mongodbService.findOne(ServerSubscribes.class,key,val);
        serverSubscribes.setDeleted(FAILED);
        serverSubscribes.setUpddatetime(StardTime.format(new Date()));
        mongodbService.save(serverSubscribes);
    }

    public void acceptSubscribeApply(int id)
    {
        /*String hql = "update ServerSubscribes ss set ss.deleted = ?,ss.upddatetime = ?,ss.enables=?"+picuuid+" where ss.id = ?";
        execUpdateHQL(hql,UNDELETED,StardTime.format(new Date()),ENABLED,id);*/
       /* String[] key = {"deleted","upddatetime","enables"};
        Object[] val = {UNDELETED,StardTime.format(new Date()),ENABLED};*/
        String[] key = {"id"};
        Object[] val = {id};
        ServerSubscribes serverSubscribes = (ServerSubscribes)mongodbService.findOne(ServerSubscribes.class,key,val);
        serverSubscribes.setDeleted(UNDELETED);
        serverSubscribes.setUpddatetime(StardTime.format(new Date()));
        serverSubscribes.setEnables(ENABLED);
        mongodbService.save(serverSubscribes);
       // mongodbService.updateFirst("id",id,key,val,"serverSubscribes");
        taskService.subscribeTask(id,taskService.CREATE);
       /* oprRecord.logOpr(null,null,"订阅审核通过"+id,request);*/
    }


    public int getServerSubscribesid(int serverCatalogId, int businessSystemId){
    	/* String hql = "from ServerSubscribes ss where ss.deleted != ? and ss.deleted != ? and ss.serverCatalogId = ? and ss.businessSystemId = ?";
         List<ServerSubscribes> list = findByHQL(hql,DELETED,FAILED,serverCatalogId,businessSystemId);*/
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED).and("serverCatalogId").is(serverCatalogId)
                .and("businessSystemId").is(businessSystemId);
        List<ServerSubscribes> list = (List<ServerSubscribes>)mongodbService.find(ServerSubscribes.class,criteria);
         if(list == null || list.size() == 0){
             return 0;
         }else{
             return list.get(0).getId();
         }
    }

    public List<UploadFiles> getPics(PageInfo pageInfo,String search) {
        Criteria criteria = new Criteria();
       /* String hql =  "from UploadFiles sp where sp.fileType = '"+UploadFiles.Pics+"' ";*/
        criteria.and("fileType").is(UploadFiles.Pics);
        if(StringUtils.isNotEmpty(search))
        {
            /*hql += " and (sp.description like '%"+search+"%' or sp.docName like '%"+search+"%' )";*/
            criteria.orOperator(Criteria.where("description").regex(search).orOperator(Criteria.where("docName").regex(search)));
        }
       /* hql += " order by sp.uploadTime desc";*/
       // pageInfo.setTotalResult(getCount(hql));
        List<UploadFiles> list = (List<UploadFiles>)mongodbService.find(UploadFiles.class,criteria);
        return list;
    }

    public void savePic(UploadFiles files, MultipartFile file, HttpServletRequest request){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        files.setDocPath(fileTool.savePicFile(file, file.getOriginalFilename(),uuid));
        files.setFileType(UploadFiles.Pics);
        files.setUuid(uuid);
        files.setDocName(request.getParameter("filename"));
        files.setUploadTime(LocalDateTime.now());
        /*save(files);
        oprRecord.logOpr(files, null, "上传协议", request);*/
        mongodbService.save(files);
    }

    public void deletePic(String uuid, HttpServletRequest request) {
        if(checkPicUsage(uuid))
        {
            return;
        }
        /*execUpdateHQL("delete from UploadFiles sp where sp.uuid = ?",uuid);
        oprRecord.logOpr(null, null, "删除协议" + uuid, request);*/
        Criteria criteria = new Criteria();
        criteria.and("uuid").is(uuid);
        mongodbService.remove(UploadFiles.class,criteria);
    }

    public boolean checkPicUsage(String uuid)
    {
       /* List list = findByHQL("from ServerSubscribes ss where ss.picuuid = '"+uuid+"' and ss.deleted = '"+UNDELETED+"'");
        return list!=null&&list.size()>0;*/
        Criteria criteria = new Criteria();
        criteria.and("picuuid").ne(uuid).and("deleted").ne(UNDELETED);
        int count = mongodbService.count(ServerSubscribes.class,criteria);
        return count>0;
    }
}
