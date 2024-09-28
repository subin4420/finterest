package org.finterest.chatbot.controller;

import org.finterest.chatbot.dto.OpenAiRequestDto;
import org.finterest.chatbot.dto.UserMessageDto;
import org.finterest.chatbot.service.ChatbotService;
import org.finterest.chatbot.vo.ChatbotResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {
    private final ChatbotService chatbotService; // OpenAI API와 연결하는 서비스

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> handleMessage(@RequestBody UserMessageDto userMessageDto){
        try{
            //OpenAiRequestDto 생성
            OpenAiRequestDto requestDto = new OpenAiRequestDto(
                    userMessageDto.getModel(), userMessageDto.getMessages()
            );

            // WebSocketSession은 REST 컨트롤러에서는 사용할 수 없으므로, null로 전달
            ChatbotResponse response = chatbotService.processUserMessage(requestDto);
            return new ResponseEntity<>(response.getResponse(), HttpStatus.OK);

        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("서버 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
