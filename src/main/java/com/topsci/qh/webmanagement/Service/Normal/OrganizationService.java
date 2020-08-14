package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.Organizationbz;
import com.topsci.qh.webmanagement.Resources.BasicService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lzw on 2017/4/18.
 */
@Service
public class OrganizationService extends BasicService{
    public String getUserOrganName(String id)
    {
        /*String hql = "from Organizationbz oi where oi.orgId = ?";
        List<Organizationbz> lists= findByHQL(hql,id);
        for(Organizationbz info : lists)
        {
            return info.getOrgName();
        }*/
        return "";
    }

    public String getUserOrganDis(String id)
    {
       /* String hql = "from Organizationbz oi where oi.orgId = ?";
        List<Organizationbz> lists= findByHQL(hql,id);
        for(Organizationbz info : lists)
        {
            return info.getAreaCode().replaceAll("0000000000","").replaceAll("00000000","").replaceAll("000000","");
        }*/
        return "";
    }
    
    public List<Organizationbz> findAll(){
    	/*String hql = " from Organizationbz";
    	return findByHQLBase(hql);*/
        return null;
    }
    
    public Organizationbz findByOrgId(String orgId) {
    	/*String hql = " from Organizationbz where orgId = ?";
    	return (Organizationbz) sessionFactoryBase.getCurrentSession().createQuery(hql).setParameter(0, orgId).uniqueResult();*/
        return null;
    }
}
