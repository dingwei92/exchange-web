package com.topsci.qh.webmanagement.WebSocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import java.util.Map;

/**
 * Created by lzw on 2016/12/14.
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
            throws Exception {
//        System.out.println("Before Handshake");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,ServerHttpResponse response, WebSocketHandler wsHandler,Exception ex) {
//        System.out.println("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }

}