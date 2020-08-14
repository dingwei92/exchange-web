package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.*;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.MongodbService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzw.
 * 16-6-25
 */
@Service
public class BusinessSystemService extends BasicService{


    @Resource
    private CatalogService catalogService;
    @Resource
    private BusinessSystemTypeService typeService;
    @Resource
    private SubscribeService subscribeService;
    @Resource
    private RelUserBusinessService relUserBusinessService;
    @Resource
    private MongodbService mongodbService;

    public void saveBusinessSystem(BusinessSystem business)
    {
        int idSs =  mongodbService.count(KafkaTopic.class,new Criteria())+1;
        KafkaTopic topic = new KafkaTopic();
        topic.setTopicName(business.getSystemShort()+"_TOPIC");
        topic.setId(idSs);
        mongodbService.save(topic);
        /* save(topic);*/
        business.setKafkaTopicId(topic.getId());
       /* save(business);*/
        int idb =  mongodbService.count(BusinessSystem.class,new Criteria())+1;
        business.setId(idb);
        mongodbService.save(business);
       /* oprRecord.logOpr(business,null,"新建业务系统"+business.getId(),request);*/
    }

    public void updateBusinessSystem(BusinessSystem business)
    {
      /*  BusinessSystem businessSystem = get(BusinessSystem.class,business.getId());
        evictObj(businessSystem);
        update(business);
        oprRecord.logOpr(business,businessSystem,"修改业务系统",request);*/
        mongodbService.save(business);
    }

    public Map getBusinessSystemIdNameMapBayId(int id)
    {
        Map<String,String> result = new LinkedHashMap<>();
        BusinessSystem bs = getBusinessSystem(id);
            if(bs != null) {
                result.put(bs.getId() + "", bs.getSystemName() + " (" + bs.getSystemShort() + ")");
            }
        return result;
    }
    
    public BusinessSystem getBusinessSystem(int uuid)
    {
        String[] key = {"id"};
        Object[] val = {uuid};
        BusinessSystem bs = (BusinessSystem)mongodbService.findOne(BusinessSystem.class,key,val);
        return bs;
    }

