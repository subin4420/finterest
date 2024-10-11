//package org.finterest.chatbot.config;
//
////import org.finterest.chatbot.websocket.ChatWebSocketHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.server.RequestUpgradeStrategy;
//import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
//import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
//
//@Configuration
//@EnableWebSocket
//@Profile("!test")
//public class WebsocketConfig implements WebSocketConfigurer {
//    private final ChatWebSocketHandler chatWebSocketHandler;
//
//    public WebsocketConfig(ChatWebSocketHandler chatWebSocketHandler){
//        this.chatWebSocketHandler = chatWebSocketHandler;
//    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(chatWebSocketHandler, "/ws/chat")
//                .setAllowedOrigins("*"); //CORS 설정
//    }
//    @Bean
//    public DefaultHandshakeHandler handshakeHandler() {
//        return new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy());
//    }
//    @Bean
//    public RequestUpgradeStrategy requestUpgradeStrategy() {
//        return new TomcatRequestUpgradeStrategy(); // 톰캣을 사용하는 경우
//    }
//}
