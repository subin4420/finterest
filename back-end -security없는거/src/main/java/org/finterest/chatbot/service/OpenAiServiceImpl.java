package org.finterest.chatbot.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.finterest.chatbot.dto.OpenAiRequestDto;
import org.finterest.chatbot.dto.OpenAiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
public class OpenAiServiceImpl implements OpenAiService{

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api.key}")
    private String openAiApiKey;

    //OkHttpClient 인스턴스 추가
    private final OkHttpClient httpClient = new OkHttpClient();

    //ObjectMapper 인스턴스 추가
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void streamResponseFromOpenAi(OpenAiRequestDto requestDto, WebSocketSession session)throws Exception{
        if(openAiApiKey == null || openAiApiKey.isEmpty()){
            throw new IllegalStateException("OpenAI API 키가 설정되지 않았습니다.");
        }

        String jsonRequestBody  = objectMapper.writeValueAsString(requestDto);

        //requestBody 생성
        RequestBody requestBody = RequestBody.create(
                jsonRequestBody,
                MediaType.parse("application/json")
        );

        //API 호출 시 API키 사용
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .header("Authorization", "Bearer " + openAiApiKey)
                .post(requestBody)
                .build();

        try(Response response = httpClient.newCall(request).execute()){
            // 응답 로깅
            System.out.println("OpenAI API 응답 코드: " + response.code());

            if(!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }

            //응답 바디 처리(스트리밍 처리 필요)
            String responseBody = response.body().string();

            //응답을 파싱하여 클라이언트에 전송
            OpenAiResponseDto responseDto = parseOpenAiResponse(responseBody);

            // 응답 메시지 추출
            String assistantMessage = responseDto.getChoices().get(0).getMessage().getContent();

            // 클라이언트로 전송
            if(session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(assistantMessage));
            }


        }
    }

//    private String callOpenAiApi(OpenAiRequestDto requestDto){
//        //OpenAI API 요청 로직 구현(OpenAI API 키, 엔드포인트 등 설정 필요)
//        //이 부분은 RestTemplate 또는 WebClient로 구현 가능
//        return "OpenAI 응답 예시"; //실제 응답 내용 반환
//    }

    private OpenAiResponseDto parseOpenAiResponse(String response) throws IOException{
        //응답을 파싱하여 OpenAiResponseDto로 변환
        //예를 들어, ObjectMapper를 사용하여 JSON 파싱
        return objectMapper.readValue(response, OpenAiResponseDto.class);
    }

    @Override
    public OpenAiResponseDto getResponseFromOpenAi(OpenAiRequestDto requestDto) throws Exception {
        if(openAiApiKey == null || openAiApiKey.isEmpty()){
            throw new IllegalStateException("OpenAI API 키가 설정되지 않았습니다.");
        }

        //requestBody 생성
        String jsonRequestBody = objectMapper.writeValueAsString(requestDto);
        RequestBody requestBody = RequestBody.create(
                jsonRequestBody,
                MediaType.parse("application/json")
        );

        //API 호출 시 API 키 사용
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .header("Authorization", "Bearer " + openAiApiKey)
                .post(requestBody)
                .build();
        try(Response response = httpClient.newCall(request).execute()){
            if(!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }

            //응답 바디 처리
            String responseBody = response.body().string();

            //응답을 파싱하여 반환
            return parseOpenAiResponse(responseBody);
        }
    }
}
