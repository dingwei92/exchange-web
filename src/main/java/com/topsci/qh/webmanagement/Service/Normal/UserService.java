package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Kafka.Producer;
import com.topsci.qh.webmanagement.Pojo.WebUsers;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Resources.MongodbService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Tools.Config;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by lzw.
 * 16-6-21
 */
@SuppressWarnings("unchecked")
@Service
public class UserService extends BasicService {

    private Map<String,String> states;
    private final String USER_BANNED = "1";
    private final String USER_NOT_BANNED = "0";
    private final String USER_NOT_BANNED_SQL = "Y";
    private final String USER_BANNED_SQL = "N";

    @Resource
    private Config config;
    @Resource
    private Producer producer;
    @Resource
    private MongodbService mongodbService;

    public UserService()
    {
        states = new HashMap<String,String>(){{
            put(USER_NOT_BANNED,"启用");
            put(USER_BANNED,"禁用");
        }};
    }

    public void saveUser(WebUsers user)
    {
        user.setDeleted(UNDELETED);
        mongodbService.save(user);
    }

    public void updateUser(WebUsers user)
    {
        mongodbService.save(user);
    }

    private void updateUserNoLog(WebUsers user)
    {
        mongodbService.save(user);
    }

    public void activateUser(WebUsers user)
    {
        if(user != null) {
            if (user.getStatu().equals(USER_BANNED)) {
                user.setStatu(USER_NOT_BANNED);
                if(StringUtils.isNotEmpty(user.getSsoId()))
                {
                   /* String sql = "update WEB_USERS set AUTH_CHANGED = ? where UUID = ?";
                    execUpdateSQL(sql,USER_NOT_BANNED_SQL,user.getUuid());*/
                    String[] key = {"auth_changed"};
                    Object[] val = {USER_NOT_BANNED_SQL};
                    mongodbService.updateFirst("uuid",user.getUuid(),key,val,"webUsers");
                }
            } else if (user.getStatu().equals(USER_NOT_BANNED)) {
                user.setStatu(USER_BANNED);
                if(StringUtils.isNotEmpty(user.getSsoId()))
                {
                   /* String sql = "update WEB_USERS set AUTH_CHANGED = ? where UUID = ?";
                    execUpdateSQL(sql,USER_BANNED_SQL,user.getUuid());*/
                    String[] key = {"auth_changed"};
                    Object[] val = {USER_BANNED_SQL};
                    mongodbService.updateFirst("uuid",user.getUuid(),key,val,"webUsers");
                }
            }
           /* WebUsers old = get(WebUsers.class,user.getUuid());
            evictObj(old);*/
            // oprRecord.logOpr(user,old,"禁用/激活用户",request);
            updateUserNoLog(user);
        }
    }

    public void activateUser(String uuid)
    {
        activateUser(getUser(uuid));
    }

    public void deleteUser(String uuid)
    {
        if(StringUtils.isNotEmpty(uuid)) {
            WebUsers user = getUser(uuid);
            /*evictObj(user);
            String hql = "update WebUsers user set user.deleted = ? where user.uuid = ?";
            execUpdateHQL(hql, DELETED,uuid);*/
            mongodbService.remove(user);
            //oprRecord.logOpr(null,user,"删除用户"+uuid,request);
        }
    }

    public WebUsers getUser(String uuid)
    {
        String[] key = {"uuid"};
        Object[] val = {uuid};
        return  (WebUsers)mongodbService.findOne(WebUsers.class,key,val);
    }

    /**
     * 仅在登陆时调用
     * @param username
     * @return
     */
    public WebUsers getUserByLoginName(String username,boolean ssoUser)
    {
       /*  String hql;
       if(ssoUser)
        {
           *//* hql="from WebUsers user where user.ssoId is not null and user.loginName = ? and user.deleted != ?" +
                    " and user.statu != "+USER_BANNED;*//*
            val[2]=
        }
        else
        {
            hql = "from WebUsers user where user.ssoId is null and user.loginName = ? and user.deleted != ?" +
                    " and user.statu != "+USER_BANNED;
        }*/
        String[] key = {"loginName","deleted","statu"};
        Object[] val = {username,UNDELETED,USER_BANNED};
        List<WebUsers> list = (List<WebUsers>)mongodbService.find(WebUsers.class,key,val);

       /* List<WebUsers> list = findByHQL(hql,username,DELETED);*/
        if(list != null && list.size() > 0)
        {
            return list.get(0);
        }
        else
        {
            return null;
        }
    }

