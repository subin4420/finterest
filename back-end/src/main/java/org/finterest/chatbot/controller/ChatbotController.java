package org.finterest.chatbot.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.chatbot.service.ChatbotService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
@Slf4j
public class ChatbotController {

    private final ChatbotService chatbotService;


    @PostMapping("/ask/{query}")
    public ResponseEntity<String> ask(@PathVariable String query) {
        // 사용자 질문 = query
        String stockPrice = chatbotService.getStockPrice(query);

        // HTTP 헤더에 Content-Type 설정 (한글)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "html", StandardCharsets.UTF_8)); // UTF-8 설정

        // ResponseEntity로 응답 반환
        return new ResponseEntity<>(stockPrice, headers, HttpStatus.OK);
    }
}
