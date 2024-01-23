package com.kuroko.heathyapi.feature.user.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReq {
    private String name;
    private String age;
    private String height;
    private String weight;
    private String gender;
    private String coefficientOfActivity;
}