    public List<WebUsers> getUsersSearchWord(String search)
    {
        Criteria criteria = new Criteria();
        if(StringUtils.isNotEmpty(search)) {
          /*  hql = "from WebUsers user where user.deleted != ? and (user.loginName like '%"+search+"%' or user.userName like '%"+search+"%') order by user.uuid)";
            return findPageByHQL(hql, pageInfo, DELETED);*/
            criteria.and("deleted").is(UNDELETED)
                    .orOperator(Criteria.where("loginName").regex(search),Criteria.where("userName").regex(search));
        }
        else {
           /* hql = "from WebUsers user where user.deleted != ? order by user.uuid";*/
            criteria.andOperator(Criteria.where("deleted").is(UNDELETED));
        }

        List<WebUsers> list = (List<WebUsers>)mongodbService.find(WebUsers.class,criteria, Sort.Direction.DESC,"uuid");
        return list;
    }

    public List<WebUsers> getUsers(String username)
    {
        if(StringUtils.isNotEmpty(username))
        {
//            String hql = "from WebUsers user where user.uuid = ?";
            String[] key = {"uuid"};
            Object[] val = {username};
            List<WebUsers> list = (List<WebUsers>)mongodbService.find(WebUsers.class,key,val);
            return list;
        }
        else
        {
            return new ArrayList<>();
        }
    }

    public int isRepeatLoginName(String uuid,String name)
    {   int result = 0;
        if(StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(uuid))
        {
           /* String hql = "";*/
            if(StringUtils.isEmpty(uuid))
            {
               /* hql = "from WebUsers user where user.deleted != ?  and user.loginName = ? and user.ssoId is null";*/
                String[] key = {"deleted","loginName"};
                Object[] val = {UNDELETED,name};

                result = mongodbService.count(WebUsers.class,key,val);
            }
            else {
                /*if(StringUtils.isEmpty(ssoid))
                {
                    hql = "from WebUsers user where user.deleted != ? and user.uuid != ? and user.loginName = ? and user.ssoId is null";
                }
                else
                {
                    hql = "from WebUsers user where user.deleted != ? and user.uuid != ? and user.loginName = ? and user.ssoId is not null";
                }*/
                Criteria criteria = new Criteria();
                criteria.andOperator(Criteria.where("deleted").is(UNDELETED),Criteria.where("uuid").ne(uuid),Criteria.where("loginName").is(name));
                result = mongodbService.count(WebUsers.class,criteria);
            }
        }
        return result;
    }

    public void loginLog(String uuid)
    {
        /*String hql = "update WebUsers user set user.recentLogin = ? where user.uuid = ?";
        execUpdateHQL(hql, new Timestamp(new Date().getTime()), uuid);*/
        String[] key = {"recentLogin"};
        Object[] val = {LocalDateTime.now()};
        mongodbService.updateFirst("uuid",uuid,key,val,"webUsers");
    }

    public void changePass(String uuid,String pass)
    {
        /*String hql = "update WebUsers user set user.passwd = ? where user.uuid = ?";
        execUpdateHQL(hql,md5Tool.getDigestPassword(pass),uuid);
        oprRecord.logOpr(null,null,"修改用户"+uuid+"密码",request);*/

        String[] key = {"passwd"};
        Object[] val = {md5Tool.getDigestPassword(pass)};
        mongodbService.updateFirst("uuid",uuid,key,val,"webUsers");
    }

    public Map<String, String> getStates() {
        return states;
    }

    public WebUsers createSuperUser()
    {
        WebUsers user = new WebUsers();
        user.setUuid("0");
        user.setUserName(config.getInit_username());
        user.setLoginName(Constants.INIT_USERNAME);
        user.setPasswd(md5Tool.getDigestPassword(Constants.INIT_PASSWORD));
        return user;
    }
    
    public List<WebUsers> getAllUsers()
    {
       /* String hql = "from WebUsers user ";*/
        List<WebUsers> list = (List<WebUsers>)mongodbService.findAll(WebUsers.class);
        return list;
    }
    
    public Map<String, String> getUsersMap(boolean flg)
    {
    	Map map;
        List<WebUsers> list = getAllUsers();
        if(!flg) {
            map = new LinkedHashMap();
            for (WebUsers use : list) {
                map.put(use.getUuid(), use.getUserName());
            }
        }
        else
        {
            map = new LinkedHashMap<>();
            for (WebUsers use : list) {
                map.put(use.getUuid(), use.getUserName()+" ("+use.getLoginName()+")");
            }
        }
        return map;
    }



}
