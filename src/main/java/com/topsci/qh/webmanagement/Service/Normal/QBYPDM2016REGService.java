package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.QBYPDM2016REG;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 药品代码管理
 * 
 * @author Administrator
 *
 */
@Service
public class QBYPDM2016REGService extends BasicService {

	@SuppressWarnings("unchecked")
	public List<QBYPDM2016REG> findPageList(PageInfo pageInfo, String keyWords) {
	/*	String hql = "from QBYPDM2016REG reg where reg.reviewStatus = 0 ";
		if (StringUtils.isNotBlank(keyWords)) {
			hql += " and reg.ypmc like '" + keyWords + "%'";
		}
		pageInfo.setTotalResult(getCountBase(hql));
		return findPageByHQLBase(hql, pageInfo);*/
		return null;
	}

	public void reviewYpReg(String id, String status) {
		/*String hql = "update QBYPDM2016REG set reviewStatus = ?,reviewTime=SYSDATE where id =?";
		execUpdateHQLBase(hql, Integer.valueOf(status), Long.valueOf(id));*/
	}

	public QBYPDM2016REG getById(String id) {
		/*return baseGet(QBYPDM2016REG.class, Long.valueOf(id));*/
		return null;
	}

	public void saveOrUpdate(QBYPDM2016REG ypReg) {

		/*if (ypReg.getId() == null) {
			String sql = "select max(id)  from QBYPDM2016REG";
			Long id = (Long) sessionFactoryBase.getCurrentSession().createQuery(sql).uniqueResult();
			if (id == null) {
				id = 0L;
			}
			id++;
			ypReg.setId(id);
			ypReg.setReviewStatus(0);
			ypReg.setSourceType(2);
		}
		saveOrUpdateBase(ypReg);*/
	}

	// 插入主表数据,插入之前需要判断是否已经存在如果存在则做更新的操作
	public void saveQBYPDM2016(QBYPDM2016REG ypReg) {
		/*Session session = sessionFactoryBase.getCurrentSession();
		String hql = "  from QBYPDM2016 where ypid = ?";
		QBYPDM2016 model = (QBYPDM2016) session.createQuery(hql).setString(0,ypReg.getYpid()).uniqueResult();
		if(model == null) {
			model = new QBYPDM2016();
			hql = " select max(id) from QBYPDM2016";
			Long num = (Long) session.createQuery(hql).uniqueResult();
			model.setId(num == null ? 1l : num+1);
		}
			model.setYpid(ypReg.getYpid());
			model.setCz(ypReg.getCz());
			model.setGg(ypReg.getGg());
			model.setJxflm(ypReg.getJxflm());
			model.setJxflmc(ypReg.getJxflmc());
			model.setPzwh(ypReg.getPzwh());
			model.setQybm(ypReg.getQybm());
			model.setQymc(ypReg.getQymc());
			model.setSgyflm(ypReg.getSgyflm());
			model.setSgyj(ypReg.getSgyj());
			model.setSign(ypReg.getSign());
			model.setSource(ypReg.getSource());
			model.setSourceType(ypReg.getSourceType());
			model.setSxm(ypReg.getSxm());
			model.setSxmbz(ypReg.getSxmbz());
			model.setTymCN(ypReg.getTymCN());
			model.setTymdm(ypReg.getTymdm());
			model.setTymEN(ypReg.getTymEN());
			model.setYlgxfl(ypReg.getYlgxfl());
			model.setYlgxflm(ypReg.getYlgxflm());
			model.setYplb(ypReg.getYplb());
			model.setYplbdm(ypReg.getYplbdm());
			model.setYpmc(ypReg.getYpmc());
			model.setZhxs(ypReg.getZhxs());
			model.setZhxsbm(ypReg.getZhxsbm());
			model.setZjggflm(ypReg.getZjggflm());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
			session.saveOrUpdate(model);*/

	}

}
