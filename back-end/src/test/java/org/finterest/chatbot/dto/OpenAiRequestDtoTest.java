package org.finterest.chatbot.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenAiRequestDtoTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testOpenAiRequestDtoBuilder() {
        OpenAiRequestDto requestDto = new OpenAiRequestDto("gpt-3.5-turbo", "안녕하세요!");

        assertEquals("gpt-3.5-turbo", requestDto.getModel());
        assertNotNull(requestDto.getMessages());
        assertEquals(1, requestDto.getMessages().size());

        Message message = requestDto.getMessages().get(0);
        assertEquals("user", message.getRole());
        assertEquals("안녕하세요!", message.getContent());
        assertNull(message.getRefusal());
    }

    @Test
    public void testMessageBuilder() {
        Message message = Message.builder()
                .role("assistant")
                .content("안녕하세요! 어떻게 도와드릴까요?")
                .refusal(null)
                .build();

        assertEquals("assistant", message.getRole());
        assertEquals("안녕하세요! 어떻게 도와드릴까요?", message.getContent());
        assertNull(message.getRefusal());
    }

    @Test
    public void testDeserializeMessage() throws Exception {
        String json = "{ \"role\": \"assistant\", \"content\": \"안녕하세요! 어떻게 도와드릴까요?\", \"refusal\": null }";
        Message message = objectMapper.readValue(json, Message.class);

        assertEquals("assistant", message.getRole());
        assertEquals("안녕하세요! 어떻게 도와드릴까요?", message.getContent());
        assertNull(message.getRefusal());
    }
}