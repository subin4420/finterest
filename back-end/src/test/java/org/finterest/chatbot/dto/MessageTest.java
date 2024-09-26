package org.finterest.chatbot.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MessageTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testDeserializeWithRefusal() throws Exception {
        String json = "{ \"role\": \"assistant\", \"content\": \"안녕하세요! 어떻게 도와드릴까요?\", \"refusal\": null }";
        Message message = objectMapper.readValue(json, Message.class);

        assertEquals("assistant", message.getRole());
        assertEquals("안녕하세요! 어떻게 도와드릴까요?", message.getContent());
        // refusal 필드가 null인지 확인
        if (message.getRefusal() != null) {
            assertNull(message.getRefusal());
        }
    }

    @Test
    public void testDeserializeWithoutRefusal() throws Exception {
        String json = "{ \"role\": \"assistant\", \"content\": \"안녕하세요! 어떻게 도와드릴까요?\" }";
        Message message = objectMapper.readValue(json, Message.class);

        assertEquals("assistant", message.getRole());
        assertEquals("안녕하세요! 어떻게 도와드릴까요?", message.getContent());
        // refusal 필드가 null인지 확인
        assertNull(message.getRefusal());
    }
}