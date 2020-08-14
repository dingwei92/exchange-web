package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.ORGANIZATIONBZREG;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构管理
 * 
 * @author Administrator
 *
 */
@Service
public class ORGANIZATIONBZREGService extends BasicService {

	@SuppressWarnings("unchecked")
	public List<ORGANIZATIONBZREG> findPageList(PageInfo pageInfo,String keyWords) {
		/*String hql = "from ORGANIZATIONBZREG  where reviewStatus = 0 ";
		if(StringUtils.isNotBlank(keyWords)) {
			hql += " and orgName like '"+keyWords+"%'";
		}
		pageInfo.setTotalResult(getCountBase(hql));
		return findPageByHQLBase(hql, pageInfo);*/
		return null;
	}

	public void reviewOrgReg(String id, String status) {
		/*String hql = "update ORGANIZATIONBZREG set reviewStatus = ?,reviewTime = SYSDATE where indexId =?";
		execUpdateHQLBase(hql, Integer.valueOf(status), Long.valueOf(id));*/
	}

	public ORGANIZATIONBZREG getById(Long id) {
		//ORGANIZATIONBZREG orgReg = (ORGANIZATIONBZREG) sessionFactoryBase.getCurrentSession().get(ORGANIZATIONBZREG.class, id);
		return null;
	}

	public void saveORGANIZATIONBZ(ORGANIZATIONBZREG orgReg) {
		/*Session session = sessionFactoryBase.getCurrentSession();
		String hql = "  from Organizationbz where orgId = ?";
		Organizationbz model = (Organizationbz) session.createQuery(hql).setString(0, orgReg.getOrgId()).uniqueResult();
		if (model == null) {
			model = new Organizationbz();
			model.setAddress(orgReg.getAddress());
			model.setAreaCode(orgReg.getAreaCode());
			model.setCommunityAreaCode(orgReg.getCommunityAreaCode());
			model.setCountyAreaCode(orgReg.getCountyAreaCode());
			model.setDeptCode(orgReg.getDeptCode());
			model.setDeptTypeCode(orgReg.getDeptTypeCode());
			model.setEcoTypeCode(orgReg.getEcoTypeCode());
			model.setGovRelation(orgReg.getGovRelation());
			model.setOrgId(orgReg.getOrgId());
			model.setOrgName(orgReg.getOrgName());
			model.setParentId(orgReg.getParent().getOrgId());
			model.setSaniTypeCode(orgReg.getSaniTypeCode());
			model.setTel(orgReg.getTel());

			// 所属国家级机构id 写死 中国
			model.setUpid0("910000000001");
			// 所属省级机构id 写死 青海
			model.setUpid1("630000000146");
			// 所属市级和县级从父级中取
			Organizationbz parent = (Organizationbz) session.createQuery(hql)
					.setString(0, orgReg.getParent().getOrgId()).uniqueResult();
			model.setUpid2(parent.getUpid2());
			model.setUpid3(parent.getUpid3());
			model.setSource(orgReg.getSource());
			model.setSourceType(orgReg.getSourceType());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
		} else {
			model.setAddress(orgReg.getAddress());
			model.setAreaCode(orgReg.getAreaCode());
			model.setCommunityAreaCode(orgReg.getCommunityAreaCode());
			model.setCountyAreaCode(orgReg.getCountyAreaCode());
			model.setDeptCode(orgReg.getDeptCode());
			model.setDeptTypeCode(orgReg.getDeptTypeCode());
			model.setEcoTypeCode(orgReg.getEcoTypeCode());
			model.setGovRelation(orgReg.getGovRelation());
			model.setOrgId(orgReg.getOrgId());
			model.setOrgName(orgReg.getOrgName());
			model.setParentId(orgReg.getParent().getOrgId());
			model.setSaniTypeCode(orgReg.getSaniTypeCode());
			model.setTel(orgReg.getTel());
			model.setSource(orgReg.getSource());
			model.setSourceType(orgReg.getSourceType());
			model.setUpdateTime(new Timestamp(new Date().getTime()));
		}
		if(orgReg.getSourceType() == 3) {
			model.setAreaId(orgReg.getAreaId());
		}else {
			//要计算area_id
			if(StringUtils.isEmpty(orgReg.getGovRelation())) {
				if(StringUtils.isNotBlank(orgReg.getCommunityAreaCode())) {
					model.setAreaId(orgReg.getCommunityAreaCode());
				}else {
					if(StringUtils.isNotBlank(orgReg.getCountyAreaCode())) {
						model.setAreaId(orgReg.getCountyAreaCode());
					}else {
						if(StringUtils.isNotBlank(orgReg.getAreaCode())) {
							model.setAreaId(orgReg.getAreaCode());
						}
					}
				}
			}else {
				String code = "";
				if(StringUtils.isNotBlank(orgReg.getCommunityAreaCode())) {
					code = orgReg.getCommunityAreaCode();
				}else {
					if(StringUtils.isNotBlank(orgReg.getCountyAreaCode())) {
						code = orgReg.getCountyAreaCode();
					}else {
						if(StringUtils.isNotBlank(orgReg.getAreaCode())) {
							code = orgReg.getAreaCode();
						}
					}
				}
				if(StringUtils.isNotBlank(code)) {
					switch(orgReg.getGovRelation()) {
						case "1"://国家,省
						case "2":
							model.setAreaId(code.substring(0,2));
							break;
						case "3"://市
							model.setAreaId(code.substring(0,4));
							break;
						case "4"://县
						case "5":
							model.setAreaId(code.substring(0,6));
							break;
						case "6":
						case "7":
						case "8":
							model.setAreaId(code.substring(0,9));
							break;
						default :
							model.setAreaId(code);
							break;
					}
				}
			}
		}
		
		if(model.getIndexId() == 0) {
			session.save(model);
		}else {
			session.update(model);
		}*/
	}

	public void saveOrUpdate(ORGANIZATIONBZREG orgReg) {
		
		/*if(orgReg.getIndexId() == null) {
			String sql = "select max(indexId)  from ORGANIZATIONBZREG";
			Long id = (Long) sessionFactoryBase.getCurrentSession().createQuery(sql).uniqueResult();
			if(id == null) {
				id = 0L;
			}
			id ++;
			orgReg.setIndexId(id);
			orgReg.setReviewStatus(0);
			orgReg.setSourceType(2);
		}
		orgReg.setInsertDate(new Timestamp(new Date().getTime()));
		saveOrUpdateBase(orgReg);*/
	}
	
}
