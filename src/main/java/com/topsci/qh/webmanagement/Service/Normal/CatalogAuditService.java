package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.BusinessSystem;
import com.topsci.qh.webmanagement.Pojo.ServerCatalog;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogList;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.MongodbService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CatalogAuditService  
 * @author tgeng
 * @date 2016-11-21 下午3:46:01  
 *
 */
@Service
public class CatalogAuditService  extends BasicService{

	protected final String AUDITING = "A";
	protected final String AUDITFAIL ="F";
	 
	@Resource
	private RelUserBusinessService relUserBusinessService;
	@Resource
    private CatalogService catalogService;
	@Resource
	private BusinessSystemService businessSystemService;
	@Resource
    private ChangeTaskService taskService;
	@Resource
	private MongodbService mongodbService;

	public List<ServerCatalog> getCatalogsAuditing(String searchword, PageInfo pageInfo) {
		/*String hql;
        if (StringUtils.isNotEmpty(searchword)) {
            hql = "from ServerCatalog sc where sc.deleted = ? and " +
                    "(sc.serverName like '%" + searchword + "%' or sc.serverShort like '%" + searchword + "%')  order by sc.upddatetime desc,sc.businessSystemId,sc.id desc";
        } else {
            hql = "from ServerCatalog sc where sc.deleted = ? order by sc.upddatetime desc,sc.businessSystemId,sc.id desc";
        }
        pageInfo.setTotalResult(getCount(hql, AUDITING));
        return findPageByHQL(hql, pageInfo, AUDITING);*/
		return null;
	}

	public List<ServerCatalog> getCatalogsAuditingByUser(String searchword,PageInfo pageInfo, String userId) {
		/*RelUserBusiness relUserBusiness = relUserBusinessService.getRelUserBusinessByUserId(userId);
        String hql;
        if (StringUtils.isNotEmpty(searchword)) {
            hql = "from ServerCatalog sc where sc.deleted = ? and " +
                    "(sc.serverName like '%" + searchword + "%' or sc.serverShort like '%" + searchword + "%') and businessSystemId = ?  order by sc.upddatetime desc,sc.businessSystemId,sc.id desc";
        } else {
            hql = "from ServerCatalog sc where sc.deleted = ?  and businessSystemId = ? order by sc.upddatetime desc,sc.businessSystemId,sc.id desc";
        }
        pageInfo.setTotalResult(getCount(hql, AUDITING,relUserBusiness.getBusinessId()));
        return findPageByHQL(hql, pageInfo, AUDITING,relUserBusiness.getBusinessId());*/
		return null;
	}

	public List<ServerCatalogList> getCatalogLists(int id, HttpServletRequest request) {
		List<ServerCatalogList> list = getCatalogLists(id);
		List<String> res = new ArrayList<>();
        for(ServerCatalogList l : list){
            res.add((l.getKeycol()==1?"* ":"")+l.getServerElementName()+"("+l.getServerElementDescribe()+")"+" : "+l.getElementDescribe()+"");
        }
        return list;
	}

	private List<ServerCatalogList> getCatalogLists(int id) {
	/*	String hql = "from ServerCatalogList sct where sct.serverCatalogId = ? order by sct.id";
		 return findByHQL(hql,id);*/
		return null;
	}

	public List<String> getBsLists(String specifySystemIdList,HttpServletRequest request) {
		List<String> res = new ArrayList<>();
		if(specifySystemIdList!=null){
			String[] bslist = specifySystemIdList.split(",");
			for(String bs:bslist){
				BusinessSystem buss = businessSystemService.getBusinessSystem(Integer.parseInt(bs));
				res.add(buss.getSystemName()+"("+buss.getSystemShort()+")");
			}
		}
		return res;
	}

	public void acceptCatalog(int id, HttpServletRequest request) {
		/*String hql = "update ServerCatalog sc set sc.deleted = ?,sc.upddatetime = ? where sc.id = ?";
        execUpdateHQL(hql,UNDELETED,StardTime.format(new Date()),id);
        taskService.catalogTask(id,taskService.CREATE);
        oprRecord.logOpr(null,null,"发布审核通过"+id,request);*/
		
	}

	public void denyCatalog(int id, HttpServletRequest request) {
		/*String hql = "update ServerCatalog sc set sc.deleted = ?,sc.upddatetime = ? where sc.id = ?";
        execUpdateHQL(hql,AUDITFAIL,StardTime.format(new Date()),id);
        oprRecord.logOpr(null,null,"发布审核失败"+id,request);*/
	}

	public List<ServerCatalog> getCatalogsAll(String searchword) {
		Criteria criteria = new Criteria();
		if (StringUtils.isNotEmpty(searchword)) {
           /* hql = "from ServerCatalog sc where " +
                    "(sc.serverName like '%" + searchword + "%' or sc.serverShort like '%" + searchword + "%')  order by sc.upddatetime desc,sc.businessSystemId,sc.id desc";*/
				criteria.orOperator(Criteria.where("serverName").regex(searchword),Criteria.where("serverShort").regex(searchword));
        }
		List<ServerCatalog> list = (List<ServerCatalog>)mongodbService.find(ServerCatalog.class,criteria, Sort.Direction.DESC,"upddatetime,id");
        return list;
	}

	public List<ServerCatalog> getCatalogsAllByUser(String searchword,PageInfo pageInfo, String userId) {
		/*RelUserBusiness relUserBusiness = relUserBusinessService.getRelUserBusinessByUserId(userId);
        String hql;
        if (StringUtils.isNotEmpty(searchword)) {
            hql = "from ServerCatalog sc where " +
                    "(sc.serverName like '%" + searchword + "%' or sc.serverShort like '%" + searchword + "%') and businessSystemId = ?  order by sc.upddatetime desc,sc.businessSystemId,sc.id desc";
        } else {
            hql = "from ServerCatalog sc where businessSystemId = ? order by sc.upddatetime desc,sc.businessSystemId,sc.id desc";
        }
        pageInfo.setTotalResult(getCount(hql,relUserBusiness.getBusinessId()));
        return findPageByHQL(hql, pageInfo,relUserBusiness.getBusinessId());*/
		return null;
	}

}
