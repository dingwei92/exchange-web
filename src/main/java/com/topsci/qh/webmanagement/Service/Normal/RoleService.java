package com.topsci.qh.webmanagement.Service.Normal;

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
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by lzw.
 * 16-7-19
 */
@SuppressWarnings("unchecked")
@Service
public class RoleService extends BasicService {

    @Resource
    private FunctionService functionService;

    private Map<String,String> status;
    private WebRoles webmanager;

    @Resource
    private MongodbService mongodbService;

    public RoleService()
    {
        status = new HashMap<String,String>(){{
            put("Y","是");
            put("N","否");
        }};
        webmanager = new WebRoles();
        webmanager.setNormalStatus("Y");
    }

    public Map<String, String> getStatus() {
        return status;
    }

    public void saveRole(WebRoles role) {
        mongodbService.save(role);
        /* save(role);*/
        /*oprRecord.logOpr(role, null, "新建角色", request);*/
    }

    public void updateRole(WebRoles role) {
       /* WebRoles tmp = getRole(role.getUuid());
        evictObj(tmp);
        update(role);
        oprRecord.logOpr(role, tmp, "修改角色", request);*/
        mongodbService.save(role);
    }

    public void deleteRole(String uuid) {
        /*WebRoles tmp = getRole(uuid);
        evictObj(tmp);
        String hql = "update WebRoles wr set wr.deleted = ? where wr.uuid = ?";
        execUpdateHQL(hql, DELETED, uuid);
        oprRecord.logOpr(null, tmp, "删除角色", request);*/
        WebRoles tmp = getRole(uuid);
        mongodbService.remove(tmp);
        mongodbService.remove("roleUuid",uuid,"webRoleFunc");
    }

