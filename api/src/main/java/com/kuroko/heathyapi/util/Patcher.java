package com.kuroko.heathyapi.util;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

import com.kuroko.heathyapi.feature.user.model.User;

@Component
public class Patcher {
    public User userPatcher(User existingUser, User updater) throws IllegalAccessException {

        // GET THE COMPILED VERSION OF THE CLASS
        Class<?> userClass = User.class;
        Field[] userFields = userClass.getDeclaredFields();
        // System.out.println(userFields.length);
        for (Field field : userFields) {
            // System.out.println(field.getName());
            // CANT ACCESS IF THE FIELD IS PRIVATE
            field.setAccessible(true);

            // CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING INTERN

            Object value = field.get(updater);
            if (value != null) {
                field.set(existingUser, value);
            }

            // MAKE THE FIELD PRIVATE AGAIN
            field.setAccessible(false);
        }
        return existingUser;

    }

}
