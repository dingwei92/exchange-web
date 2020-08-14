package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.ServerHistoryApply;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by wwhao.
 * 16-6-25
 */
@Service
public class ServerHistoryApplyService extends BasicService{

	public void saveServerHistoryApply(ServerHistoryApply serverHistoryApply){
       /* save(serverHistoryApply);*/
    }
	
    public ServerHistoryApply getServerHistoryApply(int id)
    {
       /* return get(ServerHistoryApply.class,id);*/
        return null;
    }

    public List<ServerHistoryApply> getServerHistoryApplyByServerSubscribesId(int ssid)
    {
       /* String hql = "from ServerHistoryApply bs where bs.serverSubscribesId = ?";
        List<ServerHistoryApply> list = findByHQL(hql,ssid);
        if(list!=null && list.size()>0)
        {
            return list;
        }*/
        return null;
    }

    public boolean hasServerHistoryApply(String userId, int ssid, String startDate, String endDate)
    {
    	/*String hql = "";
    	List<ServerHistoryApply> list = null;
    	if (startDate==null && endDate==null) {
    		hql = "from ServerHistoryApply bs where bs.webUserId = ? and bs.serverSubscribesId = ? and startDate is null and endDate is null";
    		list = findByHQL(hql,userId,ssid+"");
		}else {
			hql = "from ServerHistoryApply bs where bs.webUserId = ? and bs.serverSubscribesId = ? and startDate=? and endDate=?";
			list = findByHQL(hql,userId,ssid+"",startDate,endDate);
		}
        if(list!=null && list.size()>0)
        {
            return true;
        }*/
        return false;
    }
    
    public ServerHistoryApply getServerHistoryApplyByUserId(String userId)
    {
       /* String hql = "from ServerHistoryApply bs where bs.webUserId = ?";
        List<ServerHistoryApply> list = findByHQL(hql,userId);
        if(list!=null && list.size()>0)
        {
            return list.get(0);
        }*/
        return null;
    }
    
    public List<ServerHistoryApply> getAllServerHistoryApplys()
    {
       /* String hql = "from ServerHistoryApply bs)";
        return findByHQL(hql);*/
        return null;
    }
    
    public Map<String,Object> getServerHistoryApplys(PageInfo page, HttpServletRequest request,String search, String uuid)
    {
      /*  Map<String,Object> result = new HashMap<>();
        Map<String,ServerSubscribes> scribes = new HashMap<>();
        List<ServerHistoryApply> applies = new ArrayList<ServerHistoryApply>();
        
        
        String hql;
        List<ServerHistoryApply> tmpList = null;
        if (Constants.INIT_ID.equals(uuid)) {//超级管理员
        	if(StringUtils.isNotEmpty(search)) {
        		hql = "select distinct sha from ServerHistoryApply sha, ServerSubscribes ssa, ServerCatalog sc where sc.id=ssa.serverCatalogId and ssa.id = sha.serverSubscribesId and sc.serverName like '%"+search+"%' order by sha.createTime desc";
            	tmpList = findByHQL(hql);
            	
            	hql = "select distinct sha from ServerHistoryApply sha, ServerSubscribes ssa, BusinessSystem bs where bs.id=ssa.businessSystemId and ssa.id = sha.serverSubscribesId and bs.systemName like '%"+search+"%' order by sha.createTime desc";
            }else {
            	hql = "from ServerHistoryApply sha order by sha.createTime desc";
			}
		}else {
			if(StringUtils.isNotEmpty(search)) {
            	hql = "select distinct sha from ServerHistoryApply sha, ServerSubscribes ssa, ServerCatalog sc where sc.id=ssa.serverCatalogId and ssa.id = sha.serverSubscribesId and sc.serverName like '%"+search+"%' and sha.webUserId!='0' order by sha.createTime desc";
            	tmpList = findByHQL(hql);
            	
            	hql = "select distinct sha from ServerHistoryApply sha, ServerSubscribes ssa, BusinessSystem bs where bs.id=ssa.businessSystemId and ssa.id = sha.serverSubscribesId and bs.systemName like '%"+search+"%' and sha.webUserId!='0' order by sha.createTime desc";
            }else {
            	hql = "from ServerHistoryApply sha where sha.webUserId!='0' order by sha.createTime desc";
			}
		}
        
        List<ServerHistoryApply> tmpapply = findByHQL(hql);
        if (tmpList != null) {
        	tmpapply.addAll(tmpList);
		}
        if(tmpapply == null || tmpapply.size() == 0)
        {
            result.put("scribes",scribes);
            result.put("applies",applies);
            page.setTotalResult(0);
            return result;
        }

        String ids = "";
        for(ServerHistoryApply apply : tmpapply)
        {
            if(StringUtils.isNotEmpty(ids))
            {
                ids += ",";
            }
            ids+=apply.getServerSubscribesId();
        }
        ids = "("+ids+")";
        if("()".equals(ids))
        {
            result.put("scribes",new ArrayList<>());
            result.put("applies",applies);
            return result;
        }
        hql = "from ServerSubscribes ss where ss.id in "+ids+" order by ss.businessSystemId,ss.id desc";
        List<ServerSubscribes> tempScribes = findByHQL(hql);
        
        page.setTotalResult(tmpapply.size());
        for(ServerHistoryApply apply : tmpapply)
        {
        	if (apply.getParameter() !=null && !"".equals(apply.getParameter())) {
        		apply.setParameter(apply.getParameter().replace("<", "&lt").replace(">", "&gt").replace("&gt&lt", "&gt</br>&lt")); //页面格式
			}
            applies.add(apply);
        }
        
        for (ServerSubscribes scribe : tempScribes) {
        	scribes.put(scribe.getId()+"", scribe);
		}
        
        int begin = page.getCurrentPage()*page.getPageSize();
        int end = (page.getCurrentPage()+1)*page.getPageSize()+1;
        if(end > applies.size() )
        {
            end = applies.size()==0?0:applies.size();
        }
        result.put("scribes",scribes);
        result.put("applies",applies.subList(begin,end));
        return result;*/
        return null;
    }
}
