package com.team1.controller.safetySocket;

import com.fazecast.jSerialComm.SerialPort;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.*;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ScheduledFuture;

@Component
@EnableScheduling
public class SafetySocket extends TextWebSocketHandler implements DisposableBean{


    private List<WebSocketSession> 접속명단 = new Vector<>(); // 동기화 떄문에 벡터 사용

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("session = " + session);
        //1.접속한 세션정보를 리스트에 담기
        접속명단.add(session);
        System.out.println("접속명단 = " + 접속명단);

    }


    @Autowired
    private TaskScheduler taskScheduler;

    private ScheduledFuture<?> scheduledFuture;

    @PostConstruct
    public void initialize() {
        startScheduler();
    }

    public String serial(){
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("스케쥴러실행");
        // 첫 번째 시리얼 포트를 선택하여 엽니다. 포트의 인덱스를 조정하여 필요한 포트를 선택할 수 있습니다.
        SerialPort selectedPort = ports[1];
        selectedPort.openPort();

        // 시리얼 포트의 설정을 구성합니다. 이 예제에서는 전송 속도를 9600 bps로 설정합니다.
        selectedPort.setBaudRate(9600);
//        selectedPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        출처: https://milanok.tistory.com/entry/자바-시리얼-통신-예제 [IT 엔지니어:티스토리]
        // 시리얼 포트로 데이터를 쓰기 위한 출력 스트림을 가져옵니다. 안되니까 잠깐 폐기
//        try {
//            System.out.println("출력체크");
//
//            System.out.println(selectedPort.isOpen());
//            System.out.println(selectedPort.getOutputStream());
////            PrintWriter output = new PrintWriter(selectedPort.getOutputStream()); //안됨
////            DataOutputStream output = new DataOutputStream(selectedPort.getOutputStream()); //안됨
//            String check = "on";
//            OutputStream outputStream = selectedPort.getOutputStream().nullOutputStream(); //안됨
////            outputStream.write(check.getBytes());
////            output.writeUTF(check);
////            outputStream.write(check.getBytes(),0,check.getBytes().length); // 이것도 안됨
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//


        // 시리얼 포트로부터 데이터를 읽기 위한 입력 스트림을 가져옵니다.
        try {
            byte[] buffer = new byte[1024];
            int bytesRead = selectedPort.getInputStream().read(buffer);
            selectedPort.closePort();
            return new String(buffer, 0, bytesRead);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 시리얼 포트를 닫습니다

        return null;
    }
    public void startScheduler() {
        scheduledFuture = taskScheduler.scheduleAtFixedRate(this::doPost, 3000);
    }
    public void stopScheduler() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }
    @Override
    public void destroy() throws Exception {
        stopScheduler();
    } //

    //스케쥴러 안쓰기해볼때용

    public void doPost() {
        String result = 100+"";
//        result = serial().split("\n")[1]; //아두이노 연동했을떄 사용
//        System.out.println("소켓 체크용");
//        System.out.println("나온 거리:"+result);


        try {
            WebSocketMessage<String> sendmessage = new TextMessage(result);
//            System.out.println(sendmessage.getPayload());
//            System.out.println(sendmessage.getPayload()+"를 보냅니다");
            for (WebSocketSession webSocketSession : 접속명단) {
                webSocketSession.sendMessage(sendmessage);
            }

//            System.out.println(sendmessage);
        }
        catch (Exception e ){
            System.out.println(e);
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        접속명단.remove(session);
    }

}
