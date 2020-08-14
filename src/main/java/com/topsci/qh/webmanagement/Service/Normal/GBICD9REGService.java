package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.GBICD9REG;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *     手术代码管理
 * @author Administrator
 *
 */
@Service
public class GBICD9REGService extends BasicService {

	
	@SuppressWarnings("unchecked")
	public List<GBICD9REG> findPageList(PageInfo pageInfo,String keyWords){
		  /*String hql = "from GBICD9REG reg where reviewStatus = 0 ";
		  if(StringUtils.isNotBlank(keyWords)) {
			  hql += " and name like '"+keyWords+"%'";
		  }
	        pageInfo.setTotalResult(getCountBase(hql));
	        return findPageByHQLBase(hql, pageInfo);*/
		return null;
	}
	
	
	public void reviewGbicD9Reg(String id,String status) {
		/*String hql = "update GBICD9REG set reviewStatus = ?,reviewTime = SYSDATE where id =?";
		execUpdateHQLBase(hql,Integer.valueOf(status),Long.valueOf(id));*/
	}


	public GBICD9REG getById(String regId) {
		/*Session session = sessionFactoryBase.getCurrentSession();*/
		/*return (GBICD9REG) session.get(GBICD9REG.class,Long.valueOf(regId));*/
		return null;
	}


	public void saveGBICD9(GBICD9REG gbReg) {
		/*Session session = sessionFactoryBase.getCurrentSession();
		String hql = "  from GBICD9 where code = ?";
		GBICD9 model = (GBICD9) session.createQuery(hql).setString(0,gbReg.getCode()).uniqueResult();
		if(model == null) {
			model = new GBICD9();
			model.setCode(gbReg.getCode());
			model.setName(gbReg.getName());
			model.setDescription(gbReg.getDescription());
			model.setSourceType(gbReg.getSourceType());
			model.setSource(gbReg.getSource());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
			session.save(model);
		}else {
			model.setCode(gbReg.getCode());
			model.setName(gbReg.getName());
			model.setDescription(gbReg.getDescription());
			model.setSourceType(gbReg.getSourceType());
			model.setSource(gbReg.getSource());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
			session.update(model);
		}*/
	}
	
	public void saveOrUpdate(GBICD9REG gbicdReg) {
		/*if (gbicdReg.getId() == null) {
			String sql = "select max(id)  from GBICD9REG";
			Long id = (Long) sessionFactoryBase.getCurrentSession().createQuery(sql).uniqueResult();
			if (id == null) {
				id = 0L;
			}
			id++;
			gbicdReg.setId(id);
			gbicdReg.setReviewStatus(0);
			gbicdReg.setSourceType(2);
		}
		gbicdReg.setInsertDate(new Timestamp(new Date().getTime()));
		saveOrUpdateBase(gbicdReg);*/
	}
	
}
