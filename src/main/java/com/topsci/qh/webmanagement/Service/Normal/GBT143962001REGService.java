package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.GBT143962001REG;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *     疾病代码管理
 * @author Administrator
 *
 */
@Service
public class GBT143962001REGService extends BasicService {

	
	@SuppressWarnings("unchecked")
	public List<GBT143962001REG> findPageList(PageInfo pageInfo,String keyWords){
		 /* String hql = "from GBT143962001REG reg where reg.reviewStatus = 0";
		  if(StringUtils.isNotBlank(keyWords)) {
			  hql += " and name like '"+keyWords+"%'";
		  }
	        pageInfo.setTotalResult(getCountBase(hql));
	        return findPageByHQLBase(hql, pageInfo);*/
		return null;
	}
	
	
	public void reviewGbtReg(String id,String status) {
	/*	String hql = "update GBT143962001REG set reviewStatus = ?,reviewTime = SYSDATE where id =?";
		execUpdateHQLBase(hql,Integer.valueOf(status),Long.valueOf(id));*/
	}
	
	public GBT143962001REG getById(String id) {
		/*return baseGet(GBT143962001REG.class,Long.valueOf(id));*/
		return null;
	}
	
	public void saveOrUpdate(GBT143962001REG gbtReg) {
		
		/*if(gbtReg.getId() == null) {
			String sql = "select max(id)  from GBT143962001REG";
			Long id = (Long) sessionFactoryBase.getCurrentSession().createQuery(sql).uniqueResult();
			if(id == null) {
				id = 0L;
			}
			id ++;
			gbtReg.setId(id);
			gbtReg.setReviewStatus(0);
			gbtReg.setSourceType(2);
		}
		gbtReg.setInsertDate(new Timestamp(new Date().getTime()));
		saveOrUpdateBase(gbtReg);*/
	}
	
	public void saveBT143962001(GBT143962001REG gbtReg) {
		/*Session session = sessionFactoryBase.getCurrentSession();
		String hql = "  from GBT143962001 where code = ?";
		GBT143962001 model = (GBT143962001) session.createQuery(hql).setString(0,gbtReg.getCode()).uniqueResult();
		if(model == null) {
			model = new GBT143962001();
			model.setCode(gbtReg.getCode());
			model.setName(gbtReg.getName());
			model.setSource(gbtReg.getSource());
			model.setSourceType(gbtReg.getSourceType());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
			session.save(model);
		}else {
			model.setCode(gbtReg.getCode());
			model.setName(gbtReg.getName());
			model.setSource(gbtReg.getSource());
			model.setSourceType(gbtReg.getSourceType());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
			session.update(model);
		}*/
	}
	
	
}
