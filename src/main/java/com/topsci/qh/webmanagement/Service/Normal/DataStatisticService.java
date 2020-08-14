package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lzw.
 * 16-7-27
 */
@Component
@Service
public class DataStatisticService extends BasicService {
    private static Logger logger = LoggerFactory.getLogger(DataStatisticService.class);

    private static String TYPE_TPL = "TPL";
    private static String TYPE_DB = "DB";
    private static String TYPE_PCA = "PCA";
    private static String TYPE_BS = "BS";
    private static String TYPE_SCA = "SCA";

    public List<Map<String, Object>> getCatalogTemplateTypeResultTab(PageInfo pageInfo, String starttime, String endtime) {
        /*String sql = "select BUUID,sum(sds.CT),max(sds.SM) from SERVER_DATA_STATISTIC_TAB sds " +
                "where STYPE = '"+TYPE_TPL+"' and to_date(sds.SDATE,'YYYY-MM-DD')  " +
                "between to_date('"+starttime+"','YYYY-MM-DD HH24:MI:SS') and to_date('"+endtime+"','YYYY-MM-DD HH24:MI:SS') " +
                "group by BUUID "+
                "order by sum(sds.CT) desc";
        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
        Map<String,Object> map;
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] objs : tmpl) {
            if (objs[0] == null) {
                pageInfo.setTotalResult(pageInfo.getTotalResult() - 1);
                continue;
            }
            map = new LinkedHashMap<>();
            map.put("bid", objs[0]);
            map.put("message", ((BigDecimal) objs[1]).intValue());
            map.put("data", ((BigDecimal) objs[2]).intValue());
            list.add(map);
        }

        pageInfo.setTotalResult(getSQLCount(sql));
        return list;*/
        return null;
    }

    public List<Map<String, Object>> getCatalogTemplateTypeResult(PageInfo pageInfo, String starttime, String endtime) {
        /*String sql = "select BUUID,sum(sds.CT),max(sds.SM) from SERVER_DATA_STATISTIC_MESG sds " +
                "where STYPE = '"+TYPE_TPL+"' and to_date(sds.SDATE,'YYYY-MM-DD')  " +
                "between to_date('"+starttime+"','YYYY-MM-DD HH24:MI:SS') and to_date('"+endtime+"','YYYY-MM-DD HH24:MI:SS') " +
                "group by BUUID "+
                "order by sum(sds.CT) desc";
        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
        Map<String,Object> map;
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] objs : tmpl) {
            if (objs[0] == null) {
                pageInfo.setTotalResult(pageInfo.getTotalResult() - 1);
                continue;
            }
            map = new LinkedHashMap<>();
            map.put("bid", objs[0]);
            map.put("message", ((BigDecimal) objs[1]).intValue());
            map.put("data", ((BigDecimal) objs[2]).intValue());
            list.add(map);
        }

        pageInfo.setTotalResult(getSQLCount(sql));
        return list;*/
        return null;
    }
//    public List<Map<String, Object>> getCatalogTemplateTypeResultTab(PageInfo pageInfo, String starttime, String endtime) {
//        Map<String, Object> map = new LinkedHashMap<>();
//        List list = new ArrayList<>();
//        String startmonth = starttime.substring(5, 7);
//        String endmonth = endtime.substring(5, 7);
//        String tablenames = starttime.substring(0, 4) + starttime.substring(5, 7);
//        String tablenamee = endtime.substring(0, 4) + endtime.substring(5, 7);
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int month = c.get(Calendar.MONTH) + 1;
//        String curmonth;
//        if (month < 10) {
//            curmonth = "0" + month;
//        } else {
//            curmonth = month + "";
//        }
//        String sql;
//        boolean flag=true;
//        boolean flags=true;
//        boolean flage=true;
//        if(!tablenamee.equals(tablenames) && !endmonth.equals(curmonth))
//        {
//            flags = checkTableExist("SERVER_C_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//            flage = checkTableExist("SERVER_C_M_" + tablenamee) && checkTableExist("SERVER_M_D_" + tablenamee);
//        }
//        else if(!startmonth.equals(curmonth))
//        {
//            flag = checkTableExist("SERVER_C_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//        }
//        if ((startmonth.equals(endmonth) && endmonth.equals(curmonth)) || (!flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (!flag)) {
//            sql = "select * from (" +
//                    "SELECT " +
//                    "sct.TYPEUUID, " +
//                    "COUNT (*), " +
//                    "SUM (MSGCOUNT) " +
//                    "FROM " +
//                    "SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "GROUP BY " +
//                    "sct.TYPEUUID ORDER BY sct.TYPEUUID" +
//                    ")" +
//                    "union ALL " +
//                    "select distinct sctt.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_TEMPLATE_TYPE sctt where " +
//                    "sctt.uuid not in ( " +
//                    "SELECT " +
//                    "distinct sct.TYPEUUID " +
//                    "FROM " +
//                    "  SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        } else if ((flag && startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth))) {
//            sql = "select * from (" +
//                    "SELECT " +
//                    "sct.TYPEUUID, " +
//                    "COUNT (*), " +
//                    "SUM (MSGCOUNT) " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "GROUP BY " +
//                    "sct.TYPEUUID ORDER BY sct.TYPEUUID" +
//                    ")" +
//                    "union ALL " +
//                    "select distinct sctt.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_TEMPLATE_TYPE sctt " +
//                    "where sctt.uuid not in ( " +
//                    "SELECT " +
//                    "distinct sct.TYPEUUID " +
//                    "FROM " +
//                    "  SERVER_C_M_"+tablenames+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenames+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }else if ( !flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)) {
//            sql = "select * from (" +
//                    "SELECT " +
//                    "sct.TYPEUUID, " +
//                    "COUNT (*), " +
//                    "SUM (MSGCOUNT) " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenamee + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenamee + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "GROUP BY " +
//                    "sct.TYPEUUID ORDER BY sct.TYPEUUID" +
//                    ")" +
//                    "union ALL " +
//                    "select distinct sctt.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_TEMPLATE_TYPE sctt where " +
//                    "sctt.uuid not in ( " +
//                    "SELECT " +
//                    "distinct sct.TYPEUUID " +
//                    "FROM " +
//                    "  SERVER_C_M_"+tablenamee+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenamee+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }
//        else if(flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)){
//            sql = "select * from (" +
//                    "select t.tuuid,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "sct.TYPEUUID tuuid, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_C_M_"+tablenamee+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenamee+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "union all " +
//                    "SELECT " +
//                    "sct.TYPEUUID, MSGCOUNT, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    ") t" +
//                    "GROUP BY " +
//                    "t.tuuid " +
//                    "ORDER BY " +
//                    "t.tuuid"+
//                    ")" +
//                    "union ALL " +
//                    "select distinct sctt.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_TEMPLATE_TYPE sctt where " +
//                    "sctt.UUID not in ( " +
//                    "SELECT " +
//                    "distinct sct.TYPEUUID " +
//                    "FROM " +
//                    "  SERVER_C_M_"+tablenamee+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenamee+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }
//        else {
//            sql = "select * from (" +
//                    "select t.tuuid,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "sct.TYPEUUID tuuid, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "union all " +
//                    "SELECT " +
//                    "sct.TYPEUUID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    ") " +
//                    "t " +
//                    "GROUP BY " +
//                    "t.tuuid " +
//                    "ORDER BY " +
//                    "t.tuuid"+
//                    ")" +
//                    "union ALL " +
//                    "select distinct sctt.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_TEMPLATE_TYPE sctt where " +
//                    "sctt.UUID not in ( " +
//                    "SELECT " +
//                    "distinct sct.TYPEUUID " +
//                    "FROM " +
//                    "  SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }
//
//        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
//        for (Object[] objs : tmpl) {
//            if(objs[0] == null){
//                pageInfo.setTotalResult(pageInfo.getTotalResult()-1);
//                continue;
//            }
//            map = new LinkedHashMap<>();
//            map.put("bid", objs[0]);
//            map.put("message", ((BigDecimal) objs[1]).intValue());
//            map.put("data", ((BigDecimal) objs[2]).intValue());
//            list.add(map);
//        }
//
//        pageInfo.setTotalResult(getSQLCount(sql));
//        return list;
//    }

