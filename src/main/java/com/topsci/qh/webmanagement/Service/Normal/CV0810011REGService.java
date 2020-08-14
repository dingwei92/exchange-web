package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.CV0810011REG;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 科室管理
 * 
 * @author Administrator
 *
 */
@Service
public class CV0810011REGService extends BasicService {

	@SuppressWarnings("unchecked")
	public List<CV0810011REG> findPageList(PageInfo pageInfo, String keyWords) {
		/*String hql = "from CV0810011REG reg where reg.reviewStatus = 0 ";
		if (StringUtils.isNotBlank(keyWords)) {
			hql += " and name like '" + keyWords + "%'";
		}
		pageInfo.setTotalResult(getCountBase(hql));
		return findPageByHQLBase(hql, pageInfo);*/
		return null;
	}

	public void reviewCvReg(String id, String status) {
		/*String hql = "update CV0810011REG set reviewStatus = ?,reviewTime=SYSDATE where id =?";
		execUpdateHQLBase(hql, Integer.valueOf(status), Long.valueOf(id));*/
	}

	public CV0810011REG getById(String id) {
		/*return baseGet(CV0810011REG.class, Long.valueOf(id));*/
		return null;
	}

	public void saveOrUpdate(CV0810011REG cvReg) {

		/*if (cvReg.getId() == null) {
			String sql = "select max(id)  from CV0810011REG";
			Long id = (Long) sessionFactoryBase.getCurrentSession().createQuery(sql).uniqueResult();
			if (id == null) {
				id = 0L;
			}
			id++;
			cvReg.setId(id);
			cvReg.setReviewStatus(0);
			cvReg.setSourceType(2);
		}
		cvReg.setInsertDate(new Timestamp(new Date().getTime()));
		saveOrUpdateBase(cvReg);*/
	}

	// 插入主表数据,插入之前需要判断是否已经存在如果存在则做更新的操作
	public void saveCV0810011(CV0810011REG cvReg) {
		/*Session session = sessionFactoryBase.getCurrentSession();
		String hql = "  from CV0810011 where code = ?";
		CV0810011 model = (CV0810011) session.createQuery(hql).setString(0, cvReg.getCode()).uniqueResult();
		if (model == null) {
			model = new CV0810011();
			model.setCode(cvReg.getCode());
			model.setName(cvReg.getName());
			model.setSource(cvReg.getSource());
			model.setSourceType(cvReg.getSourceType());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
			session.save(model);
		} else {
			model.setCode(cvReg.getCode());
			model.setName(cvReg.getName());
			model.setSource(cvReg.getSource());
			model.setSourceType(cvReg.getSourceType());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
			session.update(model);
		}*/
	}

}
