package org.finterest.chatbot.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageDto {
    private String model;
    private List<Message> messages;
}
