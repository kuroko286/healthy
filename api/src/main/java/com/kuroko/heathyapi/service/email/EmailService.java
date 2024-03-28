package com.kuroko.heathyapi.service.email;

public interface EmailService {
    void sendEmail(String to, String subject, Object body);

}
