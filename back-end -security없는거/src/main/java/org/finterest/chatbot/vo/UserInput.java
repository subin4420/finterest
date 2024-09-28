package org.finterest.chatbot.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public final class UserInput {
    private final String message;

    public UserInput(String message){
        if(message==null || message.trim().isEmpty()){
            throw new IllegalArgumentException("메시지는 비어있을 수 없습니다.");
        }
        this.message = message;
    }

}
