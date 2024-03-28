package com.kuroko.heathyapi.feature.oauth2.user;

import java.util.Map;

import com.kuroko.heathyapi.exception.security.OAuth2AuthenticationProcessingException;

// Factory pattern to create OAuth2UserInfo for Google, Facebook, Github
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase("google")) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase("facebook")) {
            return new FacebookOauth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase("github")) {
            return new GithubOauth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(
                    "Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
