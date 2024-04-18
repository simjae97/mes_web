package com.team1.config;

import com.team1.controller.safetySocket.SafetySocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration //스프링 컨테이너에 빈 등록
@EnableWebSocket //웹소켓 매핑
public class WebSocketMapping implements WebSocketConfigurer {

    @Autowired
    private SafetySocket safetySocket;

    @Override //1.웹소켓 매핑 등록
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // - ws로 등록된 url들을 어디로 핸들러 할건지 정의
        registry.addHandler(safetySocket,"/safety");;
    }
    //스프링 버전에 따라 라이브러리 이름이 다를수 있음

    //1. 웹소켓 매핑 등록
    //
}
