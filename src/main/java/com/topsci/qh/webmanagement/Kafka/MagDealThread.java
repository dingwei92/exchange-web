package com.topsci.qh.webmanagement.Kafka;

import java.util.List;

import org.springframework.web.socket.TextMessage;

import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.WebSocket.WebsocketEndPoint;
import com.topsci.tools.kafka.consume.ConsumeThread;

public class MagDealThread extends ConsumeThread{

	@Override
	public void run() {
		String msg = super.getMessage();
		List<WebsocketEndPoint> list = Constants.sockets;
		for(WebsocketEndPoint ws : list){
			ws.sendMessageToUsers(new TextMessage(msg));
		}
	}

}
