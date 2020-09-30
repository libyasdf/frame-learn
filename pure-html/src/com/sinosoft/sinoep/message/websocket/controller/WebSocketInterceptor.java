package com.sinosoft.sinoep.message.websocket.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class WebSocketInterceptor /*extends HttpSessionHandshakeInterceptor*/ implements HandshakeInterceptor {

    private static Logger log = Logger.getLogger(WebSocketInterceptor.class);

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception ex) {
        // TODO Auto-generated method stub
        log.info("afterHandshake");
        if (ex != null) {
            ex.printStackTrace();
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse arg1, WebSocketHandler arg2,
                                   Map<String, Object> attribute) throws Exception {
        //将增强的request转换httpservletRequest
        log.info("beforeHandshake");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = serverHttpRequest.getServletRequest().getSession();
            String parameter = ((ServletServerHttpRequest) request).getServletRequest().getParameter("id");
            if (session != null) {
                session.setAttribute("userId", parameter);
                attribute.put("userId", parameter);
            }
            if (parameter.equals("") || parameter == null) {
                return false;
            }
        }
        //放行
        return true;
    }

}