    public List<Map<String, Object>> getCatalogDBResultTab(PageInfo pageInfo, String starttime, String endtime, String typeid) {
       /* String sql = "select BUUID,sum(sds.CT),max(sds.SM) from SERVER_DATA_STATISTIC_TAB sds " +
                "where STYPE = '"+TYPE_DB+"' and to_date(sds.SDATE,'YYYY-MM-DD')  between to_date('"+starttime+"','YYYY-MM-DD HH24:MI:SS') and to_date('"+endtime+"','YYYY-MM-DD HH24:MI:SS') " +
                "and exists( " +
                "    select 1 from SERVER_CATALOG sc left join SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID " +
                "                                    left join SERVER_CATALOG_DBINFO scd on scd.UUID = scdp.DB_ID " +
                "                                   left join SERVER_CATALOG_TEMPLATE sct on sct.id = sc.TEMPLATE_ID " +
                "       where sds.BUUID = scd.uuid and sct.TYPEUUID = '"+typeid+"' " +
                "    ) group by BUUID  order by sum(sds.CT) desc";
        Map<String,Object> map;
        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] objs : tmpl) {
            map = new LinkedHashMap<>();
            map.put("bid", objs[0]);
            map.put("message", ((BigDecimal) objs[1]).intValue());
            map.put("data", ((BigDecimal) objs[2]).intValue());
            list.add(map);
        }

        pageInfo.setTotalResult(getSQLCount(sql));
        return list;*/
        return null;
    }
//    public List<Map<String, Object>> getCatalogDBResultTab(PageInfo pageInfo, String starttime, String endtime,String typeid) {
//        Map<String, Object> map = new LinkedHashMap<>();
//        List list = new ArrayList<>();
//        String startmonth = starttime.substring(5, 7);
//        String endmonth = endtime.substring(5, 7);
//        String tablenames = starttime.substring(0, 4) + starttime.substring(5, 7);
//        String tablenamee = endtime.substring(0, 4) + endtime.substring(5, 7);
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int month = c.get(Calendar.MONTH) + 1;
//        String curmonth;
//        if (month < 10) {
//            curmonth = "0" + month;
//        } else {
//            curmonth = month + "";
//        }
//        String sql;
//        boolean flag=true;
//        boolean flags=true;
//        boolean flage=true;
//        if(!tablenamee.equals(tablenames) && !endmonth.equals(curmonth))
//        {
//            flags = checkTableExist("SERVER_C_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//            flage = checkTableExist("SERVER_C_M_" + tablenamee) && checkTableExist("SERVER_M_D_" + tablenamee);
//        }
//        else if(!startmonth.equals(curmonth))
//        {
//            flag = checkTableExist("SERVER_C_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//        }
//        if ((startmonth.equals(endmonth) && endmonth.equals(curmonth)) || (!flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (!flag)) {
//            sql = "select * from (" +
//                    "SELECT " +
//                    "scdp.DB_ID, " +
//                    "COUNT (*), " +
//                    "SUM (MSGCOUNT) " +
//                    "FROM " +
//                    "SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "GROUP BY " +
//                    "scdp.DB_ID ORDER BY scdp.DB_ID" +
//                    ")" +
//                    "union ALL " +
//                    "select distinct scd.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_DBINFO scd " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.DB_ID = scd.UUID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sc.TEMPLATE_ID = sct.ID "+
//                    "where sct.TYPEUUID = '"+ typeid +"' and scd.UUID not in ( " +
//                    "SELECT " +
//                    "distinct scdp.DB_ID " +
//                    "FROM " +
//                    "  SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        } else if ((flag && startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth))) {
//            sql = "select * from (" +
//                    "SELECT " +
//                    "scdp.DB_ID, " +
//                    "COUNT (*), " +
//                    "SUM (MSGCOUNT) " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "GROUP BY " +
//                    "scdp.DB_ID ORDER BY scdp.DB_ID" +
//                    ")" +
//                    "union ALL " +
//                    "select distinct scd.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_DBINFO scd " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.DB_ID = scd.UUID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sc.TEMPLATE_ID = sct.ID "+
//                    "where sct.TYPEUUID = '"+typeid+"' scd.UUID not in ( " +
//                    "SELECT " +
//                    "distinct scdp.DB_ID " +
//                    "FROM " +
//                    "  SERVER_C_M_"+tablenames+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenames+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }else if ( !flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)) {
//            sql = "select * from (" +
//                    "SELECT " +
//                    "scdp.DB_ID, " +
//                    "COUNT (*), " +
//                    "SUM (MSGCOUNT) " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenamee + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenamee + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' and TO_DATE(smd.LASTDATE, 'yyyy-mm-dd hh24:mi:ss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "GROUP BY " +
//                    "scdp.DB_ID ORDER BY scdp.DB_ID" +
//                    ")" +
//                    "union ALL " +
//                    "select distinct scd.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_DBINFO scd " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.DB_ID = scd.UUID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sc.TEMPLATE_ID = sct.ID "+
//                    "where sct.TYPEUUID = '"+typeid+"' and scd.UUID not in ( " +
//                    "SELECT " +
//                    "distinct scdp.DB_ID " +
//                    "FROM " +
//                    "  SERVER_C_M_"+tablenamee+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenamee+" sct on sct.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }
//        else if(flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)){
//            sql = "select * from (" +
//                    "select t.tuuid,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "scdp.DB_ID tuuid, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_C_M_"+tablenamee+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenamee+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' " +
//                    "AND TO_DATE ( " +
//                    "smd.LASTDATE, " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "union all " +
//                    "SELECT " +
//                    "scdp.DB_ID, MSGCOUNT, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' " +
//                    "AND TO_DATE ( " +
//                    "smd.LASTDATE, " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    ") t" +
//                    "GROUP BY " +
//                    "t.tuuid " +
//                    "ORDER BY " +
//                    "t.tuuid"+
//                    ")" +
//                    "union ALL " +
//                    "select distinct scd.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_DBINFO scd " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.DB_ID = scd.UUID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sc.TEMPLATE_ID = sct.ID "+
//                    "where sct.TYPEUUID = '"+typeid+"' scd.UUID not in ( " +
//                    "SELECT " +
//                    "distinct scdp.DB_ID " +
//                    "FROM " +
//                    "  SERVER_C_M_"+tablenamee+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenamee+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }
//        else {
//            sql = "select * from (" +
//                    "select t.tuuid,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "scdp.DB_ID tuuid, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "union all " +
//                    "SELECT " +
//                    "scdp.DB_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    ") " +
//                    "t " +
//                    "GROUP BY " +
//                    "t.tuuid " +
//                    "ORDER BY " +
//                    "t.tuuid"+
//                    ")" +
//                    "union ALL " +
//                    "select distinct scd.UUID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG_DBINFO scd " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.DB_ID = scd.UUID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sc.TEMPLATE_ID = sct.ID "+
//                    "where sct.TYPEUUID = '"+typeid+"' and scd.UUID not in ( " +
//                    "SELECT " +
//                    "distinct scdp.DB_ID " +
//                    "FROM " +
//                    "  SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.ID = scm.SERVER_CATALOG_ID " +
//                    "LEFT JOIN SERVER_CATALOG_TEMPLATE sct on sct.ID = sc.TEMPLATE_ID " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID "+
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' and sct.TYPEUUID = '"+ typeid +"' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }
//
//        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
//        for (Object[] objs : tmpl) {
//            map = new LinkedHashMap<>();
//            map.put("bid", objs[0]);
//            map.put("message", ((BigDecimal) objs[1]).intValue());
//            map.put("data", ((BigDecimal) objs[2]).intValue());
//            list.add(map);
//        }
//
//        pageInfo.setTotalResult(getSQLCount(sql));
//        return list;
//    }

