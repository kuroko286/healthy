package com.kuroko.heathyapi.feature.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kuroko.heathyapi.feature.account.payload.AuthResponse;
import com.kuroko.heathyapi.feature.user.payload.UserReq;
import com.kuroko.heathyapi.feature.user.payload.WeightDto;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(@RequestParam int month, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(userService.getStatistics(month, token));
    }

    @GetMapping("/current")
    public ResponseEntity<AuthResponse> getCurrentUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(userService.getCurrentUser(token));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserReq userData) {
        // TODO: Implement update user
        userService.updateUserInfo(token, userData);
        return ResponseEntity.ok().body("User updated successfully");
    }

    @PutMapping
    public ResponseEntity<?> updateUserGoal(@RequestHeader("Authorization") String token, @RequestBody String goal) {
        userService.updateUserGoal(token, goal);
        return ResponseEntity.ok().body("User goal updated successfully");
    }

    @PostMapping("/weight")
    public ResponseEntity<?> addUserWeight(@RequestHeader("Authorization") String token,
            @RequestBody WeightDto weightDto) {
        userService.addUserWeight(token, weightDto);
        return ResponseEntity.ok().body("Weight added successfully");
    }
}
