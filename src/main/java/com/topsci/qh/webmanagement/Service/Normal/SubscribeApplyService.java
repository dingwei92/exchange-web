package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.RelUserBusiness;
import com.topsci.qh.webmanagement.Pojo.ServerCatalog;
import com.topsci.qh.webmanagement.Pojo.ServerSubscribeApply;
import com.topsci.qh.webmanagement.Pojo.ServerSubscribes;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.MongodbService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lzw.
 * 16-7-25
 */
@Service
public class SubscribeApplyService extends BasicService{

    @Resource
    private SubscribeService subscribeService;
    @Resource
    private RelUserBusinessService relUserBusinessService;
    @Autowired
    private MongodbService mongodbService;

    public final String FAILED = "F";
    public final String APPLY = "A";

    public void saveApply(ServerSubscribeApply apply)
    {
        mongodbService.save(apply);
    }
    
    public void updateApply(ServerSubscribeApply apply)
    {
        /*update(apply);*/
    }
    
    public void deleteApply(int sid)
    {
        //hql  = "delete from ServerSubscribeApply ssa where ssa.sid = ?";
        mongodbService.remove("sid",sid,"serverSubscribeApply");
    }

    public Map<String,Object> getSubscribesApply(List<String> search,int type)
    {
        //String hql ;
        Map<Integer,ServerSubscribeApply> applies = new HashMap<>();
        Map<String,Object> result = new HashMap<>();
        String searchword=null;
        if(search!=null && search.size()>0)
        {
            searchword = search.get(0);
        }
        Criteria criteria = new Criteria();
        if(StringUtils.isNotEmpty(searchword)) {
           // hql = "from ServerSubscribeApply ssa where ssa.name like '%"+searchword+"%'";
            criteria.and("name").regex(searchword);
        }
        /*else
        {
            hql = "from ServerSubscribeApply ssa";
        }*/
        List<ServerSubscribeApply> tmpapply = (List<ServerSubscribeApply>)mongodbService.find(ServerSubscribeApply.class,criteria);
        List<Integer> ids = new ArrayList<>();
        for(ServerSubscribeApply apply : tmpapply)
        {
            ids.add(apply.getSid());
            applies.put(apply.getSid(),apply);
        }
        if(ids.size() == 0)
        {
            result.put("scribes",new ArrayList<>());
            result.put("applies",applies);
            return result;
        }
        //hql = "from ServerSubscribes ss where ss.deleted = ? and ss.id in ("+ids+") order by ss.upddatetime desc,ss.businessSystemId,ss.id desc";
        Criteria criteriaSs = new Criteria();
        if(type == 1){
            criteriaSs.and("deleted").is(APPLY);
        }
        criteriaSs.and("id").in(ids);
        List<ServerSubscribes> scribes = (List<ServerSubscribes>)mongodbService.find(ServerSubscribes.class,criteria, Sort.Direction.DESC,"upddatetime,businessSystemId,id");
        for(ServerSubscribes ss:scribes){
        	if (ss.getParameter() !=null && !"".equals(ss.getParameter())) {
        		ss.setParameter(ss.getParameter().replace("<", "&lt").replace(">", "&gt").replace("&gt&lt", "&gt</br>&lt")); //页面格式
			}
        }
        if(scribes == null || scribes.size() == 0)
        {
            result.put("scribes",new ArrayList<>());
            result.put("applies",applies);
            return result;
        }
        result.put("scribes",scribes);
        result.put("applies",applies);
        return result;
    }

    public Map<String,Object> getSubscribesApply(PageInfo page, HttpServletRequest request,String uuid)
    {
        /*Map<String,Object> result = new HashMap<>();
        Map<Integer,ServerSubscribeApply> applies = new HashMap<>();
        int bid = relUserBusinessService.getBusinessID(uuid);

        String hql = "from ServerSubscribes ss where ss.deleted != ? and ss.businessSystemId = "+bid+" order by ss.upddatetime desc,ss.businessSystemId,ss.id desc";
        List<ServerSubscribes> scribes = findByHQL(hql,DELETED);
        page.setTotalResult(scribes.size());


        int begin = page.getCurrentPage()*page.getPageSize();
        int end = (page.getCurrentPage()+1)*page.getPageSize();
        if(end > scribes.size() )
        {
            end = scribes.size()==0?0:scribes.size();
        }
        List<ServerSubscribes> subListScribes = scribes.subList(begin,end);

        String sids = "";
        for(ServerSubscribes ss:subListScribes){
        	if (ss.getParameter() !=null && !"".equals(ss.getParameter())) {
        		ss.setParameter(ss.getParameter().replace("<", "&lt").replace(">", "&gt").replace("&gt&lt", "&gt</br>&lt")); //页面格式
			}
            if(StringUtils.isNotEmpty(sids))
            {
                sids += ",";
            }
            sids+="'"+ss.getId()+"'";
        }
        sids = "("+sids+")";
        if(!"()".equals(sids)) {
            hql = "from ServerSubscribeApply ssa where ssa.sid in " + sids;
            List<ServerSubscribeApply> tmpapply = findByHQL(hql);
            for (ServerSubscribeApply apply : tmpapply) {
                applies.put(apply.getSid(), apply);
            }
        }

        result.put("scribes",subListScribes);
        result.put("applies",applies);
        page.setTotalResult(applies.size());
        return result;*/
        return null;
    }
    
