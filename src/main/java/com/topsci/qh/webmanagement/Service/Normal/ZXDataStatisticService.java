package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.Organizationbz;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogDBinfo;
import com.topsci.qh.webmanagement.Pojo.ServerDistrict;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ZXDataStatisticService extends BasicService {
    private static String COUNTTYPE_INC = "INC";
    private static String COUNTTYPE_ALL = "ALL";

    public int countDistrictByParentCode(String id)
    {
      /*  String hql = "from ServerDistrict sso where sso.sj = ?";
        return getCount(hql,id);*/
      return 0;
    }

    public List<Object[]> getChildDistrict(String id,String date,String tpltype)
    {
        /*if(StringUtils.isEmpty(id))
        {
            id = "000000000000";
        }
        int level = getDisLevel(id);

        String sql = "select qh.ID,qh.BH,qh.MC,qh.DJ,qh.SJ,nvl(tbinc.num,0),nvl(tball.num,0) from DZ_XZQY_BZ qh left join " +
                "( select substr(org.area_code,0,"+level+") dis,sum(sts.QUANTITY) num from SERVER_TABLE_STATISTIC sts " +
                "left join ORGANIZATIONBZ org on sts.ORGID=org.ORG_ID " +
                "left join SERVER_CATALOG_TEMPLATE_TYPE sctt on sctt.UUID = sts.TPLTYPE " +
                "where sts.SDATE = ? and sts.COUNTTYPE = ? and sctt.DBUUID = ? "+ (level>2?"and org.AREA_CODE like '"+id.substring(0,level-2)+"%' ":"") +
                "group by substr(org.area_code,0,"+level+") " +
                ") tbinc on qh.bh = concat(tbinc.dis,?) " +
                "left join ( " +
                "select substr(org.area_code,0,"+level+") dis,sum(sts.QUANTITY) num from SERVER_TABLE_STATISTIC sts " +
                "left join ORGANIZATIONBZ org on sts.ORGID=org.ORG_ID " +
                "left join SERVER_CATALOG_TEMPLATE_TYPE sctt on sctt.UUID = sts.TPLTYPE " +
                "where sts.SDATE = ? and sts.COUNTTYPE = ? and sctt.DBUUID = ? "+ (level>2?"and org.AREA_CODE like '"+id.substring(0,level-2)+"%' ":"") +
                "group by substr(org.area_code,0,"+level+") " +
                ") tball on qh.bh = concat(tball.dis,?) " +
                "where qh.sj=? order by qh.ID";
        return findBySQL(sql,date,COUNTTYPE_INC,tpltype,getSuffix(level),date,COUNTTYPE_ALL,tpltype,getSuffix(level),id);
 */ return null;
    }

    public ServerDistrict getDistrictByCode(String code)
    {
        /*String hql = "from ServerDistrict sso where sso.bh = ?";
        List<ServerDistrict> list = findByHQL(hql,code);
        if(list!=null && list.size() >0)
        {
            return list.get(0);
        }*/
        return null;
    }

    private int getDisLevel(String code)
    {
        if(code.endsWith("000000000000"))
        {
            return 2;
        }
        if(code.endsWith("0000000000"))
        {
            return 4;
        }
        if(code.endsWith("00000000"))
        {
            return 6;
        }
        if(code.endsWith("000000"))
        {
            return 9;
        }
        return 0;
    }

    private String getSuffix(int level)
    {
        switch (level){
            case 2:return "0000000000";
            case 4:return "00000000";
            case 6:return "000000";
            default:return "";
        }
    }

    public List<Object[]> getChildSanitype(String code,String date,String tpltype)
    {
       /* String sql = "select newtab.CODE,newtab.NAME,newtab.incnum,newtab.allnum from ( " +
                "select sani.CODE,sani.NAME,nvl(tbinc.num,0) incnum,nvl(tball.num,0) allnum from GB_T_WS218 sani " +
                "left join (select substr(org.SANI_TYPE_CODE, 0, 1) sanitype, sum(sts.QUANTITY) num " +
                "                  from SERVER_TABLE_STATISTIC sts " +
                "                         left join ORGANIZATIONBZ org on sts.ORGID = org.ORG_ID " +
                "                         left join SERVER_CATALOG_TEMPLATE_TYPE sctt on sctt.UUID = sts.TPLTYPE " +
                "                  where sts.SDATE = ? " +
                "                    and sts.COUNTTYPE = ? " +
                "                    and sctt.DBUUID = ? " +
                "                    and org.AREA_CODE like '"+code.substring(0,6)+"%' " +
                "                  group by substr(org.SANI_TYPE_CODE, 0, 1)) tbinc on sani.CODE = tbinc.sanitype " +
                "       left join (select substr(org.SANI_TYPE_CODE, 0, 1) sanitype, sum(sts.QUANTITY) num " +
                "                  from SERVER_TABLE_STATISTIC sts " +
                "                         left join ORGANIZATIONBZ org on sts.ORGID = org.ORG_ID " +
                "                         left join SERVER_CATALOG_TEMPLATE_TYPE sctt on sctt.UUID = sts.TPLTYPE " +
                "                  where sts.SDATE = ? " +
                "                    and sts.COUNTTYPE = ? " +
                "                    and sctt.DBUUID = ? " +
                "                    and org.AREA_CODE like '"+code.substring(0,6)+"%' " +
                "                  group by substr(org.SANI_TYPE_CODE, 0, 1)) tball on sani.CODE = tball.sanitype " +
                "where length(sani.CODE)=1 ) newtab where incnum >0 or allnum>0 order by newtab.CODE";

        return findBySQL(sql,date,COUNTTYPE_INC,tpltype,date,COUNTTYPE_ALL,tpltype);*/
        return null;
    }

    public Map<String,String> getSaniTypeCode()
    {
       /* String sql = "select code,name from GB_T_WS218 where length(code)=1";
        List<Object[]> sanitypes = findBySQL(sql);
        Map<String,String> map = new HashMap<>();
        for(Object[] t:sanitypes)
        {
            map.put((String)t[0],(String)t[1]);
        }
        return map;*/
        return null;
    }

    public List<Object[]> getOrganBySaniTypeAndDis(String type,String dis,String date,String tpltype){
       /* String sql = "select org.ORG_ID,ORG_NAME,nvl(tbinc.num,0),nvl(tball.num,0) from ORGANIZATIONBZ org " +
                "  LEFT JOIN (select sts.ORGID,sum(sts.QUANTITY) num from SERVER_TABLE_STATISTIC sts " +
                "left join SERVER_CATALOG_TEMPLATE_TYPE sctt on sctt.UUID = sts.TPLTYPE "+
                "where sts.SDATE=? and sts.COUNTTYPE=? and sctt.DBUUID=? GROUP BY sts.ORGID) tbinc on tbinc.ORGID = org.ORG_ID " +
                "  LEFT JOIN (select sts.ORGID,sum(sts.QUANTITY) num from SERVER_TABLE_STATISTIC sts " +
                "left join SERVER_CATALOG_TEMPLATE_TYPE sctt on sctt.UUID = sts.TPLTYPE "+
                "where sts.SDATE=? and sts.COUNTTYPE=? and sctt.DBUUID=? GROUP BY sts.ORGID) tball on tball.ORGID = org.ORG_ID " +
                "where org.AREA_CODE like '"+dis+"%' and org.SANI_TYPE_CODE like '"+type+"%' order by org.ORG_ID";
        return findBySQL(sql,date,COUNTTYPE_INC,tpltype,date,COUNTTYPE_ALL,tpltype);*/
        return null;
    }

    public List<ServerCatalogDBinfo> getDBS()
    {
       /* return findByHQL("from ServerCatalogDBinfo scdb where scdb.sourcename like '%中心%'");*/
        return null;
    }

    public List<String[]> getOrgDate(String orgid,String sdate,String tpltype,PageInfo pageInfo)
    {
        /*List<String[]> res = new ArrayList<>();
        String sql = "select sct.REMARK,sct.NAME,nvl(sts1.QUANTITY,0),nvl(sts2.QUANTITY,0) from SERVER_CATALOG_TEMPLATE sct " +
                "LEFT JOIN SERVER_CATALOG_TEMPLATE_TYPE sctt on sctt.UUID = sct.TYPEUUID " +
                "  LEFT JOIN (select sts.* from SERVER_TABLE_STATISTIC sts " +
                "left join SERVER_CATALOG_TEMPLATE_TYPE sctt on sctt.UUID = sts.TPLTYPE "+
                "where SDATE='"+sdate+"' and ORGID='"+orgid+"' and COUNTTYPE = '"+COUNTTYPE_INC+"' and sctt.DBUUID='"+tpltype+"') " +
                "            sts1 on sts1.TABLENAME = sct.NAME " +
                "  LEFT JOIN (select sts.* from SERVER_TABLE_STATISTIC sts " +
                "left join SERVER_CATALOG_TEMPLATE_TYPE sctt on sctt.UUID = sts.TPLTYPE "+
                "where SDATE='"+sdate+"' and ORGID='"+orgid+"' and COUNTTYPE = '"+COUNTTYPE_ALL+"' and sctt.DBUUID='"+tpltype+"') " +
                "            sts2 on sts2.TABLENAME = sct.NAME " +
                "where sctt.DBUUID = '"+tpltype+"' " +
                "order by sct.id";
        pageInfo.setTotalResult(getSQLCount(sql));
        List<Object[]> l = findPageBySQL(sql,pageInfo);
        l.forEach(objects -> {
            String[] tmp = new String[objects.length];
            int i = 0;
            for(Object obj : objects)
            {
                tmp[i] = obj.toString();
                i++;
            }
            res.add(tmp);
        });
        return res;*/
        return null;
    }
}
