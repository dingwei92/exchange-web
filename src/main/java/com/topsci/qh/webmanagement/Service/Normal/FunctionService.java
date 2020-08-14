package com.topsci.qh.webmanagement.Service.Normal;

import com.alibaba.fastjson.JSON;
import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.MongodbService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lzw.
 * 16-6-21
 */
@SuppressWarnings("unchecked")
@Service
public class FunctionService extends BasicService{

    @Resource
    private RoleService roleService;
    @Resource
    private MongodbService mongodbService;

    private Map<String,String> status;
    private final String TOPMENU = "0";
    public final String ISPARENT="Y";
    public final String ISNOTPARENT="N";

    public FunctionService()
    {
        status = new HashMap<String,String>(){{
            put("Y","是");
            put("N","否");
        }};
    }

    public Map<String, String> getStatus() {
        return status;
    }

    public void saveFunc(WebFuncs func)
    {
        if(!func.getParentUuid().equals(TOPMENU))
        {
            WebFuncs father = getFunc(func.getParentUuid());
            father.setIsParent(ISPARENT);
            /*save(father);*/
            mongodbService.save(father);
        }
       /* save(func);*/
        mongodbService.save(func);
       /* oprRecord.logOpr(func,null,"新建功能",request);*/
    }

    public void updateFunc(WebFuncs func)
    {
       /* WebFuncs tmp = get(WebFuncs.class,func.getUuid());
        evictObj(tmp);
        update(func);
        oprRecord.logOpr(func,tmp,"修改功能",request);*/
        mongodbService.save(func);
    }

    public void deleteFunc(String uuid)
    {
        List<WebFuncs> list = getChildFunc(uuid);
        if(list!= null && list.size()>0)
        {
            for(WebFuncs func : list)
            {
                deleteFunc(func.getUuid());
            }
        }
       /* String hql = "update WebFuncs func set func.deleted = ? where uuid = ?";
        execUpdateHQL(hql,DELETED,uuid);
        oprRecord.logOpr(null,tmp,"删除功能",request);*/
        String[] key = {"deleted"};
        Object[] val = {DELETED};
        mongodbService.updateFirst("uuid",uuid,key,val,"webFuncs");
    }

    public WebFuncs getFunc(String uuid)
    {
      /*  return get(WebFuncs.class,uuid);*/
        String[] key = {"uuid"};
        Object[] val = {uuid};
        WebFuncs webFuncs = (WebFuncs)mongodbService.findOne(WebFuncs.class,key,val);
        return webFuncs;
    }

    public List<WebFuncs> getChildFunc(String parentUUID)
    {
       /* String hql = "from WebFuncs func where func.parentUuid = ? and func.deleted!= ? order by func.sort asc";
        return findByHQL(hql, parentUUID, DELETED);*/
        Criteria criteria = new Criteria();
        criteria.and("parentUuid").is(parentUUID).and("deleted").is(UNDELETED);
        List<WebFuncs> list = (List<WebFuncs>)mongodbService.find(WebFuncs.class,criteria, Sort.Direction.ASC,"sort");
        return list;
    }

    public List<WebFuncs> getChildFuncJson(String parentUUID)
    {
        List funcs = getChildFunc(parentUUID);
        return funcs;
    }

