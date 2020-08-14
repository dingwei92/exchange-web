package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.ServerCatalogDBACPub;
import com.topsci.qh.webmanagement.Pojo.ServerCatalogDBinfo;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import com.topsci.qh.webmanagement.Tools.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Service
public class DatabasePublishService extends BasicService {
    private static Logger logger = LoggerFactory.getLogger(DatabasePublishService.class);
    public final static String ORACLE="1";
    public final static String SQLSERVER="2";
    public final static String MYSQL="3";
    public final static String ORACLE_2="4";
    private final static String ORACLE_URL = "jdbc:oracle:thin:@___IP:___PORT:___DBNAME";
    private final static String ORACLE_2_URL = "jdbc:oracle:thin:@___IP:___PORT/___DBNAME";
    private final static String SQLSERVER_URL= "jdbc:sqlserver://___IP:___PORT; DatabaseName=___DBNAME";
    private final static String MYSQL_URL="jdbc:mysql://___IP:___PORT/___DBNAME?useUnicode=true&amp;characterEncoding=utf8&amp;autoReconnect=true&amp;rewriteBatchedStatements=TRUE";
    private final static String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private final static String SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String ORACLE_TABLES="select concat(TABLE_NAME,' (TABLE)') from user_tables UNION select concat(view_name,' (VIEW)') from user_views";
    private final static String MYSQL_TABLES="select concat(table_name,' (TABLE)') from information_schema.tables where table_schema='___DBNAME' union select concat(table_name,' (VIEW)') from information_schema.views where table_schema='___DBNAME'";
    private final static String SQLSERVER_TABLES="select concat(name,' (TABLE)') from sys.tables where type='u' union select concat(name,' (VIEW)') from sys.views where type='v'";
    private final static String ORACLE_COLS="select COLUMN_NAME,DATA_TYPE from USER_TAB_COLS where TABLE_NAME = '___TABLENAME'";
    private final static String MYSQL_COLS="select COLUMN_NAME,DATA_TYPE from information_schema.columns where table_name='___TABLENAME'";
    private final static String SQLSERVER_COLS="select COLUMN_NAME,DATA_TYPE from information_schema.columns where table_name='___TABLENAME'";

    private static String DEALT = "D";
    private static String UNDEALT = "N";

    private Map<String,String> dbtypes;

    public DatabasePublishService()
    {
        dbtypes = new HashMap<String,String>(){{
            put(ORACLE,"ORACLE(11) SID");
            put(SQLSERVER,"SQLSERVER(2008及以上)");
            put(MYSQL,"MYSQL5(5.5及以上)");
            put(ORACLE_2,"ORACLE(11) 服务名");
        }};
    }

    /**
     * 验证数据库是否可用
     * @param dbtype
     * @param dbip
     * @param dbname
     * @param username
     * @param password
     * @return
     */
    public boolean validateDB(String dbtype,String dbip,String dbport,String dbname,String username,String password)
    {
       /* DBServiceMulti multi = createDBService(dbtype,dbip,dbport,dbname,username,password);
        try
        {
            multi.openConnection();
            String validatesql = "select 1 "+(ORACLE.equals(dbtype)||ORACLE_2.equals(dbtype)?"from dual":"");
            multi.getString(validatesql);
            multi.close();
            return true;
        }
        catch (Exception ex)
        {
            logger.warn("尝试连接数据库失败："+dbip+":"+dbport+"/"+dbname,ex);
            return false;
            return false;
        }*/
       return true;
    }

    public boolean validateDB(String dbuuid)
    {
        ServerCatalogDBinfo dBinfo = getDBInfo(dbuuid);
        return validateDB(dBinfo.getDbtype(),dBinfo.getDbip(),dBinfo.getDbport(),dBinfo.getDbname(),dBinfo.getUsername(),
                dBinfo.getPwd());
    }


    public Map<String,String> getTables(String dbuuid)
    {
        ServerCatalogDBinfo dBinfo = getDBInfo(dbuuid);
        return getTables(dBinfo.getDbtype(),dBinfo.getDbip(),dBinfo.getDbport(),dBinfo.getDbname(),dBinfo.getUsername(),
                dBinfo.getPwd());
    }

    /**
     * 获取用户名下的数据表和视图名称
     * @param dbtype
     * @param dbip
     * @param dbport
     * @param dbname
     * @param username
     * @param password
     * @return
     */
    public Map<String,String> getTables(String dbtype,String dbip,String dbport,String dbname,String username,String password)
    {
        /*List<String> tables = new ArrayList<>();
        Map<String,String> map = new TreeMap<>();
        DBServiceMulti multi = createDBService(dbtype,dbip,dbport,dbname,username,password);
        String sql="";
        switch (dbtype)
        {
            case ORACLE : case ORACLE_2: sql=ORACLE_TABLES;break;
            case SQLSERVER:sql=SQLSERVER_TABLES;break;
            case MYSQL:sql=MYSQL_TABLES.replaceAll("___DBNAME",dbname);break;
        }

        try
        {
            tables = multi.getStringList(sql);
            for(String s : tables)
            {
                map.put(s,s.replaceAll(" \\(TABLE\\)",""));
            }
        }
        catch (Exception ex)
        {
            logger.warn("获取数据表失败："+dbip+":"+dbport+"/"+dbname);
        }*/
        return null;
    }

