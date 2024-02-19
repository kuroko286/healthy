package com.kuroko.heathyapi.exception.security;

public class OAuth2AuthenticationProcessingException extends RuntimeException {
    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
