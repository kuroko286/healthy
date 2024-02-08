package com.kuroko.heathyapi.feature.email;

public interface IEmailService {
    void sendEmail(String to, String subject, Object body);

}
