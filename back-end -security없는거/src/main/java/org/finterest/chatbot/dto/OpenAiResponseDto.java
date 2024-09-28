package org.finterest.chatbot.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) //알려지지 않은 필드를 무시
public class OpenAiResponseDto {
    private String id;
    private String object; //응답 객체의 타입 나타내는 변수
    private int created; //UNIX 타임스탬프로 생성시간
    private String model;
    private List<Choice> choices; //응답 결과의 선택지 담는 Choice 객체의 리스트
    private Usage usage;

    //내부 클래스
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true) //알려지지 않은 필드를 무시
    public static class Choice {
        private String text;
        private Message message;
        private String finishReason;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true) //알려지지 않은 필드를 무시
    public static class Usage{
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }
}
