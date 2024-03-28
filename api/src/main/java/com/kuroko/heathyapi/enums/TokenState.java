package com.kuroko.heathyapi.enums;

public enum TokenState {
    VALID,
    INVALID;

    public static TokenState convertToTokenState(String tokenState) {
        tokenState = tokenState.toUpperCase();
        return switch (tokenState) {
            case "VALID" -> VALID;
            case "INVALID" -> INVALID;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return name();
    }

}