    public Map<String,String> getColumns(String dbuuid,String sql,String tblname,String tplid)
    {
        ServerCatalogDBinfo dBinfo = getDBInfo(dbuuid);
        return getColumns(dBinfo.getDbtype(),dBinfo.getDbip(),dBinfo.getDbport(),dBinfo.getDbname(),dBinfo.getUsername(),
                dBinfo.getPwd(),sql,tblname,tplid);
    }

    /**
     * 根据sql查询语句或者数据表名称获取表的字段
     * @param dbtype
     * @param dbip
     * @param dbport
     * @param dbname
     * @param username
     * @param password
     * @param sql
     * @param tblname
     * @return
     */
    public Map<String,String> getColumns(String dbtype,String dbip,String dbport,
                                         String dbname,String username,String password,
                                         String sql,String tblname,String tplid)
    {
        /*Map<String,String> map = new TreeMap<>();
        if(StringUtils.isNotEmpty(sql))
        {
            try {
                int index = sql.indexOf("select");
                String tmp = sql.substring(index + 6);
                index = tmp.indexOf("from");
                tmp = tmp.substring(0, index).trim();
                List<String> cols = Arrays.asList(tmp.split(","));
                for (String col : cols) {
                    map.put(col, col);
                }
            }
            catch (Exception ex)
            {
                logger.warn("解析sql语句错误 ："+sql);
            }
        }
        else
        {
            DBServiceMulti multi = createDBService(dbtype,dbip,dbport,dbname,username,password);
            String csql="";
            switch (dbtype)
            {
                case ORACLE: case ORACLE_2: csql=ORACLE_COLS;break;
                case SQLSERVER:csql=SQLSERVER_COLS;break;
                case MYSQL:csql=MYSQL_COLS;break;
            }
            try
            {
                List<String[]> tables = multi.getListArray(csql.replaceAll("___TABLENAME",tblname));
                for(String[] s : tables)
                {
                    map.put(s[0],s[0]+"  ("+s[1]+")");
                }
            }
            catch (Exception ex)
            {
                logger.warn("获取数据表失败："+dbip+":"+dbport+"/"+dbname);
            }
        }*/
        return null;

    }

   /* private DBServiceMulti createDBService(String dbtype,String dbip,String dbport,String dbname,String username,String password)
    {
        Properties p = new Properties();
        String url="";
        String driver="";
        switch (dbtype){
            case ORACLE:url = ORACLE_URL;driver=ORACLE_DRIVER;break;
            case ORACLE_2:url = ORACLE_2_URL;driver=ORACLE_DRIVER;break;
            case SQLSERVER:url = SQLSERVER_URL;driver=SQLSERVER_DRIVER;break;
            case MYSQL:url = MYSQL_URL;driver=MYSQL_DRIVER;break;
        }
        url = url.replaceAll("___IP",dbip).replaceAll("___PORT",dbport).replaceAll("___DBNAME",dbname);

        p.setProperty(DBServiceMulti.KEY_DB_CONNECTIONURL,url);
        p.setProperty(DBServiceMulti.KEY_DB_DRIVER,driver);
        p.setProperty(DBServiceMulti.KEY_DB_USERNAME,username);
        p.setProperty(DBServiceMulti.KEY_DB_PASSWORD,password);
        p.setProperty(DBServiceMulti.KEY_POOL_DESTORYONDATASOURCEEXPIRE,"true");
        p.setProperty(DBServiceMulti.KEY_POOL_DATASOURCEEXPIREHOURS,"1");
        DBServiceMulti multi = new DBServiceMulti(p,false);
        return multi;
    }*/

    public Map<String, String> getDbtypes() {
        return dbtypes;
    }

    public List<ServerCatalogDBinfo> getDBInfoByPage(PageInfo pageInfo, String search)
    {
        /*String hql = "from ServerCatalogDBinfo db where db.status != ? ";
        if(StringUtils.isNotEmpty(search))
        {
            hql += " and db.dbip like '%"+search+"%'";
        }
        hql += " order by db.sourcename";
        pageInfo.setTotalResult(getCount(hql,DELETED));
        return findPageByHQL(hql,pageInfo,DELETED);*/
        return null;
    }

