package com.kuroko.heathyapi.enums;

public enum CookieName {
    ACCESS_TOKEN,
    USER_ID,
    REFRESH_TOKEN;

    @Override
    public String toString() {
        switch (this) {
            case ACCESS_TOKEN:
                return "access_token";
            case USER_ID:
                return "user_id";
            case REFRESH_TOKEN:
                return "refresh_token";
            default:
                return "unknown";
        }
    }
}
