package org.finterest.chatbot.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.finterest.chatbot.dto.OpenAiRequestDto;
import org.finterest.chatbot.dto.UserMessageDto;
import org.finterest.chatbot.service.ChatbotService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    private final ChatbotService chatbotService;
    private final ObjectMapper objectMapper;

    public ChatWebSocketHandler(ChatbotService chatbotService, ObjectMapper objectMapper) {
        this.chatbotService = chatbotService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //연결이 수립된 후 처리 로직
        System.out.println("WebSocket 연결 수립: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        try {
            // 클라이언트로부터 메시지 수신
            String payload = message.getPayload().toString();


            // JSON 문자열을 객체로 변환
            UserMessageDto userMessageDto = objectMapper.readValue(payload, UserMessageDto.class);

            //OpenAiRequestDto 생성
            OpenAiRequestDto requestDto = new OpenAiRequestDto(
                    userMessageDto.getModel(), userMessageDto.getMessages()
            );

            System.out.println("Received message: " + userMessageDto);

            // 메시지 처리
            chatbotService.processUserMessageStream(requestDto, session);
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 메시지를 클라이언트에게 전송
            session.sendMessage(new TextMessage("서버 에러가 발생했습니다."));
            // 연결 종료
//            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //에러 처리 로직
        exception.printStackTrace();
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //연결이 종료된 후 처리 로직
        System.out.println("WebSocket 연결 종료: " + session.getId() + " - " + closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
