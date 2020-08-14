package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.ServerChangeTask;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.MongodbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lzw.
 * 16-7-2
 */
@Service
public class ChangeTaskService extends BasicService {

    private final String SUBSCRIBE = "S";
    private final String CATALOG = "C";
    private final String TEMPLATE = "T";
    private final String NOTDEALT = "N";
    public final String CREATE = "A";
    public final String UPDATE = "U";
    public final String DELETE = "D";
    @Resource
    private MongodbService mongodbService;

    /**
     * 订阅详情发生变更
     * @param id
     * @param type
     */
    public void subscribeTask(int id,String type)
    {
        ServerChangeTask task = new ServerChangeTask();
        task.setChangeType(SUBSCRIBE);
        task.setDealState(type);
        task.setStatus(NOTDEALT);
        task.setServerChangeId(id);
        task.setLastdate(StardTime.format(new Date()));
        /*save(task);*/
        mongodbService.save(task);

    }

    /**
     * 接口数据发生变更
     * @param id
     * @param type
     */
    public void catalogTask(int id,String type)
    {
        ServerChangeTask task = new ServerChangeTask();
        task.setChangeType(CATALOG);
        task.setDealState(type);
        task.setStatus(NOTDEALT);
        task.setServerChangeId(id);
        task.setLastdate(StardTime.format(new Date()));
       /* save(task);*/
        mongodbService.save(task);
    }

    /**
     * 模板数据发生变更
     * @param id
     * @param type
     */
    public void templateTask(int id,String type)
    {
        ServerChangeTask task = new ServerChangeTask();
        task.setChangeType(TEMPLATE);
        task.setDealState(type);
        task.setStatus(NOTDEALT);
        task.setServerChangeId(id);
        task.setLastdate(StardTime.format(new Date()));
        mongodbService.save(task);
    }
}
