package org.finterest.chatbot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.finterest.chatbot.dto.Message;
import org.finterest.chatbot.dto.OpenAiRequestDto;
import org.finterest.chatbot.dto.UserMessageDto;
import org.finterest.chatbot.service.ChatbotService;
import org.finterest.chatbot.vo.ChatbotResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ChatbotControllerTest {

    private ChatbotController chatbotController;

    @Mock
    private ChatbotService chatbotService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        chatbotController = new ChatbotController(chatbotService);
    }

    @Test
    void testHandleMessage() throws Exception{
        //테스트 데이터 설정
        String chatbotServiceResponse = "안녕하세요! 햄진이입니다. 무엇을 도와드릴까요?";
        String userMessage = "안녕하세요!";
        String model = "gpt-3.5-turbo";

        // 메시지 리스트 생성
        Message userMessageObj = Message.builder()
                .role("user")
                .content(userMessage)
                .build();
        UserMessageDto userMessageDto = new UserMessageDto(model, Collections.singletonList(userMessageObj));

        // OpenAiRequestDto 생성
        OpenAiRequestDto expectedRequestDto = new OpenAiRequestDto(model, Collections.singletonList(userMessageObj));

        // ChatbotResponse 생성
        ChatbotResponse chatbotResponse = new ChatbotResponse(chatbotServiceResponse);

        //ChatbotService의 모킹 동작 정의
        when(chatbotService.processUserMessage(eq(expectedRequestDto))).thenReturn(chatbotResponse);

        //컨트롤러 메서드 호출
        ResponseEntity<String> response = chatbotController.handleMessage(userMessageDto);

        //응답 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(chatbotServiceResponse, response.getBody());

        // ChatbotService가 올바르게 호출되었는지 검증
        ArgumentCaptor<OpenAiRequestDto> requestDtoCaptor = ArgumentCaptor.forClass(OpenAiRequestDto.class);
        verify(chatbotService, times(1)).processUserMessage(requestDtoCaptor.capture());

        OpenAiRequestDto capturedRequestDto = requestDtoCaptor.getValue();
        assertEquals(model, capturedRequestDto.getModel());
        assertEquals(1, capturedRequestDto.getMessages().size());
        assertEquals(userMessage, capturedRequestDto.getMessages().get(0).getContent());
    }
}