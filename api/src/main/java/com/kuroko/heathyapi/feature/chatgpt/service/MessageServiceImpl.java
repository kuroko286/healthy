package com.kuroko.heathyapi.feature.chatgpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.chatgpt.MessageRepository;
import com.kuroko.heathyapi.feature.chatgpt.model.ChatMessage;
import com.kuroko.heathyapi.feature.user.model.User;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ChatMessage createMessage(@NonNull ChatMessage quest) {
        return messageRepository.save(quest);
    }

    @Override
    public List<ChatMessage> getAllMessages(Long id) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Account with id " + id + " not found."));
        User user = account.getUser();
        return messageRepository.findByUser(user);
    }

}