    public List<Map<String, Object>> getCatalogDBResult(PageInfo pageInfo, String starttime, String endtime, String typeid) {
        /*String sql = "select BUUID,sum(sds.CT),max(sds.SM) from SERVER_DATA_STATISTIC_MESG sds " +
                "where STYPE = '"+TYPE_DB+"' and to_date(sds.SDATE,'YYYY-MM-DD')  between to_date('"+starttime+"','YYYY-MM-DD HH24:MI:SS') and to_date('"+endtime+"','YYYY-MM-DD HH24:MI:SS') " +
                "and exists( " +
                "    select 1 from SERVER_CATALOG sc left join SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc.DB_ID " +
                "                                    left join SERVER_CATALOG_DBINFO scd on scd.UUID = scdp.DB_ID " +
                "                                   left join SERVER_CATALOG_TEMPLATE sct on sct.id = sc.TEMPLATE_ID " +
                "       where sds.BUUID = scd.uuid and sct.TYPEUUID = '"+typeid+"' " +
                "    ) group by BUUID  order by sum(sds.CT) desc";
        Map<String,Object> map;
        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] objs : tmpl) {
            map = new LinkedHashMap<>();
            map.put("bid", objs[0]);
            map.put("message", ((BigDecimal) objs[1]).intValue());
            map.put("data", ((BigDecimal) objs[2]).intValue());
            list.add(map);
        }

        pageInfo.setTotalResult(getSQLCount(sql));
        return list;*/
        return null;
    }

