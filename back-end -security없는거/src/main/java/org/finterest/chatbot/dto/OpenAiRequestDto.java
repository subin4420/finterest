package org.finterest.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiRequestDto {
    private String model;
    private List<Message> messages;

    public OpenAiRequestDto(String model, String userMessage){
        this.model = model;
        this.messages = Arrays.asList(
                Message.builder()
                        .role("user")
                        .content(userMessage)
                        .build()
        );
    }
}
