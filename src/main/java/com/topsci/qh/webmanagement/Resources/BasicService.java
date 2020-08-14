package com.topsci.qh.webmanagement.Resources;

import com.topsci.qh.webmanagement.Tools.MD5Tool;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * Created by lzw.
 * 16-6-21
 */
public class BasicService  {

    protected final String DELETED = "Y";
    protected final String UNDELETED ="N";
    protected SimpleDateFormat StardTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    protected MD5Tool md5Tool;
    /*@Resource
    protected SessionFactory sessionFactory;
    //@Resource
    protected SessionFactory sessionFactoryMysql;
    @Resource
    protected SessionFactory sessionFactoryBase;


    @Resource
    protected OprRecord oprRecord;
    @Resource
    protected SessionManager sessionManager;
    @Resource
    protected Cache cache;



//    @Autowired
//    public void setMySessionFactory(SessionFactory sessionFactory){
//        super.setSessionFactory(sessionFactory);
//    }

    protected void evictObj(Object obj)
    {
        sessionFactory.getCurrentSession().evict(obj);
    }

    protected void save(Object obj)
    {
        Session session = sessionFactory.getCurrentSession();
        session.save(obj);
    }

    protected void saveOrUpdateBase(Object obj)
    {
        Session session = sessionFactoryBase.getCurrentSession();
        session.saveOrUpdate(obj);
    }

    protected void update(Object obj)
    {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    protected void delete(Object obj)
    {
        Session session = sessionFactory.getCurrentSession();
        session.delete(obj);
    }

    protected <T> T get(Class<T> cls,Serializable id)
    {
        Session session = sessionFactory.getCurrentSession();
        return (T)session.get(cls,id);
    }


    protected <T> T baseGet(Class<T> cls,Serializable id)
    {
        Session session = sessionFactoryBase.getCurrentSession();
        return (T)session.get(cls,id);
    }

    *//**
     * 查找全部
     * @param hql
     * @param values
     * @return
     *//*
    protected List findByHQL(String hql, Object... values)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        int i = 0 ;
        if(values!=null) {
            for (Object value : values) {
                query.setParameter(i,value);
                i++;
            }
        }
        return query.list();
    }

    *//**
     * 查找全部
     * @param hql
     * @param values
     * @return
     *//*
    protected List findByHQLBase(String hql, Object... values)
    {
        Session session = sessionFactoryBase.getCurrentSession();
        Query query = session.createQuery(hql);
        int i = 0 ;
        if(values!=null) {
            for (Object value : values) {
                query.setParameter(i,value);
                i++;
            }
        }
        return query.list();
    }

    *//**
     * 按分页方式查找
     * @param hql
     * @param start 起始位置
     * @param max  长度
     * @param values
     * @return
     *//*
    protected List findPageByHQL(String hql,int start,int max, Object... values)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        if(values!=null)
        {
            int i = 0;
            for(Object value : values)
            {
                query.setParameter(i,value);
                i++;
            }
        }
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.list();
    }

    *//**
     * 按分页方式查找(默认使用公共定义的页面长度)
     * @param hql
     * @param pageInfo 页面信息
     * @param values
     * @return
     *//*
    protected List findPageByHQL(String hql,PageInfo pageInfo, Object... values)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        if(values!=null)
        {
            int i = 0;
            for(Object value : values)
            {
                query.setParameter(i,value);
                i++;
            }
        }
        query.setFirstResult(pageInfo.getCurrentPage()*pageInfo.getPageSize());
        query.setMaxResults(pageInfo.getPageSize());
        return query.list();
    }

    *//**
     * 按分页方式查找(默认使用公共定义的页面长度)
     * @param hql
     * @param pageInfo 页面信息
     * @param values
     * @return
     *//*
    protected List findPageByHQLBase(String hql,PageInfo pageInfo, Object... values)
    {
        Session session = sessionFactoryBase.getCurrentSession();
        Query query = session.createQuery(hql);
        if(values!=null)
        {
            int i = 0;
            for(Object value : values)
            {
                query.setParameter(i,value);
                i++;
            }
        }
        query.setFirstResult(pageInfo.getCurrentPage()*pageInfo.getPageSize());
        query.setMaxResults(pageInfo.getPageSize());
        return query.list();
    }

    *//**
     * 运行update语句
     * @param hql
     * @param values
     * @return
     *//*
    protected int execUpdateHQL(final String hql, final Object... values)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        int i = 0;
        for (Object value : values) {
            query.setParameter(i, value);
            i++;
        }
        return query.executeUpdate();

    }

    *//**
     * 运行update语句
     * @param hql
     * @param values
     * @return
     *//*
    protected int execUpdateHQLBase(final String hql, final Object... values)
    {
        Session session = sessionFactoryBase.getCurrentSession();
        Query query = session.createQuery(hql);
        int i = 0;
        for (Object value : values) {
            query.setParameter(i, value);
            i++;
        }
        return query.executeUpdate();

    }

    *//**
     * 运行update语句
     * @param hql
     * @param values
     * @return
     *//*
    protected int execUpdateSQL(final String hql, final Object... values)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql);
        int i = 0;
        for (Object value : values) {
            query.setParameter(i, value);
            i++;
        }
        return query.executeUpdate();

    }


    *//**
     * 计算查询结果数
     * @param hql
     * @param values
     * @return
     *//*
    protected int getCount(final String hql,final Object... values)
    {
        String tmphql = "select count(*) "+hql;
        List result = findByHQL(tmphql,values);
        if(result != null && result.size()>0)
        {
            return (int)((long)result.get(0));
        }
        else
        {
            return 0;
        }
    }

    *//**
     * 计算查询结果数
     * @param hql
     * @param values
     * @return
     *//*
    protected int getCountBase(final String hql,final Object... values)
    {
        String tmphql = "select count(*) "+hql;
        List result = findByHQLBase(tmphql,values);
        if(result != null && result.size()>0)
        {
            return (int)((long)result.get(0));
        }
        else
        {
            return 0;
        }
    }

    protected int getCountCustom(final String fullhql,final Object... values)
    {
        List result = findByHQL(fullhql,values);
        if(result != null && result.size()>0)
        {
            return (int)((long)result.get(0));
        }
        else
        {
            return 0;
        }
    }

    protected void batchSaveOrUpdateList(List list)
    {
        Session session = sessionFactory.getCurrentSession();
        for(Object obj:list)
        {
            session.evict(obj);
            session.saveOrUpdate(obj);
        }
    }

    protected List findBySQL(String sql, Object... values)
    {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createSQLQuery(sql);
        int i = 0;
        for(Object val : values)
        {
            q.setParameter(i,val);
            i++;
        }
        return q.list();
    }

    protected List findPageBySQL(String sql,PageInfo pageInfo,Object... values)
    {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createSQLQuery(sql);
        int i = 0;
        for(Object val : values)
        {
            q.setParameter(i,val);
            i++;
        }
        q.setFirstResult(pageInfo.getCurrentPage()*pageInfo.getPageSize());
        q.setMaxResults(pageInfo.getPageSize());
        return q.list();
    }

    protected int getSQLCount(final String sql,final Object... values)
    {
        String tmphql = "select count(*) from ( "+sql+" )";
        List result = findBySQL(tmphql, values);
        if(result != null && result.size()>0)
        {
            return ((BigDecimal)result.get(0)).intValue();
        }
        else
        {
            return 0;
        }
    }

    protected List findBySQLMysql(String sql, Object... values)
    {
        Session session = sessionFactoryMysql.getCurrentSession();
        Query q = session.createSQLQuery(sql);
        int i = 0;
        for(Object val : values)
        {
            q.setParameter(i,val);
            i++;
        }
        return q.list();
    }

    public String[] getStatisticDateMonth(String starttime,String endtime)
    {
        if(StringUtils.isEmpty(starttime))
        {
        	Calendar c = Calendar.getInstance();
        	c.setTime(new Date());
            c.add(Calendar.MONTH, -2);
            int month = (int)(c.get(Calendar.MONTH)+1);
        	starttime = c.get(Calendar.YEAR)+"-"+(month>9?month:"0"+month);
        }
        if(StringUtils.isEmpty(endtime))
        {
        	Calendar c = Calendar.getInstance();
        	c.setTime(new Date());
        	c.add(Calendar.MONTH, -1);
        	int month = (int)(c.get(Calendar.MONTH)+1);
            endtime = c.get(Calendar.YEAR)+"-"+(month>9?month:"0"+month);
        }

        return new String[]{starttime,endtime};
    }

    public String[] getStatisticDateYear(String starttime,String endtime)
    {
        if(StringUtils.isEmpty(starttime))
        {
            starttime = ((int)cache.getAttr(Cache.DATE_YEAR_YYYY)-1)+"";
        }
        if(StringUtils.isEmpty(endtime))
        {
            endtime = cache.getAttr(Cache.DATE_YEAR_YYYY)+"";
        }

        return new String[]{starttime,endtime};
    }

    public String generateYearFrom1980ToNowOption()
    {
        return generateYearFrom1980ToNowOption(-1);
    }
    public String generateYearFrom1980ToNowOption(int select)
    {
        int start = 1980;
        Calendar c = Calendar.getInstance();
        int end = c.get(Calendar.YEAR);
        String options = "";
        if(select == -1)
        {
            select = end;
        }
        for(int i = end ; i>= start ;i--)
        {
            if(i == select) {
                options += "<option value='" + i + "' selected>" + i + "</option>";
            }
            else
            {
                options += "<option value='" + i + "'>" + i + "</option>";
            }
        }
        return options;
    }*/
}
