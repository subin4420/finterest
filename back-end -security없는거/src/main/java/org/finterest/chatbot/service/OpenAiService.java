package org.finterest.chatbot.service;


import org.finterest.chatbot.dto.OpenAiRequestDto;
import org.finterest.chatbot.dto.OpenAiResponseDto;
import org.springframework.web.socket.WebSocketSession;

public interface OpenAiService {
    void streamResponseFromOpenAi(OpenAiRequestDto requestDto, WebSocketSession session) throws Exception;
    OpenAiResponseDto getResponseFromOpenAi(OpenAiRequestDto requestDto) throws Exception;
}