    public boolean isNameRepeat(int id,String name)
    {
        String hql;
        int result;
        if(id != 0) {
         /*   hql = "from BusinessSystem bs where bs.systemName = ? and bs.id != ? and bs.deleted !=?";
            result = getCount(hql,name, id,DELETED);*/
            Criteria criteria = new Criteria();
            criteria.and("systemName").is(name).and("id").ne(id).and("deleted").ne(DELETED);
            result = mongodbService.count(BusinessSystem.class,criteria);
        }
        else
        {
            /*hql = "from BusinessSystem bs where bs.systemName = ? and bs.deleted !=?";
            result = getCount(hql,name,DELETED);*/
            Criteria criteria = new Criteria();
            criteria.and("systemName").is(name).and("deleted").ne(DELETED);
            result = mongodbService.count(BusinessSystem.class,criteria);
        }
        if(result > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isShortRepeat(int id,String sht)
    {
        String hql;
        int result;
        if(id != 0) {
           /* hql = "from BusinessSystem bs where bs.systemShort = ? and bs.id != ? and bs.deleted !=?";
            result = getCount(hql,sht,id,DELETED);*/
            Criteria criteria = new Criteria();
            criteria.and("systemShort").is(sht).and("id").ne(id).and("deleted").ne(DELETED);
            result = mongodbService.count(BusinessSystem.class,criteria);
        }
        else
        {
            /*hql = "from BusinessSystem bs where bs.systemShort = ? and bs.deleted !=?";
            result = getCount(hql,sht,DELETED);*/
            Criteria criteria = new Criteria();
            criteria.and("systemShort").is(sht).and("deleted").ne(DELETED);
            result = mongodbService.count(BusinessSystem.class,criteria);
        }
        if(result > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public BusinessSystem getBusinessSystemByShort(String Short)
    {
        /*String hql = "from BusinessSystem bs where bs.systemShort = ? and bs.deleted != ? ";
        List<BusinessSystem> list = findByHQL(hql,Short,DELETED);*/
        Criteria criteria = new Criteria();
        criteria.and("systemShort").is(Short).and("deleted").ne(DELETED);
        List<BusinessSystem> list = (List<BusinessSystem>)mongodbService.find(BusinessSystem.class,criteria);
        if(list!=null && list.size()>0)
        {
            return list.get(0);
        }
        return null;
    }

    public List<BusinessSystem> getAllBusinessSystems()
    {
       /* String hql = "from BusinessSystem bs where  bs.deleted !=? order by bs.systemShort asc, bs.id desc)";
        return findByHQL(hql, DELETED);*/
        Criteria criteria = new Criteria();
        criteria.and("deleted").ne(DELETED);
        List<BusinessSystem> list = (List<BusinessSystem>)mongodbService.find(BusinessSystem.class,criteria);
       return list;
    }

    public List<BusinessSystem> getBusinessSystems(String search)
    {
        String hql;
        Criteria criteria = new Criteria();
        if(StringUtils.isNotEmpty(search)) {
//            hql = "from BusinessSystem bs where  (bs.systemName like '%"+search+"%' or bs.systemShort like '%"+search+"%') and bs.deleted !=? order by bs.id desc)";
            criteria.andOperator(Criteria.where("deleted").ne(DELETED),Criteria.where("systemName").regex(search).orOperator(Criteria.where("systemShort").regex(search)));
        }
        else {
           /* hql = "from BusinessSystem bs where bs.deleted !=? order by bs.id desc";*/
            criteria.and("deleted").ne(DELETED);
        }
        List<BusinessSystem> list = (List<BusinessSystem>)mongodbService.find(BusinessSystem.class,criteria, Sort.Direction.DESC,"id");
       /* page.setTotalResult(getCount(hql,DELETED));*/
        return list;//findPageByHQL(hql, page,DELETED);
    }

    public List<BusinessSystem> getBusinessSystemStatus()
    {
        /*String hql = "from BusinessSystem bs where bs.deleted !=? order by bs.lastConnect,bs.id desc";
        page.setTotalResult(getCount(hql,DELETED));
        return findPageByHQL(hql, page,DELETED);*/
        Criteria criteria = new Criteria();
        criteria.and("deleted").ne(DELETED);
        List<BusinessSystem> list = (List<BusinessSystem>)mongodbService.find(BusinessSystem.class,criteria, Sort.Direction.DESC,"lastConnect,id");
        return list;
    }

    public void deleteBusinessSystem(int bid)
    {
        BusinessSystem businessSystem = getBusinessSystem(bid);
        businessSystem.setDeleted(DELETED);
        int topicid= businessSystem.getKafkaTopicId();
        /*update(businessSystem);*/
        updateBusinessSystem(businessSystem);
       /* KafkaTopic topic = get(KafkaTopic.class,topicid);*/
        String[] key = {"id"};
        Object[] val = {topicid};
        KafkaTopic topic = (KafkaTopic)mongodbService.findOne(KafkaTopic.class,key,val);
        //删除业务系统的同时删除kafka topic
        if(topic!=null) {
           /* delete(topic);*/
            mongodbService.remove(topic);
        }
        //删除业务系统的同时删除订阅和发布
        catalogService.deleteByBusiness(bid);
        subscribeService.deleteByBusiness(bid);
        //删除业务系统的同时删除关联用户
        relUserBusinessService.deleteByBusiness(bid);
       /* oprRecord.logOpr(null,null,"删除业务系统"+id,request);*/
    }

    public Map getBusinessSystemIdNameMap(boolean string)
    {
        Map map;
        List<BusinessSystem> list = getAllBusinessSystems();
        if(!string) {
            map = new LinkedHashMap();
            for (BusinessSystem bs : list) {
                map.put(bs.getId(), bs.getSystemName());
            }
        }
        else
        {
            map = new LinkedHashMap<>();
            for (BusinessSystem bs : list) {
                map.put(bs.getId()+"", bs.getSystemName()+" ("+bs.getSystemShort()+")");
            }
        }
        return map;
    }

    /**
     * 系统发布的字段
     * @param yes
     * @return
     */
    public Map getBusinessSystemIdNameMapBasic(boolean yes)
    {
        List<Integer> bids = typeService.getBasicTypesId();
        Map<String,String> result = new LinkedHashMap<>();
        List<BusinessSystem> list = getAllBusinessSystems();
        for(BusinessSystem bs : list){
            if(yes && bids.contains(bs.getTypeId())) {
            	result.put(bs.getId() + "", bs.getSystemName() + " (" + bs.getSystemShort() + ")");
            } else if (!yes && !bids.contains(bs.getTypeId())) {
                result.put(bs.getId() + "", bs.getSystemName() + " (" + bs.getSystemShort() + ")");
            }
        }
        return result;
    }

    /**
     *
     * @param uuid
     * @param name
     * @param sht
     * @return  名称重复返回1,缩写重复返回2,正确返回0
     */
    public int checkRepeat(int uuid,String name,String sht)
    {

        if(isNameRepeat(uuid,name))
        {
            return 1;
        }
        if (isShortRepeat(uuid,sht))
        {
            return 2;
        }
        return 0;
    }

    public Map<String,String> getShortNameMap()
    {
        Map<String,String> map = new HashMap<>();
        List<BusinessSystem> list = getAllBusinessSystems();
        for(BusinessSystem bs : list)
        {
            map.put(bs.getSystemShort(),bs.getSystemName());
        }
        return map;
    }
}
