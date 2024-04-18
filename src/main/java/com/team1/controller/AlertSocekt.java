package com.team1.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Vector;

@Component
public class AlertSocekt extends TextWebSocketHandler {
    // 0. 접속 성공한 session 들을 모아두기 ( 접속 명단 )
    private List<WebSocketSession> 접속명단 = new Vector<>(); // ArrayList 비동기화 vs Vector 동기화 : 순차적으로 처리

    // 1. 클라이언트 소켓의 (서버소켓)접속이 성공일떄. ( session : 접속한 클라이언트의 소켓 정보 )
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("session = " + session);
        // 1. 접속한 세션정보를 리스트에 담기
        접속명단.add(session);
        // 2. 확인
        System.out.println("접속명단 = " + 접속명단);
    }

    // 서버(검사 완료 시 ) 클라이언트에 메세지 보내기
    public void sendString(TextMessage message) throws Exception{

        System.out.println("message = " + message);
        System.out.println("접속명단 = " + 접속명단);

        // 접속명단의 클라이언트 소켓들에게 메시지 보내기
        for (WebSocketSession 각클라이언트소켓 : 접속명단){
            각클라이언트소켓.sendMessage(message);
            // DB 처리시 여기서 DB 처리 한다.

        }
    }


    // 3. 클라이언트 소켓과 접속 종료 되었을떄 ( session : 접속이 종료된 클라이언트 소켓 정보 )
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        접속명단.remove(session);
    }
}
