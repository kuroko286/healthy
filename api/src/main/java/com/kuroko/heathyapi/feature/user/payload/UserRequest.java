package com.kuroko.heathyapi.feature.user.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    private String name;
    private int age;
    private double height;
    private double weight;
    private String gender;
    private double coefficientOfActivity;
}
