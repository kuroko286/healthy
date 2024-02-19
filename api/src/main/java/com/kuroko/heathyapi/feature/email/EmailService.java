package com.kuroko.heathyapi.feature.email;

public interface EmailService {
    void sendEmail(String to, String subject, Object body);

}
