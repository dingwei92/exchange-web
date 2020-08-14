package com.topsci.qh.webmanagement.InitTask;

import com.topsci.qh.webmanagement.InitTask.Tasks.DateUpdateTask;
import com.topsci.qh.webmanagement.Kafka.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by lzw on 2016/12/5.
 */
@Component
public class Init implements ApplicationListener<ContextRefreshedEvent>{

   /* @Resource
    private AreaCodeTask codeTask;*/
    @Resource
    private DateUpdateTask dateUpdateTask;
   /* @Resource
    private OrganCodeTask organCodeTask;*/
    @Resource
    private Producer producer;
    //spring初始化后执行的代码
    private Logger logger = LoggerFactory.getLogger(Init.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            logger.info("init初始化");
            producer.init();
            ScheduledExecutorService tasks = Executors.newSingleThreadScheduledExecutor();
            //初始化缓存
        }
    }
}
