package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.DZXZQYBZ;
import com.topsci.qh.webmanagement.Pojo.DZXZQYBZREG;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *     区划管理
 * @author Administrator
 *
 */
@Service
public class DZXZQYBZService extends BasicService {

	
	@SuppressWarnings("unchecked")
	public List<DZXZQYBZREG> findPageList(PageInfo pageInfo,String keyWords){
		/*  String hql = "from DZXZQYBZREG reg where reviewStatus = 0 ";
		  if(StringUtils.isNotBlank(keyWords)) {
			  hql += " and mc like '"+keyWords+"%' ";
		  }
	        pageInfo.setTotalResult(getCountBase(hql));
	        return findPageByHQLBase(hql, pageInfo);*/
		return null;
	}
	
	
	public void reviewDzxzqybzReg(String id,String status) {
		/*String hql = "update DZXZQYBZREG set reviewStatus = ?,reviewTime = SYSDATE where id =?";
		execUpdateHQLBase(hql,Integer.valueOf(status),Long.valueOf(id));*/
	}
	
	public List<DZXZQYBZ> findListByCondition(String condition){
	/*	String hql = " select new DZXZQYBZ(id,bh,mc,dj) from DZXZQYBZ where 1 = 1 ";
		if(StringUtils.isNotBlank(condition)) {
			hql += condition;
		}
		 List<DZXZQYBZ> list = sessionFactoryBase.getCurrentSession().createQuery(hql).list();*/
		return null;
	}
	
	public DZXZQYBZREG getById(String id) {
		/*Session session = sessionFactoryBase.getCurrentSession();
		return (DZXZQYBZREG) session.get(DZXZQYBZREG.class,Long.valueOf(id));*/
		return null;
	}
	
	public void saveDZXZQYBZ(DZXZQYBZREG dzReg) {
		/*Session session = sessionFactoryBase.getCurrentSession();
		String hql = "  from DZXZQYBZ where bh = ?";
		DZXZQYBZ model = (DZXZQYBZ) session.createQuery(hql).setString(0,dzReg.getBh()).uniqueResult();
		if(model == null) {
			model = new DZXZQYBZ();
			model.setBh(dzReg.getBh());
			model.setMc(dzReg.getMc());
			model.setDj(dzReg.getDj());
			DZXZQYBZ parent = new DZXZQYBZ();
			parent.setBh(dzReg.getSj().getBh());
			model.setSj(parent);
			model.setSource(dzReg.getSource());
			model.setSourceType(dzReg.getSourceType());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
			session.save(model);
		}else {
			model.setBh(dzReg.getBh());
			model.setMc(dzReg.getMc());
			model.setDj(dzReg.getDj());
			DZXZQYBZ parent = new DZXZQYBZ();
			parent.setBh(dzReg.getSj().getBh());
			model.setSj(parent);
			model.setSource(dzReg.getSource());
			model.setSourceType(dzReg.getSourceType());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
			session.update(model);
		}*/
	}
	
	public void saveOrUpdate(DZXZQYBZREG dzReg) {
		/*if (dzReg.getId() == null) {
			String sql = "select max(id)  from DZXZQYBZREG";
			Long id = (Long) sessionFactoryBase.getCurrentSession().createQuery(sql).uniqueResult();
			if (id == null) {
				id = 0L;
			}
			id++;
			dzReg.setId(id);
			dzReg.setReviewStatus(0);
			dzReg.setSourceType(2);
		}
		dzReg.setInsertDate(new Timestamp(new Date().getTime()));
		saveOrUpdateBase(dzReg);*/
	}
	
	public List<DZXZQYBZ> queryByBhs(List<String> bhs){
		/*Session session = sessionFactoryBase.getCurrentSession();
		if(bhs.size() < 1000) {
			String bhStr = StringUtils.join(bhs,",");
			String hql = " from DZXZQYBZ where bh in ("+bhStr+")";
			return session.createQuery(hql).list();
		}else {
			List<List<String>> listBhs = new ArrayList<List<String>>();
			List<String> temp = new ArrayList<>();
			for (int i = 0; i < bhs.size(); i++) {
				temp.add(bhs.get(i));
				if(i % 999 == 0 || i == bhs.size()-1) {
					listBhs.add(temp);
					temp = new ArrayList<>();
				}
			}
			List<DZXZQYBZ> dzs = new ArrayList<>();
			for (List<String> list : listBhs) {
				String bhStr = StringUtils.join(list,",");
				String hql = " from DZXZQYBZ where bh in ("+bhStr+")";
				dzs.addAll(session.createQuery(hql).list());
			}
			return dzs;
		}*/
		return null;
	}
	
	public void saveOrUpdate(DZXZQYBZ dzxzqy) {
		/*Session session = sessionFactoryBase.getCurrentSession();
		if(dzxzqy.getId() != null) {
			session.update(dzxzqy);
		}else {
			session.save(dzxzqy);
		}*/
	}
	
}
