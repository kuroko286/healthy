package com.kuroko.heathyapi.feature.user.model;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender getGender(String gender) {
        if (gender == null) {
            return MALE;
        }
        gender = gender.toLowerCase();
        return switch (gender) {
            case "male" -> MALE;
            case "female" -> FEMALE;
            default -> MALE;
        };
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
