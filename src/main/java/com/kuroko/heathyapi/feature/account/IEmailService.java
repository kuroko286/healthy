package com.kuroko.heathyapi.feature.account;

public interface IEmailService {
    void sendEmail(String to, String subject, Object body);
}
