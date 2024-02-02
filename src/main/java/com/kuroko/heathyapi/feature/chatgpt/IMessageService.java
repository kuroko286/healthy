package com.kuroko.heathyapi.feature.chatgpt;

import java.util.List;

import com.kuroko.heathyapi.feature.user.User;

public interface IMessageService {

    void createMessage(ChatMessage quest);

    List<ChatMessage> getAllMessages(String email);

}
