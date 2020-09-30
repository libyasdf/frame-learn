package com.sinosoft.sinoep.message.websocket.controller;

import org.apache.log4j.Logger;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSocket
public class WebscoketConfig implements WebSocketConfigurer {

    private static Logger log = Logger.getLogger(WebscoketConfig.class);

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myHandler(),"/dunHandler").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("file://");
	}

	@Bean
	public WebSocketHandler myHandler(){
	    log.info("创建webSocket服务service >>>>>>>>>>>>>>>>>");
		return new MyHandler();
	}
}