	public List<Map<String, Object>> getBussinessResult(PageInfo pageInfo, String starttime, String endtime) {
        Map<String, Object> map = new LinkedHashMap<>();
        List list = new ArrayList<>();
        String startmonth = starttime.substring(5, 7);
        String endmonth = endtime.substring(5, 7);
        String tablenames = starttime.substring(0, 4) + starttime.substring(5, 7);
        String tablenamee = endtime.substring(0, 4) + endtime.substring(5, 7);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int month = c.get(Calendar.MONTH) + 1;
        String curmonth;
        if (month < 10) {
            curmonth = "0" + month;
        } else {
            curmonth = month + "";
        }
        String sql;
        boolean flag=true;
        boolean flags=true;
        boolean flage=true;
        if(!tablenamee.equals(tablenames) && !endmonth.equals(curmonth))
        {
        	flags = checkTableExist("SERVER_C_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
            flage = checkTableExist("SERVER_C_M_" + tablenamee) && checkTableExist("SERVER_M_D_" + tablenamee);
        }
        else if(!startmonth.equals(curmonth))
        {
            flag = checkTableExist("SERVER_C_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
        }
        if ((startmonth.equals(endmonth) && endmonth.equals(curmonth)) || (!flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (!flag)) {
            sql = "select * from (" +
                    "SELECT " +
                    "BUSINESS_SYSTEM_ID, " +
                    "COUNT (*), " +
                    "SUM (MSGCOUNT) " +
                    "FROM " +
                    "SERVER_CATALOG_MESSAGE scm " +
                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
                    "WHERE " +
                    "SCM.STATUS = 'Y' and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
                    "GROUP BY " +
                    "BUSINESS_SYSTEM_ID ORDER BY BUSINESS_SYSTEM_ID" +
                    ")" +
                    "union ALL " +
                    "select distinct BUSINESS_SYSTEM_ID," +
                    "0 as message," +
                    "0 as datacount from SERVER_CATALOG sc where sc.DELETED = 'N'" +
                    "and sc.BUSINESS_SYSTEM_ID not in ( " +
                    "SELECT " +
                    "distinct scm.BUSINESS_SYSTEM_ID " +
                    "FROM " +
                    "  SERVER_CATALOG_MESSAGE scm " +
                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
                    "WHERE " +
                    "  scm.STATUS = 'Y' " +
                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
                    "  '" + starttime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "AND TO_DATE ( " +
                    "  '" + endtime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ")  " +
                    ") ";
        } else if ((flag && startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth))) {
            sql = "select * from (" +
                    "SELECT " +
                    "BUSINESS_SYSTEM_ID, " +
                    "COUNT (*), " +
                    "SUM (MSGCOUNT) " +
                    "FROM " +
                    "SERVER_C_M_" + tablenames + " scm " +
                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
                    "WHERE " +
                    "SCM.STATUS = 'Y' and TO_DATE(smd.LASTDATE, 'yyyy-mm-dd hh24:mi:ss') " +
                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
                    "GROUP BY " +
                    "BUSINESS_SYSTEM_ID ORDER BY BUSINESS_SYSTEM_ID" +
                    ")" +
                    "union ALL " +
                    "select distinct BUSINESS_SYSTEM_ID," +
                    "0 as message," +
                    "0 as datacount from SERVER_CATALOG sc where sc.DELETED = 'N'" +
                    "and sc.BUSINESS_SYSTEM_ID not in ( " +
                    "SELECT " +
                    "distinct scm.BUSINESS_SYSTEM_ID " +
                    "FROM " +
                    "  SERVER_C_M_"+tablenames+" scm " +
                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
                    "WHERE " +
                    "  scm.STATUS = 'Y' " +
                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
                    "  '" + starttime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "AND TO_DATE ( " +
                    "  '" + endtime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ")  " +
                    ") ";
        }else if ( !flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)) {
            sql = "select * from (" +
                    "SELECT " +
                    "BUSINESS_SYSTEM_ID, " +
                    "COUNT (*), " +
                    "SUM (MSGCOUNT) " +
                    "FROM " +
                    "SERVER_C_M_" + tablenamee + " scm " +
                    "LEFT JOIN SERVER_M_D_" + tablenamee + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
                    "WHERE " +
                    "SCM.STATUS = 'Y' and TO_DATE(smd.LASTDATE, 'yyyy-mm-dd hh24:mi:ss') " +
                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
                    "GROUP BY " +
                    "BUSINESS_SYSTEM_ID ORDER BY BUSINESS_SYSTEM_ID" +
                    ")" +
                    "union ALL " +
                    "select distinct BUSINESS_SYSTEM_ID," +
                    "0 as message," +
                    "0 as datacount from SERVER_CATALOG sc where sc.DELETED = 'N'" +
                    "and sc.BUSINESS_SYSTEM_ID not in ( " +
                    "SELECT " +
                    "distinct scm.BUSINESS_SYSTEM_ID " +
                    "FROM " +
                    "  SERVER_C_M_"+tablenamee+" scm " +
                    "WHERE " +
                    "  scm.STATUS = 'Y' " +
                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
                    "  '" + starttime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "AND TO_DATE ( " +
                    "  '" + endtime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ")  " +
                    ") ";
        }
        else if(flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)){
            sql = "select * from (" +
                    "select BUSINESS_SYSTEM_ID,count(*),sum(MSGCOUNT) from ( " +
                    "SELECT " +
                    "BUSINESS_SYSTEM_ID, " +
                    "MSGCOUNT " +
                    "FROM " +
                    "SERVER_C_M_"+tablenamee+" scm " +
                    "LEFT JOIN SERVER_M_D_"+tablenamee+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
                    "WHERE " +
                    "SCM.STATUS = 'Y' " +
                    "AND TO_DATE ( " +
                    "smd.LASTDATE, " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") BETWEEN TO_DATE ( " +
                    "'" + starttime + "', " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "AND TO_DATE ( " +
                    "'" + endtime + "', " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "union all " +
                    "SELECT " +
                    "BUSINESS_SYSTEM_ID, " +
                    "MSGCOUNT " +
                    "FROM " +
                    "SERVER_C_M_" + tablenames + " scm " +
                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
                    "WHERE " +
                    "SCM.STATUS = 'Y' " +
                    "AND TO_DATE ( " +
                    "smd.LASTDATE, " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") BETWEEN TO_DATE ( " +
                    "'" + starttime + "', " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "AND TO_DATE ( " +
                    "'" + endtime + "', " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    ") " +
                    "GROUP BY " +
                    "BUSINESS_SYSTEM_ID " +
                    "ORDER BY " +
                    "BUSINESS_SYSTEM_ID"+
                    ")" +
                    "union ALL " +
                    "select distinct BUSINESS_SYSTEM_ID," +
                    "0 as message," +
                    "0 as datacount from SERVER_CATALOG sc where sc.DELETED = 'N'" +
                    "and sc.BUSINESS_SYSTEM_ID not in ( " +
                    "SELECT " +
                    "distinct scm.BUSINESS_SYSTEM_ID " +
                    "FROM " +
                    "  SERVER_CATALOG_MESSAGE scm " +
                    "WHERE " +
                    "  scm.STATUS = 'Y' " +
                    "AND TO_DATE ( " +
                    "  scm.LASTDATE, " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ") BETWEEN TO_DATE ( " +
                    "  '" + starttime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "AND TO_DATE ( " +
                    "  '" + endtime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ")  " +
                    ") ";
        }
        else {
            sql = "select * from (" +
                    "select BUSINESS_SYSTEM_ID,count(*),sum(MSGCOUNT) from ( " +
                    "SELECT " +
                    "BUSINESS_SYSTEM_ID, " +
                    "MSGCOUNT " +
                    "FROM " +
                    "SERVER_CATALOG_MESSAGE scm " +
                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
                    "WHERE " +
                    "SCM.STATUS = 'Y' " +
                    "AND TO_DATE ( " +
                    "smd.LASTDATE, " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") BETWEEN TO_DATE ( " +
                    "'" + starttime + "', " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "AND TO_DATE ( " +
                    "'" + endtime + "', " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "union all " +
                    "SELECT " +
                    "BUSINESS_SYSTEM_ID, " +
                    "MSGCOUNT " +
                    "FROM " +
                    "SERVER_C_M_" + tablenames + " scm " +
                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
                    "WHERE " +
                    "SCM.STATUS = 'Y' " +
                    "AND TO_DATE ( " +
                    "smd.LASTDATE, " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") BETWEEN TO_DATE ( " +
                    "'" + starttime + "', " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "AND TO_DATE ( " +
                    "'" + endtime + "', " +
                    "'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    ") " +
                    " " +
                    "GROUP BY " +
                    "BUSINESS_SYSTEM_ID " +
                    "ORDER BY " +
                    "BUSINESS_SYSTEM_ID"+
                    ")" +
                    "union ALL " +
                    "select distinct BUSINESS_SYSTEM_ID," +
                    "0 as message," +
                    "0 as datacount from SERVER_CATALOG sc where sc.DELETED = 'N'" +
                    "and sc.BUSINESS_SYSTEM_ID not in ( " +
                    "SELECT " +
                    "distinct scm.BUSINESS_SYSTEM_ID " +
                    "FROM " +
                    "  SERVER_CATALOG_MESSAGE scm " +
                    "WHERE " +
                    "  scm.STATUS = 'Y' " +
                    "AND TO_DATE ( " +
                    "  scm.LASTDATE, " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ") BETWEEN TO_DATE ( " +
                    "  '" + starttime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ") " +
                    "AND TO_DATE ( " +
                    "  '" + endtime + "', " +
                    "  'yyyy-mm-dd hh24:mi:ss' " +
                    ")  " +
                    ") ";
        }


