package com.kuroko.heathyapi.feature.chatgpt.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ChatRequest {
    private String model;
    private List<MessageDto> messages;
    // private int n;
    // private double temperature;

    public ChatRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new MessageDto("user", prompt));
    }

}
