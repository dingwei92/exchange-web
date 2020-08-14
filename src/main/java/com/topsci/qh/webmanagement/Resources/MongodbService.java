package com.topsci.qh.webmanagement.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
/**
 * Created by zlli on 2020/6/8.
 * Desc:
 * _        _     _
 * | |      | |   | |
 * ___| |_ __ _| |__ | | ___
 * / __| __/ _` | '_ \| |/ _ \
 * \__ \ || (_| | |_) | |  __/
 * |___/\__\__,_|_.__/|_|\___|
 */

/**
 * mongodb curd 工具类
 *
 * @author starry
 *
 */
@Component
public class MongodbService {
    @Autowired
    private MongoTemplate mongoTemplate;


    public Object  aggregate(Aggregation var1, String ss, Class var2) {
        return this.mongoTemplate.aggregate(var1,ss,var2).getMappedResults();
    }

    /**
     * 保存数据对象，集合为数据对象中@Document 注解所配置的collection
     *
     * @param obj
     *            数据对象
     */
    public void save(Object obj) {

        this.mongoTemplate.save(obj);
    }

    /**
     * 指定集合保存数据对象
     *
     * @param obj
     *            数据对象
     * @param collectionName
     *            集合名
     */
    public void save(Object obj, String collectionName) {

        this.mongoTemplate.save(obj, collectionName);
    }

    /**
     * 根据数据对象中的id删除数据，集合为数据对象中@Document 注解所配置的collection
     *
     * @param obj
     *            数据对象
     */
    public void remove(Object obj) {

        this.mongoTemplate.remove(obj);
    }

    /**
     * 指定集合 根据数据对象中的id删除数据
     *
     * @param obj
     *            数据对象
     * @param collectionName
     *            集合名
     */
    public void remove(Object obj, String collectionName) {

        this.mongoTemplate.remove(obj, collectionName);
    }

    /**
     * 根据key，value到指定集合删除数据
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param collectionName
     *            集合名
     */
    public void remove(String key, Object value, String collectionName) {
        Criteria criteria = Criteria.where(key).is(value);
        Query query = Query.query(criteria);
        this.mongoTemplate.remove(query, collectionName);
    }

    /**
     * 根据key，value到指定集合删除数据
     *
     * @param c
     * @param criteria
     *            集合名
     */
    public void remove(Class c,Criteria criteria) {
        Query query = Query.query(criteria);
        this.mongoTemplate.findAllAndRemove(query, c);
    }

    /**
     * 指定集合 修改数据，且仅修改找到的第一条数据
     *
     * @param accordingKey
     *            修改条件 key
     * @param accordingValue
     *            修改条件 value
     * @param updateKeys
     *            修改内容 key数组
     * @param updateValues
     *            修改内容 value数组
     * @param collectionName
     *            集合名
     */
    public void updateFirst(String accordingKey, Object accordingValue, String[] updateKeys, Object[] updateValues,
                                   String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Query query = Query.query(criteria);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
        this.mongoTemplate.updateFirst(query, update, collectionName);
    }
    public void updateFirst(String accordingKey, Object accordingValue, String[] updateKeys, Object[] updateValues,
                                   Class collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Query query = Query.query(criteria);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
        this.mongoTemplate.updateFirst(query, update, collectionName);
    }
    /**
     * 指定集合 修改数据，且修改所找到的所有数据
     *
     * @param accordingKey
     *            修改条件 key
     * @param accordingValue
     *            修改条件 value
     * @param updateKeys
     *            修改内容 key数组
     * @param updateValues
     *            修改内容 value数组
     * @param collectionName
     *            集合名
     */
    public void updateMulti(String accordingKey, Object accordingValue, String[] updateKeys, Object[] updateValues,
                                   String collectionName) {

        Criteria criteria = Criteria.where(accordingKey).is(accordingValue);
        Query query = Query.query(criteria);
        Update update = new Update();
        for (int i = 0; i < updateKeys.length; i++) {
            update.set(updateKeys[i], updateValues[i]);
        }
        this.mongoTemplate.updateMulti(query, update, collectionName);
    }