      /*  List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
        for (Object[] objs : tmpl) {
            map = new LinkedHashMap<>();
            map.put("bid", ((BigDecimal) objs[0]).intValue() + "");
            map.put("message", ((BigDecimal) objs[1]).intValue());
            map.put("data", ((BigDecimal) objs[2]).intValue());
            list.add(map);
        }

        pageInfo.setTotalResult(getSQLCount(sql));
        return list;*/
        return null;
    }

    public List<Map<String, Object>> getCatalogResultTab(PageInfo pageInfo, String starttime, String endtime, String did) {
        String sql = "select tb1.* from (select bs.id,sc.id sid,sum(sds.CT) SCT,sum(sds.SM) SSM from SERVER_DATA_STATISTIC_TAB sds left join SERVER_CATALOG sc on sc.ID = to_number(sds.BUUID) left join BUSINESS_SYSTEM bs on bs.id = sc.BUSINESS_SYSTEM_ID " +
                "where STYPE = '"+TYPE_PCA+"' and to_date(sds.SDATE,'YYYY-MM-DD')  between to_date('"+starttime+"','YYYY-MM-DD HH24:MI:SS') and to_date('"+endtime+"','YYYY-MM-DD HH24:MI:SS') " +
                "and exists( " +
                "    select 1 from SERVER_CATALOG sc1 left join SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc1.DB_ID left join SERVER_CATALOG_DBINFO scd on scd.UUID = scdp.DB_ID " +
                "    where scd.UUID = '"+did+"' and sc1.id = sds.BUUID " +
                "          ) group by bs.id,sc.id )tb1 order by tb1.SCT DESC";
        Map<String,Object> map;
       /* List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] objs : tmpl) {
            map = new LinkedHashMap<>();
            map.put("bid", ((BigDecimal) objs[0]).intValue() + "");
            map.put("cid", ((BigDecimal) objs[1]).intValue());
            map.put("message", ((BigDecimal) objs[2]).intValue());
            map.put("data", ((BigDecimal) objs[3]).intValue());
            list.add(map);
        }

        pageInfo.setTotalResult(getSQLCount(sql));
        return list;*/
        return null;
    }
