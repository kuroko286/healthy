package com.kuroko.heathyapi.feature.chatgpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.Account;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.user.User;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void createMessage(ChatMessage quest) {
        messageRepository.save(quest);
    }

    @Override
    public List<ChatMessage> getAllMessages(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                "Account with email " + email + " not found."));
        User user = account.getUser();
        return messageRepository.findByUser(user);
    }

}