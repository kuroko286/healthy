package com.kuroko.heathyapi.feature.oauth2;

public enum AuthProvider {
    google,
    facebook,
    github;

    public static AuthProvider from(String providerId) {
        if (providerId.equalsIgnoreCase("google")) {
            return google;
        } else if (providerId.equalsIgnoreCase("facebook")) {
            return facebook;
        } else if (providerId.equalsIgnoreCase("github")) {
            return github;
        }
        return null;
    }
}