//    public List<Map<String, Object>> getCatalogResultTab(PageInfo pageInfo, String starttime, String endtime,String did) {
//        Map<String, Object> map = new LinkedHashMap<>();
//        List list = new ArrayList<>();
//        String startmonth = starttime.substring(5, 7);
//        String endmonth = endtime.substring(5, 7);
//        String tablenames = starttime.substring(0, 4) + starttime.substring(5, 7);
//        String tablenamee = endtime.substring(0, 4) + endtime.substring(5, 7);
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int month = c.get(Calendar.MONTH) + 1;
//        String curmonth;
//        if (month < 10) {
//            curmonth = "0" + month;
//        } else {
//            curmonth = month + "";
//        }
//        String sql;
//        boolean flag=true;
//        boolean flags=true;
//        boolean flage=true;
//        if(!tablenamee.equals(tablenames) && !endmonth.equals(curmonth))
//        {
//        	flags = checkTableExist("SERVER_C_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//            flage = checkTableExist("SERVER_C_M_" + tablenamee) && checkTableExist("SERVER_M_D_" + tablenamee);
//        }
//        else if(!startmonth.equals(curmonth))
//        {
//            flag = checkTableExist("SERVER_C_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//        }
//        if ((startmonth.equals(endmonth) && endmonth.equals(curmonth)) || (!flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (!flag)) {
//            sql = "select * from (" +
//                    "SELECT " +
//                    "scm.BUSINESS_SYSTEM_ID, " +
//                    "SERVER_CATALOG_ID, " +
//                    "COUNT (*), " +
//                    "SUM (MSGCOUNT) " +
//                    "FROM " +
//                    "SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    ("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    "GROUP BY " +
//                    "scm.BUSINESS_SYSTEM_ID,SERVER_CATALOG_ID  ORDER BY SERVER_CATALOG_ID" +
//                    ")" +
//                    "union ALL " +
//                    "select BUSINESS_SYSTEM_ID," +
//                    "ID as SERVER_CATALOG_ID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG sc " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "where sc.DELETED = 'N' " +
//                    ("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    "and sc.id not in ( " +
//                    "SELECT " +
//                    "distinct scm.SERVER_CATALOG_ID " +
//                    "FROM " +
//                    "  SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        } else if ((flag && startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth))){
//            sql = "select * from (" +
//                    "SELECT " +
//                    "scm.BUSINESS_SYSTEM_ID, " +
//                    "SERVER_CATALOG_ID, " +
//                    "COUNT (*), " +
//                    "SUM (MSGCOUNT) " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and TO_DATE(smd.LASTDATE, 'yyyy-mm-dd hh24:mi:ss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    ("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    "GROUP BY " +
//                    "scm.BUSINESS_SYSTEM_ID,SERVER_CATALOG_ID ORDER BY SERVER_CATALOG_ID" +
//                    ")" +
//                    "union ALL " +
//                    "select BUSINESS_SYSTEM_ID," +
//                    "ID as SERVER_CATALOG_ID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG sc " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "where sc.DELETED = 'N' " +
//                    ("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    "and sc.id not in ( " +
//                    "SELECT " +
//                    "distinct scm.SERVER_CATALOG_ID " +
//                    "FROM " +
//                    "  SERVER_C_M_"+tablenames+" scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }else if (!flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)){
//            sql = "select * from (" +
//                    "SELECT " +
//                    "scm.BUSINESS_SYSTEM_ID, " +
//                    "SERVER_CATALOG_ID, " +
//                    "COUNT (*), " +
//                    "SUM (MSGCOUNT) " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenamee + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenamee + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    ("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    "GROUP BY " +
//                    "scm.BUSINESS_SYSTEM_ID,SERVER_CATALOG_ID ORDER BY SERVER_CATALOG_ID" +
//                    ")" +
//                    "union ALL " +
//                    "select BUSINESS_SYSTEM_ID," +
//                    "ID as SERVER_CATALOG_ID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG sc " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "where sc.DELETED = 'N' " +
//                    ("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    "and sc.id not in ( " +
//                    "SELECT " +
//                    "distinct scm.SERVER_CATALOG_ID " +
//                    "FROM " +
//                    "  SERVER_C_M_"+tablenamee+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenamee+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") ";
//        }
//        else if(flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)){
//            sql = "select * from (" +
//                    "select BUSINESS_SYSTEM_ID,SERVER_CATALOG_ID,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "scm.BUSINESS_SYSTEM_ID, " +
//                    "SERVER_CATALOG_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_C_M_"+tablenamee+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenamee+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") "  +
//                    ("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    "union all " +
//                    "SELECT " +
//                    "scm.BUSINESS_SYSTEM_ID, " +
//                    "SERVER_CATALOG_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +"" +
//                    ("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    ") " +
//                    "GROUP BY " +
//                    "scm.BUSINESS_SYSTEM_ID,SERVER_CATALOG_ID " +
//                    "ORDER BY " +
//                    "SERVER_CATALOG_ID"+
//                    ")" +
//                    "union ALL " +
//                    "select BUSINESS_SYSTEM_ID," +
//                    "ID as SERVER_CATALOG_ID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG sc " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "where sc.DELETED = 'N'" +
//                    "and sc.id not in ( " +
//                    "SELECT " +
//                    "distinct scm.SERVER_CATALOG_ID " +
//                    "FROM " +
//                    "  SERVER_C_M_"+tablenamee+" scm " +
//                    "LEFT JOIN SERVER_M_D_"+tablenamee+" smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") "+("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ");
//        }
//        else {
//            sql = "select * from (" +
//                    "select BUSINESS_SYSTEM_ID,SERVER_CATALOG_ID,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "scm.BUSINESS_SYSTEM_ID, " +
//                    "SERVER_CATALOG_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    "union all " +
//                    "SELECT " +
//                    "scm.BUSINESS_SYSTEM_ID, " +
//                    "SERVER_CATALOG_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_C_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "LEFT JOIN SERVER_CATALOG sc on sc.id = scm.SERVER_CATALOG_ID "+
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ")+
//                    ") " +
//                    " " +
//                    "GROUP BY " +
//                    "scm.BUSINESS_SYSTEM_ID,SERVER_CATALOG_ID " +
//                    "ORDER BY " +
//                    "SERVER_CATALOG_ID"+
//                    ")" +
//                    "union ALL " +
//                    "select BUSINESS_SYSTEM_ID," +
//                    "ID as SERVER_CATALOG_ID," +
//                    "0 as message," +
//                    "0 as datacount from SERVER_CATALOG sc " +
//                    "LEFT JOIN SERVER_CATALOG_DB_PUB scdp on sc.DB_ID = scdp.UUID "+
//                    "where sc.DELETED = 'N'" +
//                    "and sc.id not in ( " +
//                    "SELECT " +
//                    "distinct scm.SERVER_CATALOG_ID " +
//                    "FROM " +
//                    "  SERVER_CATALOG_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    "  scm.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "  '" + starttime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "  '" + endtime + "', " +
//                    "  'yyyy-mm-dd hh24:mi:ss' " +
//                    ")  " +
//                    ") "+("__OTHER__".equals(did)?"and scdp.DB_ID is null ":"and scdp.DB_ID = '"+did+"' ");
//        }
//
//        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
//        for (Object[] objs : tmpl) {
//            map = new LinkedHashMap<>();
//            map.put("bid", ((BigDecimal) objs[0]).intValue() + "");
//            map.put("cid", ((BigDecimal) objs[1]).intValue());
//            map.put("message", ((BigDecimal) objs[2]).intValue());
//            map.put("data", ((BigDecimal) objs[3]).intValue());
//            list.add(map);
//        }
//
//        pageInfo.setTotalResult(getSQLCount(sql));
//        return list;
//    }

    public List<Map<String, Object>> getCatalogResult(PageInfo pageInfo, String starttime, String endtime, String did) {
        String sql = "select tb1.* from (select bs.id,sc.id sid,sum(sds.CT) from SERVER_DATA_STATISTIC_MESG sds left join SERVER_CATALOG sc on sc.ID = to_number(sds.BUUID) left join BUSINESS_SYSTEM bs on bs.id = sc.BUSINESS_SYSTEM_ID " +
                "where STYPE = '"+TYPE_PCA+"' and to_date(sds.SDATE,'YYYY-MM-DD')  between to_date('"+starttime+"','YYYY-MM-DD HH24:MI:SS') and to_date('"+endtime+"','YYYY-MM-DD HH24:MI:SS') " +
                "and exists( " +
                "    select 1 from SERVER_CATALOG sc1 left join SERVER_CATALOG_DB_PUB scdp on scdp.UUID = sc1.DB_ID left join SERVER_CATALOG_DBINFO scd on scd.UUID = scdp.DB_ID " +
                "    where scd.UUID = '"+did+"' and sc1.id = sds.BUUID " +
                "          ) group by bs.id,sc.id )tb1 left join SERVER_CATALOG sc1 on tb1.sid = sc1.ID order by sc1.TEMPLATE_ID";
        Map<String,Object> map;
      /*  List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] objs : tmpl) {
            map = new LinkedHashMap<>();
            map.put("bid", ((BigDecimal) objs[0]).intValue() + "");
            map.put("cid", ((BigDecimal) objs[1]).intValue());
            map.put("message", ((BigDecimal) objs[2]).intValue());
            map.put("data", 0);
            list.add(map);
        }

        pageInfo.setTotalResult(getSQLCount(sql));
        return list;*/
        return null;
    }

    public List<Map<String, Object>> getDayCatalogResultTab(String starttime, String endtime,String cid) {
      /*  Map<String, Object> map = new LinkedHashMap<>();
        List list = new ArrayList<>();
        String sql = "select sdate,ct from SERVER_DATA_STATISTIC_Tab where stype='PCA' and buuid = '"+cid+"' and sdate >= '"+starttime.subSequence(0,10)+"' and sdate <= '"+endtime.subSequence(0,10)+"' order by sdate asc";
        List<Object[]> tmpl = findBySQL(sql);
        for (Object[] objs : tmpl) {
            map = new LinkedHashMap<>();
            map.put("msgdate", ((String) objs[0]).toString());
            map.put("msgcount", ((BigDecimal) objs[1]).intValue());
            list.add(map);
        }

        //pageInfo.setTotalResult(getSQLCount(sql));
        return list;*/
        return null;
    }
    
    public List<Map<String, Object>> getDayCatalogResult(String starttime, String endtime,String cid) {
       /* Map<String, Object> map = new LinkedHashMap<>();
        List list = new ArrayList<>();
        String sql = "select sdate,ct from SERVER_DATA_STATISTIC_MESG where stype='PCA' and buuid = '"+cid+"' and sdate >= '"+starttime.subSequence(0,10)+"' and sdate <= '"+endtime.subSequence(0,10)+"' order by sdate asc";
        List<Object[]> tmpl = findBySQL(sql);
        for (Object[] objs : tmpl) {
            map = new LinkedHashMap<>();
            map.put("msgdate", ((String) objs[0]).toString());
            map.put("msgcount", ((BigDecimal) objs[1]).intValue());
            list.add(map);
        }

        //pageInfo.setTotalResult(getSQLCount(sql));
        return list;*/
        return null;
    }
    
    
    private boolean checkTableExist(String tablename) {
       /* String hql = "SELECT COUNT(*) FROM USER_OBJECTS WHERE OBJECT_NAME = UPPER('" + tablename + "')";
        List<BigDecimal> result = findBySQL(hql);
        if (result != null && result.size() > 0) {
            BigDecimal num = result.get(0);
            return num.intValue() > 0;
        } else {
            return false;
        }*/
       return true;
    }
    
    public List<Map<String, Object>> getBussinessSubscribeResult(PageInfo pageInfo, String starttime, String endtime) {
      /*  String sql = "select bs.id,sum(sds.CT),sum(SM) from SERVER_DATA_STATISTIC_MESG sds left join BUSINESS_SYSTEM bs on bs.id = TO_NUMBER(sds.BUUID) " +
                "where STYPE = '"+TYPE_BS+"' and to_date(sds.SDATE,'YYYY-MM-DD')  between to_date('"+starttime+"','YYYY-MM-DD HH24:MI:SS') and to_date('"+endtime+"','YYYY-MM-DD HH24:MI:SS') " +
                "group by bs.id order by sum(sds.CT) desc";
        Map<String,Object> map;
        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] objs : tmpl) {
            map = new LinkedHashMap<>();
            map.put("bid", ((BigDecimal) objs[0]).intValue() + "");
            map.put("message", ((BigDecimal) objs[1]).intValue());
            map.put("data", ((BigDecimal) objs[2]).intValue());
            list.add(map);
        }
        pageInfo.setTotalResult(getSQLCount(sql));

        return list;*/
        return null;
    }
