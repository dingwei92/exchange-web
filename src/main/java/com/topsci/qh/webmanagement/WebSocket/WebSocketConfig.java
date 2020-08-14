package com.topsci.qh.webmanagement.WebSocket;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.topsci.qh.webmanagement.Resources.Constants;

/**
 * Created by lzw on 2016/12/14.
 */
@Configuration
@EnableWebSocket//开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Resource
	private WebsocketEndPoint websocketEndPoint;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	//WebsocketEndPoint we = new WebsocketEndPoint();
    	Constants.sockets.add(websocketEndPoint);
        registry.addHandler(websocketEndPoint,"/websocket/log").setAllowedOrigins("*").addInterceptors(new HandshakeInterceptor()); //支持websocket 的访问链接
//        registry.addHandler(new WebSocketHander(),"/sockjs/echo").addInterceptors(new HandshakeInterceptor()).withSockJS(); //不支持websocket的访问链接
    }

}