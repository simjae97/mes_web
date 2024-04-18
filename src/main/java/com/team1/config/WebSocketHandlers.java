package com.team1.config;

import com.team1.controller.AlertSocekt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration // 스프링 컨테이너에 빈 등록 (설정)
@EnableWebSocket // 웹 소켓 매핑
public class WebSocketHandlers implements WebSocketConfigurer {
    @Autowired
    private AlertSocekt alertSocekt; // 검사 완료 알림창 관련 서버 소켓

    @Override // 1. 웹소켓 매핑 등록
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // - ws 로 요청된 url 들을 어디로 핸들러 할껀지 정의 설정
        registry.addHandler(alertSocekt, "/");
    } //
}
