package com.test.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket 연결이 열렸습니다.");
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        System.out.println("클라이언트로부터 메시지를 받았습니다: " + receivedMessage);

        sendMessageToAllClients(receivedMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket 연결이 닫혔습니다.");
        sessions.remove(session);
    }

    private void sendMessageToAllClients(String message) throws IOException {
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(message));
        }
    }
}

