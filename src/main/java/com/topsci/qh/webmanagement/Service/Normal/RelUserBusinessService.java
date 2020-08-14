package com.topsci.qh.webmanagement.Service.Normal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.topsci.qh.webmanagement.Resources.MongodbService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.topsci.qh.webmanagement.Pojo.BusinessSystem;
import com.topsci.qh.webmanagement.Pojo.RelUserBusiness;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Tools.Config;

/**
 * Created by wwhao.
 * 16-6-25
 */
@Service
public class RelUserBusinessService{
	@Resource
    private UserService userService;
    @Resource
    private MongodbService mongodbService;

    public List<RelUserBusiness> getRelUserBusinessByBusinessId(int businessId)
    {
        /*String hql = "from RelUserBusiness bs where bs.businessId = ?";
        List<RelUserBusiness> list = findByHQL(hql,businessId);*/
        Criteria criteria = new Criteria();
        if(businessId != 0) {
            criteria.and("businessId").is(businessId);
        }
        List<RelUserBusiness> list = (List<RelUserBusiness>)mongodbService.find(RelUserBusiness.class,criteria);
        if(list!=null && list.size()>0)
        {
            return list;
        }
        return null;
    }

    public RelUserBusiness getRelUserBusinessByUserId(String userId)
    {
        /*String hql = "from RelUserBusiness bs where bs.userId = ?";
        List<RelUserBusiness> list = findByHQL(hql,userId);*/
        Criteria criteria = new Criteria();
        criteria.and("userId").is(userId);
        List<RelUserBusiness> list = (List<RelUserBusiness>)mongodbService.find(RelUserBusiness.class,criteria);
        if(list!=null && list.size()>0)
        {
            return list.get(0);
        }
        return null;
    }

    
    public  Map getUserIdBusinessIDMap() {
    	/*String hql = "from RelUserBusiness r order by r.id";
        List<RelUserBusiness> relUserBusiness = findByHQL(hql);*/
        Criteria criteria = new Criteria();
        List<RelUserBusiness> relUserBusiness = (List<RelUserBusiness>)mongodbService.find(RelUserBusiness.class,criteria, Sort.Direction.DESC,"id");

        Map result = new LinkedHashMap<>();
        for(RelUserBusiness r : relUserBusiness){
        	result.put(r.getUserId(),r.getBusinessId());
        }
        return result;
    }
    
    public int getBusinessID(String uuid) {
    	/* String hql = "from RelUserBusiness r where r.userId = ?";
    	 List<RelUserBusiness> list = findByHQL(hql,uuid);*/
        Criteria criteria = new Criteria();
        criteria.and("userId").is(uuid);
        List<RelUserBusiness> list = (List<RelUserBusiness>)mongodbService.find(RelUserBusiness.class,criteria);
         if(list != null && list.size()>0)
         {
             return list.get(0).getBusinessId();
         }
         return 0;
    }
    
    public void saveUserBusiness(String uid,String bid)
    {
    	RelUserBusiness rel = new RelUserBusiness();
    	rel.setUserId(uid);
    	rel.setBusinessId(Integer.valueOf(bid));
       /* save(rel);*/
        mongodbService.save(rel);
    }

    public void updateUserBusiness(String uid,String bid)
    {
       /* String hql = "delete from RelUserBusiness r where r.userId = ?";
        execUpdateHQL(hql,uid);*/
        mongodbService.remove("userId",uid,"relUserBusiness");
        saveUserBusiness(uid,bid);
    }
    
    public void deleteByBusiness(int bid){
    	/*String hql = "from RelUserBusiness r where r.businessId = ?";
    	List<RelUserBusiness> list = findByHQL(hql,bid);*/
        Criteria criteria = new Criteria();
        criteria.and("businessId").is(bid);
        List<RelUserBusiness> list = (List<RelUserBusiness>)mongodbService.find(RelUserBusiness.class,criteria);
    	for(RelUserBusiness r : list){
    		userService.deleteUser(r.getUserId());
            mongodbService.remove(r);
    	}
    	
    }
    
    public void deleteByUserId(String userID){
    	/*String hql = "from RelUserBusiness r where r.userId = ?";
    	List<RelUserBusiness> list = findByHQL(hql,userID);*/
        Criteria criteria = new Criteria();
        criteria.and("userId").is(userID);
        mongodbService.remove("userId",userID,"relUserBusiness");
    	/*if(list != null && list.size()>0){
    		for(RelUserBusiness r : list){
        		delete(r);
        	}
    	}*/
    }
}
