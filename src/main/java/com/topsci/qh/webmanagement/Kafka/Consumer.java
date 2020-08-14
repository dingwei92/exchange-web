package com.topsci.qh.webmanagement.Kafka;

import javax.annotation.Resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.topsci.qh.webmanagement.Tools.Config;
import com.topsci.tools.kafka.consume.KafkaConsumer;
@Component
public class Consumer {
	private Logger logger = LoggerFactory.getLogger(Consumer.class);
	@Resource
	private Config config;
	
	public KafkaConsumer msgconsumer = null;
	
	public void init(){
			msgconsumer = new KafkaConsumer(config.getKfkIp(),config.getRequestTopic(),Producer.class.getClassLoader().getResource("/").getPath()+"client.truststore.jks");
	}
	
	public void consumeMsg(){
		try {
			msgconsumer.consume(MagDealThread.class,100);
		} catch (Exception e) {
			logger.error("接收消息失败",e);
		}
	}
}
