package com.topsci.qh.webmanagement.Kafka;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
@Component
public class InitConsumer implements Runnable{
	@Resource
    private Consumer consumer;

	@Override
	public void run() {
		if(consumer.msgconsumer == null){
			consumer.init();
			consumer.consumeMsg();
		}
	}

}
