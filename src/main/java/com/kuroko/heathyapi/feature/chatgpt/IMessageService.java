package com.kuroko.heathyapi.feature.chatgpt;

import java.util.List;

import com.kuroko.heathyapi.feature.user.User;

public interface IMessageService {

    void createMessage(Message quest);

    List<Message> getAllMessages(String email);

}