//    public List<Map<String, Object>> getBussinessSubscribeResult(PageInfo pageInfo, String starttime, String endtime) {
//        Map<String, Object> map = new LinkedHashMap<>();
//        List list = new ArrayList<>();
//        String startmonth = starttime.substring(5, 7);
//        String endmonth = endtime.substring(5, 7);
//        String tablenames = starttime.substring(0, 4) + starttime.substring(5, 7);
//        String tablenamee = endtime.substring(0, 4) + endtime.substring(5, 7);
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int month = c.get(Calendar.MONTH) + 1;
//        String curmonth;
//        if (month < 10) {
//            curmonth = "0" + month;
//        } else {
//            curmonth = month + "";
//        }
//        String sql;
//        boolean flag=true;
//        boolean flags=true;
//        boolean flage=true;
//        if(!tablenamee.equals(tablenames) && !endmonth.equals(curmonth))
//        {
//            flags = checkTableExist("SERVER_S_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//            flage = checkTableExist("SERVER_S_M_" + tablenamee) && checkTableExist("SERVER_M_D_" + tablenamee);
//        }
//        else if(!startmonth.equals(curmonth))
//        {
//            flag = checkTableExist("SERVER_S_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//        }
//        if ((startmonth.equals(endmonth) && endmonth.equals(curmonth)) || (!flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (!flag)) {
//            sql = "SELECT " +
//                    " BUSINESS_SYSTEM_ID, " +
//                    " COUNT (*), " +
//                    " SUM (MSGCOUNT) " +
//                    " " +
//                    "FROM " +
//                    " SERVER_SUBSCRIBES_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    " SCM.STATUS = 'Y' " +
//                    "and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "GROUP BY " +
//                    " BUSINESS_SYSTEM_ID order by BUSINESS_SYSTEM_ID";
//        }  else if ((flag && startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth))){
//            sql = "SELECT " +
//                    " BUSINESS_SYSTEM_ID, " +
//                    " COUNT (*), " +
//                    " SUM (MSGCOUNT) " +
//                    " " +
//                    "FROM " +
//                    " SERVER_S_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    " SCM.STATUS = 'Y' " +
//                    "and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "GROUP BY " +
//                    " BUSINESS_SYSTEM_ID order by BUSINESS_SYSTEM_ID";
//        } else if(!flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)){
//        	sql = "SELECT " +
//                    " BUSINESS_SYSTEM_ID, " +
//                    " COUNT (*), " +
//                    " SUM (MSGCOUNT) " +
//                    " " +
//                    "FROM " +
//                    " SERVER_S_M_" + tablenamee + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenamee + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    " SCM.STATUS = 'Y' " +
//                    "and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "GROUP BY " +
//                    " BUSINESS_SYSTEM_ID order by BUSINESS_SYSTEM_ID";
//        }else if(flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)){
//        	sql = "select BUSINESS_SYSTEM_ID,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "BUSINESS_SYSTEM_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_S_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "union all " +
//                    "SELECT " +
//                    "BUSINESS_SYSTEM_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_S_M_" + tablenamee + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenamee + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    " " +
//                    " " +
//                    ") " +
//                    "GROUP BY " +
//                    "BUSINESS_SYSTEM_ID " +
//                    "ORDER BY " +
//                    "BUSINESS_SYSTEM_ID ";
//        }else {
//            sql = "select BUSINESS_SYSTEM_ID,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "BUSINESS_SYSTEM_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_SUBSCRIBES_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "union all " +
//                    "SELECT " +
//                    "BUSINESS_SYSTEM_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_S_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    " " +
//                    " " +
//                    ") " +
//                    "GROUP BY " +
//                    "BUSINESS_SYSTEM_ID " +
//                    "ORDER BY " +
//                    "BUSINESS_SYSTEM_ID ";
//        }
//
//        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
//        for (Object[] objs : tmpl) {
//            map = new LinkedHashMap<>();
//            map.put("bid", ((BigDecimal) objs[0]).intValue() + "");
//            map.put("message", ((BigDecimal) objs[1]).intValue());
//            map.put("data", ((BigDecimal) objs[2]).intValue());
//            list.add(map);
//        }
//        pageInfo.setTotalResult(getSQLCount(sql));
//
//        return list;
//    }

    public List<Map<String, Object>> getSubscribeResult(PageInfo pageInfo, String starttime, String endtime,int bid) {
       /* String sql = "select bs.id,sc.id sid,sum(sds.CT),sum(sds.SM) from SERVER_DATA_STATISTIC_MESG sds left join SERVER_SUBSCRIBES ss on ss.ID = sds.BUUID left join SERVER_CATALOG sc on sc.id = ss.SERVER_CATALOG_ID " +
                "left join BUSINESS_SYSTEM bs on bs.ID = ss.BUSINESS_SYSTEM_ID " +
                "where STYPE = '"+TYPE_SCA+"' and to_date(sds.SDATE,'YYYY-MM-DD')  between to_date('"+starttime+"','YYYY-MM-DD HH24:MI:SS') and to_date('"+endtime+"','YYYY-MM-DD HH24:MI:SS') " +
                "and exists( " +
                "    select 1 from SERVER_SUBSCRIBES ss where ss.BUSINESS_SYSTEM_ID = '"+bid+"' and ss.ID =sds.BUUID " +
                "          ) group by bs.id,sc.id order by sum(sds.CT) desc";
        Map<String,Object> map;
        execUpdateSQL(sql);
        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] objs : tmpl) {
            map = new LinkedHashMap<>();
            map.put("bid", ((BigDecimal) objs[0]).intValue() + "");
            map.put("cid", ((BigDecimal) objs[1]).intValue());
            map.put("message", ((BigDecimal) objs[2]).intValue());
            map.put("data", ((BigDecimal) objs[3]).intValue());
            list.add(map);
        }
        pageInfo.setTotalResult(getSQLCount(sql));

        return list;*/
        return null;
    }
