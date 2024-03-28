package com.kuroko.heathyapi.feature.chatgpt.service;

import java.util.List;

import org.springframework.lang.NonNull;

import com.kuroko.heathyapi.feature.chatgpt.model.ChatMessage;

public interface MessageService {

    ChatMessage createMessage(@NonNull ChatMessage quest);

    List<ChatMessage> getAllMessages(Long id);

}