    /**
     * 根据条件查询出所有结果集 集合为数据对象中@Document 注解所配置的collection
     *
     * @param obj
     *            数据对象
     * @param findKeys
     *            查询条件 key
     * @param findValues
     *            查询条件 value
     * @return
     */
    public List<? extends Object> find(Class obj, String[] findKeys, Object[] findValues) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        List<? extends Object> resultList = this.mongoTemplate.find(query, obj);
        return resultList;
    }

    /**
     * 指定集合 根据条件查询出所有结果集
     *
     * @param obj
     *            数据对象
     * @param findKeys
     *            查询条件 key
     * @param findValues
     *            查询条件 value
     * @param collectionName
     *            集合名
     * @return
     */
    public List<? extends Object> find(Object obj, String[] findKeys, Object[] findValues, String collectionName) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        List<? extends Object> resultList = this.mongoTemplate.find(query, obj.getClass(), collectionName);
        return resultList;
    }

    /**
     * 指定集合 根据条件查询出所有结果集 并排倒序
     *
     * @param obj
     *            数据对象
     * @param findKeys
     *            查询条件 key
     * @param findValues
     *            查询条件 value
     * @param collectionName
     *            集合名
     * @param sort
     *            排序字段
     * @return
     */
    public List<? extends Object> find(Object obj, String[] findKeys, Object[] findValues, String collectionName ,String sort) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        query.with(new Sort(Direction.DESC, sort));
        List<? extends Object> resultList = this.mongoTemplate.find(query, obj.getClass(), collectionName);
        return resultList;
    }

    public List<? extends Object> find(Class obj, Criteria criteria) {
        Query query = Query.query(criteria);
        List<? extends Object> resultList = this.mongoTemplate.find(query, obj);
        return resultList;
    }

    public List<? extends Object> find(Class obj, Criteria criteria,Direction direction,String sortColunm) {
        Query query = Query.query(criteria);
        for(String sc : sortColunm.split(",")) {
            query.with(new Sort(direction, sc));
        }
        List<? extends Object> resultList = this.mongoTemplate.find(query, obj);
        return resultList;
    }

    /**
     * 根据条件查询出符合的第一条数据 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param obj
     *            数据对象
     * @param findKeys
     *            查询条件 key
     * @param findValues
     *            查询条件 value
     * @return
     */
    public Object findOne(Class obj, String[] findKeys, Object[] findValues) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        Object resultObj = this.mongoTemplate.findOne(query, obj);
        return resultObj;
    }

    /**
     * 指定集合 根据条件查询出符合的第一条数据
     *
     * @param obj
     *            数据对象
     * @param findKeys
     *            查询条件 key
     * @param findValues
     *            查询条件 value
     * @param collectionName
     *            集合名
     * @return
     */
    public Object findOne(Class obj, String[] findKeys, Object[] findValues, String collectionName) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        Object resultObj = this.mongoTemplate.findOne(query, obj, collectionName);
        return resultObj;
    }

    /**
     * 查询出所有结果集 集合为数据对象中 @Document 注解所配置的collection
     *
     * @param obj
     *            数据对象
     * @return
     */
    public List<? extends Object> findAll(Class obj) {

        List<? extends Object> resultList = this.mongoTemplate.findAll(obj);
        return resultList;
    }

    /**
     * 指定集合 查询出所有结果集
     *
     * @param obj
     *            数据对象
     * @param collectionName
     *            集合名
     * @return
     */
    public List<? extends Object> findAll(Object obj, String collectionName) {

        List<? extends Object> resultList = this.mongoTemplate.findAll(obj.getClass(), collectionName);
        return resultList;
    }


    public int count(Class obj, String[] findKeys, Object[] findValues) {

        Criteria criteria = null;
        for (int i = 0; i < findKeys.length; i++) {
            if (i == 0) {
                criteria = Criteria.where(findKeys[i]).is(findValues[i]);
            } else {
                criteria.and(findKeys[i]).is(findValues[i]);
            }
        }
        Query query = Query.query(criteria);
        long count = this.mongoTemplate.count(query, obj);
        return (int)count;
    }
    public int count(Class obj, Criteria criteria) {
        Query query = Query.query(criteria);
        long count = this.mongoTemplate.count(query, obj);
        return (int)count;
    }
}