    public Map<Integer,ServerSubscribeApply> getAllSubscribesApply(){
    	Map<Integer,ServerSubscribeApply> res = new HashMap<Integer,ServerSubscribeApply>();
    /*	String hql = "from ServerSubscribeApply";*/
        List<ServerSubscribeApply> tmpapply = (List<ServerSubscribeApply>)mongodbService.findAll(ServerSubscribeApply.class);
        for(ServerSubscribeApply apply : tmpapply)
        {
        	res.put(apply.getSid(),apply);
        }
        return res;
    }

    public Map<String,Object> getSubscribesApplyHistory(String search)
    {
        Map<String,Object> result = new HashMap<>();
        Map<Integer,ServerSubscribeApply> applies = new HashMap<>();
        String hql;
        Criteria criteria = new Criteria();
        if(StringUtils.isNotEmpty(search)) {
            //hql = "from ServerSubscribeApply ssa where ssa.name like '%"+search+"%' ";
            criteria.and("name").regex(search);
        }
        //List<ServerSubscribeApply> tmpapply = findByHQL(hql);
        List<ServerSubscribeApply> tmpapply = (List<ServerSubscribeApply>)mongodbService.find(ServerSubscribeApply.class,criteria);
        if(tmpapply == null || tmpapply.size() == 0)
        {
            result.put("scribes",new ArrayList<>());
            result.put("applies",applies);
            return result;
        }

        List<Integer> ids = new ArrayList<>();
        for(ServerSubscribeApply apply : tmpapply)
        {
            ids.add(apply.getSid());
        }
        if(ids.size() == 0)
        {
            result.put("scribes",new ArrayList<>());
            result.put("applies",applies);
            return result;
        }
        //hql = "from ServerSubscribes ss where ss.id in "+ids+" order by ss.businessSystemId,ss.id desc";
        Criteria criteriass = new Criteria();
        criteriass.and("id").in(ids);
        List<ServerSubscribes> scribes = (List<ServerSubscribes>)mongodbService.find(ServerSubscribes.class,criteriass, Sort.Direction.DESC,"businessSystemId,id");

        for(ServerSubscribeApply apply : tmpapply)
        {
            applies.put(apply.getSid(),apply);
        }
        for(ServerSubscribes ss:scribes){
        	if (ss.getParameter() !=null && !"".equals(ss.getParameter())) {
        		ss.setParameter(ss.getParameter().replace("<", "&lt").replace(">", "&gt").replace("&gt&lt", "&gt</br>&lt")); //页面格式
			}
        }
        result.put("scribes",scribes);
        result.put("applies",applies);
        return result;
    }

    public void saveSubscribeApply(ServerSubscribes subscribe,ServerSubscribeApply apply,HttpServletRequest request)
    {
        if("Y".equals(subscribe.getIsFull()))
        {
            subscribe.setEndTime(StardTime.format(new Date()));
        }
       /* save(subscribe);
        save(apply);
        oprRecord.logOpr(apply,null,"申请订阅"+subscribe.getId(),request);*/
//        taskService.subscribeTask(subscribes.getId(),taskService.CREATE);
    }

    public void saveSubscribe(ServerSubscribes subscribes,ServerSubscribeApply apply)
    {
        int idSs =  mongodbService.count(ServerSubscribes.class,new Criteria())+1;
        int idSsa =  mongodbService.count(ServerSubscribeApply.class,new Criteria())+1;
        subscribes.setId(idSs);
        subscribeService.saveSubscribe(subscribes, false);
        apply.setCreateTime(StardTime.format(new Date()));
        apply.setSid(subscribes.getId());
        apply.setId(idSsa);
        saveApply(apply);
       /* oprRecord.logOpr(apply,null,"申请订阅"+subscribes.getId(),request);*/
    }

    public ServerSubscribeApply getApplyBySubscribe(int id)
    {
       /* String hql = "from ServerSubscribeApply ssa where ssa.sid = ?";
        List<ServerSubscribeApply> applies = findByHQL(hql,id);
        if(applies != null && applies.size()>0)
        {
            return applies.get(0);
        }
        else
        {
            return new ServerSubscribeApply();
        }*/
        return null;
    }


}
