package com.topsci.qh.webmanagement.WebSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.springframework.web.socket.*;

import com.topsci.qh.webmanagement.Kafka.InitConsumer;
import com.topsci.qh.webmanagement.Kafka.Producer;
import com.topsci.qh.webmanagement.Tools.Config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;

/**
 * Created by lzw on 2016/12/14.
 */
@Component
public class WebsocketEndPoint extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketEndPoint.class);
    private static final List<WebSocketSession> users = new CopyOnWriteArrayList<>();
    @Resource
	private Config config;
    
    @Resource
    private Producer producer;
    
    @Resource
    private InitConsumer initConsumer;
    
    private Timer timer = null;
    
    private TimerTask task = null;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
    private boolean isConsumerInit = false;
    
    private boolean isTimer = false;

    //初次链接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("链接成功......");
        users.add(session);
        String userName = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
//        if(userName!= null){
//            //查询未读消息
//            int count = 5;
//            session.sendMessage(new TextMessage(count + ""));
//        }
        if(users.size()>0 && isTimer == false){
        	if(!isConsumerInit){
    			Thread initC = new Thread(initConsumer);
    			initC.setName("initConsumer");
    			initC.start();
    			isConsumerInit = true;
    		}
//    		if(producer.kafkaProducer == null){
//    			producer.init();
//    		}
    		timer = new Timer();
    		task = new TimerTask() {
    			 
    	        @Override
    	        public void run() {
    	        	dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    	        	producer.send(config.getSendTopic(), dateFormat.format(new Date()));
    	        }
    	    };
    		timer.schedule(task, 0,2000);
    		isTimer = true;
        }
    }

    //接受消息处理消息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        sendMessageToUsers(new TextMessage(webSocketMessage.getPayload() + ""));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.debug("链接出错，关闭链接......");
        users.remove(webSocketSession);
        if(users.size()==0 && isTimer == true){
        	timer.cancel();
        	isTimer = false;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.debug("链接关闭......" + closeStatus.toString());
        users.remove(webSocketSession);
        if(users.size()==0 && isTimer == true){
        	timer.cancel();
        	isTimer = false;
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public synchronized void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}