    public List<WebRoles> getRoles() {
       /* String hql = "from WebRoles wr where wr.deleted != ? order by wr.createTime desc,uuid ";
        pageInfo.setTotalResult(getCount(hql,DELETED));*/
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED);
        List<WebRoles> list = (List<WebRoles>)mongodbService.find(WebRoles.class,criteria, Sort.Direction.DESC,"createTime");
        return list;
    }

    public WebRoles getRole(String uuid) {
        if(uuid.equals(Constants.INIT_ID))
        {
            return webmanager;
        }
        String[] key = {"uuid"};
        Object[] val = {uuid};
        return  (WebRoles)mongodbService.findOne(WebRoles.class,key,val);
    }

    public List<String> getAuthFuncs(String uuid) {
       /* String hql = "from WebRoleFunc rf where rf.roleUuid = ?";
        List<WebRoleFunc> rels = findByHQL(hql, uuid);*/

        String[] key = {"roleUuid"};
        Object[] val = {uuid};
        List<WebRoleFunc> rels = (List<WebRoleFunc>)mongodbService.find(WebRoleFunc.class,key,val);

        List<String> result = new ArrayList<>();
        for (WebRoleFunc rel : rels) {
            result.add(rel.getFuncUuid());
        }
        return result;
    }

    public void saveFuncRelations(List<String> listFunIds, String roleuuid) {
       /* String tmp = "";
        for(String str : tmplist)
        {
            if(StringUtils.isNotEmpty(tmp))
            {
                tmp+=",";
            }
            tmp += "'"+str+"'";
        }
        if(StringUtils.isEmpty(tmp))
        {
            tmp += "'-1'";
        }
        String hql = "from WebFuncs wf where wf.uuid in (" + tmp + ")";
        List<WebFuncs> childfuncs = findByHQL(hql);*/
        Criteria criteria = new Criteria();
        criteria.and("uuid").in(listFunIds);
        List<WebFuncs> childfuncs = (List<WebFuncs>)mongodbService.find(WebFuncs.class,criteria);

        List<WebFuncs> allfuncs = new ArrayList<>();
        for(WebFuncs f : childfuncs)
        {
            getallfuncs(f,allfuncs);
        }

        for (WebFuncs f : allfuncs) {
            WebRoleFunc func = new WebRoleFunc();
            func.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
            func.setUpdatedTime(LocalDateTime.now());
            func.setRoleUuid(roleuuid);
            func.setFuncUuid(f.getUuid());
            func.setDeleted(UNDELETED);
            mongodbService.save(func);
           /* funclist.add(func);*/
        }
      /*  batchSaveOrUpdateList(funclist);*/
    }

    public void updateFuncRelations(List<String> listFunIds, String roleuuid) {
        /*String hql = "delete from WebRoleFunc rf where rf.roleUuid = ?";
        execUpdateHQL(hql, roleuuid);;*/
        mongodbService.remove("roleUuid",roleuuid,"webRoleFunc");
        saveFuncRelations(listFunIds, roleuuid);
    }

    private void getallfuncs(WebFuncs child, List<WebFuncs> result) {
        result.add(child);
        if (!"0".equals(child.getParentUuid())) {
            getallfuncs(functionService.getFunc(child.getParentUuid()), result);
        }
    }

    public List<WebRoles> getAllRoles()
    {
       /* String hql = "from WebRoles wr where wr.deleted != ?";*/

        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED);
        List<WebRoles> childfuncs = (List<WebRoles>)mongodbService.find(WebRoles.class,criteria);

        return childfuncs;
    }

    public Map<String,String> getAllRolesUuidNameMap()
    {
        List<WebRoles> roles = getAllRoles();
        Map<String,String> result = new HashMap<>();
        for(WebRoles role : roles)
        {
            result.put(role.getUuid(),role.getRoleName());
        }
        return result;
    }

    public String getUserRole(String uuid)
    {
        if(Constants.INIT_ID.equals(uuid))
        {
            return Constants.INIT_ID;
        }
      /*  String hql = "from WebUserRole ur where ur.userUuid = ?";
        List<WebUserRole> list = findByHQL(hql,uuid);*/
        Criteria criteria = new Criteria();
        criteria.and("userUuid").is(uuid);
        List<WebUserRole> list = (List<WebUserRole>)mongodbService.find(WebUserRole.class,criteria);
        if(list != null && list.size()>0)
        {
            return list.get(0).getRoleUuid();
        }
        return "";
    }

    public void saveUserRole(String uid,String rid)
    {
        WebUserRole role = new WebUserRole();
        //role.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        role.setRoleUuid(rid);
        role.setUserUuid(uid);
        role.setDeleted(UNDELETED);
        role.setUpdatedTime(LocalDateTime.now());
       /* save(role);*/
        mongodbService.save(role);
    }

    public void updateUserRole(String uid,String rid)
    {
        String hql = "delete from WebUserRole ur where ur.userUuid = ?";
        mongodbService.remove("userUuid",uid,"webUserRole");
        //execUpdateHQL(hql,uid);
        saveUserRole(uid,rid);
    }

    public int checkRepeat(String uuid,String name)
    {

        List<WebRoles> list;
        if(StringUtils.isEmpty(uuid))
        {
           /* hql = "from WebRoles wr where wr.deleted != ? and wr.roleName = ?";*/
            Criteria criteria = new Criteria();
            criteria.and("deleted").is(UNDELETED).and("roleName").is(name);
            list = (List<WebRoles>)mongodbService.find(WebRoles.class,criteria);
            /*list= findByHQL(hql,DELETED,name);*/
        }
        else
        {
            /*hql = "from WebRoles wr where wr.deleted != ? and wr.roleName = ? and wr.uuid != ?";
            list= findByHQL(hql,DELETED,name,uuid);*/
            Criteria criteria = new Criteria();
            criteria.and("deleted").is(UNDELETED).and("roleName").is(name).and("uuid").ne(uuid);
            list = (List<WebRoles>)mongodbService.find(WebRoles.class,criteria);
        }
        if(list != null && list.size()>0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public boolean isSuperUser(String uuid)
    {
        if(Constants.INIT_ID.equals(uuid))
        {
            return true;
        }
        String rid = getUserRole(uuid);
        WebRoles role = getRole(rid);
        if(role != null)
        {
            if("Y".equals(role.getSuperUser()))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    public boolean isSuperUserByRid(String uuid)
    {
        WebRoles role = getRole(uuid);
        if(role != null)
        {
            if("Y".equals(role.getSuperUser()))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public Map<String,String> getUserRoleMap(List<WebUsers> users)
    {
        Map<String,String> maps = new HashMap<>();
        for(WebUsers user : users)
        {
            String rid = getUserRole(user.getUuid());
            maps.put(user.getUuid(),rid == null?"":rid);
        }
        return maps;
    }

}
