package org.finterest.chatbot.service;

import org.finterest.chatbot.dto.OpenAiRequestDto;
import org.finterest.chatbot.vo.ChatbotResponse;
import org.springframework.web.socket.WebSocketSession;

public interface ChatbotService {
    void processUserMessageStream(OpenAiRequestDto requestDto, WebSocketSession session) throws Exception;
    ChatbotResponse processUserMessage(OpenAiRequestDto requestDto) throws Exception;
}
