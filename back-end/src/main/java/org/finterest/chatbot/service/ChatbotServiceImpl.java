package org.finterest.chatbot.service;

import org.finterest.chatbot.dto.OpenAiRequestDto;
import org.finterest.chatbot.dto.OpenAiResponseDto;
import org.finterest.chatbot.vo.ChatbotResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;


@Service
public class ChatbotServiceImpl implements ChatbotService{

    private final OpenAiService openAiService;

    public ChatbotServiceImpl(OpenAiService openAiService){
        this.openAiService = openAiService;
    }


    @Override
    public void processUserMessageStream(OpenAiRequestDto requestDto, WebSocketSession session) throws Exception {
        //OpenAI API 호출
        openAiService.streamResponseFromOpenAi(requestDto, session);
    }

    @Override
    public ChatbotResponse processUserMessage(OpenAiRequestDto requestDto) throws Exception {
        // OpenAI API를 통해 동기식으로 응답을 받아 ChatbotResponse로 반환
        OpenAiResponseDto responseDto = openAiService.getResponseFromOpenAi(requestDto);
        String assistantMessage = responseDto.getChoices().get(0).getMessage().getContent();
        return new ChatbotResponse(assistantMessage);
    }

}