//    public List<Map<String, Object>> getSubscribeResult(PageInfo pageInfo, String starttime, String endtime,int bid) {
//        Map<String, Object> map = new LinkedHashMap<>();
//        List list = new ArrayList<>();
//        String startmonth = starttime.substring(5, 7);
//        String endmonth = endtime.substring(5, 7);
//        String tablenames = starttime.substring(0, 4) + starttime.substring(5, 7);
//        String tablenamee = endtime.substring(0, 4) + endtime.substring(5, 7);
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int month = c.get(Calendar.MONTH) + 1;
//        String curmonth;
//        if (month < 10) {
//            curmonth = "0" + month;
//        } else {
//            curmonth = month + "";
//        }
//        String sql;
//        boolean flag=true;
//        boolean flags=true;
//        boolean flage=true;
//        if(!tablenamee.equals(tablenames) && !endmonth.equals(curmonth))
//        {
//            flags = checkTableExist("SERVER_S_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//            flage = checkTableExist("SERVER_S_M_" + tablenamee) && checkTableExist("SERVER_M_D_" + tablenamee);
//        }
//        else if(!startmonth.equals(curmonth))
//        {
//            flag = checkTableExist("SERVER_S_M_" + tablenames) && checkTableExist("SERVER_M_D_" + tablenames);
//        }
//        if ((startmonth.equals(endmonth) && endmonth.equals(curmonth)) || (!flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (!flag)) {
//            sql = "SELECT " +
//                    " BUSINESS_SYSTEM_ID, " +
//                    " SERVER_SUBSCRIBES_ID, " +
//                    " COUNT (*), " +
//                    " SUM (MSGCOUNT) " +
//                    " " +
//                    "FROM " +
//                    " SERVER_SUBSCRIBES_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    " SCM.STATUS = 'Y' " +
//                    "and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and BUSINESS_SYSTEM_ID = "+bid+" "+
//                    "GROUP BY " +
//                    " BUSINESS_SYSTEM_ID,SERVER_SUBSCRIBES_ID order by SERVER_SUBSCRIBES_ID";
//        }  else if ((flag && startmonth.equals(endmonth) && !endmonth.equals(curmonth)) || (flags && !flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth))){
//            sql = "SELECT " +
//                    " BUSINESS_SYSTEM_ID, " +
//                    " SERVER_SUBSCRIBES_ID, " +
//                    " COUNT (*), " +
//                    " SUM (MSGCOUNT) " +
//                    " " +
//                    "FROM " +
//                    " SERVER_S_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    " SCM.STATUS = 'Y' " +
//                    "and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and BUSINESS_SYSTEM_ID = "+bid+" "+
//                    "GROUP BY " +
//                    " BUSINESS_SYSTEM_ID,SERVER_SUBSCRIBES_ID order by SERVER_SUBSCRIBES_ID";
//        }   else if(!flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)){
//            sql = "SELECT " +
//                    " BUSINESS_SYSTEM_ID, " +
//                    " SERVER_SUBSCRIBES_ID, " +
//                    " COUNT (*), " +
//                    " SUM (MSGCOUNT) " +
//                    " " +
//                    "FROM " +
//                    " SERVER_S_M_" + tablenamee + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenamee + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd.ID " +
//                    "WHERE " +
//                    " SCM.STATUS = 'Y' " +
//                    "and TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') " +
//                    "BETWEEN TO_DATE('" + starttime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and TO_DATE('" + endtime + "','yyyy-mm-dd hh24:mi:ss' ) " +
//                    "and BUSINESS_SYSTEM_ID = "+bid+" "+
//                    "GROUP BY " +
//                    " BUSINESS_SYSTEM_ID,SERVER_SUBSCRIBES_ID order by SERVER_SUBSCRIBES_ID";
//        } else if(flags && flage && !startmonth.equals(endmonth) && !endmonth.equals(curmonth)){
//        	sql = "select BUSINESS_SYSTEM_ID,SERVER_SUBSCRIBES_ID,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "BUSINESS_SYSTEM_ID, " +
//                    "SERVER_SUBSCRIBES_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_S_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +"and BUSINESS_SYSTEM_ID = "+bid+" "+
//                    "union all " +
//                    "SELECT " +
//                    "BUSINESS_SYSTEM_ID, " +
//                    "SERVER_SUBSCRIBES_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_S_M_" + tablenamee + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenamee + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +"and BUSINESS_SYSTEM_ID = "+bid+" "+
//                    " " +
//                    " " +
//                    ") " +
//                    "GROUP BY " +
//                    "BUSINESS_SYSTEM_ID,SERVER_SUBSCRIBES_ID " +
//                    "ORDER BY " +
//                    "SERVER_SUBSCRIBES_ID ";
//        }else {
//            sql = "select BUSINESS_SYSTEM_ID,SERVER_SUBSCRIBES_ID,count(*),sum(MSGCOUNT) from ( " +
//                    "SELECT " +
//                    "BUSINESS_SYSTEM_ID, " +
//                    "SERVER_SUBSCRIBES_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_SUBSCRIBES_MESSAGE scm " +
//                    "LEFT JOIN SERVER_MESSAGE_DETAIL smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +"and BUSINESS_SYSTEM_ID = "+bid+" "+
//                    "union all " +
//                    "SELECT " +
//                    "BUSINESS_SYSTEM_ID, " +
//                    "SERVER_SUBSCRIBES_ID, " +
//                    "MSGCOUNT " +
//                    "FROM " +
//                    "SERVER_S_M_" + tablenames + " scm " +
//                    "LEFT JOIN SERVER_M_D_" + tablenames + " smd ON scm.SERVER_MESSAGE_DETAIL_ID = smd. ID " +
//                    "WHERE " +
//                    "SCM.STATUS = 'Y' " +
//                    "AND TO_DATE(smd.MSGSENTTIME, 'yyyymmddhh24miss') BETWEEN TO_DATE ( " +
//                    "'" + starttime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +
//                    "AND TO_DATE ( " +
//                    "'" + endtime + "', " +
//                    "'yyyy-mm-dd hh24:mi:ss' " +
//                    ") " +"and BUSINESS_SYSTEM_ID = "+bid+" "+
//                    " " +
//                    " " +
//                    ") " +
//                    "GROUP BY " +
//                    "BUSINESS_SYSTEM_ID,SERVER_SUBSCRIBES_ID " +
//                    "ORDER BY " +
//                    "SERVER_SUBSCRIBES_ID ";
//        }
//
//        List<Object[]> tmpl = findPageBySQL(sql, pageInfo);
//        for (Object[] objs : tmpl) {
//            map = new LinkedHashMap<>();
//            map.put("bid", ((BigDecimal) objs[0]).intValue() + "");
//            map.put("cid", ((BigDecimal) objs[1]).intValue());
//            map.put("message", ((BigDecimal) objs[2]).intValue());
//            map.put("data", ((BigDecimal) objs[3]).intValue());
//            list.add(map);
//        }
//        pageInfo.setTotalResult(getSQLCount(sql));
//
//        return list;
//    }
}
