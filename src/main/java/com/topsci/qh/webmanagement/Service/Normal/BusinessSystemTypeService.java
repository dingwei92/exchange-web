package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.ServerCatalog;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogList;
import com.topsci.qh.webmanagement.Pojo.ServerType;
import com.topsci.qh.webmanagement.Pojo.WebFuncs;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by lzw.
 * 16-7-22
 */
@Service
@SuppressWarnings("unchecked")
public class BusinessSystemTypeService extends BasicService {

    @Resource
    private MongodbService mongodbService;

    private Map<String,String> status;

    public BusinessSystemTypeService()
    {
        status = new HashMap<String,String>(){{
            put("Y","是");
            put("N","否");
        }};
    }

    public Map<String, String> getStatus() {
        return status;
    }

    public void saveType(ServerType type)
    {
        if(type.getId() == 0){
            int id =  mongodbService.count(ServerType.class,new Criteria())+1;
            type.setId(id);
        }
        type.setDeleted(UNDELETED);
        type.setOrd("N");
        type.setState("N");
        type.setUpdateTime(LocalDateTime.now());
       /* save(type);
        oprRecord.logOpr(type,null,"新建业务类别",request);*/
        mongodbService.save(type);
    }

    public void udpateType(ServerType type)
    {
       /* ServerType tmp = getType(type.getId());
        evictObj(tmp);
        update(type);
        oprRecord.logOpr(type,null,"修改业务类别",request);*/
        mongodbService.save(type);
    }

    public void deleteType(int it)
    {
        /*ServerType tmp = getType(it);
        evictObj(tmp);
        String hql = "update ServerType st set st.deleted = ? where st.id = ? ";
        execUpdateHQL(hql,DELETED,it);
        oprRecord.logOpr(null,tmp,"删除业务类别",request);*/
        String[] key = {"deleted"};
        Object[] val = {DELETED};
        mongodbService.updateFirst("id",it,key,val,"serverType");
    }

    public ServerType getType(int id)
    {
        String[] key = {"id"};
        Object[] val = {id};
        ServerType serverType = (ServerType)mongodbService.findOne(ServerType.class,key,val);
        return serverType;
    }

    public List<ServerType> getTypesByPage(String searchword)
    {
       /* String hql;*/
        Criteria criteria = new Criteria();
        if(StringUtils.isEmpty(searchword)) {
            //hql = "from ServerType st where st.deleted != ?";
            criteria.and("deleted").ne(DELETED);
        }
        else
        {
           // hql = "from ServerType st where st.deleted != ? and st.typeName like '%"+searchword+"%'";
            criteria.and("deleted").ne(DELETED).and("typeName").regex(searchword);
        }
        /*hql += " order by st.id desc";
        pageInfo.setTotalResult(getCount(hql,DELETED));*/
        List<ServerType> list = (List<ServerType>)mongodbService.find(ServerType.class,criteria, Sort.Direction.DESC,"id");
        return list;
    }

    public int checkrepeat(int id,String name)
    {
        Criteria criteria = new Criteria();
        criteria.and("deleted").ne(DELETED).and("typeName").is(name).and("id").ne(id);
        List<ServerType> list = (List<ServerType>)mongodbService.find(ServerType.class,criteria);

      /*  String hql = "from ServerType st where st.deleted != ? and st.typeName = ? and st.id != "+id ;
        List list = findByHQL(hql,DELETED,name);*/
        if(list != null && list.size() > 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public List<ServerType> getAllTypes()
    {
        /*String hql = "from ServerType st where st.deleted != ?";
        return findByHQL(hql,DELETED);*/
        Criteria criteria = new Criteria();
        criteria.and("deleted").is(UNDELETED);
        List<ServerType> list = (List<ServerType>)mongodbService.find(ServerType.class,criteria);
        return list;
    }

    public Map<Integer,String> getIdNameMap()
    {
        Map<Integer,String> maps = new HashMap<>();
        List<ServerType> lists = getAllTypes();
        for(ServerType type : lists)
        {
            maps.put(type.getId(),type.getTypeName());
        }
        return maps;
    }

    public List<Integer> getBasicTypesId()
    {
        List<ServerType> types = getAllTypes();
        List<Integer> result = new ArrayList<>();
        for(ServerType type : types)
        {
            if("Y".equals(type.getIsBase()))
            {
                result.add(type.getId());
            }
        }
        return result;
    }
}