    public List<WebFuncs> getFuncs()
    {
       /* String hql = "from WebFuncs cs where cs.deleted != ? order by cs.parentUuid,cs.sort ";
        pageInfo.setTotalResult(getCount(hql,DELETED));
        return findPageByHQL(hql,pageInfo,DELETED);*/
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED);
        List<WebFuncs> list = (List<WebFuncs>)mongodbService.find(WebFuncs.class,criteria, Sort.Direction.ASC,"parentUuid,sort");
        return list;
    }

    public List<WebFuncs> getAllFuncs()
    {
       /* String hql = "from WebFuncs cs where cs.deleted != ?";
        List<WebFuncs> list = findByHQL(hql,DELETED);
        for(WebFuncs wf : list){
            mongodbService.save(wf);
        }*/

        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED);
        List<WebFuncs> list = (List<WebFuncs>)mongodbService.find(WebFuncs.class,criteria);
        return list;
    }

    public List<WebFuncs> getAllFuncsNotHide()
    {
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED).and("hide").is("N");
        List<WebFuncs> list = (List<WebFuncs>)mongodbService.find(WebFuncs.class,criteria);
        return list;
    }

    public Map<String,String> getUuidNameMap()
    {
        List<WebFuncs> funcs = getAllFuncs();
        Map maps = new HashMap();
        for(WebFuncs func : funcs)
        {
            maps.put(func.getUuid(),func.getName());
        }
        maps.put("0","无");
        return maps;
    }

    public Map<String,String> getOtherFuncsMap(String uuid)
    {
        Map<String,String> maps = getUuidNameMap();
        if(StringUtils.isNotEmpty(uuid))
        {
            maps.remove(uuid);
        }
        return maps;
    }
    public List<WebFuncsList> getAllFuncList(String uuid){
        List<WebFuncsList> models = getFuncModel(uuid);
        return iterateFuncs(models,"0");
    }

    public String getAllFuncListStr(String uuid){
        List<WebFuncsList> models = getFuncModel(uuid);
        return iterateFuncsStr(models,"0").append(Constants.INDEXPATH).toString();
    }

    private List<WebFuncsList> getFuncModel(String uuid)
    {
        String rid =null;
        List<String> authfuncs=null;
        boolean normal_login=  true;
        if(StringUtils.isNotEmpty(uuid)) {
            rid = roleService.getUserRole(uuid);
            authfuncs = roleService.getAuthFuncs(rid);
        }
        else
        {
            normal_login = false;
        }
        List<WebFuncsList> models = new ArrayList<>();
        if(authfuncs!=null || !normal_login) {
            List<WebFuncs> funcs = getAllFuncs();
            for (WebFuncs func : funcs) {
                if (normal_login  && !authfuncs.contains(func.getUuid())) {
                    continue;
                }
                if(normal_login && func.getHide().equals("Y"))
                {
                    continue;
                }
                WebFuncsList model = new WebFuncsList();
                model.name = func.getName();
                model.parentid = func.getParentUuid();
                model.url = func.getPath();
                model.children = new ArrayList<>();
                model.uuid = func.getUuid();
                model.isCaption = func.getIsCaption();
                model.sort = func.getSort();
                model.hide = func.getHide();
                model.newPage = func.getNewPage();
                models.add(model);
            }
        }
        return models;
    }

    private List<WebFuncsList> iterateFuncs(List<WebFuncsList> models, String parentid)
    {
        List<WebFuncsList> m = new ArrayList<>();
        for(WebFuncsList model : models)
        {
            String uuid = model.uuid;
            String pid = model.parentid;
            if(StringUtils.isNotEmpty(parentid))
            {
                if(parentid.equals(pid)) {
                    List<WebFuncsList> tmp = iterateFuncs(models, uuid);
                    model.children = tmp;
                    m.add(model);
                }
            }
        }
        Collections.sort(m, new Comparator<WebFuncsList>() {
            @Override
            public int compare(WebFuncsList o1, WebFuncsList o2) {
                return o1.sort - o2.sort;
            }
        });
        return m;
    }

    private StringBuilder iterateFuncsStr(List<WebFuncsList> models, String parentid)
    {
        StringBuilder sb = new StringBuilder();
        for(WebFuncsList model : models)
        {
            String uuid = model.uuid;
            String pid = model.parentid;
            if(StringUtils.isNotEmpty(parentid))
            {
                if(parentid.equals(pid)) {
                    StringBuilder tmp = iterateFuncsStr(models, uuid);
                    sb.append(tmp);
                }
            }
            if(model.getUrl() != null) {
                sb.append(model.getUrl()).append(",");
            }
        }
        return sb;
    }

    public String getUuidNameMapJson(List<String> uuid,boolean selectAll)
    {
        List<WebFuncsList> list = getAllFuncList(null);
        return iterateJson(list,uuid,selectAll);
    }

    private String iterateJson(List<WebFuncsList> models, List<String> uuid, boolean selectAll)
    {
        if(models == null || models.size() ==0)
        {
            return "";
        }
        String json="";
        for(WebFuncsList mode : models)
        {
            if(mode.getHide().equals("Y"))
            {
                continue;
            }
            String tmp = "{text:\""+mode.getName()+"\",tag:\""+mode.getUuid()+"\"";
            if("Y".equals(mode.getIsCaption()) && !selectAll)
            {
                tmp += ",selectable:false";
            }
            else
            {
                tmp += ",selectable:true";
                if(uuid.contains(mode.getUuid()))
                {
                    tmp += ",state:{selected:true}";
                }
                else
                {
                    tmp += ",state:{selected:false}";
                }
            }

            if(mode.getChildren() != null && mode.getChildren().size()>0)
            {
                tmp += ",nodes:["+iterateJson(mode.getChildren(),uuid,selectAll)+"]";
            }
            tmp +="}";
            if(StringUtils.isEmpty(json))
            {
                json += tmp;
            }
            else
            {
                json += ","+tmp;
            }
        }
        return json;
    }

    public int repeatCheck(String uuid,String puuid,String name)
    {
        String hql;
        List<WebFuncs> list;
        if(StringUtils.isEmpty(uuid)) {
            /*hql = "from WebFuncs wf where wf.parentUuid = ? and wf.deleted != ? and wf.name = ?";
            list = findByHQL(hql,puuid,DELETED,name);*/
            Criteria criteria = new Criteria();
            criteria.and("deleted").is(UNDELETED).and("parentUuid").is(puuid).and("name").is(name);
            list = (List<WebFuncs>)mongodbService.find(WebFuncs.class,criteria);
        }
        else
        {
           /* hql = "from WebFuncs wf where wf.parentUuid = ? and wf.deleted != ? and wf.name = ? and wf.uuid != ?";
            list = findByHQL(hql,puuid,DELETED,name,uuid);*/
            Criteria criteria = new Criteria();
            criteria.and("deleted").is(UNDELETED).and("parentUuid").is(puuid).and("name").is(name).and("uuid").ne(uuid);
            list = (List<WebFuncs>)mongodbService.find(WebFuncs.class,criteria);
        }
        if(list!=null && list.size()>0)
        {
           return 1;
        }
        else
        {
            return 0;
        }
    }

}
