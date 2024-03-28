package com.kuroko.heathyapi.feature.user.model;

// 
public enum Goal {
    LOSE_WEIGHT,
    MAINTAIN,
    GAIN_MUSCLE;

    public static Goal getGoal(String goal) {
        return switch (goal) {
            case "lose_weight" -> LOSE_WEIGHT;
            case "maintain" -> MAINTAIN;
            case "gain_muscle" -> GAIN_MUSCLE;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', ' ');
    }

}
