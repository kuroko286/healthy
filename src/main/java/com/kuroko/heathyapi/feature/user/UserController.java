package com.kuroko.heathyapi.feature.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kuroko.heathyapi.feature.user.payload.Goal;
import com.kuroko.heathyapi.feature.user.payload.GoalUpdatedDto;
import com.kuroko.heathyapi.feature.user.payload.StatisticsDto;
import com.kuroko.heathyapi.feature.user.payload.UserDto;
import com.kuroko.heathyapi.feature.user.payload.UserReq;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsDto> getStatistics(@RequestParam int month,
            @RequestAttribute("email") String email) {
        return ResponseEntity.ok().body(userService.getStatistics(month, email));
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(@RequestAttribute("email") String email) {
        return ResponseEntity.ok().body(userService.getCurrentUser(email));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestAttribute("email") String email,
            @RequestBody UserReq userData) {
        UserDto user = userService.updateUserInfo(email, userData);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/goal")
    public ResponseEntity<GoalUpdatedDto> updateUserGoal(@RequestAttribute("email") String email,
            @RequestBody Goal goal) {
        GoalUpdatedDto goalUpdated = userService.updateUserGoal(email, goal);
        return ResponseEntity.ok().body(goalUpdated);
    }

    @PostMapping("/avatar")
    public ResponseEntity<String> updateUserAvatar(@RequestAttribute("email") String email,
            @RequestPart("avatar") MultipartFile avatar) {
        userService.updateUserAvatar(email, avatar);
        return ResponseEntity.ok().body("Avatar updated successfully");
    }
}
