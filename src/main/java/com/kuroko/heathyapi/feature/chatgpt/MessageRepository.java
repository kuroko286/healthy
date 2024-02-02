package com.kuroko.heathyapi.feature.chatgpt;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kuroko.heathyapi.feature.user.User;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByUser(User user);

}