    public List<ServerCatalogDBinfo> getDBInfoByTemplateTypePage(PageInfo pageInfo,String typeuuid)
    {
       /* String hql = "from ServerCatalogDBinfo db where db.status != ? and db.uuid in " +
                "(select scdp.dbId from ServerCatalog sc,ServerCatalogDbPub scdp,ServerCatalogTemplate sct " +
                " where sct.typeuuid = ? and sct.id = sc.templateId and scdp.uuid = sc.dbId)";
        pageInfo.setTotalResult(getCount(hql,DELETED,typeuuid));
        return findPageByHQL(hql,pageInfo,DELETED,typeuuid);*/
        return null;
    }

    public List<ServerCatalogDBinfo> getDBInfos()
    {
        /*String hql = "from ServerCatalogDBinfo db where db.status != ? ";
        return findByHQL(hql,DELETED);*/
        return null;
    }

    public ServerCatalogDBinfo getDBInfo(String uuid)
    {
       /* ServerCatalogDBinfo info = get(ServerCatalogDBinfo.class,uuid);
        if(info != null) {
            evictObj(info);
            info.setPwd(EncryptTool.decrypt(info.getPwd()));
        }
        return info;*/
        return null;
    }

    /**
     * 更新数据源
     * @param template
     * @param request
     */
    public void updateDBInfo(ServerCatalogDBinfo template,HttpServletRequest request)
    {
       /* template.setPwd(EncryptTool.encrypt(template.getPwd()));
        ServerCatalogDBinfo sct = getDBInfo(template.getUuid());
        evictObj(sct);
        update(template);
        oprRecord.logOpr(template, sct, "修改数据源", request);*/
    }

    /**
     * 更新数据源
     * @param template
     * @param request
     */
    public void saveDBInfo(ServerCatalogDBinfo template,HttpServletRequest request)
    {
        /*template.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
        template.setCreateTime(new Timestamp(new Date().getTime()));
        template.setStatus(UNDELETED);
        template.setPwd(EncryptTool.encrypt(template.getPwd()));
        save(template);
        oprRecord.logOpr(template, null, "新建数据源", request);*/
    }

    /**
     * 删除数据源
     * @param id
     * @param request
     */
    public void deleteDBInfo(String id, HttpServletRequest request) {
       /* ServerCatalogDBinfo tmp = getDBInfo(id);
        evictObj(tmp);
        execUpdateHQL("update ServerCatalogDBinfo sc set sc.status = ? where sc.uuid = ?", DELETED, id);
        oprRecord.logOpr(null, tmp, "删除接口模板" + id, request);*/
    }

    /**
     * 检查是否有接口在使用数据源
     * @param id  模板ID
     * @return
     */
    public String checkDBInfoUse(String id)
    {
       /* String hql = "from ServerCatalog sc where sc.dbId = ?";
        int r = getCount(hql,id);
        JSONObject obj = new JSONObject();
        if(r>0)
        {
            obj.put("result",1);
        }
        else
        {
            obj.put("result",0);
        }
        return obj.toJSONString();*/
        return null;
    }

    public String checkDBInfoRepeated(String uuid, String sourcename) {
        if (StringUtils.isEmpty(uuid)) {
            uuid = "0";
        }
        if (isTemplateNameRepeated(uuid, sourcename)) {
            return "{\"result\":\"1\"}";
        }
        return "{\"result\":\"0\"}";
    }

    public boolean isTemplateNameRepeated(String uuid, String sourcename) {
     /*   String hql;
        int result;
        if (StringUtils.isNotEmpty(uuid)) {
            hql = "from ServerCatalogDBinfo sc where sc.status != ? and sc.sourcename = ? and sc.uuid != ? ";
            result = getCount(hql, DELETED, uuid,sourcename);
        } else {
            hql = "from ServerCatalogDBinfo sc where sc.status != ? and sc.sourcename = ? ";
            result = getCount(hql, DELETED, sourcename);
        }
        return result > 0;*/
        return true;
    }

    public void activepubsave(String clid,String startTime,String endTime,HttpServletRequest request) throws Exception
    {
        ServerCatalogDBACPub acpub = new ServerCatalogDBACPub();
        acpub.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        acpub.setCreatetime(new Timestamp(new Date().getTime()));
        acpub.setCatalogid(clid);
        acpub.setStatus(UNDEALT);
        if(StringUtils.isNotEmpty(startTime)) {
            acpub.setStarttime(new Timestamp(DateUtil.parseDate(startTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime()));
        }
        if(StringUtils.isNotEmpty(endTime)) {
            acpub.setEndtime(new Timestamp(DateUtil.parseDate(endTime+" 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime()));
        }
        /*save(acpub);
        oprRecord.logOpr(acpub,null,"新建手动触发接口"+clid+"任务",request);*/
    }
}
