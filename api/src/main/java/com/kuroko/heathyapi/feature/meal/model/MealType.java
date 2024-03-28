package com.kuroko.heathyapi.feature.meal.model;

public enum MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK,
    OTHER;

    public static MealType getType(String type) {
        if (type == null) {
            return OTHER;
        }
        type = type.toLowerCase();
        return switch (type) {
            case "breakfast" -> BREAKFAST;
            case "lunch" -> LUNCH;
            case "dinner" -> DINNER;
            case "snack" -> SNACK;
            default -> OTHER;
        };
    }
}
