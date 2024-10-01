package org.finterest.chatbot.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString

public final class ChatbotResponse {
    private final String response;

    public ChatbotResponse(String response){
        if(response == null){
            throw new IllegalArgumentException("응답은 null일 수 없습니다.");
        }
        this.response = response;
    }
}
