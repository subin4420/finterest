//package org.finterest.chatbot.controller;
//
//
//import org.finterest.chatbot.service.ChatbotService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ChatbotControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private ChatbotService chatbotService;
//
//    @InjectMocks
//    private ChatbotController chatbotController;
//
//    @BeforeEach
//    public void setup() {
//
//        mockMvc = MockMvcBuilders.standaloneSetup(chatbotController).build();
//
//        // 필요한 목킹만 유지
//        when(chatbotService.handleUserQuestion(anyString())).thenReturn("This is a financial question response");
//
//    }
//
//    @Test
//    public void testAskQuestion() throws Exception {
//        // Given: Mock behavior for ChatbotService
//        String question = "What is finance?";
//        String expectedResponse = "This is a financial question response";
//
//        // When & Then: Simulate POST request and expect OK status and correct response
//        mockMvc.perform(post("/api/chatbot/ask")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"question\":\"What is finance?\"}"))
//                .andExpect(status().isOk());
//
//        // Verify: chatbotService.handleUserQuestion가 올바른 파라미터로 호출되었는지 확인
//        verify(chatbotService, times(1)).handleUserQuestion(question);
//
//    }
//}