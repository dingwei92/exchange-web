package com.topsci.qh.webmanagement.Service.springcloudstream;


import com.topsci.qh.webmanagement.Service.Normal.BaseDataService;
import com.topsci.qh.webmanagement.messaging.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;


/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: dingwei
 * @Date: 2020/06/24/15:13
 * @Description: Sink Service
 **/
@Service
@EnableBinding(Processor.class)
public class RecieveService {
    private Logger logger = LoggerFactory.getLogger(BaseDataService.class);


    //使用inputrec通道
    @StreamListener(Processor.INPUT_RECEIVE)
    public void subscribeByRec(String message) {
        logger.info("*******************springcloudstream*****************");
        logger.info("StreamListener RECEIVE_TOPIC 收到消息：{}", message);
    }

    //使用inputstatus通道
    @StreamListener(Processor.INPUT_RECEIVE_STATUS)
    public void subscribeByRecStatus(String message) {
        logger.info("*******************springcloudstream*****************");
        logger.info("StreamListener RECEIVE_TOPIC_STATUS 收到消息：{}", message);

    }
}