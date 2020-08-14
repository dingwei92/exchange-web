package com.topsci.qh.webmanagement.Kafka;

import javax.annotation.Resource;


import com.topsci.qh.webmanagement.ExchageWebApplication;
import com.topsci.qh.webmanagement.Pojo.MyProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topsci.qh.webmanagement.Tools.Config;
import com.topsci.tools.kafka.producer.KafkaProducer;

import java.io.InputStream;

@Component
public class Producer {
	private Logger logger = LoggerFactory.getLogger(Producer.class);
	@Resource
	private Config config;
	
	public KafkaProducer kafkaProducer = null;
	@Autowired
	private MyProps myProps;
	public void init(){
		kafkaProducer = new KafkaProducer(config.getKfkIp(),myProps.getKafkaPath());
		//ExchageWebApplication.class.getClassLoader().getResource("").toString()+"client.truststore.jks"
		//kafkaProducer = new KafkaProducer(config.getKfkIp(),Producer.class.getClassLoader().getResource("").getPath()+"client.truststore.jks");
	}
    
	public void send(String topic,String msg){
		kafkaProducer.sendProducer(topic,msg);
		logger.debug("向"+topic+"主题发送消息:"+msg);
	}
	
	
}
