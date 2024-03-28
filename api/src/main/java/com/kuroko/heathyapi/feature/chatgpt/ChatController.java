package com.kuroko.heathyapi.feature.chatgpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.feature.account.AccountRepository;
import com.kuroko.heathyapi.feature.account.model.Account;
import com.kuroko.heathyapi.feature.chatgpt.dto.ChatRequest;
import com.kuroko.heathyapi.feature.chatgpt.dto.ChatResponse;
import com.kuroko.heathyapi.feature.chatgpt.dto.Prompt;
import com.kuroko.heathyapi.feature.chatgpt.model.ChatMessage;
import com.kuroko.heathyapi.feature.chatgpt.model.Role;
import com.kuroko.heathyapi.feature.chatgpt.service.MessageService;
import com.kuroko.heathyapi.feature.user.model.User;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatController {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/v1/chatgpt/{id}/messages")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getAllMessages(id));
    }

    @MessageMapping("/chatgpt")
    public String chat(@Payload Prompt prompt) {
        // create ChatMessage from prompt
        String email = prompt.getEmail();
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email " + email + " not found."));
        User user = account.getUser();
        ChatMessage quest = ChatMessage.builder().role(Role.USER).content(prompt.getText()).user(user).build();
        ChatMessage savedQuest = messageService.createMessage(quest);

        messagingTemplate.convertAndSendToUser(email, "/private", savedQuest);

        String res = promptToChatGpt(prompt);
        ChatMessage answer = ChatMessage.builder().role(Role.ASSISTANT).content(res).user(user).build();
        ChatMessage savedAnswer = messageService.createMessage(answer);
        messagingTemplate.convertAndSendToUser(email, "/private", savedAnswer);
        return "Sended ChatMessage";
    }

    public String promptToChatGpt(Prompt prompt) {
        // create a request
        ChatRequest request = new ChatRequest(model, prompt.getText());

        // call the chatgpt API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
        String res;
        if (response == null || response.getChoices() == null ||
                response.getChoices().isEmpty()) {
            res = "No response";
        } else {
            // get the first response
            res = response.getChoices().get(0).getMessage().getContent();
        }
        return res;
    }
}
